package com.vi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.data.FormData;
import com.vi.data.ItemFailure;
import com.vi.data.PoInformation;

public class PoInformationAction extends ActionSupport{
	//DataSource
	private BasicDataSource dataSource;
	
	private FormData formData;
	
	private String planQuantity;
	
	//list for storing the database query results
	//so they can be iterated by Struts2	
	private ArrayList<PoInformation> poInformationList;
	private boolean retrievedData;
	
	/**
	 * Action for linking to the  PO information/history page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String openPoInformation(){
		retrievedData=false;
		return SUCCESS;
	}
	
	/**
	 * Function to get the data from the database
	 * to populate the lists and set the counts
	 */
	private void retrieveData(){
		poInformationList=new ArrayList<PoInformation>();		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			//query database for the information			
			conn = dataSource.getConnection();
			//calling the stored procedure which returns the quantity
			//PROCEDURE GET_PO_QUANTITY(in_po_no IN VARCHAR2,out_quantity OUT VARCHAR2);    
			coll = conn.prepareCall("{call PCBVI.PKG_PO_HISTORY.GET_PO_QUANTITY(?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getPoNo());			

			coll.registerOutParameter(2, OracleTypes.NUMBER);
			
			coll.execute();
			//get quantity
			planQuantity=coll.getString(2);
			coll.close();
			
			//calling the stored procedure which returns a cursor to the data
			//PROCEDURE GET_WORKSTATION_INFO(in_po_no IN VARCHAR2,v_cursor_workstation_info OUT CURSOR_WI);   
			coll = conn.prepareCall("{call PCBVI.PKG_PO_HISTORY.GET_WORKSTATION_INFO(?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getPoNo());			

			coll.registerOutParameter(2, OracleTypes.CURSOR);
			
			coll.execute();
			//get cursor
			ResultSet rs = (ResultSet) coll.getObject(2);
			//and process data
						
			while (rs.next()) {
				//calling the stored procedure which returns the passed POs quantity
				//PROCEDURE GET_QUANTITY_INFO(in_po_no IN VARCHAR2,in_item_no IN VARCHAR2,
				//in_as_ IN VARCHAR2,in_workstation_no IN VARCHAR2,in_test_result IN CHAR,out_qty OUT NUMBER); 
				CallableStatement coll2 = conn.prepareCall("{call PCBVI.PKG_PO_HISTORY.GET_QUANTITY_INFO(?,?,?,?,?,?)}");
				//prepare input and output arguments 
				coll2.setString(1, formData.getPoNo());			
				coll2.setString(2, rs.getString(1));	
				coll2.setString(3, rs.getString(2));	
				coll2.setString(4, rs.getString(3));
				coll2.setString(5, "P");
				
				coll2.registerOutParameter(6, OracleTypes.NUMBER);
				
				coll2.execute();
				
				int passQty=coll2.getInt(6);
				coll2.close();
				
				//calling the stored procedure which returns the failed POs quantity
				//PROCEDURE GET_QUANTITY_INFO(in_po_no IN VARCHAR2,in_item_no IN VARCHAR2,
				//in_as_ IN VARCHAR2,in_workstation_no IN VARCHAR2,in_test_result IN CHAR,out_qty OUT NUMBER); 				
				coll2 = conn.prepareCall("{call PCBVI.PKG_PO_HISTORY.GET_QUANTITY_INFO(?,?,?,?,?,?)}");
				//prepare input and output arguments 
				coll2.setString(1, formData.getPoNo());			
				coll2.setString(2, rs.getString(1));	
				coll2.setString(3, rs.getString(2));	
				coll2.setString(4, rs.getString(3));
				coll2.setString(5, "F");
				
				coll2.registerOutParameter(6, OracleTypes.NUMBER);
				
				coll2.execute();
				
				int failQty=coll2.getInt(6);
				coll2.close();
				
				//process data
				PoInformation poInformation=new PoInformation();
				
				poInformation.setItemNr(rs.getString(1));
				poInformation.setVersionAS(rs.getString(2));
				poInformation.setWorkstationNr(rs.getString(3));
				poInformation.setWorkstationDescription(rs.getString(4));
				poInformation.setTestedQty(String.valueOf(passQty+failQty));
				poInformation.setPassedQty(String.valueOf(passQty));
				poInformation.setFailedQty(String.valueOf(failQty));
	
				poInformationList.add(poInformation);							
			
				System.out.println(	 	poInformation.getItemNr()+'\t' +
										poInformation.getVersionAS()+'\t' +
										poInformation.getWorkstationNr()+'\t' +
										poInformation.getWorkstationDescription()+'\t' +
										poInformation.getTestedQty()+'\t' +
										poInformation.getPassedQty()+'\t' +
										poInformation.getFailedQty());

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
		
		retrievedData=true;
	}
	
	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}

	public String getPlanQuantity() {
		//only retrieve the data once, makes no sense to split query into many queries
		//but have to make sure it's done when the first getter is called
		if(!retrievedData){
			retrieveData();
		}
		return planQuantity;
	}

	public void setPlanQuantity(String planQuantity) {
		this.planQuantity = planQuantity;
	}

	public ArrayList<PoInformation> getPoInformationList() {
		//only retrieve the data once, makes no sense to split query into many queries
		//but have to make sure it's done when the first getter is called
		if(!retrievedData){
			retrieveData();
		}
		return poInformationList;
	}

	public void setPoInformationList(ArrayList<PoInformation> poInformationList) {
		this.poInformationList = poInformationList;
	}
	
	
}
