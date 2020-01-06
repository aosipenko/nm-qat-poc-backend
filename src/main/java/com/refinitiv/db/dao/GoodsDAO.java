package com.refinitiv.db.dao;

import com.refinitiv.db.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface GoodsDAO extends JpaRepository<Goods, Long> {
}
