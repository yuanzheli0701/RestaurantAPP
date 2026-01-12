package com.example.restaurantapp;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainLauncher extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. 启动厨师端窗口
        KitchenDashboard kitchen = new KitchenDashboard();
        Stage kitchenStage = new Stage();
        kitchen.start(kitchenStage);

        // 2. 启动客户端窗口
        RestaurantApp app = new RestaurantApp();
        Stage appStage = new Stage();
        app.start(appStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}