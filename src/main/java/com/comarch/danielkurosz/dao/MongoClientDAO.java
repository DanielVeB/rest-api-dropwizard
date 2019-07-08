package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MongoClientDAO implements ClientDAO {

    final Morphia morphia = new Morphia();
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
        LinkedList<ClientEntity> returnClientEntities = new LinkedList<>();

        return returnClientEntities;
    }

    @Override
    public ClientEntity getByEmail(String email) {

        return null;
    }

    @Override
    public List<ClientEntity> getAll() {

        List<ClientEntity> clientEntities= new LinkedList<>();

        try {
            clientEntities = this.datastore.find(ClientEntity.class).asList();

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return clientEntities;
    }
}
