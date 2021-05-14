package com.orderclient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

/**
 * Servlet implementation class OrderClientAPI
 */
@WebServlet("/OrderClientAPI")
public class OrderClientAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Order order = new Order();

    /**
     * Default constructor. 
     */
    public OrderClientAPI() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JsonObject output = order.insertOrder(
                Integer.parseInt(request.getParameter("product_id")),
                Integer.parseInt(request.getParameter("items")),
                Double.parseDouble(request.getParameter("item_price")),
                Double.parseDouble(request.getParameter("discount")),
                Integer.parseInt(request.getParameter("customerid")));
                
                
        response.getWriter().write(output.toString());
		
		
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> paras = getParasMap(request);
		JsonObject output = order.updateOrder(
				 Integer.parseInt(paras.get("hiddenorder_idSave").toString()),
			     Integer.parseInt(paras.get("product_id")),
	             Integer.parseInt(paras.get("items")),
	             Double.parseDouble(paras.get("item_price")),
	             Double.parseDouble(paras.get("discount")),
	             Integer.parseInt(paras.get("customerid")));
		response.getWriter().write(output.toString());
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String> paras = getParasMap(request);
		JsonObject output = order.deleteOrder(Integer.parseInt(paras.get("order_id").toString()));
		response.getWriter().write(output.toString());
	}
	
	
	
	// Convert request parameters to a Map
	private static Map<String,String> getParasMap(HttpServletRequest request)
	{
		Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
					scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			String[] params = queryString.split("&");
			for (String param : params)
			{
				String[] p = param.split("=");
				map.put(p[0], java.net.URLDecoder.decode(p[1], StandardCharsets.UTF_8.name()));
			}
		}
		catch (Exception e)
		{
		}
		return map;
	}
	

}
