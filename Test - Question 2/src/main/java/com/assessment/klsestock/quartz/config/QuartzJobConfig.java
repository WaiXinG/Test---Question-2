package com.assessment.klsestock.quartz.config;

import java.util.Calendar;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.assessment.klsestock.quartz.job.SaveLatestKlseStockQuartzJob;

@Configuration
@EnableAutoConfiguration
public class QuartzJobConfig {
	final static Logger LOGGER = LoggerFactory.getLogger(QuartzJobConfig.class);

	private static final String CRON_EVERYDAY_9AM = "0 0 9 ? * * *";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private QuartzProperties quartzProperties;
	

	@Bean
	public SchedulerJobFactory springBeanJobFactory() {
		SchedulerJobFactory jobFactory = new SchedulerJobFactory();
		LOGGER.debug("Configuring Job factory");

		jobFactory.setApplicationContext(applicationContext);

		return jobFactory;
	}
	
	//Configure quartz job 
	@Bean(name = "saveLatestKlseStockQuartzJob")
	public JobDetailFactoryBean jobHandleSaveLatestKlseStock() {
		return createJobDetail(SaveLatestKlseStockQuartzJob.class, "Save Latest Klse Stock");
	}

	//Configure quartz trigger 
	@Bean(name = "handleSaveLatestKlseStock")
	public CronTriggerFactoryBean triggerHandleSaveLatestKlseStock(@Qualifier("saveLatestKlseStockQuartzJob") JobDetail jobDetail) {
		return createCronTriggerJob(jobDetail, CRON_EVERYDAY_9AM, "Save Latest Klse Stock Trigger");
	}
	
	public CronTriggerFactoryBean createCronTriggerJob(JobDetail jobDetail, String cronExpression, String triggerName) {

		LOGGER.debug("createCronTriggerJob| jobClass={}, triggerName={}, cronExpression={}", jobDetail.getJobClass(), triggerName,cronExpression);
		// To fix an issue with time-based cron jobs
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
		factoryBean.setJobDetail(jobDetail);
		factoryBean.setName(triggerName);
		factoryBean.setCronExpression(cronExpression);
		factoryBean.setStartTime(calendar.getTime());
		factoryBean.setStartDelay(0L);
		
		factoryBean.setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
		
		return factoryBean;
	}

	public JobDetailFactoryBean createJobDetail(Class jobClass, String jobName) {
		LOGGER.debug("createJobDetail| jobClass={}, jobName={}", jobClass.getName(), jobName);

		JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
		factoryBean.setName(jobName);
		factoryBean.setJobClass(jobClass);
		factoryBean.setDurability(true);
		return factoryBean;
	}

	@Bean
	public SchedulerFactoryBean scheduler(Trigger[] trigger, JobDetail[] job) {

		Properties properties = new Properties();
		properties.putAll(quartzProperties.getProperties());

		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setAutoStartup(true);
		factory.setTransactionManager(transactionManager);
		factory.setWaitForJobsToCompleteOnShutdown(true);
		
		factory.setDataSource(dataSource);
		factory.setQuartzProperties(properties);
		factory.setJobFactory(springBeanJobFactory());
		factory.setJobDetails(job);
		factory.setTriggers(trigger);
	
		
		return factory;
	}


}
