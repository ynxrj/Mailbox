package mailbox.controllers.host.stock;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mailbox.controllers.host.*;
import mailbox.controllers.host.roomAndResident.HostAdd;
import mailbox.controllers.host.roomAndResident.HostRoom;
import mailbox.models.userAccount.ResidentUsers;
import mailbox.models.userAccount.ResidentsList;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.models.storage.AllStockList;
import mailbox.models.storage.Document;
import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.ImageDataWriter;
import mailbox.services.implementation.ImageFileDataWriter;
import mailbox.services.interfaceClass.FileData;
import mailbox.services.implementation.StockFileData;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;


public class HostDocument {
    private Users adminUsers;
    private AdminLists adminLists;
    private HostUsers hostUsers, currentHost;
    private HostLists hostLists;
    private AllUsersLists allLists;
    private RoomLists roomLists;
    private ResidentsList residentsList;
    private AllStockList stockList;
    private FileChooser fc;
    private File selectedFile;
    private String path = "-";
    private ImageDataWriter imageDataWriter;
    private FileData stockData;
    @FXML
    private Label nameLabel;
    @FXML
    private Label imagePath;
    @FXML
    private JFXTextField senderText;
    @FXML
    private JFXComboBox<String> sizeText, priority, receiverText;

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
        stockList = new AllStockList();
        stockData = new StockFileData("data", "stock.csv");
        stockList = (AllStockList) stockData.getList();
        imageDataWriter = new ImageFileDataWriter("stockImage");
        fc = new FileChooser();
        fc.setTitle("Stock Image Uploader");
        fc.getExtensionFilters().addAll
                (new FileChooser.ExtensionFilter("JPG Files", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG Files", "*.png"));

        Platform.runLater(() -> {
            sizeText.getItems().addAll("XS", "S", "M", "L", "XL", "XXL");
            for(ResidentUsers residentUsers : residentsList.getResidentslist()){
                receiverText.getItems().add(residentUsers.getRoomNumber() + " - " + residentUsers.getName());
            }
            priority.getItems().addAll("Secret","Critical", "Important", "Normal", "Low");
            stockData = new StockFileData("data", "stock.csv");
            stockList = (AllStockList) stockData.getList();
            nameLabel.setText(currentHost.getName());
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
    public void handleHostPackageBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_package.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostPackage host = loader.getController();
        host.setAllData(adminUsers, adminLists, allLists, hostUsers, hostLists, currentHost, roomLists, residentsList);
        stage.show();
    }
    @FXML
    public void handleHostDocumentBtn(ActionEvent event) throws IOException {
        Button hostBtn = (Button) event.getSource();
        Stage stage = (Stage) hostBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/host/host_document.fxml"));
        stage.setScene(new Scene(loader.load(), 800, 600));
        HostDocument host = loader.getController();
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

    public void handleUploadBtn(){
        selectedFile = fc.showOpenDialog(null);
        if (selectedFile != null) {
            path = "stockImage" + File.separator + selectedFile.getName();
            imagePath.setText(selectedFile.getName());
        }
    }

    public void handleAddBtn() throws IOException{
        if(!senderText.getText().equals("") && receiverText.getValue() != null && sizeText.getValue() != null && priority.getValue() != null) {
            if(senderText.getText().contains(",") || sizeText.getValue().contains(",") || priority.getValue().contains(",")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setGraphic(new ImageView("images/cancel.png"));
                alert.setTitle("Failed...");
                alert.setHeaderText("");
                alert.setContentText("Can not use comma in information text.");
                alert.showAndWait();
            }else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setGraphic(new ImageView("images/warning.png"));
                confirm.setTitle("Confirmation.");
                confirm.setHeaderText("");
                confirm.setContentText("Are you sure?");
                Optional<ButtonType> confirmation = confirm.showAndWait();
                if (confirmation.get() == ButtonType.OK) {
                    try {
                        if (selectedFile != null) {
                            imageDataWriter.copyData(selectedFile, new File(path));
                        }
                        String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

                        Document document = new Document(senderText.getText(), receiverText.getValue(), sizeText.getValue(), priority.getValue(), path, currentHost.getName(), time, "InStock", "-");

                        stockList.addStock(document);
                        stockData = new StockFileData("data", "stock.csv");
                        stockData.setList(stockList);
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setGraphic(new ImageView("images/confirm.png"));
                        success.setHeaderText("");
                        success.setTitle("Success");
                        success.setContentText("The parcel has been successfully added.");
                        success.showAndWait();
                        clearData();
                    } catch (FileAlreadyExistsException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setGraphic(new ImageView("images/cancel.png"));
                        alert.setTitle("Failed...");
                        alert.setHeaderText("");
                        alert.setContentText(selectedFile.getName() + " already exists, Please rename or select another file.");
                        alert.showAndWait();
                        imagePath.setText("");
                        selectedFile = null;
                        path = "";
                    }
                }
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setGraphic(new ImageView("images/cancel.png"));
            alert.setTitle("Failed...");
            alert.setHeaderText("");
            alert.setContentText("Text cannot be blank.");
            alert.showAndWait();
        }
    }

    public void clearData(){
        imagePath.setText("");
        senderText.clear();
        receiverText.getSelectionModel().clearSelection();
        sizeText.setValue("");
        sizeText.getSelectionModel().clearSelection();
        priority.setValue("");
        priority.getSelectionModel().clearSelection();
        selectedFile = null;
        path = "-";
    }

}
