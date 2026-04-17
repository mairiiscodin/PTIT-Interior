/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Laptop
 */
public class DBConnect {
    public static Connection getConnection() {
        Connection connection = null;

        try {
            String url = "jdbc:mysql://localhost:3306/web_ban_hang";
            String user = "root";
            String password = "admin";

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connect success");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return connection;
    }
}
