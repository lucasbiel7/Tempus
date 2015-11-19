/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.entity.Usuario;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author OCTI01
 */
public class Security {

    public String codigoDeSeguranca() {
        String cod = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            cod += String.valueOf(random.nextInt(9));
        }
        cod += (char) (random.nextInt(25) + 65);
        return cod;
    }

    public static String criptografar(String dado) {
        try {
            if (dado != null) {
                MessageDigest criptografia = MessageDigest.getInstance("SHA1");
                StringBuilder senhaCriptografada = new StringBuilder();
                for (byte digito : criptografia.digest(dado.getBytes(Charset.forName("UTF-8")))) {
                    senhaCriptografada.append(String.format("%02X", 0xFF & digito));
                }
                return senhaCriptografada.toString();
            } else {
                return dado;
            }
        } catch (NoSuchAlgorithmException | NullPointerException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String criptar(String senha) {
        try {
            char[] newsenha = senha.toCharArray();
            int[] cripNumber = new int[newsenha.length];
            String senhafinal = "";
            int i = 0;
            for (char a : newsenha) {
                cripNumber[i] = (int) a;
                i++;
            }
            for (int dado : cripNumber) {
                senhafinal += (dado + "@");
            }
            return senhafinal;
        } catch (Exception e) {
            return null;
        }
    }

    public static String decript(String senha) {
        try {
            String[] caracteres = senha.split("@");
            int[] cripNumber = new int[caracteres.length];
            char[] senhaFinal = new char[caracteres.length];
            for (int i = 0; i < caracteres.length; i++) {
                cripNumber[i] = Integer.parseInt(caracteres[i]);
                senhaFinal[i] = (char) cripNumber[i];
            }
            return new String(senhaFinal);
        } catch (Exception e) {
            return null;
        }
    }
}
