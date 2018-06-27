package com.ataccama.bilka.dbbrowser.connection.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

@Relation(collectionRelation = "tables")
public class TableResource extends ResourceSupport {

	private String table;
		
	public TableResource(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
		
}
