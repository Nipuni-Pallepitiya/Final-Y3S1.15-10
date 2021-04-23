package com;

import model.Project;

import javax.annotation.security.RolesAllowed;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Projects")
public class ProjectService
{
	Project projectObj = new Project();
	@RolesAllowed("Admin")
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readProjects()
	{
		//return "Hello";
		return projectObj.readProjects();
	}
	
	@RolesAllowed("Researcher")
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertProject(@FormParam("ProjectName") String ProjectName,@FormParam("ProjectType") String ProjectType,@FormParam("UserID") String UserID,
	@FormParam("sdate") String sdate,
	@FormParam("edate") String edate,
	@FormParam("status") String status,
	@FormParam("investStatus") String investStatus,@FormParam("InvestmentAmount") String InvestmentAmount,@FormParam("ProposedEquity") String ProposedEquity,@FormParam("ProjectTimeline") String ProjectTimeline) 
	{
	String output = projectObj.insertProject(ProjectName,ProjectType,UserID, sdate, edate, status,investStatus,InvestmentAmount,ProposedEquity,ProjectTimeline);
	return output;
	}
	
	@RolesAllowed({"Researcher","Admin"})
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProject(String projectData)
	{
	//Convert the input string to a JSON object
	JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject();
	//Read the values from the JSON object
	String ProjectID = projectObject.get("ProjectID").getAsString();
	String ProjectName = projectObject.get("ProjectName").getAsString();
	String ProjectType = projectObject.get("ProjectType").getAsString();
	String UserID=projectObject.get("UserID").getAsString();
	String sdate = projectObject.get("sdate").getAsString();
	String edate = projectObject.get("edate").getAsString();
	String status = projectObject.get("status").getAsString();
	String investStatus = projectObject.get("investStatus").getAsString();
	String InvestmentAmount = projectObject.get("InvestmentAmount").getAsString();
	String ProposedEquity = projectObject.get("ProposedEquity").getAsString();
	String ProjectTimeline = projectObject.get("ProjectTimeline").getAsString();
	String output = projectObj.updateProject(ProjectID, ProjectName,ProjectType,UserID, sdate, edate, status,investStatus,InvestmentAmount,ProposedEquity,ProjectTimeline);
	return output;
	}
	
	@RolesAllowed({"Researcher","Admin"})
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(String projectData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());
	//Read the value from the element <itemID>
	String ProjectID = doc.select("ProjectID").text();
	String output = projectObj.deleteProject(ProjectID);
	return output;
	}
	
	
	
	
	
	
	
	
}