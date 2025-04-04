package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderController {
    @FXML
    private MenuItem expensesMenuItem;

    @FXML
    private MenuItem dashboardMenuItem;

    public void initialize() {
        expensesMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("go to expenses page");
                switchScene(e, "hello-view.fxml", expensesMenuItem);
            }
        });

        dashboardMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                System.out.println("go to dashboard page");
                switchScene(e, "dashboard.fxml", dashboardMenuItem);
            }
        });
    }

    public void switchScene(ActionEvent event, String fxmlFile, MenuItem menuItem) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) menuItem.getParentPopup().getOwnerNode()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
