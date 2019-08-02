package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.mongodb.morphia.query.UpdateOperations;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import java.util.*;

public class MongoClientDAO implements ClientDAO {

    private Datastore datastore;

    public MongoClientDAO(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public ClientEntity create(ClientEntity clientEntity) throws DuplicateKeyException {

        datastore.save(clientEntity);

        return datastore.get(ClientEntity.class, clientEntity.getId());
    }

    @Override
    public ClientEntity update(@Valid ClientEntity clientEntity) throws DuplicateKeyException {

        Query<ClientEntity> query = datastore.createQuery(ClientEntity.class).field("_id").equal(clientEntity.getId());

        UpdateOperations<ClientEntity> operation = datastore.createUpdateOperations(ClientEntity.class);

        operation = applyToUpdateQuery(operation, "firstName", clientEntity.getFirstName());
        operation = applyToUpdateQuery(operation, "lastName", clientEntity.getLastName());
        operation = applyToUpdateQuery(operation, "email", clientEntity.getEmail());
        operation = applyToUpdateQuery(operation, "birthday", clientEntity.getBirthday());

        datastore.update(query, operation);

        return datastore.get(ClientEntity.class, clientEntity.getId());
    }

    /**
     * @param id UUID of client
     * @return ClientEntity which has been removed
     * @throws NullPointerException when id is wrong
     */
    @Override
    public ClientEntity delete(@Nonnull UUID id) throws NullPointerException {
        ClientEntity client;

        client = datastore.get(ClientEntity.class, id);
        if (client == null) {
            throw new NullPointerException();
        }
        datastore.delete(client);

        return client;
    }

    @Override
    public List<ClientEntity> get(ClientEntity clientEntity, HashMap<String, String> sorts, int limit, int offset) {

        //because for Morphia limit = 0 means 'return everything'
        if (limit == 0) {
            return new LinkedList<>();
        }

        Query<ClientEntity> query = this.datastore.createQuery(ClientEntity.class);

        query = applyToQuery(query, "id", clientEntity.getId());
        query = applyToQuery(query, "firstName", clientEntity.getFirstName());
        query = applyToQuery(query, "lastName", clientEntity.getLastName());
        query = applyToQuery(query, "email", clientEntity.getEmail());

        if (sorts != null) {
            Sort[] mongosorts = new Sort[sorts.size()];
            int i = 0;
            for (Map.Entry<String, String> sort : sorts.entrySet()) {
                mongosorts[i] = setSortingParameter(sort.getKey(), sort.getValue());
                i++;
            }
            query.order(mongosorts);
        }

        return query.asList(new FindOptions().limit(limit).skip(offset));
    }


    public Query<ClientEntity> getQuery() {
        return this.datastore.createQuery(ClientEntity.class);
    }
    public List<ClientEntity> getClients(Query<ClientEntity> query,int limit, int offset){
        return query.asList(new FindOptions().limit(limit).skip(offset));
    }

    private Query<ClientEntity> applyToQuery(Query<ClientEntity> query, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            return query.field(fieldName).equal(fieldValue);
        }
        return query;
    }

    private UpdateOperations<ClientEntity> applyToUpdateQuery(UpdateOperations<ClientEntity> update, String fieldName, Object fieldValue) {
        if (fieldValue != null) {
            return update.set(fieldName, fieldValue);
        }
        return update;
    }

    private Sort setSortingParameter(String key, String value) {
        if (value.equals("asc")) {
            return Sort.ascending(key);
        }
        if (value.equals("desc")) {
            return Sort.descending(key);
        }
        // never happens
        else return null;
    }
}
