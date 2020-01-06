package com.refinitiv.db.dao;

import com.refinitiv.db.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface OrdersDAO extends JpaRepository<Orders, Long> {
}
