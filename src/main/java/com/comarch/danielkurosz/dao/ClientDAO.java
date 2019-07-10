package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * This interface contains basic methods that operate on database
 * This class implements all the functionality required for fetching, updating, and removing ClientEntities objects.
 * CRUD - create, read, update and delete
 *
 * @author daniel kurosz
 * @see ClientEntity
 * @see LinkedList
 */

public interface ClientDAO {

    boolean create(ClientEntity clientEntity);

    ClientEntity update(ClientEntity clientEntity);

    ClientEntity delete(UUID id);

    List<ClientEntity> get(ClientEntity clientEntity, HashMap<String, String> sorts, int limit, int offset);

}
