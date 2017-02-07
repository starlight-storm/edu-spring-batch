package sample.batch.step;

import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component("itemReader")
public class EntryItemReader implements ItemReader<String> {

	private String[] input = {"Hello World", "hoge", "こんにちわ。世界", null};

	private int index = 0;

	public String read() throws Exception {

		String message = input[index++];

		if(message == null) {
			return null;
		}

		// TODO: 演習2で例外処理を記述する
		
		return message;
	}
}
