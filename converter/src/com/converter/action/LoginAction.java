package com.converter.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;

import com.converter.data.PageLabel;
import com.converter.SvgFile;
import com.converter.SvgFileId;
import com.converter.TabLogin;
import com.converter.TabLoginId;
import com.converter.dao.SvgFileDAO;
import com.converter.dao.TabLoginDAO;
import com.opensymphony.xwork2.ActionContext;

public class LoginAction
{
	private String domain;
	private String username;
	private String password;
	
	private SvgFileDAO svgFileDAO;
	private DataSource dataSource;
	
	private TabLoginDAO tabLoginDAO;
	
	private PageLabel pageLabel;
	private String outmessage;
	private String message;
	private List<TabLogin> tabLoginList;
	
	public LoginAction(){
		//this.setDomain("cn001");
	}

	public String preLogin(){
		this.setDomain("cn001");
		this.setTabLoginList(tabLoginDAO.findAll());
		//System.out.println(tabLoginList);
		return "success";
	}
	public String login() throws SQLException
	{		
		//Check if the account exist
		TabLoginId tLoginId=new TabLoginId();
		tLoginId.setAccount(username);
		tLoginId.setDomain(domain);
		
		TabLogin tlLogin=tabLoginDAO.findById(tLoginId);
		
		if(tlLogin!=null)
		{
			if(ldap_creadit(domain,username,password).equalsIgnoreCase("OK"))
			{
				//account exist and password correct
				HttpServletRequest request=ServletActionContext.getRequest();
				HttpSession session=request.getSession();
				session.setAttribute("username",username);
				session.setAttribute("role",tlLogin.getRole());

				return "success";
			}
			else
			{
				//account exist but password not correct
				//this.addFieldError("error","Password is not correct!");
				
				message="Password is not correct!";
				
				return "error";
			}
		}
		else
		{
			//account don't exist
			//this.addFieldError("error","Account don't exist!");
			
			message="Account don't exist!";
			
			return "error";
		}
	}
	
	public String approve()
	{
		try{
			ActionContext cx=ActionContext.getContext();
			this.setUsername(cx.getSession().get("username").toString());
			SvgFileId svgFileId=new SvgFileId(pageLabel.getPartNo(), pageLabel.getPartAs(), "0");
			SvgFile	svgFile=svgFileDAO.findById(svgFileId);
			if (svgFile.getActive().equals("F")){
				svgFile.setApprover(this.getUsername());
				svgFile.setApprove_date(new Timestamp(new Date().getTime()) );
				svgFile.setActive("T");
				this.svgFileDAO.attachDirty(svgFile);
				this.setOutmessage(username+" approve successfully");
			}else {
				this.setOutmessage(pageLabel.getPartNo()+"-"+pageLabel.getPartAs()+" has been approved by "+svgFile.getApprover()+" at "+svgFile.getApprove_date());
			}
		} catch (Exception e) {
			e.printStackTrace();
			setOutmessage("Approve failed for unespected error, "+pageLabel.getPartNo()+"-"+pageLabel.getPartAs());
			return "error";
		} finally {
		}
		
		return "success";
	}
	public String unApprove()
	{
		try{
			ActionContext cx=ActionContext.getContext();
			this.setUsername(cx.getSession().get("username").toString());
			SvgFileId svgFileId=new SvgFileId(pageLabel.getPartNo(), pageLabel.getPartAs(), "0");
			SvgFile	svgFile=svgFileDAO.findById(svgFileId);
			if (svgFile == null){
				this.setOutmessage(pageLabel.getPartNo()+"-"+pageLabel.getPartAs()+" does not exist!");
				return "error";
			}
			if (svgFile.getActive().equals("T")){
				svgFile.setModifier(this.getUsername());
				svgFile.setModify_date(new Timestamp(new Date().getTime()) );
				svgFile.setActive("F");
				this.getSvgFileDAO().attachDirty(svgFile);
				this.setOutmessage(username+" unlock successfully and it is ready for upload");
			}else {
				this.setOutmessage(pageLabel.getPartNo()+"-"+pageLabel.getPartAs()+" is not approved.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			setOutmessage("Operation failed for unespected error, "+pageLabel.getPartNo()+"-"+pageLabel.getPartAs());
			return "error";
		} finally {
		}
		
		return "success";
	}
	//ldap authentication function
	public String ldap_creadit(String v_domain, String v_account_name, String v_password) throws SQLException
	{
		String result=null;
		Connection conn=null;
		CallableStatement coll=null;
		try
		{
			conn=dataSource.getConnection();
			coll = conn.prepareCall("{?=call PCBVI.ldap_creadit(?,?,?)}");
			coll.setString(2, v_domain);
			coll.setString(3, v_account_name);
			coll.setString(4, v_password);
		
			coll.registerOutParameter(1,Types.VARCHAR);
			
			coll.execute();
			
			result=coll.getString(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(conn!=null)
			{
				conn.close();
			}
			
			if(coll!=null)
			{
				coll.close();
			}
		}		
		return result;
		
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dataSource
	 */
	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * @param dataSource the dataSource to set
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * @return the tabLoginDAO
	 */
	public TabLoginDAO getTabLoginDAO() {
		return tabLoginDAO;
	}

	/**
	 * @param tabLoginDAO the tabLoginDAO to set
	 */
	public void setTabLoginDAO(TabLoginDAO tabLoginDAO) {
		this.tabLoginDAO = tabLoginDAO;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public void setOutmessage(String outmessage) {
		this.outmessage = outmessage;
	}

	public String getOutmessage() {
		return outmessage;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}
	public void setSvgFileDAO(SvgFileDAO svgFileDAO) {
		this.svgFileDAO = svgFileDAO;
	}
	public SvgFileDAO getSvgFileDAO() {
		return svgFileDAO;
	}

	/**
	 * @return the pageLabel
	 */
	public PageLabel getPageLabel() {
		return pageLabel;
	}

	/**
	 * @param pageLabel the pageLabel to set
	 */
	public void setPageLabel(PageLabel pageLabel) {
		this.pageLabel = pageLabel;
	}

	/**
	 * @param tabLoginList the tabLoginList to set
	 */
	public void setTabLoginList(List tabLoginList) {
		this.tabLoginList = tabLoginList;
	}

	/**
	 * @return the tabLoginList
	 */
	public List getTabLoginList() {
		return tabLoginList;
	}

	

}
