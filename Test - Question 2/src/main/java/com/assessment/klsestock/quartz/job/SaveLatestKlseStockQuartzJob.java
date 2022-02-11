package com.assessment.klsestock.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assessment.klsestock.service.KlseStockService;

@Component
@DisallowConcurrentExecution
public class SaveLatestKlseStockQuartzJob implements Job{

static final Logger LOGGER = LoggerFactory.getLogger(SaveLatestKlseStockQuartzJob.class);
	
	@Autowired
	KlseStockService service;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		LOGGER.debug("GetLatestKlseStockQuartzJob: {} |start @ {}",context.getJobDetail().getKey().getName(), context.getFireTime());
		try {
			service.saveLatestKlseStock();
		} catch (Exception e) {
			LOGGER.debug("",e);
		}		
		LOGGER.debug("GetLatestKlseStockQuartzJob: {} |completed| Next job scheduled @ {}",context.getJobDetail().getKey().getName(), context.getNextFireTime());
	}
}
