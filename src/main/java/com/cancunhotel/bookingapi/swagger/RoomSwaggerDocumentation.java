package com.cancunhotel.bookingapi.swagger;
import org.springframework.http.ResponseEntity;
import com.cancunhotel.bookingapi.entities.Room;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


@Api( tags ="Room" )
public interface RoomSwaggerDocumentation{


	@ApiOperation("Find a room by id")
	@ApiResponses({ @ApiResponse(code = 204, message = "No Content") })
	public ResponseEntity< StandardizedResponse< Room > > findById( @ApiParam(value="Room ID", example = "100") Long id );

}