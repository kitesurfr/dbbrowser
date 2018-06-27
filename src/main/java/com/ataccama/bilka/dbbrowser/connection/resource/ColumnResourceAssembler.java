package com.ataccama.bilka.dbbrowser.connection.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.ataccama.bilka.dbbrowser.connection.Connection;
import com.ataccama.bilka.dbbrowser.connection.ConnectionController;
import com.ataccama.bilka.dbbrowser.connection.database.ColumnDTO;

public class ColumnResourceAssembler extends ResourceAssemblerSupport<ColumnDTO, ColumnResource > {

	private Connection connection;
	private String table;

	public ColumnResourceAssembler(Connection connection, String table) {
		super(ConnectionController.class, ColumnResource.class);
		this.connection = connection;
		this.table = table;
	}

	@Override
	public ColumnResource toResource(ColumnDTO column) {
		
		ColumnResource columnResource = instantiateResource(column);
		
		Link selfRel = linkTo(ConnectionController.class, new Object[0]).slash(connection.getId())
				.slash("tables")
				.slash(table)
				.slash(column)
				.withSelfRel();
		
		columnResource.add(selfRel);
		
		return columnResource;
	}
	
	@Override
	protected ColumnResource instantiateResource(ColumnDTO column) {
		return new ColumnResource(column);
	}

}
