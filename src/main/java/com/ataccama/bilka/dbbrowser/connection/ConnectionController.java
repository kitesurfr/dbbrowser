package com.ataccama.bilka.dbbrowser.connection;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ataccama.bilka.dbbrowser.connection.database.ColumnDTO;
import com.ataccama.bilka.dbbrowser.connection.database.DataPreviewDTO;
import com.ataccama.bilka.dbbrowser.connection.database.DataPreviewService;
import com.ataccama.bilka.dbbrowser.connection.database.DatabaseMetadataService;
import com.ataccama.bilka.dbbrowser.connection.resource.ColumnResource;
import com.ataccama.bilka.dbbrowser.connection.resource.ColumnResourceAssembler;
import com.ataccama.bilka.dbbrowser.connection.resource.ConnectionResource;
import com.ataccama.bilka.dbbrowser.connection.resource.ConnectionResourceAssembler;
import com.ataccama.bilka.dbbrowser.connection.resource.TableResource;
import com.ataccama.bilka.dbbrowser.connection.resource.TableResourceAssembler;


/**
 *  Main controller to handle all {@link Connection} related operations
 *
 */
@RestController
@RequestMapping(path="/connection")
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class ConnectionController {

	private static final String TABLES = "tables";

	@Autowired
	private ConnectionService connectionService;
	
	@Autowired
	private DatabaseMetadataService databaseMetadataService;
	
	@Autowired
	private DataPreviewService dataPreviewService;
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ConnectionResource> getConnection(@PathVariable Long id) {
		Connection connection = connectionService.getConnection(id);
		ConnectionResource resource = new ConnectionResourceAssembler().toResource(connection);
		return ResponseEntity.ok(resource);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateConnection(@PathVariable Long id, @Validated @RequestBody ConnectionDTO connectionDTO) {
		connectionService.updateConnection(connectionDTO.toConnection(), id);
		return ResponseEntity.ok().body("Updated");
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public  ResponseEntity<String> delete(@PathVariable Long id) {
		connectionService.deleteConnection(id);
		return ResponseEntity.ok().body("Deleted");
	}
	
	@PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> addConnection(@Validated @RequestBody ConnectionDTO connectionDTO) {
		connectionService.addConnection(connectionDTO.toConnection());
		return ResponseEntity.ok().body("Created");
	}
	
	@GetMapping("/all")
	public  ResponseEntity<Resources<ConnectionResource>> getAllConnections() {
		
		Link selfRel = linkTo(methodOn(ConnectionController.class).getAllConnections()).withSelfRel();
		Link addConnection = linkTo(methodOn(ConnectionController.class).addConnection(null)).withRel("add");
		List<ConnectionResource> resources = new ConnectionResourceAssembler().toResources(connectionService.getAllConnections());
		
		return ResponseEntity.ok(new Resources<>(resources, selfRel, addConnection));
	}
	
	@GetMapping("/{id}/tables")
	public  ResponseEntity<Resources<TableResource>> getConnectionTables(@PathVariable Long id) {
			
		Connection connection = connectionService.getConnection(id);
		List<String> tables = databaseMetadataService.getTables(connection);
		List<TableResource> resources = new TableResourceAssembler(connection).toResources(tables);
		
		Link selfLink = linkTo(ConnectionController.class, new Object[0])
		.slash(connection.getId())
		.slash(TABLES)
		.withSelfRel();
		
		Link allConnectionsLink = linkTo(methodOn(ConnectionController.class).getAllConnections()).withRel("allConnections");
		return ResponseEntity.ok(new Resources<>(resources, selfLink, allConnectionsLink));
	}

	
	@GetMapping("/{id}/tables/{table}")
	public  ResponseEntity<Resources<ColumnResource>> getTableColumns(@PathVariable Long id, @PathVariable String table ) {
		
		Connection connection = connectionService.getConnection(id);
		List<ColumnDTO> columns = databaseMetadataService.getColumns(connection, table);
		List<ColumnResource> resources = new ColumnResourceAssembler(connection, table).toResources(columns);
		
		Link selfLink = linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash(TABLES)
				.slash(table)
				.withSelfRel();
		
		Link allTablesLink = linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash(TABLES)
				.withRel("allTables");
		
		Link dataLink = linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash(TABLES)
				.slash(table)
				.slash("data")
				.withRel("data");
		
		return ResponseEntity.ok(new Resources<>(resources, selfLink, allTablesLink, dataLink));
	}
	
	@GetMapping("/{id}/tables/{table}/data")
	public  ResponseEntity<Resource<DataPreviewDTO>> getTableData(@PathVariable Long id, @PathVariable String table ) {
		
		Connection connection = connectionService.getConnection(id);
		DataPreviewDTO dataPreviewForTable = dataPreviewService.getDataPreviewForTable(connection, table);
		
		
		Link selfLink = linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash(TABLES)
				.slash(table)
				.slash("data")
				.withSelfRel();
		
		Link allColumnsLink = linkTo(ConnectionController.class, new Object[0])
				.slash(connection.getId())
				.slash(TABLES)
				.slash(table)
				.withRel("allColumns");
		
		return ResponseEntity.ok(new Resource<>(dataPreviewForTable, selfLink, allColumnsLink));
	}
	
}
