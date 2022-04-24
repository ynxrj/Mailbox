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
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.storage.*;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.StockFileData;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;

public class HostDocumentsList {
    @FXML
    private Label nameLabel, senderText, receiverText;;
    @FXML
    private TableView<Letter> documentTable;
    private ObservableList<Letter> documentObservableList;
    @FXML
    private JFXComboBox<String> sortBy;
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
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
    private AllStockList stockList, documentsList, documentInList;
    private FileData stockData;
    private Document selectedDocument;

    public void initialize(){
            Platform.runLater(() -> {
                receivedBtn.setDisable(true);
                stockList = new AllStockList();
                nameLabel.setText(currentHost.getName());
                stockData = new StockFileData("data", "stock.csv");
                stockList = (AllStockList) stockData.getList();
                documentsList = new AllStockList();
                for (Letter item : stockList.getAllStockList()) {
                    if (item instanceof Document) {
                        documentsList.addStock(item);
                    }
                }
                sortBy.getItems().addAll("Room", "Time");
                showDocumentData();
                showSearch();
            });
        documentTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedItem((Document) newValue);
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

    private void showSelectedItem(Document document) {
        selectedDocument = document;
        senderText.setText(document.getSender());
        receiver = document.getReceiver().split(" - ");
        receiverText.setText(receiver[1]);
        receivedBtn.setDisable(false);
        File picture = new File(document.getPath());
        Image pic = new Image(picture.toURI().toString());
        sampleImage.setImage(pic);
        getTime = document.getTime();
        path = document.getPath();
    }

    private String getTime;
    private String path;

    private void clearSelectedItem() {
        selectedDocument = null;
        senderText.setText("-");
        receiverText.setText("-");
        receivedBtn.setDisable(true);
        sampleImage.setImage(null);
        receivedBtn.setDisable(true);
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
                Document item = (Document) stockList.getItemByTime(getTime);
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                if(item.getPriority().equals("Secret")){
                    stockList.removeItemByTime(item.getTime());
                    if (!path.equals("-")) {
                        Files.delete(Paths.get(path));
                    }
                }else {
                    item.setStatus("Outgoing");
                    item.setReceiveFrom(currentHost.getName());
                    item.setTime(time);
                }
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_documents_list.fxml"));
                stage.setScene(new Scene(loader.load(), 800, 600));
                HostDocumentsList host = loader.getController();
                host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
                stage.show();
            }
        }
    }

    public void showDocumentData(){
        documentInList = documentsList.getInStockList();
        documentObservableList = FXCollections.observableArrayList(documentInList.getAllStockList());

        documentTable.setItems(documentObservableList);
        TableColumn sender = new TableColumn("Sender");
        TableColumn receiver = new TableColumn("Receiver");
        TableColumn size = new TableColumn("Size");
        TableColumn addBy = new TableColumn("Host");
        TableColumn time = new TableColumn("Time");
        TableColumn priority = new TableColumn("Priority");
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
        priority.setCellValueFactory(
                new PropertyValueFactory<Letter,String>("priority")
        );

        documentTable.getColumns().addAll(sender, receiver, size, priority, addBy, time);
        time.setSortType(TableColumn.SortType.ASCENDING);
        receiver.setSortType(TableColumn.SortType.ASCENDING);
        sortBy.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sortBy.getValue().equals("Time") || sortBy.getValue() == null) {
                documentTable.getColumns().clear();
                documentTable.getColumns().addAll(sender, receiver, size, priority, addBy, time);
                documentTable.getSortOrder().add(time);
            } else if (sortBy.getValue().equals("Room")){
                documentTable.getColumns().clear();
                documentTable.getColumns().addAll(sender, receiver, size, priority, addBy, time);
                documentTable.getSortOrder().add(receiver);
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
        ObservableList observableList =  documentTable.getItems();
        searchText.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (oldValue != null && (newValue.length() < oldValue.length())) {
                documentTable.setItems(observableList);
            }
            String search = newValue.toLowerCase();
            ObservableList<Letter> showSearchList = FXCollections.observableArrayList();
            long column = documentTable.getColumns().size();
            for (int i = 0; i < documentTable.getItems().size(); i++) {
                for (int j = 0; j < column; j++) {
                    String residentResult = "" + documentTable.getColumns().get(j).getCellData(i);
                    if (residentResult.toLowerCase().contains(search)) {
                        showSearchList.add(documentTable.getItems().get(i));
                        break;
                    }
                }
            }
            documentTable.setItems(showSearchList);
        });
    }


}
