package br.com.floricultura.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class EnviarEmail {
	private static final String HOST = "smtp.gmail.com";
    private static final int PORT = 587;
    private static final boolean SSL_FLAG = true;
    
    public void enviarEmail() {
    	String emailAcesso = "";
    	String senhaAcesso = "";
    	
    	String emailRemetente= "";
    	String emailDestinatario= "";
    	String assunto = "Test Mail";
    	String mensagem = "Teste email";
    	
    	try {
			Email email = new SimpleEmail();
			email.setHostName(HOST);
            email.setSmtpPort(PORT);
            email.setAuthenticator(new DefaultAuthenticator(emailAcesso, senhaAcesso));
            //email.setTLS(true);
            email.setSSLOnConnect(SSL_FLAG);
            email.setFrom(emailRemetente);
            email.setSubject(assunto);
            email.setMsg(mensagem);
            email.addTo(emailDestinatario);
            email.send();
            System.out.println("Email enviado!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
