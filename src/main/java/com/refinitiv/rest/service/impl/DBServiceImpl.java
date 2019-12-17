package com.refinitiv.rest.service.impl;

import com.refinitiv.db.Addresses;
import com.refinitiv.db.Clients;
import com.refinitiv.db.Contacts;
import com.refinitiv.db.dao.AddressesDAO;
import com.refinitiv.db.dao.ClientsDAO;
import com.refinitiv.db.dao.ContactsDAO;
import com.refinitiv.rest.service.DBService;
import com.refinitiv.rest.service.entity.Client;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Component
public class DBServiceImpl extends SpringBeanAutowiringSupport implements DBService {

    private final AddressesDAO addressesDAO;

    private final ClientsDAO clientsDAO;

    private final ContactsDAO contactsDAO;

    public DBServiceImpl(AddressesDAO dao, ClientsDAO clientsDAO, ContactsDAO contactsDAO) {
        addressesDAO = dao;
        this.clientsDAO = clientsDAO;
        this.contactsDAO = contactsDAO;
    }

    @Override
    public Client getClientById(Long id) {
        Clients client = clientsDAO.findById(id).orElse(null);
        if (client == null) {
            return getDefaultClient();
        }
        Addresses addresses = addressesDAO.findById(client.getAddressId()).orElse(new Addresses(0L, 0L, "", "", ""));
        Contacts contacts = contactsDAO.findById(client.getContactId()).orElse(new Contacts(0L, "", ""));

        return Client.builder().id(client.getId()).street(addresses.getStreet()).city(addresses.getCity())
                     .country(addresses.getCountry()).phone(contacts.getPhone()).email(contacts.getEmail())
                     .fName(client.getFName()).lName(client.getLName()).region(addresses.getRegion()).build();
    }

    @Override
    public Long createClient(Client input) {
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

    private Client getDefaultClient() {
        return Client.builder().id(-1L).city("Austin").country("USA").email("john.doe@undefined.com").fName("John")
                     .lName("Doe").phone("+3809811111111").region(0L).street("Elm Street").build();
    }
}
