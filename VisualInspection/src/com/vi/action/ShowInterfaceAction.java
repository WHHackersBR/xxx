package com.vi.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.vi.dao.TabTestedDAO;
import com.vi.dao.TabWorkstationDAO;
import com.vi.data.FailureReportData;
import com.vi.data.FormData;
import com.vi.data.ErrMessage;
import com.vi.pojo.TabTested;
import com.vi.pojo.TabTestedId;
import com.vi.pojo.TabWorkstation;

@SuppressWarnings("serial")
public class ShowInterfaceAction extends ActionSupport {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public String errorOutput=""; 
	//private ErrMessage errMessage;
	//DataSource
	private BasicDataSource dataSource;
	
	// DAOs
	private TabWorkstationDAO tabWorkstationDAO;
	private TabTestedDAO tabTestedDAO;

	
	private FormData formData;
	private FailureReportData failureReportData;
	private TabTestedId tabTestedId=new TabTestedId();
	//private TabTested testedFID = new TabTested();
	
	/**
	 * Action for linking to the interface page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String goToInterface() {
		failureReportData.init(dataSource);
		
		return SUCCESS;
	}
	
	/**
	 * Action for linking to the store SVG in DB page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String goToStoreSvgInDB(){
		return SUCCESS;
	}
	/**
	 * dummy action for linking back to the interface page
	 * needed when using onclick button actions
	 * otherwise the last action is executed again
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String noAction(){
		return SUCCESS;
	}

	/**
	 * Action for filling the item number and version 
	 * lists according to the PO number
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String fillItemList() {
		// remove old stuff
		formData.getItemList().clear();
		formData.getVersionASList().clear();
		if(formData.getPoNo().equals("") ){
			errorOutput=ErrMessage.NullPO;
			return ERROR;
		}
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			//query database for the information			
			conn = dataSource.getConnection();
			//calling the stored procedure which returns a cursor to the data
			// PROCEDURE GET_PO(v_po_no IN VARCHAR2,v_cursor_po OUT CURSOR_PO);
			coll = conn.prepareCall("{call PCBVI.PKG_GET_PO.GET_PO(?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getPoNo());

			coll.registerOutParameter(2, OracleTypes.CURSOR);
			
			coll.execute();
			
			//get cursor
			ResultSet rs = (ResultSet) coll.getObject(2);
			//and process data
			
			while (rs.next()) {
				//System.out.println(rs.getString(1)); // po_no
				//System.out.println(rs.getString(2)); // a5e... article_no
				//System.out.println(rs.getString(3)); // 2. listenfeld last 2
														// of revision
				//System.out.println(rs.getString(4)); // quantity

				// add the values
				formData.getItemList().add(rs.getString(2));
				formData.getVersionASList().add(rs.getString(3));
				formData.setItemNr(rs.getString(2));
				formData.setVersionAS(rs.getString(3));
				
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

		return SUCCESS;
	}

	/**
	 * Action for filling the workstation description
	 * according to the workstation number
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String fillWorkstationDescription() {
		//use Hibernate to get data
		if (!formData.getWorkstationNr().equals("")) {
			TabWorkstation tabWorkstation = tabWorkstationDAO
					.findById(formData.getWorkstationNr());
			if (tabWorkstation != null) {
				formData.setWorkstationDescription( tabWorkstation.getEquipContent());
			} else {
				formData.setWorkstationDescription( null);
			}
			return SUCCESS;

		} else {
			errorOutput=ErrMessage.NullWS;
			formData.setWorkstationDescription( null);
			return ERROR;
		}
	}
	
	/**
	 * Action for storing operatorID
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String changeOperatorID(){
		return SUCCESS;
	}
	
	/**
	 * Action for storing side
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String changeSide(){
		return SUCCESS;
	}
	
	/**
	 * Action for filling the current fid
	 * according to the fid
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String fillCurrentFID(){
		errorOutput="";
		if(!(formData.getFid()==null || formData.getFid().equals(""))){
			formData.setCurrentFid(formData.getFid());
			formData.setFid("");
			if (formData.getPoNo()==null || formData.getPoNo().equals("")){
				errorOutput=ErrMessage.NullPO+ "<br>";
			}
			if( formData.getWorkstationNr()==null || formData.getWorkstationNr().equals("") ){
				errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
			}
			if (formData.getSide()==null || formData.getSide().equals("")){
				errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
			}
			if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
				errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
			}
			if (!errorOutput.isEmpty()){
				return SUCCESS;
			}
			TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(),formData.getWorkstationNr());
			TabTested testedFID= tabTestedDAO.findById(tabTestedId);
			if (testedFID == null ){
				Connection conn = null;
				CallableStatement coll = null;
				try {
					conn = dataSource.getConnection();
					//calling the stored procedure which stores the PO/test
					//PROCEDURE ADD_TESTED(  in_fid IN VARCHAR2,in_test_result IN CHAR,in_workstation_no IN VARCHAR2,
		            //in_item_no IN VARCHAR2,in_as IN VARCHAR2,in_po_no IN VARCHAR2,in_fake IN CHAR,
		            //in_operatior_id IN VARCHAR2,in_side IN VARCHAR2);
		            coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.ADD_TESTED(?,?,?,?,?,?,?,?,?)}");
					//prepare input and output arguments 
					coll.setString(1, formData.getCurrentFid());
					coll.setString(2, "P");
					coll.setString(3, formData.getWorkstationNr());
					coll.setString(4, formData.getItemNr());
					coll.setString(5, formData.getVersionAS());
					coll.setString(6, formData.getPoNo());
					coll.setString(7, "Y");
					coll.setString(8, formData.getOperatorID());
					coll.setString(9, formData.getSide());
							
					coll.execute();	
					formData.setFailed(true);
					errorOutput=formData.getCurrentFid()+ErrMessage.passFID;
				} catch (Exception e) {
					formData.setFailed(false);
					errorOutput=ErrMessage.failInsertFID;
					e.printStackTrace();
				} finally {
					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							formData.setFailed(false);
							errorOutput=ErrMessage.failInsertFID;
							e.printStackTrace();
						}
					}
	
					if (coll != null) {
						try {
							coll.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							formData.setFailed(false);
							errorOutput=ErrMessage.failInsertFID;
							e.printStackTrace();
						}
					}
				} 
			}else if(testedFID.getTestResult()=="P"){
				formData.setFailed(true);
				errorOutput=formData.getCurrentFid()+ErrMessage.pastFID;
			}else{
				formData.setFailed(true);
				errorOutput=formData.getCurrentFid()+ErrMessage.failedFID;
			}
		
		}else{
			errorOutput=ErrMessage.NullFID;
		}
		return SUCCESS;
	}
	

	
	/**
	 * Action adding a PO to the TAB_TESTED database table 
	 * (as passed, at least at first)
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String startPO() {		
		
		//numbers of the files i have (poNr)
		//800003874527 -> A5E00994011-08.svg
		//800003923175 -> A5E00758720-05.svg
		//800004602370 -> A5E00758712-14.svg
		//800004996525 -> A5E02507171-06.svg
		errorOutput="";
		if (formData.getPoNo()==null || formData.getPoNo().equals("")){
			errorOutput=ErrMessage.NullPO+ "<br>";
		}
		if( formData.getWorkstationNr()==null || formData.getWorkstationNr().equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		if (formData.getSide()==null || formData.getSide().equals("")){
			errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
		}
		if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
			errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
		}
		if (errorOutput.equals("")){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * Action adding a PO to the TAB_TESTED database table 
	 * (as passed, at least at first), it also changes the test 
	 * result to failed
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String addFailureReport() {
		
		formData.setFailed(true);		
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			conn = dataSource.getConnection();
			//calling the stored procedure which changes the test result in TAB_TESTED			
			//  PROCEDURE CHANGE_TESTRESULT(  in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2,
			//in_test_result IN CHAR);                        			
			coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.CHANGE_TESTRESULT(?,?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getCurrentFid());
			coll.setString(2, formData.getWorkstationNr());
			coll.setString(3, "F");
			
			coll.execute();	
			coll.close();
			
			//calling the stored procedure which adds a failure in TAB_FAILURE						
			//PROCEDURE ADD_FAILURE(in_fid IN VARCHAR2,in_workstation_no IN VARCHAR2,                        
			//in_piece_location IN VARCHAR2,in_component_location IN VARCHAR2,
            //in_failure_code IN VARCHAR2,in_failure_description IN VARCHAR2,
            //in_side IN VARCHAR2);
			coll = conn.prepareCall("{call PCBVI.PKG_TESTRECORDS.ADD_FAILURE(?,?,?,?,?,?,?)}");
			//prepare input and output arguments 
			coll.setString(1, formData.getCurrentFid());
			coll.setString(2, formData.getWorkstationNr());
			coll.setString(3, failureReportData.getPositionNr());
			coll.setString(4, failureReportData.getPartname());
			coll.setString(5, failureReportData.getFailureCode());
			coll.setString(6, failureReportData.getFailureDescription());
			coll.setString(7, formData.getSide());			
			
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
	
	public String testComplete(){
		
		formData.setFailed(false);
		formData.setCurrentFid(null);
		formData.setFid(null);
		formData.setItemNr(null);
		formData.setVersionAS(null);
		
		return SUCCESS;
	}

	public TabWorkstationDAO getTabWorkstationDAO() {
		return tabWorkstationDAO;
	}

	public void setTabWorkstationDAO(TabWorkstationDAO tabWorkstationDAO) {
		this.tabWorkstationDAO = tabWorkstationDAO;
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

	public FailureReportData getFailureReportData() {
		return failureReportData;
	}

	public String getErrorOutput() {
		return errorOutput;
	}

	public void setErrorOutput(String errorOutput) {
		this.errorOutput = errorOutput;
	}

	public TabTestedDAO getTabTestedDAO() {
		return tabTestedDAO;
	}

	public void setTabTestedDAO(TabTestedDAO tabTestedDAO) {
		this.tabTestedDAO = tabTestedDAO;
	}

	public TabTestedId getTabTestedId() {
		return tabTestedId;
	}

	public void setTabTestedId(TabTestedId tabTestedId) {
		this.tabTestedId = tabTestedId;
	}


	public void setFailureReportData(FailureReportData failureReportData) {
		this.failureReportData = failureReportData;
	}


}
