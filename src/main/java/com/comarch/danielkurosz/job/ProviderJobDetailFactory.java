package com.comarch.danielkurosz.job;

import org.quartz.JobDetail;

public interface ProviderJobDetailFactory {

    JobDetail createProviderJobDetail(String operatorId);
}
