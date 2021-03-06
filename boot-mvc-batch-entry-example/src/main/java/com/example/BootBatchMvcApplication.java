package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BootBatchMvcApplication {

	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job1;

	public static void main(String[] args) {
		SpringApplication.run(BootBatchMvcApplication.class, args);
	}

	@GetMapping("/")
	public String batchExecute() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder()
				.addLong("time", System.currentTimeMillis())
				.addString("data", "パラメータ追加！")
				.toJobParameters();
		jobLauncher.run(job1, jobParameters);

		return "!!! Batch終了 !!!";
	}
}
