/**
 * 
 */
package org.assembly.tyr.reports.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author emanuel
 *
 */
public class Report {
	
	private Long id;
	
	private String nombre;
	
	private List<Parameter> parameters = new ArrayList<Parameter>();
	
	private String fileName;
	
	private String urlConnection;
	
	private String driver;

	public Report() {
	}

	public Report(Long id, String nombre, List<Parameter> parameters,
			String fileName, String urlConnection, String driver) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.parameters = parameters;
		this.fileName = fileName;
		this.urlConnection = urlConnection;
		this.driver = driver;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUrlConnection() {
		return urlConnection;
	}

	public void setUrlConnection(String urlConnection) {
		this.urlConnection = urlConnection;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	
}
