package com.learn.spring;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.jdbc.support.JdbcUtils;

import com.learn.util.SpringApplicationContext;

public class SelectSpringTest {
	protected static final Logger LOG = Logger
			.getLogger(SelectSpringTest.class);

	protected static final String SQL_STAGE_SELECT = "select sysdate from dual";

	@Test
	public void runTest() throws Exception {
		LOG.info("Hello World!");

		DataSource dataSource = SpringApplicationContext.getBean("dataSource");

		Connection connection = null;
		PreparedStatement select = null;
		ResultSet resultSet = null;

		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);

			select = connection.prepareStatement(SQL_STAGE_SELECT);

			resultSet = select.executeQuery();

			while (resultSet.next()) {
				Date date = resultSet.getDate("sysdate");

				LOG.info("Date: " + date);
			}

			// connection.commit();
		} catch (Exception e) {
			connection.rollback();
			LOG.error("Unable to update table.", e);
		} finally {
			JdbcUtils.closeResultSet(resultSet);
			JdbcUtils.closeStatement(select);
			JdbcUtils.closeConnection(connection);
		}

		LOG.info("Completed.");
	}

}
