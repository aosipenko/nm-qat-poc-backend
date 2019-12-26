package com.refinitiv.db.dao;

import com.refinitiv.db.Contractors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContractorsDAO extends JpaRepository<Contractors, Long> {
}
