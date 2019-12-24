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

    private final static String MAIL_REGEXP =
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

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
    public Long createClient(Client input) throws Exception {
        if (!validateData(input)) {
            throw new Exception("Client data is incorrect!");
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
            throw new Exception("Client data is incorrect!");
        }
        if (clientsDAO.findById(id).isPresent()) {
            try {
                Clients dbENtry = clientsDAO.findById(id).get();
                Contacts contacts = contactsDAO.findById(dbENtry.getContactId()).get();
                Addresses addresses = addressesDAO.findById(dbENtry.getAddressId()).get();

                dbENtry.setLName(client.getLName() == null ? dbENtry.getLName() : client.getLName());
                dbENtry.setFName(client.getFName() == null ? dbENtry.getFName() : client.getFName());
                contacts.setEmail(client.getEmail() == null ? contacts.getEmail() : client.getEmail());
                contacts.setPhone(client.getPhone() == null ? contacts.getPhone() : client.getPhone());
                addresses.setStreet(client.getStreet() == null ? addresses.getStreet() : client.getStreet());
                addresses.setRegion(client.getRegion() == null ? addresses.getRegion() : client.getRegion());
                addresses.setCountry(client.getCountry() == null ? addresses.getCountry() : client.getCountry());
                addresses.setCity(client.getCity() == null ? addresses.getCity() : client.getCity());

                contactsDAO.save(contacts);
                addressesDAO.save(addresses);
                clientsDAO.save(dbENtry);
                return dbENtry.getId();
            } catch (Exception e) {
                e.printStackTrace();
                return -1L;
            }
        } else {
            return -1L;
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

    private boolean validateData(Client client) {
        if (client.getCountry().length() > 3) {
            return false;
        }
        if (client.getPhone().length() > 12) {
            return false;
        }
        if (!client.getEmail().matches(MAIL_REGEXP)) {
            return false;
        }
        return true;
    }

    private Client getDefaultClient() {
        return Client.builder().id(-1L).city("Austin").country("USA").email("john.doe@undefined.com").fName("John")
                     .lName("Doe").phone("+3809811111111").region(0L).street("Elm Street").build();
    }
}