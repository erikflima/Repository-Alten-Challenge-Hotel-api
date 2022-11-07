package com.cancunhotel.bookingapi.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.cancunhotel.bookingapi.dtos.CheckInCheckOutDatesDto;
import com.cancunhotel.bookingapi.dtos.PlaceAReservationDto;
import com.cancunhotel.bookingapi.entities.Client;
import com.cancunhotel.bookingapi.entities.Reservation;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import com.cancunhotel.bookingapi.services.ClientService;
import com.cancunhotel.bookingapi.services.ReservationService;
import com.cancunhotel.bookingapi.swagger.ReservationSwaggerDocumentation;
import com.cancunhotel.bookingapi.util.Converter;
import com.cancunhotel.bookingapi.util.InputParameterValidator;


@RestController
@RequestMapping(value="/api/reservation", produces="application/json")
public class ReservationController implements ReservationSwaggerDocumentation{


    @Autowired
    ReservationService reservationService;

    @Autowired
    ClientService clientService;   

    @Autowired
    private MessageSource messageSource;


	@PostMapping(value = "/checkroomavailability")
	public ResponseEntity< StandardizedResponse< Boolean > > checkRoomAvailability( @Valid @RequestBody CheckInCheckOutDatesDto checkInCheckOutDatesDto, BindingResult bindingResult ){


		StandardizedResponse<Boolean> standardizedResponse = new StandardizedResponse<Boolean>();


		//General validations
		boolean validationResultContainsErrors = InputParameterValidator.checkBindingResultValidation( bindingResult, standardizedResponse );
		if( validationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		//Specific validations.
		boolean specificValidationResultContainsErrors = InputParameterValidator.validateCheckInCheckOutDatesDto( checkInCheckOutDatesDto, standardizedResponse );
		if( specificValidationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		Boolean isRoomAvailableOnDesiredDates = reservationService.checkRoomAvailability( checkInCheckOutDatesDto );


		standardizedResponse.setResponseContent( isRoomAvailableOnDesiredDates );


		return ResponseEntity.ok().body( standardizedResponse );
	}    


	@PostMapping(value = "/placeareservation")
	public ResponseEntity< StandardizedResponse< Reservation > > placeAReservation(  @Valid @RequestBody  PlaceAReservationDto placeAReservationDto,  BindingResult bindingResult ){


		StandardizedResponse<Reservation> standardizedResponse = new StandardizedResponse<Reservation>();		
		

		//General validations
		boolean validationResultContainsErrors = InputParameterValidator.checkBindingResultValidation( bindingResult, standardizedResponse );
		if( validationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		CheckInCheckOutDatesDto checkInCheckOutDatesDto = Converter.placeAReservationDtoToCheckInCheckOutDatesDto( placeAReservationDto );
		
		
		//Specific validations.
		boolean specificValidationResultContainsErrors = InputParameterValidator.validateCheckInCheckOutDatesDto( checkInCheckOutDatesDto , standardizedResponse );
		if( specificValidationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}		


		//Check the dates are available
		Boolean isRoomAvailableOnDesiredDates = reservationService.checkRoomAvailability( checkInCheckOutDatesDto );
		if( !isRoomAvailableOnDesiredDates ){

			standardizedResponse.getErrors().add( messageSource.getMessage("reservation.controler.placeAReservation.msg6", null, LocaleContextHolder.getLocale() ) );
			
			return ResponseEntity.badRequest().body( standardizedResponse );
		}
		
		
		//Dados do client - Verifico se o cliente existe	
		Optional<Client> optClient = clientService.findById( placeAReservationDto.getClientId() );
		if( !optClient.isPresent() ){
			
			standardizedResponse.getErrors().add( messageSource.getMessage("reservation.controler.placeAReservation.msg7", null, LocaleContextHolder.getLocale() ) );

			return ResponseEntity.badRequest().body( standardizedResponse );			
		}

	
		Reservation reservation = Converter.placeAReservationDtoToReservation( placeAReservationDto, optClient.get() );
		Reservation reservationRegistered = reservationService.save( reservation );
		
		
		URI uriToGetTheReservation = ServletUriComponentsBuilder.fromCurrentContextPath()																
                .path("api/reservation/findbyid/{id}")
                .buildAndExpand( reservationRegistered.getId() )
                .toUri();		
		
	
		standardizedResponse.setResponseContent( reservation );
		
		return ResponseEntity.created( uriToGetTheReservation ).body( standardizedResponse );
	
	}


	@DeleteMapping(value = "/cancel/{id}")
	public ResponseEntity< StandardizedResponse< Reservation > > cancel( @PathVariable("id") Long id) {


		StandardizedResponse<Reservation> standardizedResponse = new StandardizedResponse<Reservation>();


		Optional<Reservation> optReservation = reservationService.findById( id );


		if ( !optReservation.isPresent() ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("reservation.controler.cancel.msg8", null, LocaleContextHolder.getLocale() ) );

			return ResponseEntity.badRequest().body( standardizedResponse );	

		}


		reservationService.deleteAReservation( id );		
		
		standardizedResponse.setResponseContent( optReservation.get() );

		return ResponseEntity.ok().body( standardizedResponse );
	}


	@PutMapping(value = "/modify/{id}")
	public ResponseEntity< StandardizedResponse< Reservation > > modify( @PathVariable("id") Long id,
												                         @Valid @RequestBody CheckInCheckOutDatesDto checkInCheckOutDatesDto, 
																					         BindingResult bindingResult ) {


		StandardizedResponse<Reservation> standardizedResponse = new StandardizedResponse<Reservation>();
		

		//General validations
		boolean validationResultContainsErrors = InputParameterValidator.checkBindingResultValidation( bindingResult, standardizedResponse );
		if( validationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		//Specific validations.
		boolean specificValidationResultContainsErrors = InputParameterValidator.validateCheckInCheckOutDatesDto( checkInCheckOutDatesDto, standardizedResponse );
		if( specificValidationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}

		
		//Check if the reservation exists.
		Optional<Reservation> optReservation = reservationService.findById( id );
		if ( !optReservation.isPresent() ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("reservation.controler.modify.msg9", null, LocaleContextHolder.getLocale() ) );
			
			return ResponseEntity.badRequest().body( standardizedResponse );			
			
		}		


		//Check if the dates are available.
		Boolean isRoomAvailableOnDesiredDates = reservationService.checkRoomAvailability( checkInCheckOutDatesDto );	
		if( !isRoomAvailableOnDesiredDates ){

			standardizedResponse.getErrors().add( messageSource.getMessage("reservation.controler.modify.msg10", null, LocaleContextHolder.getLocale() ) );
			
			return ResponseEntity.badRequest().body( standardizedResponse );
		}

		
		//Update reservation.
		Reservation reservationToUpdate = updateClientDates( optReservation.get(), checkInCheckOutDatesDto );
		standardizedResponse.setResponseContent( reservationService.save( reservationToUpdate ) );
		

		return ResponseEntity.ok().body( standardizedResponse );

	}


	@GetMapping("/findbyid/{id}")  
	public ResponseEntity< StandardizedResponse< Reservation > > findById( @PathVariable Long id ) {
		

		StandardizedResponse<Reservation> standardizedResponse = new StandardizedResponse<Reservation>();
		
		
		Optional<Reservation> optReservation = reservationService.findById( id );
		
		
		if ( !optReservation.isPresent() ) {

			return ResponseEntity.noContent().build();		
			
		}

	
		standardizedResponse.setResponseContent( optReservation.get() );
		
		return ResponseEntity.ok().body( standardizedResponse );
		
	}		


	@GetMapping(value = "/findall")
	public  ResponseEntity< StandardizedResponse< List<Reservation> > >  findAll() {


		StandardizedResponse< List<Reservation> > standardizedResponse = new StandardizedResponse< List<Reservation> >();
		
		List<Reservation> reservations = reservationService.findAll();
		
		standardizedResponse.setResponseContent( reservations );	
		
		return ResponseEntity.ok().body( standardizedResponse );
	}	


	private Reservation updateClientDates( Reservation reservation, CheckInCheckOutDatesDto checkInCheckOutDatesDto ) {

		reservation.setCheckin( checkInCheckOutDatesDto.getCheckInDate() );
		reservation.setCheckOut(checkInCheckOutDatesDto.getCheckOutDate() );
		
		return reservation;
	}

}