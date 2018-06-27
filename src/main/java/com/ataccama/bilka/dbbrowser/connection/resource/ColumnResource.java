package com.ataccama.bilka.dbbrowser.connection.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import com.ataccama.bilka.dbbrowser.connection.database.ColumnDTO;

@Relation(collectionRelation = "columns")
public class ColumnResource extends ResourceSupport {


	private String columnName;
	private String dataType;
	private String typeName;
	private String columnSize;
	private String isAutoincrement;
	private String isNullable;
	private String ordinalPosition;
	private String isPrimaryKey;
	
	public ColumnResource(ColumnDTO column) {
		this.columnName = column.getColumnName();
		this.dataType = column.getDataType();
		this.typeName = column.getTypeName();
		this.columnSize = column.getColumnName();
		this.isAutoincrement = column.getIsAutoincrement();
		this.isNullable = column.getIsNullable();
		this.ordinalPosition = column.getOrdinalPosition();
		this.isPrimaryKey = String.valueOf(column.isPrimaryKey());
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}

	public String getIsAutoincrement() {
		return isAutoincrement;
	}

	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public String getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(String ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(String isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	
}
