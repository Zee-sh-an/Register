package com.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.catalina.connector.InputBuffer;

import java.sql.*;

/**
 * Servlet implementation class Register
 */
@MultipartConfig
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("user_name");
		String email = request.getParameter("user_email");
		String password = request.getParameter("user_password");
		Part part = request.getPart("image");
		String filename = part.getSubmittedFileName();
//		out.println(filename);
//		out.println(name);
//		out.println(password);
//		out.println(email);
//		connection......
		try {
			Thread.sleep(3000);

			Class.forName("com.mysql.jdbc.Driver");

			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/youtube", "root", "as12b1305");
//			query.....

			String q = "insert into user(name,password,email,imageName) values(?,?,?,?)";

			PreparedStatement pstm = con.prepareStatement(q);
			pstm.setString(1, name);
			pstm.setString(2, password);
			pstm.setString(3, email);
			pstm.setString(4, filename);

			pstm.executeUpdate();
//			upload file
			InputStream is = part.getInputStream();
			byte[] data = new byte[is.available()];

			is.read(data);
			String path = request.getRealPath("/") + "img" + File.separator + filename;
//			out.println(path);
			FileOutputStream fos = new FileOutputStream(path);

			fos.write(data);
			fos.close();
//			response.sendRedirect("signup.jsp");

			out.println("done");
		} catch (Exception e) {
			e.printStackTrace();
			out.println("error");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
