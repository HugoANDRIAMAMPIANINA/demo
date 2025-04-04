package com.example.demo.db;

import com.example.demo.model.Income;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncomeDAO {
    public static void createNewIncome(Income income) {
        String query = """
                INSERT INTO income(period, salary, aid, selfEmployed, passive, other)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, income.getPeriod());
            statement.setFloat(2, income.getSalary());
            statement.setFloat(3, income.getAid());
            statement.setFloat(4, income.getSelfEmployed());
            statement.setFloat(5, income.getPassive());
            statement.setFloat(6, income.getOther());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Income> fetchAllIncomes() {
        List<Income> incomes = new ArrayList<>();
        String query = "SELECT * FROM income";

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                float salary = rs.getFloat("salary");
                float aid = rs.getFloat("aid");
                float selfEmployed = rs.getFloat("selfEmployed");
                float passive = rs.getFloat("passive");
                float other = rs.getFloat("other");
                float total = salary + aid + selfEmployed + passive + other;
                incomes.add(new Income(
                        rs.getString("period"),
                        total,
                        salary,
                        aid,
                        selfEmployed,
                        passive,
                        other
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return incomes;
        }
    }
}
