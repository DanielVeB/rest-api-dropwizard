package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.mongodb.DuplicateKeyException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ClientsService {

    private MongoClientDAO mongoClientDAO;
    private ClientMapper clientMapper;


   public ClientsService(MongoClientDAO mongoClientDAO, ClientMapper clientMapper){
       this.mongoClientDAO = mongoClientDAO;
       this.clientMapper = clientMapper;
   }

   public List<ClientDTO> getAllClients(int limit, int offset){
       List<ClientEntity> clientEntities = mongoClientDAO.getAll(limit, offset);

       //map to ClientDTO list
       return clientEntities.stream()
               .filter(Objects::nonNull)
               .map(clientEntity -> clientMapper.mapToClientDTO(clientEntity))
               .collect(Collectors.toList());
   }

   public ClientDTO getClientByEmail(String email){
       ClientEntity clientEntity = mongoClientDAO.getByEmail(email);
       return clientMapper.mapToClientDTO(clientEntity);

   }


   public List<ClientDTO> getClientsByName(String name){
       List<ClientEntity> clientEntities = mongoClientDAO.getByName(name);

       return clientEntities.stream()
               .filter(Objects::nonNull)
               .map(clientEntity -> clientMapper.mapToClientDTO(clientEntity))
               .collect(Collectors.toList());
   }


   public boolean createClient(ClientDTO clientDTO)throws InvalidEmailError,IllegalArgumentException, DuplicateKeyException {
       clientDTO.setCreationDate(new Date());

       // check correctness of email
       //regex from https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
       String regex ="(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
               "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
               "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
               "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
               "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]" +
               ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
               "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))";

       if(clientDTO.getEmail()==null || clientDTO.getFirstName()==null || clientDTO.getLastName() ==null){
           throw new IllegalArgumentException();
       }
       if(!clientDTO.getEmail().matches(regex)) {
           throw new InvalidEmailError();
       }

       ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
       clientEntity.setId(UUID.randomUUID());
       return mongoClientDAO.create(clientEntity);

   }

   public boolean updateClient(ClientDTO clientDTO){
        return true;
   }

   public boolean deleteClient(ClientDTO clientDTO){
       return true;
   }

}



