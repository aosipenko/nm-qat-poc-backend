package com.refinitiv.db.dao;

import com.refinitiv.db.Addresses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface AddressesDAO extends JpaRepository<Addresses, Long> {
}
