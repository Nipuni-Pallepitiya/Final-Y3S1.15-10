package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Product;
@Path("/Product")
public class ProductService {

	Product prodobj = new Product();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProduct()
	{
		//return "Hello";
		return prodobj.readProduct();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String insertProject(@FormParam("pname") String pname,@FormParam("ptype") String ptype,@FormParam("description") String description,
	@FormParam("price") String price,
	@FormParam("quantity") String quantity,
	@FormParam("projId") String projId)
 
	{
		String output = prodobj.insertProduct(pname,ptype,description, price, quantity, projId);
		return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	
	public String updateProject(String productData)
	{
		//Convert the input string to a JSON object
		JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
		//Read the values from the JSON object
		String pid = productObject.get("pid").getAsString();
		String pname = productObject.get("pname").getAsString();
		String ptype = productObject.get("ptype").getAsString();
		String description=productObject.get("description").getAsString();
		String price = productObject.get("price").getAsString();
		String quantity = productObject.get("quantity").getAsString();
		String projId = productObject.get("projId").getAsString();
		String output = prodobj.updateProduct(pid, pname,ptype,description, price, quantity, projId);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProduct(String productData)
	{
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(productData, "", Parser.xmlParser());
		//Read the value from the element <itemID>
		String pid = doc.select("pid").text();
		String output = prodobj.deleteProduct(pid);
		return output;
	}
}
