package com.example.restaurantapp;

import java.util.Arrays;
import java.util.List;

public class DataService {

    public static List<Dish> getMenu() {
        return Arrays.asList(
                // ==================== Appetizers (前菜) - 5 items ====================
                new Dish("A001", "Caprese Skewers", "Appetizer", 8.50, "Fresh mozzarella, cherry tomatoes, and basil drizzled with balsamic glaze.", "10m", "Dairy", Arrays.asList("Vegetarian"), "https://images.unsplash.com/photo-1551939504-d2e85906f2e8?w=600"),
                new Dish("A002", "Tuna Tartare", "Appetizer", 14.00, "Finely diced fresh tuna mixed with avocado and a light soy dressing.", "15m", "Fish, Soy", Arrays.asList("Seafood"), "https://images.unsplash.com/photo-1627883311820-22c95e1355be?w=600"),
                new Dish("A003", "Aged Cheese Platter", "Appetizer", 12.50, "Selection of three artisan cheeses, served with fig jam and crackers.", "5m", "Dairy, Gluten", Arrays.asList("Vegetarian"), "https://images.unsplash.com/photo-1542475143-5d517c5b9679?w=600"),
                new Dish("A004", "Prosciutto Melone", "Appetizer", 9.00, "Thin slices of Italian cured ham paired with fresh cantaloupe.", "5m", "None", Arrays.asList("Classic"), "https://images.unsplash.com/photo-1589408643878-fa6c8d76e330?w=600"),
                new Dish("A005", "Shrimp Cocktail", "Appetizer", 11.50, "Chilled prawns served with a classic spicy horseradish cocktail sauce.", "10m", "Shellfish", Arrays.asList("Seafood"), "https://images.unsplash.com/photo-1510250684288-510061e3d360?w=600"),

                // ==================== Main Course (主菜) - 5 items ====================
                new Dish("M001", "Filet Mignon", "Main Course", 34.90, "8oz premium beef tenderloin, served with asparagus and potato gratin.", "30m", "Dairy", Arrays.asList("Beef"), "https://images.unsplash.com/photo-1550547660-d9225881c039?w=600"),
                new Dish("M002", "Seared Salmon", "Main Course", 26.50, "Wild-caught salmon with lemon-butter sauce and seasonal vegetables.", "25m", "Fish", Arrays.asList("Seafood", "Healthy"), "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=600"),
                new Dish("M003", "Truffle Pasta", "Main Course", 22.00, "Homemade tagliatelle in a rich creamy truffle sauce with Parmesan.", "20m", "Gluten, Dairy", Arrays.asList("Vegetarian"), "https://images.unsplash.com/photo-1551026040-302a20df9c8c?w=600"),
                new Dish("M004", "Roasted Duck Breast", "Main Course", 31.00, "Crispy-skinned duck breast with a cherry reduction sauce and wild rice.", "35m", "None", Arrays.asList("Poultry"), "https://images.unsplash.com/photo-1594220551061-e0e2e28a9561?w=600"),
                new Dish("M005", "Vegetable Risotto", "Main Course", 19.50, "Creamy Arborio rice slow-cooked with seasonal market vegetables and herbs.", "25m", "Dairy", Arrays.asList("Vegetarian", "Healthy"), "https://images.unsplash.com/photo-1621841364531-15a0c36e4f9b?w=600"),

                // ==================== Desserts (甜点) - 5 items ====================
                new Dish("D001", "Molten Lava Cake", "Dessert", 10.00, "Warm chocolate cake with a gooey center, served with vanilla bean ice cream.", "15m", "Gluten, Dairy, Egg", Arrays.asList("Chocolate"), "https://images.unsplash.com/photo-1559986326-8c4608c0205d?w=600"),
                new Dish("D002", "Creme Brulee", "Dessert", 9.50, "Classic vanilla custard topped with a layer of caramelized sugar.", "10m", "Dairy, Egg", Arrays.asList("Classic"), "https://images.unsplash.com/photo-1556910118-63914a275464?w=600"),
                new Dish("D003", "Lemon Mousse", "Dessert", 8.00, "Light and tangy lemon mousse with a fresh berry garnish.", "5m", "Dairy", Arrays.asList("Refreshing"), "https://images.unsplash.com/photo-1600746270605-78e7c10b25e1?w=600"),
                new Dish("D004", "Tiramisu", "Dessert", 11.00, "Layers of coffee-soaked ladyfingers and mascarpone cream, dusted with cocoa.", "5m", "Dairy, Egg, Gluten", Arrays.asList("Coffee"), "https://images.unsplash.com/photo-1588147690326-b9242d5d852c?w=600"),
                new Dish("D005", "Seasonal Fruit Tart", "Dessert", 9.00, "Buttery shortcrust pastry filled with diplomat cream and fresh berries.", "5m", "Gluten, Dairy", Arrays.asList("Healthy"), "https://images.unsplash.com/photo-1610444855476-d0df2c2c019d?w=600"),

                // ==================== Beverages (饮料) - 5 items ====================
                new Dish("B001", "Sparkling Water", "Beverage", 4.00, "Imported natural sparkling mineral water.", "1m", "None", Arrays.asList("Hydrating"), "https://images.unsplash.com/photo-1579208030886-df418a002492?w=600"),
                new Dish("B002", "Espresso", "Beverage", 3.50, "Rich, concentrated coffee brewed under high pressure.", "2m", "None", Arrays.asList("Coffee"), "https://images.unsplash.com/photo-1517700778438-e4b93b865611?w=600"),
                new Dish("B003", "House Red Wine (Glass)", "Beverage", 11.00, "A full-bodied Cabernet Sauvignon.", "1m", "Sulfite", Arrays.asList("Alcohol"), "https://images.unsplash.com/photo-1574888746006-25807afb72b6?w=600"),
                new Dish("B004", "Fresh Orange Juice", "Beverage", 5.00, "Freshly squeezed juice from local oranges.", "2m", "None", Arrays.asList("Healthy"), "https://images.unsplash.com/photo-1582236894056-55a5b51c88de?w=600"),
                new Dish("B005", "Mint Lemonade", "Beverage", 6.00, "Refreshing homemade lemonade infused with fresh mint leaves.", "3m", "None", Arrays.asList("Refreshing"), "https://images.unsplash.com/photo-1553530182-019672658197?w=600")
        );
    }
}
