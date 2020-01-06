package com.refinitiv.rest.service.impl;

import com.refinitiv.db.Addresses;
import com.refinitiv.db.Clients;
import com.refinitiv.db.Contacts;
import com.refinitiv.db.Contractors;
import com.refinitiv.db.dao.AddressesDAO;
import com.refinitiv.db.dao.ClientsDAO;
import com.refinitiv.db.dao.ContactsDAO;
import com.refinitiv.db.dao.ContractorsDAO;
import com.refinitiv.rest.service.DBService;
import com.refinitiv.rest.service.entity.Client;
import com.refinitiv.rest.service.exception.BadClientDataException;
import com.refinitiv.rest.service.exception.NoSuchClientException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DBServiceImpl extends SpringBeanAutowiringSupport implements DBService {

    private final static String MAIL_REGEXP =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    private final static String PHONE_REGEXP = "[0-9]{12}";

    private final static String COUNTRY_REGEXP = "[a-zA-Z]{3}";

    private final AddressesDAO addressesDAO;

    private final ClientsDAO clientsDAO;

    private final ContactsDAO contactsDAO;

    private final ContractorsDAO contractorsDAO;

    public DBServiceImpl(AddressesDAO dao, ClientsDAO clientsDAO, ContactsDAO contactsDAO,
                         ContractorsDAO contractorsDAO) {
        addressesDAO = dao;
        this.clientsDAO = clientsDAO;
        this.contactsDAO = contactsDAO;
        this.contractorsDAO = contractorsDAO;
    }

    @Override
    public Client getClientById(Long id) throws NoSuchClientException {
        Clients client = clientsDAO.findById(id).orElse(null);
        if (client == null) {
            throw new NoSuchClientException();
        }
        Addresses addresses = addressesDAO.findById(client.getAddressId()).orElse(new Addresses(0L, 0L, "", "", ""));
        Contacts contacts = contactsDAO.findById(client.getContactId()).orElse(new Contacts(0L, "", ""));

        return assembleClient(client, addresses, contacts);
    }

    @Override
    public Long createClient(Client input) throws Exception {
        if (!validateData(input)) {
            throw new BadClientDataException();
        }
        Clients client = new Clients();
        client.setFName(input.getFName());
        client.setLName(input.getLName());

        Addresses addresses = new Addresses();
        addresses.setCity(input.getCity());
        addresses.setCountry(input.getCountry());
        addresses.setStreet(input.getStreet());
        addresses.setRegion(input.getRegion());

        Contacts contacts = new Contacts();
        contacts.setPhone(input.getPhone());
        contacts.setEmail(input.getEmail());

        addressesDAO.saveAndFlush(addresses);
        contactsDAO.saveAndFlush(contacts);

        client.setAddressId(addresses.getId());
        client.setContactId(contacts.getId());

        clientsDAO.saveAndFlush(client);

        return client.getId();
    }

    @Override
    public Long updateClient(Long id, Client client) throws Exception {
        if (!validateData(client)) {
            throw new BadClientDataException();
        }
        if (clientsDAO.findById(id).isPresent()) {
            try {
                Clients dbEntry = clientsDAO.findById(id).get();
                Contacts contacts = contactsDAO.findById(dbEntry.getContactId()).get();
                Addresses addresses = addressesDAO.findById(dbEntry.getAddressId()).get();

                dbEntry.setLName(client.getLName() == null ? dbEntry.getLName() : client.getLName());
                dbEntry.setFName(client.getFName() == null ? dbEntry.getFName() : client.getFName());
                contacts.setEmail(client.getEmail() == null ? contacts.getEmail() : client.getEmail());
                contacts.setPhone(client.getPhone() == null ? contacts.getPhone() : client.getPhone());
                addresses.setStreet(client.getStreet() == null ? addresses.getStreet() : client.getStreet());
                addresses.setRegion(client.getRegion() == null ? addresses.getRegion() : client.getRegion());
                addresses.setCountry(client.getCountry() == null ? addresses.getCountry() : client.getCountry());
                addresses.setCity(client.getCity() == null ? addresses.getCity() : client.getCity());

                contactsDAO.save(contacts);
                addressesDAO.save(addresses);
                clientsDAO.save(dbEntry);
                return dbEntry.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return -1L;
            }
        } else {
            throw new NoSuchClientException();
        }
    }

    @Override
    public Long deleteClient(Long id) {
        if (clientsDAO.findById(id).isPresent()) {
            try {
                Clients dbENtry = clientsDAO.findById(id).get();
                Contacts contacts = contactsDAO.findById(dbENtry.getContactId()).get();
                Addresses addresses = addressesDAO.findById(dbENtry.getAddressId()).get();

                contactsDAO.delete(contacts);
                addressesDAO.delete(addresses);
                clientsDAO.delete(dbENtry);
                return id;
            } catch (Exception e) {
                e.printStackTrace();
                return -1L;
            }
        } else {
            return -1L;
        }
    }

    @Override
    public List<Client> getAllClients() {
        return clientsDAO.findAll().stream().map(clients -> {
            try {
                return getClientById(clients.getId());
            } catch (NoSuchClientException e) {
                e.printStackTrace();
            }
            return null;
        })
                         .collect(Collectors.toList());
    }

    @Override
    public List<Contractors> getRegionContractors(Long regionId) {
        return contractorsDAO.findAll().stream()
                             .filter(contractor -> contractor.getRegions().contains(String.valueOf(regionId)))
                             .collect(Collectors.toList());
    }

    @Override
    public List<Contractors> getContractorsForClient(Long clientId) throws NoSuchClientException {
        return getRegionContractors(getClientById(clientId).getRegion());
    }

    private boolean validateData(Client client) {
        if (client.getCountry().matches(COUNTRY_REGEXP)) {
            return false;
        }
        if (client.getPhone().matches(PHONE_REGEXP)) {
            return false;
        }
        if (!client.getEmail().matches(MAIL_REGEXP)) {
            return false;
        }
        return true;
    }

    private Client assembleClient(Clients client, Addresses address, Contacts contact) {
        return Client.builder().id(client.getId()).street(address.getStreet()).city(address.getCity())
                     .country(address.getCountry()).phone(contact.getPhone()).email(contact.getEmail())
                     .fName(client.getFName()).lName(client.getLName()).region(address.getRegion()).build();
    }
}