/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.model.util;

import br.com.QuadroDeHorario.control.EncriptacaoAES;
import br.com.QuadroDeHorario.control.dao.AtualizacaoDAO;
import br.com.QuadroDeHorario.control.dao.SistemaDAO;
import br.com.QuadroDeHorario.model.entity.Atualizacao;
import br.com.QuadroDeHorario.model.entity.Sistema;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI01
 */
public class ParametrosBanco {

    public static String NOME_BANCO;
    public static String IP;
    public static String USUARIO;
    public static String SENHA;
    public static final double VERSAO = 3.732;
    public static final String REMOTO = "banco";
    public static final String LOCAL = "local";

    static {
        atribuirPropriedades(REMOTO);
    }

    public static void atribuirPropriedades(String propriedades) {
        File file = new File("etc/");
        file.mkdir();
        try {
            File arquivoConf = new File("etc/" + propriedades + ".txt");
            List<String> parametros;
            if (!arquivoConf.isFile()) {
                parametros = new ArrayList<>();
                if (propriedades.equals(LOCAL)) {
                    //Cluster local
                    parametros.add(new EncriptacaoAES().encriptar("127.0.0.1"));
                    parametros.add(new EncriptacaoAES().encriptar("root"));
                    parametros.add(new EncriptacaoAES().encriptar("OC2015"));
                    parametros.add(new EncriptacaoAES().encriptar("sisCetel"));
                } else if (propriedades.equals(REMOTO)) {
                    parametros.add(new EncriptacaoAES().encriptar("10.31.1.5"));
                    parametros.add(new EncriptacaoAES().encriptar("qHorario"));
                    parametros.add(new EncriptacaoAES().encriptar("qu4dr0!elw"));
                    parametros.add(new EncriptacaoAES().encriptar("sisCetel"));
                }
                //Administrador database
//                parametros.add(new EncriptacaoAES().encriptar("root"));
//                parametros.add(new EncriptacaoAES().encriptar("FIEMG2015"));
                Files.write(Paths.get(arquivoConf.getAbsolutePath()), parametros, StandardCharsets.UTF_8);
            }
            parametros = Files.readAllLines(Paths.get(arquivoConf.getAbsolutePath()), StandardCharsets.UTF_8);
            if (!parametros.isEmpty()) {
                IP = new EncriptacaoAES().desencriptar(parametros.get(0));
                USUARIO = new EncriptacaoAES().desencriptar(parametros.get(1));
                SENHA = new EncriptacaoAES().desencriptar(parametros.get(2));
                if (parametros.size() < 4) {
                    NOME_BANCO = "sisCetel";
                } else {
                    NOME_BANCO = new EncriptacaoAES().desencriptar(parametros.get(3));
                }
            }
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public boolean atualizacao() {
        Sistema sistema = new SistemaDAO().pegarPorNome(FxMananger.NOME_PROGRAMA);
        if (sistema == null) {
            try {
                sistema = new Sistema();
                sistema.setNome(FxMananger.NOME_PROGRAMA);
                sistema.setNomeJar("QuadroDeHorarioFX.jar");
                InputStream inputStream = getClass().getResourceAsStream("/br/com/QuadroDeHorario/view/image/icone.png");
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[16000];
                while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                sistema.setFoto(buffer.toByteArray());
                new SistemaDAO().cadastrar(sistema);
            } catch (IOException ex) {
                Logger.getLogger(ParametrosBanco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (sistema != null) {
            Atualizacao atualizacao = new AtualizacaoDAO().pegarPorSistema(sistema);
            if (atualizacao != null) {
                if (atualizacao.getVersion() > VERSAO) {
                    if (atualizacao.getJar() != null) {
                        try {
                            Files.write(Paths.get(sistema.getNomeJar()), atualizacao.getJar());
                            return true;
                        } catch (IOException ex) {
                            System.err.println(ex.getMessage());
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String getIP() {
        return IP;
    }

    public static void setIP(String IP) {
        ParametrosBanco.IP = IP;
    }

    public static String getSENHA() {
        return SENHA;
    }

    public static void setSENHA(String SENHA) {
        ParametrosBanco.SENHA = SENHA;
    }

    public static String getUSUARIO() {
        return USUARIO;
    }

    public static void setUSUARIO(String USUARIO) {
        ParametrosBanco.USUARIO = USUARIO;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + ParametrosBanco.getIP() + "/" + ParametrosBanco.NOME_BANCO, ParametrosBanco.getUSUARIO(), ParametrosBanco.getSENHA());
        } catch (ClassNotFoundException | SQLException e) {
            return null;
        }
        return connection;
    }

    public static String getNOME_BANCO() {
        return NOME_BANCO;
    }

    public static void setNOME_BANCO(String NOME_BANCO) {
        ParametrosBanco.NOME_BANCO = NOME_BANCO;
    }

    public static Properties carregarPropriedades() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.username", ParametrosBanco.USUARIO == null ? "root" : ParametrosBanco.USUARIO);
        properties.setProperty("hibernate.connection.password", ParametrosBanco.SENHA == null ? "root" : ParametrosBanco.SENHA);
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://" + ParametrosBanco.IP + "/" + ParametrosBanco.NOME_BANCO);
        return properties;
    }
}
