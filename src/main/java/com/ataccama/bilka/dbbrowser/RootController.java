package com.ataccama.bilka.dbbrowser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ataccama.bilka.dbbrowser.connection.ConnectionController;
import com.ataccama.bilka.dbbrowser.connection.resource.ConnectionResource;


/**
 * Simple forward controller for a root url query. 
 */
@RestController
@RequestMapping(path="/")
@EnableHypermediaSupport(type = { HypermediaType.HAL })
public class RootController {

	
	@Autowired
	private ConnectionController connectionController;
	
	/**
	 * Forwards to main {@link ConnectionController}}
	 * @return
	 */
	@GetMapping("/")
	public  ResponseEntity<Resources<ConnectionResource>> getAllConnections() {
		return connectionController.getAllConnections();
	}
	
}
