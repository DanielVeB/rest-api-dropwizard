package com.comarch.danielkurosz.jdbi;

import com.comarch.danielkurosz.data.Client;

import java.util.LinkedList;

/**
 * This interface contains basic methods that operate on database
 * This class implements all the functionality required for fetching, updating, and removing Clients objects.
 * CRUD - create, read, update and delete
 * read method was split into 3 methods: getByName, getByEmail and getAll
 *
 * @author daniel kurosz
 * @see Client
 * @see LinkedList
 */

public interface ClientDAO {

    void create(Client client);

    void update(Client client);

    void delete(Client client);

    LinkedList<Client> getByName(String name);

    Client getByEmail(String email);

    LinkedList<Client> getAll();

}
