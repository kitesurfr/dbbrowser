package com.ataccama.bilka.dbbrowser.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *  Service to provide crud operations on a {@link ConnectionRepository}}
 *
 */
@Service
public class ConnectionService {

	@Autowired
	private ConnectionRepository connectionRepository;
	
	public Iterable<Connection> getAllConnections() {
		return connectionRepository.findAll();
	}
	
	public Connection getConnection(Long id) {
		return connectionRepository.findById(id).orElse(null);
	}
	
	
	public void deleteConnection(Long id) {
		connectionRepository.deleteById(id);
	}
	
	public void addConnection(Connection connection) {
		connectionRepository.save(connection);
	}
	
	
	public void updateConnection(Connection connection, Long id) {
		Connection connectionDb = getConnection(id);
		
		connectionDb.setDatabaseName(connection.getDatabaseName());
		connectionDb.setHostname(connection.getHostname());
		connectionDb.setPort(connection.getPort());
		connectionDb.setName(connection.getName());
		connectionDb.setUsername(connection.getUsername());
		
		if (!StringUtils.isEmpty(connection.getPassword())) {
			connectionDb.setPassword(connection.getPassword());
		}
		
		connectionRepository.save(connectionDb);
	}
	
	
}
