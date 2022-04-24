package mailbox.controllers.host.roomAndResident;

import com.jfoenix.controls.JFXComboBox;
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
import mailbox.controllers.host.HostChangingPassword;
import mailbox.controllers.host.HostLogin;
import mailbox.controllers.host.stock.HostStock;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.Room;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.RoomFileData;

import java.io.IOException;
import java.util.Optional;

public class HostRoom {
    @FXML
    private Label nameLabel;
    @FXML
    private JFXComboBox<String> roomType, building, floor, roomBox;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private FileData roomData, usersData;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentUsers residentUsers;
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

    public void initialize() {
        roomLists = new RoomLists();
        roomData = new RoomFileData("data", "rooms.csv");
        roomLists = (RoomLists) roomData.getList();
        Platform.runLater(() -> {
            //Standard 1-2 //Deluxe 1-2 //Suite 1-3 //Studio 1-3 //Family 1-4
            roomType.getItems().addAll("Standard - Capacity : 2", "Deluxe - Capacity : 2", "Studio - Capacity : 3", "Suite - Capacity : 3", "Family - Capacity : 4");
            building.getItems().addAll("A", "B", "C");
            floor.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12", "13", "14", "15");
            roomBox.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "09", "10", "11", "12", "13", "14", "15");
            nameLabel.setText(currentHost.getName());
        });
    }

    public void setRoom(RoomLists roomLists){
        this.roomLists = roomLists;
    }

    @FXML
    public void handleHostAddBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_add.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostAdd host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostRoomBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoom host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostStockBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStock host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostChangingPasswordBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostChangingPassword host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostSoutBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostLogin host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostRoomListsBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoomList host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleAddBtn(ActionEvent event) throws IOException {
        //Standard 1-2 //Deluxe 1-2 //Suite 1-3 //Studio 1-3 //Family 1-4
        Room room = null;
        if(roomType.getValue() != null && building.getValue() != null &&
                floor.getValue() != null && roomBox.getValue() != null) {
            String[] type = roomType.getValue().split(" - ");
            if (type[0].equals("Standard") || type[0].equals("Deluxe")) {
                room = new Room(type[0], building.getValue(), floor.getValue(), roomBox.getValue(), 2, 2);
            } else if (type[0].equals("Studio") || type[0].equals("Suite")) {
                room = new Room(type[0], building.getValue(), floor.getValue(), roomBox.getValue(), 3, 3);
            } else if (type[0].equals("Family")) {
                room = new Room(type[0], building.getValue(), floor.getValue(), roomBox.getValue(), 4, 4);
            }
        }
        if(roomLists.checkRoomNumber(room) && roomType.getValue() != null && building.getValue() != null &&
                floor.getValue() != null && roomBox.getValue() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Confirmation.");
            alert.setHeaderText("");
            alert.setContentText("Are you sure you want to add room " + room.getRoomNumber() + " ?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.get() == ButtonType.OK) {
                roomLists.addRoom(room);
                roomData = new RoomFileData("data", "rooms.csv");
                roomData.setList(roomLists);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setGraphic(new ImageView("images/confirm.png"));
                success.setHeaderText("");
                success.setTitle("Success");
                success.setContentText("Room " + room.getRoomNumber() + " has been successfully added.");
                success.showAndWait();
                Button hostBtn = (Button) event.getSource();
                Stage stage = (Stage) hostBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                HostRoom host = loader.getController();
                host.setRoom(roomLists);
                host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

                stage.show();
            }
        }else if (roomType.getValue() == null || building.getValue() == null &&
                floor.getValue() == null || roomBox.getValue() == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setGraphic(new ImageView("images/cancel.png"));
            error.setHeaderText("");
            error.setTitle("Failed");
            if(roomType.getValue() == null && building.getValue() == null &&
                    floor.getValue() == null && roomBox.getValue() == null){
                error.setContentText("Text cannot be blank.");
            }
            else if(roomType.getValue() == null) {
                error.setContentText("Please Select RoomType.");
            }
            else if(building.getValue() == null) {
                error.setContentText("Please Select Building.");
            }
            else if(floor.getValue() == null) {
                error.setContentText("Please Select Floor.");
            }
            else if(roomBox.getValue() == null) {
                error.setContentText("Please Select Room.");
            }
            error.showAndWait();
        } else{
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setGraphic(new ImageView("images/cancel.png"));
            error.setHeaderText("");
            error.setTitle("Failed");
            error.setContentText("Room Number : " + room.getRoomNumber() + " already exists");
            error.showAndWait();
        }
    }
}
