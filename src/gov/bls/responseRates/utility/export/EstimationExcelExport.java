package gov.bls.responseRates.utility.export;

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
public class EstimationExcelExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final static ILog logger = LogFactory.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EstimationExcelExport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
	      	logger.info("SnapshotExportExcelAction servlet entering.");       	  
	      	
	      	//Unit test that the data was sent.	      	
	      	logger.info("Test data data " + request.getParameter("data") );
	      	logger.info("Test data period" + request.getParameter("period") );
	
	    	//Workbook wb = new HSSFWorkbook();
	      	Workbook wb = new XSSFWorkbook();
	      	
	      	
	        Map<String, CellStyle> styles = createStyles(wb);

	        Sheet sheet = wb.createSheet("Estimation");
	        
	        sheet.setColumnWidth(0,4000);
	        sheet.setColumnWidth(1,3500);
	        sheet.setColumnWidth(2,3500);
	        sheet.setColumnWidth(3,3500);

	        
	        PrintSetup printSetup = sheet.getPrintSetup();
	        printSetup.setLandscape(true);
	        sheet.setFitToPage(true);
	        sheet.setHorizontallyCenter(true);

	        
	    	Row titleRow = sheet.createRow(0);
	        titleRow.setHeightInPoints(45);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Estimation Report");
	        titleCell.setCellStyle(styles.get("title"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$D$1"));

	        //Parms area.	        
	        Row parmRow2 = sheet.createRow(2);  
	        Cell periodCell = parmRow2.createCell(0);
	        periodCell.setCellValue("Period: " + request.getParameter("period"));
	        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$2:$B$2"));
	         
	        //Create the report header table header row.
	        Row tableHeaderRow = sheet.createRow(4);
	        
	        Cell headerCell0 = tableHeaderRow.createCell(0);
	        Cell headerCell1 = tableHeaderRow.createCell(1);
	        Cell headerCell2 = tableHeaderRow.createCell(2);
	        Cell headerCell3 = tableHeaderRow.createCell(3);       
	        
	        headerCell0.setCellValue("Data Type");
	        headerCell1.setCellValue("Period");	        
	        headerCell2.setCellValue("Weighted");
	        headerCell3.setCellValue("Unwt Usable Ratio");
        
	        headerCell0.setCellStyle(styles.get("header"));
	        headerCell1.setCellStyle(styles.get("header"));      
	        headerCell2.setCellStyle(styles.get("header"));
	        headerCell3.setCellStyle(styles.get("header"));

	        //Data area.
	        String data = request.getParameter("data");
	        String delims = "[*]";
	        String[] tokens = data.split(delims);
	        
	        logger.info("tokens is " + tokens);
	        
	        logger.info("tokens length " + tokens.length);
	        
        	int row_excel = 5;	
        	
	        for(int i=0; i <= tokens.length-4; i=i+4){
	        	Row row = sheet.createRow(row_excel);
	        	
	        	row_excel = row_excel+1;
	        	
		        logger.info("tokens is " + tokens[i+0]);
		        logger.info("tokens is " + tokens[i+1]);
		        logger.info("tokens is " + tokens[i+2]);
		        logger.info("tokens is " + tokens[i+3]);
		        
		        
	        	row.createCell(0).setCellValue(tokens[i+0]);
        		row.getCell(0).setCellStyle(styles.get("cell_text"));            		
        		row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);     
        		
	        	row.createCell(1).setCellValue(new Integer(tokens[i+1]));
        		row.getCell(1).setCellStyle(styles.get("cell_integer"));       
        		row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);    
        		
	        	row.createCell(2).setCellValue(new Double(tokens[i+2]));
        		row.getCell(2).setCellStyle(styles.get("cell_double"));       
        		row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);    
    		
	        	row.createCell(3).setCellValue(new Double(tokens[i+3]));
        		row.getCell(3).setCellStyle(styles.get("cell_double"));       
        		row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);    
        		
	        }
	        
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename=\"Estimation.xlsx\"");
            response.addHeader("Content-description", "Estimation Report");
            ServletOutputStream stream = response.getOutputStream();
              
            wb.write(stream);
            
            //the flush puts the bytes on the buffer into the stream for return to client.
            stream.flush();
            stream.close();
              
	      //wb.write(out);
	      logger.info("Writing Excel Report");      
	      //response.setContentType("application/vnd.ms-excel");
	      //response.setContentType("text/html");
	      //response.setHeader("Content-disposition", "attachment;filename=Snapshot.xls");
	      //OutputStream out2 = response.getOutputStream();
	     //ByteArrayOutputStream out3 = new ByteArrayOutputStream();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	private static Map<String, CellStyle> createStyles(Workbook wb){
	        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
	        CellStyle style;
	        Font titleFont = wb.createFont();
	        titleFont.setFontHeightInPoints((short)18);
	        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFont(titleFont);
	        styles.put("title", style);

	        Font monthFont = wb.createFont();
	        monthFont.setFontHeightInPoints((short)11);
	        monthFont.setColor(IndexedColors.WHITE.getIndex());
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setFont(monthFont);
	        style.setWrapText(true);
	        styles.put("header", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_LEFT);
	        style.setWrapText(true);
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(CellStyle.BORDER_THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setIndention((short)1);
	        styles.put("cell_text", style);
	        
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_RIGHT);
	        style.setWrapText(true);
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(CellStyle.BORDER_THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setIndention((short)1);
	        styles.put("cell_integer", style);
	        
	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_RIGHT);
	        style.setWrapText(true);
	        style.setBorderRight(CellStyle.BORDER_THIN);
	        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderLeft(CellStyle.BORDER_THIN);
	        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderTop(CellStyle.BORDER_THIN);
	        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
	        style.setBorderBottom(CellStyle.BORDER_THIN);
	        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        style.setIndention((short)1);
	        styles.put("cell_double", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        styles.put("formula", style);

	        style = wb.createCellStyle();
	        style.setAlignment(CellStyle.ALIGN_CENTER);
	        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
	        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
	        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
	        styles.put("formula_2", style);

	        return styles;
	    }

	
	

}
