package com.comarch.danielkurosz.job;

import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import static org.quartz.TriggerBuilder.newTrigger;

public class ProviderTriggerBuilder {
    private static final String TRIGGER_GROUP = "providerTriggers";

    public static TriggerBuilder<Trigger> newProviderTrigger(String name) {
        return newTrigger()
                .withIdentity(name, TRIGGER_GROUP);
    }

}
