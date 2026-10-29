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
public class MonthlyFinalExcelExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final static ILog logger = LogFactory.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthlyFinalExcelExport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
      	logger.info("MonthlyFinalExportExcelAction servlet entering.");       	  
      	
      	//Unit test that the data was sent.	      	
        String report_title = "Monthly Final " + request.getParameter("type") + " Report";
   	 
    	//Workbook wb = new HSSFWorkbook();
        Workbook wb = new XSSFWorkbook();
      	  
        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet(report_title);
        
        sheet.setColumnWidth(0,2600);
        sheet.setColumnWidth(1,2600);
        sheet.setColumnWidth(2,2600);
        sheet.setColumnWidth(3,2600);
        sheet.setColumnWidth(4,2600);
        sheet.setColumnWidth(5,2600);
        sheet.setColumnWidth(6,2600);
        sheet.setColumnWidth(7,2600);
        sheet.setColumnWidth(8,2600);
        sheet.setColumnWidth(9,2600);
        sheet.setColumnWidth(10,2600);
        sheet.setColumnWidth(11,2700);
        
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

    	Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        
   
        titleCell.setCellValue(report_title);
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$L$1"));

        //Create the report header table header row.
        Row tableHeaderRow = sheet.createRow(2);
        
        Cell headerCell0 = tableHeaderRow.createCell(0);
        Cell headerCell1 = tableHeaderRow.createCell(1);
        Cell headerCell2 = tableHeaderRow.createCell(2);
        Cell headerCell3 = tableHeaderRow.createCell(3);
        Cell headerCell4 = tableHeaderRow.createCell(4);
        Cell headerCell5 = tableHeaderRow.createCell(5);
        Cell headerCell6 = tableHeaderRow.createCell(6);	        
        Cell headerCell7 = tableHeaderRow.createCell(7);	
        Cell headerCell8 = tableHeaderRow.createCell(8);      
        Cell headerCell9 = tableHeaderRow.createCell(9);
        Cell headerCell10 = tableHeaderRow.createCell(10);
        Cell headerCell11 = tableHeaderRow.createCell(11);
 	        
        headerCell0.setCellValue("Period");
        headerCell1.setCellValue("Success");	        
        headerCell2.setCellValue("Usable");
        headerCell3.setCellValue("Eligible");
        headerCell4.setCellValue("PCT Success");
        headerCell5.setCellValue("PCT Usable");
        headerCell6.setCellValue("S.T.  PCT  Success");	        
        headerCell7.setCellValue("L.T.  PCT  Success");
        headerCell8.setCellValue("S.T.  PCT  Usable");
        headerCell9.setCellValue("L.T.  PCT  Usable");
        headerCell10.setCellValue("Price Quotes");
        headerCell11.setCellValue("Est. Response");
        	        
        headerCell0.setCellStyle(styles.get("header"));
        headerCell1.setCellStyle(styles.get("header"));      
        headerCell2.setCellStyle(styles.get("header"));
        headerCell3.setCellStyle(styles.get("header"));
        headerCell4.setCellStyle(styles.get("header"));
        headerCell5.setCellStyle(styles.get("header"));
        headerCell6.setCellStyle(styles.get("header"));	       
        headerCell7.setCellStyle(styles.get("header"));	
        headerCell8.setCellStyle(styles.get("header"));	
        headerCell9.setCellStyle(styles.get("header"));	
        headerCell10.setCellStyle(styles.get("header"));	
        headerCell11.setCellStyle(styles.get("header"));	
        
        //Data area.
        String data = request.getParameter("data");
        String delims = "[*]";
        String[] tokens = data.split(delims);
        
    	int row_excel = 3;	
    	
        for(int i=0; i <= tokens.length-12; i=i+12){
        	Row row = sheet.createRow(row_excel);
        	row_excel = row_excel+1;

        	row.createCell(0).setCellValue(new Integer(tokens[i+0]));
    		row.getCell(0).setCellStyle(styles.get("cell_integer"));       
    		row.getCell(0).setCellType(Cell.CELL_TYPE_NUMERIC);   
    		
        	row.createCell(1).setCellValue(new Integer(tokens[i+1]));
    		row.getCell(1).setCellStyle(styles.get("cell_integer"));       
    		row.getCell(1).setCellType(Cell.CELL_TYPE_NUMERIC);   

        	row.createCell(2).setCellValue(new Integer(tokens[i+2]));
    		row.getCell(2).setCellStyle(styles.get("cell_integer"));       
    		row.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);   
    		
        	row.createCell(3).setCellValue(new Integer(tokens[i+3]));
    		row.getCell(3).setCellStyle(styles.get("cell_integer"));       
    		row.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);   
    
       		if(tokens[i+4].toString().trim().equalsIgnoreCase("n.a.")){    		
    			row.createCell(4).setCellValue(new String(tokens[i+4]));
    			row.getCell(4).setCellStyle(styles.get("cell_double"));       
    			row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);   
    		}else{
    			row.createCell(4).setCellValue(new Double(tokens[i+4]));
    			row.getCell(4).setCellStyle(styles.get("cell_double"));       
    			row.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);       			
    		}
    		
    		if(tokens[i+5].toString().trim().equalsIgnoreCase("n.a.")){
    			row.createCell(5).setCellValue(new String(tokens[i+5]));
    			row.getCell(5).setCellStyle(styles.get("cell_double"));       
    			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
    			row.createCell(5).setCellValue(new Double(tokens[i+5]));
    			row.getCell(5).setCellStyle(styles.get("cell_double"));       
    			row.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);        			
    		}
    		
    		if(tokens[i+6].toString().trim().equalsIgnoreCase("n.a.")){
    			row.createCell(6).setCellValue(new String(tokens[i+6]));
    			row.getCell(6).setCellStyle(styles.get("cell_double"));       
    			row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
    			row.createCell(6).setCellValue(new Double(tokens[i+6]));
    			row.getCell(6).setCellStyle(styles.get("cell_double"));       
    			row.getCell(6).setCellType(Cell.CELL_TYPE_NUMERIC);        			
    		}
    		
    		if(tokens[i+7].toString().trim().equalsIgnoreCase("n.a.")){
    			row.createCell(7).setCellValue(new String(tokens[i+7]));
    			row.getCell(7).setCellStyle(styles.get("cell_double"));       
    			row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
    			row.createCell(7).setCellValue(new Double(tokens[i+7]));
    			row.getCell(7).setCellStyle(styles.get("cell_double"));       
    			row.getCell(7).setCellType(Cell.CELL_TYPE_NUMERIC);        			
    		}
    		
    		if(tokens[i+8].toString().trim().equalsIgnoreCase("n.a.")){
    			row.createCell(8).setCellValue(new String(tokens[i+8]));
    			row.getCell(8).setCellStyle(styles.get("cell_double"));       
    			row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{  			
    			row.createCell(8).setCellValue(new Double(tokens[i+8]));
    			row.getCell(8).setCellStyle(styles.get("cell_double"));       
    			row.getCell(8).setCellType(Cell.CELL_TYPE_NUMERIC);    
    		}
    		
    		if(tokens[i+9].toString().trim().equalsIgnoreCase("n.a.")){
    			row.createCell(9).setCellValue(new String(tokens[i+9]));
    			row.getCell(9).setCellStyle(styles.get("cell_double"));       
    			row.getCell(9).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
            	row.createCell(9).setCellValue(new Double(tokens[i+9]));
            	row.getCell(9).setCellStyle(styles.get("cell_double"));       
            	row.getCell(9).setCellType(Cell.CELL_TYPE_NUMERIC);    
        	}

    		
    		if(tokens[i+10].toString().trim().equalsIgnoreCase("n.a.")){  			          		
    			row.createCell(10).setCellValue(new String(tokens[i+10]));
    			row.getCell(10).setCellStyle(styles.get("cell_double"));       
    			row.getCell(10).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
    			row.createCell(10).setCellValue(new Double(tokens[i+10]));
    			row.getCell(10).setCellStyle(styles.get("cell_double"));       
    			row.getCell(10).setCellType(Cell.CELL_TYPE_NUMERIC);       			
    		}
    		
    		if(tokens[i+11].toString().trim().equalsIgnoreCase("n.a.")){	   
    			row.createCell(11).setCellValue(new String(tokens[i+11]));
    			row.getCell(11).setCellStyle(styles.get("cell_double"));       
    			row.getCell(11).setCellType(Cell.CELL_TYPE_STRING);    
    		}else{
    			row.createCell(11).setCellValue(new Double(tokens[i+11]));
    			row.getCell(11).setCellStyle(styles.get("cell_double"));       
    			row.getCell(11).setCellType(Cell.CELL_TYPE_NUMERIC);   	
    		}
        }
        
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition","attachment;filename=\""+report_title+ ".xlsx\"");
        response.addHeader("Content-description", "Monthy Final Report");
        ServletOutputStream stream = response.getOutputStream ();
          
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
