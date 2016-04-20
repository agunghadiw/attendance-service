/**
 * 
 */
package com.depsos.scheduler;

import java.util.List;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

/**
 * @author Mas AH
 * @create on Jun 17, 2015 10:47:29 AM
 */
@PersistJobDataAfterExecution
//@DisallowConcurrentExecution
public class MachineJob implements Job {

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
			/*List<Object> machineIDs = config.getList("machineIDs");
			String machineID = null;
			//String machineKey = config.getString("machineKey");
			//String machineURL = config.getString("machineURL");
			
			for (Object obj : machineIDs) {
				if (config.getString(((String)obj+".run"))!=null && config.getString(((String)obj+".run")).equalsIgnoreCase("F")) {
					machineID = (String)obj;
					config.setProperty((machineID+".run"), "T");
					config.save();
					break;
				}
			}
			
			if (machineID==null) {
				// reset all to F
				for (Object obj : machineIDs) {
					config.setProperty(((String)obj+".run"), "F");
				}
				config.save();
			} else {
				String machineKey = config.getString((machineID+".key"));
				String machineURL = config.getString((machineID+".URL"));
				MachineTask machineTask = new MachineTask(url, username, password, machineKey, machineURL);
				
				if (machineID==null || (machineID!=null && machineID.length()==0)) {
					machineID = machineTask.checkMachineId();
					if (machineID!=null && machineID.length()>0) {
						config.setProperty("machineID", machineID);
						config.save("service.properties");
					}
				} else {
					
					machineTask.saveAllData(machineID);
					
				//}
			}*/
			
			
			MachineTask machineTask = new MachineTask(url, username, password);
			Object[] obj = machineTask.getMachineIdKeyUrl();
			if (obj!=null) {
				machineTask.saveAllData((String)obj[0], (String)obj[1], (String)obj[2]);
				//machineTask.demo((String)obj[0], (String)obj[1], (String)obj[2]);
				// finish
				machineTask.finishFlagMachineIdKeyUrl();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
