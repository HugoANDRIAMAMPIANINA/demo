package com.example.demo;

import com.example.demo.db.ExpenseDAO;
import com.example.demo.model.Expense;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LineChartController {
    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    public void initialize() {
        List<Expense> expenses = ExpenseDAO.fetchAllExpenses();
        if (expenses.isEmpty()) {
            return;
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Dépenses totales");

        for (Expense expense : expenses) {
            series.getData().add(new XYChart.Data<>(expense.getPeriod(), expense.getTotal()));
        }

        lineChart.getData().add(series);
        lineChart.setTitle("Suivi du total des dépenses par mois");
    }
}