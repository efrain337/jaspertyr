package org.assembly.tyr.reports.model;

import java.util.Date;

/**
 * @author emanuel
 *
 */
public class Example {

	private Long id;
	
	private String exampleString;
	
	private Date exampleDate;
	
	private Long exampleLong;

	public Example() {	}
	
	public Example(Long id, String exampleString, Date exampleDate,
			Long exampleLong) {
		super();
		this.id = id;
		this.exampleString = exampleString;
		this.exampleDate = exampleDate;
		this.exampleLong = exampleLong;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExampleString() {
		return exampleString;
	}

	public void setExampleString(String exampleString) {
		this.exampleString = exampleString;
	}

	public Date getExampleDate() {
		return exampleDate;
	}

	public void setExampleDate(Date exampleDate) {
		this.exampleDate = exampleDate;
	}

	public Long getExampleLong() {
		return exampleLong;
	}

	public void setExampleLong(Long exampleLong) {
		this.exampleLong = exampleLong;
	}
	
	
}
