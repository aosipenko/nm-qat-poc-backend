package com.refinitiv.rest.service.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class DeliveryAgent {
    private String name;

    private List<WorldRegion> regions;
}
