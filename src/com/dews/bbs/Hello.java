package com.dews.bbs;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.PathParam;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.List;

// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {
	@POST
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public void newTodo(@FormParam("id") String id,
	      @FormParam("name") String name,
	      @FormParam("desc") String desc,
	      @Context HttpServletResponse servletResponse) throws IOException {
	    System.out.println("name: " + name + ", desc: " + desc);
	
	    servletResponse.sendRedirect("../create_todo.html");
	  }
  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public List<Board> getBoards(@PathParam("id") String id) throws SQLException, ClassNotFoundException {
	  Class.forName("org.postgresql.Driver");
	  String url = "jdbc:postgresql://localhost/bbs";
	  Properties props = new Properties();
	  props.setProperty("user","postgres");
	  props.setProperty("password","moondied");
	  Connection conn = DriverManager.getConnection(url, props);
	  
	  PreparedStatement st = conn.prepareStatement("SELECT * FROM boards");
	  ResultSet rs = st.executeQuery();
	  StringBuilder output = new StringBuilder();
	  List<Board> boards = new ArrayList();
	  while (rs.next())
	  {
		  Board a = new Board();
		  a.setName(rs.getString(3));
		  a.setDesc(rs.getString(2));
		  a.setId(rs.getInt(1));
		  boards.add(a);
	     System.out.print("Column 1 returned ");
	     System.out.println(rs.getString(3));
	     
	     output.append(rs.getString(3) + "\n");
	     
	  }
	  rs.close();
	  st.close();
	  return boards;
  }

} 