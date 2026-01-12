package com.example.logic.api;

import com.example.logic.domain.*;
import com.example.logic.repo.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
public class Controllers {
  private final RestaurantRepo restaurants;
  private final MenuItemRepo menu;
  private final OrderRepo orders;

  public Controllers(RestaurantRepo restaurants, MenuItemRepo menu, OrderRepo orders){
    this.restaurants = restaurants; this.menu = menu; this.orders = orders;
  }

  @GetMapping("/health")
  public Map<String,Object> health(){ return Map.of("ok", true, "service", "logic"); }

  @GetMapping("/restaurants")
  public List<Restaurant> listRestaurants(){ return restaurants.findAll(); }

  @GetMapping("/restaurants/{id}/menu")
  public ResponseEntity<?> menu(@PathVariable String id){
    return restaurants.findById(id)
      .<ResponseEntity<?>>map(r -> ResponseEntity.ok(menu.findByRestaurantId(id)))
      .orElseGet(() -> ResponseEntity.status(404).body(Map.of("error","restaurant_not_found")));
  }

  @PostMapping("/orders")
  public ResponseEntity<?> place(@Validated @RequestBody PlaceOrderRequest req){
    if(!restaurants.existsById(req.restaurantId())){
      return ResponseEntity.status(404).body(Map.of("error","restaurant_not_found"));
    }
    double total = 0;
    for(var it : req.items()){
      var m = menu.findById(it.menuId()).orElse(null);
      if(m==null || !m.getRestaurantId().equals(req.restaurantId())){
        return ResponseEntity.badRequest().body(Map.of("error","invalid_menu_item","menuId", it.menuId()));
      }
      total += m.getPrice() * Math.max(1, it.qty());
    }

    var o = new OrderEntity(UUID.randomUUID().toString().substring(0,8));
    o.setRestaurantId(req.restaurantId());
    o.setStatus("PLACED");

    var items = req.items().stream().map(it -> {
      var m = menu.findById(it.menuId()).get();
      var oi = new OrderItem();
      oi.setMenuId(m.getId());
      oi.setQty(Math.max(1, it.qty()));
      oi.setPrice(m.getPrice());
      oi.setOrder(o);
      return oi;
    }).collect(Collectors.toList());
    o.setItems(items);
    o.setTotal(Math.round(total * 100.0) / 100.0);

    orders.save(o);

    return ResponseEntity.status(201).body(new OrderResponse(
      o.getId(), o.getRestaurantId(), o.getTotal(), o.getStatus(),
      o.getItems().stream().map(x -> new OrderResponse.Item(x.getMenuId(), x.getQty(), x.getPrice())).toList()
    ));
  }

  @GetMapping("/orders")
  public List<OrderResponse> list(){
    return orders.findAll().stream().map(o -> new OrderResponse(
      o.getId(), o.getRestaurantId(), o.getTotal(), o.getStatus(),
      o.getItems().stream().map(x -> new OrderResponse.Item(x.getMenuId(), x.getQty(), x.getPrice())).toList()
    )).toList();
  }

  @PatchMapping("/orders/{id}/status")
  public ResponseEntity<?> update(@PathVariable String id, @RequestBody Map<String,String> body){
    var o = orders.findById(id).orElse(null);
    if(o==null) return ResponseEntity.status(404).body(Map.of("error","order_not_found"));
    var status = body.getOrDefault("status", "");
    var allowed = List.of("PLACED","CONFIRMED","PREPARING","OUT_FOR_DELIVERY","DELIVERED","CANCELLED");
    if(!allowed.contains(status)) return ResponseEntity.badRequest().body(Map.of("error","invalid_status"));
    o.setStatus(status);
    orders.save(o);
    return ResponseEntity.ok(Map.of("id", o.getId(), "status", o.getStatus()));
  }
}
