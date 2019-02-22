package com.example.onebook;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    private Borrower user = new Borrower("John", "hunter2",
            "mjohn@ualberta.ca", 7806216276L, 17892);


    @Test
    public void checkLogin(){
        Assert.assertTrue(user.Login("John", "hunter2"));
    }

    @Test
    public void checkChangeUsername(){
        Assert.assertTrue(user.changeUsername("Micheal"));
    }

    @Test
    public void checkChangePassword(){
        Assert.assertTrue(user.changePassword("password"));
    }

    @Test
    public void checkChangeContactInfo(){
        Assert.assertTrue(user.editContactInfo("jmichael@ualberta.ca",
                7804737373L));
    }

    @Test
    public void checkGetUsername(){
        Assert.assertEquals("Michael", user.getUsername());
    }

    @Test
    public void checkGetPassword(){
        Assert.assertEquals("password", user.getPassword());
    }

    @Test
    public void checkGetPhone(){
        Assert.assertEquals(7804737373L, user.getPhone());
    }

    @Test
    public void checkGetEmail(){
        Assert.assertEquals("jmichael@ualberta.ca",
                user.getEmail());
    }

    @Test
    public void checkGetUserID(){
        Assert.assertEquals(17892, user.getUserID());
    }

}
