package org.example.demo;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;
import java.util.logging.ErrorManager;

import static org.example.demo.Database.isOK;

public class ExpenseApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        if(isOK()) {
            System.out.println(isOK());
            FXMLLoader fxmlLoader = new FXMLLoader(ExpenseApplication.class.getResource("expense-view.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root, 720, 240);
            stage.setTitle("Tableau de dépense");
            stage.getIcons().add(new Image(Objects.requireNonNull(ExpenseApplication.class.getResourceAsStream("assets/icon.png"))));
            stage.setScene(scene);
            stage.show();
        } else {
            ErrorManager Alerts = new ErrorManager();
            Alerts.error(
                    "Database error",
                    null,
                    0
            );
            Platform.exit();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}