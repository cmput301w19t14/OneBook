package ca.ualberta.c301w19t14.onebook;

public class Notification{

    private Boolean read;
    private String notification;
    private Integer senderID;
    private Integer recipientID;


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

    public Integer getSenderID() {
        return senderID;
    }
    public void setSenderID(Integer senderID) {
        this.senderID = senderID;
    }

    public Integer getRecipientID() {
        return recipientID;
    }
    public void setRecipientID(Integer recipientID) {
        this.recipientID = recipientID;
    }

    public Boolean createNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
        return Boolean.TRUE;
    }

    public Boolean deleteNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
        return Boolean.TRUE;
    }

    public Boolean sendNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
        return Boolean.TRUE;
    }
}
