package com.cancunhotel.bookingapi.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cancunhotel.bookingapi.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}