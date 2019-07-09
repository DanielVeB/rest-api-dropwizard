package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MongoClientDAO implements ClientDAO {

    private final Morphia morphia = new Morphia();
    private Datastore datastore;

    public MongoClientDAO() {
        this.morphia.mapPackage("com.comarch.danielkurosz.data");
        this.datastore = morphia.createDatastore(new MongoClient(), "dropwizard");

    }

    @Override
    public boolean create(ClientEntity clientEntity) throws DuplicateKeyException {
        datastore.save(clientEntity);
        return true;
    }

    @Override
    public ClientEntity update(ClientEntity clientEntity) {

        Query<ClientEntity> query = datastore.createQuery(ClientEntity.class).field("_id").equal(clientEntity.getId());
        UpdateOperations<ClientEntity> operation = datastore.createUpdateOperations(ClientEntity.class).
                set("firstName",clientEntity.getFirstName()).set("lastName",clientEntity.getLastName()).set("email",clientEntity.getEmail());

        datastore.update(query,operation);

        return datastore.get(ClientEntity.class, clientEntity.getId());
    }

    @Override
    public void delete(ClientEntity clientEntity) {

    }

    @Override
    public List<ClientEntity>get(ClientEntity clientEntity, HashMap<String, String > sorts, int limit, int offset){
        Query<ClientEntity> query = this.datastore.createQuery(ClientEntity.class);

        if(clientEntity.getFirstName()!=null)query.field("firstName").equal(clientEntity.getFirstName());
        if(clientEntity.getLastName()!=null)query.field("lastName").equal(clientEntity.getLastName());
        if(clientEntity.getEmail()!=null)query.field("email").equal(clientEntity.getEmail());

        if(sorts != null){
            Sort [] mongosorts = new Sort[sorts.size()];
            int i =0;
            for(Map.Entry<String, String> sort : sorts.entrySet()){
                if(sort.getValue().equals("asc"))mongosorts[i]=Sort.ascending(sort.getKey());
                if(sort.getValue().equals("desc"))mongosorts[i]=Sort.descending(sort.getKey());
                i++;
            }
            query.order(mongosorts);
        }
        return query.asList(new FindOptions().limit(limit).skip(offset));
    }

//    @Override
//    public List<ClientEntity> getAll(int limit, int offset) {
//
//        List<ClientEntity> clientEntities= new LinkedList<>();
//
//        try {
//
//            clientEntities = this.datastore.createQuery(ClientEntity.class).asList(new FindOptions().limit(limit).skip(offset));
//
//        }catch (Exception ex){
//            System.out.println(ex.getMessage());
//        }
//
//        return clientEntities;
//    }
}
