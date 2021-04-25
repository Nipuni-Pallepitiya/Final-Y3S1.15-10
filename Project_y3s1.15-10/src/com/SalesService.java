package com;

import model.Sales;

//for REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.*;

//for REST Authorization
import javax.annotation.security.RolesAllowed;

//for JSON
import com.google.gson.*;

//for XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Sales")
public class SalesService {

	Sales saleObj = new Sales();
	
	//Retrieve all sales data
	@RolesAllowed({"Admin","Customer"})
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readSales() {
		
		String sales = saleObj.allSales();
		
		if(!sales.isEmpty()) {
			return sales;
		} else {
			return "Table is Empty";
		}
		//return saleObj.allSales(); this is working
		
	}
	
	
	@RolesAllowed({"Customer"})
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String addSales(@FormParam("invoiceId") String invoiceId, @FormParam("purchaseDate") String purchaseDate,
			@FormParam("totalUnits") String totalUnits, @FormParam("netAmount") String netAmount, 
			@FormParam("discountTax") String discountTax, @FormParam("totalAmount") String totalAmount, 
			@FormParam("paymentType") String paymentType, @FormParam("orderStatus") String orderStatus)
	{
		String output = saleObj.insertSales(invoiceId, purchaseDate, totalUnits, netAmount, discountTax, totalAmount, paymentType, orderStatus);
		
		if(!output.isEmpty()) {
			return output;
		} else {
			return "Record Not Added";
		}
		//return output;
	}
	
	@RolesAllowed({"Admin","Customer"})
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateSales(String salesData) {
		JsonObject salesObject = new JsonParser().parse(salesData).getAsJsonObject();
		
		//Read the values from the JSON Object
		String invoiceId = salesObject.get("invoiceId").getAsString();
		String purchaseDate = salesObject.get("purchaseDate").getAsString();
		String totalUnits = salesObject.get("totalUnits").getAsString();
		String netAmount = salesObject.get("netAmount").getAsString();
		String discountTax = salesObject.get("discountTax").getAsString();
		String totalAmount = salesObject.get("totalAmount").getAsString();
		String paymentType = salesObject.get("paymentType").getAsString();
		String orderStatus = salesObject.get("orderStatus").getAsString();
		
		String output = saleObj.updateSales(invoiceId, purchaseDate, totalUnits, netAmount, discountTax, totalAmount, paymentType, orderStatus);
		
		//if(!output.isEmpty()) {
		//	return output;
		//} else {
		//	return "Record Not Found or does not Exist";
		//}
		
		return output;
	}
	
	@RolesAllowed({"Admin"})
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteSales(String salesData) {
		Document doc = Jsoup.parse(salesData, "", Parser.xmlParser());
		
		String invoiceId = doc.select("invoiceId").text();
		String output = saleObj.deleteSales(invoiceId);
		
		if(!output.isEmpty()) {
			return output;
		} else {
			return "Record Not Found or does not Exist";
		}
		//return output;
	}
}

