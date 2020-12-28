package com.example.typicodepostviewer;

import java.io.Serializable;

public class Post implements Serializable {
    //default serialVersion id
    private static final long serialVersionUID = 1L;

    private final String mTitle;
    private final String mBody;
    private final int mUserId;
    private final int mId;

    public Post(String title, String body, int userId, int id) {
        mTitle = title;
        mBody = body;
        mUserId = userId;
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public int getUserId() {
        return mUserId;
    }

    public int getId() {
        return mId;
    }
}
