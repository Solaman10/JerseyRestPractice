package org.rest;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnect {

    private DbConnect(){}

    public static Connection getConnection()
    {
        System.out.println("Getting connection with DB");
        Connection con = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/restpractice";
            String userName = "root";
            String password = "Solaimani@4";
            con = DriverManager.getConnection(url,userName,password);
            return con;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
}
