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

@Entity(name = "OREDER")
@Table(name = "OREDER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_ID ")
    private Long orderId;

    @Column(name = "CLIENT_ID")
    private Long name;

    @Column(name = "GOODS_ID")
    private Long regions;

    @Column(name = "AMOUNT")
    private Long amount;
}
