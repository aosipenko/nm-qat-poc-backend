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

@Entity(name = "CONTACTS")
@Table(name = "CONTACTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contacts {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;
}
