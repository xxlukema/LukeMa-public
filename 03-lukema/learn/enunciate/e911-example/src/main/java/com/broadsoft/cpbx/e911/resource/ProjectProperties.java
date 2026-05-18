package com.broadsoft.cpbx.e911.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProjectProperties {

	private static final Properties properties = new Properties();
	
	private static ProjectProperties instance = null;
	
	private ProjectProperties() {
		final InputStream propertiesStream = ProjectProperties.class.getResourceAsStream("/project.properties");
		try {
			properties.load(propertiesStream);
		} catch (IOException e) {
			throw new RuntimeException("Unable to read the project.properties file, this is a problem");
		}
	}
	
	public static ProjectProperties getInstance() {
		if (instance == null) {
			instance = new ProjectProperties();
		}
		return instance;
	}
	
	public String getAddressChangeStatusUrl() {
		return properties.getProperty("address_change_status_url");
	}
	
	public String getAddressChangeRequestUrl() {
		return properties.getProperty("address_change_request_url");
	}
	
	public String getRestResourceUrlTemplate() {
		return properties.getProperty("rest_resources_url");
	}
	
	
}
