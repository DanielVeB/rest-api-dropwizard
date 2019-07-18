package com.comarch.danielkurosz;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ClientServiceConfiguration extends Configuration {

    @NotEmpty
    private String version;



    @NotEmpty
    private String login;
    @NotEmpty
    private String password;

    @NotEmpty
    private String tagServiceLogin;
    @NotEmpty
    private String tagServicePassword;

    @JsonProperty
    public void setLogin(String login) {
        this.login = login;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty
    String getLogin() {
        return login;
    }
    @JsonProperty
    String getPassword() {
        return password;
    }
    @JsonProperty
    String getVersion() {
        return version;
    }
    @JsonProperty
    String getTagServiceLogin() {
        return tagServiceLogin;
    }
    @JsonProperty
    public void setTagServiceLogin(String tagServiceLogin) {
        this.tagServiceLogin = tagServiceLogin;
    }
    @JsonProperty
    String getTagServicePassword() {
        return tagServicePassword;
    }
    @JsonProperty
    public void setTagServicePassword(String tagServicePassword) {
        this.tagServicePassword = tagServicePassword;
    }
}
