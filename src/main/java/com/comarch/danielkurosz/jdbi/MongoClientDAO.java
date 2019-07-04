package com.comarch.danielkurosz.jdbi;

import com.comarch.danielkurosz.data.Client;

import java.util.LinkedList;

public class MongoClientDAO implements ClientDAO {

    private String server;
    private int port;

    LinkedList<Client> clients ;


    public MongoClientDAO(){
        clients = new LinkedList<>();
        clients.push(new Client("daniel@", "Daniel", "Kurosz"));
        clients.push(new Client("tomek@","Tomasz","Kowalski"));
        clients.push(new Client("janek@","Jan","Nowak"));
        clients.push(new Client("jan@","Jan","Kowal"));
    }



    @Override
    public void create(Client client) {
        clients.push(client);
    }

    @Override
    public void update(Client client) {

    }

    @Override
    public void delete(Client client) {
        clients.remove(client);
    }

    @Override
    public LinkedList<Client> getByName(String name) {
        LinkedList<Client> returnClients = new LinkedList<>();
        for(Client c: clients){
            if(c.getFirstName().equals(name))returnClients.push(c);
        }
        return returnClients;
    }

    @Override
    public Client getByEmail(String email) {
        for(Client c : clients){
            if(c.getEmail().equals(email))return c;
        }
        return null;
    }

    @Override
    public LinkedList<Client> getAll() {
        return clients;
    }
}
