/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.entity.Usuario;
import br.com.QuadroDeHorario.util.FxMananger;
import br.com.QuadroDeHorario.util.Mensagem;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author OCTI01
 */
public class Mail {

    private final Session session;

    public Mail() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", "bquestao@gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("bquestao@gmail.com", "b4nc0!elw");
            }
        });
        session.setDebug(true);
    }

    public void sendEmail(Usuario usuario) {
        new Thread(() -> {
            try {
                MimeMessage email = new MimeMessage(session);
                email.setFrom(new InternetAddress("bquestao@gmail.com", FxMananger.NOME_PROGRAMA + " - CETEL"));
                email.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
                email.setSubject(FxMananger.NOME_PROGRAMA + " - Recuperar Senha");
                email.setText("Sr.(a) " + usuario.getNome() + "\n "
                        + "Recebemos a solicitação do código de segurança para alterar sua senha,\n"
                        + "caso você não tenha solicitado ingnore este e-mail e troque sua senha pois pode ter sido uma tentativa de roubo.\n"
                        + "Código de Segurança:\n"
                        + "" + new Security().codigoDeSeguranca(usuario));
                Transport transporte = session.getTransport("smtp");
                transporte.connect("smtp.gmail.com", 465, "bquestao", "b4nc0!elw");
                transporte.sendMessage(email, email.getRecipients(Message.RecipientType.TO));
                transporte.close();
            } catch (MessagingException | UnsupportedEncodingException ex) {
                Mensagem.showError("Falha de conexão", "Não foi possível enviar o e-mail verifique a conexão da sua internet.");
            }
        }).start();
    }
}
