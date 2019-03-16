package ca.ualberta.c301w19t14.onebook;

/**This class will be used in part 5
 * @author CMPUT 301 Team 14*/
public class Notification{

    private Boolean read;
    private String notification;
    private String senderID;
    private String recipientID;

    /**
     * constructor for Notification
     * @param read
     * @param notification
     * @param senderID
     * @param recipientID
     */
    public Notification(Boolean read, String notification, String senderID, String recipientID ){
        this.read = read;
        this.notification = notification;
        this.senderID = senderID;
        this.recipientID = recipientID;
    }

    /**
     * getter for the read status
     * @return read
     */
    public Boolean getRead() {
        return read;
    }

    /**
     * setter for the set read
     * @param read
     */
    public void setRead(Boolean read) {
        this.read = read;
    }

    /**
     * getter for notifications
     * @return notification
     */
    public String getNotification() {
        return notification;
    }

    /**
     * setter for notification
     * @param notification
     */
    public void setNotification(String notification) {
        this.notification = notification;
    }

    /**
     * getter for the senderID
     * @return senderID
     */
    public String getSenderID() {
        return senderID;
    }

    /**
     * setter for the senderID
     * @param senderID
     */
    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    /**
     * getter for the recipient ID
     * @return recipientID
     */
    public String getRecipientID() {
        return recipientID;
    }

    /**
     * setter for the recipient ID
     * @param recipientID
     */
    public void setRecipientID(String recipientID) {
        this.recipientID = recipientID;
    }

    /**
     *
     * @param read
     * @param notification
     * @param senderID
     * @param recipientID
     * @return Notification
     */
    public Notification createNotification(Boolean read, String notification,
                                           String senderID, String recipientID){
        Notification note = new Notification(read, notification, senderID, recipientID);
        return note;
    }

    /**
     *
     * @param notificationType
     * @param senderID
     * @param recipientID
     * @param notification
     * @return boolean
     */
    public Boolean deleteNotification(Boolean read, String notification,
                                      String senderID, String recipientID){
        return Boolean.TRUE;
    }

    /**
     *
     * @param notificationType
     * @param senderID
     * @param recipientID
     * @param notification
     * @return boolean
     */
    public Boolean sendNotification(Boolean read, String notification,
                                    String senderID, String recipientID){
        return Boolean.TRUE;
    }
}