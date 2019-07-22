package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.dto.UserTagDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateEmailException;
import com.google.gson.Gson;
import com.mongodb.DuplicateKeyException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

public class ClientsService {

    private TagsClient tagsClient;

    private MongoClientDAO mongoClientDAO;
    private ClientMapper clientMapper;
    private SortingConverter sortingConverter;

    public ClientsService(MongoClientDAO mongoClientDAO, ClientMapper clientMapper, SortingConverter sortingConverter, TagsClient tagsClient) {
        this.mongoClientDAO = mongoClientDAO;
        this.clientMapper = clientMapper;
        this.sortingConverter = sortingConverter;
        this.tagsClient = tagsClient;
    }

    public List<ClientDTO> getClients(ClientDTO clientDTO, String sortBy, @NotNull int limit, @NotNull int offset) throws AppException {

        HashMap<String, String> sorts = sortingConverter.getSorts(sortBy);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);

        List<ClientEntity> clientEntities = mongoClientDAO.get(clientEntity, sorts, limit, offset);

        List<ClientDTO> clientsDTO =  clientEntities.stream()
                .filter(Objects::nonNull)
                .map(clientE -> clientMapper.mapToClientDTO(clientE))
                .collect(Collectors.toList());

        List<String> clientsId = new LinkedList<>();
        for (ClientDTO clientDTO_ : clientsDTO){
            clientsId.add(clientDTO_.getId());
        }

//        connect with tag service to get tags for all passed id
        HashMap<String,List<UserTagDTO>> tags = this.tagsClient.getTags(new Gson().toJson(clientsId.toArray()));

        for(ClientDTO DTOclient : clientsDTO){
            DTOclient.setTags(tags.get(DTOclient.getId()));
        }

        return clientsDTO;
    }

    public ClientDTO createClient(ClientDTO clientDTO) throws AppException {

        clientDTO.setCreationDate(new Date());

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        clientEntity.setId(UUID.randomUUID());

        ClientEntity returnClientEntity;
        try {
            returnClientEntity = mongoClientDAO.create(clientEntity);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateEmailException();
        }

        ClientDTO returnedClient =  clientMapper.mapToClientDTO(returnClientEntity);
        this.tagsClient.create(returnedClient.getId());
        return returnedClient;
    }

    public ClientDTO updateClient(ClientDTO clientDTO, String uuid) throws AppException {

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);

        ClientEntity returnClientEntity;
        try {
            clientEntity.setId(UUID.fromString(uuid));
            returnClientEntity = mongoClientDAO.update(clientEntity);
            if(returnClientEntity == null){
               throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),"There is no user with this id","","");
            }
        } catch (DuplicateKeyException ex) {
            throw new DuplicateEmailException();
        } catch (IllegalArgumentException ex) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid UUID String", "Please add correct ID", "");
        }
        return clientMapper.mapToClientDTO(returnClientEntity);
    }

    public ClientDTO deleteClient(String id) throws AppException {
        ClientEntity clientEntity;
        try {
            clientEntity = mongoClientDAO.delete(UUID.fromString(id));
        } catch (NullPointerException ex) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "There is no user with this id", "Please add another id", "");
        } catch (IllegalArgumentException ex) {
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), "Invalid UUID String", "Please add correct ID", "");
        }
        return clientMapper.mapToClientDTO(clientEntity);
    }
}




