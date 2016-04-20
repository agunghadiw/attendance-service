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
 * @create on Jun 17, 2015 10:52:29 AM
 */
@DisallowConcurrentExecution
public class SendingJob implements Job {

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
			String pathJson = config.getString("pathJson");
			String msAccessUrl = config.getString("msAccessUrl");
			
			SendingTask task = new SendingTask(url, username, password, pathJson, msAccessUrl);
			task.sending();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
