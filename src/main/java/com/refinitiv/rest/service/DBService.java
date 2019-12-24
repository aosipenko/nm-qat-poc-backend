package com.refinitiv.rest.service;

import com.refinitiv.rest.service.entity.Client;

public interface DBService {

    public Client getClientById(Long id);

    public Long createClient(Client client) throws Exception;

    Long updateClient(Long id, Client client) throws Exception;

    Long deleteClient(Long id);
}
