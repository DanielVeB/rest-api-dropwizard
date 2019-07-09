package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.FindOptions;
import java.util.LinkedList;
import java.util.List;

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
    public void update(ClientEntity clientEntity) {

    }

    @Override
    public void delete(ClientEntity clientEntity) {

    }

    @Override
    public LinkedList<ClientEntity> getByName(String name) {
        return null;
    }

    @Override
    public ClientEntity getByEmail(String email) {

        return null;
    }

    @Override
    public List<ClientEntity> getAll(int limit, int offset) {

        List<ClientEntity> clientEntities= new LinkedList<>();

        try {
            //TO DO!!!
            //find better way
            // limit(int limit) and offset(int offset) is deprecated
            //clientEntities = this.datastore.find(ClientEntity.class).limit(limit).offset(offset).asList();

            //it's better
            clientEntities = this.datastore.createQuery(ClientEntity.class).asList(new FindOptions().limit(limit).skip(offset));

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return clientEntities;
    }
}
