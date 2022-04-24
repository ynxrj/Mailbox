package mailbox.controllers;

import com.jfoenix.controls.JFXComboBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class MailboxInfo{

    @FXML
    private JFXComboBox<String> selectBox;
    @FXML
    private AnchorPane guidePane, pane;

    public void initialize(){
        selectBox.getItems().addAll("Admin", "Host", "Resident");
        selectBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    if (selectBox.getValue().equals("Admin")) {
                        pane = FXMLLoader.load(getClass().getResource("/info/admin_guide.fxml"));
                    } else if (selectBox.getValue().equals("Host")) {
                        pane = FXMLLoader.load(getClass().getResource("/info/host_guide.fxml"));
                    } else {
                        pane = FXMLLoader.load(getClass().getResource("/info/resident_guide.fxml"));
                    }
                    guidePane.getChildren().setAll(pane);
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
        });
    }

            public void handleBackBtn(ActionEvent event) throws IOException {
                Button host = (Button) event.getSource();
                Stage stage = (Stage) host.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailbox_controller.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                stage.show();
            }
}


