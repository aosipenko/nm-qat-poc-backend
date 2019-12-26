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

@Entity(name = "CONTRACTOR")
@Table(name = "CONTRACTOR")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contractors {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CONTRACTOR_NAME")
    private String name;

    @Column(name = "CONTRACTOR_REGIONS")
    private String regions;
}
