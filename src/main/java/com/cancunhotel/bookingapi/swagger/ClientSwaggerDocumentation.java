package com.cancunhotel.bookingapi.swagger;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.cancunhotel.bookingapi.dtos.ClientDto;
import com.cancunhotel.bookingapi.entities.Client;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api( tags ="Client" )
public interface ClientSwaggerDocumentation{

	
	@ApiOperation("Register a new client")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Client > > register( ClientDto clientDto, 
																	  BindingResult bindingResult );

	@ApiOperation("Find a client by id")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	public ResponseEntity< StandardizedResponse< Client > > findById( @ApiParam(value="Client ID", example = "100") Long id );

	
	@ApiOperation("	Find all clients")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	public  ResponseEntity< StandardizedResponse< List<Client> > > findAll();

	
	@ApiOperation("Delete a client by id")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Client > > delete( @ApiParam(value="Client ID", example = "100") Long id );

	
	@ApiOperation("Update a client's information")
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request", response = StandardizedResponse.class) })
	public ResponseEntity< StandardizedResponse< Client > > modify( @ApiParam(value="Client ID", example = "100") Long id,
																	ClientDto clientDto, 
																	BindingResult bindingResult );
	
	
	
	
}