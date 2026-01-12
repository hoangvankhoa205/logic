package com.example.logic.repo;
import com.example.logic.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepo extends JpaRepository<OrderEntity,String> {}
