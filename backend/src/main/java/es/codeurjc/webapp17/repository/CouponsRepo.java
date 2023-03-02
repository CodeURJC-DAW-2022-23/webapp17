package es.codeurjc.webapp17.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.codeurjc.webapp17.model.Coupon;

public interface CouponsRepo extends JpaRepository<Coupon, Long>{
    List<Coupon> findById(long id);
}
