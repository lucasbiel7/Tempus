/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.QuadroDeHorario.control;

import br.com.QuadroDeHorario.control.dao.TokenCodeDAO;
import br.com.QuadroDeHorario.model.entity.TokenCode;
import br.com.QuadroDeHorario.model.entity.Usuario;
import br.com.QuadroDeHorario.model.util.FxMananger;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.hibernate.exception.ConstraintViolationException;

/**
 *
 * @author OCTI01
 */
public class Mail {

    private final Session session;
    private final String user = "bquestao";
    private final String senha = "b4nc0!elw";
    private final String domain = "gmail.com";
    private final int smtpPort = 465;

    public Mail() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.user", user + "@" + domain);
        properties.put("mail.smtp.host", "smtp." + domain);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.debug", "true");
        properties.put("mail.smtp.port", String.valueOf(smtpPort));
        properties.put("mail.smtp.socketFactory.port", String.valueOf(smtpPort));
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false");
        session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user + "@" + domain, senha);
            }
        });
        //Lembrar de comentar depois
        session.setDebug(false);
    }

    public void sendEmail(Usuario usuario) throws ConstraintViolationException, MessagingException, UnsupportedEncodingException {

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress("bquestao@gmail.com", FxMananger.NOME_PROGRAMA + " - CETEL"));
        email.addRecipient(Message.RecipientType.TO, new InternetAddress(usuario.getEmail()));
        email.setSubject(FxMananger.NOME_PROGRAMA + " - Recuperar Senha");
        String codigo = new Security().codigoDeSeguranca();
        email.setText("Sr.(a) " + usuario.getNome() + "\n "
                + "Recebemos a solicitação do código de segurança para alterar sua senha,\n"
                + "caso você não tenha solicitado ingnore este e-mail e troque sua senha pois pode ter sido uma tentativa de roubo.\n"
                + "Código de Segurança:\n"
                + "" + codigo);
        Transport transporte = session.getTransport("smtp");
        transporte.connect("smtp." + domain, smtpPort, user, senha);
        transporte.sendMessage(email, email.getRecipients(Message.RecipientType.TO));
        transporte.close();
        TokenCode tokenCode = new TokenCode();
        tokenCode.setUsuario(usuario);
        tokenCode.setLastChange(new TokenCodeDAO().dataAtual());
        tokenCode.setToken(codigo);
        new TokenCodeDAO().cadastrar(tokenCode);

    }

    public void sendEmail(String email, String nome, String title, String mensagem, File... dados) {
        try {
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress("bquestao@gmail.com", FxMananger.NOME_PROGRAMA + " - CETEL"));
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(email, nome));
            mimeMessage.setSubject(FxMananger.NOME_PROGRAMA + " - " + title);
            //Parte dos dados
            Multipart multipart = new MimeMultipart();
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(mensagem);
            multipart.addBodyPart(textPart);
            for (File dado : dados) {
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                FileDataSource fileDataSource = new FileDataSource(dado);
                mimeBodyPart.setDataHandler(new DataHandler(fileDataSource));
                mimeBodyPart.setFileName(fileDataSource.getName());
                //Onde é fica todos arquivos
                multipart.addBodyPart(mimeBodyPart);
            }
            //Adicionar o conteúdo
            mimeMessage.setContent(multipart);
            Transport transporte = session.getTransport("smtp");
            transporte.connect("smtp." + domain, smtpPort, user, senha);
            transporte.sendMessage(mimeMessage, mimeMessage.getRecipients(Message.RecipientType.TO));
            transporte.close();
        } catch (MessagingException | UnsupportedEncodingException ex) {
            Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
