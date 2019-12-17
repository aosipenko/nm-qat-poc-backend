package com.refinitiv.rest.service.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
//@NoArgsConstructor
public class Client implements Serializable {
    private Long id;

    private Long region;

    private String country;

    private String city;

    private String street;

    private String fName;

    private String lName;

    private String email;

    private String phone;
}
