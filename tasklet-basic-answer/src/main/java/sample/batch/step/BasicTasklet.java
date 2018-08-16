package sample.batch.step;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import sample.business.domain.Product;

@Component
@Scope("step")
public class BasicTasklet implements Tasklet {

	@Autowired
	private ResourceAwareItemReaderItemStream<Product> productItemReader;

	@Autowired
	private JdbcBatchItemWriter<Product> productItemWriter;

	public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
		List<Product> productList = new ArrayList<Product>();
		try {
			productItemReader.open(chunkContext.getStepContext().getStepExecution().getExecutionContext());
			Product product;
			while ((product = productItemReader.read()) != null) {
				productList.add(product);
			}
			productItemWriter.write(productList);
		} finally {
			productItemReader.close();
		}
		return RepeatStatus.FINISHED;
	}
}
