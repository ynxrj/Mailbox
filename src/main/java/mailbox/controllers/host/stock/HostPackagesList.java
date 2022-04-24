package mailbox.controllers.host.stock;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import mailbox.controllers.host.roomAndResident.HostAdd;
import mailbox.controllers.host.HostChangingPassword;
import mailbox.controllers.host.HostLogin;
import mailbox.controllers.host.roomAndResident.HostRoom;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.storage.*;
import mailbox.models.userAccount.*;
import mailbox.services.implementation.StockFileData;
import mailbox.services.interfaceClass.FileData;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

public class HostPackagesList {
    @FXML
    private Label nameLabel, senderText, receiverText;
    @FXML
    private TableView<Letter> packagesTable;
    private ObservableList<Letter> packagesObservableList;
    @FXML
    private JFXComboBox<String> sortBy;
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
    private AllStockList stockList, packagesList, packagesInList;
    private FileData stockData;
    private Packages selectedPackage;

    public void initialize() {
        receivedBtn.setDisable(true);
        Platform.runLater(() -> {
            stockList = new AllStockList();
            nameLabel.setText(currentHost.getName());
            stockData = new StockFileData("data", "stock.csv");
            stockList = (AllStockList) stockData.getList();
            packagesList = new AllStockList();
            for (Letter item : stockList.getAllStockList()) {
                if (item instanceof Packages) {
                    packagesList.addStock(item);
                }
            }
            sortBy.getItems().addAll("Room", "Time");
            showPackagesData();
            showSearch();
        });

        packagesTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem((Packages) newValue);
            }
        });
    }

    @FXML
    private Button receivedBtn;
    @FXML
    private ImageView sampleImage;
    @FXML
    private JFXTextField nameText, surnameText;
    String[] receiver;

    private void showSelectedItem(Packages packages) {
        selectedPackage = packages;
        senderText.setText(packages.getSender());
        receiver = packages.getReceiver().split(" - ");
        receiverText.setText(receiver[1]);
        receivedBtn.setDisable(false);
        File picture = new File(packages.getPath());
        Image pic = new Image(picture.toURI().toString());
        sampleImage.setImage(pic);
        getTime = packages.getTime();
        path = packages.getPath();
    }

    private String getTime;
    private String path;

    private void clearSelectedItem() {
        selectedPackage = null;
        senderText.setText("-");
        receiverText.setText("-");
        receivedBtn.setDisable(true);
        sampleImage.setImage(null);
        getTime = "";
    }

    public void handleReceivedBtn(ActionEvent event) throws IOException {
        if(nameText.getText().equals("") || surnameText.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Error.");
            alert.setHeaderText("");
            alert.setContentText("Text cannot be blank.");
            alert.showAndWait();
        }else if (residentsList.checkResidentByName(nameText.getText(), surnameText.getText()) == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Error.");
            alert.setHeaderText("");
            alert.setContentText("Resident not found");
            alert.showAndWait();
        }else if (!nameText.getText().equals("") && !surnameText.getText().equals("") && !residentsList.checkResidentByName(nameText.getText(), surnameText.getText()).equals(receiver[0])) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Error.");
            alert.setHeaderText("");
            alert.setContentText("This Resident cannot receive this letter");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setGraphic(new ImageView("images/warning.png"));
            alert.setTitle("Confirmation.");
            alert.setHeaderText("");
            alert.setContentText("Are you sure?");
            Optional<ButtonType> confirmation = alert.showAndWait();
            if (confirmation.get() == ButtonType.OK) {
                Packages item = (Packages) stockList.getItemByTime(getTime);
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                item.setStatus("Outgoing");
                item.setReceiveFrom(currentHost.getName());
                item.setTime(time);
                stockData = new StockFileData("data", "stock.csv");
                stockData.setList(stockList);
                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setGraphic(new ImageView("images/confirm.png"));
                success.setHeaderText("");
                success.setTitle("Success");
                success.setContentText("Parcel has been received successfully");
                success.showAndWait();
                clearSelectedItem();
                Button hostBtn = (Button) event.getSource();
                Stage stage = (Stage) hostBtn.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_packages_list.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                HostPackagesList host = loader.getController();
                host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                stage.show();
            }
        }
    }

    public void showPackagesData(){
        packagesInList= packagesList.getInStockList();
        packagesObservableList = FXCollections.observableArrayList(packagesInList.getAllStockList());

        packagesTable.setItems(packagesObservableList);
        TableColumn sender = new TableColumn("Sender");
        TableColumn receiver = new TableColumn("Receiver");
        TableColumn size = new TableColumn("Size");
        TableColumn addBy = new TableColumn("Host");
        TableColumn time = new TableColumn("Time");
        TableColumn transporter = new TableColumn("Transporter");
        TableColumn tracking = new TableColumn("Tracking Number");
        sender.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("sender")
        );
        receiver.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("receiver")
        );
        size.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("size")
        );
        addBy.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("addBy")
        );
        time.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("time")
        );
        transporter.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("transporter")
        );
        tracking.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("trackingNumber")
        );

        packagesTable.getColumns().addAll(sender, receiver, size, transporter, tracking, addBy, time);
        time.setSortType(TableColumn.SortType.ASCENDING);
        receiver.setSortType(TableColumn.SortType.ASCENDING);
        sortBy.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sortBy.getValue().equals("Time") || sortBy.getValue() == null) {
                packagesTable.getColumns().clear();
                packagesTable.getColumns().addAll(sender, receiver, size, transporter, tracking, addBy, time);
                packagesTable.getSortOrder().add(time);
            } else if (sortBy.getValue().equals("Room")){
                packagesTable.getColumns().clear();
                packagesTable.getColumns().addAll(sender, receiver, size, transporter, tracking, addBy, time);
                packagesTable.getSortOrder().add(receiver);
            }
        });
    }

    @FXML
    public void handleHostAddBtn(ActionEvent event) throws IOException{
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
    public void handleHostStockListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStockList host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }
    @FXML
    public void handleHostDocumentListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_documents_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostDocumentsList host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }
    @FXML
    public void handleHostPackageListsBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_packages_list.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostPackagesList host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    public void handleBackBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_stock.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostStock host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);

        stage.show();
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  packagesTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                packagesTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Letter> showSearchList = FXCollections.observableArrayList();
            long column = packagesTable.getColumns().size();
            for (int i = 0; i < packagesTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + packagesTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(packagesTable.getItems().get(i));
                        break;
                    }
                }
            }
            packagesTable.setItems(showSearchList);
        });
    }


}
