package com.ataccama.bilka.dbbrowser.connection.database;

import java.io.Closeable;
import java.io.IOException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.stereotype.Service;

import com.ataccama.bilka.dbbrowser.connection.Connection;


/**
 * Class to retrieve basic metadata (tables. columns) for give connection {@link Connection}}
 * 
 */
@Service
public class DatabaseMetadataService {
	
	/**
	 * Get columns and associated basic metadata for given table and {@link Connection}.
	 * 
	 * @param connection
	 * @param table
	 * @return
	 */
	public List<ColumnDTO> getColumns(Connection connection, String table) {
		
		DataSource datasource = getDataSource(connection);	
		DatabaseMetaData databaseMetaData = getDatabaseMetaData(datasource);
		ResultSet resultSet = null;
		List<ColumnDTO> columns = new ArrayList<>();
		
		try {
			resultSet = databaseMetaData.getColumns(null, null, table, null);

			while(resultSet.next()) {
				
				ColumnDTO columnDTO = new ColumnDTO();
				
				columnDTO.setColumnName(resultSet.getString("COLUMN_NAME"));
				columnDTO.setDataType(resultSet.getString("DATA_TYPE"));
				columnDTO.setTypeName(resultSet.getString("TYPE_NAME"));
				columnDTO.setIsAutoincrement(resultSet.getString("IS_AUTOINCREMENT"));
				columnDTO.setIsNullable(resultSet.getString("NULLABLE"));
				columnDTO.setColumnSize(resultSet.getString("COLUMN_SIZE"));
				columnDTO.setOrdinalPosition(resultSet.getString("ORDINAL_POSITION"));
				
				columns.add(columnDTO);
			}
			
			//revisit and isalote to private open/close block 
			resultSet = databaseMetaData.getPrimaryKeys(null, null, table);

		    while (resultSet.next()) {
		      String columnName = resultSet.getString("COLUMN_NAME");
		      columns.stream().filter( col -> col.getColumnName().equals(columnName)).forEach( col -> col.setPrimaryKey(true));
		    }

			
		} catch (SQLException e) {
			e.printStackTrace();
			return columns;
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
				return columns;
			}
		}
		
		return columns;

		
	}
	
	/**
	 * Get columns and associated basic metadata for given table and {@link Connection}.
	 * 
	 * @param connection
	 * @param table
	 * @return
	 */
	public List<String> getTables(Connection connection) {
		
		DataSource datasource = getDataSource(connection);	
		DatabaseMetaData databaseMetaData = getDatabaseMetaData(datasource);
		ResultSet resultSet = null;
		List<String> tables = new ArrayList<>();
		try {
			resultSet = databaseMetaData.getTables(null, null, null, new String[]{"TABLE"});
			
			while(resultSet.next()) {
				tables.add(resultSet.getString("TABLE_NAME"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return tables;
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
				return tables;
			}
		}
		
		return tables;
	}
	
	private DataSource getDataSource(Connection connection) {
		return DataSourceBuilderHelper.getDatasource(connection);
	}



	public DatabaseMetaData getDatabaseMetaData(DataSource dataSource) {
		
		DatabaseMetaData metaData = null;
		java.sql.Connection conn = null;
		
		try {
			conn = dataSource.getConnection();
			metaData = conn.getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();

				try {
					if (conn != null) {
						conn.close();
					}
					
					if (dataSource != null && dataSource instanceof Closeable){
						((Closeable)dataSource).close();
					}
					
				} catch (SQLException | IOException e1) {
					e1.printStackTrace();
					return metaData;
				}
			
		}
		
		return metaData;
	}
	
}
