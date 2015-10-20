/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.dao.AmbienteDAO;
import br.com.QuadroDeHorario.dao.EmprestaChaveDAO;
import br.com.QuadroDeHorario.dao.UsuarioDAO;
import br.com.QuadroDeHorario.entity.Ambiente;
import br.com.QuadroDeHorario.entity.EmprestaChave;
import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.model.SerialConstants;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI01
 */
public class SerialCommunication implements Runnable, SerialPortEventListener {

    //Mensagens do sistema para 
    private static final String MSG_AGUARDANDO = "Conexão estabelecida! Aguardando leitura... ";
    private static final String MSG_DESCONECTADO = "Equipamento desconectado...";

    //Todas as portas
    private Enumeration carregarPortas;
    //Verifica se ainda existe conexão
    private boolean conexao;
    //Porta onde o aparelho está conectado
    private SerialPort serialPort;
    //Armazena a mensagem lida pelo aparelho
    private String mensagem;

    //Entrada e saida de dados da porta serial
    private InputStream inputStream;
    private OutputStream outputStream;

    private String status;
    //Ultima vez que chego/Envio informação
    private Date lastInfo;
    //Mensagem promptext
    private String debug;

    //Caso apenas leitura de cartão e chave
    private boolean cadastro;
    //Para realizar a entrega da chave
    private boolean entrega;
    //Funciona como leitura de tags-> somente para cadastro
    private String leitura;

    //Caso leu cartão e nao recebeu chave
    private Thread leuCartao;

    //Objetos lidos na tela de pegar chave
    private Usuario usuario;
    private Ambiente ambiente;

    public SerialCommunication() {
        setStatus("Carregando portas serial!");
    }

    private void encontrarPorta() {
        carregarPortas = CommPortIdentifier.getPortIdentifiers();
        while (carregarPortas.hasMoreElements()) {
            CommPortIdentifier commPortIdentifier = (CommPortIdentifier) carregarPortas.nextElement();
            testarConexao(commPortIdentifier);
            if (conexao) {
                break;
            }
        }
        if (!conexao) {
            encontrarPorta();
        }
    }

    public void testarConexao(CommPortIdentifier commPortIdentifier) {
        try {
            SerialConnection serialConnection = new SerialConnection();
            serialPort = serialConnection.abrirPorta(commPortIdentifier);
            if (serialPort != null) {
                setStatus(MSG_DESCONECTADO);
                setDebug("Conectando a porta: " + serialPort.getName().replace("/", "").replace(".", ""));
                ativarLeitura();
                enviarMensagem(SerialConstants.ALIVE);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (!conexao) {
                    enviarMensagem(SerialConstants.RESET);
                }
            }
        } catch (PortInUseException ex) {
            setStatus("Porta encontrada porém está em uso, verifique se existe outro aplicativo conectado no aparelho!");
        } catch (UnsupportedCommOperationException ex) {
            setStatus("Falha ao tentar estabelecer conexão!");
        }

    }

