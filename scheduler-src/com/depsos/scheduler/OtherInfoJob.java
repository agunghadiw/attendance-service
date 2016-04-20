/**
 * 
 */
package com.depsos.scheduler;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author Mas AH
 * @create on Jun 17, 2015 10:54:18 AM
 */
public class OtherInfoJob implements Job {

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
			String machineID = config.getString("machineID");
			String machineKey = config.getString("machineKey");
			String machineURL = config.getString("machineURL");
			
			OtherInfoTask otherInfoTask = new OtherInfoTask(url, username, password, pathJson, machineKey, machineURL);
			
			if (machineID==null || (machineID!=null && machineID.length()==0)) {
				MachineTask machineTask = new MachineTask(url, username, password);
				/*machineID = machineTask.checkMachineId();
				if (machineID!=null && machineID.length()>0) {
					config.setProperty("machineID", machineID);
					config.save("service.properties");
				}*/
			} else {
				
				otherInfoTask.ping(machineID);
				otherInfoTask.restart(machineID);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
