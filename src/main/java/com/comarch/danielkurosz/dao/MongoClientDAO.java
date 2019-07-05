package com.comarch.danielkurosz.dao;

import com.comarch.danielkurosz.data.ClientEntity;
import org.mongodb.morphia.Morphia;

import java.util.Date;
import java.util.LinkedList;

public class MongoClientDAO implements ClientDAO {

    LinkedList<ClientEntity> clientsEntities;

    final Morphia morphia = new Morphia();


    public MongoClientDAO() {
        

        clientsEntities = new LinkedList<>();
        clientsEntities.push(new ClientEntity("daniel@", "Daniel", "Kurosz",new Date()));
        clientsEntities.push(new ClientEntity("tomek@", "Tomasz", "Kowalski",new Date()));
        clientsEntities.push(new ClientEntity("janek@", "Jan", "Nowak",new Date()));
        clientsEntities.push(new ClientEntity("jan@", "Jan", "Kowal",new Date()));
    }


    @Override
    public void create(ClientEntity clientEntity) {
        clientsEntities.push(clientEntity);
    }

    @Override
    public void update(ClientEntity clientEntity) {

    }

    @Override
    public void delete(ClientEntity clientEntity) {
        clientsEntities.remove(clientEntity);
    }

    @Override
    public LinkedList<ClientEntity> getByName(String name) {
        LinkedList<ClientEntity> returnClientEntities = new LinkedList<>();
        for (ClientEntity c : clientsEntities) {
            if (c.getFirstName().equals(name)) returnClientEntities.push(c);
        }
        return returnClientEntities;
    }

    @Override
    public ClientEntity getByEmail(String email) {
        for (ClientEntity c : clientsEntities) {
            if (c.getEmail().equals(email)) return c;
        }
        return null;
    }

    @Override
    public LinkedList<ClientEntity> getAll() {
        return clientsEntities;
    }
}
