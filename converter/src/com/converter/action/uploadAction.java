/**
 * 
 */
package com.converter.action;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.sql.Blob;
import java.sql.ResultSet;

import org.kabeja.Main;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFLayer;
//import oracle.sql.BLOB;


import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;

import com.converter.dao.SvgFileDAO;
import com.converter.dao.SvgSideDAO;
import com.converter.data.PageLabel;
import com.converter.*;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.bcel.internal.generic.Select;
/**
 * @author zhou
 *
 */

//private SvgFileId svgfileid=new SvgFileId();

public class uploadAction extends ActionSupport{
	private SvgFileDAO uploadfiledao;
	private SvgSideDAO savesidedao;

	private PageLabel pageLabel;
	private String filename="100_6162.JPG";
	private String outmessage;

	//collection/array/map/enumeration/iterator type
	public static List<String> sideList = new ArrayList<String>();
	/**
	 * 
	 */
	private List<String> leftCsList;
	private List<String> rightSsList;
 
	//@SuppressWarnings("deprecation")
	public uploadAction() {
		// TODO Auto-generated constructor stub
		leftCsList = new ArrayList<String>();
		rightSsList = new ArrayList<String>();
	}

	public String upload(){
		Blob photo = null;
	
	if( pageLabel.getFilePath()==null || pageLabel.getFilePath().isEmpty()){
		return "success";
	}
	try{
		filename = pageLabel.getFilePath().substring(pageLabel.getFilePath().lastIndexOf("\\"));
		filename = filename.substring(1, filename.lastIndexOf("."));
		if (filename.isEmpty()){
			setOutmessage("Part number is empty\\n");
		}
		int ASIndex = filename.lastIndexOf("-")!=-1 ? filename.lastIndexOf("-"):
			(filename.lastIndexOf("_") !=-1 ? filename.lastIndexOf("_"):
				filename.lastIndexOf(" ") );
		if ( ASIndex ==-1){
			setOutmessage("Cannot locate the Version,without seperator/n");
		}
		pageLabel.setPartAs(filename.substring(ASIndex+1));
		if (pageLabel.getPartAs().isEmpty()){
			setOutmessage("Version of Partnumber is empty\\n");
		}
		pageLabel.setSide("0");
		pageLabel.setPartNo(filename.substring(0, ASIndex));
		//start to parse the dxf file  
		Main main = new Main();
		main.setSourceFile(pageLabel.getFilePath());
		//set the file format for converting
		main.setPipeline("svg");
		main.omitUI(true);
		filename = filename + "." + main.getPipeline();
		String upfilename=ServletActionContext.getServletContext().getRealPath("/SVG-FILE")+"\\"+ filename;
		main.setDestinationFile(upfilename);
		main.initialize();
		main.process();
		
		//start to get layer information for side maintenance
		DXFDocument doc = main.getParser().getDocument();
		// the layers as container g-elements
		Iterator i = doc.getDXFLayerIterator();
		uploadAction.sideList.clear();
        while (i.hasNext()) {
            DXFLayer layer = (DXFLayer) i.next();
            if(!layer.getName().equals("0")){
        		uploadAction.sideList.add(layer.getName());
            }
        }

        //start to save SVG file into database
		FileInputStream fis = new FileInputStream(upfilename);
	    filename="SVG-FILE\\" + filename;
	    
	    try{
			byte[] data = new byte[(int)fis.available()];
			fis.read(data);
			photo = Hibernate.createBlob(data);
	    }catch (Exception e) {
			e.printStackTrace();
			fis.close();
			return "failed";
		} finally {
			fis.close();
			pageLabel.setFilePath("");
		}

		SvgFileId svgfileid=new SvgFileId(pageLabel.getPartNo(),pageLabel.getPartAs(),pageLabel.getSide());
		SvgFile svgfile= new SvgFile(svgfileid);
		svgfile.setSourcefile(photo);
		
		this.getUploadfiledao().attachDirty(svgfile);
		//this.getUploadfiledao().merge(svgfile);//it cannot update the a exist SVG file and always insert a empty SVG file	
		
	} catch (Exception e) {
		e.printStackTrace();
		return "failed";
	} finally {
	}
	setOutmessage("Successful");
	return "success";	
	}


