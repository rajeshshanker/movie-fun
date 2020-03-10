package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RabbitMessageController {

    private final RabbitTemplate rabbitTemplate;
    private final String queue;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public RabbitMessageController (RabbitTemplate rabbitTemplate , @Value("${rabbitmq.queue}") String queue){
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @PostMapping("/rabbit")
    public Map<String,String> publishMessage (){
        logger.info("trying to publish an amqp message");
        logger.info("Message : This text message will trigger the consumer");
        rabbitTemplate.convertAndSend(queue, "This text message will trigger the consumer");
        Map<String, String> response = new HashMap<>();
        logger.info("Sending stubbed response : This text message will trigger the consumer");
        response.put("response", "This is an unrelated JSON response");
        return response;

    }

}
