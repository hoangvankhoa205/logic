package com.example.logic.repo;
import com.example.logic.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RestaurantRepo extends JpaRepository<Restaurant,String> {}
