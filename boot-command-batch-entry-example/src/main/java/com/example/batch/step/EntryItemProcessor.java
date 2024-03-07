package com.example.batch.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("itemProcessor")
public class EntryItemProcessor implements
		ItemProcessor<Object, String> {

	public String process(Object message)
			throws Exception {
		return message + "!!";
	}
}
