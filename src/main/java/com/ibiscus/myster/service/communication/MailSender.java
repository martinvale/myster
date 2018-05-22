package com.ibiscus.myster.service.communication;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSender {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailSender.class);

    private final String host;
    private final int port;
    private final String from;
    private final String user;
    private final String password;

    public MailSender(String host, int port, String from, String user, String password) {
        this.host = host;
        this.port = port;
        this.from = from;
        this.user = user;
        this.password = password;
    }

    public void sendMail(String to, String subject, String message) {
        HtmlEmail email = new HtmlEmail();

        email.setHostName(host);
        email.setSmtpPort(port);
        email.setAuthenticator(new DefaultAuthenticator(user, password));
        email.setStartTLSEnabled(true);
        try {
            email.setFrom(from);
            email.addTo(to);
            email.setSubject(subject);
            email.setDebug(true);
            email.setHtmlMsg(message);
            email.send();
        } catch (EmailException e) {
            LOGGER.error("Cannot close a mail to {} with subject {}", to, subject);
            throw new RuntimeException(e);
        }

    }
}
