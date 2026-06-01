//package com.ciffarf.vehicle_service.messaging;
//
//
//import com.ciffarf.vehicle_service.config.RabbitMQConfig;
//import com.ciffarf.vehicle_service.event.VehicleCreatedEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class VehicleConsumer {
//    @RabbitListener(
//            queues = RabbitMQConfig.QUEUE)
//    public void consumeVehicleCreatedEvent(
//            String message){
//
//        log.info(
//                "Received Message {}",
//                message);
//    }
//
//}
