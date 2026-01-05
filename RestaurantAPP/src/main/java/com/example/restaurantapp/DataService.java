package com.example.restaurantapp;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DataService {


    private static String img(String fileName) {
        return Objects.requireNonNull(
                DataService.class.getResource("/images/" + fileName)
        ).toExternalForm();
    }

    public static List<Dish> getMenu() {
        return Arrays.asList(
                // ==================== Appetizers (前菜) - 5 items ====================
                new Dish("A001", "Caprese Skewers", "Appetizer", 8.50,
                        "Fresh mozzarella, cherry tomatoes, and basil drizzled with balsamic glaze.",
                        "10m", "Dairy", Arrays.asList("Vegetarian"), img("A001.jpeg")),
                new Dish("A002", "Tuna Tartare", "Appetizer", 14.00,
                        "Finely diced fresh tuna mixed with avocado and a light soy dressing.",
                        "15m", "Fish, Soy", Arrays.asList("Seafood"), img("A002.jpeg")),
                new Dish("A003", "Aged Cheese Platter", "Appetizer", 12.50,
                        "Selection of three artisan cheeses, served with fig jam and crackers.",
                        "5m", "Dairy, Gluten", Arrays.asList("Vegetarian"), img("A003.jpeg")),
                new Dish("A004", "Prosciutto Melone", "Appetizer", 9.00,
                        "Thin slices of Italian cured ham paired with fresh cantaloupe.",
                        "5m", "None", Arrays.asList("Classic"), img("A004.jpeg")),
                new Dish("A005", "Shrimp Cocktail", "Appetizer", 11.50,
                        "Chilled prawns served with a classic spicy horseradish cocktail sauce.",
                        "10m", "Shellfish", Arrays.asList("Seafood"), img("A005.jpeg")),

                // ==================== Main Course (主菜) - 5 items ====================
                new Dish("M001", "Filet Mignon", "Main Course", 34.90,
                        "8oz premium beef tenderloin, served with asparagus and potato gratin.",
                        "30m", "Dairy", Arrays.asList("Beef"), img("M001.jpeg")),
                new Dish("M002", "Seared Salmon", "Main Course", 26.50,
                        "Wild-caught salmon with lemon-butter sauce and seasonal vegetables.",
                        "25m", "Fish", Arrays.asList("Seafood", "Healthy"), img("M002.jpeg")),
                new Dish("M003", "Truffle Pasta", "Main Course", 22.00,
                        "Homemade tagliatelle in a rich creamy truffle sauce with Parmesan.",
                        "20m", "Gluten, Dairy", Arrays.asList("Vegetarian"), img("M003.jpeg")),
                new Dish("M004", "Roasted Duck Breast", "Main Course", 31.00,
                        "Crispy-skinned duck breast with a cherry reduction sauce and wild rice.",
                        "35m", "None", Arrays.asList("Poultry"), img("M004.jpeg")),
                new Dish("M005", "Vegetable Risotto", "Main Course", 19.50,
                        "Creamy Arborio rice slow-cooked with seasonal market vegetables and herbs.",
                        "25m", "Dairy", Arrays.asList("Vegetarian", "Healthy"), img("M005.jpeg")),

                // ==================== Desserts (甜点) - 5 items ====================
                new Dish("D001", "Molten Lava Cake", "Dessert", 10.00,
                        "Warm chocolate cake with a gooey center, served with vanilla bean ice cream.",
                        "15m", "Gluten, Dairy, Egg", Arrays.asList("Chocolate"), img("D001.jpeg")),
                new Dish("D002", "Creme Brulee", "Dessert", 9.50,
                        "Classic vanilla custard topped with a layer of caramelized sugar.",
                        "10m", "Dairy, Egg", Arrays.asList("Classic"), img("D002.jpeg")),
                new Dish("D003", "Lemon Mousse", "Dessert", 8.00,
                        "Light and tangy lemon mousse with a fresh berry garnish.",
                        "5m", "Dairy", Arrays.asList("Refreshing"), img("D003.jpeg")),
                new Dish("D004", "Tiramisu", "Dessert", 11.00,
                        "Layers of coffee-soaked ladyfingers and mascarpone cream, dusted with cocoa.",
                        "5m", "Dairy, Egg, Gluten", Arrays.asList("Coffee"), img("D004.jpeg")),
                new Dish("D005", "Seasonal Fruit Tart", "Dessert", 9.00,
                        "Buttery shortcrust pastry filled with diplomat cream and fresh berries.",
                        "5m", "Gluten, Dairy", Arrays.asList("Healthy"), img("D005.jpeg")),

                // ==================== Beverages (饮料) - 5 items ====================
                new Dish("B001", "Sparkling Water", "Beverage", 4.00,
                        "Imported natural sparkling mineral water.",
                        "1m", "None", Arrays.asList("Hydrating"), img("B001.jpeg")),
                new Dish("B002", "Espresso", "Beverage", 3.50,
                        "Rich, concentrated coffee brewed under high pressure.",
                        "2m", "None", Arrays.asList("Coffee"), img("B002.jpeg")),
                new Dish("B003", "House Red Wine (Glass)", "Beverage", 11.00,
                        "A full-bodied Cabernet Sauvignon.",
                        "1m", "Sulfite", Arrays.asList("Alcohol"), img("B003.jpeg")),
                new Dish("B004", "Fresh Orange Juice", "Beverage", 5.00,
                        "Freshly squeezed juice from local oranges.",
                        "2m", "None", Arrays.asList("Healthy"), img("B004.jpeg")),
                new Dish("B005", "Mint Lemonade", "Beverage", 6.00,
                        "Refreshing homemade lemonade infused with fresh mint leaves.",
                        "3m", "None", Arrays.asList("Refreshing"), img("B005.jpeg"))
        );
    }
}
