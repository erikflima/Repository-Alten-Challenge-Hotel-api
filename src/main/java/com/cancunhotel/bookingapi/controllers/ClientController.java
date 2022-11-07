package com.cancunhotel.bookingapi.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cancunhotel.bookingapi.dtos.ClientDto;
import com.cancunhotel.bookingapi.entities.Client;
import com.cancunhotel.bookingapi.response.StandardizedResponse;
import com.cancunhotel.bookingapi.services.ClientService;
import com.cancunhotel.bookingapi.services.ReservationService;
import com.cancunhotel.bookingapi.swagger.ClientSwaggerDocumentation;
import com.cancunhotel.bookingapi.util.Converter;
import com.cancunhotel.bookingapi.util.InputParameterValidator;


@RestController
@RequestMapping(value="/api/client", produces="application/json")
public class ClientController implements ClientSwaggerDocumentation{


    @Autowired
    ClientService clientService;
    
    @Autowired
    ReservationService reservationService;

    @Autowired
    private MessageSource messageSource;


	@PostMapping(value = "/register")
	public ResponseEntity< StandardizedResponse< Client > > register( @Valid @RequestBody ClientDto clientDto, BindingResult bindingResult ){


		StandardizedResponse<Client> standardizedResponse = new StandardizedResponse<Client>();

		//General validations
		boolean validationResultContainsErrors = InputParameterValidator.checkBindingResultValidation( bindingResult, standardizedResponse );
		if( validationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		// Check if someone is already registered
		Optional<Client> optClient = clientService.findByEmail( clientDto.getEmail() );
		
		if (optClient.isPresent() ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("client.controler.register.msg1", null, LocaleContextHolder.getLocale() )  );
			
			return ResponseEntity.badRequest().body( standardizedResponse );
		}

	
		Client client = Converter.convertClientDTOToClient( clientDto );

		
		Client clientRegistered =  clientService.register( client );


		URI uriToGetTheClient = ServletUriComponentsBuilder.fromCurrentContextPath()																
				                                                .path("api/client/findbyid/{id}")
			                                                    .buildAndExpand( clientRegistered.getId() )
			                                                    .toUri();


		standardizedResponse.setResponseContent( clientRegistered );
		
		return ResponseEntity.created( uriToGetTheClient ).body( standardizedResponse );

	}  


	@GetMapping("/findbyid/{id}")  
	public ResponseEntity< StandardizedResponse< Client > > findById( @PathVariable Long id ) {
		

		StandardizedResponse<Client> standardizedResponse = new StandardizedResponse<Client>();
		
		
		Optional<Client> optClient = clientService.findById( id );
		
		
		if ( !optClient.isPresent() ) {

			return ResponseEntity.noContent().build();		
			
		}

	
		standardizedResponse.setResponseContent( optClient.get() );
		
		return ResponseEntity.ok().body( standardizedResponse );
		
	}	


	@GetMapping(value = "/findall")
	public  ResponseEntity< StandardizedResponse< List<Client> > > findAll() {


		StandardizedResponse< List<Client> > standardizedResponse = new StandardizedResponse< List<Client> >();
		
		List<Client> clients = clientService.findAll();


		if ( clients.isEmpty() ) {

			return ResponseEntity.noContent().build();		

		}


		standardizedResponse.setResponseContent( clients );	
		
		return ResponseEntity.ok().body( standardizedResponse );
	}	


	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity< StandardizedResponse< Client > > delete( @PathVariable("id") Long id ) {


		StandardizedResponse<Client> standardizedResponse = new StandardizedResponse<Client>();


		Optional<Client> optClient = clientService.findById( id );
		if ( !optClient.isPresent() ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("client.controler.delete.msg2", null, LocaleContextHolder.getLocale() )  );

			return ResponseEntity.badRequest().body( standardizedResponse );	

		}

		
		int total =  reservationService.countReservationsByClient( id );
		if ( total > 0 ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("client.controler.delete.msg3", null, LocaleContextHolder.getLocale() ) );
			
			return ResponseEntity.badRequest().body( standardizedResponse );	

		}		


		clientService.deleteAClient( id );		
		
		standardizedResponse.setResponseContent( optClient.get() );

		return ResponseEntity.ok().body( standardizedResponse );
	}


	@PutMapping(value = "/modify/{id}")
	public ResponseEntity< StandardizedResponse< Client > > modify( @PathVariable("id") Long id,
																	@Valid @RequestBody ClientDto clientDto, 
																	BindingResult bindingResult ) {


		StandardizedResponse<Client> standardizedResponse = new StandardizedResponse<Client>();
		

		//General validations
		boolean validationResultContainsErrors = InputParameterValidator.checkBindingResultValidation( bindingResult, standardizedResponse );
		if( validationResultContainsErrors ){

			return ResponseEntity.badRequest().body( standardizedResponse );
		}


		//Check if the client exists.
		Optional<Client> optClient = clientService.findById( id );
		if ( !optClient.isPresent() ) {

			standardizedResponse.getErrors().add( messageSource.getMessage("client.controler.modify.msg4", null, LocaleContextHolder.getLocale() ) );

			return ResponseEntity.badRequest().body( standardizedResponse );
			
		}		


		//Check if the new email already belongs to another client.
		 boolean equal = clientDto.getEmail().equals( optClient.get().getEmail() );
		 if( !equal ){
			 
				Optional<Client> optClientSameEmail = clientService.findByEmail( clientDto.getEmail() );		
				if ( optClientSameEmail.isPresent() ) {
					
					standardizedResponse.getErrors().add( messageSource.getMessage("client.controler.modify.msg5", null, LocaleContextHolder.getLocale() ) );
					
					return ResponseEntity.badRequest().body( standardizedResponse );
				}			 
			 
		 }
		
		
			Client clientToUpdate = updateClient( clientDto, optClient.get()  );

			standardizedResponse.setResponseContent( clientService.register( clientToUpdate ) );

			
		return ResponseEntity.ok().body( standardizedResponse );

	}


	private Client updateClient( ClientDto clientDto, Client client) {

		client.setName( clientDto.getName() );
		client.setEmail( clientDto.getEmail() );
		client.setPhone( clientDto.getPhone() );
		
		return client;
	}
	
}