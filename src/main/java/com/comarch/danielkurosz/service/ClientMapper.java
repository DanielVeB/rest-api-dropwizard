package com.comarch.danielkurosz.service;

import com.comarch.danielkurosz.data.ClientEntity;
import com.comarch.danielkurosz.dto.ClientDTO;

public class ClientMapper {

    public ClientDTO mapToClientDTO(ClientEntity clientEntity){
        return new ClientDTO(clientEntity.getEmail(),
                             clientEntity.getFirstName(),
                             clientEntity.getLastName(),
                             clientEntity.getCreationDate());
    }

    public ClientEntity mapToClientEntity(ClientDTO clientDTO){
        return new ClientEntity(clientDTO.getEmail(),
                                clientDTO.getFirstName(),
                                clientDTO.getLastName(),
                                clientDTO.getCreationDate());
    }

}
