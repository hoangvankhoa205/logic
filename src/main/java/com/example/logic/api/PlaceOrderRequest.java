package com.example.logic.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PlaceOrderRequest(
    @NotBlank String restaurantId,
    @Size(min = 1) List<Item> items
) {
  public record Item(@NotBlank String menuId, @Min(1) int qty) {}
}
