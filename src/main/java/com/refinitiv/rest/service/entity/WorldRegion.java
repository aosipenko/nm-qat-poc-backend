package com.refinitiv.rest.service.entity;

import java.util.Arrays;

public enum WorldRegion {
    ASIA(1), EUROPE(2), NORTH_AMERICA(3), SOUTH_AMERICA(4), AFRICA(5), OCEANIA(6), UNKNOWN(0);

    private int id;

    WorldRegion(int id) {
        this.id = id;
    }

    public WorldRegion getById(int id) {
        return Arrays.stream(WorldRegion.values()).filter(wr -> wr.getId() == id).findFirst().orElse(UNKNOWN);
    }

    public int getId() {
        return id;
    }
}
