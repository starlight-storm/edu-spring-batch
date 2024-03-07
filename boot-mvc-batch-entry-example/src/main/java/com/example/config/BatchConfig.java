package com.example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.batch.step.EntryItemProcessor;
import com.example.batch.step.EntryItemReader;
import com.example.batch.step.EntryItemWriter;

@Configuration
public class BatchConfig {


	@Bean
	Job job1(JobRepository jobRepository, Step step) {
		return new JobBuilder("job1", jobRepository)
				.start(step)
				.build();
	}


	@Bean
	Step step1(JobRepository jobRepository, PlatformTransactionManager tm) {
		return new StepBuilder("step1", jobRepository)
				.chunk(1, tm)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.faultTolerant()
				.build();
	}

	@Bean
	@StepScope
	public ItemReader<String> reader() {
		return new EntryItemReader();
	}

	@Bean
	public ItemProcessor<Object, String> processor() {
		return new EntryItemProcessor();
	}

	@Bean
	public ItemWriter<Object> writer() {
		return new EntryItemWriter();
	}
}
