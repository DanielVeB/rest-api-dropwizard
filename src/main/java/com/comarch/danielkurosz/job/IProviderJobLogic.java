package com.comarch.danielkurosz.job;

import org.quartz.JobExecutionException;


public interface IProviderJobLogic {
    void execute() throws JobExecutionException;
}
