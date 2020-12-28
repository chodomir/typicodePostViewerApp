package com.example.typicodepostviewer;

import java.io.Serializable;

public class User implements Serializable {
    //default serialVersion id
    private static final long serialVersionUID = 1L;

    private final String mName;
    private final String mEmail;
    private final int mUserId;

    public User(String name, String email, int userId) {
        mName = name;
        mEmail = email;
        mUserId = userId;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }

    public int getUserId() {
        return mUserId;
    }
}
