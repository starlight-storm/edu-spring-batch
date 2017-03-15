package com.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.SpringValidator;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.batch.listener.JobCompletedListener;
import com.example.batch.listener.SampleChunkListener;
import com.example.batch.listener.SampleStepExecutionListener;
import com.example.business.domain.Product;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;

	@Autowired
	JobCompletedListener jobCompletedListener;

	@Autowired
	SampleStepExecutionListener sampleStepExecutionListener;
	
	@Autowired
	SampleChunkListener sampleChunkListener;
	
	@Bean
	public Job job1() {
		// TODO:演習8 必要な記述を加えなさい
		return null;
	}

	@SuppressWarnings("unchecked")
	@Bean
	public Step step1() {
		// TODO:演習8 必要な記述を加えなさい
		return null;
	}
	
	@Bean
	public ItemReader<Product> reader() {
		// TODO:演習8 必要な記述を加えなさい
		return null;
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
