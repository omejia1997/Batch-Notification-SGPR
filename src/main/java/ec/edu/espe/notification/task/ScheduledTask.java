package ec.edu.espe.notification.task;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private Job job;

	@Scheduled(cron = "0 0 19 * * *")
	public void executeJob() throws Exception {
		JobParameters jobParameter = new JobParametersBuilder().addString("JobID",String.valueOf(System.currentTimeMillis())).toJobParameters();
		jobLauncher.run(job,jobParameter);
	}
}