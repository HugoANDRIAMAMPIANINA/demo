package com.example.demo;

import com.example.demo.db.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class FinanceApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FinanceApplication.class.getResource("dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Finance Application");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (!Database.isOK()) {
            throw new RuntimeException();
        }
        launch();
    }
}