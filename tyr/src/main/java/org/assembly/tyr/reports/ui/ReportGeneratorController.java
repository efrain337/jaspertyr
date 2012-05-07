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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.assembly.tyr.reports.dao.ExampleDao;
import org.assembly.tyr.reports.dao.ReportDao;
import org.assembly.tyr.reports.model.Example;
import org.assembly.tyr.reports.model.Parameter;
import org.assembly.tyr.reports.model.Report;

public class ReportGeneratorController {
	
	private ReportDao reportDao = new ReportDao();
	private Long reportId;
	private List<Parameter> parameters;
	private Map<String, Object> parameterValues = new HashMap<String, Object>();
	
	private ExampleDao exampleDao = new ExampleDao();
	
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

	public Collection<Example> getExample() {
		return this.exampleDao.getAll();
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
	
	public void generateReportWithList(ActionEvent event) throws IOException {
		//Buscamos el contexto de jsf
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		
		ServletOutputStream servletOutputStream = response.getOutputStream();		
		
		// Definimos cual sera nuestra fuente de datos  
        JRBeanCollectionDataSource ds =new JRBeanCollectionDataSource(this.getExample());  
        try {
        	// Recuperamos el fichero fuente 
        	InputStream reportStream = facesContext.getExternalContext()
    				.getResourceAsStream("/resources/reports/reportWithList.jrxml");
        	JasperDesign jd=JRXmlLoader.load(reportStream);  
        	// Compilamos el informe jrxml  
        	JasperReport report = JasperCompileManager.compileReport(jd);  
        	// Rellenamos el informe con la conexion creada y sus parametros establecidos  
			JasperPrint print = JasperFillManager.fillReport(report,new HashMap(), ds);
			
			JasperExportManager.exportReportToPdfStream(print, servletOutputStream); 
			
			servletOutputStream.flush();
			servletOutputStream.close();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
}
