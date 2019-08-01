package com.comarch.danielkurosz.clients;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;
import java.util.UUID;

public interface TagsClient {

    @RequestLine("POST tags/client")
    @Headers("Content-Type: application/json")
    void create(ClientTagsDTO clientTagDTO);

    @RequestLine("PUT tags/client")
    @Headers("Content-Type: application/json")
    void update(ClientTagsDTO clientTagDTO);


    @RequestLine("GET tags/client={client_id}?tag_id={tag_id}&tag_value={tag_value}&limit={limit}&offset={offset}")
    List<Tag> getTagsByClientId(@Param("client_id") String clientId,
                                @Param("tag_id") String tagId,
                                @Param("tag_value") String tagValue,
                                @Param("limit") int limit,
                                @Param("offset") int offset);

    @RequestLine("GET tags/clients_id?tag_id={id}&tag_value={value}&limit={limit}&offset={offset}")
    List<UUID> getClientsId(@Param("id") String tagId,
                            @Param("value") String tagValue,
                            @Param("limit") int limit,
                            @Param("offset") int offset);

    @RequestLine("GET tags?tag_id={id}&tag_value={value}&limit={limit}&offset={offset}")
    List<ClientTagDTO> getClientTagsDTO(@Param("id") String tagId,
                                        @Param("value") String tagValue,
                                        @Param("limit") int limit,
                                        @Param("offset") int offset);

    @RequestLine("GET tags/clients?client_id={client_id}")
    List<ClientTagsDTO> getTags(@Param("client_id") List<String> clientsId);

    @RequestLine("GET tags/clients/id?tag_id!={withoutTags}&tag_id={withTags}")
    List<String> getClientsId(@Param("withoutTags") List<String> withoutTags, @Param("withTags") List<String> withTags);

}
