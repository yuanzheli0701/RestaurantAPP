package com.example.restaurantapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class KitchenDashboard extends Application {

    // 🎨 统一配色方案
    private final String COLOR_PRIMARY = "#FA541C";
    private final String COLOR_DARK = "#FFFFFF";
    private final String COLOR_TEXT_GRAY = "#A9B8C9";
    private final String COLOR_BG_LIGHT = "#1D2633";
    private final String COLOR_CARD_BG = "#2A3648";
    private final String COLOR_ACCENT = "#1E90FF";
    private final String FONT_FAMILY = "Roboto, Arial";
    private final double CARD_RADIUS = 12.0;

    private FlowPane pendingContainer;
    private FlowPane preparingContainer;

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG_LIGHT + ";");

        // 1. 顶部状态栏
        HBox header = new HBox();
        header.setPadding(new Insets(20, 40, 20, 40));
        header.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("GOURMET KITCHEN TERMINAL");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 26));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label statusInfo = new Label("● LIVE UPDATES ACTIVE");
        statusInfo.setStyle("-fx-text-fill: #52C41A; -fx-font-weight: bold;");

        header.getChildren().addAll(title, spacer, statusInfo);
        root.setTop(header);

        // 2. 主看板区域
        HBox kanbanBoard = new HBox(40);
        kanbanBoard.setPadding(new Insets(30));
        kanbanBoard.setAlignment(Pos.TOP_CENTER);

        VBox pendingCol = createColumn("NEW ORDERS", "#E67E22");
        pendingContainer = new FlowPane(20, 20);
        setupScrollPane(pendingCol, pendingContainer);

        VBox preparingCol = createColumn("IN PREPARATION", COLOR_ACCENT);
        preparingContainer = new FlowPane(20, 20);
        setupScrollPane(preparingCol, preparingContainer);

        kanbanBoard.getChildren().addAll(pendingCol, preparingCol);
        root.setCenter(kanbanBoard);

        // ============================================================
        // 🚀 核心联动逻辑：监听 OrderService 中的订单列表
        // ============================================================
        OrderService.getOrders().addListener((ListChangeListener<OrderService.OrderData>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (OrderService.OrderData newOrder : change.getAddedSubList()) {
                        // 使用 Platform.runLater 确保在 UI 线程更新界面
                        Platform.runLater(() -> {
                            VBox newCard = createOrderCard(newOrder.getId(), newOrder.getTableNum(), newOrder.getItems(), false);
                            pendingContainer.getChildren().add(newCard);
                        });
                    }
                }
            }
        });

        // 加载初始模拟数据
        loadMockOrders();

        Scene scene = new Scene(root, 1280, 850);
        stage.setTitle("Chef Station - The Gourmet");
        stage.setScene(scene);
        stage.show();
    }

    private VBox createColumn(String title, String accentColor) {
        VBox col = new VBox(20);
        col.setPrefWidth(580);
        Label lbl = new Label(title);
        lbl.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 22));
        lbl.setTextFill(Color.web(accentColor));
        lbl.setStyle("-fx-border-color: transparent transparent " + accentColor + " transparent; " +
                "-fx-border-width: 0 0 3 0; -fx-padding: 0 0 10 0;");
        col.getChildren().add(lbl);
        return col;
    }

    private void setupScrollPane(VBox col, FlowPane container) {
        ScrollPane sp = new ScrollPane(container);
        sp.setFitToWidth(true);
        sp.setPrefHeight(750);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent; -fx-viewport-background-color: transparent; -fx-border-color: transparent;");
        col.getChildren().add(sp);
    }

    private VBox createOrderCard(String orderId, String tableNum, List<String> items, boolean isPreparing) {
        VBox card = new VBox(15);
        card.setPrefWidth(265);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; " +
                "-fx-background-radius: " + CARD_RADIUS + "; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 10, 0, 0, 5);");

        HBox head = new HBox();
        Label tLabel = new Label("TABLE " + tableNum);
        tLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        tLabel.setTextFill(Color.WHITE);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label idLabel = new Label("#" + orderId);
        idLabel.setTextFill(Color.web(COLOR_TEXT_GRAY));
        idLabel.setFont(Font.font(FONT_FAMILY, 14));
        head.getChildren().addAll(tLabel, spacer, idLabel);

        VBox itemList = new VBox(10);
        for (String item : items) {
            Label iLabel = new Label("• " + item);
            iLabel.setFont(Font.font(FONT_FAMILY, 16));
            iLabel.setTextFill(Color.web("#D1D9E6"));
            iLabel.setWrapText(true);
            itemList.getChildren().add(iLabel);
        }

        Button actionBtn = new Button(isPreparing ? "MARK AS READY" : "START COOKING");
        actionBtn.setMaxWidth(Double.MAX_VALUE);
        String btnColor = isPreparing ? COLOR_PRIMARY : COLOR_ACCENT;
        actionBtn.setStyle("-fx-background-color: " + btnColor + "; -fx-text-fill: white; " +
                "-fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 8; -fx-cursor: hand;");

        actionBtn.setOnAction(e -> {
            if (!isPreparing) {
                pendingContainer.getChildren().remove(card);
                preparingContainer.getChildren().add(createOrderCard(orderId, tableNum, items, true));
            } else {
                preparingContainer.getChildren().remove(card);
            }
        });

        card.getChildren().addAll(head, new Separator(), itemList, new Region(), actionBtn);
        return card;
    }

    private void loadMockOrders() {
        // 你也可以通过 OrderService.addOrder 来加载初始数据，或者保持原样
        pendingContainer.getChildren().add(createOrderCard("101", "05", List.of("Grilled Salmon x1", "Red Wine x2"), false));
        preparingContainer.getChildren().add(createOrderCard("099", "03", List.of("Tiramisu x2", "Espresso x2"), true));
    }

    public static void main(String[] args) { launch(args); }
}