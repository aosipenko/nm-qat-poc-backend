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

@Entity(name = "CLIENTS")
@Table(name = "CLIENTS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients {

    @Id
    @Column(name = "ID")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    private String fName;

    @Column(name = "LAST_NAME")
    private String lName;

    @Column(name = "ADDRESS_ID")
    private Long addressId;

    @Column(name = "CONTACTS_ID")
    private Long contactId;
}
