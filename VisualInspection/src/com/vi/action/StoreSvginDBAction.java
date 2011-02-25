package com.vi.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;

import com.opensymphony.xwork2.ActionSupport;

public class StoreSvginDBAction extends ActionSupport {
	//DataSource
	private BasicDataSource dataSource;
	
	private static final int BUFFER_SIZE = 16 * 1024;
	
	private String itemNr;
	private String versionAS;
	private String side;				
	
	private File svg;
	private String svgContentType;
	private String svgFileName;
	
	/**
	 * Takes a SVG file and stores it in the SVG_FILE database table
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String doStoreSvgInDB()
	{
		int bytesRead= 0;        
        int totBytesRead = 0;
        int totBytesWritten= 0;
		
        //check if a file was selected
		if (svg == null) 
		{
			addFieldError("svg", "You must choose a file!");
			return "input";
		}
		//check if the file is SVG
		if (!"image/svg+xml".equalsIgnoreCase(svgContentType)) 
		{
			addFieldError("svg", "File is not a SVG file!");
			return "input";
		}	
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			conn = dataSource.getConnection();
			//disable autocommit! only commit after data was transfered
			conn.setAutoCommit(false);			
			//calling the stored procedure which get adds a new entry 
			//and returns a reference to the Blob object			
			//PROCEDURE STORE_SVG(in_itemNr IN VARCHAR2,in_versionAS IN VARCHAR2,
			//in_side IN VARCHAR2,out_blob OUT BLOB);
			coll = conn.prepareCall("{call PCBVI.PKG_SVG_STORAGE.STORE_SVG(?,?,?,?)}");
			//prepare input and output arguments 
			coll.setString(1, itemNr);
			coll.setString(2, versionAS);
			coll.setString(3, side);
			
			coll.registerOutParameter(4, OracleTypes.BLOB);
			
			coll.execute();		
			
			// Get the Blob
            Blob myBlob = coll.getBlob(4);
          
            //prepare buffer
            byte[] byteBuffer = new byte[BUFFER_SIZE];  
            //and open inputstream from SVG file
            FileInputStream inputFileInputStream = new FileInputStream(svg);
            //and get outputstream to the database (to the blob)          
            OutputStream storeSvgStream = myBlob.setBinaryStream(0);            
            
            //send the data to the database 
            while ((bytesRead = inputFileInputStream.read(byteBuffer)) != -1) {
                          
            	storeSvgStream.write(byteBuffer, 0, bytesRead);
                
                totBytesRead += bytesRead;
                totBytesWritten += bytesRead;

            }        
            
            //close streams
            inputFileInputStream.close();
            storeSvgStream.close();
            
            //finally commit
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

		return SUCCESS;
		
	}		

	public String getItemNr() {
		return itemNr;
	}

	public void setItemNr(String itemNr) {
		this.itemNr = itemNr;
	}

	public String getVersionAS() {
		return versionAS;
	}

	public void setVersionAS(String versionAS) {
		this.versionAS = versionAS;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public File getSvg() {
		return svg;
	}

	public void setSvg(File svg) {
		this.svg = svg;
	}

	public String getSvgContentType() {
		return svgContentType;
	}

	public void setSvgContentType(String svgContentType) {
		this.svgContentType = svgContentType;
	}

	public String getSvgFileName() {
		return svgFileName;
	}

	public void setSvgFileName(String svgFileName) {
		this.svgFileName = svgFileName;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
}
