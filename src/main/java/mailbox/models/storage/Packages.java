package mailbox.models.storage;

import java.util.ArrayList;

public class Packages extends Letter {
    private String trackingNumber;
    private String transporter;

    public Packages(String sender, String receiver, String size, String transporter, String trackingNumber, String path, String addby, String time, String status, String receiveFrom){
        super(sender, receiver, size, path, addby, time, status, receiveFrom);
        this.transporter = transporter;
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getTransporter() {
        return transporter;
    }


}
