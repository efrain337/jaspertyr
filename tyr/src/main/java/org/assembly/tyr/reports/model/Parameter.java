/**
 * 
 */
package org.assembly.tyr.reports.model;

/**
 * @author emanuel
 *
 */
public class Parameter {
	
	private String nombre;
	
	private Type type;
	
	private Object value;
	
	public Parameter(String nombre, Type type) {
		super();
		this.nombre = nombre;
		this.type = type;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Type getType() {
		return type;
	}
	
	public String getTypeStr() {
		return type.name();
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
