package org.assembly.tyr.reports.ui;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import org.assembly.tyr.reports.dao.ReportDao;
import org.assembly.tyr.reports.model.Parameter;
import org.assembly.tyr.reports.model.Report;

public class ReportGeneratorController {
	
	private ReportDao reportDao = new ReportDao();
	private Long reportId;
	private List<Parameter> parameters;
	private Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	public List<SelectItem> getReportList() {
		List<SelectItem> result = new ArrayList<SelectItem>();
		Collection<Report> reports = reportDao.getAll();
		for (Report report : reports) {
			SelectItem item = new SelectItem(report.getId(), report.getNombre());
			result.add(item);
		}
		return result;
	}
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public List<Parameter> getParameters() {
		if ( reportId!= null) {
			Report report = this.reportDao.get(reportId);
			parameters = report.getParameters();
		}
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	public Map<String, Object> getParameterValues() {
		return parameterValues;
	}

	public void setParameterValues(Map<String, Object> parameterValues) {
		this.parameterValues = parameterValues;
	}
	
	public Object getParameterValue(String key) {
		return this.parameterValues.get(key);
	}

	public String generateReportSubmit()
			throws ClassNotFoundException, SQLException, IOException,
			JRException {
		Connection connection;
		
		if (parameters != null) {
			for (Parameter parameter: parameters) {
				parameterValues.put(parameter.getNombre(), parameter.getValue());
			}
		}
		
		Report report = this.reportDao.get(reportId);
		
		//Buscamos el contexto de jsf
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		//Con el contexto buscamos el jasper
		InputStream reportStream = facesContext.getExternalContext()
				.getResourceAsStream(report.getFileName());
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		//Nos conectamos a la base de datos (creamos una coneccion)
		Class.forName(report.getDriver());
		connection = DriverManager
					.getConnection(report.getUrlConnection());
		facesContext.responseComplete();
		//seteamos el contentType
		response.setContentType("application/pdf");
		
		//ejecutamos el reporte
		JasperRunManager.runReportToPdfStream(reportStream,
				servletOutputStream, this.parameterValues, connection);
		// Cerramos la coneccion a la Base
		connection.close();
		// flush y close del reporte
		servletOutputStream.flush();
		servletOutputStream.close();
		return null;
	}
	
	public void setParam(ActionEvent event) {
		event.getComponent().getId();

	}
}
