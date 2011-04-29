package com.vi.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vi.data.FormData;
import com.vi.svgFileOperations.SVGEdit;

/**
 * A servlet which produces an SVG/XML stream to be sent back to the browser. 
 * So the file has not to be stored locally
 * 
 * @author Johannes Sauer
 *
 */
public class SvgServlet extends HttpServlet {

	@Override()
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//set response content type to SVG data
		//response.;
		response.setContentType("image/svg+xml");
		PrintWriter out = response.getWriter();

		//getting spring beans with setter and getters does not work inside servlet
		//this get the beans, bean name must match id in applicationContext.xml
		//there apparently is a better way to do this: 
		//http://andykayley.blogspot.com/2008/06/how-to-inject-spring-beans-into.html
		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		FormData formData = (FormData) springContext.getBean("formData");
		BasicDataSource dataSource = (BasicDataSource) springContext
				.getBean("dataSource");

		String itemNr = formData.getItemNr();
		String version = formData.getVersionAS();
		String operator = formData.getOperatorID();
		String side = formData.getSide();
		if (itemNr == null || itemNr.equals("") || version == null
				|| version.equals("") || side == null || side.equals("")
				||operator==null||operator.equals("")) {
			// no item selected or found
			//show a simple SVG and some information
			out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\"");
			out.println("\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");

			out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
			out.println(" xmlns:xlink=\"http://www.w3.org/1999/xlink\" ");
			out.println(" width='300px' height='300px'>");

			out.println("<title>No Item</title>");

			out.println("<circle cx='120' cy='150' r='60' style='fill: gold;'>");
			out.println(" <animate attributeName='r' from='2' to='80' begin='0' ");
			out.println(" dur='3' repeatCount='indefinite' /></circle>");

			out.println("<polyline points='120 30, 25 150, 290 150' ");
			out.println("stroke-width='4' stroke='brown' style='fill: none;' />");

			out.println("<polygon points='210 100, 210 200, 270 150' ");
			out.println("style='fill: lawngreen;' /> ");

			out.println("<text x='60' y='250' fill='blue'>No item selected or found!</text>");

			out.println("</svg>");

		}

		else {
			//get basis SVG from the database
			Blob svgDataBlob = null;

			Connection conn = null;
			CallableStatement coll = null;
			try {
				conn = dataSource.getConnection();
				conn.setAutoCommit(false);
				//calling the stored procedure which gets the Blob/SVG from the DB
				//PROCEDURE GET_SVG(in_itemNr IN VARCHAR2,in_versionAS IN VARCHAR2,
				//in_side IN VARCHAR2,out_blob OUT BLOB);
				coll = conn.prepareCall("{call PCBVI.PKG_SVG_STORAGE.GET_SVG(?,?,?,?)}");
				//prepare input and output arguments 
				coll.setString(1, formData.getItemNr());
				coll.setString(2, formData.getVersionAS());
				coll.setString(3, "0");

				coll.registerOutParameter(4, OracleTypes.BLOB);

				coll.execute();
			
				//get the Blob/SVG
				svgDataBlob = coll.getBlob(4);
				//open the SVG from the DB as InputStream
				InputStream svgStream = svgDataBlob.getBinaryStream();		
				//check that there is data
				if (svgDataBlob != null && svgStream !=null) {
					SVGEdit svgEdit = new SVGEdit();
					//need the absolute URL, so I can put links (popups) in the SVG
					//relative won't work due to security reasons
					String absoluteURL = request.getScheme() + "://"
							+ request.getServerName() + ":"
							+ request.getServerPort()
							+ request.getContextPath();
					//add information to the SVG and send it to the user
					svgEdit.writeEnhancedSVG_useStream(out, svgStream, itemNr,
							version, dataSource, absoluteURL,side);
	

				} else {
					// no file for item
					//show a simple SVG and some information
					out.println("<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.0//EN\"");
					out.println("\"http://www.w3.org/TR/2001/REC-SVG-20010904/DTD/svg10.dtd\">");

					out.println("<svg xmlns=\"http://www.w3.org/2000/svg\" ");
					out.println(" xmlns:xlink=\"http://www.w3.org/1999/xlink\" ");
					out.println(" width='300px' height='300px'>");

					out.println("<title>No file</title>");

					out.println("<circle cx='120' cy='150' r='60' style='fill: gold;'>");
					out.println(" <animate attributeName='r' from='2' to='80' begin='0' ");
					out.println(" dur='3' repeatCount='indefinite' /></circle>");

					out.println("<polyline points='120 30, 25 150, 290 150' ");
					out.println("stroke-width='4' stroke='brown' style='fill: none;' />");

					out.println("<polygon points='210 100, 210 200, 270 150' ");
					out.println("style='fill: lawngreen;' /> ");

					out.println("<text x='60' y='250' fill='blue'>There was no source file for this SVG!</text>");

					out.println("</svg>");
				}
					
				//close the stream from the DB
				if(svgStream!=null){
					svgStream.close();
				}
				conn.commit();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (coll != null) {
					try {
						coll.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

	}

	/**Process the HTTP Post request*/
	@Override()
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
