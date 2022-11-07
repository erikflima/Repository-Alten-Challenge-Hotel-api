package com.cancunhotel.bookingapi.swagger;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.cancunhotel.bookingapi.dtos.CheckInCheckOutDatesDto;
import com.cancunhotel.bookingapi.dtos.PlaceAReservationDto;
import com.cancunhotel.bookingapi.entities.Reservation;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api( tags ="Reservation" )
public interface ReservationSwaggerDocumentation{


	@ApiOperation("Check availability of a room on the desired days")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Boolean > > checkRoomAvailability( CheckInCheckOutDatesDto checkInCheckOutDatesDto, 
																					BindingResult bindingResult );    

	
	@ApiOperation("Place a reservation for a room on the desired dates")	
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Reservation > > placeAReservation( PlaceAReservationDto placeAReservationDto,  
																					BindingResult bindingResult );


	@ApiOperation("Cancel a reservation")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Reservation > > cancel( @ApiParam(value="Reservation ID", example = "100") Long id);


	@ApiOperation("Update a reservation's information")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Reservation > > modify( @ApiParam(value="Reservation ID", example = "100") Long id,
												                         CheckInCheckOutDatesDto checkInCheckOutDatesDto, 
																		 BindingResult bindingResult );


	@ApiOperation("Find a reservation by id")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })	
	public ResponseEntity< StandardizedResponse< Reservation > > findById( @ApiParam(value="Reservation ID", example = "100")  Long id );		


	@ApiOperation("	Find all reservations")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	public  ResponseEntity< StandardizedResponse< List<Reservation> > >  findAll();	


}