package com.comarch.danielkurosz.config;

import com.comarch.danielkurosz.config.job.BirthdayJobConfiguration;
import com.comarch.danielkurosz.config.job.ZodiacJobConfiguration;
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

    private TagServiceConfiguration tagService;

    private BirthdayJobConfiguration birthdayJob;
    private ZodiacJobConfiguration zodiacJob;

    @JsonProperty
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public String getLogin() {
        return login;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public TagServiceConfiguration getTagService() {
        return tagService;
    }

    public void setTagService(TagServiceConfiguration tagService) {
        this.tagService = tagService;
    }

    public BirthdayJobConfiguration getBirthdayJob() {
        return birthdayJob;
    }

    public void setBirthdayJob(BirthdayJobConfiguration birthdayJob) {
        this.birthdayJob = birthdayJob;
    }

    public ZodiacJobConfiguration getZodiacJob() {
        return zodiacJob;
    }

    public void setZodiacJob(ZodiacJobConfiguration zodiacJob) {
        this.zodiacJob = zodiacJob;
    }
}
