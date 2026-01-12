package com.example.logic.repo;
import com.example.logic.domain.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MenuItemRepo extends JpaRepository<MenuItem,String> {
  List<MenuItem> findByRestaurantId(String restaurantId);
}
