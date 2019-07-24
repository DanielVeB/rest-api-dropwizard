package com.comarch.danielkurosz.job;

import org.mongodb.morphia.Datastore;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.simpl.PropertySettingJobFactory;
import org.quartz.spi.TriggerFiredBundle;

import java.util.HashMap;
import java.util.Map;

public class ServiceJobFactory extends PropertySettingJobFactory {

    private Map<Class, JobModifier> jobModifiers;

    public ServiceJobFactory(Datastore datastore){
        jobModifiers = new HashMap<>();

        jobModifiers.put(ProviderJob.class, (Job job)->{
            ProviderJob providerJob = (ProviderJob) job;
            providerJob.setDatastore(datastore);
        });
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) throws SchedulerException {
        Job job = super.newJob(bundle, scheduler);

        JobModifier modifier = jobModifiers.get(job.getClass());

        if (modifier != null) {
            modifier.apply(job);
        }

        return job;
    }



    private interface JobModifier{
        void apply(Job job);
    }
}
