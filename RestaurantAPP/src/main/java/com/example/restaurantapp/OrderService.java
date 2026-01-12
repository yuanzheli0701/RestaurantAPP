package com.example.restaurantapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

/**
 * 订单服务类：负责在客户端和厨师端之间同步数据
 */
public class OrderService {

    // 使用 ObservableList，这是 JavaFX 实现联动的核心
    // 当这个列表发生 add 或 remove 操作时，所有监听它的 UI 都会自动触发更新
    private static final ObservableList<OrderData> orders = FXCollections.observableArrayList();

    public static ObservableList<OrderData> getOrders() {
        return orders;
    }

    /**
     * 由客户端调用：提交新订单
     * @param tableNum 桌号
     * @param items 菜品名称和数量的列表
     */
    public static void addOrder(String tableNum, List<String> items) {
        // 随机生成一个订单号
        String orderId = String.valueOf((int)(Math.random() * 900) + 100);
        OrderData newOrder = new OrderData(orderId, tableNum, items);

        // 将订单加入列表，此时厨师端的监听器会被触发
        orders.add(newOrder);
    }

    /**
     * 内部订单模型类
     */
    public static class OrderData {
        private final String id;
        private final String tableNum;
        private final List<String> items;

        public OrderData(String id, String tableNum, List<String> items) {
            this.id = id;
            this.tableNum = tableNum;
            this.items = items;
        }

        public String getId() { return id; }
        public String getTableNum() { return tableNum; }
        public List<String> getItems() { return items; }
    }
}