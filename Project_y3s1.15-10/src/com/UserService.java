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


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.User;
@Path("/Users") 
public class UserService {
	User userObj = new User(); 
	@GET
	@Path("/") 
	@Produces(MediaType.TEXT_HTML) 
	public String readUsers() 
	{ 
		return userObj.readUsers();
	}
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_HTML) 
	public String loginUser(String udata) 
	{ 
		JsonObject userObject = new JsonParser().parse(udata).getAsJsonObject(); 
		
		String uname = userObject.get("uname").getAsString(); 
		String pwd = userObject.get("pwd").getAsString(); 
		
		String output=userObj.userLogin(uname, pwd);
		return output; 
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertUser(@FormParam("fname") String fname,
	 @FormParam("lname") String lname,
	 @FormParam("userName") String uname, 
	 @FormParam("email") String email, 
	 @FormParam("pwd") String pwd, 
	 @FormParam("nic") String nic,
	 @FormParam("level") String level,
	 @FormParam("type") String type) 
	{ 
	 String output = userObj.insertUser(fname,lname,uname,email,pwd,nic,level,type); 
	return output; 
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateUser(String userData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject(); 
	//Read the values from the JSON object
	 String userID = userObject.get("id").getAsString(); 
	 String fname = userObject.get("fname").getAsString(); 
	 String lname = userObject.get("lname").getAsString(); 
	 String email = userObject.get("email").getAsString(); 
 
	 String output = userObj.updateUser(userID, fname,lname, email); 
	return output; 
	}
	
	@DELETE
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteUser(String userData) 
	{ 
	
	 JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject(); 
	
	 String userID = userObject.get("userId").getAsString(); 
	 String output = userObj.deleteUser(userID); 
	return output; 
	}

}
