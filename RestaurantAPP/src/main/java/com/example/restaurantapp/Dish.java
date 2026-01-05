package com.example.restaurantapp;

import java.util.List;

public class Dish {
    private final String id;
    private final String name;
    private final String category;
    private final double price;
    private final String description;
    private final String prepTime; // 新增：准备时间
    private final String allergens;
    private final List<String> tags;
    private final String imageUrl;

    public Dish(String id, String name, String category, double price, String description, String prepTime, String allergens, List<String> tags, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.prepTime = prepTime;
        this.allergens = allergens;
        this.tags = tags;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getPrepTime() { return prepTime; }
    public String getAllergens() { return allergens; }
    public List<String> getTags() { return tags; }
    public String getImageUrl() { return imageUrl; }

    // 覆盖 equals 和 hashCode 方法，确保 Map 能正确识别相同的菜品
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return id.equals(dish.id); // 仅通过 ID 比较是否是同一个菜品
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
