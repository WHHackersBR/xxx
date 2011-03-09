/**
 * 
 */
package com.converter.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.sql.Blob;
import org.kabeja.Main;
//import oracle.sql.BLOB;


import org.apache.struts2.ServletActionContext;
import org.hibernate.Hibernate;

import com.converter.dao.SvgFileDAO;
import com.converter.*;
/**
 * @author zhou
 *
 */

//private SvgFileId svgfileid=new SvgFileId();

public class uploadAction {
	private SvgFileDAO uploadfiledao;
	private String filepath;
	private String articleNo;
	private String filename;
	private String estand;
	private String side;
	private String outmessage;

	//@SuppressWarnings("deprecation")
	public String upload(){
		Blob photo = null;
	
    
	try{
		filename = filepath.substring(filepath.lastIndexOf("\\"));
		filename = filename.substring(1, filename.lastIndexOf("."));
		if (filename.isEmpty()){
			outmessage = "Part number is empty/n";
		}
		int estandIndex = filename.lastIndexOf("-")!=-1 ? filename.lastIndexOf("-"):
			(filename.lastIndexOf("_") !=-1 ? filename.lastIndexOf("_"):
				filename.lastIndexOf(" ") );
		if ( estandIndex ==-1){
			outmessage = "Cannot locate the Version,without seperator/n";
		}
		estand = filename.substring(estandIndex+1);
		if (estand.isEmpty()){
			outmessage = "Version of Partnumber is empty/n";
		}
		side = filename.substring(estandIndex-1,estandIndex).toUpperCase();
		if (side.isEmpty() || !(side.contains("A") || side.contains("B") ) ){
			outmessage = "Pannel side is empty or not correct/n";
		}
		articleNo = filename.substring(0, estandIndex-1);
		//��ͼƬ����������  
		Main main = new Main();
		main.setSourceFile(filepath);
		//����ת���ļ���ʽΪsvg
		main.setPipeline("svg");
		main.omitUI(true);
		filename = filename + "." + main.getPipeline();
		String upfilename=ServletActionContext.getServletContext().getRealPath("/SVG-FILE")+"\\"+ filename;
		main.setDestinationFile(upfilename);
		main.initialize();
		main.process();
	    FileInputStream fis = new FileInputStream(upfilename);
	    filename="SVG-FILE\\"+filename;
	    
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
			setFilepath("");
		}

		SvgFileId svgfileid=new SvgFileId(this.getarticleNo(),this.getestand(),this.getside());
		SvgFile svgfile= new SvgFile(svgfileid);
		svgfile.setSourcefile(photo);
		svgfile.setArticleNo(this.getarticleNo());
		svgfile.setEstand(this.getestand());
		svgfile.setSide(this.getside());
		
		this.getUploadfiledao().merge(svgfile);
		return "success";
	} catch (Exception e) {
		e.printStackTrace();
		return "failed";
	} finally {
	}
	
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
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getarticleNo() {
		return articleNo;
	}
	public void setarticleNo(String ArticleNo) {
		articleNo = ArticleNo;
	}
	public String getestand() {
		return estand;
	}

	public void setestand(String Estand) {
		estand = Estand;
	}

	public String getside() {
		return side;
	}
	public void setside(String Side) {
		side = Side;
	}

}
