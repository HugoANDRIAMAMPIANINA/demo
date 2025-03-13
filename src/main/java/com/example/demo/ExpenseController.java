package com.example.demo;

import com.example.demo.db.ExpenseDAO;
import com.example.demo.model.Expense;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ExpenseController {
    @FXML
    private TableView<Expense> expenseTableView;

    public void initialize() {
        List<Expense> list = ExpenseDAO.fetchAllExpenses();
        if (!list.isEmpty()) {
            System.out.println(list.get(0).getHousing());
            expenseTableView.getItems().addAll(list);
        }
    }

    @FXML
    private void addExpense(ActionEvent event) throws IOException {
        Dialog dialog = new ExpenseDialog();
        dialog.setTitle("Ajouter des dépenses");
        Optional<Expense> newExpense = dialog.showAndWait();

        if (Stream.ofNullable(newExpense).anyMatch(Optional::isPresent)) {
            ExpenseDAO.createNewExpense(newExpense.get());
            expenseTableView.getItems().add(newExpense.get());
        }
        event.consume();
    }
}