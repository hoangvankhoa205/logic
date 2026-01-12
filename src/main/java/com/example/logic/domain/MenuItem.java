package com.example.logic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MenuItem {
  @Id private String id;
  private String restaurantId;
  private String name;
  private double price;

  public MenuItem() {}
  public MenuItem(String id, String restaurantId, String name, double price){
    this.id=id; this.restaurantId=restaurantId; this.name=name; this.price=price;
  }

  public String getId(){return id;} public void setId(String id){this.id=id;}
  public String getRestaurantId(){return restaurantId;} public void setRestaurantId(String restaurantId){this.restaurantId=restaurantId;}
  public String getName(){return name;} public void setName(String name){this.name=name;}
  public double getPrice(){return price;} public void setPrice(double price){this.price=price;}
}
