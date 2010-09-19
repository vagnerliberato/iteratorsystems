package br.iteratorsystems.cps.test;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe de envio de email
 * @author André
 *
 */
public class EnviarEmail {
	
	private static final String EMAIL = "sistemacps@gmail.com";
	private static final String SENHA = "sistemacps2010";
	private Session session;
	
	/**
	 * Construtor padrão
	 */
	public EnviarEmail() {
		obterPropiedadesSaida();
	}
	
	/**
	 * Obtem as propiedades de saida
	 */
	private void obterPropiedadesSaida() {
		Properties props = new Properties();

		/** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(EMAIL, SENHA);
					}
				});
	}
	
	/**
	 * Envia o email
	 * @param titulo - título da mensagem
	 * @param corpo - corpo da mensagem
	 * @param destinatario - lista de destinatários
	 * @return Se a mensagem foi enviada
	 * @throws Exception - Se ocorrer algum erro durante o envio
	 */
	public boolean enviarEmail(String titulo, String corpo,
			String... destinatario) throws Exception {
		boolean enviado = false;
		enviado = comporMensagem(titulo, corpo, destinatario);
		return enviado;
	}
	
	/**
	 * Compõe a mensagem a ser enviada
	 * @param titulo - título da mensagem
	 * @param corpo - corpo da mensagem
	 * @param destinatario - lista de destinatários
	 * @return Se a mensagem foi enviada
	 * @throws Exception - Se ocorrer algum erro durante o envio
	 */
	private boolean comporMensagem(String titulo, String corpo,
			String... destinatario) throws Exception {
		boolean mensagemEnviada = false;
		/** Ativa Debug para sessão */
		session.setDebug(true);
		try {

			Message message = new MimeMessage(session);
			// Remetente
			message.setFrom(new InternetAddress(EMAIL));
			// Destinatário(s)
			StringBuilder builder = new StringBuilder();
			for (String dest : destinatario) {
				builder.append(dest);
				builder.append(",");
			}

			Address[] toUser = InternetAddress.parse(builder.toString());
			message.setRecipients(Message.RecipientType.TO, toUser);
			// Assunto
			message.setSubject(titulo);
			// Corpo
			message.setText(corpo);
			/** Método para enviar a mensagem criada */
			Transport.send(message);
			mensagemEnviada = true;
		} catch (MessagingException e) {
			throw new Exception(e);
		}
		return mensagemEnviada;
	}
}