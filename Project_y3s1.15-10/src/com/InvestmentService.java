package com;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Investment;
@Path("/Investments") 
public class InvestmentService {
	Investment investmentObj = new Investment(); 

	@RolesAllowed({"Admin","Investor"})
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readInvestments() 
	{ 
		return investmentObj.readInvestment();
	}
	
	/*@GET
	@Path("/{userid}") 
	@Produces(MediaType.TEXT_HTML) 
	public String readAUser(@PathParam("userid") String uid) 
	{ 
		return userObj.readAUser(uid);
	}*/
	
	@RolesAllowed({"Admin","Investor"})
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertInvestment(@FormParam("amountFunded") String amountFund, 
	 @FormParam("equityAssigned") String equityAssigned, 
	 @FormParam("conDate") String confirmedDate, 
	 @FormParam("investDate") String investedDate,
	@FormParam("projId") String projId)
	{ 
	 String output = investmentObj.insertInvestment(amountFund, equityAssigned, confirmedDate, investedDate, projId);
	return output; 
	}
	
	@RolesAllowed({"Admin","Investor"})
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateInvestment(String investData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject invObject = new JsonParser().parse(investData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String inID = invObject.get("investid").getAsString(); 
	 String amount = invObject.get("amountFunded").getAsString(); 
	 String equity = invObject.get("equityAssigned").getAsString(); 
	 String conDate = invObject.get("confirmDate").getAsString(); 
	 String invDate = invObject.get("investmentDate").getAsString(); 
 
	 String output = investmentObj.updateInvestment(inID,amount,equity,conDate,invDate);
	return output; 
	}
	
	@RolesAllowed({"Admin","Investor"})
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteUser(String invData) 
	{ 
	
	 JsonObject invObject = new JsonParser().parse(invData).getAsJsonObject(); 
	
	 String investID = invObject.get("investID").getAsString(); 
	 String output = investmentObj.deleteInvestment(investID); 
	return output; 
	}

}
