package com.comarch.danielkurosz.clients;

import com.comarch.danielkurosz.dto.UserTagDTO;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.util.HashMap;
import java.util.List;

public interface TagsClient {

    @RequestLine("POST /userid={id}")
    Response create(@Param("id") String id);

    @RequestLine("GET /users/{client_ids}")
    HashMap<String, List<UserTagDTO>> getTags(@Param("client_ids") String clientsId);
}
