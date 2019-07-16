package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.AppException;
import com.comarch.danielkurosz.exceptions.DuplicateEmailException;
import com.mongodb.DuplicateKeyException;

import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

public class ClientsService {

    private MongoClientDAO mongoClientDAO;
    private ClientMapper clientMapper;
    private SortingConverter sortingConverter;

    public ClientsService(MongoClientDAO mongoClientDAO, ClientMapper clientMapper, SortingConverter sortingConverter) {
        this.mongoClientDAO = mongoClientDAO;
        this.clientMapper = clientMapper;
        this.sortingConverter = sortingConverter;
    }

    public List<ClientDTO> getClients(ClientDTO clientDTO, String sortBy, @NotNull int limit, @NotNull int offset) throws AppException {

        HashMap<String, String> sorts = sortingConverter.getSorts(sortBy);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);

        List<ClientEntity> clientEntities = mongoClientDAO.get(clientEntity, sorts, limit, offset);
        return clientEntities.stream()
                .filter(Objects::nonNull)
                .map(clientE -> clientMapper.mapToClientDTO(clientE))
                .collect(Collectors.toList());
    }

    public ClientDTO createClient(ClientDTO clientDTO) throws AppException {

        clientDTO.setCreationDate(new Date());
        ClientValidator.validate(clientDTO);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        clientEntity.setId(UUID.randomUUID());

        ClientEntity returnClientEntity;
        try {
            returnClientEntity = mongoClientDAO.create(clientEntity);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateEmailException();
        }
        return clientMapper.mapToClientDTO(returnClientEntity);
    }

    public ClientDTO updateClient(ClientDTO clientDTO, String uuid) throws AppException {

        ClientValidator.validate(clientDTO);
        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);

        ClientEntity returnClientEntity;
        try {
            clientEntity.setId(UUID.fromString(uuid));
            returnClientEntity = mongoClientDAO.update(clientEntity);
        } catch (DuplicateKeyException ex) {
            throw new DuplicateEmailException();
        } catch (IllegalArgumentException ex){
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
                    Response.Status.BAD_REQUEST.getStatusCode(),"Invalid UUID String","Please add correct ID","");
        }
        return clientMapper.mapToClientDTO(returnClientEntity);
    }

    public ClientDTO deleteClient(String id) throws AppException {
        ClientEntity clientEntity;
        try {
            clientEntity = mongoClientDAO.delete(UUID.fromString(id));
        } catch (NullPointerException ex) {
            throw new AppException(400, 400, "wrong id", "", "");
        }catch (IllegalArgumentException ex){
            throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(),
                    Response.Status.BAD_REQUEST.getStatusCode(),"Invalid UUID String","Please add correct ID","");
        }
        return clientMapper.mapToClientDTO(clientEntity);

    }

}




