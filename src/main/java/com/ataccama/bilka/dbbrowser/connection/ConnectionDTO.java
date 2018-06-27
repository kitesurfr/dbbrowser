package com.ataccama.bilka.dbbrowser.connection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ConnectionDTO {

	@NotNull
	private String name;
	
	@NotNull
	private String hostname;
	
	@NotNull
	@Min(value = 1L, message = "The value must be positive")
	private Integer port;
	
	@NotNull
	private String databaseName;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	
	public ConnectionDTO() {
	}
	
	public Connection toConnection() {
		Connection connection = new Connection();
		connection.setName(name);
		connection.setHostname(hostname);
		connection.setPort(port);
		connection.setDatabaseName(databaseName);
		connection.setUsername(username);
		connection.setPassword(password);
		return connection;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
