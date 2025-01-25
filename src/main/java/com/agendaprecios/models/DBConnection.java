package com.agendaprecios.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection{
    private static final String URL = "jdbc://mariadb//localhost:3306/AgendaPrecios";
    private static final String USER = "dbuser";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException{
        try{
            // Registra el driver en mariadb
            Class.forName("org.mariadb.jdbc.Driver");
            // retorna la conexión a la base de datos
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(ClassNotFoundException | SQLException e){
            throw new SQLException("Error en la conexión con la base de datos", e);
        }
    }

    
}