package com.example.insuranceregistrationsystem;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class InsuranceRegistrationSystemApplication {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    ConnectionFactory connectionFactory() {
//        ConnectionFactory connectionFactory = new PooledConnectionFactory("tcp://localhost:61616");
//        return connectionFactory;
//    }
//
//    // custom JmsTemplate
//    @Bean
//    public JmsTemplate jmsTemplate() {
//        JmsTemplate template = new JmsTemplate(connectionFactory());
//        template.setMessageConverter(MessageConverters.defaultMessageConverter());
//        return template;
//    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(InsuranceRegistrationSystemApplication.class, args);

    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//    }
}
