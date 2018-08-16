package com.example.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.stereotype.Component;

@Component
public class SampleStepExecutionListener {

	private static final Logger log = LoggerFactory.getLogger(SampleStepExecutionListener.class);

	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		log.info("*** Before Step :Start Time " + stepExecution.getStartTime());
	}

	@AfterStep
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("*** After Step :Commit Count " + stepExecution.getCommitCount());
		return ExitStatus.COMPLETED;
	}
}
