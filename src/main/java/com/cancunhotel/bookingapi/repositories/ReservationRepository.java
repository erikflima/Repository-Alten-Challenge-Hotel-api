package com.cancunhotel.bookingapi.repositories;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cancunhotel.bookingapi.entities.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	
	@Query( value ="select * from reservation where ?1 between check_in and check_out", nativeQuery = true)
    public List<Reservation> verificar( LocalDate data );
		

	public Optional<Reservation> findById( Long id );

	
	@Query( value ="select count(*) from reservation where client_id = ?1", nativeQuery = true)
    public int countReservationsByClient( Long id );	
}