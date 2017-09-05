package com.example.bootproject.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bootproject.model.Product;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;

public class ProductReport {
	Connection con;
	public ProductReport(){
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/springbootdemo","root","admin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}

	public void getSingleReport(Map<String,Object> model,HttpServletRequest request,HttpServletResponse resp){
		try {			
			File jrxmlfile = new File(request.getServletContext().getRealPath("/jrxmlFile/productReport.jrxml"));
			InputStream streamFile = new FileInputStream(jrxmlfile);
			JasperReport jasperReport = JasperCompileManager.compileReport(streamFile);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, model,con);
			
			
			File outputReport = new File(request.getServletContext().getRealPath("/report/productReport.pdf"));
			
			
			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(outputReport));
			 JRPdfExporter exporter = new JRPdfExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print); 
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, resp.getOutputStream()); 
		        resp.setContentType("application/pdf");
		        exporter.exportReport();
			System.out.println("reportGenrated");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void getAllProductReport(HttpServletRequest request,HttpServletResponse resp,List<Product> model){
		
		try {			
			File jrxmlfile = new File(request.getServletContext().getRealPath("/jrxmlFile/allProductReport.jrxml"));
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(model,false);
			System.out.println(model.toString());
			InputStream streamFile = new FileInputStream(jrxmlfile);
			JasperReport jasperReport = JasperCompileManager.compileReport(streamFile);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, null,dataSource);
			//JasperViewer.viewReport(print, true);
			File outputReport = new File(request.getServletContext().getRealPath("/report/allProductReport.pdf"));			
			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(outputReport));
			JRPdfExporter exporter = new JRPdfExporter();
		        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print); 
		        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, resp.getOutputStream()); 
		        resp.setContentType("application/pdf");
		        exporter.exportReport();
			System.out.println("reportGenrated");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
