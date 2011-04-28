/**
 * 
 */
package com.converter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.sun.org.apache.bcel.internal.generic.NEW;
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
	private String filename="A5E00994011-08.svg";
	private File oriFile;

	private String contentType;
	private String fileName;
	
	private String outmessage;
	private SvgFileDAO svgFileDAO;

	private final int maxFileSize=2048;
	private final String userManualDir="/webserver/ViUserManual/";

	//collection/array/map/enumeration/iterator type
	public static List<String> sideList = new ArrayList<String>();

	private List<String> leftCsList;
	private List<String> rightSsList;
	private List<String> colorList;
 
	//@SuppressWarnings("deprecation")

	public uploadAction() {
		// TODO Auto-generated constructor stub
		leftCsList = new ArrayList<String>();
		rightSsList = new ArrayList<String>();
		colorList = new ArrayList<String>();
		colorList.add("blueviolet");
		colorList.add("brown");
		colorList.add("chartreuse");
		colorList.add("darkorange");
		colorList.add("magenta");
		colorList.add("olivedrab");
		colorList.add("orangered");
		colorList.add("royalblue");
		colorList.add("tan");
		colorList.add("yellowgreen");
	}

	public String upload(){
		Blob photo = null;
	
	if( this.oriFile == null){
		return "failed";
	}
	try{
		//filename = this.getOriFileFileName().substring(this.getOriFileFileName().lastIndexOf("\\"));
		filename = this.getOriFileFileName();
		filename = filename.substring(0, filename.lastIndexOf("."));
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
		//main.setSourceFile(pageLabel.getOriFilefileName());
		main.setOriFile(this.getOriFile());
		//set the file format for converting
		main.setPipeline("svg");
		main.omitUI(true);
		filename = filename + "." + main.getPipeline();
		String upfilename=ServletActionContext.getServletContext().getRealPath("/SVG-FILE")+"/"+ filename;
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
            if(!layer.getName().equals("0") ){ //&& !layer.getName().equals("BOARDFIGURE")
        		uploadAction.sideList.add(layer.getName().trim());
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
			this.setOriFile(null);
		}

		SvgFileId svgfileid=new SvgFileId(pageLabel.getPartNo(),pageLabel.getPartAs(),pageLabel.getSide());
		SvgFile svgfile= this.getUploadfiledao().findById(svgfileid);
		if (svgfile == null){
			svgfile= new SvgFile(svgfileid);
		}
		svgfile.setSourcefile(photo);
		//System.out.println(this.getUploadfiledao().findById(svgfileid).getSourcefile().toString());
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
	public String originalSvg(){
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
		//System.out.println(pageLabel.getPartNo());
		//System.out.println(pageLabel.getPartAs());
		//Collections.copy(leftCsList, uploadAction.sideList);  
		leftCsList.clear();
		rightSsList.clear();
		
		try{
			List LayerList=this.getSavesidedao().findByPropertys(new String[]{"id.articleNo","id.as"}, new String[]{pageLabel.getPartNo(),pageLabel.getPartAs()});
			//System.out.println(LayerList.size());
			for(int i=0; i<uploadAction.sideList.size(); i++){
				Iterator<SvgSide> j = LayerList.iterator();
				//for new partnumber
				if(!j.hasNext()){
					leftCsList.add(uploadAction.sideList.get(i));
				}
				while (j.hasNext()) {
					SvgSide svgSide=j.next();
					//System.out.println("side1: "+svgSide.getId().getLayerName().toString()+":"+uploadAction.sideList.get(i));
		        	if(svgSide.getId().getLayerName().toString().equals(uploadAction.sideList.get(i))){
						//System.out.println("side2: "+svgSide.getId().getLayerName().toString()+":"+uploadAction.sideList.get(i));
						if(svgSide.getSide().toString().equals("CS")){
							leftCsList.add(uploadAction.sideList.get(i));
							//System.out.println("side3: "+svgSide.getId().getLayerName().toString()+":"+uploadAction.sideList.get(i));
						}else{
							rightSsList.add(uploadAction.sideList.get(i));
							//System.out.println("side4: "+svgSide.getId().getLayerName().toString()+":"+uploadAction.sideList.get(i));
						}
						break;
		        	}else if(!j.hasNext()){
						leftCsList.add(uploadAction.sideList.get(i));
		        	}
		        }
			}
			Iterator<SvgSide> j = LayerList.iterator();
	        while (j.hasNext()) {
	        	SvgSide svgSide=j.next();
				//System.out.println("delete: "+svgSide.getId().getLayerName().toString());
	        	int i=0;
				for(i=0; i<uploadAction.sideList.size() && 
							!svgSide.getId().getLayerName().toString().equals(uploadAction.sideList.get(i)); i++);
					if(i==uploadAction.sideList.size()){
						this.getSavesidedao().delete(svgSide);
					
				}
	        }
		
		} catch (Exception e) {
			e.printStackTrace();
			setOutmessage("Cannot set side!!");
			return "error";
		} finally {
		}
		setOutmessage("Successful");
		return "success";	
	}

	public String sideContentaction(){
		setOutmessage("");
		//System.out.println("left Contains : " + pageLabel.getCsSide());
		//System.out.println("right Contains : " + pageLabel.getSsSide());
		//System.out.println("leftCsList Contains : " + leftCsList);
		//System.out.println("rightSsList Contains : " + rightSsList);
	try{
		//SvgSide   svgSide1 = new SvgSide();
		//svgSide1.setSide("SS");
		//List svgLayerList=this.getSavesidedao().findByExample(svgSide1);
		//System.out.println(svgLayerList.size());
		Iterator i = pageLabel.getCsSide().iterator();
		int j=1,colorIndex=0;
        while (i.hasNext()) {
			SvgSideId svgSideId=new SvgSideId(pageLabel.getPartNo(),pageLabel.getPartAs(),i.next().toString());
			SvgSide   svgSide = new SvgSide(svgSideId);
			svgSide.setSide("CS");
			svgSide.setLayerSn(new BigDecimal(j));
			svgSide.setColor(colorList.get(colorIndex));
			this.getSavesidedao().attachDirty(svgSide);
			j++;
			colorIndex++;
        }
        i = pageLabel.getSsSide().iterator();
        j=1;
        while (i.hasNext()) {
			SvgSideId svgSideId=new SvgSideId(pageLabel.getPartNo(),pageLabel.getPartAs(),i.next().toString());
			SvgSide   svgSide = new SvgSide(svgSideId);
			svgSide.setSide("SS");
			svgSide.setLayerSn(new BigDecimal(j));
			svgSide.setColor(colorList.get(colorIndex));
			this.getSavesidedao().attachDirty(svgSide);
			j++;
			colorIndex++;
        }
        
	} catch (Exception e) {
		e.printStackTrace();
		setOutmessage("Side set failed!!");
		return ERROR;
	} finally {
	}
	setOutmessage("Successful");
	return SUCCESS;	
	}
	
	public String userManual(){
		
		SvgFileId svgFileId = new SvgFileId(pageLabel.getPartNo(), pageLabel.getPartAs(), "0");
		
		SvgFile svgFile= svgFileDAO.findById(svgFileId);
		//List<String> viList = new ArrayList<String>();
		//viList.add("abc");
		
		if (svgFile != null && svgFile.getUserManual() != null && !svgFile.getUserManual().isEmpty()){
			//viList.addAll(Arrays.asList(svgFile.getUserManual().split(":")));
			pageLabel.setViDocReview(svgFile.getUserManual().replaceFirst(pageLabel.getPartNo()+"-"+pageLabel.getPartAs()+"-", ""));
			pageLabel.setViDocReal("userManual/"+svgFile.getUserManual());
		}
			//pageLabel.setViDocList(viList);
		
		return SUCCESS;
		
	}

	public String uploadManual(){
		
		setOutmessage("");
		try {
			InputStream stream = new FileInputStream(pageLabel.getViDocument());
			String realName=pageLabel.getPartNo() + "-" + pageLabel.getPartAs() + "-" + pageLabel.getViDocumentFileName();
			String targetPath=ServletActionContext.getServletContext().getRealPath("/userManual")+"/" + realName;
			// System.out.println(stream.available());
			if (stream.available() < maxFileSize * 1024) {
				OutputStream bos = new FileOutputStream(targetPath);
				byte[] buffer = new byte[8192];
				int bytesRead = 0;
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead);
				}
				bos.close();
				stream.close();
				
			} else {
				setOutmessage("Cannot upload file, because file size is largger than "+ maxFileSize + "K bytes");
				pageLabel.setViDocReview(this.getOutmessage());
				return ERROR;
			}
			SvgFileId svgFileId = new SvgFileId(pageLabel.getPartNo(), pageLabel.getPartAs(), "0");
			
			SvgFile svgFile= svgFileDAO.findById(svgFileId);
			
			if (svgFile == null){
				svgFile = new SvgFile(svgFileId);
			}else {
				File oldFile=new File(ServletActionContext.getServletContext().getRealPath("/userManual")+"/"+svgFile.getUserManual());
				if (oldFile.exists()&& svgFile.getUserManual().equals(realName)){
					oldFile.delete();
				}
			}
			svgFile.setUserManual(realName);
			this.svgFileDAO.attachDirty(svgFile);
			//pageLabel.setViDocReview(pageLabel.getViDocumentFileName());
			pageLabel.setViDocReview(pageLabel.getViDocumentFileName());
			pageLabel.setViDocReal("userManual/"+ svgFile.getUserManual());
			

		} catch (Exception e) {
			e.printStackTrace();
			this.setOutmessage("Upload file failed");
			pageLabel.setViDocReview(this.getOutmessage());
			return ERROR;

		}

		setOutmessage("Upload file successfully");
		return SUCCESS;
		
	}
	public String showSvg(){
		Blob photo = null;
    
	try{
		
		
		return SUCCESS;
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
		/*if(pageLabel!=null){
			System.out.println("getpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
		}else{
			System.out.println("setpagelabel:"+pageLabel);
		}*/
		return pageLabel;
	}

	public void setPageLabel(PageLabel pageLabel) {
		/*if(pageLabel!=null){

		System.out.println("setpagelabel:"+pageLabel.getPartNo()+":"+pageLabel.getClass());
		}else{
			System.out.println("setpagelabel:"+pageLabel);
		}*/
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
	/**
	 * @return the colorList
	 */
	public List<String> getColorList() {
		return colorList;
	}

	/**
	 * @param colorList the colorList to set
	 */
	public void setColorList(List<String> colorList) {
		this.colorList = colorList;
	}
	/**
	 * @return the oriFile
	 */
	public File getOriFile() {
		return oriFile;
	}

	/**
	 * @param oriFile the oriFile to set
	 */
	public void setOriFile(File oriFile) {
		this.oriFile = oriFile;
	}
	/**
	 * @return the contentType
	 */
	public String getOriFileContentType() {
		return contentType;
	}

	/**
	 * @param contentType the contentType to set
	 */
	public void setOriFileContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * @return the fileName
	 */
	public String getOriFileFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setOriFileFileName(String fileName) {
		this.fileName = fileName;
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