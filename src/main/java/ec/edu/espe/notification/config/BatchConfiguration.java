package ec.edu.espe.notification.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ec.edu.espe.notification.task.SendInvestigationActivityEmail;
import ec.edu.espe.notification.task.SendLinkingActivityEmail;
  
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
  @Autowired 
  private JobBuilderFactory jobs;

  @Autowired 
  private StepBuilderFactory steps;

  @Bean
  protected Step sendLinkingActivityEmail() {
      return steps
        .get("SendLinkingActivityEmail")
        .tasklet(new SendLinkingActivityEmail())
        .build();
  }

  @Bean
  protected Step sendInvestigationActivityEmail() {
      return steps
        .get("sendInvestigationActivityEmail")
        .tasklet(new SendInvestigationActivityEmail())
        .build();
  }

  @Bean
  public Job job() {
      return jobs
        .get("taskletsJob")
        .start(sendLinkingActivityEmail())
        .next(sendInvestigationActivityEmail())
        .build();
  }
}
