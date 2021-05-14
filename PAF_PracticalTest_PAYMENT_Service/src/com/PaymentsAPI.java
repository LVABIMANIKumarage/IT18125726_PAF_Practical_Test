package com;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@WebServlet("/PaymentsAPI")
public class PaymentsAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	Payments PayObj = new Payments();
   
    public PaymentsAPI() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		int CNO = Integer.parseInt(request.getParameter("cardnumber"));
	    String Pname=request.getParameter("name").toString();
	    int pcvc = Integer.parseInt(request.getParameter("cvc"));
	    int pamount = Integer.parseInt(request.getParameter("amount"));
	    String stringdate=request.getParameter("datepicker").toString();
	    int Poid = Integer.parseInt(request.getParameter("oid"));
	    
	   	    
		 String output = PayObj.addPayment(CNO,Pname,pcvc,pamount,stringdate,Poid);
				 
		 System.out.println(output);
		 
		 response.getWriter().write(output);
		
	}


	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		
		Map paras = getParasMap(request);
		
		int buttonID=Integer.parseInt(paras.get("hidPayIDSave").toString());
		int CNO = Integer.parseInt(paras.get("cardnumber").toString());
		String NAME = request.getParameter("name").toString();
		String CalenderDate =  paras.get("datepicker").toString().replace("%2F", "-");
		int CVC = Integer.parseInt(paras.get("cvc").toString());
		
		
		
		int orderID=Integer.parseInt(paras.get("oid").toString());
		int Amount = Integer.parseInt(paras.get("amount").toString());
	    
		
		
		 String output = PayObj.UpdatePayments( buttonID,CNO,NAME,CVC,Amount,CalenderDate,orderID);
				
		 
		
		 
		response.getWriter().write(output);
		System.out.println(output);
		} 


	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	
		 Map paras = getParasMap(request);
		 String output = PayObj.deletePayment(paras.get("PayID").toString());
		 response.getWriter().write(output);
		 System.out.println(output);
	}

	private static Map getParasMap(HttpServletRequest request)
    {
     Map<String, String> map = new HashMap<String, String>();
    try {
     Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
     String queryString = scanner.hasNext() ?
     scanner.useDelimiter("\\A").next() : "";
     scanner.close();
     String[] params = queryString.split("&");
     for (String param : params)
     { 
    	 String[] p = param.split("=");
    	 map.put(p[0], p[1]);
     }
      }catch (Exception e)
    	 {
    	 }
    	return map;  
    }
}
