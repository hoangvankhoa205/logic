package com.example.logic.domain;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity @Table(name="orders")
public class OrderEntity {
  @Id private String id;
  private String restaurantId;
  private double total;
  private String status;     // PLACED, CONFIRMED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
  private Instant createdAt = Instant.now();

  @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
  private List<OrderItem> items = new ArrayList<>();

  public OrderEntity() {}
  public OrderEntity(String id){ this.id = id; }

  public String getId(){return id;} public void setId(String id){this.id=id;}
  public String getRestaurantId(){return restaurantId;} public void setRestaurantId(String restaurantId){this.restaurantId=restaurantId;}
  public double getTotal(){return total;} public void setTotal(double total){this.total=total;}
  public String getStatus(){return status;} public void setStatus(String status){this.status=status;}
  public Instant getCreatedAt(){return createdAt;} public void setCreatedAt(Instant createdAt){this.createdAt=createdAt;}
  public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> items){this.items=items;}
}
