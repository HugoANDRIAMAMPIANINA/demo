package com.example.demo.db;

import org.sqlite.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {

    /**
     * Location of database
     */
    private static final String location = "database.db";

    /**
     * Currently only table needed
     */
    private static final String requiredTable = "Expense";

    public static boolean isOK() {
        if (!checkDrivers()) return false; //driver errors

        if (!checkConnection()) return false; //can't connect to db

        return createTableIfNotExists(); //tables didn't exist
    }

    private static boolean checkDrivers() {
        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.registerDriver(new JDBC());
            return true;
        } catch (ClassNotFoundException | SQLException classNotFoundException) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
            return false;
        }
    }

    private static boolean checkConnection() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to database");
            return false;
        }
    }

    private static boolean createTableIfNotExists() {
        String createExpenseTable =
                """
                        CREATE TABLE IF NOT EXISTS expense(
                             period TEXT NOT NULL,
                             housing REAL NOT NULL,
                             food REAL NOT NULL,
                             outing REAL NOT NULL,
                             transport REAL NOT NULL,
                             travel REAL NOT NULL,
                             taxes REAL NOT NULL,
                             other REAL NOT NULL
                        );
                   """;
        String createIncomeTable =
                """
                        CREATE TABLE IF NOT EXISTS income(
                             period TEXT NOT NULL,
                             salary REAL NOT NULL,
                             aid REAL NOT NULL,
                             selfEmployed REAL NOT NULL,
                             passive REAL NOT NULL,
                             other REAL NOT NULL
                        );
                   """;

        try (Connection connection = Database.connect()) {
            try (PreparedStatement statement = connection.prepareStatement(createExpenseTable)) {
                statement.executeUpdate();
            }
            try (PreparedStatement statement = connection.prepareStatement(createIncomeTable)) {
                statement.executeUpdate();
            }
            return true;
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not find tables in database");
            return false;
        }
    }

    protected static Connection connect() {
        String dbPrefix = "jdbc:sqlite:";
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPrefix + location);
        } catch (SQLException exception) {
            Logger.getAnonymousLogger().log(Level.SEVERE,
                    LocalDateTime.now() + ": Could not connect to SQLite DB at " +
                            location);
            return null;
        }
        return connection;
    }

}
