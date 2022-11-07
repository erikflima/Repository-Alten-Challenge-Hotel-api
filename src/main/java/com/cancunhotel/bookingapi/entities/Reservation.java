package com.cancunhotel.bookingapi.entities;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;


@Entity                                        
@Table(name = "reservation")
@ApiModel(description = "Represents a reservation")
public class Reservation implements Serializable {

	private static final long serialVersionUID = 2983845593379306548L;

	@Id                                              
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "creation_date", nullable = false)
	private LocalDate creationDate;	

	@Column(name = "last_update", nullable = false)
	private LocalDate lastUpdate;
	
	@Column(name = "check_in", nullable = false)
	private LocalDate checkIn;	
	
	@Column(name = "check_out", nullable = false)
	private LocalDate checkOut;	
	
	@ManyToOne(fetch = FetchType.EAGER)	
	private Client client;
	
	@ManyToOne(fetch = FetchType.EAGER)	
	private Room room;
	
	
	public Reservation() {
	}

	//------------------------------//
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
	public LocalDate getCheckin() {
		return checkIn;
	}

	public void setCheckin(LocalDate checkin) {
		this.checkIn = checkin;
	}


	public LocalDate getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(LocalDate checkOut) {
		this.checkOut = checkOut;
	}


	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}
	
	
	//------------------------------//
	
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