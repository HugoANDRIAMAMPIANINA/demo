package com.example.demo;

import com.example.demo.model.Expense;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpenseDialog extends Dialog<Expense> {
    private Expense expense;

    @FXML
    private TextField periodTextField;
    @FXML
    private TextField housingTextField;
    @FXML
    private TextField foodTextField;
    @FXML
    private TextField outingTextField;
    @FXML
    private TextField transportTextField;
    @FXML
    private TextField travelTextField;
    @FXML
    private TextField taxesTextField;
    @FXML
    private TextField otherTextField;

    public ExpenseDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(FinanceApplication.class.getResource("add-expense-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = loader.load();

        setDialogPane(dialogPane);

        // Disable dialog add button by default
        ButtonType addButtonType = dialogPane.getButtonTypes().get(0);
        Button addButton = (Button) dialogPane.lookupButton(addButtonType);
        addButton.setDisable(true);

        List<TextField> textFields = List.of(
                periodTextField, housingTextField, foodTextField, outingTextField,
                transportTextField, travelTextField, taxesTextField, otherTextField
        );

        // Disable or enable the dialog add button based on textFields content
        addButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> textFields.stream().anyMatch(tf -> tf.getText().trim().isEmpty()),
                        textFields.stream().map(TextField::textProperty).toArray(javafx.beans.Observable[]::new)
                )
        );

        // Check if the textFields have valid user input
        addButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!checkUserInputsAreValid()) {
                event.consume();
            }
        });

        setResultConverter(buttonType -> {
            if (!Objects.equals(ButtonBar.ButtonData.OK_DONE, buttonType.getButtonData())) {
                return null;
            }

            String period = periodTextField.getText();
            float housing = Float.parseFloat(housingTextField.getText());
            float food = Float.parseFloat(foodTextField.getText());
            float outing = Float.parseFloat(outingTextField.getText());
            float transport = Float.parseFloat(transportTextField.getText());
            float travel = Float.parseFloat(travelTextField.getText());
            float taxes = Float.parseFloat(taxesTextField.getText());
            float other = Float.parseFloat(otherTextField.getText());
            float total = housing + food + travel + outing + transport + taxes + other;
            return new Expense(
                    period,
                    total,
                    housing,
                    food,
                    outing,
                    transport,
                    travel,
                    taxes,
                    other
            );
        });
    }

    private boolean checkUserInputsAreValid() {
        String period = periodTextField.getText();
        if (!period.matches("^(\\d{4})-(\\d{2})$")) {
            showAlertDialog("Le champ Période est incorrect", "Il doit être au format suivant : aaaa-MM");
            return false;
        }

        try {
            Float.parseFloat(housingTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Logement est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(foodTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Nourriture est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(outingTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Sorties est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(transportTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Transport est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(travelTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Voyage est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(taxesTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs de Impôts est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(otherTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs de Autres est incorrect", "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }

        HashMap<TextField, String> expenses = new HashMap();
        expenses.put(housingTextField, "Logement");
        expenses.put(foodTextField, "Nourriture");
        expenses.put(outingTextField, "Sorties");
        expenses.put(transportTextField, "Transports");
        expenses.put(travelTextField, "Voyage");
        expenses.put(taxesTextField, "Impots");
        expenses.put(otherTextField, "Autres");

        for (TextField textField : expenses.keySet()) {
            if (!checkExpenseIsValid(textField.getText(), expenses.get(textField))) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkExpenseIsValid(String expenseValue, String expenseType) {
        try {
            Float.parseFloat(expenseValue);
            return true;
        } catch (NumberFormatException e) {
            showAlertDialog(String.format("Le champs de %s est incorrect", expenseType), "Les champs de dépenses doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
    }

    private static void showAlertDialog(String headerText, String contentText) {
        Alert alertDialog = new Alert(Alert.AlertType.WARNING);
        alertDialog.setTitle("Erreur de champs");
        alertDialog.setHeaderText(headerText);
        alertDialog.setContentText(contentText);
        alertDialog.showAndWait();
    }
}