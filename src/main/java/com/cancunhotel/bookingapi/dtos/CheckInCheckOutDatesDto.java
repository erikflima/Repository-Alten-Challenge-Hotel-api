package com.cancunhotel.bookingapi.dtos;
import java.time.LocalDate;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Represents the desired check-in and check-out dates")
public class CheckInCheckOutDatesDto {
	

	@ApiModelProperty( value = "Check-in date", required = true )
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "The field 'checkInDate' cannot be null")
	@Future(message = "The field 'checkInDate' must be a future date")
	private LocalDate checkInDate;
	
	@ApiModelProperty( value = "Check-out date", required = true )
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "The field 'checkOutDate' cannot be null")
	@Future(message = "The field 'checkOutDate' must be a future date")
	private LocalDate checkOutDate;	


	public CheckInCheckOutDatesDto() {
	}

	public CheckInCheckOutDatesDto( LocalDate checkInDate, LocalDate checkOutDate) {
		
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}
	
	//--------------------------------------//
	
	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}


	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

}