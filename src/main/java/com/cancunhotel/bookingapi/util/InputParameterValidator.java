package com.cancunhotel.bookingapi.util;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.cancunhotel.bookingapi.dtos.CheckInCheckOutDatesDto;
import com.cancunhotel.bookingapi.response.StandardizedResponse;


@Component
public class InputParameterValidator {


	private static final Logger log = LoggerFactory.getLogger( InputParameterValidator.class );


	
	public static boolean checkBindingResultValidation( BindingResult bindingResult, StandardizedResponse<?> standardizedResponse  ){
			
		
		if ( bindingResult.hasErrors() ) {
			
			
			log.error( "There are validation errors in the received dto object: ", bindingResult.getAllErrors() );
			
			
			List<ObjectError> errorList = bindingResult.getAllErrors();
			
			
			for( ObjectError auxiliary : errorList  ){
				
				//Pegar a mensagem de erro da posicao atual.
				String extractedErrorMessage = auxiliary.getDefaultMessage();	

				//Preparar mensagem de erro os dados recebidos estejam no tipo incorreto.
				if( auxiliary.getCode().equals("typeMismatch") ){
					
					extractedErrorMessage = "The parameters received are not correctly typed (text, numeric, etc.). Please check the type of parameters.";
				}

				standardizedResponse.getErrors().add( extractedErrorMessage );

			}
					
			//Validation errors were found..
			return true;
		}
		
		//No validation errors found.
		return false;
	}






	public static boolean validateCheckInCheckOutDatesDto( CheckInCheckOutDatesDto checkInCheckOutDatesDto, StandardizedResponse<?> standardizedResponse ){
		
		
		boolean thereAreValidationErrors = false;
	    LocalDate tomorrow = LocalDate.now().plusDays(1);
		LocalDate checkInDate  = checkInCheckOutDatesDto.getCheckInDate();
		LocalDate checkOutDate = checkInCheckOutDatesDto.getCheckOutDate();		


		//Rule - The check-in date must be before or on the same day as the check-out date
		if( checkOutDate.isBefore( checkInDate) ){
			
			thereAreValidationErrors = true;
			
			standardizedResponse.getErrors().add( "The check-in date must be before or on the same day as the check-out date." );
		}


		//Rule - All reservations start at least the next day of booking.
		if( checkInDate.isBefore(tomorrow) ){
			
			thereAreValidationErrors = true;
			
			standardizedResponse.getErrors().add( "The check-in date must be at least the next day of booking." );
		}		


		//Rule - Can’t be reserved more than 30 days in advance
		LocalDate checkInDatePlus30Days = checkInDate.plusDays(30);
		if( checkInDate.isAfter( checkInDatePlus30Days ) ){
			
			thereAreValidationErrors = true;
			
			standardizedResponse.getErrors().add( "The reservation cannot be made more than 30 days in advance." );
		}	

		
		//Rule - The stay can’t be longer than 3 days
	    Period period = Period.between( checkInDate, checkOutDate); 
	    int totalOfStayDays = Math.abs( period.getDays() +1 ); 		
		if( totalOfStayDays > 3 ){
			
			thereAreValidationErrors = true;
			
			standardizedResponse.getErrors().add( "The stay can’t be longer than 3 days. Please check the dates." );
		}		
		
		
		if( thereAreValidationErrors ){
			return true;			
		}
		
		
		return false;

	}	

	

}