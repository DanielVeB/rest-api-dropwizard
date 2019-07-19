package com.comarch.danielkurosz.clients;

import com.comarch.danielkurosz.dto.UserTagDTO;
import feign.Param;
import feign.RequestLine;
import feign.Response;

import java.util.List;

public interface TagsClient {

    @RequestLine("GET /userid={id}")
    List<UserTagDTO> tags(@Param("id") String id);

    @RequestLine("POST /userid={id}")
    Response create(@Param("id") String id);
}
