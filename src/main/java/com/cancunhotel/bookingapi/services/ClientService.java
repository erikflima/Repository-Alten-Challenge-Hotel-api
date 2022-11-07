package com.cancunhotel.bookingapi.services;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cancunhotel.bookingapi.entities.Client;
import com.cancunhotel.bookingapi.repositories.ClientRepository;

@Service
public class ClientService {

	
	private static final Logger log = LoggerFactory.getLogger( ClientService.class);
	
	
	@Autowired
	private ClientRepository clientRepository;
	
	
	public ClientService() {
	}


	public Client register( Client client ) {

		log.info("Registering the client: ", client.getEmail() );
		
		Client registeredClient = clientRepository.save( client );
		
		return registeredClient;
	}


	public Optional<Client> findByEmail( String email) {

		log.info("Finding the client with the email: ", email );		
		
		Client registeredClient = clientRepository.findByEmail( email );

		return Optional.ofNullable( registeredClient);	
	}

	
	public Optional<Client> findById( Long id ) {

		log.info("Finding the client by id: ", id );
		
		Optional<Client> optClient = clientRepository.findById( id );		
		
		return optClient;
	}


	public List<Client> findAll() {
		
		log.info("Finding all clients.");
		
		List<Client> reservations = clientRepository.findAll();		
		
		return reservations;
	}

	
	public void deleteAClient(Long id) {
		
		log.info("Deleting the client with id: ", id);
		
		clientRepository.deleteById(id);		
		
	}
}