package com.example.batch.step;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("itemReader")
@StepScope
public class EntryItemReader implements ItemReader<String> {

	private String[] input = { "Hello World", "こんにちわ。世界", null };
	private int index = 0;

	@Value("#{jobParameters['data']}")
	private String data;

	public String read() throws Exception {
		String message = input[index++];

		if (message == null) {
			return null;
		}
		return data + message;
	}
}
