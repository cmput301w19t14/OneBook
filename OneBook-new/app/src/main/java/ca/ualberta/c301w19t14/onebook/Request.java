package ca.ualberta.c301w19t14.onebook;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Request {


    private String id;
    private String date;
    private Location location;
    private User user;
    private Book book;
    private String status;
    private String owneremail;
    private String requesteremail;
    private long ISBN;

    public Request() {
    }

    public Request(User user, Book book){
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        this.user = user;
        this.book = book;
        this.status = "Pending";
        this.date = ts;
    }

    Request(User user, Book book, Location location, String date, String status){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
        this.status = status;
    }


    public static void requestBook(User user, Book book) {
        // save to database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Requests");
        String name = myRef.child(book.getId()).push().getKey();
        Request request = new Request(user, book);
        request.setId(name);
        myRef.child(name).setValue(request);

        // notify the user
        // part 5
    }

    public void setLocation() {
        // TODO
    }

    public String getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public String getStatus() {
        return status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setISBN(long ISBN) {
        book.setIsbn(ISBN);
    }

    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }


    public void setRequesteremail(String requesteremail) {
        this.requesteremail = requesteremail;
    }

    Request(User user, Book book, Location location, String date){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
