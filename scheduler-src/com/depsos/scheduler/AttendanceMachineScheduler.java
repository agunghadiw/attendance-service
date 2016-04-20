package com.depsos.scheduler;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class AttendanceMachineScheduler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			PropertiesConfiguration config = new PropertiesConfiguration("service.properties");
			String triggerArchiveJobSch = config.getString("triggerArchiveJobSch");
			int triggerMachineJobSch = config.getInt("triggerMachineJobSch");
			int triggerOtherInfoJobSch = config.getInt("triggerOtherInfoJobSch");
			int triggerSendingJobSch = config.getInt("triggerSendingJobSch");
			
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();            
            
            JobDetail archiveJob = JobBuilder.newJob(ArchiveJob.class).withIdentity("archiveJob", "group1").build();
            JobDetail machineJob = JobBuilder.newJob(MachineJob.class).withIdentity("machineJob", "group1").build();
            JobDetail otherInfoJob = JobBuilder.newJob(OtherInfoJob.class).withIdentity("otherInfoJob", "group1").build();
            JobDetail sendingJob = JobBuilder.newJob(SendingJob.class).withIdentity("sendingJob", "group1").build();

            
            Trigger triggerArchiveJob = TriggerBuilder.newTrigger()
            	    .withIdentity("triggerArchiveJob", "group1")
            	    .withSchedule(CronScheduleBuilder.cronSchedule(triggerArchiveJobSch))
            	    .build();
            
            Trigger triggerMachineJob = TriggerBuilder.newTrigger().withIdentity("triggerMachineJob", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		.withIntervalInSeconds(triggerMachineJobSch)
                    .repeatForever())            
                    .build();
            
            Trigger triggerOtherInfoJob = TriggerBuilder.newTrigger().withIdentity("triggerOtherInfoJob", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		.withIntervalInSeconds(triggerOtherInfoJobSch)
                    .repeatForever())            
                    .build();
            
            Trigger triggerSendingJob = TriggerBuilder.newTrigger().withIdentity("triggerSendingJob", "group1").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule()
            		.withIntervalInSeconds(triggerSendingJobSch)
                    .repeatForever())            
                    .build();

            scheduler.scheduleJob(archiveJob, triggerArchiveJob);
            scheduler.scheduleJob(machineJob, triggerMachineJob);
            scheduler.scheduleJob(otherInfoJob, triggerOtherInfoJob);
            scheduler.scheduleJob(sendingJob, triggerSendingJob);

            //scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

}
