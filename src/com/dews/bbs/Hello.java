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
import javax.ws.rs.core.Response;
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
	@Path("/login")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response login(User user) throws IOException, ClassNotFoundException, SQLException {
	    System.out.println(user.toString());
	    Class.forName("org.postgresql.Driver");
		  String url = "jdbc:postgresql://localhost/bbs";
		  Properties props = new Properties();
		  props.setProperty("user","postgres");
		  props.setProperty("password","moondied");
		  Connection conn = DriverManager.getConnection(url, props);
		  PreparedStatement pst = null;
		  String stm = "SELECT * FROM users WHERE username = ?";
          pst = conn.prepareStatement(stm);
          pst.setString(1, user.getUsername());
		  ResultSet rs = pst.executeQuery();
		  rs.next();
		  System.out.println(rs.getInt(1));
		  System.out.println(rs.getString(2));
		  System.out.println(rs.getBoolean(3));
		  System.out.println(rs.getString(4));
		  if (BCrypt.checkpw(user.getPassword(), rs.getString(4))) return Response.status(200).entity(rs.getString(4)).build();
		  if (rs.getString(4).equals(user.getPassword())) return Response.status(200).entity(rs.getString(4)).build();
	    return Response.serverError().entity("Incorrect password").build();
	}
	@POST
	@Path("/register")
	  @Consumes(MediaType.APPLICATION_JSON)
	  public Response createBoard(User user) throws IOException, ClassNotFoundException {
	    System.out.println(user.toString());
	    Class.forName("org.postgresql.Driver");
	    Connection con = null;
        PreparedStatement pst = null;

        String url = "jdbc:postgresql://localhost/bbs";
        String pw_hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        try {
            Properties props = new Properties();
      	  	props.setProperty("user","postgres");
      	  	props.setProperty("password","moondied");
      	  	con = DriverManager.getConnection(url, props);

            String stm = "INSERT INTO users(username, admin, password) VALUES(?, ?, ?)";
            pst = con.prepareStatement(stm);
            pst.setString(1, user.getUsername());
            pst.setBoolean(2,true);
            pst.setString(3, pw_hash);
            pst.executeUpdate();
            System.out.println("Added: " + user.getUsername() + " password hash: " + pw_hash);

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.toString());
            if (ex.getMessage().contains("already exists")) return Response.serverError().entity("Username already exists").build();
            return Response.serverError().entity(ex.toString()).build();

        } finally {

            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                System.out.println("Error: " + ex.toString());
                return Response.serverError().entity(ex.toString()).build();
            }
        }
	    return Response.status(200).entity(pw_hash).build();
	}
  @GET
  @Path("/boards")
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