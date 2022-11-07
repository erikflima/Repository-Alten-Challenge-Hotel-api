package com.cancunhotel.bookingapi.services;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cancunhotel.bookingapi.entities.Room;
import com.cancunhotel.bookingapi.repositories.RoomRepository;

@Service
public class RoomService {


	private static final Logger log = LoggerFactory.getLogger( ClientService.class);
	
	
	@Autowired
	private RoomRepository roomRepository;


	public RoomService() {
	}

	//-----------------------------------------------//

	public Optional<Room> findById( Long id ) {

		log.info("Finding room by id: ", id );
		
		Optional<Room> optClient = roomRepository.findById( id );		
		
		return optClient;
	}
}