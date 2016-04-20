/**
 * 
 */
package com.depsos.scheduler;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Mas AH
 * @create on Jun 17, 2015 10:47:47 AM
 */
@DisallowConcurrentExecution
public class ArchiveJob implements Job {

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("service.properties");
			String url = config.getString("url");
			String username = config.getString("username");
			String password = config.getString("password");
			int maxArchiveDay = config.getInt("maxArchiveDay");
			//
			ArchiveTask task = new ArchiveTask(url, username, password, maxArchiveDay);
			task.remove();			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
