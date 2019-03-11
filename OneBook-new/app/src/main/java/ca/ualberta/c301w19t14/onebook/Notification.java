package ca.ualberta.c301w19t14.onebook;

/**This class will be used in part 5
 * @author CMPUT 301 Team 14*/
public class Notification{

    private Boolean read;
    private String notification;
    private String senderID;
    private String recipientID;

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
     * @param notificationType
     * @param senderID
     * @param recipientID
     * @param notification
     * @return boolean
     */
    public Boolean createNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
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
    public Boolean deleteNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
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
    public Boolean sendNotification(Integer notificationType, Integer senderID,  Integer recipientID, String notification){
        return Boolean.TRUE;
    }
}