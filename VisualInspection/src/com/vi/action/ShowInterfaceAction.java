package com.vi.action;

import java.io.File;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.dao.SvgFileDAO;
import com.vi.dao.TabFailureDAO;
import com.vi.dao.TabTestedDAO;
import com.vi.dao.TabViPoDAO;
import com.vi.dao.TabWorkstationDAO;
import com.vi.dao.VFidHistDAO;
import com.vi.dao.VPoDAO;
import com.vi.data.FailureReportData;
import com.vi.data.FormData;
import com.vi.data.ErrMessage;
import com.vi.data.PositionNr;
import com.vi.pojo.SvgFile;
import com.vi.pojo.SvgFileId;
import com.vi.pojo.TabFailure;
import com.vi.pojo.TabFailureId;
import com.vi.pojo.TabTested;
import com.vi.pojo.TabTestedId;
import com.vi.pojo.TabViPo;
import com.vi.pojo.TabViPoId;
import com.vi.pojo.TabWorkstation;
import com.vi.pojo.VFidHist;
import com.vi.pojo.VFidHistId;
import com.vi.pojo.VPo;
import com.vi.pojo.VPoId;

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
	private TabTestedDAO	tabTestedDAO;
	private TabFailureDAO tabFailureDAO;
	private VPoDAO	vPoDAO;
	private TabViPoDAO	tabViPoDAO;
	private SvgFileDAO	svgFileDAO;
	//private 
	

	private FormData formData;
	private FailureReportData failureReportData;
	private TabTestedId tabTestedId=new TabTestedId();
	private String v_machType;
    private String v_side;
	//private TabTested testedFID = new TabTested();
    
    public ShowInterfaceAction(){

    }
	
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
		errorOutput="";
		formData.getItemList().clear();
		formData.getVersionASList().clear();
		formData.setItemNr("");
		formData.setVersionAS("");
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
			
			//get available item number/version that has been defined
			SvgFileId svgFileId;
			SvgFile	svgFile=null;
			while (rs.next()) {
				//System.out.println(rs.getString(1)); // po_no
				//System.out.println(rs.getString(2)); // a5e... article_no
				//System.out.println(rs.getString(3)); // 2. listenfeld last 2
														// of revision
				//System.out.println(rs.getString(4)); // quantity

				// add the values
				//get available item number/version that has been defined
				svgFileId = new SvgFileId(rs.getString(2),rs.getString(3), "0");
				svgFile = svgFileDAO.findById(svgFileId);
				if (svgFile != null ){
					formData.getItemList().add(rs.getString(2));
					formData.getVersionASList().add(rs.getString(3));
					formData.setItemNr(rs.getString(2));
					formData.setVersionAS(rs.getString(3));
					break;
				}

			}
			if (svgFile == null ){
				errorOutput=ErrMessage.noItem;
			}

		} catch (Exception e) {
			if (!e.getMessage().equals("Cursor is closed.")){
				e.printStackTrace();
			}
			errorOutput=ErrMessage.WrongPO;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorOutput=ErrMessage.WrongPO;
				}
			}

			if (coll != null) {
				try {
					coll.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					errorOutput=ErrMessage.WrongPO;
				}
			}
		} 
		if (errorOutput.isEmpty()){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}

	/**
	 * Action for filling the workstation description
	 * according to the workstation number
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String fillWorkstationDescription() {
		//use Hibernate to get data
		if (formData.getWorkstationNr() != null && !formData.getWorkstationNr().equals("")) {
			TabWorkstation tabWorkstation = tabWorkstationDAO
					.findById(formData.getWorkstationNr());
			if (tabWorkstation != null) {
				formData.setWorkstationDescription( tabWorkstation.getEquipContent());
				v_machType = "SCT"+tabWorkstation.getCode();
			} else {
				formData.setWorkstationDescription( null);
				v_machType = "SCTVITEMP";
			}
			return SUCCESS;

		} else {
			errorOutput=ErrMessage.NullWS;
			formData.setWorkstationDescription( null);
			return ERROR;
		}
	}
	/**
	 * Action for get the quantity of PO finished/planed
	 * according to PO number
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String poCompleted() {
		//use Hibernate to get data
		//List vPo<VPo>;
		try {
			if (formData.getWorkstationNr() != null) {
				TabWorkstation tabWorkstation = tabWorkstationDAO
						.findById(formData.getWorkstationNr());
				if (tabWorkstation != null) {
					formData.setWorkstationDescription( tabWorkstation.getEquipContent());
					v_machType = "SCT"+tabWorkstation.getCode();
				} else {
					formData.setWorkstationDescription( null);
					v_machType = "SCTVITEMP";
				}
			}
			VPoId vPoId = new VPoId(formData.getPoNo(),formData.getItemNr(),v_machType);
			VPo vPo = vPoDAO.findById(vPoId);
			 if (vPo != null){
				formData.setPoCompleted(vPo.getQtyProduce().toString() + "/" + vPo.getQtyPlan().toString());
			 }
			 else {
				formData.setPoCompleted("");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			//e.getCause();
		}
		return SUCCESS;
	}
	

	/**
	 * Action for storing operatorID
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String changeOperatorID(){
		/*if (formData.getPoNo() != null && !formData.getPoNo().isEmpty()){
			try{
	            Date   now   =   new   Date(); 
	            SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
				TabViPoId tabViPoId=new TabViPoId(formData.getPoNo(), formData.getWorkstationNr(), formData.getItemNr(), 
						formData.getVersionAS(), formData.getSide(), formData.getOperatorID(),dateFormat.format(now));
				TabViPo	tabViPo = new TabViPo(tabViPoId);
				tabViPoDAO.attachDirty(tabViPo);			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}*/
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
			if (formData.getPoNo()==null || formData.getPoNo().equals("")){
				errorOutput=ErrMessage.NullPO+ "<br>";
			}
			if (formData.getItemNr()==null || formData.getItemNr().equals("")){
				errorOutput=errorOutput+ErrMessage.NullItemNr+ "<br>";
			}
			if (formData.getVersionAS()==null || formData.getVersionAS().equals("")){
				errorOutput=errorOutput+ErrMessage.NullVersion+ "<br>";
			}
			if( formData.getWorkstationDescription()==null || formData.getWorkstationDescription().equals("") ){
				errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
			}
			if (formData.getSide()==null || formData.getSide().equals("")){
				errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
			}
			if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
				errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
			}
			if (!errorOutput.isEmpty()){
				formData.setFailed(false);
				return ERROR;
			}
			formData.setCurrentFid(formData.getFid());
			formData.setFid("");
			TabTested	tabTested = new TabTested();
			//System.out.println();
			try {
	    		if (!formData.getWorkstationNr().equals("")) {
	    			TabWorkstation tabWorkstation = tabWorkstationDAO
	    					.findById(formData.getWorkstationNr());
	    			//System.out.println(tabWorkstation.getMachId()+":"+tabWorkstation);
	    			if (tabWorkstation != null && tabWorkstation.getMachId()!=null) {
	    				//v_mach_id = tabWorkstation.getMachId();
	    				v_side = tabWorkstation.getSide();
	    				v_machType = "SCT"+tabWorkstation.getCode();
	    			} else {
		    			//v_mach_id = formData.getWorkstationNr();
		    			v_side = formData.getSide();
	    				v_machType = "SCTVITEMP";
	    			}
	    		}else {
	    			//v_mach_id = formData.getWorkstationNr();
	    			v_side = formData.getSide();
	    		}
	    		TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
				tabTested = tabTestedDAO.findById(tabTestedId);

			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//e.getCause();
			}
			if ( tabTested == null ){
				Connection conn = null;
				CallableStatement coll = null;
				try {
					Date   now   =   new   Date(); 
		            SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
					TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
					tabTested=new TabTested(tabTestedId, "P", formData.getItemNr(), formData.getVersionAS(), formData.getPoNo(), "Y", v_side);
					tabTested.setEventTime(new Timestamp(new Date().getTime()));
					tabTested.setMachTime(dateFormat.format(now));
					tabTested.setOperatorId(formData.getOperatorID());
					tabTested.setTransferBit(BigDecimal.valueOf(0));
					
					formData.setFailed(true);
					errorOutput= formData.getCurrentFid() + ":" + ErrMessage.passFID;
					

					VPoId vPoId = new VPoId(formData.getPoNo(), formData.getItemNr(), v_machType);
					VPo	  vPo = vPoDAO.findById(vPoId);
					if (vPo != null) {
						vPo.setQtyProduce(BigDecimal.valueOf(vPo.getQtyProduce().intValue()+1));
						vPo.setEventTime(new Timestamp(new Date().getTime()));
						vPo.setTransferBit("0");
					}else {
						vPo = new VPo(vPoId);
						vPo.setQtyProduce(BigDecimal.valueOf(1));

						conn = dataSource.getConnection();
						coll = conn.prepareCall("{call PCBVI.PKG_GET_PO.GET_PO(?,?)}");
						//prepare input and output arguments 
						coll.setString(1, formData.getPoNo());
						coll.registerOutParameter(2, OracleTypes.CURSOR);
						coll.execute();
						//get cursor
						ResultSet rs = (ResultSet) coll.getObject(2);
						//and process data
						while(rs.next()){
							if(rs.getString(2).equals(formData.getItemNr())){
								vPo.setQtyPlan(BigDecimal.valueOf(Integer.valueOf(rs.getString(4))));
							}
							else {
								vPo.setQtyPlan(BigDecimal.valueOf(Integer.valueOf(0)));
							}
						}
						vPo.setEstand("00");
						vPo.setFirstTime(new Timestamp(new Date().getTime()));
						vPo.setEventTime(new Timestamp(new Date().getTime()));
						vPo.setRevision(formData.getVersionAS());
						vPo.setTransferBit("0");
						rs.close();
						
					}
					vPoDAO.attachDirty(vPo);
					tabTestedDAO.attachDirty(tabTested);
					
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
			}else{ 
				try {
					if(tabTested.getTestResult().equals("P")){
						formData.setFailed(true);
						if (tabTested.getFake().equals("N")){
							errorOutput = formData.getCurrentFid() + ErrMessage.pastFID +"," + ErrMessage.Confirmed;
						}else {
							errorOutput=formData.getCurrentFid() + ErrMessage.pastFID + "," + ErrMessage.NotConfirmed;
						}
					}else{
						formData.setFailed(false);
						if (tabTested.getFake().equals("N")){
							errorOutput=formData.getCurrentFid() + ErrMessage.failedFID +"," + ErrMessage.Confirmed;
						}else {
							errorOutput=formData.getCurrentFid() + ErrMessage.failedFID + "," + ErrMessage.NotConfirmed;
						}
		
					}
				}catch (Exception e) {
					formData.setFailed(false);
					errorOutput=ErrMessage.failInsertFID;
					e.printStackTrace();
				}
			}
		
		}else{
			formData.setFailed(false);
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
		//800005416353 -> A5E02358154-03.svg
		//800005212291 -> A5E02358165-02.svg
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
		if( formData.getWorkstationDescription()==null || formData.getWorkstationDescription().equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		if (formData.getSide()==null || formData.getSide().equals("")){
			errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
		}
		if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
			errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
		}
		try{
            formData.setCurrentFid("");
            formData.setFid("");
			Date   now   =   new   Date(); 
            SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
			TabViPoId tabViPoId=new TabViPoId(formData.getPoNo(), formData.getWorkstationNr(), formData.getItemNr(), 
					formData.getVersionAS(), formData.getSide(), formData.getOperatorID(),dateFormat.format(now));
			TabViPo	tabViPo = new TabViPo(tabViPoId);
			tabViPoDAO.attachDirty(tabViPo);			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			errorOutput = errorOutput + "Cannot save PO basic information<br>";
		}
		if (errorOutput.equals("")){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * Action for linking to the failure report page
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String goToFailureReport() {
		//get and set partname
		String partname=ServletActionContext.getRequest().getParameter("partname");
		//String partname=ServletActionContext.getRequest().QueryString[
		failureReportData.setPartname(partname);
		failureReportData.setFailureDescription(null);
		failureReportData.setPositionNr(null);
		failureReportData.setFailureCode(null);
		if((formData.getCurrentFid()==null || formData.getCurrentFid().isEmpty()) ){
			errorOutput=ErrMessage.NullFID+ "<br>";
		}
		if (formData.getPoNo()==null || formData.getPoNo().equals("")){
			errorOutput=ErrMessage.NullPO+ "<br>";
		}
		if( formData.getWorkstationDescription()==null || formData.getWorkstationDescription().equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		if (formData.getSide()==null || formData.getSide().equals("")){
			errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
		}
		if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
			errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
		}
		//failureReportData.getFailureCodes().clear();
		//failureReportData.init(dataSource);

		Connection conn = null;
		CallableStatement coll = null;
		try {
			conn=dataSource.getConnection();
			coll = conn.prepareCall("{call PCBVI.PKG_GET_FAILURECODES.GET_POSITIONNRS(?,?)}");			
			//prepare input and output arguments
			coll.setString(1, formData.getItemNr());
			coll.registerOutParameter(2, OracleTypes.VARCHAR);
			
			coll.execute();
			
			//get data and process it
			failureReportData.getPositionNrs().clear();
			PositionNr positionNr;
			for (int quantity = 1; quantity <= coll.getInt(2); quantity++) {
				int positionAbbr=quantity;
				int positionDefinition=quantity;
				
				//System.out.println(formData.getItemNr()+":"+rs.getString(1));
				// add the values
				positionNr=new PositionNr();
				positionNr.setPositionNrAbbr(String.valueOf(positionAbbr));
				positionNr.setPositionNrName(String.valueOf(positionDefinition));
				failureReportData.getPositionNrs().add(positionNr);
			}
			positionNr=new PositionNr();
			positionNr.setPositionNrAbbr(String.valueOf(-1));
			positionNr.setPositionNrName(ErrMessage.singlePanel);
			failureReportData.getPositionNrs().add(positionNr);

			TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
			TabTested tabTested = tabTestedDAO.findById(tabTestedId);
			
			if (tabTested != null && tabTested.getFake().equals("Y") ){
				if( !tabTested.getTestResult().equals("F") ){
					formData.setFailed(true);		
					errorOutput = ErrMessage.setFIDfail;
				}
			}else{
				errorOutput = ErrMessage.NUllorConfirmFID;
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
		
		
		if (errorOutput.isEmpty()){
			//formData.setFailed(false);
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
		
		errorOutput="";
		
		try {
			TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
			TabTested tabTested = tabTestedDAO.findById(tabTestedId);
			
			if (tabTested != null){
				TabFailureId tabFailureId=new TabFailureId(formData.getCurrentFid(),formData.getWorkstationNr(),
						failureReportData.getPositionNr(), failureReportData.getPartname(),failureReportData.getFailureCode());
				TabFailure tabFailure = new TabFailure(tabFailureId);
	            //Date   now   =   new   Date();
	            //SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
	            tabFailure.setMachTime(tabTested.getMachTime());
	            tabFailure.setEventTime(new Timestamp(new Date().getTime()));
	            tabFailure.setFailureDescription(failureReportData.getFailureDescription());
	            tabFailure.setSide(formData.getSide());
	            tabFailure.setTransferBit(BigDecimal.valueOf(0));
	            tabFailureDAO.attachDirty(tabFailure);
				formData.setFailed(false);		
	
				}
			} catch (Exception e) {
			e.printStackTrace();
		} finally {
		} 

		if (errorOutput.equals("")){
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	public String testComplete(){
		
		errorOutput = "";
		try {
			TabTestedId tabTestedId=new TabTestedId(formData.getCurrentFid(), formData.getWorkstationNr());
			TabTested tabTested = tabTestedDAO.findById(tabTestedId);
			
			if (tabTested != null && tabTested.getFake().equals("Y") ){
				tabTested.setTestResult("F");
				tabTested = tabTestedDAO.merge(tabTested);
				formData.setFailed(false);		
			}else {
				errorOutput = ErrMessage.ConfirmedFid;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		} 
		return SUCCESS;
	}
	public String help(){
		
		errorOutput = "";
		try {
			SvgFileId svgFileId = new SvgFileId(formData.getItemNr(), formData.getVersionAS(), "0");
			SvgFile	svgFile = svgFileDAO.findById(svgFileId);
			if (svgFile != null && svgFile.getUserManual() != null && !svgFile.getUserManual().isEmpty()){
				File oldFile=new File(ServletActionContext.getServletContext().getRealPath("userManual")+"/"+svgFile.getUserManual());
				if (!oldFile.exists()){
					formData.setViDoc("");
					formData.setViDocReal("");
				}else {
					formData.setViDoc(svgFile.getUserManual().replaceFirst(formData.getItemNr()+"-"+formData.getVersionAS()+"-", ""));
					formData.setViDocReal("userManual/"+svgFile.getUserManual());
				}
			}else {
				formData.setViDoc("");
				formData.setViDocReal("");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		} 
		return SUCCESS;
	}

	public String saveFailureReportData(){
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

	public TabTestedId getTabTestedId() {
		return tabTestedId;
	}

	public void setTabTestedId(TabTestedId tabTestedId) {
		this.tabTestedId = tabTestedId;
	}


	public void setFailureReportData(FailureReportData failureReportData) {
		this.failureReportData = failureReportData;
	}

	/**
	 * @return the vPoDAO
	 */
	public VPoDAO getvPoDAO() {
		return vPoDAO;
	}

	/**
	 * @param vPoDAO the vPoDAO to set
	 */
	public void setvPoDAO(VPoDAO vPoDAO) {
		this.vPoDAO = vPoDAO;
	}


	/**
	 * @return the tabViPoDAO
	 */
	public TabViPoDAO getTabViPoDAO() {
		return tabViPoDAO;
	}

	/**
	 * @param tabViPoDAO the tabViPoDAO to set
	 */
	public void setTabViPoDAO(TabViPoDAO tabViPoDAO) {
		this.tabViPoDAO = tabViPoDAO;
	}

	/**
	 * @return the svgFileDAO
	 */
	public SvgFileDAO getSvgFileDAO() {
		return svgFileDAO;
	}

	/**
	 * @param svgFileDAO the svgFileDAO to set
	 */
	public void setSvgFileDAO(SvgFileDAO svgFileDAO) {
		this.svgFileDAO = svgFileDAO;
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

}
