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
public class WeeklyExcelExport extends HttpServlet {
	private static final long serialVersionUID = 1L;
    final static ILog logger = LogFactory.getLogger();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeeklyExcelExport() {
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

	      	/*Parms: period, date_time, weekly_mail_out, num_days, mail_out_date, previous_period
	      		previous_date, data_previous, data_current 
	      		
	      		*/
	      	//request.getParameter("period");
	      		      	
	    	//Workbook wb = new HSSFWorkbook();
	      	Workbook wb = new XSSFWorkbook();
	      	
	      	
	        Map<String, CellStyle> styles = createStyles(wb);

	        Sheet sheet_current = wb.createSheet("Current");
	        Sheet sheet_previous = wb.createSheet("Previous");
	        
	        sheet_current.setColumnWidth(0,4000);
	        sheet_current.setColumnWidth(1,3500);
	        sheet_current.setColumnWidth(2,3500);
	        sheet_current.setColumnWidth(3,3500);
	        sheet_current.setColumnWidth(4,3500);
	        sheet_current.setColumnWidth(5,3500);
	        sheet_current.setColumnWidth(6,3500);

	        sheet_previous.setColumnWidth(0,4000);
	        sheet_previous.setColumnWidth(1,3500);
	        sheet_previous.setColumnWidth(2,3500);
	        sheet_previous.setColumnWidth(3,3500);
	        sheet_previous.setColumnWidth(4,3500);
	        sheet_previous.setColumnWidth(5,3500);
	        sheet_previous.setColumnWidth(6,3500);
	        
	        PrintSetup printSetup = sheet_current.getPrintSetup();
	        printSetup.setLandscape(true);
	        sheet_current.setFitToPage(true);
	        sheet_current.setHorizontallyCenter(true);

	    	Row titleRow = sheet_current.createRow(0);
	        titleRow.setHeightInPoints(45);
	        Cell titleCell = titleRow.createCell(0);
	        titleCell.setCellValue("Weekly Report");
	        titleCell.setCellStyle(styles.get("title"));
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$A$1:$G$1"));

	        /*Parms: period, date_time, weekly_mail_out, num_days, previous_mail_out_date, previous_period,
      		         previous_date*/
	        /*
	        	Period:
	        	Login Date Time:
	        	Mail Out Date:
	        	Number of Days Between:
	        	Previous Mail Out Date:
	        	Previous Period:
	        	Previous Date Time Period: 	        
	        */
	        
	        //Parms area.	        
	        
	        Row parmRow2 = sheet_current.createRow(2);  
	        Row parmRow3 = sheet_current.createRow(3);  
	        Row parmRow4 = sheet_current.createRow(4);          
	        
	        Cell periodCell = parmRow2.createCell(0);
	        periodCell.setCellValue("Period: " + request.getParameter("period"));
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$A$2:$C$2"));
      
	        Cell dateTimeCell = parmRow3.createCell(0);
	        dateTimeCell.setCellValue( "Login Date Time: " + request.getParameter("date_time") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$A$3:$C$3"));
	        
	        Cell mailOutCell = parmRow2.createCell(3);
	        mailOutCell.setCellValue( "Mail Out Date: " + request.getParameter("weekly_mail_out") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$D$2:$F$2"));
	 
	        Cell daysCell = parmRow3.createCell(3);
	        daysCell.setCellValue( "Number of Days Between: " + request.getParameter("num_days") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$D$3:$F$3"));
      		
	        Cell prevMailOutCell = parmRow2.createCell(6);
	        prevMailOutCell.setCellValue( "Previous Mail Out Date: " + request.getParameter("previous_mail_out_date") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$G$2:$J$2"));
	        
	        Cell prevPeriod = parmRow3.createCell(6);
	        prevPeriod.setCellValue( "Previous Period: " + request.getParameter("previous_period") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$G$3:$J$3"));
	
	        Cell prevDate = parmRow4.createCell(6);
	        prevDate.setCellValue( "Previous Date Time Period: " + request.getParameter("previous_date") );
	        sheet_current.addMergedRegion(CellRangeAddress.valueOf("$G$4:$J$4"));
	        
      		
	        //Create the report header table header row.
	        Row tableHeaderRowCurr = sheet_current.createRow(6);
	        
	        Cell headerCell0Curr = tableHeaderRowCurr.createCell(0);
	        Cell headerCell1Curr = tableHeaderRowCurr.createCell(1);
	        Cell headerCell2Curr = tableHeaderRowCurr.createCell(2);
	        Cell headerCell3Curr = tableHeaderRowCurr.createCell(3);
	        Cell headerCell4Curr = tableHeaderRowCurr.createCell(4);
	        Cell headerCell5Curr = tableHeaderRowCurr.createCell(5);
	        Cell headerCell6Curr = tableHeaderRowCurr.createCell(6);	        
	        
	        headerCell0Curr.setCellValue("Mode of Collection");
	        headerCell1Curr.setCellValue("Requested");	        
	        headerCell2Curr.setCellValue("Successful");
	        headerCell3Curr.setCellValue("PCT Successful");
	        headerCell4Curr.setCellValue("Usable");
	        headerCell5Curr.setCellValue("PCT Usable");
	        headerCell6Curr.setCellValue("Eligible");	        

	        headerCell0Curr.setCellStyle(styles.get("header"));
	        headerCell1Curr.setCellStyle(styles.get("header"));      
	        headerCell2Curr.setCellStyle(styles.get("header"));
	        headerCell3Curr.setCellStyle(styles.get("header"));
	        headerCell4Curr.setCellStyle(styles.get("header"));
	        headerCell5Curr.setCellStyle(styles.get("header"));
	        headerCell6Curr.setCellStyle(styles.get("header"));	       
	        
	        //Data area.
	        String data_current = request.getParameter("data_current");
	        String delims = "[*]";
	        String[] tokens_current = data_current.split(delims);
	        
        	int row_excel_curr = 7;	
        	
	        for(int i=0; i <= tokens_current.length-7; i=i+7){
	        	Row row_current = sheet_current.createRow(row_excel_curr);
	        	row_excel_curr = row_excel_curr+1;
	        	       	        	
	        	row_current.createCell(0).setCellValue(tokens_current[i+0]);
        		row_current.getCell(0).setCellStyle(styles.get("cell_text"));            		
        		row_current.getCell(0).setCellType(Cell.CELL_TYPE_STRING);     

	        	row_current.createCell(1).setCellValue(tokens_current[i+1]);
        		row_current.getCell(1).setCellStyle(styles.get("cell_text"));       
        		row_current.getCell(1).setCellType(Cell.CELL_TYPE_STRING);    
        		
	        	row_current.createCell(2).setCellValue(new Integer(tokens_current[i+2]));
        		row_current.getCell(2).setCellStyle(styles.get("cell_integer"));       
        		row_current.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);    
        		
	        	row_current.createCell(3).setCellValue(new Double(tokens_current[i+3]));
        		row_current.getCell(3).setCellStyle(styles.get("cell_double"));       
        		row_current.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);    

        		        		
	        	row_current.createCell(4).setCellValue(new Integer(tokens_current[i+4]));
        		row_current.getCell(4).setCellStyle(styles.get("cell_integer"));       
        		row_current.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);    
        		
	        	row_current.createCell(5).setCellValue(new Double(tokens_current[i+5]));
        		row_current.getCell(5).setCellStyle(styles.get("cell_double"));       
        		row_current.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);    
        		
	        	row_current.createCell(6).setCellValue(new Integer(tokens_current[i+6]));
        		row_current.getCell(6).setCellStyle(styles.get("cell_integer"));       
        		row_current.getCell(6).setCellType(Cell.CELL_TYPE_NUMERIC);    
	        }	
        		
        		//88888888888888
                Row tableHeaderRowPrev = sheet_previous.createRow(6);
    	        
    	        Cell headerCell0Prev = tableHeaderRowPrev.createCell(0);
    	        Cell headerCell1Prev = tableHeaderRowPrev.createCell(1);
    	        Cell headerCell2Prev = tableHeaderRowPrev.createCell(2);
    	        Cell headerCell3Prev = tableHeaderRowPrev.createCell(3);
    	        Cell headerCell4Prev = tableHeaderRowPrev.createCell(4);
    	        Cell headerCell5Prev = tableHeaderRowPrev.createCell(5);
    	        Cell headerCell6Prev = tableHeaderRowPrev.createCell(6);	        
    	        
    	        headerCell0Prev.setCellValue("Mode of Collection");
    	        headerCell1Prev.setCellValue("Requested");	        
    	        headerCell2Prev.setCellValue("Successful");
    	        headerCell3Prev.setCellValue("PCT Successful");
    	        headerCell4Prev.setCellValue("Usable");
    	        headerCell5Prev.setCellValue("PCT Usable");
    	        headerCell6Prev.setCellValue("Eligible");	        

    	        headerCell0Prev.setCellStyle(styles.get("header"));
    	        headerCell1Prev.setCellStyle(styles.get("header"));      
    	        headerCell2Prev.setCellStyle(styles.get("header"));
    	        headerCell3Prev.setCellStyle(styles.get("header"));
    	        headerCell4Prev.setCellStyle(styles.get("header"));
    	        headerCell5Prev.setCellStyle(styles.get("header"));
    	        headerCell6Prev.setCellStyle(styles.get("header"));	       
    	        
    	        //Data area.
    	        String data_previous = request.getParameter("data_previous");
    	        String[] tokens_previous = data_previous.split(delims);
    	        
            	int row_excel_prev = 7;	
            	
    	        for(int j=0; j <= tokens_previous.length-7; j=j+7){
    	        	Row row_prev = sheet_previous.createRow(row_excel_prev);
    	        	row_excel_prev = row_excel_prev+1;
    	        	       	
    	        	
    	        	row_prev.createCell(0).setCellValue(tokens_previous[j+0]);
            		row_prev.getCell(0).setCellStyle(styles.get("cell_text"));            		
            		row_prev.getCell(0).setCellType(Cell.CELL_TYPE_STRING);     

    	        	row_prev.createCell(1).setCellValue(tokens_previous[j+1]);
            		row_prev.getCell(1).setCellStyle(styles.get("cell_text"));       
            		row_prev.getCell(1).setCellType(Cell.CELL_TYPE_STRING);    
            		
    	        	row_prev.createCell(2).setCellValue(new Integer(tokens_previous[j+2]));
            		row_prev.getCell(2).setCellStyle(styles.get("cell_integer"));       
            		row_prev.getCell(2).setCellType(Cell.CELL_TYPE_NUMERIC);    
            		
    	        	row_prev.createCell(3).setCellValue(new Double(tokens_previous[j+3]));
            		row_prev.getCell(3).setCellStyle(styles.get("cell_double"));       
            		row_prev.getCell(3).setCellType(Cell.CELL_TYPE_NUMERIC);    

            		        		
    	        	row_prev.createCell(4).setCellValue(new Integer(tokens_previous[j+4]));
            		row_prev.getCell(4).setCellStyle(styles.get("cell_integer"));       
            		row_prev.getCell(4).setCellType(Cell.CELL_TYPE_NUMERIC);    
            		
    	        	row_prev.createCell(5).setCellValue(new Double(tokens_previous[j+5]));
            		row_prev.getCell(5).setCellStyle(styles.get("cell_double"));       
            		row_prev.getCell(5).setCellType(Cell.CELL_TYPE_NUMERIC);    
            		
    	        	row_prev.createCell(6).setCellValue(new Integer(tokens_previous[j+6]));
            		row_prev.getCell(6).setCellStyle(styles.get("cell_integer"));       
            		row_prev.getCell(6).setCellType(Cell.CELL_TYPE_NUMERIC);            	
	        }
	        
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition","attachment;filename=\"Weekly.xlsx\"");
            //response.setHeader("Content-disposition","attachment;filename=\"Weekly.xls\"");
            response.addHeader("Content-description", "Weekly Report");
            ServletOutputStream stream = response.getOutputStream ();
              
            wb.write(stream);
            
            //the flush puts the bytes on the buffer into the stream for return to client.
            stream.flush();
            stream.close();
              
	      //wb.write(out);
	      logger.info("Writing Excel Report");      		
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
