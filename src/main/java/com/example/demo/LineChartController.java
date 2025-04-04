package com.example.demo;

import com.example.demo.db.ExpenseDAO;
import com.example.demo.model.Expense;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class LineChartController {
    @FXML
    private LineChart<Number, Number> lineChart;

    @FXML
    private NumberAxis xAxis, yAxis;

    @FXML
    public void initialize() {
        List<Expense> expenses = ExpenseDAO.fetchAllExpenses();
        if (expenses.isEmpty()) {
            return;
        }

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Dépenses totales");

        for (Expense expense : expenses) {
            LocalDate date = LocalDate.parse(expense.getPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            long daysSinceStart = ChronoUnit.DAYS.between(LocalDate.now(), date);
            series.getData().add(new XYChart.Data<>(daysSinceStart, expense.getTotal()));
        }

        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                return LocalDate.now().plusDays(object.longValue()).format(DateTimeFormatter.ofPattern("yyyy-MM"));
            }
        });

        lineChart.getData().add(series);
        lineChart.setTitle("Suivi du total des dépenses par mois");
    }
}