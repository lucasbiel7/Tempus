/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.TokenCodeDAO;
import br.com.QuadroDeHorario.model.entity.TokenCode;
import br.com.QuadroDeHorario.model.entity.Usuario;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author OCTI01
 */
public class Seguranca {

    public String codigoDeSeguranca(Usuario usuario) throws ConstraintViolationException {
        String cod = "";
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            cod += String.valueOf(random.nextInt(9));
        }
        cod += (char) (random.nextInt(25) + 65);
        TokenCode tokenCode = new TokenCode();
        tokenCode.setLastChange(new Date());
        tokenCode.setUsuario(usuario);
        tokenCode.setToken(cod);
        new TokenCodeDAO().cadastrar(tokenCode);
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
}
