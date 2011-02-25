package com.vi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.data.ItemFailure;
import com.vi.data.Unconfirmed;

public class GetUnconfirmed extends ActionSupport {
	//DataSource
	private BasicDataSource dataSource;
	
	//list for storing the database query results
	//so they can be iterated by Struts2
	private ArrayList<Unconfirmed> unconfirmedList;

	/**
	 * Action for linking to the unconfirmed POs page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String openShowUnconfirmed(){
		return SUCCESS;
	}
	
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Use getter method to query database
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public ArrayList<Unconfirmed> getUnconfirmedList() {
		unconfirmedList=new ArrayList<Unconfirmed>();
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			//query database for the information
			conn = dataSource.getConnection();
			//calling the stored procedure which returns a cursor to the data			
			//PROCEDURE GET_UNCONFIRMED(v_cursor_unconfirmed OUT CURSOR_IF);
			coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.GET_UNCONFIRMED(?)}");
			//prepare input and output arguments 			
			coll.registerOutParameter(1, OracleTypes.CURSOR);
			
			coll.execute();
			//get cursor
			ResultSet rs = (ResultSet) coll.getObject(1);
			//and process data			
			while (rs.next()) {
				System.out.println(	 rs.getString(1)+'\t'
									+rs.getString(2)+'\t'
									+rs.getString(3)); 
				
				// add the values
				Unconfirmed unconfirmed=new Unconfirmed();
				
				unconfirmed.setPoNo(rs.getString(1));
				unconfirmed.setWorkstationNr(rs.getString(2));
				unconfirmed.setWorkstationDescription(rs.getString(3));				
				
				unconfirmedList.add(unconfirmed);
				
			}
			

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
		
		return unconfirmedList;
	}

	public void setUnconfirmedList(ArrayList<Unconfirmed> unconfirmedList) {
		this.unconfirmedList = unconfirmedList;
	}	
	
}
