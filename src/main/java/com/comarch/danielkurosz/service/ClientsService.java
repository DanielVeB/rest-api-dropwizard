package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.dao.MongoClientDAO;
import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;
import com.comarch.danielkurosz.exceptions.InvalidEmailError;
import com.mongodb.DuplicateKeyException;

import java.util.*;
import java.util.stream.Collectors;

public class ClientsService {

    private MongoClientDAO mongoClientDAO;
    private ClientMapper clientMapper;

    public ClientsService(MongoClientDAO mongoClientDAO, ClientMapper clientMapper) {
        this.mongoClientDAO = mongoClientDAO;
        this.clientMapper = clientMapper;
    }

    public List<ClientDTO> getClients(ClientDTO clientDTO, String sortBy, int limit, int offset) throws IllegalArgumentException{

        HashMap<String, String> sorts = SortingConverter.getSorts(sortBy);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);

        List<ClientEntity> clientEntities = mongoClientDAO.get(clientEntity, sorts, limit, offset);
        return clientEntities.stream()
                .filter(Objects::nonNull)
                .map(clientE -> clientMapper.mapToClientDTO(clientE))
                .collect(Collectors.toList());
    }

    public ClientDTO createClient(ClientDTO clientDTO) throws InvalidEmailError, IllegalArgumentException, DuplicateKeyException {

        clientDTO.setCreationDate(new Date());
        validateFields(clientDTO);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        clientEntity.setId(UUID.randomUUID());

        ClientEntity returnClientEntity = mongoClientDAO.create(clientEntity);
        System.out.println(returnClientEntity.getFirstName() + "  " + returnClientEntity.getId());
        return clientMapper.mapToClientDTO(returnClientEntity);
    }

    public ClientDTO updateClient(ClientDTO clientDTO, String uuid) throws DuplicateKeyException, InvalidEmailError {

        validateFields(clientDTO);

        ClientEntity clientEntity = clientMapper.mapToClientEntity(clientDTO);
        clientEntity.setId(UUID.fromString(uuid));

        ClientEntity returnClientEntity = mongoClientDAO.update(clientEntity);

        return clientMapper.mapToClientDTO(returnClientEntity);
    }

    public ClientDTO deleteClient(String id) throws NullPointerException {

        ClientEntity clientEntity = mongoClientDAO.delete(UUID.fromString(id));

        return clientMapper.mapToClientDTO(clientEntity);

    }


    private void validateFields(ClientDTO clientDTO) throws IllegalArgumentException, InvalidEmailError {

        // check correctness of email
        //regex from https://stackoverflow.com/questions/201323/how-to-validate-an-email-address-using-a-regular-expression
        String regex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"" +
                "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9]" +
                "(?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.)" +
                "{3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]" +
                ":(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\" +
                "[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+))";

        if (clientDTO.getEmail() == null || clientDTO.getFirstName() == null || clientDTO.getLastName() == null) {
            throw new IllegalArgumentException();
        }
        if (!clientDTO.getEmail().matches(regex)) {
            throw new InvalidEmailError();
        }
    }
}




