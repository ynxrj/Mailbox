package mailbox.services.implementation;

import mailbox.models.aboutCondo.Room;
import mailbox.models.aboutCondo.RoomLists;
import mailbox.services.interfaceClass.FileData;

import java.io.*;

public class RoomFileData implements FileData {

    private String fileDirectoryName;
    private String fileName;
    private RoomLists lists;

    public RoomFileData(String fileDirectoryName, String fileName) {
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
            Room room = new Room(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Integer.parseInt(data[5]));
            lists.addRoom(room);
        }
        reader.close();
    }

    @Override
    public RoomLists getList() {
        try {
            lists = new RoomLists();
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
        RoomLists lists = (RoomLists) object;
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Room room: lists.getLists()) {
                String line = room.getRoomType() + ","
                            + room.getBuilding() + ","
                            + room.getFloor() + ","
                            + room.getRoom() + ","
                            + room.getRemaining() + ","
                            + room.getCapacity() + ","
                            + room.getRoomNumber();
                    writer.append(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Cannot write " + filePath);
        }
    }
}
