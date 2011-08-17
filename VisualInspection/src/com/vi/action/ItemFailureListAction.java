package com.vi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.dao.TabFailureDAO;
import com.vi.dao.TabTestedDAO;
import com.vi.dao.TabWorkstationDAO;
import com.vi.data.ErrMessage;
import com.vi.data.FailureReportData;
import com.vi.data.FormData;
import com.vi.data.ItemFailure;
import com.vi.pojo.TabTested;
import com.vi.pojo.TabTestedId;
import com.vi.pojo.TabWorkstation;

public class ItemFailureListAction extends ActionSupport {
	//DataSource
	private BasicDataSource dataSource;
	private TabTestedDAO tabTestedDAO;
	private TabFailureDAO tabFailureDAO;
	private TabWorkstationDAO tabWorkstationDAO;
	
	private FormData formData;
	//list for storing the database query results
	//so they can be iterated by Struts2
	private ArrayList<ItemFailure> itemFailureList;
	private FailureReportData failureReportData;

	private String oldFailureCode;
	private String machType;
	private String side;

	public String errorOutput=""; 

	/**
	 * Action for linking to the failures of the current PO page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String openItemFailureList() {
		
		errorOutput="";
		if (formData.getPoNo()==null || formData.getPoNo().equals("")){
			errorOutput=ErrMessage.NullPO+ "<br>";
		}
		if (formData.getItemNr()==null || formData.getItemNr().equals("")){
			errorOutput=errorOutput+ErrMessage.NullItemNr+ "<br>";
		}
		if (formData.getVersionAS()==null || formData.getVersionAS().equals("")){
			errorOutput=errorOutput+ErrMessage.NullVersion+ "<br>";
		}
		if( formData.getWorkstationNr()==null || formData.getWorkstationNr().equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		
		if (errorOutput.equals("")){
			return SUCCESS;
		}else{
			return ERROR;
		}

	}
	
	/**
	 * Delete all records of the current PO failures from the TAB_FAILURE database table
	 * Then change test result in TAB_TESTED to 'P' (passed)
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String changeToPassed(){
		
		errorOutput = "";
		Connection conn = null;
		CallableStatement coll = null;
		try {
			TabTestedId tabTestedId = new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
			TabTested tabTested = tabTestedDAO.findById(tabTestedId);
			if (tabTested.getFake().equals("N")){
				errorOutput = ErrMessage.ConfirmedFid;
			}else{
				conn = dataSource.getConnection();
				//calling the stored procedure which deletes all failures of the current PO
				//  PROCEDURE DELETE_ALL_ITEM_FAILURES(in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2);   
				coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.DELETE_ALL_ITEM_FAILURES(?,?)}");
				//prepare input and output arguments 
				coll.setString(1, formData.getCurrentFid());
				coll.setString(2, formData.getWorkstationNr());
				
				coll.execute();		
				coll.close();			
			
				coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.CHANGE_TESTRESULT(?,?,?)}");
				coll.setString(1, formData.getCurrentFid());
				coll.setString(2, formData.getWorkstationNr());
				coll.setString(3, "P");
				
				coll.execute();	
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

		
		if (errorOutput.equals("")){
			formData.setFailed(false);		
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * Delete a record from the TAB_FAILURE database table
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String delFailure(){
		
		errorOutput = "";
		Connection conn = null;
		CallableStatement coll = null;
		try {
			TabTestedId tabTestedId = new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
			TabTested tabTested = tabTestedDAO.findById(tabTestedId);
			if (tabTested.getFake().equals("N")){
				errorOutput = ErrMessage.ConfirmedFid;
			}else{
				conn = dataSource.getConnection();
				//calling the stored procedure which deletes a failure entry
				//PROCEDURE DELETE_FAILURE(in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2,                        
	            //in_piece_location IN VARCHAR2,in_component_location IN VARCHAR2,
	            //in_failure_code IN VARCHAR2);  			
				coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.DELETE_FAILURE(?,?,?,?,?)}");
				//prepare input and output arguments 
				coll.setString(1, formData.getCurrentFid());
				coll.setString(2, formData.getWorkstationNr());
				coll.setString(3, failureReportData.getPositionNr());
				coll.setString(4, failureReportData.getPartname());
				coll.setString(5, failureReportData.getFailureCode());
				
				coll.execute();		
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

		if (errorOutput.equals("")){
			formData.setFailed(false);		
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * Action for linking to the edit failure page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String goToEditFailureReport() {
		
		oldFailureCode=failureReportData.getFailureCode();
		TabTestedId tabTestedId = new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
		TabTested tabTested = tabTestedDAO.findById(tabTestedId);
		if (tabTested.getFake().equals("N")){
			errorOutput = ErrMessage.ConfirmedFid;
			return ERROR;
		}else{
			
			return SUCCESS;
		}
	}
	
	/**
	 * Change a record in the TAB_FAILURE database table
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String editFailure(){
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			conn = dataSource.getConnection();
			//calling the stored procedure which edits a failure in TAB_FAILURE
			//PROCEDURE EDIT_FAILURE(in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2,                        
            //in_piece_location IN VARCHAR2,in_component_location IN VARCHAR2,
            //in_old_failure_code IN VARCHAR2,in_new_failure_code IN VARCHAR2,
			//in_failure_description IN VARCHAR2);			
			coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.EDIT_FAILURE(?,?,?,?,?,?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getCurrentFid());
			coll.setString(2, formData.getWorkstationNr());
			coll.setString(3, failureReportData.getPositionNr());
			coll.setString(4, failureReportData.getPartname());
			coll.setString(5, oldFailureCode);
			coll.setString(6, failureReportData.getFailureCode());			
			coll.setString(7, failureReportData.getFailureDescription());
			
			coll.execute();		
			
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
	
	public String cancelEditFailure(){
		
		return SUCCESS;
	}
	
	//getters and setters
	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}


	/**
	 * Use getter method to query database
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public ArrayList<ItemFailure> getItemFailureList() {
		
		itemFailureList=new ArrayList<ItemFailure>();
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			//query database for the information
			conn = dataSource.getConnection();
			//calling the stored procedure which returns a cursor to the data			
			//PROCEDURE GET_ALL_ITEM_FAILURES(in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2,
			//v_cursor_item_failures OUT CURSOR_IF);                           			 
			coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.GET_ALL_ITEM_FAILURES(?,?,?)}");
			//prepare input and output arguments 			
			coll.setString(1, formData.getCurrentFid());
			coll.setString(2, formData.getWorkstationNr());

			coll.registerOutParameter(3, OracleTypes.CURSOR);
			
			coll.execute();
			//get cursor
			ResultSet rs = (ResultSet) coll.getObject(3);
			//and process data			
			while (rs.next()) {
				/*System.out.println(	 rs.getString(1)+'\t'
									+rs.getString(2)+'\t'
									+rs.getString(3)+'\t'
									+rs.getString(4)+'\t'
									+rs.getString(5)+'\t'
									+rs.getString(6)+'\t'
									+rs.getString(7)); */
				
