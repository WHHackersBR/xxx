package com.converter.action;

import java.util.ArrayList;
import java.util.List;

import com.converter.data.PageLabel;

public class sideMainenanceAction {
	private PageLabel pageLabel;
	//collection/array/map/enumeration/iterator type
	public static List<String> sideList = new ArrayList<String>();
	/**
	 * 
	 */
	private List<String> leftCsList;
	private List<String> rightSsList;
	private String outmessage;

	/**
	 * @return the outmessage
	 */
	public String getOutmessage() {
		return outmessage;
	}
	/**
	 * @param outmessage the outmessage to set
	 */
	public void setOutmessage(String outmessage) {
		this.outmessage = outmessage;
	}
	public sideMainenanceAction(){
		leftCsList = new ArrayList<String>();
		rightSsList = new ArrayList<String>();
	}
	public String sideMaintenance(){
		//System.out.println(pageLabel.getPartNo());
		//System.out.println(pageLabel.getPartAs());
		//leftCsList = Arrays.asList(new String[uploadAction.sideList.size()]);  
		//Collections.copy(leftCsList, uploadAction.sideList);  
		//leftCsList =  uploadAction.sideList;
		
		return "success";
	}
	/**
	 * @return the pageLabel
	 */
	public PageLabel getPageLabel() {
		/*if(pageLabel!=null){
			System.out.println("getpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
		}else{
			System.out.println("setpagelabel:"+pageLabel);
		}*/
		return pageLabel;
	}
	/**
	 * @param pageLabel the pageLabel to set
	 */
	public void setPageLabel(PageLabel pageLabel) {
		/*if(pageLabel!=null){

			System.out.println("setpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
			}else{
				System.out.println("setpagelabel:"+pageLabel);
			}*/
		this.pageLabel = pageLabel;
	}
	/**
	 * @return the leftCsList
	 */
	public List<String> getLeftCsList() {
		return leftCsList;
	}

	/**
	 * @param leftCsList the leftCsList to set
	 */
	public void setLeftCsList(List<String> leftCsList) {
		this.leftCsList = leftCsList;
	}

	/**
	 * @return the rightSsList
	 */
	public List<String> getRightSsList() {
		return rightSsList;
	}

	/**
	 * @param rightSsList the rightSsList to set
	 */
	public void setRightSsList(List<String> rightSsList) {
		this.rightSsList = rightSsList;
	}
	/**
	 * @return the savesidedao
	 */

}
