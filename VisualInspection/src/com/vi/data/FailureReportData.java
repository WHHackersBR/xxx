package com.vi.data;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbcp.BasicDataSource;

import oracle.jdbc.driver.OracleTypes;

/**
 * Made a Spring bean with this class, so that failureReportData can be shared and accessed easily 
 * on several pages
 * 
 * @author Johannes Sauer
 *
 */
public class FailureReportData {

	private String partname;
	private String positionNr;
	private String failureCode;
	private String failureDescription;
	
	private ArrayList<FailureCode> failureCodes;
	private ArrayList<PositionNr> positionNrs;
	private boolean initialized=false;
	 
	public FailureReportData(){
		//initialize list
		failureCodes=new ArrayList<FailureCode>();
		positionNrs=new ArrayList<PositionNr>();
	
	}

	public void init(BasicDataSource dataSource){
		if(!initialized){
				
			Connection conn = null;
			CallableStatement coll = null;
			try {

				for (int quantity = 1; quantity <= 16; quantity++) {
					int positionAbbr=quantity;
					int positionDefinition=quantity;
					
					// add the values
					PositionNr positionNr=new PositionNr();
					positionNr.setPositionNrAbbr(String.valueOf(positionAbbr));
					positionNr.setPositionNrName(String.valueOf(positionDefinition));
					getPositionNrs().add(positionNr);
				}

				conn = dataSource.getConnection();
				//calling the stored procedure which gets the failure codes
				//PROCEDURE GET_FAILURECODES(v_cursor_failurecodes OUT CURSOR_PO);
				coll = conn.prepareCall("{call PCBVI.PKG_GET_FAILURECODES.GET_FAILURECODES(?)}");			
				//prepare input and output arguments 				
				coll.registerOutParameter(1, OracleTypes.CURSOR);
				
				coll.execute();

				//get data
				ResultSet rs = (ResultSet) coll.getObject(1);
				//and process it
				while (rs.next()) {
					String codeAbbr=rs.getString(1);
					String codeDefinition=rs.getString(2);
					
					//System.out.println(codeAbbr + " - " + codeDefinition); // code - description matching code	
					
					// add the values
					FailureCode failure=new FailureCode();
					failure.setFailureCodeAbbr(codeAbbr);
					failure.setFailurCodeName(codeAbbr + " - " + codeDefinition);
					failureCodes.add(failure);
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
			initialized=true;
		}
		
	}
	
	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getPositionNr() {
		return positionNr;
	}

	public void setPositionNr(String positionNr) {
		this.positionNr = positionNr;
	}

	
	public String getFailureDescription() {
		return failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public ArrayList<FailureCode> getFailureCodes() {
		return failureCodes;
	}

	public void setFailureCodes(ArrayList<FailureCode> failureCodes) {
		this.failureCodes = failureCodes;
	}

	/**
	 * @return the positionNrs
	 */
	public ArrayList<PositionNr> getPositionNrs() {
		return positionNrs;
	}

	/**
	 * @param positionNrs the positionNrs to set
	 */
	public void setPositionNrs(ArrayList<PositionNr> positionNrs) {
		this.positionNrs = positionNrs;
	}
	
}
