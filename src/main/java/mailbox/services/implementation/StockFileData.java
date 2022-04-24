package mailbox.services.implementation;

import mailbox.models.storage.AllStockList;
import mailbox.models.storage.Document;
import mailbox.models.storage.Letter;
import mailbox.models.storage.Packages;
import mailbox.services.interfaceClass.FileData;

import java.io.*;

public class StockFileData implements FileData {

    private String fileDirectoryName;
    private String fileName;
    private AllStockList lists;

    public StockFileData(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }

    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line = "";
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
                if (data[0].equals("11")) {
                    Letter item = new Letter(data[1], data[2], data[3], data[4], data[5], data[6],data[7], data[8]);
                    lists.addStock(item);
                }
                else if (data[0].equals("12")) {
                    Letter item = new Document(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9]);
                    lists.addStock(item);
                }
                else if (data[0].equals("13")) {
                    Letter item = new Packages(data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10]);
                    lists.addStock(item);
                }
        }
        reader.close();
    }

    @Override
    public AllStockList getList() {
        try {
            lists = new AllStockList();
            readData();
        } catch (FileNotFoundException ex) {
            System.err.println(this.fileName + " not found");
        } catch (IOException ex) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return lists;
    }

    @Override
    public void setList(Object object) {
        AllStockList lists = (AllStockList) object;
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for(Letter item : lists.getAllStockList()) {
                if (item instanceof Packages) {
                    String line = "13" + "," + item.getSender() + ","
                            + item.getReceiver() + ","
                            + item.getSize() + ","
                            + ((Packages) item).getTransporter() + ","
                            + ((Packages) item).getTrackingNumber() + ","
                            + item.getPath() + ","
                            + item.getAddBy() + ","
                            + item.getTime() + ","
                            + item.getStatus() + ","
                            + item.getReceiveFrom();
                    writer.append(line);
                } else if (item instanceof Document) {
                    String line = "12" + "," + item.getSender() + ","
                            + item.getReceiver() + ","
                            + item.getSize() + ","
                            + ((Document) item).getPriority() + ","
                            + item.getPath() + ","
                            + item.getAddBy() + ","
                            + item.getTime() + ","
                            + item.getStatus() + ","
                            + item.getReceiveFrom();
                    writer.append(line);
                } else if(item != null){
                    String line = "11" + "," + item.getSender() + ","
                            + item.getReceiver() + ","
                            + item.getSize() + ","
                            + item.getPath() + ","
                            + item.getAddBy() + ","
                            + item.getTime() + ","
                            + item.getStatus() + ","
                            + item.getReceiveFrom();
                    writer.append(line);
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
