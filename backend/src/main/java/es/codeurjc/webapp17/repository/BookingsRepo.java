package es.codeurjc.webapp17.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Booking;

public interface BookingsRepo extends JpaRepository<Booking, Long>{
}
