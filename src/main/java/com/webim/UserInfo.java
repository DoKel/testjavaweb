package com.webim;

import java.net.URL;

public class UserInfo {

    private String firstName;
    private String lastName;
    private URL photoUrl;

    public UserInfo(String firstName, String lastName, URL photoUrl){
        this.firstName = firstName;
        this.lastName = lastName;
        this.photoUrl = photoUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public URL getPhotoUrl() {
        return photoUrl;
    }
}
