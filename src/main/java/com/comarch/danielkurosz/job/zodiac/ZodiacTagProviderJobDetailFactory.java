package com.comarch.danielkurosz.job.zodiac;

import com.comarch.danielkurosz.job.ProviderJobDetailFactory;
import org.quartz.JobDetail;

import static org.quartz.JobBuilder.newJob;

public class ZodiacTagProviderJobDetailFactory implements ProviderJobDetailFactory {

    private static final String JOB_GROUP = "providerJobs";
    private static final String KEY_OPERATOR_ID = "operatorID";

    public JobDetail createProviderJobDetail(String operatorId){
        return newJob(ZodiacTagProviderJob.class)
                .withIdentity(operatorId, JOB_GROUP)
                .usingJobData(KEY_OPERATOR_ID, operatorId)
                .build();
    }

}
