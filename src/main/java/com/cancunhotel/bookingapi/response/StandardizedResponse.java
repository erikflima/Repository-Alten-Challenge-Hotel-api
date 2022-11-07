package com.cancunhotel.bookingapi.response;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents the standard response format for information transfer and error list.")
public class StandardizedResponse<ReceivedClass> {


	@ApiModelProperty( value = "Content", required = true )
	private ReceivedClass responseContent;
	
	@ApiModelProperty( value = "Message error list", required = true )
	private List<String> errors;


	public StandardizedResponse(){
	}

	//----------------------------------------------//	

	public ReceivedClass getResponseContent() {
		return responseContent;
	}


	public void setResponseContent(ReceivedClass responseContent) {
		this.responseContent = responseContent;
	}
	
	
	public List<String> getErrors() {
		
		if (this.errors == null) {
			
			//It does not return "null", but an empty object.
			this.errors = new ArrayList<String>();
		}
		
		return errors;
	}

	public void setErrors( List<String> errors ) {
		
		this.errors = errors;
	}

}