package com.refinitiv.db.dao;

import com.refinitiv.db.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientsDAO extends JpaRepository<Clients, Long> {
}
