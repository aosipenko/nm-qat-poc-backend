package com.refinitiv.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "GOODS")
@Table(name = "GOODS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Goods {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOODS_NAME")
    private String name;

    @Column(name = "REGION_AVAILABILITY")
    private String regions;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "IMG_URL")
    private String url;
}
