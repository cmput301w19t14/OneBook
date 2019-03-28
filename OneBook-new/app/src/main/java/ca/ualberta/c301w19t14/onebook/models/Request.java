package ca.ualberta.c301w19t14.onebook.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.ualberta.c301w19t14.onebook.Globals;

/**This class implements the transaction of a user requesting a book and updates the database
 * @author CMPUT 301 Team 14*/
public class Request {

    final static String ACCEPTED = "Accepted";

    private String id;
    private String date;
    private Location location;
    private User user;
    private Book book;
    private String status;
    private String owneremail;
    private String requesteremail;
    private String time;
    private long ISBN;

    public Request() {
    }

    /**
     *
     * @param user
     * @param book
     */
    public Request(User user, Book book){
        // https://stackoverflow.com/questions/8077530/android-get-current-timestamp
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        this.user = user;
        //this.book = book;
        this.status = "Pending";
        //this.date = ts;
    }

    /**
     *
     * @param user
     * @param book
     * @param location
     * @param date
     * @param status
     */
    public Request(User user, Book book, Location location, String date, String status){
        this.user = user;
        //this.book = book;
        this.location = location;
        this.date = date;
        this.status = status;
    }

    /**
     *
     * @param user
     * @param book
     */
    public static void requestBook(User user, Book book, String book_id) {
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();

        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database2.getReference("Books");
        String name2 = myRef2.child(book.getId()).getKey();

        //adds user to waitlist if they aren't already on that book's waitlist
        DataSnapshot bookData = Globals.getInstance().books.getData();
        for (DataSnapshot i: bookData.getChildren()){
            Book item = i.getValue(Book.class);
            if (item.getId().equals(book_id)){

                //Natalie is currently editing this part for the waitlist

                //for(DataSnapshot j: bookData.child(book.getId()).getChildren()) {
                    ///Book item2 = j.getValue(Book.class);

                    //Log.d("my_test_working", j.child("request").getKey());
                    //Log.d("my_test_maybe", j.child("request").child("user").getValue().toString());
                    //Log.d("my_test_1", j.getValue().toString());
                    //Log.d("my_test_2", item.getRequest().
                //}
                Request request2 = new Request(user, book);
                myRef2.child(book_id).child("request").child(ts).setValue(request2);
            }
        }

        /*
                    long isbn = Long.parseLong(barcode);
                    DataSnapshot book = Globals.getInstance().books.getData();
                    for (DataSnapshot i : book.getChildren()) {
                        Book item = i.getValue(Book.class);
                        if(item.getIsbn() == isbn) {
                            if(item.getOwner().getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                // user is owner: we go to view book activity
                                Intent intent = new Intent(ScanIsbnActivity.this, ViewBookActivity.class);
                                final Bundle bundle = new Bundle();
                                String id = item.getId();
                                bundle.putString("id", id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }


         */





        // notify the user
        // part 5
    }

    /**
     *
     */
    public void setLocation() {
        // TODO
    }

    /**
     *
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @return location
     */
    public Location getLocation() {
        return location;
    }

    /**
     *
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @return book
     */
    public Book getBook() {
        return book;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @param ISBN
     */
    public void setISBN(long ISBN) {
        book.setIsbn(ISBN);
    }

    /**
     *
     * @param owneremail
     */
    public void setOwneremail(String owneremail) {
        this.owneremail = owneremail;
    }

    /**
     *
     * @param requesteremail
     */
    public void setRequesteremail(String requesteremail) {
        this.requesteremail = requesteremail;
    }

    /**
     *
     * @param user
     * @param book
     * @param location
     * @param date
     */
    Request(User user, Book book, Location location, String date){
        this.user = user;
        this.book = book;
        this.location = location;
        this.date = date;
    }

    /**
     *
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @param user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @param book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

}