				// add the values
				ItemFailure itemFailure=new ItemFailure();
				itemFailure.setFid(rs.getString(1));
				itemFailure.setPieceLocation(rs.getString(2));
				itemFailure.setComponentLocation(rs.getString(3));
				itemFailure.setFailureCode(rs.getString(4));
				itemFailure.setFailureDefinition(rs.getString(5));
				itemFailure.setFailureDescription(rs.getString(6));
				itemFailure.setTime(rs.getString(7));
				
				itemFailureList.add(itemFailure);
				
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
		
		return itemFailureList;
	}

	public void setItemFailureList(ArrayList<ItemFailure> itemFailureList) {
		this.itemFailureList = itemFailureList;
	}

	public BasicDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(BasicDataSource dataSource) {
		this.dataSource = dataSource;
	}



	public FailureReportData getFailureReportData() {
		//make sure that failure codes are retrieved from DB
		failureReportData.init(dataSource);
		return failureReportData;
	}

	public void setFailureReportData(FailureReportData failureReportData) {
		this.failureReportData = failureReportData;
	}

	public String getOldFailureCode() {
		return oldFailureCode;
	}

	public void setOldFailureCode(String oldFailureCode) {
		this.oldFailureCode = oldFailureCode;
	}


	/**
	 * @return the tabTestedDAO
	 */
	public TabTestedDAO getTabTestedDAO() {
		return tabTestedDAO;
	}

	/**
	 * @param tabTestedDAO the tabTestedDAO to set
	 */
	public void setTabTestedDAO(TabTestedDAO tabTestedDAO) {
		this.tabTestedDAO = tabTestedDAO;
	}

	/**
	 * @return the tabFailureDAO
	 */
	public TabFailureDAO getTabFailureDAO() {
		return tabFailureDAO;
	}

	/**
	 * @param tabFailureDAO the tabFailureDAO to set
	 */
	public void setTabFailureDAO(TabFailureDAO tabFailureDAO) {
		this.tabFailureDAO = tabFailureDAO;
	}

	/**
	 * @return the tabWorkstationDAO
	 */
	public TabWorkstationDAO getTabWorkstationDAO() {
		return tabWorkstationDAO;
	}

	/**
	 * @param tabWorkstationDAO the tabWorkstationDAO to set
	 */
	public void setTabWorkstationDAO(TabWorkstationDAO tabWorkstationDAO) {
		this.tabWorkstationDAO = tabWorkstationDAO;
	}

	/**
	 * @return the machType
	 */
	public String getMachType() {
		TabWorkstation tabWorkstation = tabWorkstationDAO.findById(formData.getWorkstationNr());
		machType	=	tabWorkstation.getMachType();
		return machType;
	}

	/**
	 * @param machType the machType to set
	 */
	public void setMachType(String machType) {
		this.machType = machType;
	}

	/**
	 * @return the side
	 */
	public String getSide() {
		TabWorkstation tabWorkstation = tabWorkstationDAO.findById(formData.getWorkstationNr());
		side	=	tabWorkstation.getSide();
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(String side) {
		this.side = side;
	}

	
}
