package mailbox.models.storage;

public class Document extends Letter {
    private String priority;

    public Document(String sender, String receiver, String size, String priority, String path, String addby, String time, String status, String receiveFrom){
        super(sender, receiver, size, path, addby, time, status, receiveFrom);
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
}
