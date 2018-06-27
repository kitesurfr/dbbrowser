package com.ataccama.bilka.dbbrowser.connection.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.ataccama.bilka.dbbrowser.connection.Connection;
import com.ataccama.bilka.dbbrowser.connection.ConnectionController;

public class TableResourceAssembler extends ResourceAssemblerSupport<String, TableResource > {

	private Connection connection;

	public TableResourceAssembler(Connection connection) {
		super(ConnectionController.class, TableResource.class);
		this.connection = connection;
	}

	@Override
	public TableResource toResource(String table) {
		
		TableResource tableResource = instantiateResource(table);
		
		Link selfRel = linkTo(ConnectionController.class, new Object[0]).slash(connection.getId()).slash("tables").slash(table).withSelfRel();
		tableResource.add(selfRel);
		
		return tableResource;
	}
	
	@Override
	protected TableResource instantiateResource(String table) {
		return new TableResource(table);
	}

}
