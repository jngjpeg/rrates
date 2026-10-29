package gov.bls.responseRates.filters;

import gov.bls.platform.log.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Servlet Filter implementation class ParmFilter
 */
public class ParmFilter implements Filter {

    final static ILog logger = LogFactory.getLogger();
	
    /**
     * Default constructor. 
     */
    public ParmFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		logger.debug("ParmFilter, organizing parameters.");   
			
		String p_page = request.getParameter("p_page").toString().trim();
		
		logger.debug("p_page is" + p_page);
			
		if (p_page.equals("Response Rates")){
			
			request.setAttribute( "p_period", formatPeriod(request.getParameter("p_period").toString().trim()) );
			
			java.sql.Date p_date_time = formatDate(request.getParameter( "p_login_date" ),request.getParameter( "p_login_time" ));			
			request.setAttribute( "p_date_time", p_date_time );	
			
		}else if ( p_page.equals("Weighted Estimation") ){
			
			request.setAttribute( "p_period", formatPeriod(request.getParameter("p_period").toString().trim()) );
		
		}else if ( p_page.equals("Weekly Analysis") ){
			
			request.setAttribute( "p_period", formatPeriod(request.getParameter("p_period").toString().trim()) );
					
			java.sql.Date p_date_time = formatDate(request.getParameter( "p_login_date" ),"06:30:00AM");			
			request.setAttribute( "p_date_time", p_date_time );
	
		}else if ( p_page.equals("Final Aggregate") ){
			
			request.setAttribute( "p_agg_type", "A" );
		
		}else if ( p_page.equals("Final Export") ){
			
			request.setAttribute( "p_agg_type", "X" );			
		
		}else if ( p_page.equals("Final Import") ){
			
			request.setAttribute( "p_agg_type", "M" );
		
		}else if ( p_page.equals("Preliminary Aggregate") ){
			
			request.setAttribute( "p_agg_type", "A" );
			
		}else if ( p_page.equals("Preliminary Export") ){
			
			request.setAttribute( "p_agg_type", "X" );
			
		}else if ( p_page.equals("Preliminary Import") ){
			
			request.setAttribute( "p_agg_type", "M" );
			
		}else if ( p_page.equals("Calculate Monthly Data") ){
			
			request.setAttribute( "p_period", formatPeriod(request.getParameter("p_period").toString().trim()) );
			
			java.sql.Date p_date_time = formatDate(request.getParameter( "p_login_date" ),request.getParameter( "p_login_time" ));			
			request.setAttribute( "p_date_time", p_date_time );
			
			request.setAttribute( "p_prelim_final", request.getParameter("p_prelim_final").toString().trim() );						
		}

        //logger.info("format the period: " + formatPeriod(request.getParameter("p_period").toString().trim()) );
        
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
	private java.sql.Date formatDate(String p_date, String p_time){
	
		String adate = new String( p_date + " " + p_time );
		
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MMM-dd hh:mm:ssa" );
		java.sql.Date p_date_time = null;
		
		try{			
			p_date_time = new java.sql.Date ( ((sdf.parse(adate)).getTime()) );     
		}catch(Exception e){
			logger.error( "ParmFilter, parse exception " + e.toString() );     	
		}
		
		return p_date_time;
	}
	
	private Integer formatPeriod(String p_period_string){
		
		String year = p_period_string.substring(0,4);
		String month = p_period_string.substring(5);
		String temp_month="";
		 
		if( month.equals("January") ){
			temp_month="01";
		}else if( month.equals("February") ){
			temp_month="02";
		}else if( month.equals("March") ){
			temp_month="03";
		}else if( month.equals("April") ){
			temp_month="04";
		}else if( month.equals("May") ){
			temp_month="05";
		}else if( month.equals("June") ){
			temp_month="06";
		}else if( month.equals( "July") ){
			temp_month="07";
		}else if( month.equals( "August" ) ){
			temp_month="08";
		}else if( month.equals( "September") ){
			temp_month="09";
		}else if( month.equals( "October" ) ){
			temp_month="10";
		}else if( month.equals( "November" ) ){
			temp_month="11";
		}else if(month.equals( "December" ) ){
			temp_month="12";
		}
		
		String p_period_temp = ""+year+temp_month;
		
		return new Integer(p_period_temp);		
	}

}
