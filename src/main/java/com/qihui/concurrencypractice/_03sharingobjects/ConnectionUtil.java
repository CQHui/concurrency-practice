package com.qihui.concurrencypractice._03sharingobjects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

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
        connectionHolder = null;
        connectionHolder.remove();
        return connectionHolder.get();
    }

}
