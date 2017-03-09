package com.example.batch.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.annotation.AfterChunk;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.stereotype.Component;

@Component
public class SampleChunkListener {

	private static final Logger log = LoggerFactory.getLogger(JobCompletedListener.class);
	
	@BeforeChunk
	public void beforeChunk() {
		log.info("*** before Chunk");
	}
	
	@AfterChunk
	public void afterChunk() {
		log.info("*** after Chunk");
	}
}
