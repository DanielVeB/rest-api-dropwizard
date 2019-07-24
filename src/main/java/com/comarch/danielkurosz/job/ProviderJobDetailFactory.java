package com.comarch.danielkurosz.job;

import org.quartz.JobDetail;

import static org.quartz.JobBuilder.newJob;

public class ProviderJobDetailFactory {

    private static final String JOB_GROUP = "providerJobs";
    private static final String KEY_OPERATOR_ID = "operatorID";


    public static JobDetail createProviderJobDetail(String operatorId){
        return newJob(ProviderJob.class)
                .withIdentity(operatorId, JOB_GROUP)
                .usingJobData(KEY_OPERATOR_ID, operatorId)
                .build();

    }
}
