package com.refinitiv.db.dao;

import com.refinitiv.db.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ContactsDAO extends JpaRepository<Contacts, Long> {
}
