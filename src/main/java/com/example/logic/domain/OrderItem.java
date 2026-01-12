package com.example.logic.domain;

import jakarta.persistence.*;

@Entity
public class OrderItem {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String menuId;
  private int qty;
  private double price;

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="order_id")
  private OrderEntity order;

  public OrderItem() {}

  public Long getId(){return id;} public void setId(Long id){this.id=id;}
  public String getMenuId(){return menuId;} public void setMenuId(String menuId){this.menuId=menuId;}
  public int getQty(){return qty;} public void setQty(int qty){this.qty=qty;}
  public double getPrice(){return price;} public void setPrice(double price){this.price=price;}
  public OrderEntity getOrder(){return order;} public void setOrder(OrderEntity order){this.order=order;}
}
