package com.refinitiv.rest.service;

import com.refinitiv.rest.service.entity.Client;

public interface DBService {

    public Client getClientById(Long id);

    public Long createClient(Client client);
}
