/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI01
 */
public class SerialConnection {

    private File file = new File("etc/serialPort.conf");
    //Configuração da porta e do equipamento
    private static final int BAUDRATE = 9600;
    private static final int TIME_OUT = 2000;

    public SerialPort abrirPorta(CommPortIdentifier cpi) throws PortInUseException, UnsupportedCommOperationException {
        CommPort portaOriginal = cpi.open(getClass().getName(), TIME_OUT);
        if (portaOriginal instanceof SerialPort) {
            SerialPort serialPort = (SerialPort) portaOriginal;
            serialPort.setSerialPortParams(BAUDRATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);
            return serialPort;
        }
        return null;
    }

    public boolean fecharPorta(SerialPort serialPort) {
        try {
            if (serialPort != null) {
                serialPort.removeEventListener();
                serialPort.getInputStream().close();
                serialPort.getOutputStream().close();
                serialPort.close();
                return true;
            }
        } catch (IOException ex) {
            System.out.println("Pode ter ocorrido um erro ao fechar o inputStream(Leitura do serial)");
            System.out.println("Pode ter ocorrido um erro ao fechar a porta(" + serialPort.getName() + ")");
        }
        return false;
    }

    public void setPortaFavorita(String serialPort) {
        try {
            file.createNewFile();
            List<String> lista = new ArrayList<>();
            lista.add(serialPort);
            Files.write(Paths.get(file.getAbsolutePath()), lista, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            Logger.getLogger(SerialConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  CommPortIdentifier getPortaFavorita() {
        if (file.exists()) {
            try {
                List<String> porta = Files.readAllLines(Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8);
                if (porta.isEmpty()) {
                    return null;
                }
                try {
                    return CommPortIdentifier.getPortIdentifier(porta.get(0));
                } catch (NoSuchPortException ex) {
                    return null;
                }
            } catch (IOException ex) {
                return null;
            }
        } else {
            return null;
        }
    }
}
