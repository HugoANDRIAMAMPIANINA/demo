package com.example.demo;

import com.example.demo.model.Income;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class IncomeDialog extends Dialog<Income> {
    private Income income;

    @FXML
    private TextField periodTextField;
    @FXML
    private TextField salaryTextField;
    @FXML
    private TextField aidTextField;
    @FXML
    private TextField selfEmployedTextField;
    @FXML
    private TextField passiveTextField;
    @FXML
    private TextField otherTextField;

    public IncomeDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(FinanceApplication.class.getResource("add-income-dialog.fxml"));
        loader.setController(this);

        DialogPane dialogPane = loader.load();

        setDialogPane(dialogPane);

        // Disable dialog add button by default
        ButtonType addButtonType = dialogPane.getButtonTypes().get(0);
        Button addButton = (Button) dialogPane.lookupButton(addButtonType);
        addButton.setDisable(true);

        List<TextField> textFields = List.of(
                periodTextField, salaryTextField, aidTextField, selfEmployedTextField,
                passiveTextField, otherTextField
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
            float salary = Float.parseFloat(salaryTextField.getText());
            float aid = Float.parseFloat(aidTextField.getText());
            float selfEmployed = Float.parseFloat(selfEmployedTextField.getText());
            float passive = Float.parseFloat(passiveTextField.getText());
            float other = Float.parseFloat(otherTextField.getText());
            float total = salary + aid + selfEmployed + passive + other;
            return new Income(
                    period,
                    total,
                    salary,
                    aid,
                    selfEmployed,
                    passive,
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
            Float.parseFloat(salaryTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Salaire est incorrect", "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(aidTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Aides est incorrect", "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(selfEmployedTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Auto-entreprise est incorrect", "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(passiveTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs Revenus passifs est incorrect", "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }
        try {
            Float.parseFloat(otherTextField.getText());
        } catch (NumberFormatException e) {
            showAlertDialog("Le champs de Autres est incorrect", "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
            return false;
        }

        HashMap<TextField, String> incomes = new HashMap();
        incomes.put(salaryTextField, "Salaire");
        incomes.put(aidTextField, "Aides");
        incomes.put(selfEmployedTextField, "Auto-entreprise");
        incomes.put(passiveTextField, "Revenus passifs");
        incomes.put(otherTextField, "Autres");

        for (TextField textField : incomes.keySet()) {
            if (!checkIncomeIsValid(textField.getText(), incomes.get(textField))) {
                return false;
            }
        }
        return true;
    }

    private static boolean checkIncomeIsValid(String incomeValue, String incomeType) {
        try {
            Float.parseFloat(incomeValue);
            return true;
        } catch (NumberFormatException e) {
            showAlertDialog(String.format("Le champs de %s est incorrect", incomeType), "Les champs de revenus doivent être des nombres, et les virgules doivent être remplacées par des points.");
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