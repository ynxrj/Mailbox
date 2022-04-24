package mailbox.models.storage;

public class Letter {
    private String sender;
    private String receiver;
    private String size;
    private String path;
    private String addBy;
    private String time;
    private String status;
    private String receiveFrom;
    private final String recipient = "By Yourself";

    public Letter(String sender, String receiver, String size, String path, String addBy, String time, String status, String receiveFrom) {
        this.sender = sender;
        this.receiver = receiver;
        this.size = size;
        this.path = path;
        this.addBy = addBy;
        this.time = time;
        this.status = status;
        this.receiveFrom = receiveFrom;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getStatus() {
        return status;
    }

    public String getReceiveFrom() {
        return receiveFrom;
    }

    public String getSender() {
        return sender;
    }
    public String getReceiver() {
        return receiver;
    }
    public String getSize() {
        return size;
    }
    public String getPath() {
        return path;
    }

    public String getAddBy() {
        return addBy;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }
}
