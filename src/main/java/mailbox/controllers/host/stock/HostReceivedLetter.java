package mailbox.controllers.host.stock;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class HostReceivedLetter {
    @FXML
    private Label nameLabel, senderText, receiverText;
    @FXML
    private TableView<Letter> lettersTable;
    @FXML
    private Button removeBtn;
    private ObservableList<Letter> lettersObservableList;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private AllStockList stockList, lettersList, letterOutList;
    private FileData stockData;
    private Letter selectedLetter;

    public void setAllData(Users adminUsers, AdminLists adminLists, AllUsersLists allLists, HostUsers hostUsers, HostLists hostLists, HostUsers currentHost, RoomLists roomLists, ResidentsList residentsList) {
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
        removeBtn.setDisable(true);
        Platform.runLater(() -> {
            stockList = new AllStockList();
            nameLabel.setText(currentHost.getName());
            stockData = new StockFileData("data", "stock.csv");
            stockList = (AllStockList) stockData.getList();
            lettersList = new AllStockList();
            for(Letter item : stockList.getAllStockList()){
                if(item instanceof Packages){
                    continue;
                }else if(item instanceof Document){
                    continue;
                }else{
                    lettersList.addStock(item);
                }
            }
            showLetterData();
            showSearch();
        });

        lettersTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem(newValue);
            }
        });
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

    public void showLetterData(){
        letterOutList = lettersList.getOutStockList();
        lettersObservableList = FXCollections.observableArrayList(letterOutList.getAllStockList());

        lettersTable.setItems(lettersObservableList);
        TableColumn sender = new TableColumn("Sender");
        TableColumn receiver = new TableColumn("Receiver");
        TableColumn size = new TableColumn("Size");
        TableColumn from = new TableColumn("Host");
        TableColumn time = new TableColumn("Time");
        TableColumn resident = new TableColumn("Recipient");

        resident.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("recipient")
        );

        sender.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("sender")
        );
        receiver.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("receiver")
        );
        size.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("size")
        );
        from.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("receiveFrom")
        );
        time.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("time")
        );


        time.setSortType(TableColumn.SortType.DESCENDING);
        lettersTable.getColumns().addAll(sender, receiver, size, from, resident, time);
        lettersTable.getSortOrder().add(time);
    }

    @FXML
    private ImageView sampleImage;
    private String[] receiver;
    private String getTime;
    private String path;

    private void showSelectedItem(Letter letter) {
        receiver = letter.getReceiver().split(" - ");
        receiverText.setText(receiver[1]);
        selectedLetter = letter;
        senderText.setText(letter.getSender());
        removeBtn.setDisable(false);
        File picture = new File(letter.getPath());
        Image pic = new Image(picture.toURI().toString());
        sampleImage.setImage(pic);
        getTime = letter.getTime();
        path = letter.getPath();
    }

    private void clearSelectedItem() {
        selectedLetter = null;
        senderText.setText("-");
        receiverText.setText("-");
        removeBtn.setDisable(true);
        sampleImage.setImage(null);
        getTime = "";
    }

    @FXML
    public void handleRemoveBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setGraphic(new ImageView("images/warning.png"));
        alert.setTitle("Confirmation.");
        alert.setHeaderText("");
        alert.setContentText("Are you sure you want to permanently delete this parcel");
        Optional<ButtonType> confirmation = alert.showAndWait();
        if (confirmation.get() == ButtonType.OK) {
            stockList.removeItemByTime(getTime);
            if (!path.equals("-")) {
                Files.delete(Paths.get(path));
            }
            stockData = new StockFileData("data", "stock.csv");
            stockData.setList(stockList);
            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setGraphic(new ImageView("images/confirm.png"));
            success.setHeaderText("");
            success.setTitle("Success");
            success.setContentText("Parcel has been removed successfully");
            success.showAndWait();
            clearSelectedItem();
            Button hostBtn = (Button) event.getSource();
            Stage stage = (Stage) hostBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_received_letter.fxml"));
            stage.setScene(new Scene(loader.load(), 800, 600));
            HostReceivedLetter host = loader.getController();
            host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
            stage.show();
        }
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
    public void handleHostReceivedBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_received_letter.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostReceivedLetter host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleHostReceivedDocBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_received_document.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostReceivedDocument host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    public void handleHostReceivedPackBtn(ActionEvent event) throws IOException{
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_received_package.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostReceivedPackage host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }

    @FXML
    private TextField searchText;
    public void showSearch(){
        ObservableList observableList =  lettersTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                lettersTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Letter> showSearchList = FXCollections.observableArrayList();
            long column = lettersTable.getColumns().size();
            for (int i = 0; i < lettersTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + lettersTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(lettersTable.getItems().get(i));
                        break;
                    }
                }
            }
            lettersTable.setItems(showSearchList);
        });
    }
}