    public void ativarLeitura() {
        if (serialPort != null) {
            try {
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
                inputStream = serialPort.getInputStream();
                outputStream = serialPort.getOutputStream();
            } catch (TooManyListenersException | IOException ex) {
                Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reConectar() {
        System.out.println("Reconectando");
        testarConexao(new SerialConnection().getPortaFavorita());

    }

    public void enviarMensagem(SerialConstants serialConstants) {
        if (outputStream != null) {
            try {
                setLastInfo(new Date());
                outputStream.write(serialConstants.toString().getBytes());
                switch (serialConstants) {
                    case RESET:
                        fecharPorta();
                        break;
                    case REQUISITAR_CARTAO:
                        cadastro = true;
                        break;
                    case REQUISITAR_CHAVE:
                        cadastro = true;
                        break;
                    case CONEXAO_ESTABELECIDA:
                        conexao = true;
                        setStatus(MSG_AGUARDANDO);
                        setDebug("Conectado ao Key's Guardian! Porta: " + serialPort.getName().replace("/", "").replace(".", ""));
                        new SerialConnection().setPortaFavorita(serialPort.getName().replace("/", "").replace(".", ""));
                        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                            if (isConexao()) {
                                enviarMensagem(SerialConstants.RESET);
                            }
                        }, "Resetar equipamento"));
                }
            } catch (IOException ex) {
                System.out.println("Falha no envio da mensagem");
                Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void serialEvent(SerialPortEvent spe) {
        try {
            StringBuilder stringBuffer = new StringBuilder();
            int entradaDeDados = 0;
            switch (spe.getEventType()) {
                case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                    break;
                case SerialPortEvent.DATA_AVAILABLE:
                    while (entradaDeDados != -1) {
                        entradaDeDados = inputStream.read();
                        if (entradaDeDados == -1) {
                            break;
                        }
                        stringBuffer.append((char) entradaDeDados);
                    }
                    mensagem = new String(stringBuffer);
                    leitura(mensagem);
                    break;

            }
        } catch (IOException ex) {
            try {
                inputStream.close();
                outputStream.close();
                serialPort.close();
                new SerialConnection().fecharPorta(serialPort);
                conexao = false;
                serialPort = null;
                setStatus(MSG_DESCONECTADO);
                encontrarPorta();
            } catch (IOException ex1) {

            }
        }
    }

    @Override
    public void run() {
        if (new SerialConnection().getPortaFavorita() != null) {
            testarConexao(new SerialConnection().getPortaFavorita());
        }
        if (!conexao) {
            encontrarPorta();
        }
    }

    private void leitura(String mensagem) {
        if (mensagem != null) {
            setLastInfo(new Date());
            if (mensagem.equals(SerialConstants.TESTE_CONECTIVIDADE.toString())) {
                enviarMensagem(SerialConstants.CONEXAO_ESTABELECIDA);
            }
            //Leitura do Cartão
            if (mensagem.matches("^(01:).*")) {
                lerCartao(mensagem);
            }
            //Leitura da Chave
            if (mensagem.matches("^(02:).*")) {
                lerChave(mensagem);
            }
        }
    }

    //Metodos de leitura
    private void lerChave(String mensagem) {
        if (!mensagem.matches("^(02" + SerialConstants.SEPARADOR.toString() + ")(\\w{4})$")) {
            enviarMensagem(SerialConstants.DADO_INCOMPLETO);
        } else {
            String[] partes = mensagem.split(SerialConstants.SEPARADOR.toString());
            if (partes.length > 1) {
                if (entrega) {
                    System.out.println("entrega de chave");
                } else if (cadastro) {
                    setLeitura(partes[1]);
                    cadastro = false;
                } else {
                    if (leuCartao.isAlive()) {
                        leuCartao.interrupt();
                    }
                    ambiente = new AmbienteDAO().pegarPorChave(partes[1]);
                    EmprestaChave empresaChave = new EmprestaChave();
                    empresaChave.setDia(new EmprestaChaveDAO().dataAtual());
                    empresaChave.setHorario(new EmprestaChaveDAO().dataAtual());
                    empresaChave.setUsuario(usuario);
                    if (ambiente == null) {
                        setStatus("Chave não corresponde a nenhum ambiente!");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setStatus(MSG_AGUARDANDO);
                        enviarMensagem(SerialConstants.FALSO);
                    } else {
                        empresaChave.setAmbiente(ambiente);
                        empresaChave.setReserva(partes[1].equals(ambiente.getChaveReserva()));
                        empresaChave.setTransferencia(EmprestaChave.Transferencia.EMPRESTIMO);
                        //Validar usuario take key
                        empresaChave.setValido(UsuarioChave.validar(empresaChave));
                        new EmprestaChaveDAO().cadastrar(empresaChave);
                        if (empresaChave.isValido()) {
                            setStatus("Armazenando no banco que " + usuario.getNome() + " pegou a chave do ambiente " + ambiente.getNome());
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            enviarMensagem(SerialConstants.VERDADEIRO);
                            setStatus(MSG_AGUARDANDO);
                        } else {
                            setStatus("Você não está permitido a pega essa chave, passe uma chave liberada...");
                            enviarMensagem(SerialConstants.INVALIDO);
                            leuCartao = new Thread(() -> {
                                try {
                                    Thread.sleep(60000);
                                    enviarMensagem(SerialConstants.CANCELAR);
                                    setStatus(MSG_AGUARDANDO);
                                } catch (InterruptedException ex) {
                                    System.out.println("Interropendo cancelar a leitura da chave invalida");
                                }
                            }, "Tempo acabo sem ler chave");
                            leuCartao.start();
                        }

                    }
                }
            }
        }
    }

    private void lerCartao(String mensagem) {
        if (!mensagem.matches("^(01" + SerialConstants.SEPARADOR.toString() + ")(\\w{4})$")) {
            enviarMensagem(SerialConstants.DADO_INCOMPLETO);
        } else {
            String[] partes = mensagem.split(SerialConstants.SEPARADOR.toString());
            if (partes.length > 1) {
                if (cadastro) {
                    setLeitura(partes[1]);
                    cadastro = false;
                } else {
                    usuario = new UsuarioDAO().pegarPorCartao(partes[1]);
                    if (usuario == null) {
                        setStatus("Usuário inválido, Por favor passe o cartão de usuário válido!");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        setStatus(MSG_AGUARDANDO);
                        enviarMensagem(SerialConstants.FALSO);
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        String comprimento;
                        int hora = calendar.get(Calendar.HOUR_OF_DAY);
                        if (hora >= 1 && hora < 12) {
                            comprimento = "Bom dia";
                        } else if (hora < 18) {
                            comprimento = "Boa tarde";
                        } else {
                            comprimento = "Boa noite";
                        }
                        setStatus(comprimento + " " + usuario.getNome() + "! Por favor passe a chave que deseja pegar ...");
                        leuCartao = new Thread(() -> {
                            try {
                                Thread.sleep(60000);
                                enviarMensagem(SerialConstants.CANCELAR);
                                setStatus("Processo cancelado por não ler a chave!");
                                Thread.sleep(3000);
                                setStatus(MSG_AGUARDANDO);
                            } catch (InterruptedException ex) {
                                System.out.println("Interropendo Thread de Cancelamento da leitura do cartao");
                            }
                        }, "Cancelar por chave incorreta");
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(SerialCommunication.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        leuCartao.start();
                        enviarMensagem(SerialConstants.VERDADEIRO);
                    }
                }
            }
        }
    }

    //Funções do hardware
    public void fecharPorta() {
        conexao = false;
        new SerialConnection().fecharPorta(serialPort);
    }

    //
    //
    //Getters e setters
    //
    //
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastInfo() {
        return lastInfo;
    }

    public void setLastInfo(Date lastInfo) {
        this.lastInfo = lastInfo;
    }

    public boolean isConexao() {
        return conexao;
    }

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public String getLeitura() {
        return leitura;
    }

    public void setLeitura(String leitura) {
        this.leitura = leitura;
    }

}
