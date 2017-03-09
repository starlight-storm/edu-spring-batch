package com.example.batch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.business.domain.Product;

@Component
public class JobCompletedListener extends JobExecutionListenerSupport {
	private static final Logger log = LoggerFactory.getLogger(JobCompletedListener.class);
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public JobCompletedListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("--- JOB 完了");
			List<Product> results = jdbcTemplate.query("SELECT NAME, PRICE FROM PRODUCT", new RowMapper<Product>() {
				@Override
				public Product mapRow(ResultSet rs, int row) throws SQLException {
					Product product = new Product();
					product.setName(rs.getString(1));
					product.setPrice(rs.getInt(2));
					return product;
				}
			});

			for (Product product : results) {
				log.info("---- テーブルに登録できたよ ：" + product);
			}
		}
	}
}