package com.ciffarf.vehicle_service.messaging;

import com.ciffarf.vehicle_service.config.RabbitMQConfig;
import com.ciffarf.vehicle_service.event.VehicleCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VehicleProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendVehicleCreatedEvent(
            VehicleCreatedEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                "Vehicle Created : "
                        + event.getVehicleNumber()
                        + " Owner : "
                        + event.getOwnerName());

        log.info(
                "Vehicle event published {}",
                event.getVehicleNumber());
    }
}
