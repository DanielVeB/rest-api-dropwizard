package com.comarch.danielkurosz.clients;

import com.comarch.danielkurosz.dto.UserTagDTO;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.util.HashMap;
import java.util.List;

public interface TagsClient {

    @RequestLine("POST tags/client={client_id}")
    Response create(@Param("client_id") String clientId);

    @RequestLine("GET tags/clients?client_id={client_id}")
    HashMap<String, List<UserTagDTO>> getTags(@Param("client_id") List<String> clientsId);
}
