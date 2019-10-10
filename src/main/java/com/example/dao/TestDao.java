package com.example.dao;


import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDao{

    protected String url;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    protected String username;

    protected String password;
    Connection connection = null;


    public TestDao()
    {
        super();
    }
    public TestDao(String url, String username, String password)
    {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public void connect()
    {
        //try
       // {
            System.out.println("urllllll is : "+url);
            System.out.println("usernameeeee is : "+username);
            System.out.println("passwordddddd is : "+password);
            //Class.forName("com.mysql.cj.jdbc.Driver");
            //connection = DriverManager.getConnection(url, username,password );

       // }
        /*catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return;
        }*/
    }
}
