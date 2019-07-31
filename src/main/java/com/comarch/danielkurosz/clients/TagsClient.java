package com.comarch.danielkurosz.clients;

import com.comarch.danielkurosz.dto.ClientTagDTO;
import feign.*;

import java.util.List;

public interface TagsClient {

    @RequestLine("POST tags/client")
    @Headers("Content-Type: application/json")
    void create(ClientTagDTO clientTagDTO);

    @RequestLine("PUT tags/client")
    @Headers("Content-Type: application/json")
    void update(ClientTagDTO clientTagDTO);

    @RequestLine("GET tags/clients?client_id={client_id}")
    List<ClientTagDTO> getTags(@Param("client_id") List<String> clientsId);

    @RequestLine("GET tags/clients/id?tag_id!={withoutTags}&tag_id={withTags}")
    List<String> getClientsId(@Param("withoutTags")List<String>withoutTags, @Param("withTags") List<String> withTags);

}
