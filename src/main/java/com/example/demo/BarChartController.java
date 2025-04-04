package com.example.demo;

import com.example.demo.db.ExpenseDAO;
import com.example.demo.db.IncomeDAO;
import com.example.demo.model.Expense;
import com.example.demo.model.Income;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.text.DecimalFormat;
import java.util.List;

public class BarChartController {
    @FXML
    public BarChart barChart;

    public void initialize() {
        List<Expense> expenses = ExpenseDAO.fetchAllExpenses();
        List<Income> incomes = IncomeDAO.fetchAllIncomes();

        XYChart.Series incomeSeries = new XYChart.Series();
        incomeSeries.setName("Revenus");

        for (Income income : incomes) {
            incomeSeries.getData().add(new XYChart.Data(income.getPeriod(), income.getTotal()));
        }

        XYChart.Series expenseSeries = new XYChart.Series();
        expenseSeries.setName("Dépenses");

        for (Expense expense : expenses) {
            expenseSeries.getData().add(new XYChart.Data(expense.getPeriod(), expense.getTotal()));
        }

        barChart.getData().addAll(expenseSeries, incomeSeries);
        barChart.setTitle("Dépenses et Revenus");
    }
}
