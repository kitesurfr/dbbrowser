package com.ataccama.bilka.dbbrowser.connection.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.ataccama.bilka.dbbrowser.connection.Connection;

@Relation(collectionRelation = "connections")
public class ConnectionResource extends ResourceSupport {

	private String name;
	private String hostname;
	private Integer port;

	private String databaseName;
	
	private String username;
	private String password;
	
	public ConnectionResource() {
	}
	
	public ConnectionResource(Connection connection) {
		this.name = connection.getName();
		this.hostname = connection.getHostname();
		this.port = connection.getPort();
		this.databaseName = connection.getDatabaseName();
		this.username = connection.getUsername();
		this.password = connection.getPassword();
	}

	public String getName() {
		return name;
	}


	public String getHostname() {
		return hostname;
	}


	public Integer getPort() {
		return port;
	}


	public String getDatabaseName() {
		return databaseName;
	}


	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}
	
	
	
}
