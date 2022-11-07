package com.cancunhotel.bookingapi.dtos;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents information to place a reservation")
public class PlaceAReservationDto extends  CheckInCheckOutDatesDto{


	@ApiModelProperty( value = "Client ID", required = true )
	@NotNull(message = "The field 'clientId' cannot be null")
	@PositiveOrZero(message = "The field 'clientId' must be a positive number or zero.")
	private Long clientId;


	public PlaceAReservationDto() {
	}

	//--------------------------------------//
	
	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

} 