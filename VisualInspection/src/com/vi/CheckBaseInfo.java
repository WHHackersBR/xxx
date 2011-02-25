package com.vi;

import com.vi.data.ErrMessage;

public class CheckBaseInfo {

	private String errorOutput="";   

	public String checkInfo(String po,String ws,String side,String id) {
		//errorOutput="WS:"+formData.getWorkstationNr()+"PO:"+errorOutput+formData.getPoNo();
		if (po==null || po.equals("")){
			errorOutput=ErrMessage.NullPO+ "<br>";
		}
		if( ws==null || ws.equals("") ){
			errorOutput=errorOutput+ErrMessage.NullWS+"<br>";
		}
		if (side==null || side.equals("")){
			errorOutput=errorOutput+ErrMessage.NullSIDE+"<br>";
		}
		if (id==null || id.equals("")){
			errorOutput=errorOutput+ErrMessage.NullOperatorID+"<br>";
		}
		return errorOutput;
	}

	
}
