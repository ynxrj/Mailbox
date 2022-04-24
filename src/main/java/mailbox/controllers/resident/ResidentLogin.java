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
import mailbox.controllers.MailboxSelectLogin;
import mailbox.controllers.resident.stockList.ResidentStockList;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;

import java.io.IOException;

public class ResidentLogin {
    @FXML
    private JFXTextField textField;
    @FXML
    private JFXPasswordField passwordField;

    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
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
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mailbox_controller.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        MailboxSelectLogin controller = loader.getController();
        controller.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleLoginBtn(ActionEvent event) throws IOException {
        if(!textField.getText().equals("") && !passwordField.getText().equals("") || passwordField.getText().equals("null")) {
            if (residentsList.checkLoginResident(textField.getText(), passwordField.getText()) && !textField.getText().equals("") && !passwordField.getText().equals("") && !passwordField.getText().equals("null")) {
                currentResident = residentsList.getCurrentByUserName(textField.getText());
                Button hostLoginBtn = (Button) event.getSource();
                Stage stage = (Stage) hostLoginBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_stock_list.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                ResidentStockList resident = loader.getController();
                resident.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList, currentResident);
            } else if (residentsList.checkLoginResident(textField.getText(), passwordField.getText()) && passwordField.getText().equals("null")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("Please register before login.");
                alert.showAndWait();
            } else {
                System.out.println("2");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setHeaderText("");
                alert.setTitle("Please Try Again...");
                alert.setContentText("Incorrect Username or Password.");
                alert.showAndWait();
            }
        }else{
            System.out.println("1");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setHeaderText("");
            alert.setTitle("Please Try Again...");
            alert.setContentText("Incorrect Username or Password.");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleRegisterBtn(ActionEvent event) throws IOException {
        Button backBtn = (Button) event.getSource();
        Stage stage = (Stage) backBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resident/resident_register.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        ResidentRegister residentRegister = loader.getController();
        residentRegister.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }
}
