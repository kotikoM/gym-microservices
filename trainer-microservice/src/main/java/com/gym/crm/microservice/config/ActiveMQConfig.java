package com.gym.crm.microservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gym.crm.microservice.dto.TrainerWorkloadRequestDto;
import com.gym.crm.microservice.constant.AppConstants;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJms
public class ActiveMQConfig {

    @Value("${factory.brokerURL}")
    private String brokerURL;

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerURL);
        return factory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory());
        jmsTemplate.setMessageConverter(jmsMessageConverter());
        return jmsTemplate;
    }

    @Bean
    public MessageConverter jmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName(AppConstants.TYPE_ID_PROPERTY_NAME);
        converter.setObjectMapper(objectMapper());
        Map<String, Class<?>> typeIdMappings = new HashMap<>();
        typeIdMappings.put(AppConstants.TRAINER_WORKLOAD_REQUEST_DTO, TrainerWorkloadRequestDto.class);
        converter.setTypeIdMappings(typeIdMappings);
        return converter;
    }

    private ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
