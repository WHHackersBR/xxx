package com.vi.action;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.vi.data.FailureReportData;
import com.vi.data.FormData;

public class FailureReportAction extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BasicDataSource dataSource;
	private FailureReportData failureReportData;
	//need it to pass values to UI
	private FormData formData;
		
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
		
		return SUCCESS;
	}
	
	public String saveFailureReportData(){
		return SUCCESS;
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

	public FormData getFormData() {
		return formData;
	}

	public void setFormData(FormData formData) {
		this.formData = formData;
	}

}
