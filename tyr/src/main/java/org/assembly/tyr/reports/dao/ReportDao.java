/**
 * 
 */
package org.assembly.tyr.reports.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assembly.tyr.reports.model.Parameter;
import org.assembly.tyr.reports.model.Report;
import org.assembly.tyr.reports.model.Type;

/**
 * @author emanuel
 *
 */
public class ReportDao {
	
	private Map<Long, Report> reports = new HashMap<Long, Report>();
	
	public ReportDao() {
		List<Parameter> parameter1 = new ArrayList<Parameter>();
		Report report1 = new Report(1l,"hola1", parameter1, "/resources/reports/reporte.jasper", "jdbc:mysql://localhost:3306/holaMundo?user=root&password=", "com.mysql.jdbc.Driver");
		reports.put(1l, report1);
		
		List<Parameter> parameter2 = Arrays.asList(new Parameter("prueba1", Type.NUMBER), new Parameter("prueba2", Type.STRING), new Parameter("prueba3", Type.DATE));
		Report report2 = new Report(2l,"hola2", parameter2, "/resources/reports/reporte2.jasper", "jdbc:mysql://localhost:3306/holaMundo?user=root&password=", "com.mysql.jdbc.Driver");
		reports.put(2l, report2);
	}
	
	public Collection<Report> getAll() {
		return reports.values();
	}
	
	public Report get(Long id) {
		return reports.get(id);
	}

}
