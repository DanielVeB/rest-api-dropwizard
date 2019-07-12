package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.DuplicateKeyException;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.Sort;
import org.mongodb.morphia.query.UpdateOperations;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
    public ClientEntity update(ClientEntity clientEntity) throws DuplicateKeyException {

        Query<ClientEntity> query = datastore.createQuery(ClientEntity.class).field("_id").equal(clientEntity.getId());

        UpdateOperations<ClientEntity> operation = datastore.createUpdateOperations(ClientEntity.class).
                set("firstName", clientEntity.getFirstName()).
                set("lastName", clientEntity.getLastName()).
                set("email", clientEntity.getEmail());

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

        Query<ClientEntity> query = this.datastore.createQuery(ClientEntity.class);

        query = applyToQuery(query, "firstName", clientEntity.getFirstName());
        query = applyToQuery(query, "lastName", clientEntity.getLastName());
        query = applyToQuery(query, "email", clientEntity.getEmail());

        if (sorts != null) {
            Sort[] mongosorts = new Sort[sorts.size()];
            int i = sorts.size()-1;
            for (Map.Entry<String, String> sort : sorts.entrySet()) {
                if (sort.getValue().equals("asc")) {
                    mongosorts[i] = Sort.ascending(sort.getKey());
                }
                if (sort.getValue().equals("desc")) {
                    mongosorts[i] = Sort.descending(sort.getKey());
                }
                i--;
            }
            query.order(mongosorts);
        }
        return query.asList(new FindOptions().limit(limit).skip(offset));
    }

    private Query<ClientEntity> applyToQuery(Query<ClientEntity> query, String fieldName, String fieldValue) {
        if (fieldValue != null) {
            return query.field(fieldName).equal(fieldValue);
        }
        return query;
    }
}
