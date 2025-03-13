package com.example.demo.db;

import com.example.demo.model.Expense;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {
    public static void createNewExpense(Expense expense) {
        String query = """
                INSERT INTO expense(period, housing, food, outing, transport, travel, taxes, other)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, expense.getPeriod());
            statement.setFloat(2, expense.getHousing());
            statement.setFloat(3, expense.getFood());
            statement.setFloat(4, expense.getOuting());
            statement.setFloat(5, expense.getTransport());
            statement.setFloat(6, expense.getTravel());
            statement.setFloat(7, expense.getTaxes());
            statement.setFloat(8, expense.getOther());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Expense> fetchAllExpenses() {
        List<Expense> expenses = new ArrayList<>();
        String query = "SELECT * FROM expense";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                float housing = rs.getFloat("housing");
                float food = rs.getFloat("food");
                float outing = rs.getFloat("outing");
                float transport = rs.getFloat("transport");
                float travel = rs.getFloat("travel");
                float taxes = rs.getFloat("taxes");
                float other = rs.getFloat("other");
                float total = housing + food + outing + transport + travel + taxes + other;
                expenses.add(new Expense(
                        rs.getString("period"),
                        total,
                        housing,
                        food,
                        outing,
                        transport,
                        travel,
                        taxes,
                        other
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return expenses;
        }
    }
}
