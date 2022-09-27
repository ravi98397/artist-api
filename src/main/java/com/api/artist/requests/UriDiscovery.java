package com.api.artist.requests;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

@Component
public class UriDiscovery {
	
	@Autowired
	private DiscoveryClient discoveryClient;

	public URI getServiceURI(String serviceName) {
		List<ServiceInstance> uriList = discoveryClient.getInstances(serviceName);
		return uriList.get(0).getUri();
	}
	
}
