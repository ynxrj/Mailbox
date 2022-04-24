package mailbox.controllers.admin;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.UsersFileData;

import java.io.IOException;
import java.util.Optional;

public class AdminAdd {
    @FXML
    private Label nameLabel;
    @FXML
    private JFXTextField name, surname, age, phone, username;
    @FXML
    private JFXPasswordField password, confirmPassword;

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData usersData;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;

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

    public void initialize(){
        Platform.runLater(() -> {
            nameLabel.setText(adminUsers.getName());
        });
    }

    @FXML
    public void handleAdminAddBtn(ActionEvent event) throws IOException {
        Button adminAddBtn = (Button) event.getSource();
        Stage stage = (Stage) adminAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_add.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminAdd admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminBlockBtn(ActionEvent event) throws IOException {
        Button adminBlockBtn = (Button) event.getSource();
        Stage stage = (Stage) adminBlockBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_block.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminBlock admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminHostlistsBtn(ActionEvent event) throws IOException {
        Button adminHostListsBtn = (Button) event.getSource();
        Stage stage = (Stage) adminHostListsBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_hosts_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminHostsList admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminChangePasswordBtn(ActionEvent event) throws IOException {
        Button adminChangPassBtn = (Button) event.getSource();
        Stage stage = (Stage) adminChangPassBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminChangingPassword admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAdminSoutBtn(ActionEvent event) throws IOException {
        Button adminSoutBtn = (Button) event.getSource();
        Stage stage = (Stage) adminSoutBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        AdminLogin admin = loader.getController();
        admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleAddBtn(ActionEvent event) throws IOException {
        if (name.getText().contains(",") || surname.getText().contains(",") ||
                username.getText().contains(",") || password.getText().contains(",") ||
                confirmPassword.getText().contains(",") || age.getText().contains(",") ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setTitle("Failed...");
            alert.setHeaderText("");
            alert.setContentText("Can not use comma in information text.");
            alert.showAndWait();
        }else {
            if (name.getText().equals("") || surname.getText().equals("") ||
                    username.getText().equals("") || password.getText().equals("") ||
                    confirmPassword.getText().equals("") || age.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("Text cannot be blank.");
                alert.showAndWait();
            } else {
                try {
                    HostUsers host = new HostUsers(name.getText(), surname.getText(), phone.getText(),
                            Integer.parseInt(age.getText()), username.getText(), password.getText(), "Authorize", 0, "-");
                    if (hostLists.checkAdd(host, adminUsers) && password.getText().equals(confirmPassword.getText())) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setGraphic(new ImageView("images/warning.png"));
                        alert.setTitle("Confirmation.");
                        alert.setHeaderText("");
                        alert.setContentText("Are you sure you want to add " + username.getText() + " to be host?");
                        Optional<ButtonType> confirmation = alert.showAndWait();
                        if (confirmation.get() == ButtonType.OK) {
                            allLists.addUsers(host);
                            hostLists.addHost(host);
                            usersData = new UsersFileData("data", "users.csv");
                            usersData.setList(allLists);

                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setGraphic(new ImageView("images/confirm.png"));
                            success.setHeaderText("");
                            success.setTitle("Success");
                            success.setContentText("Host " + username.getText() + " has been successfully added.");
                            success.showAndWait();

                            Button add = (Button) event.getSource();
                            Stage stage = (Stage) add.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/admin_add.fxml"));
                            stage.setScene(new Scene(loader.load(), 800, 600));
                            AdminAdd admin = loader.getController();
                            admin.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                            stage.show();
                        }
                    } else if (!password.getText().equals(confirmPassword.getText())) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setGraphic(new ImageView("images/cancel.png"));
                        alert.setHeaderText("");
                        alert.setTitle("Please Try Again...");
                        alert.setContentText("Password doesn't match.");
                        password.clear();
                        confirmPassword.clear();
                        alert.showAndWait();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setGraphic(new ImageView("images/cancel.png"));
                        alert.setHeaderText("");
                        alert.setTitle("Failed");
                        alert.setContentText("Username already exists.");
                        username.clear();
                        alert.showAndWait();
                    }
                } catch (NumberFormatException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setGraphic(new ImageView("images/cancel.png"));
                    alert.setHeaderText("");
                    alert.setTitle("Failed");
                    alert.setContentText("Wrong Input.");
                    age.clear();
                    alert.showAndWait();
                }
            }
        }
    }
}
