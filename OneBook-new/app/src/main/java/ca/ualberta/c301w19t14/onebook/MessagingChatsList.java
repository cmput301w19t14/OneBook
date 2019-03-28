package ca.ualberta.c301w19t14.onebook;

public class MessagingChatsList {
    private String id;

    public MessagingChatsList(String id){
        this.id = id;
    }

    public MessagingChatsList(){
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }
}
