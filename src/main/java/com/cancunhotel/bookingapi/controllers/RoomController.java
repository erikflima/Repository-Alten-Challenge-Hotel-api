package com.cancunhotel.bookingapi.controllers;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cancunhotel.bookingapi.entities.Room;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import com.cancunhotel.bookingapi.services.RoomService;
import com.cancunhotel.bookingapi.swagger.RoomSwaggerDocumentation;


@RestController
@RequestMapping(value="/api/room", produces="application/json")
public class RoomController implements RoomSwaggerDocumentation{
	
	
    @Autowired
    RoomService roomService;	
	
    
	@GetMapping("/findbyid/{id}")  
	public ResponseEntity< StandardizedResponse< Room > > findById( @PathVariable Long id ) {
		

		StandardizedResponse<Room> standardizedResponse = new StandardizedResponse<Room>();
		
		
		Optional<Room> optClient = roomService.findById( id );
		
		
		if ( !optClient.isPresent() ) {

			return ResponseEntity.noContent().build();		
			
		}

	
		standardizedResponse.setResponseContent( optClient.get() );
		
		return ResponseEntity.ok().body( standardizedResponse );
		
	}


}