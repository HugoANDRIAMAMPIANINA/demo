package com.example.demo;

import com.example.demo.db.ExpenseDAO;
import com.example.demo.model.Expense;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

import java.text.DecimalFormat;
import java.util.List;

public class PieChartController {
    @FXML
    public PieChart pieChart;

    public void initialize() {
        List<Expense> expenses = ExpenseDAO.fetchAllExpenses();
        if (expenses.isEmpty()) {
            return;
        }

        Expense globalExpense = new Expense("0000-00",0,0,0,0,0,0,0);
        for (Expense expense : expenses) {
            globalExpense.setFood(globalExpense.getFood() + expense.getFood());
            globalExpense.setHousing(globalExpense.getHousing() + expense.getHousing());
            globalExpense.setOther(globalExpense.getOther() + expense.getOther());
            globalExpense.setOuting(globalExpense.getOuting() + expense.getOuting());
            globalExpense.setTaxes(globalExpense.getTaxes() + expense.getTaxes());
            globalExpense.setTransport(globalExpense.getTransport() + expense.getTransport());
            globalExpense.setTravel(globalExpense.getTravel() + expense.getTravel());
            globalExpense.setTotal(globalExpense.getTotal() + expense.getTotal());
        }

        PieChart.Data slice1 = new PieChart.Data("Nourriture", globalExpense.getFood());
        PieChart.Data slice2 = new PieChart.Data("Logement", globalExpense.getHousing());
        PieChart.Data slice3 = new PieChart.Data("Autres", globalExpense.getOther());
        PieChart.Data slice4 = new PieChart.Data("Sorties", globalExpense.getOuting());
        PieChart.Data slice5 = new PieChart.Data("Impôts", globalExpense.getTaxes());
        PieChart.Data slice6 = new PieChart.Data("Transports", globalExpense.getTransport());
        PieChart.Data slice7 = new PieChart.Data("Voyages", globalExpense.getTravel());

        DecimalFormat df = new DecimalFormat("#.##");
        pieChart.getData().addAll(slice1, slice2, slice3, slice4, slice5, slice6, slice7);
        pieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " - ", df.format(data.pieValueProperty().floatValue() / globalExpense.getTotal() * 100), "%"
                        )
                )
        );
        pieChart.setLegendVisible(false);
        pieChart.setTitle("Dépenses totales");
    }
}
