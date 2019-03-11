package ca.ualberta.c301w19t14.onebook;

public class Notification{

    private Boolean read;
    private String notification;
    private String senderID;
    private String recipientID;


    public Boolean getRead() {
        return read;
    }
    public void setRead(Boolean read) {
        this.read = read;
    }

    public String getNotification() {
        return notification;
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getSenderID() {
        return senderID;
    }
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getRecipientID() {
        return recipientID;
    }
    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    public Boolean createNotification(Integer notificationType, String senderID,
                                      String recipientID, String notification){
        return Boolean.TRUE;
    }

    public Boolean deleteNotification(Integer notificationType, String senderID,
                                      String recipientID, String notification){
        return Boolean.TRUE;
    }

    public Boolean sendNotification(Integer notificationType, String senderID,
                                    String recipientID, String notification){
        return Boolean.TRUE;
    }
}
