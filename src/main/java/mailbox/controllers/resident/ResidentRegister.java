package mailbox.controllers.resident;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.implementation.UsersFileData;
import mailbox.services.interfaceClass.FileData;

import java.io.IOException;

public class ResidentRegister {
    @FXML
    private JFXTextField nameField, surnameField, usernameField;
    @FXML
    private JFXPasswordField passwordField, confirmField;

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private FileData usersData;
    private ResidentUsers currentResident;

    public void setAllData(Users adminUsers, AdminLists adminLists, AllUsersLists allLists, HostUsers hostUsers, HostLists hostLists, HostUsers currentHost, RoomLists roomLists , ResidentsList residentsList){
        this.adminUsers = adminUsers;
        this.adminLists = adminLists;
        this.allLists = allLists;
        this.hostUsers = hostUsers;
        this.hostLists = hostLists;
        this.currentHost = currentHost;
        this.residentsList = residentsList;
        this.roomLists = roomLists;
    }

    @FXML
    public void handleBackBtn(ActionEvent event) throws IOException {
        Button resident = (Button) event.getSource();
        Stage stage = (Stage) resident.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentLogin residentLogin = loader.getController();
        residentLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleNewRegisterBtn(ActionEvent event) throws IOException{
        if(nameField.getText().equals("") || surnameField.getText().equals("") || usernameField.getText().equals("") || passwordField.getText().equals("") || confirmField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Text cannot be blank. ");
            alert.showAndWait();
        }else {
            if (passwordField.getText().contains(",") || confirmField.getText().contains(",")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setTitle("Failed...");
                alert.setHeaderText("");
                alert.setContentText("Can not use comma in information text.");
                alert.showAndWait();
            } else {
                if (residentsList.checkRegister(nameField.getText(), surnameField.getText(), usernameField.getText()) && passwordField.getText().equals(confirmField.getText())) {
                    if (residentsList.checkRegisterAgain(usernameField.getText())) {
                        currentResident = residentsList.getCurrentByUserName(usernameField.getText());
                        currentResident.setPassword(passwordField.getText());
                        usersData = new UsersFileData("data", "users.csv");
                        usersData.setList(allLists);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setGraphic(new ImageView("images/confirm.png"));
                        alert.setHeaderText("");
                        alert.setTitle("Success");
                        alert.setContentText("Registration successful, you're ready to access now.");
                        alert.showAndWait();
                        Button resident = (Button) event.getSource();
                        Stage stage = (Stage) resident.getScene().getWindow();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_login.fxml"));
                        stage.setScene(new Scene(loader.load(), 800, 600));
                        ResidentLogin residentLogin = loader.getController();
                        residentLogin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setGraphic(new ImageView("images/cancel.png"));
                        alert.setHeaderText("");
                        alert.setTitle("Sorry...");
                        alert.setContentText("You cannot register again.");
                        alert.showAndWait();
                    }
                } else if (!residentsList.checkRegister(nameField.getText(), surnameField.getText(), usernameField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setGraphic(new ImageView("images/cancel.png"));
                    alert.setHeaderText("");
                    alert.setTitle("Sorry...");
                    alert.setContentText("Resident not found.");
                    alert.showAndWait();
                } else if (residentsList.getCurrentByUserName(usernameField.getText()) == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setGraphic(new ImageView("images/cancel.png"));
                    alert.setHeaderText("");
                    alert.setTitle("Sorry...");
                    alert.setContentText("You don't have permission to register.");
                    usernameField.clear();
                    alert.showAndWait();
                } else if (!passwordField.getText().equals(confirmField.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setGraphic(new ImageView("images/cancel.png"));
                    alert.setHeaderText("");
                    alert.setTitle("Sorry...");
                    alert.setContentText("Password doesn't match.");
                    passwordField.clear();
                    confirmField.clear();
                    alert.showAndWait();
                }
            }
        }
    }
}
