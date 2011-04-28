package com.mkyong.common.action;
 
import java.util.ArrayList;
import java.util.List;
 
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Result;
 
public class OptionTransferSelectAction extends ActionSupport{
 
	private List<String> leftAntivirusList = new ArrayList<String>();
	private List<String> rightAntivirusList = new ArrayList<String>();
 
	private String leftAntivirus;
	private String rightAntivirus;
 
	private String leftNumber;
	private String rightNumber;
 
	public OptionTransferSelectAction(){
 
		leftAntivirusList.add("Norton 360 Version 4.0");
		leftAntivirusList.add("McAfee Total Protection 2010");
		leftAntivirusList.add("Trend Micro IS Pro 2010");
		leftAntivirusList.add("BitDefender Total Security 2010");
 
		rightAntivirusList.add("Norton Internet Security 2010");
		rightAntivirusList.add("Kaspersky Internet Security 2010");
		rightAntivirusList.add("McAfee Internet Security 2010");
		rightAntivirusList.add("AVG Internet Security 2010");
		rightAntivirusList.add("Trend Micro Internet Security 2010");
		rightAntivirusList.add("F-Secure Internet Security 2010");
 
	}
	public String result(){
		System.out.println("left Contains : " + leftAntivirus);
		System.out.println("left list : " + leftAntivirusList);
		return SUCCESS;
	}
 
	public String getLeftNumber() {
		return leftNumber;
	}
 
	public void setLeftNumber(String leftNumber) {
		this.leftNumber = leftNumber;
	}
 
	public String getRightNumber() {
		return rightNumber;
	}
 
	public void setRightNumber(String rightNumber) {
		this.rightNumber = rightNumber;
	}
 
	public List<String> getLeftAntivirusList() {
		return leftAntivirusList;
	}
 
	public void setLeftAntivirusList(List<String> leftAntivirusList) {
		this.leftAntivirusList = leftAntivirusList;
	}
 
	public List<String> getRightAntivirusList() {
		return rightAntivirusList;
	}
 
	public void setRightAntivirusList(List<String> rightAntivirusList) {
		this.rightAntivirusList = rightAntivirusList;
	}
 
	public String getLeftAntivirus() {
		return leftAntivirus;
	}
 
	public void setLeftAntivirus(String leftAntivirus) {
		this.leftAntivirus = leftAntivirus;
	}
 
	public String getRightAntivirus() {
		return rightAntivirus;
	}
 
	public void setRightAntivirus(String rightAntivirus) {
		this.rightAntivirus = rightAntivirus;
	}
 
	public String execute() throws Exception{
 
		return SUCCESS;
	}
 
	public String display() {
		return NONE;
	}
 
}