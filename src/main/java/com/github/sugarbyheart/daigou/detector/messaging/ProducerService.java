package com.github.sugarbyheart.daigou.detector.messaging;

import com.github.sugarbyheart.daigou.common.DTO.ItemDetectResult;
import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.common.Enum.ItemBrandEnum;
import com.github.sugarbyheart.daigou.common.Enum.LinkTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange topicExchange;

    @Value("${item.discription.routingKey}")
    private String routingKey;

    @Autowired
    public ProducerService(RabbitTemplate rabbitTemplate, TopicExchange topicExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.topicExchange = topicExchange;
    }

    public void sendResult(ItemDetectResult itemDetectResult) {
        if (itemDetectResult.isHasItem()) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), routingKey, itemDetectResult);
        }
    }

}
