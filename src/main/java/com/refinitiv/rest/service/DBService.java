package com.refinitiv.rest.service;

import com.refinitiv.db.Contractors;
import com.refinitiv.rest.service.entity.Client;
import com.refinitiv.rest.service.exception.NoSuchClientException;

import java.util.List;

public interface DBService {

    public Client getClientById(Long id) throws NoSuchClientException;

    public Long createClient(Client client) throws Exception;

    Long updateClient(Long id, Client client) throws Exception;

    Long deleteClient(Long id);

    List<Client> getAllClients();

    public List<Contractors> getRegionContractors(Long regionId);

    public List<Contractors> getContractorsForClient(Long clientId) throws NoSuchClientException;
}
