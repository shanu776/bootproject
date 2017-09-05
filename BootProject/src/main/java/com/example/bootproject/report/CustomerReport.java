package com.example.bootproject.report;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bootproject.model.Customer;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class CustomerReport {

	public void genrateInvoice(List<Customer> model,HttpServletRequest request,HttpServletResponse resp){
		Map<String,Object> logo = new HashMap<>();
		logo.put("logo", request.getServletContext().getRealPath("jrxmlFile/invoice_logo.png"));
		File jrxmlfileloc = new File(request.getServletContext().getRealPath("jrxmlFile/Invoice.jrxml"));
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(model,false);
		try {
			InputStream streamfile = new FileInputStream(jrxmlfileloc);
			JasperReport jasperReport = JasperCompileManager.compileReport(streamfile);
			JasperPrint print = JasperFillManager.fillReport(jasperReport, logo, dataSource);
			File outputReport = new File(request.getServletContext().getRealPath("/report/customerInvoice.pdf"));			
			JasperExportManager.exportReportToPdfStream(print, new FileOutputStream(outputReport));
			//JasperPrintManager.printReport(print, true);
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
		
	}
	
	public void allCustomerReport(List<Customer> model,HttpServletRequest request,HttpServletResponse resp){
		File reportLocation = new File(request.getServletContext().getRealPath("report/AllCustomerReport.pdf"));
		JasperReportBuilder customerReport = DynamicReports.report();		
		customerReport.columns(
				Columns.column("ID", "id",DataTypes.integerType()),
				Columns.column("NAME","name",DataTypes.stringType()),
				Columns.column("EMAIL","email",DataTypes.stringType()),
				Columns.column("PHONE","phone",DataTypes.stringType())
				).title(Components.text("Customer Report")
						.setHorizontalAlignment(HorizontalAlignment.CENTER))
						.pageFooter(Components.pageXofY())
						.setDataSource(model);		
		try {
			//customerReport.show();
			customerReport.toPdf(new FileOutputStream(reportLocation));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}						
						
	}
}
