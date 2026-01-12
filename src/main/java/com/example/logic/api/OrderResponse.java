package com.example.logic.api;

import java.util.List;

public record OrderResponse(
    String id,
    String restaurantId,
    double total,
    String status,
    List<Item> items
) {
  public record Item(String menuId, int qty, double price) {}
}
