package com.refinitiv.rest.service.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Item {
    private Long id;
    private String name;
    private String url;
    private Double price;
    private int amount;
}
