package com.ataccama.bilka.dbbrowser.connection.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.ataccama.bilka.dbbrowser.connection.Connection;
import com.ataccama.bilka.dbbrowser.connection.ConnectionController;

public class ConnectionResourceAssembler extends ResourceAssemblerSupport<Connection, ConnectionResource > {

	public ConnectionResourceAssembler() {
		super(ConnectionController.class, ConnectionResource.class);
	}

	@Override
	public ConnectionResource toResource(Connection connection) {
		ConnectionResource createResourceWithId = createResourceWithId(connection.getId(), connection);
		
		createResourceWithId.add(linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash("tables")
				.withRel("tables"));
		
		return createResourceWithId;
	}
	
	@Override
	protected ConnectionResource instantiateResource(Connection connection) {
		return new ConnectionResource(connection);
	}

}
