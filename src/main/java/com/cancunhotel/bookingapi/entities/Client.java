package com.cancunhotel.bookingapi.entities;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;


@Entity                                        
@Table(name = "client")
@ApiModel(description = "Represents a client")
public class Client implements Serializable {

	private static final long serialVersionUID = 2576981550405079774L;

	@Id                                              
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "email", nullable = false)
	private String email;	

	@Column(name = "phone", nullable = false)
	private String phone;

	@Column(name = "creation_date", nullable = false)
	private LocalDate creationDate;
	
	@Column(name = "last_update", nullable = false)
	private LocalDate lastUpdate;
	

	public Client() {
	}

	//------------------------------//

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	
	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	//------------------------------//
	
	@Override
	public String toString() {
		
		return "\n Client: \n" +
			   " > id = " +id+ "\n" +
			   " > e-mail = " +email+ "\n" +
			   " > phone = " +phone+ "\n";
	}


	@PreUpdate
    public void preUpdate() {
		
		lastUpdate = LocalDate.now();
    }


    @PrePersist
    public void prePersist() {

        final LocalDate now = LocalDate.now();
        creationDate = now;
        lastUpdate = now;
    }
}