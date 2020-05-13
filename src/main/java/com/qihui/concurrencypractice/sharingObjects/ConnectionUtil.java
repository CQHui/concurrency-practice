package com.qihui.concurrencypractice.sharingObjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author chenqihui
 * @date 2020/5/13
 */
public class ConnectionUtil {

    private static ThreadLocal<Connection> connectionHolder = ThreadLocal.withInitial(() -> {
        try {
            return DriverManager.getConnection("url");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    });

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
