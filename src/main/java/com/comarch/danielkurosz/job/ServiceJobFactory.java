package com.comarch.danielkurosz.job;

import com.comarch.danielkurosz.clients.TagsClient;
import com.comarch.danielkurosz.job.birthday.BirthdayProviderJob;
import com.comarch.danielkurosz.job.zodiac.ZodiacTagProviderJob;
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

    public ServiceJobFactory(Datastore datastore, TagsClient client){
        jobModifiers = new HashMap<>();

        jobModifiers.put(BirthdayProviderJob.class, (Job job)->{
            BirthdayProviderJob providerJob = (BirthdayProviderJob) job;
            providerJob.setDatastore(datastore);
        });

        jobModifiers.put(ZodiacTagProviderJob.class, (Job job)->{
            ZodiacTagProviderJob providerJob = (ZodiacTagProviderJob) job;
            providerJob.setClient(client);
            providerJob.setDatastore(datastore);
        });

    }

//    Called by the scheduler at the time of the trigger firing, in order to produce a Job instance on which to call execute.
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
