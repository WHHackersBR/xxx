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

import javax.servlet.http.HttpServletRequest;

import oracle.jdbc.driver.OracleTypes;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.dao.SvgFileDAO;
import com.vi.dao.TabViPoDAO;
import com.vi.dao.TabWorkstationDAO;
import com.vi.dao.VFailureDAO;
import com.vi.dao.VFidHistDAO;
import com.vi.dao.VPoDAO;
import com.vi.dao.VtestedDAO;
import com.vi.data.FailureReportData;
import com.vi.data.FormData;
import com.vi.data.ErrMessage;
import com.vi.pojo.SvgFile;
import com.vi.pojo.SvgFileId;
import com.vi.pojo.TabTestedId;
import com.vi.pojo.TabViPo;
import com.vi.pojo.TabViPoId;
import com.vi.pojo.TabWorkstation;
import com.vi.pojo.VFailure;
import com.vi.pojo.VFailureId;
import com.vi.pojo.VFidHist;
import com.vi.pojo.VFidHistId;
import com.vi.pojo.VPo;
import com.vi.pojo.VPoId;
import com.vi.pojo.Vtested;
import com.vi.pojo.VtestedId;

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
	private VtestedDAO	vtestedDAO;
	private VPoDAO	vPoDAO;
	private VFailureDAO vFailureDAO;
	private VFidHistDAO vFidHistDAO;
	private TabViPoDAO	tabViPoDAO;
	private SvgFileDAO	svgFileDAO;
	//private 
	

	private FormData formData;
	private FailureReportData failureReportData;
	private TabTestedId tabTestedId=new TabTestedId();
	private String v_machType = "PVI******";
    private String v_side;
    private String v_mach_id;
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
		if(formData.getPoNo().equals("") ){
			errorOutput=ErrMessage.NullPO;
			return ERROR;
		}
		
		Connection conn = null;
		CallableStatement coll = null;
		try {
			formData.setItemNr("");
			formData.setVersionAS("");
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
				v_machType = tabWorkstation.getMachType();
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
	 * Action for get the quantity of PO finished/planed
	 * according to PO number
	 * 
	 * @return Result which will be processed by Struts2
	 */
	public String poCompleted() {
		//use Hibernate to get data
		//List vPo<VPo>;
		try {
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
		if (formData.getPoNo() != null && !formData.getPoNo().isEmpty()){
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
		}
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
				formData.setFailed(false);
				return ERROR;
			}
			formData.setCurrentFid(formData.getFid());
			formData.setFid("");
			Vtested testedFID = new Vtested();
			VFidHist vFidHist = new VFidHist();
			//System.out.println();
			try {
	    		if (!formData.getWorkstationNr().equals("")) {
	    			TabWorkstation tabWorkstation = tabWorkstationDAO
	    					.findById(formData.getWorkstationNr());
	    			//System.out.println(tabWorkstation.getMachId()+":"+tabWorkstation);
	    			if (tabWorkstation != null && tabWorkstation.getMachId()!=null) {
	    				v_mach_id = tabWorkstation.getMachId();
	    				v_side = tabWorkstation.getSide();
	    			} else {
		    			v_mach_id = formData.getWorkstationNr();
		    			v_side = formData.getSide();
	    			}
	    		}else {
	    			v_mach_id = formData.getWorkstationNr();
	    			v_side = formData.getSide();
	    		}
				VtestedId vtestedId=new VtestedId(formData.getCurrentFid(), v_machType,v_side);
				testedFID = vtestedDAO.findById(vtestedId);

				// try to compare PO number with the one in the jerry.fid_hist
				VFidHistId vFidHistId = new VFidHistId(formData.getCurrentFid());
				vFidHist = vFidHistDAO.findById(vFidHistId);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				//e.getCause();
			}
			if ( testedFID == null ){
				Connection conn = null;
				CallableStatement coll = null;
				try {
		            Date   now   =   new   Date(); 
		            SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
					VtestedId vtestedId=new VtestedId(formData.getCurrentFid(), v_machType,v_side);
					testedFID = new Vtested(vtestedId);
					testedFID.setArticleNo(formData.getItemNr());
					testedFID.setRevision(formData.getVersionAS());
					testedFID.setTestResult("P");
					testedFID.setEventTime( new Timestamp(new Date().getTime()));
					testedFID.setMsg1("0");
					testedFID.setTransferBit("0");
					testedFID.setConfirm("N");
					//testedFID.setEventTime(dateFormat2.format(now));
					testedFID.setMachId(v_mach_id);
					testedFID.setMachTime(dateFormat.format(now));
					formData.setFailed(true);
					if ( vFidHist == null ){
						errorOutput= formData.getCurrentFid() + ":" + ErrMessage.FidNotExist;
					}else if ( !vFidHist.getPoNo().equals(formData.getPoNo()) ){
						errorOutput=vFidHist.getPoNo() + ":" + ErrMessage.PoNotMatch;
					}else {
						testedFID.setEstand(vFidHist.getEstand());
						testedFID.setPsw(vFidHist.getPsw());
						errorOutput= formData.getCurrentFid() + ":" + ErrMessage.passFID;
					}
					
					vtestedDAO.attachDirty(testedFID);

					VPoId vPoId = new VPoId(vFidHist.getPoNo(), vFidHist.getArticleNo(), vtestedId.getMachType());
					VPo	  vPo = vPoDAO.findById(vPoId);
					if (vPo != null) {
						vPo.setQtyProduce(vPo.getQtyProduce());
						vPo.setEventTime(new Timestamp(new Date().getTime()));
						vPo.setTransferBit("0");
					}else {
						vPo = new VPo(vPoId);
						vPo.setQtyProduce(BigDecimal.valueOf(1));
						vPo.setQtyPlan(BigDecimal.valueOf(Integer.valueOf(vFidHist.getQuantity())));
						vPo.setEstand(vFidHist.getEstand());
						vPo.setFirstTime(new Timestamp(new Date().getTime()));
						vPo.setEventTime(new Timestamp(new Date().getTime()));
						vPo.setRevision(vFidHist.getRevision());
						vPo.setTransferBit("0");
					}
					vPoDAO.attachDirty(vPo);
					
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
			}else{ 
				try {
					if(testedFID.getTestResult().equals("P")){
						formData.setFailed(true);
						if (testedFID.getConfirm().equals("Y")){
							errorOutput = formData.getCurrentFid() + ErrMessage.pastFID +"," + ErrMessage.Confirmed;
						}else {
							errorOutput=formData.getCurrentFid() + ErrMessage.pastFID + "," + ErrMessage.NotConfirmed;
						}
					}else{
						formData.setFailed(false);
						if (testedFID.getConfirm().equals("Y")){
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
		if( formData.getWorkstationNr()==null || formData.getWorkstationNr().equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		if (formData.getSide()==null || formData.getSide().equals("")){
			errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
		}
		if (formData.getOperatorID()==null || formData.getOperatorID().equals("")){
			errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
		}
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
		failureReportData.setPartname(partname);
		failureReportData.setFailureDescription(null);
		failureReportData.setPositionNr(null);
		if((formData.getCurrentFid()==null || formData.getCurrentFid().isEmpty()) ){
			errorOutput=ErrMessage.NullFID+ "<br>";
		}
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

		try {
			VtestedId vtestedId=new VtestedId(formData.getCurrentFid(), v_machType,v_side);
			Vtested vtested = vtestedDAO.findById(vtestedId);
			if (vtested != null && vtested.getConfirm().equals("N") ){
				if( !vtested.getTestResult().equals("F") ){
					formData.setFailed(true);		
					errorOutput = ErrMessage.setFIDfail;
				}
			}else{
				errorOutput = ErrMessage.NUllorConfirmFID;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
		
		//Connection conn = null;
		//CallableStatement coll = null;
		try {
					VFailureId vFailureId = new VFailureId(formData.getCurrentFid(), v_machType, v_side
							,failureReportData.getPartname(),failureReportData.getPositionNr(),failureReportData.getFailureCode());
					VFailure vFailure = new VFailure(vFailureId);
					vFailure.setMachId(v_mach_id);
		            Date   now   =   new   Date(); 
		            SimpleDateFormat   dateFormat   =   new   SimpleDateFormat("yyyyMMddHHmmss");
					vFailure.setMachTime(dateFormat.format(now));
					vFailure.setTolerance(formData.getWorkstationNr());
					vFailure.setDescription(failureReportData.getFailureDescription());
					vFailure.setConfirm("N");
					vFailureDAO.attachDirty(vFailure);
					formData.setFailed(false);		
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
			VtestedId vtestedId=new VtestedId(formData.getCurrentFid(), v_machType,v_side);
			Vtested vtested = vtestedDAO.findById(vtestedId);
			if (vtested != null && vtested.getConfirm().equals("N") ){
				vtested.setTestResult("F");
				vtested = vtestedDAO.merge(vtested);
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
	 * @return the vtestedDAO
	 */
	public VtestedDAO getVtestedDAO() {
		return vtestedDAO;
	}

	/**
	 * @param vtestedDAO the vtestedDAO to set
	 */
	public void setVtestedDAO(VtestedDAO vtestedDAO) {
		this.vtestedDAO = vtestedDAO;
	}

	/**
	 * @return the vFidHistDAO
	 */
	public VFidHistDAO getvFidHistDAO() {
		return vFidHistDAO;
	}

	/**
	 * @param vFidHistDAO the vFidHistDAO to set
	 */
	public void setvFidHistDAO(VFidHistDAO vFidHistDAO) {
		this.vFidHistDAO = vFidHistDAO;
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
	 * @return the vFailureDAO
	 */
	public VFailureDAO getvFailureDAO() {
		return vFailureDAO;
	}

	/**
	 * @param vFailureDAO the vFailureDAO to set
	 */
	public void setvFailureDAO(VFailureDAO vFailureDAO) {
		this.vFailureDAO = vFailureDAO;
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

}
