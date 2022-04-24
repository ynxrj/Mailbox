package mailbox.services.implementation;

import mailbox.models.userAccount.*;
import mailbox.services.interfaceClass.FileData;

import java.io.*;

public class UsersFileData implements FileData {

    private String fileDirectoryName;
    private String fileName;
    private AllUsersLists lists;

    public UsersFileData(String fileDirectoryName, String fileName) {
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
            if(data[0].equals("01")){
                Users admin = new Users(data[1], data[2], data[3]);
                lists.addUsers(admin);
            }else if(data[0].equals("02")){
                Users hostUsers = new HostUsers(data[1], data[2], data[3].trim(),
                        Integer.parseInt(data[4].trim()), data[5], data[6], data[7], Integer.parseInt(data[8]), data[9]);
                lists.addUsers(hostUsers);
            }else if(data[0].equals("03")){
                Users residentUsers = new ResidentUsers(data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
                lists.addUsers(residentUsers);
            }
        }
        reader.close();
    }

    @Override
    public AllUsersLists getList() {
        try {
            lists = new AllUsersLists();
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
        AllUsersLists lists = (AllUsersLists) object;
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Users users: lists.getLists()) {
                if(users instanceof ResidentUsers) {
                    String line = "03" + "," + users.getName() + ","
                            + ((ResidentUsers) users).getSurname() + ","
                            + ((ResidentUsers) users).getPhone() + ","
                            + ((ResidentUsers) users).getRoomNumber() + ","
                            + ((ResidentUsers) users).getRoomType() + ","
                            + users.getUsername() + ","
                            + users.getPassword();
                    writer.append(line);
                }
                else if(users instanceof HostUsers) {
                    String line = "02" + "," + users.getName() + ","
                            + ((HostUsers) users).getSurname() + ","
                            + ((HostUsers) users).getPhone() + ","
                            + ((HostUsers) users).getAge() + ","
                            + users.getUsername() + ","
                            + users.getPassword() + ","
                            + ((HostUsers) users).getStatus() + ","
                            + ((HostUsers) users).getBlockNum() + ","
                            + ((HostUsers) users).getDate();
                    writer.append(line);
                }else{
                    String line = "01" + "," + users.getUsername() + ","
                            + users.getPassword() + ","
                            + users.getName();
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
