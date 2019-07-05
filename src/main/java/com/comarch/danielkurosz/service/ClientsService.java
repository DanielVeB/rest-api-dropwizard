package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ClientsService {

    MongoClientDAO mongoClientDAO;



   public ClientsService(){
       mongoClientDAO = new MongoClientDAO();
   }

   public List<ClientDTO> getAllClients(){
       List<ClientEntity> clientEntities = mongoClientDAO.getAll();

       //map to ClientDTO list
       return clientEntities.stream()
               .filter(clientEntity -> clientEntity != null)
               .map(clientEntity -> doSomething(clientEntity))
               .collect(Collectors.toList());

   }

   private ClientDTO doSomething(ClientEntity c){
       return new ClientDTO(c.getFirstName(),c.getLastName(),c.getEmail(),new Date());
   }

   public ClientDTO getClientByEmail(){
       return null;
   }

   public List<ClientDTO> getClientsByName(){
       return null;
   }

   public boolean createClient(ClientDTO clientDTO){
       return true;
   }

   public boolean updateClient(ClientDTO clientDTO){
        return true;
   }

   public boolean deleteClient(ClientDTO clientDTO){
       return true;
   }





}
