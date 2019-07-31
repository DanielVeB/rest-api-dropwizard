package com.comarch.danielkurosz.config.job;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class JobConfiguration {
    private String name;
    private String cron;

    @JsonProperty
    public String getName() {
        return name;
    }
    @JsonProperty
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getCron() {
        return cron;
    }

    @JsonProperty
    public void setCron(String cron) {
        this.cron = cron;
    }
}
