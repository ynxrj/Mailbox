package mailbox.controllers.host.roomAndResident;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import java.io.IOException;

public class HostRoomList {
    @FXML
    private Label nameLabel;
    @FXML
    private TableView<Room> roomTable;
    private ObservableList<Room> roomObservableList;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
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
            nameLabel.setText(currentHost.getName());
            showLists();
            showSearch();
        });
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
    public void handleHostRoomBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoom host = loader.getController();
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
    public void handleHostStockBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStock host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostChangingPasswordBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_changing_password.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostChangingPassword host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleHostSoutBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_login.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostLogin host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleBackBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_room.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostRoom host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void showLists(){
        roomObservableList = FXCollections.observableArrayList(roomLists.getLists());

        roomTable.setItems(roomObservableList);
        TableColumn building = new TableColumn("Building");
        TableColumn floor = new TableColumn("Floor");
        TableColumn room = new TableColumn("Room");
        TableColumn roomType = new TableColumn("Room Type");
        TableColumn roomNum = new TableColumn("Number");
        TableColumn capacity = new TableColumn("Capacity");
        TableColumn remaining = new TableColumn("Remaining");

        building.setCellValueFactory(
                new PropertyValueFactory<Room,String>("building")
        );
        floor.setCellValueFactory(
                new PropertyValueFactory<Room,String>("floor")
        );
        room.setCellValueFactory(
                new PropertyValueFactory<Room,String>("room")
        );
        roomType.setCellValueFactory(
                new PropertyValueFactory<Room,String>("roomType")
        );
        remaining.setCellValueFactory(
                new PropertyValueFactory<Room,String>("remaining")
        );
        roomNum.setCellValueFactory(
                new PropertyValueFactory<Room,String>("roomNumber")
        );
        capacity.setCellValueFactory(
                new PropertyValueFactory<Room,String>("capacity")
        );


        roomTable.getColumns().addAll(building, floor, room, roomNum, roomType, capacity, remaining);
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  roomTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                roomTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Room> showSearchList = FXCollections.observableArrayList();
            long column = roomTable.getColumns().size();
            for (int i = 0; i < roomTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + roomTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(roomTable.getItems().get(i));
                        break;
                    }
                }
            }
            roomTable.setItems(showSearchList);
        });
    }


}
