package com.example.sghapi.model.repository;

import com.example.sghapi.model.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
