package com.ibiscus.myster.configuration;

import com.ibiscus.myster.service.communication.MailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    private final String host = "smtp.office365.com";
    private final int port = 587;
    private final String from = "pagosshopnchek@shopnchek.com.ar";
    private final String user = "pagosshopnchek@shopnchek.com.ar";
    private final String password = "DOejj339";

    @Bean
    MailSender getMailSender() {
        return new MailSender(host, port, from, user, password);
    }

}
