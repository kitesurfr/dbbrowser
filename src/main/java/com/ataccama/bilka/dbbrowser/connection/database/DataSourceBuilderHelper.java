package com.ataccama.bilka.dbbrowser.connection.database;

import javax.sql.DataSource;

import org.springframework.boot.jdbc.DataSourceBuilder;

import com.ataccama.bilka.dbbrowser.connection.Connection;

/**
 * 
 * Helper class to construct a {@link DataSource}} object
 *
 * Needs to be revisited as the underlying dataSource for spring boot is a pooled {@ HikariDataSource} that needs an extra care 
 * closing the pools after each use. 
 */
public class DataSourceBuilderHelper {

	
	/**
	 * Constructs {@link DataSource} for given {@link Connection}.
	 * 
	 * @param connection
	 * @return
	 */
	public static DataSource getDatasource(Connection connection) {
		
		DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
		dataSourceBuilder.url(getUrl(connection));
		dataSourceBuilder.username(connection.getUsername());
		dataSourceBuilder.password(connection.getPassword());
		dataSourceBuilder.driverClassName("com.mysql.jdbc.Driver");
		
		return dataSourceBuilder.build();
	}

	/**
	 * Constructs a jdbc url connection for MySql database.
	 * 
	 * @param connection
	 * @return
	 */
	public static String getUrl(Connection connection) {
		
		String hostname = connection.getHostname();
		Integer port = connection.getPort();
		String databaseName = connection.getDatabaseName();
		
		String url = "jdbc:mysql://" + hostname + ":" + port + "/" + databaseName;
		return url;
	}
	
}
