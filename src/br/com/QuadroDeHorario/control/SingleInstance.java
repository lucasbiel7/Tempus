/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI01
 */
public class SingleInstance {

    private static ServerSocket socket;

    public SingleInstance() throws IOException {
        socket = new ServerSocket(50596);
        if (!socket.isClosed()) {
            System.out.println("Server open");
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(SingleInstance.class.getName()).log(Level.SEVERE, null, ex);
                }
            }));
        }
    }

}
