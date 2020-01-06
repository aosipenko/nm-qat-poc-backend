package com.refinitiv.rest.service.entity;

import lombok.Builder;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.util.List;

@Builder
@Data
public class Order {

    private Client client;
    private DeliveryAgent deliveryAgent;
    private List<Item> goodsList;
}
