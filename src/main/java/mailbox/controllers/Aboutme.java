package mailbox.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Aboutme {
    @FXML
    public Button backBtn;
    @FXML
    public void handleBackBtnOnAction(ActionEvent event) throws IOException {
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailbox_controller.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

}

