package com.comarch.danielkurosz.clients;

import com.comarch.danielkurosz.dto.UserTagDTO;
import feign.*;

import java.util.HashMap;
import java.util.List;

@Headers("Accept: application/json")
public interface TagsClient {

    @RequestLine("POST tags/client={client_id}")
    Response create(@Param("client_id") String clientId);

    @RequestLine("GET tags/clients?client_id={client_id}")
    HashMap<String, List<UserTagDTO>> getTags(@Param("client_id") List<String> clientsId);

    @RequestLine("PUT tags/client={client_id}")
    @Headers("Content-Type: application/json")
    @Body("%7B\"tag_id\": \"{tag_id}\", \"tag_value\": \"{tag_value}\"%7D")
    void addZodiac(@Param("client_id")String clientId, @Param("tag_id") String tagId, @Param("tag_value") String tagValue);

}
