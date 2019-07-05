package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ClientsService {

    MongoClientDAO mongoClientDAO;
    ClientMapper clientMapper;


   public ClientsService(){
       mongoClientDAO = new MongoClientDAO();
       clientMapper = new ClientMapper();
   }

   public List<ClientDTO> getAllClients(){
       List<ClientEntity> clientEntities = mongoClientDAO.getAll();

       //map to ClientDTO list
       return clientEntities.stream()
               .filter(clientEntity -> clientEntity != null)
               .map(clientEntity -> clientMapper.mapToClientDTO(clientEntity))
               .collect(Collectors.toList());
   }

   public ClientDTO getClientByEmail(){
       return null;
   }

   public List<ClientDTO> getClientsByName(){
       return null;
   }

   public boolean createClient(ClientDTO clientDTO){
       clientDTO.setCreationDate(new Date());
       ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
       //TO DO!!!
       // check correctness of email
       return mongoClientDAO.create(clientEntity);

   }

   public boolean updateClient(ClientDTO clientDTO){
        return true;
   }

   public boolean deleteClient(ClientDTO clientDTO){
       return true;
   }





}
