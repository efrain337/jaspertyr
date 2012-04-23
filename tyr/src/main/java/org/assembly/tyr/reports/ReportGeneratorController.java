package org.assembly.tyr.reports;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

public class ReportGeneratorController {
	
	public void generateReport(ActionEvent actionEvent)
			throws ClassNotFoundException, SQLException, IOException,
			JRException {
		Connection connection;
		
		//Buscamos el contexto de jsf
		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext
				.getExternalContext().getResponse();
		//Con el contexto buscamos el jasper
		InputStream reportStream = facesContext.getExternalContext()
				.getResourceAsStream("/resources/reports/reporte.jasper");
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		//Nos conectamos a la base de datos (creamos una coneccion)
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager
				.getConnection("jdbc:mysql://localhost:3306/holaMundo?"
						+ "user=root&password=");
		facesContext.responseComplete();
		//seteamos el contentType
		response.setContentType("application/pdf");
		
		//ejecutamos el reporte
		JasperRunManager.runReportToPdfStream(reportStream,
				servletOutputStream, new HashMap(), connection);
		// Cerramos la coneccion a la Base
		connection.close();
		// flush y close del reporte
		servletOutputStream.flush();
		servletOutputStream.close();
	}
}
