package com.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.batch.listener.JobCompletedListener;
import com.example.batch.listener.SampleChunkListener;
import com.example.batch.listener.SampleStepExecutionListener;
import com.example.business.domain.Product;

@Configuration
public class BatchConfig {


	@Autowired
	public DataSource dataSource;

	@Autowired
	JobCompletedListener jobCompletedListener;

	@Autowired
	SampleStepExecutionListener sampleStepExecutionListener;

	@Autowired
	SampleChunkListener sampleChunkListener;



	@Bean
	Job job1(JobRepository jobRepository, Step step) {
		return new JobBuilder("job1", jobRepository)
				.listener(jobCompletedListener)
				.start(step)
				.build();
	}


	@Bean
	Step step1(JobRepository jobRepository, PlatformTransactionManager tm) {
		return new StepBuilder("step1", jobRepository)
				.<Product, Product>chunk(1, tm)
				.reader(reader())
				.processor(processor())
				.writer(writer())
				.listener(sampleStepExecutionListener)
				.faultTolerant()
				.skipLimit(10)
				.skip(ValidationException.class)
				.listener(sampleChunkListener)
				.build();
	}

	@Bean
	public ItemReader<Product> reader() {
		FlatFileItemReader<Product> reader = new FlatFileItemReader<Product>();
		reader.setLinesToSkip(1);
		reader.setResource(new ClassPathResource("/product_csv/1.csv"));
		reader.setLineMapper(lineMapper());
		return reader;
	}

	@Bean
	public LineMapper<Product> lineMapper() {
		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames(new String[] { "name", "price" });
		BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
		fieldSetMapper.setTargetType(Product.class);
		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);
		return lineMapper;
	}

	@Bean
	public ItemProcessor<Product, Product> processor() {
		SpringValidator<Product> springValidator = new SpringValidator<Product>();
		springValidator.setValidator(validator());
		ValidatingItemProcessor<Product> processor = new ValidatingItemProcessor<>();
		processor.setValidator(springValidator);
		return processor;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		return validator;
	}

	@Bean
	public JdbcBatchItemWriter<Product> writer() {
		JdbcBatchItemWriter<Product> writer = new JdbcBatchItemWriter<Product>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
		writer.setSql("insert into product (name, price) values(:name, :price)");
		writer.setDataSource(dataSource);
		return writer;
	}
}