	public String loadSvg(){
		setOutmessage("");
		//System.out.println(pageLabel.getPartNo());
		if (pageLabel.getPartNo()==null || pageLabel.getPartNo().equals("")){
			setOutmessage("Part number is empty\\n" + "<br>");
		}
		if (pageLabel.getPartAs()==null || pageLabel.getPartAs().equals("")){
			setOutmessage("Version is empty\\n" + "<br>");
		}
		if (getOutmessage().equals("")){
			return "loadsvg";
		}else{
			return "error";
		}
	}

	public String sideMaintenance(){
		setOutmessage("");
		System.out.println(pageLabel.getPartNo());
		System.out.println(pageLabel.getPartAs());
		//leftCsList = Arrays.asList(new String[uploadAction.sideList.size()]);  
		//Collections.copy(leftCsList, uploadAction.sideList);  
		//leftCsList =  uploadAction.sideList;
		for(int i=0; i<uploadAction.sideList.size(); i++){
			leftCsList.add(uploadAction.sideList.get(i));
		}
		
		return "success";
	}

	public String sideContentaction(){
		setOutmessage("");
		//System.out.println("left Contains : " + pageLabel.getCsSide());
		//System.out.println("right Contains : " + pageLabel.getSsSide());
		//System.out.println("leftCsList Contains : " + leftCsList);
		//System.out.println("rightSsList Contains : " + rightSsList);
	try{
		Iterator i = pageLabel.getCsSide().iterator();
		int j=1;
        while (i.hasNext()) {
			SvgSideId svgSideId=new SvgSideId(pageLabel.getPartNo(),pageLabel.getPartAs(),"CS",i.next().toString());
			SvgSide   svgSide = new SvgSide(svgSideId);
			svgSide.setLayerSn(new BigDecimal(j));
			svgSide.setColor(Integer.toString(j));
			this.getSavesidedao().attachDirty(svgSide);
        }
        i = pageLabel.getSsSide().iterator();
        j=1;
        while (i.hasNext()) {
			SvgSideId svgSideId=new SvgSideId(pageLabel.getPartNo(),pageLabel.getPartAs(),"SS",i.next().toString());
			SvgSide   svgSide = new SvgSide(svgSideId);
			svgSide.setLayerSn(new BigDecimal(j));
			svgSide.setColor(Integer.toString(j));
			this.getSavesidedao().attachDirty(svgSide);
        }
        
	} catch (Exception e) {
		e.printStackTrace();
		return "failed";
	} finally {
	}
	setOutmessage("Successful");
	return "success";	
	}
	
	public String showSvg(){
		Blob photo = null;
    
	try{
		
		
		return "success";
	} catch (Exception e) {
		e.printStackTrace();
		return "failed";
	} finally {
	}
	
	}
	
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public SvgFileDAO getUploadfiledao() {
		return uploadfiledao;
	}
	public void setUploadfiledao(SvgFileDAO uploadfiledao) {
		this.uploadfiledao = uploadfiledao;
	}

	public PageLabel getPageLabel() {
		if(pageLabel!=null){
			System.out.println("getpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
		}else{
			System.out.println("setpagelabel:"+pageLabel);
		}
		return pageLabel;
	}

	public void setPageLabel(PageLabel pageLabel) {
		if(pageLabel!=null){

		System.out.println("setpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
		}else{
			System.out.println("setpagelabel:"+pageLabel);
		}
		this.pageLabel = pageLabel;
	}

	public void setOutmessage(String outmessage) {
		this.outmessage = outmessage;
	}

	public String getOutmessage() {
		return outmessage;
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
	public SvgSideDAO getSavesidedao() {
		return savesidedao;
	}

	/**
	 * @param savesidedao the savesidedao to set
	 */
	public void setSavesidedao(SvgSideDAO savesidedao) {
		this.savesidedao = savesidedao;
	}

}
