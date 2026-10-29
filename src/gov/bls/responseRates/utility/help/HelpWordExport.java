package gov.bls.responseRates.utility.help;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.bls.platform.log.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;
import java.util.HashMap;

import java.io.*;
/**
 * Servlet implementation class SnapshotExcelExport
 */
public class HelpWordExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final static ILog logger = LogFactory.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelpWordExport() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
	      	logger.info("HelpWordExport servlet entering.");       	  

	      	String file = request.getSession().getServletContext().getRealPath("/")+"/"+"Help_Glossary.docx";			
	        
	        ServletOutputStream stream = null;
	        BufferedInputStream buf = null;

	        try {
	        	stream = response.getOutputStream();
	        	File doc = new File(file);
	            
	        	response.setContentType("application/msword");
	        	response.addHeader("Content-Disposition", "attachment; filename=Help_Glossary.docx");    
	        	response.setContentLength((int) doc.length());
	        	
	        	FileInputStream input = new FileInputStream(doc);
	        	buf = new BufferedInputStream(input);
	        	
	        	int readBytes = 0;
	        	while ((readBytes = buf.read()) != -1)
	        		stream.write(readBytes);
	        	
	        } catch (IOException ioe) {
		       	logger.error("HelpWordExport servlet error."+ioe);	        	
	        	throw new ServletException(ioe.getMessage());
	        } finally {
	          if (stream != null)
	            stream.close();
	          if (buf != null)
	            buf.close();
	        }

	       	logger.info("HelpWordExport servlet exiting.");       	  	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
