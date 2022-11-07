package com.cancunhotel.bookingapi.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cancunhotel.bookingapi.entities.Client;


public interface ClientRepository extends JpaRepository<Client, Long> {
	
	public Client findByEmail( String email );
	

	public Optional<Client> findById( Long id );

}