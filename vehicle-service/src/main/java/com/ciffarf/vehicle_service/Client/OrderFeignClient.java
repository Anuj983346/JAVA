package com.ciffarf.vehicle_service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderFeignClient {

    @GetMapping("/orders")
    String getOrders();
}
