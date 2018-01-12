package com.ibiscus.myster;

import com.ibiscus.myster.service.survey.data.DatastoreService;
import com.ibiscus.myster.service.survey.data.GoogleDatastoreService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MysterApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MysterApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(MysterApplication.class, args);
    }

    @Bean
    public DatastoreService getDatastoreService() {
        return new GoogleDatastoreService();
    }
}
