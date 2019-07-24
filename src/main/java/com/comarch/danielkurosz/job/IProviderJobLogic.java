package com.comarch.danielkurosz.job;

import org.quartz.JobExecutionException;

import java.util.Date;

public interface IProviderJobLogic {
    void execute(String operatorId, Date now) throws JobExecutionException;
}
