package ca.ualberta.c301w19t14.onebook;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private Borrower user = new Borrower("John", "hunter2",
            "mjohn@ualberta.ca", 7806216276L, 17892L);


//comment
    @Test
    public void checkLogin(){
        Assert.assertTrue(user.Login("John", "hunter2"));
    }

    @Test
    public void checkChangeContactInfo(){
        user.editContactInfo("jmike@ualberta.ca", 7804474485L);
        Assert.assertEquals("jmike@ualberta.ca", user.getEmail());
        Assert.assertEquals(7804474485L, user.getPhone());
    }

    @Test
    public void checkSetUsername(){
        user.setUsername("Mike");
        Assert.assertEquals("Mike", user.getUsername());
    }

    @Test
    public void checkSetPassword(){
        user.setPassword("password123");
        Assert.assertEquals("password123", user.getPassword());
    }

    @Test
    public void checkSetPhone(){
        user.setPhone(7809042237L);
        Assert.assertEquals(7809042237L, user.getPhone());
    }

    @Test
    public void checkSetEmail(){
        user.setEmail("jMickey@ualberta.ca");
        Assert.assertEquals("jMickey@ualberta.ca",
                user.getEmail());
    }

    @Test
    public void checkSetUserID(){
        user.setUserID(17224L);
        Assert.assertEquals(17224L, user.getUserID());
    }

}
