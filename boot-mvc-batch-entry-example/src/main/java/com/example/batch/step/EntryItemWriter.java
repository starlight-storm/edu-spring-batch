package com.example.batch.step;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("itemWriter")
public class EntryItemWriter implements ItemWriter<Object> {


	@Override
	public void write(Chunk<? extends Object> chunk) throws Exception {
		System.out.println(chunk);

	}
}
