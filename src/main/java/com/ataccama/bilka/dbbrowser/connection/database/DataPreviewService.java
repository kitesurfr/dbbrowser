package com.ataccama.bilka.dbbrowser.connection.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ataccama.bilka.dbbrowser.connection.Connection;

/**
 * 
 * Class to retrieved preview (first 100 records) for a given table and connection.
 *
 */
@Service
public class DataPreviewService {
	
	/**
	 * These data types will not be presented in a preview as they might be to large for to display.
	 */
	private static Set<String> largeDataTypes = new HashSet<>();
	
	static {
		largeDataTypes.add("CLOB");
		largeDataTypes.add("BLOB");
		largeDataTypes.add("LOB");
		largeDataTypes.add("BINARY");
		largeDataTypes.add("VARBINARY");
		largeDataTypes.add("LONGVARBINARY");
		largeDataTypes.add("ARRAY");
		largeDataTypes.add("REF");
		largeDataTypes.add("STRUCT");
		largeDataTypes.add("JAVA OBJECT");
	}
	
	@Autowired
	private DatabaseMetadataService databaseMetadataService;
	
	
	/**
	 * Gets a preview (first 100 records) for give table and {@link Connection}.
	 * 
	 * @param connection
	 * @param table
	 * @return
	 */
	public DataPreviewDTO getDataPreviewForTable(Connection connection, String table) {
		
		List<String> tables = databaseMetadataService.getTables(connection);
		
		//simple protection from SQL injection
		if (tables.stream().noneMatch(tableName -> tableName.equals(table))) {
			return new DataPreviewDTO();
		}
		
		ResultSet resultSet = null;
		List<List<String>> data = new ArrayList<>();
		List<String> columns = null;

		DataSource datasource = DataSourceBuilderHelper.getDatasource(connection);
		DatabaseMetaData databaseMetaData = databaseMetadataService.getDatabaseMetaData(datasource);
		
		try {
				java.sql.Statement stmt = databaseMetaData.getConnection().createStatement();
				stmt.setFetchSize(25);
				
				//revisit and make a statement with parameters
				String query = "SELECT * FROM " + table + " LIMIT 100";
				
				resultSet = stmt.executeQuery(query);
				columns = retrieveData(resultSet, data);
				
			} catch (SQLException e) {
				e.printStackTrace();
				return new DataPreviewDTO();
			} finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (databaseMetaData != null) {
						databaseMetaData.getConnection().close();
					}
					if (datasource instanceof Closeable) {
						((Closeable) datasource).close();
					}
				} catch (SQLException | IOException e) {
					e.printStackTrace();
					return new DataPreviewDTO();
				}
			}
			
		
		return new DataPreviewDTO(data, columns, table);
	}
	
	
	private List<String> retrieveData(ResultSet rs, List<List<String>> table) throws SQLException {
		
        if (rs == null) {
        	return Collections.emptyList();
        }

        ResultSetMetaData rsmd = rs.getMetaData();
        int NumOfCol = rsmd.getColumnCount();

        List<String> columns = new ArrayList<>();
        
        boolean columnsCollected = false;
        while (rs.next()){
        	List<String> row = new ArrayList<>();
        	
            for(int i = 1; i <= NumOfCol; i++){
            	
            	if (!columnsCollected) {
            		columns.add(rsmd.getColumnName(i));
            	}
            	
            	String columnTypeName = rsmd.getColumnTypeName(i);
            	
            	if (largeDataTypes.contains(columnTypeName)) {
            		row.add(columnTypeName);
            	} else {
            		Object object = rs.getObject(i);
            		row.add(object != null ? object.toString() : "null");
            	}
            }
            table.add(row);
            columnsCollected = true;
        }
        
        return columns;
	} 

}
