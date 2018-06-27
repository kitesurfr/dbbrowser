package com.ataccama.bilka.dbbrowser.connection.database;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataPreviewDTO {

	private List<List<String>> data = new ArrayList<>();

	private List<String> columns = new ArrayList<>();

	private String table;
	
	public DataPreviewDTO() {
	}
	
	
	public DataPreviewDTO(List<List<String>> data, List<String> columns, String table) {
		this.data = data;
		this.columns = columns;
		this.table = table;
	}

	public List<List<String>> getData() {
		return data;
	}

	public List<String> getColumns() {
		return columns;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
	
	
	
}
