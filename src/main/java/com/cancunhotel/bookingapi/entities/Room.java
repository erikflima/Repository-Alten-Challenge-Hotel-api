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
@Table(name = "room")
@ApiModel(description = "Represents a room")
public class Room implements Serializable {

	private static final long serialVersionUID = 8938231839389790692L;

	@Id                                              
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "number", nullable = false)
	private Integer number;
	
	@Column(name = "creation_date", nullable = false)
	private LocalDate creationDate;	

	@Column(name = "last_update", nullable = false)
	private LocalDate lastUpdate;

	
	public Room() {
	}

	//------------------------------//
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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
		
		return "\n Room: \n" +
			   " > id = " +id+ "\n" +
			   " > number = " +number+ "\n";
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