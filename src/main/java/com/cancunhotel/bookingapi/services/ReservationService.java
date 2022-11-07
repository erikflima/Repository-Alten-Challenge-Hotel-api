package com.cancunhotel.bookingapi.services;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cancunhotel.bookingapi.dtos.CheckInCheckOutDatesDto;
import com.cancunhotel.bookingapi.entities.Reservation;
import com.cancunhotel.bookingapi.repositories.ReservationRepository;


@Service
public class ReservationService {


	private static final Logger log = LoggerFactory.getLogger( ReservationService.class);


	@Autowired
	private ReservationRepository reservationRepository;


	public ReservationService() {
	}


	public Boolean checkRoomAvailability( CheckInCheckOutDatesDto checkInCheckOutDatesDto ) {


		 LocalDate checkInDate  = checkInCheckOutDatesDto.getCheckInDate();
		 LocalDate checkOutDate = checkInCheckOutDatesDto.getCheckOutDate();		
		 

	     Period period = Period.between( checkInDate, checkOutDate); 
	     int diff = Math.abs( period.getDays() +1 );		 
		 
		 
		 for( int i = 0; i < diff; i++ ){
			 
			 List<Reservation> lista =  reservationRepository.verificar( checkInDate.plusDays( i ) );
			 
			 //If there is something on the list, then the room is not available on the date.
			 if( !lista.isEmpty() ) {
				 
				 //The room is not available.
				 return false;
			 }
		
		 }		 
		 
		//The room is available.
		return true;
	}


	public Reservation save( Reservation reservation ) {

		log.info("Registering the reservation.");
		
		Reservation savedReservation = reservationRepository.save( reservation );
		
		return savedReservation;
	}


	public void deleteAReservation(Long id) {
		
		log.info("Canceling the reservation with id: ", id);
		
		reservationRepository.deleteById(id);		
		
	}


	public Optional<Reservation> findById(Long id) {
		
		log.info("Finding the reservation by id: ", id );
		
		Optional<Reservation> optReservation = reservationRepository.findById( id );		
		
		return optReservation;
	}


	public List<Reservation> findAll() {
		
		log.info("Finding all reservations.");
		
		List<Reservation> reservations = reservationRepository.findAll();		
		
		return reservations;
	}
	



	public int countReservationsByClient( Long id)  {
		
		log.info("Finding all reservations.");
		
		int total = reservationRepository.countReservationsByClient(id);
		
		return total;
	}


}