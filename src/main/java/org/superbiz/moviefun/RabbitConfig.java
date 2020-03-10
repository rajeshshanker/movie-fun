package org.superbiz.moviefun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.superbiz.moviefun.albums.AlbumsUpdateMessageConsumer;

@Configuration
public class RabbitConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${rabbitmq.queue}")
    String rabbitMqQueue;

    @Bean
    public IntegrationFlow amqpInbound(ConnectionFactory connectionFactory, AlbumsUpdateMessageConsumer consumer) {
        logger.info("Rabbit Config configurations :" +rabbitMqQueue);

        return IntegrationFlows
                .from(Amqp.inboundAdapter(connectionFactory, rabbitMqQueue))
                .handle(consumer::consume)
                .get();
    }
}