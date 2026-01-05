package com.example.restaurantapp;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.stream.Collectors;

public class RestaurantApp extends Application {

    private Stage primaryStage;
    private List<Dish> menuData;

    private java.util.Map<Dish, Integer> shoppingCart = new java.util.LinkedHashMap<>();
    private Button cartButton;
    private VBox cartItemsContainer;
    private VBox orderSummaryContainer;
    private Label prepTimeLabel;

    // 🎨 深色主题配色方案 (Dark Mode)
    private final String FONT_FAMILY = "Roboto, Arial";
    private final String COLOR_PRIMARY = "#FA541C";       // 温暖珊瑚红 (强调色)
    private final String COLOR_DARK = "#FFFFFF";          // 文本主色 (白色)
    private final String COLOR_TEXT_GRAY = "#A9B8C9";     // 描述文本 (浅灰蓝)
    private final String COLOR_BG_LIGHT = "#1D2633";      // 整体应用背景 (深海军蓝/深灰)
    private final String COLOR_CARD_BG = "#2A3648";        // 卡片背景/导航栏 (略浅的深蓝)
    private final String COLOR_ACCENT = "#1E90FF";        // 蓝色 (推荐按钮)
    private final double CARD_RADIUS = 12.0;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.menuData = DataService.getMenu();
        primaryStage.setTitle("Gourmet Dining Experience (Dark Mode)");
        showHomeView();
        primaryStage.show();
    }

    // ==================== 动画和效果 ====================
    /** 触发购物车按钮缩放动画以提供视觉反馈 */
    private void animateCartButton() {
        if (cartButton == null) return;

        // 创建缩放动画：持续 0.25 秒，从当前大小缩放到 1.2 倍再缩回
        ScaleTransition st = new ScaleTransition(Duration.millis(250), cartButton);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(1.2);
        st.setToY(1.2);
        st.setCycleCount(2);    // 放大 -> 缩小 (2次循环)
        st.setAutoReverse(true); // 自动反转（即从 1.2 缩放回 1.0）

        st.play(); // 播放动画
    }


    // ==================== 1. 首页 (Home View) ====================
    private void showHomeView() {
        StackPane root = new StackPane();

        VBox bg = new VBox();
        bg.setStyle("-fx-background-color: linear-gradient(to bottom right, #131B29, #3A475A);");

        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(60));

        Label logo = new Label("THE GOURMET");
        logo.setFont(Font.font("Times New Roman", FontWeight.BOLD, 58));
        logo.setTextFill(Color.WHITE);
        logo.setEffect(new DropShadow(25, Color.BLACK));

        Label slogan = new Label("Refined Flavors • Unforgettable Moments");
        slogan.setFont(Font.font(FONT_FAMILY, 22));
        slogan.setTextFill(Color.web("#C0CCDA"));

        HBox btns = new HBox(25);
        btns.setAlignment(Pos.CENTER);

        Button menuBtn = createStyledButton("BROWSE MENU", COLOR_PRIMARY, true);
        menuBtn.setOnAction(e -> showMenuView());

        Button mysteryBtn = createStyledButton("CHEF'S SURPRISE (€25)", "transparent", true);
        mysteryBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 12 35; -fx-background-radius: 30; -fx-border-color: white; -fx-border-radius: 30; -fx-border-width: 2; -fx-cursor: hand;");
        mysteryBtn.setOnAction(e -> showMysteryBoxDialog());

        btns.getChildren().addAll(menuBtn, mysteryBtn);
        content.getChildren().addAll(logo, slogan, new Region(), btns);

        root.getChildren().addAll(bg, content);
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
    }

    // ==================== 2. 菜单页 (Menu View) ====================
    private void showMenuView() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG_LIGHT + ";");
        root.setTop(createNavBar());

        ScrollPane scroll = new ScrollPane();
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scroll.setPadding(new Insets(30, 50, 50, 50));

        VBox content = new VBox(50);
        String[] cats = {"Appetizer", "Main Course", "Dessert", "Beverage"};

        for (String c : cats) {
            VBox section = new VBox(20);
            Label header = new Label(c);
            header.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 30));
            header.setTextFill(Color.web(COLOR_DARK));

            FlowPane grid = new FlowPane();
            grid.setHgap(30); grid.setVgap(30);

            menuData.stream().filter(d -> d.getCategory().equals(c))
                    .forEach(d -> grid.getChildren().add(createMenuCard(d)));

            section.getChildren().addAll(header, grid);
            content.getChildren().add(section);
        }

        scroll.setContent(content);
        root.setCenter(scroll);
        primaryStage.setScene(new Scene(root, 1200, 800));
    }

    // 菜单卡片 (Vertical)
    private VBox createMenuCard(Dish d) {
        VBox card = new VBox();
        card.setPrefSize(280, 360);
        card.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-background-radius: " + CARD_RADIUS + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 4);");

        ImageView img = new ImageView(new Image(d.getImageUrl(), 280, 180, true, true, true));
        img.setFitWidth(280); img.setFitHeight(180);
        Rectangle clip = new Rectangle(280, 180);
        clip.setArcWidth(CARD_RADIUS); clip.setArcHeight(CARD_RADIUS);
        img.setClip(clip);

        VBox info = new VBox(8);
        info.setPadding(new Insets(15));

        Label name = new Label(d.getName());
        name.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        name.setTextFill(Color.web(COLOR_DARK));

        Label prepTime = new Label("🕒 " + d.getPrepTime());
        prepTime.setStyle("-fx-text-fill: " + COLOR_TEXT_GRAY + "; -fx-font-size: 13px;");

        Label desc = new Label(d.getDescription());
        desc.setStyle("-fx-text-fill: " + COLOR_TEXT_GRAY + "; -fx-font-size: 13px;");
        desc.setWrapText(true); desc.setPrefHeight(45);

        HBox bottom = new HBox();
        Label price = new Label("€" + String.format("%.2f", d.getPrice()));
        price.setStyle("-fx-text-fill: " + COLOR_PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 18px;");

        Region r = new Region(); HBox.setHgrow(r, Priority.ALWAYS);

        Button add = new Button("Add");
        add.setStyle("-fx-background-color: " + COLOR_PRIMARY + "; -fx-text-fill: white; -fx-background-radius: 50; -fx-font-weight: bold; -fx-min-width: 50; -fx-min-height: 30; -fx-cursor: hand;");
        add.setOnAction(e -> {
            shoppingCart.put(d, shoppingCart.getOrDefault(d, 0) + 1);
            updateGlobalCartBtn();
            animateCartButton(); // <-- 触发动画
        });

        bottom.getChildren().addAll(price, r, add);
        info.getChildren().addAll(name, prepTime, desc, bottom);
        card.getChildren().addAll(img, info);

        card.setOnMouseClicked(e -> { if(!(e.getTarget() instanceof Button)) showDetailView(d); });
        return card;
    }


    // ==================== 3. 购物车页 (Cart View) ====================
    private void showCartView() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG_LIGHT + ";");
        root.setTop(createNavBar());

        HBox mainLayout = new HBox(40);
        mainLayout.setPadding(new Insets(40));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // --- 左侧：商品列表区域 (65%) ---
        VBox leftPane = new VBox(25);
        leftPane.setPrefWidth(750); // 固定宽度
        leftPane.setMaxWidth(750);

        Label title = new Label("Your Order");
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 36));
        title.setTextFill(Color.web(COLOR_DARK));

        prepTimeLabel = new Label("🕒 Estimated Prep Time: Calculating...");
        prepTimeLabel.setFont(Font.font(FONT_FAMILY, FontWeight.SEMI_BOLD, 16));
        prepTimeLabel.setTextFill(Color.web(COLOR_PRIMARY));

        cartItemsContainer = new VBox(15);

        leftPane.getChildren().addAll(title, prepTimeLabel, cartItemsContainer);

        // --- 右侧：订单摘要区域 (35%) ---
        VBox rightPane = new VBox();
        rightPane.setPrefWidth(380);
        rightPane.setPadding(new Insets(30));
        rightPane.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-background-radius: " + CARD_RADIUS + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 15, 0, 0, 5);");

        Label summaryTitle = new Label("Summary");
        summaryTitle.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 24));
        summaryTitle.setTextFill(Color.web(COLOR_DARK));

        orderSummaryContainer = new VBox(12);

        Button checkoutBtn = createStyledButton("PLACE ORDER NOW", COLOR_PRIMARY, true);
        checkoutBtn.setMaxWidth(Double.MAX_VALUE);
        checkoutBtn.setOnAction(e -> processCheckout());

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #4A566A;");

        rightPane.getChildren().addAll(summaryTitle, separator, orderSummaryContainer, new Separator(), checkoutBtn);

        refreshCartUI();

        mainLayout.getChildren().addAll(leftPane, rightPane);

        ScrollPane scroll = new ScrollPane(mainLayout);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        StackPane aligner = new StackPane(scroll);
        aligner.setAlignment(Pos.TOP_CENTER);

        root.setCenter(aligner);
        primaryStage.setScene(new Scene(root, 1200, 800));
    }

    // 购物车商品行 (Horizontal) - 终极修正：使用 GridPane 确保列对齐和防止截断
    private HBox createCartItemCard(Dish d, int quantity) {
        HBox row = new HBox(10); // HBox 包装
        row.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-padding: 15; -fx-background-radius: " + CARD_RADIUS + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 8, 0, 0, 2);");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setMaxWidth(Double.MAX_VALUE);

        // === 1. 图片和主要信息 (固定左侧) ===
        ImageView thumb = new ImageView(new Image(d.getImageUrl(), 80, 80, true, true, true));
        Rectangle clip = new Rectangle(80, 80);
        clip.setArcWidth(8); clip.setArcHeight(8);
        thumb.setClip(clip);

        VBox info = new VBox(5);
        info.setPrefWidth(260); // 为名称和描述预留宽度
        Label name = new Label(d.getName());
        name.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 16));
        name.setTextFill(Color.web(COLOR_DARK));
        name.setWrapText(true);

        Label desc = new Label(d.getDescription());
        desc.setWrapText(true);
        desc.setTextFill(Color.web(COLOR_TEXT_GRAY));
        desc.setFont(Font.font(12));

        info.getChildren().addAll(name, desc);
        row.getChildren().addAll(thumb, info);


        // === 2. 使用 GridPane 确保中间三列对齐 (单价, 数量, 总价) ===
        GridPane detailsGrid = new GridPane();
        detailsGrid.setHgap(20);
        detailsGrid.setAlignment(Pos.CENTER_RIGHT);

        // 设置列约束，确保每列有固定的最小宽度
        ColumnConstraints col1 = new ColumnConstraints(90); // 单价列
        ColumnConstraints col2 = new ColumnConstraints(120); // 数量/Prep Time 列
        ColumnConstraints col3 = new ColumnConstraints(120); // 行总价列
        col3.setHalignment(HPos.RIGHT); // 总价右对齐
        detailsGrid.getColumnConstraints().addAll(col1, col2, col3);


        // --- A. 单价/Prep Time (第 1 行) ---
        // 单价 (完整数字选项)
        Label unitPriceLabel = new Label("€" + String.format("%.2f", d.getPrice()));
        unitPriceLabel.setTextFill(Color.web(COLOR_DARK));
        unitPriceLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 14));
        GridPane.setHalignment(unitPriceLabel, HPos.LEFT);
        detailsGrid.add(unitPriceLabel, 0, 0);

        Label prepTimeLabel = new Label("Prep: " + d.getPrepTime());
        prepTimeLabel.setTextFill(Color.web(COLOR_TEXT_GRAY));
        prepTimeLabel.setFont(Font.font(13));
        GridPane.setHalignment(prepTimeLabel, HPos.CENTER);
        detailsGrid.add(prepTimeLabel, 1, 0);

        Label lineTotalLabel = new Label("Line Total");
        lineTotalLabel.setTextFill(Color.web(COLOR_TEXT_GRAY));
        lineTotalLabel.setFont(Font.font(13));
        GridPane.setHalignment(lineTotalLabel, HPos.RIGHT);
        detailsGrid.add(lineTotalLabel, 2, 0);


        // --- B. 数量控制 / 总价 (第 2 行) ---

        // 数量控制
        HBox quantityControl = new HBox(5);
        quantityControl.setAlignment(Pos.CENTER); // 确保内部居中

        String controlBtnStyle = "-fx-background-color: " + COLOR_BG_LIGHT + "; -fx-text-fill: " + COLOR_DARK + "; -fx-font-size: 16px; -fx-min-width: 30; -fx-min-height: 30; -fx-background-radius: 5; -fx-cursor: hand;";

        Button minusBtn = new Button("−");
        minusBtn.setStyle(controlBtnStyle);

        Label quantityLabel = new Label(String.valueOf(quantity));
        quantityLabel.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 16));
        quantityLabel.setPadding(new Insets(0, 5, 0, 5));
        quantityLabel.setTextFill(Color.web(COLOR_DARK));

        Button plusBtn = new Button("+");
        plusBtn.setStyle(controlBtnStyle);

        quantityControl.getChildren().addAll(minusBtn, quantityLabel, plusBtn);
        GridPane.setHalignment(quantityControl, HPos.CENTER);
        detailsGrid.add(quantityControl, 1, 1);

        // 总价
        double itemTotal = d.getPrice() * quantity;
        Label price = new Label("€" + String.format("%.2f", itemTotal));
        price.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 22));
        price.setTextFill(Color.web(COLOR_PRIMARY));
        GridPane.setHalignment(price, HPos.RIGHT);
        detailsGrid.add(price, 2, 1);

        // --- 数量操作逻辑 (保持不变) ---
        minusBtn.setOnAction(e -> {
            int newQty = shoppingCart.get(d) - 1;
            if (newQty <= 0) {
                shoppingCart.remove(d);
            } else {
                shoppingCart.put(d, newQty);
            }
            refreshCartUI();
        });

        plusBtn.setOnAction(e -> {
            shoppingCart.put(d, shoppingCart.get(d) + 1);
            refreshCartUI();
        });

        // 填充剩余空间并将 GridPane 添加到主 HBox
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);

        row.getChildren().addAll(filler, detailsGrid);
        return row;
    }

    // refreshCartUI - 更新购物车视图和摘要
    private void refreshCartUI() {
        cartItemsContainer.getChildren().clear();

        if (shoppingCart.isEmpty()) {
            cartItemsContainer.getChildren().add(createEmptyCartState());
        } else {
            for (java.util.Map.Entry<Dish, Integer> entry : shoppingCart.entrySet()) {
                cartItemsContainer.getChildren().add(createCartItemCard(entry.getKey(), entry.getValue()));
            }
            cartItemsContainer.getChildren().add(createUpsellSection());
        }

        orderSummaryContainer.getChildren().clear();
        double subtotal = 0;

        for (java.util.Map.Entry<Dish, Integer> entry : shoppingCart.entrySet()) {
            Dish d = entry.getKey();
            int quantity = entry.getValue();
            double itemTotal = d.getPrice() * quantity;
            subtotal += itemTotal;

            HBox row = new HBox();
            Label name = new Label(d.getName() + " (x" + quantity + ")");
            name.setStyle("-fx-font-size: 14px; -fx-text-fill: " + COLOR_TEXT_GRAY + ";");
            Region r = new Region(); HBox.setHgrow(r, Priority.ALWAYS);
            Label price = new Label("€" + String.format("%.2f", itemTotal));
            price.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + COLOR_DARK + ";");
            row.getChildren().addAll(name, r, price);
            orderSummaryContainer.getChildren().add(row);
        }

        // 计算税费和总价
        double tax = subtotal * 0.05;        // 5% 增值税
        double total = subtotal + tax;

        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: #4A566A;");
        orderSummaryContainer.getChildren().add(separator);

        orderSummaryContainer.getChildren().add(createSummaryRow("Subtotal", subtotal, false));
        orderSummaryContainer.getChildren().add(createSummaryRow("Tax (VAT 5%)", tax, false));

        Separator separator2 = new Separator();
        separator2.setStyle("-fx-background-color: #4A566A;");
        orderSummaryContainer.getChildren().add(separator2);
        orderSummaryContainer.getChildren().add(createSummaryRow("Order Total", total, true));

        updateGlobalCartBtn();
        updatePrepTimeLabel();
    }

    // updateGlobalCartBtn - 更新导航栏上的购物车总数
    private void updateGlobalCartBtn() {
        int totalItems = shoppingCart.values().stream().mapToInt(Integer::intValue).sum();

        if(cartButton != null) {
            cartButton.setText("🛒 Order (" + totalItems + ")");
        }
    }
    private void updatePrepTimeLabel() {
        if (prepTimeLabel == null) return;

        // 1. 智能分组：将购物车拆分为 [前菜/饮料] 和 [主菜/甜点]
        Map<Dish, Integer> starters = new HashMap<>();
        Map<Dish, Integer> mains = new HashMap<>();

        shoppingCart.forEach((dish, qty) -> {
            if (isStarter(dish)) {
                starters.put(dish, qty);
            } else {
                mains.put(dish, qty);
            }
        });

        // 2. 独立计算两组的“厨房制作工时” (Work Time)
        // 这里计算的是：如果厨房只做这组菜，需要多久做完
        int starterWorkTime = calculateGroupPrepTime(starters);
        int mainWorkTime = calculateGroupPrepTime(mains);

        // 3. 计算最终送达时间轴 (Timeline)
        // 核心逻辑：利用客人吃前菜的时间 (Eating Gap) 来抵消主菜的制作时间
        int eatingGap = 15; // 假设客人吃前菜至少需要 15 分钟

        int starterArrival = starterWorkTime;
        int mainArrival;

        if (starterWorkTime == 0) {
            // 场景 A: 没点前菜，主菜直接做
            mainArrival = mainWorkTime;
        } else if (mainWorkTime == 0) {
            // 场景 B: 没点主菜，只显示前菜时间
            mainArrival = 0;
        } else {
            // 场景 C: 前菜 + 主菜 (并行重叠模型)
            // 公式：主菜送达 = 前菜送达 + Max(最小间隔, 主菜耗时 - 提前准备的并行量)

            // 假设：主菜耗时的 40% 可以在前菜阶段或进食阶段并发完成 (如预处理、摆盘)
            int concurrentOffset = (int)(mainWorkTime * 0.4);

            // 计算主菜在前菜之后还需要多久才能好
            // 如果主菜做得很快(小于吃前菜的时间)，那就等吃完(eatingGap)再上
            // 如果主菜做得慢，那就取 (实际耗时 - 并行抵扣)
            int effectiveMainDuration = Math.max(eatingGap, mainWorkTime - concurrentOffset);

            mainArrival = starterArrival + effectiveMainDuration;
        }

        // 4. 构建 UI 显示文本
        StringBuilder sb = new StringBuilder("🕒 Est. Time: ");

        if (starterWorkTime == 0 && mainWorkTime == 0) {
            sb.append("N/A");
        } else if (starterWorkTime > 0 && mainWorkTime == 0) {
            sb.append(starterArrival).append(" mins");
        } else if (starterWorkTime == 0 && mainWorkTime > 0) {
            sb.append(mainArrival).append(" mins");
        } else {
            // 双阶段显示：Starters 25m ➜ Mains ~45m
            sb.append("Starters ").append(starterArrival).append("m ➜ Mains ~").append(mainArrival).append("m");
        }

        prepTimeLabel.setText(sb.toString());
    }

    /**
     * 辅助方法：计算一组菜品（如所有主菜）的总制作时间
     * 包含：批量效应（同一种菜多份） + 拥堵效应（不同菜品）
     */
    private int calculateGroupPrepTime(Map<Dish, Integer> items) {
        if (items.isEmpty()) return 0;

        // A. 计算每种单品的总耗时（应用批量折扣）
        List<Integer> itemTimes = items.entrySet().stream()
                .map(e -> {
                    String s = e.getKey().getPrepTime().replace("m", "");
                    if (s.equals("N/A")) return 0;

                    int base = Integer.parseInt(s);
                    int qty = e.getValue();

                    // 批量算法：
                    // 1份 = 100% 时间
                    // 3份 = 100% + 40% + 40% = 180% 时间 (而不是 300%)
                    // 系数 0.4 代表熟练厨房的边际成本
                    return qty <= 1 ? base : (int)(base + base * 0.4 * (qty - 1));
                })
                .sorted()
                .collect(Collectors.toList());

        if (itemTimes.isEmpty()) return 0;

        // B. 计算组内并行耗时
        // 取最长的那道菜作为主线
        int maxTime = itemTimes.get(itemTimes.size() - 1);

        // 其他较快的菜品导致的主线拥堵延迟（拥堵系数 15%）
        int congestionDelay = itemTimes.stream()
                .limit(itemTimes.size() - 1)
                .mapToInt(t -> (int)(t * 0.15))
                .sum();

        return maxTime + congestionDelay;
    }

    /**
     * 辅助方法：定义哪些属于“第一阶段上桌”
     */
    private boolean isStarter(Dish d) {
        String cat = d.getCategory();
        return "Appetizer".equalsIgnoreCase(cat) ||
                "Beverage".equalsIgnoreCase(cat) ||
                "Soup".equalsIgnoreCase(cat) ||
                "Salad".equalsIgnoreCase(cat);
    }
    private HBox createSummaryRow(String label, double val, boolean isTotal) {
        HBox row = new HBox();
        Label l = new Label(label);
        l.setFont(Font.font(FONT_FAMILY, isTotal ? FontWeight.BOLD : FontWeight.NORMAL, isTotal ? 18 : 14));
        l.setTextFill(Color.web(isTotal ? COLOR_DARK : COLOR_TEXT_GRAY));

        Region r = new Region(); HBox.setHgrow(r, Priority.ALWAYS);

        Label v = new Label("€" + String.format("%.2f", val));
        v.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, isTotal ? 22 : 14));
        v.setTextFill(Color.web(isTotal ? COLOR_PRIMARY : COLOR_DARK));

        row.getChildren().addAll(l, r, v);
        return row;
    }

    private HBox createNavBar() {
        HBox nav = new HBox(20);
        nav.setPadding(new Insets(15, 50, 15, 50));
        nav.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 10, 0, 0, 5);");
        nav.setAlignment(Pos.CENTER_LEFT);

        Label brand = new Label("THE GOURMET.");
        brand.setFont(Font.font("Times New Roman", FontWeight.BOLD, 24));
        brand.setTextFill(Color.web(COLOR_DARK));
        brand.setOnMouseClicked(e -> showHomeView());
        brand.setStyle("-fx-cursor: hand;");

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);

        Button menuBtn = new Button("Menu");
        menuBtn.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand; -fx-text-fill: " + COLOR_DARK + ";");
        menuBtn.setOnAction(e -> showMenuView());

        cartButton = new Button();
        cartButton.setStyle("-fx-background-color: " + COLOR_BG_LIGHT + "; -fx-text-fill: " + COLOR_DARK + "; -fx-background-radius: 20; -fx-padding: 8 20; -fx-font-weight: bold; -fx-cursor: hand;");
        cartButton.setOnAction(e -> showCartView());
        updateGlobalCartBtn();

        nav.getChildren().addAll(brand, spacer, menuBtn, cartButton);
        return nav;
    }

    private VBox createUpsellSection() {
        VBox box = new VBox(15);
        box.setPadding(new Insets(20, 0, 0, 0));
        Label title = new Label("✨ Recommended Pairings");
        title.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 18));
        title.setTextFill(Color.web(COLOR_DARK));

        HBox cards = new HBox(15);
        List<Dish> recs = menuData.stream()
                .filter(d -> !shoppingCart.containsKey(d) && (d.getCategory().equals("Beverage") || d.getCategory().equals("Dessert")))
                .collect(Collectors.toList());
        Collections.shuffle(recs);

        for(int i=0; i<Math.min(3, recs.size()); i++) {
            Dish d = recs.get(i);
            VBox card = new VBox(10);
            card.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-padding: 10; -fx-background-radius: " + CARD_RADIUS + "; -fx-border-color: #4A566A;");
            card.setPrefWidth(150); card.setAlignment(Pos.CENTER);

            ImageView img = new ImageView(new Image(d.getImageUrl(), 130, 80, true, true, true));
            Rectangle clip = new Rectangle(130, 80); clip.setArcWidth(8); clip.setArcHeight(8); img.setClip(clip);

            Label n = new Label(d.getName()); n.setFont(Font.font(13)); n.setTextFill(Color.web(COLOR_DARK));
            Button add = new Button("+ Add €" + String.format("%.2f", d.getPrice()));
            add.setStyle("-fx-background-color: " + COLOR_ACCENT + "; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold; -fx-cursor: hand; -fx-background-radius: 15; -fx-padding: 5 10;");
            add.setOnAction(e -> {
                shoppingCart.put(d, shoppingCart.getOrDefault(d, 0) + 1);
                refreshCartUI();
                animateCartButton(); // <-- 触发动画
            });

            card.getChildren().addAll(img, n, add);
            cards.getChildren().add(card);
        }
        box.getChildren().addAll(title, cards);
        return box;
    }

    private VBox createEmptyCartState() {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(50));
        box.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-background-radius: " + CARD_RADIUS + ";");
        Label icon = new Label("🛒"); icon.setFont(Font.font(50));
        Label txt = new Label("Your cart is currently empty."); txt.setFont(Font.font(18)); txt.setTextFill(Color.web(COLOR_DARK));
        Button btn = createStyledButton("Discover the Menu", COLOR_PRIMARY, true);
        btn.setOnAction(e -> showMenuView());
        box.getChildren().addAll(icon, txt, btn);
        return box;
    }

    // 商品详情页 - 增加返回按钮并改为横向布局 (左图右文)
    private void showDetailView(Dish d) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + COLOR_BG_LIGHT + ";");

        // 顶部容器：导航栏 + 返回按钮
        HBox topBar = new HBox(createNavBar());
        topBar.setAlignment(Pos.TOP_LEFT);

        Button backButton = new Button("← Back to Menu");
        backButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + COLOR_TEXT_GRAY + "; -fx-font-weight: bold; -fx-font-size: 14px; -fx-cursor: hand;");
        backButton.setPadding(new Insets(10, 50, 10, 50));
        backButton.setOnAction(e -> showMenuView()); // 返回菜单页

        VBox topContainer = new VBox(topBar, backButton);
        topContainer.setStyle("-fx-background-color: " + COLOR_CARD_BG + ";");
        root.setTop(topContainer);


        // ==================== 中央横向内容布局 ====================
        HBox contentLayout = new HBox(50); // 左右组件之间的间距
        contentLayout.setAlignment(Pos.CENTER); // HBox 内部垂直居中
        contentLayout.setPadding(new Insets(60, 100, 60, 100)); // 整个横向布局的内边距

        // --- 1. 左侧：图片区域 ---
        StackPane imgC = new StackPane();
        imgC.setPrefWidth(500); // 图片容器固定宽度
        imgC.setMinHeight(400);

        ImageView img = new ImageView(new Image(d.getImageUrl(), 500, 400, true, true, true));
        img.setFitWidth(500);
        img.setFitHeight(400);
        Rectangle clip = new Rectangle(500, 400); // 调整裁剪矩形大小
        clip.setArcWidth(CARD_RADIUS); clip.setArcHeight(CARD_RADIUS);
        img.setClip(clip);
        imgC.getChildren().add(img);

        // --- 2. 右侧：信息和操作区域 ---
        VBox info = new VBox(25); // 垂直间距
        info.setMaxWidth(400);
        info.setAlignment(Pos.CENTER_LEFT); // 文字和按钮在右侧容器中左对齐

        Label name = new Label(d.getName());
        name.setFont(Font.font(FONT_FAMILY, FontWeight.BOLD, 48));
        name.setTextFill(Color.web(COLOR_DARK));

        Label desc = new Label(d.getDescription() + "\n\n(Preparation Time: " + d.getPrepTime() + ")");
        desc.setFont(Font.font(20));
        desc.setTextFill(Color.web(COLOR_TEXT_GRAY));
        desc.setWrapText(true);

        Button add = createStyledButton("ADD TO ORDER - €" + String.format("%.2f", d.getPrice()), COLOR_PRIMARY, true);
        add.setPrefWidth(350);
        add.setOnAction(e -> {
            shoppingCart.put(d, shoppingCart.getOrDefault(d, 0) + 1);
            updateGlobalCartBtn();
            animateCartButton(); // <-- 触发动画
        });

        info.getChildren().addAll(name, desc, add);

        // 将左右两部分加入到横向布局中
        contentLayout.getChildren().addAll(imgC, info);


        // 使用 ScrollPane 确保内容不会溢出
        ScrollPane scrollContent = new ScrollPane(contentLayout);
        scrollContent.setFitToWidth(true);
        scrollContent.setFitToHeight(true);
        scrollContent.setStyle("-fx-background: transparent; -fx-background-color: transparent;");

        root.setCenter(scrollContent);
        primaryStage.setScene(new Scene(root, 1200, 800));
    }

    private void showMysteryBoxDialog() {
        Stage s = new Stage(); s.initModality(Modality.APPLICATION_MODAL);
        VBox r = new VBox(20); r.setAlignment(Pos.CENTER); r.setPadding(new Insets(40));
        r.setStyle("-fx-background-color: " + COLOR_CARD_BG + "; -fx-border-color: " + COLOR_PRIMARY + "; -fx-border-width: 3; -fx-background-radius: " + CARD_RADIUS + ";");

        Label t = new Label("CHEF'S SURPRISE BOX 🎁"); t.setFont(Font.font(24)); t.setTextFill(Color.web(COLOR_DARK));
        Label d = new Label("Four exquisite courses selected daily by our Head Chef. Prep: 25m");
        d.setWrapText(true); d.setTextAlignment(javafx.scene.text.TextAlignment.CENTER); d.setTextFill(Color.web(COLOR_TEXT_GRAY));
        Button b = createStyledButton("ADD (€25.00)", COLOR_PRIMARY, true);
        b.setOnAction(e -> {
            Dish mysteryDish = new Dish("mys", "Chef's Surprise Box", "Set", 25.00, "Daily selection of 4 premium courses.", "25m", "Varies", Arrays.asList("Surprise"), "https://images.unsplash.com/photo-1549488344-cbb6c34cf08b?w=600");
            shoppingCart.put(mysteryDish, shoppingCart.getOrDefault(mysteryDish, 0) + 1);
            updateGlobalCartBtn();
            animateCartButton(); // <-- 触发动画
            s.close();
            showCartView();
        });
        r.getChildren().addAll(t, d, b); s.setScene(new Scene(r, 450, 300)); s.show();
    }

    private void processCheckout() {
        if(shoppingCart.isEmpty()) return;

        double subtotal = shoppingCart.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();

        // 总价仅为 Subtotal + Tax (5%)
        double t = subtotal * 1.05;
        Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle("Order Confirmed"); a.setHeaderText("Payment Successful!"); a.setContentText("Total Charged: €" + String.format("%.2f", t) + ". Your order is being prepared!"); a.showAndWait();

        shoppingCart.clear();
        refreshCartUI();
    }

    private Button createStyledButton(String txt, String color, boolean fill) {
        Button b = new Button(txt);
        String base = "-fx-font-weight: bold; -fx-font-size: 16px; -fx-background-radius: 30; -fx-cursor: hand; -fx-padding: 12 30; ";
        if(fill) b.setStyle(base + "-fx-background-color: " + color + "; -fx-text-fill: white;");
        else b.setStyle(base + "-fx-background-color: transparent; -fx-border-color: " + color + "; -fx-text-fill: " + color + "; -fx-border-width: 2;");
        return b;
    }

    public static void main(String[] args) { launch(); }
}
