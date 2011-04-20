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
import com.vi.data.PoInformation;
import com.vi.data.PoPlanInformation;

public class PoInformationAction extends ActionSupport{
	//DataSource
	private BasicDataSource dataSource;
	
	private FormData formData;
	

	//list for storing the database query results
	//so they can be iterated by Struts2	
	private ArrayList<PoInformation> poInformationList;
	private ArrayList<PoPlanInformation>	poPlanInfoList;
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
		poPlanInfoList = new ArrayList<PoPlanInformation>();
		Connection conn = null;
		CallableStatement coll = null;
		try {
			conn = dataSource.getConnection();
			coll = conn.prepareCall("{call PCBVI.PKG_GET_PO.GET_PO(?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getPoNo());

			coll.registerOutParameter(2, OracleTypes.CURSOR);
			
			coll.execute();
			
			//get cursor
			ResultSet rs = (ResultSet) coll.getObject(2);
			//and process data
			while (rs.next()){
				PoPlanInformation poPlanInformation = new PoPlanInformation();
				
				poPlanInformation.setPoNo(rs.getString(1));
				poPlanInformation.setItemNo(rs.getString(2));
				poPlanInformation.setAs_(rs.getString(3));
				poPlanInformation.setPlanQty(rs.getString(4));
				poPlanInformation.setReleaseDate(rs.getString(5));
				//System.out.println(formData.getPoNo() +":" + rs.getString(1) + ":" +
				//		rs.getShort(2) +":"+rs.getString(3)+":"+rs.getString(4));
				
				poPlanInfoList.add(poPlanInformation);
			}
			rs.close();
			coll.close();
			
			//calling the stored procedure which returns a cursor to the data
			//PROCEDURE GET_WORKSTATION_INFO(in_po_no IN VARCHAR2,v_cursor_workstation_info OUT CURSOR_WI);   
			coll = conn.prepareCall("{call PCBVI.PKG_PO_HISTORY.GET_QUANTITY_INFO(?,?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getPoNo());
			coll.setString(2, formData.getWorkstationNr());
			coll.registerOutParameter(3, OracleTypes.CURSOR);
			
			coll.execute();
			//get cursor
			 rs = (ResultSet) coll.getObject(3);
			//and process data
						
			while (rs.next()) {
				
				//process data
				PoInformation poInformation=new PoInformation();
				
				poInformation.setItemNr(rs.getString(1));
				poInformation.setVersionAS(rs.getString(2));
				poInformation.setWorkstationNr(rs.getString(3));
				poInformation.setWorkstationDescription(rs.getString(4));
				poInformation.setTestedQty(String.valueOf(Integer.parseInt(rs.getString(5))+Integer.parseInt(rs.getString(6)) ));
				poInformation.setPassedQty(rs.getString(5));
				poInformation.setFailedQty(rs.getString(6));
				poInformation.setSide(rs.getString(7));
	
				poInformationList.add(poInformation);							
			
				/*System.out.println(	 	poInformation.getItemNr()+'\t' +
										poInformation.getVersionAS()+'\t' +
										poInformation.getWorkstationNr()+'\t' +
										poInformation.getWorkstationDescription()+'\t' +
										poInformation.getTestedQty()+'\t' +
										poInformation.getPassedQty()+'\t' +
										poInformation.getFailedQty());
				*/

			}
			rs.close();
			

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

	/**
	 * @return the poPlanInfoList
	 */
	public ArrayList<PoPlanInformation> getPoPlanInfoList() {
		if(!retrievedData){
			retrieveData();
		}
		return poPlanInfoList;
	}

	/**
	 * @param poPlanInfoList the poPlanInfoList to set
	 */
	public void setPoPlanInfoList(ArrayList<PoPlanInformation> poPlanInfoList) {
		this.poPlanInfoList = poPlanInfoList;
	}

	
	
}
