package com.github.sugarbyheart.daigou.detector.messaging;

import com.github.sugarbyheart.daigou.common.DTO.ItemDetectResult;
import com.github.sugarbyheart.daigou.common.DTO.ItemDiscription;
import com.github.sugarbyheart.daigou.detector.LogicService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ConsumerService {

    @Autowired
    private LogicService logicService;

    @Autowired
    private ProducerService producerService;

    @RabbitListener
    public void receive(ItemDiscription itemDiscription) {
        log.info("Received itemDescription: {}", itemDiscription);
        if (logicService.canBuy(itemDiscription)) {
            ItemDetectResult itemDetectResult = ItemDetectResult.builder()
                    .itemDiscription(itemDiscription)
                    .hasItem(true)
                    .build();
            producerService.sendResult(itemDetectResult);
        }
    }

}