package com.example.typicodepostviewer;

import java.io.Serializable;

public class User implements Serializable {
    //default serialVersion id
    private static final long serialVersionUID = 1L;

    private final String mName;
    private final String mEmail;

    public User(String name, String email) {
        mName = name;
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public String getEmail() {
        return mEmail;
    }
}
