package com.cancunhotel.bookingapi.util;
import org.springframework.stereotype.Component;
import com.cancunhotel.bookingapi.dtos.CheckInCheckOutDatesDto;
import com.cancunhotel.bookingapi.dtos.ClientDto;
import com.cancunhotel.bookingapi.dtos.PlaceAReservationDto;
import com.cancunhotel.bookingapi.entities.Client;
import com.cancunhotel.bookingapi.entities.Reservation;
import com.cancunhotel.bookingapi.entities.Room;


@Component
public class Converter {

	public static Client convertClientDTOToClient( ClientDto clientDto ){
		
		Client client = new Client();

		client.setName( clientDto.getName() );
		client.setEmail( clientDto.getEmail() );	
		client.setPhone( clientDto.getPhone() );		
		
		return client;
	}

	
	public static CheckInCheckOutDatesDto placeAReservationDtoToCheckInCheckOutDatesDto( PlaceAReservationDto placeAReservationDto ){
	
		return new CheckInCheckOutDatesDto ( placeAReservationDto.getCheckInDate(), placeAReservationDto.getCheckOutDate() );
	}


	public static Reservation placeAReservationDtoToReservation( PlaceAReservationDto placeAReservationDto, Client client) {
		
		Reservation reservation = new Reservation();
		
		reservation.setCheckin( placeAReservationDto.getCheckInDate() );
		reservation.setCheckOut( placeAReservationDto.getCheckOutDate() );
		
		Room room = new Room();
		room.setId( 1l ); //Fixed
		room.setNumber(7); //Fixed
		reservation.setRoom( room );

		reservation.setClient( client );
		
		return reservation;
	}	
}