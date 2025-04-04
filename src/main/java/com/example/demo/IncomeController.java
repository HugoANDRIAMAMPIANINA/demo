package com.example.demo;

import com.example.demo.db.IncomeDAO;
import com.example.demo.model.Income;
import com.example.demo.model.Income;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class IncomeController {
    @FXML
    private TableView<Income> incomeTableView;

    public void initialize() {
        List<Income> list = IncomeDAO.fetchAllIncomes();
        if (!list.isEmpty()) {
            incomeTableView.getItems().addAll(list);
        }
    }

    @FXML
    private void addIncome(ActionEvent event) throws IOException {
        Dialog dialog = new IncomeDialog();
        dialog.setTitle("Ajouter des revenus");
        Optional<Income> newIncome = dialog.showAndWait();

        if (Stream.ofNullable(newIncome).anyMatch(Optional::isPresent)) {
            IncomeDAO.createNewIncome(newIncome.get());
            incomeTableView.getItems().add(newIncome.get());
        }
        event.consume();
    }
}