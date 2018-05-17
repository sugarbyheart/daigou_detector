package com.github.sugarbyheart.daigou.detector.messaging;

import com.github.sugarbyheart.daigou.detector.LogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ConsumerConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${exchange.name}")
    private String exchangeName;

    @Value("${item.discription.queue}")
    private String queueName;

    @Value("${item.discription.routingKey}")
    private String routingKey;

    @Bean
    public Exchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }

    @Bean
    public LogicService logicService() {
        return new LogicService();
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange topicExchange) {
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(routingKey);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                    MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService, "receive");
    }

    @Bean
    public ConsumerService consumerService() {
        return new ConsumerService();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("SUBSCRIBING TO EVENTS MATCHING KEY '{}' FROM QUEUE '{}'!", routingKey, queueName);
    }

}
