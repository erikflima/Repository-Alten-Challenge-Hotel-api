package com.cancunhotel.bookingapi.dtos;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents client information")
public class ClientDto {


	@ApiModelProperty( value = "Name of the client", required = true )
    @Size(min = 2, max = 200, message = "Name must be between 2 and 60 characters")	
	@NotEmpty(message = "Name cannot be null or empty.")
	@Column(name = "name", nullable = false)
	private String name;
	
	
	@ApiModelProperty( value = "E-mail of the client", required = true )
	@Email(message = "Email should be valid.")
	@NotEmpty(message = "Email cannot be null or empty.")
	@Column(name = "email", nullable = false)
	private String email;	

	
	@ApiModelProperty( value = "Phone number of the client", required = true )
	@NotEmpty(message = "Phone cannot be null or empty.")
	@Column(name = "phone", nullable = false)
	private String phone;


	public ClientDto() {
	}

	//--------------------------------------//
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}