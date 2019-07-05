package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;

import java.util.LinkedList;

/**
 * This interface contains basic methods that operate on database
 * This class implements all the functionality required for fetching, updating, and removing Clients objects.
 * CRUD - create, read, update and delete
 * read method was split into 3 methods: getByName, getByEmail and getAll
 *
 * @author daniel kurosz
 * @see ClientEntity
 * @see LinkedList
 */

public interface ClientDAO {

    void create(ClientEntity clientEntity);

    void update(ClientEntity clientEntity);

    void delete(ClientEntity clientEntity);

    LinkedList<ClientEntity> getByName(String name);

    ClientEntity getByEmail(String email);

    LinkedList<ClientEntity> getAll();

}
