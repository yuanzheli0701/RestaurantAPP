module com.example.restaurantapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    exports com.example.restaurantapp;
    opens com.example.restaurantapp to javafx.fxml;
}
