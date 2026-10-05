package com.model;
import java.util.ArrayList;

public class Comment {
    
    private User author;
    private String timestamp;
    private String text;
    private ArrayList<String> replies;

    public Comment(User author, String timestamp, String text) {
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
        this.replies = new ArrayList<String>();
    }

}
