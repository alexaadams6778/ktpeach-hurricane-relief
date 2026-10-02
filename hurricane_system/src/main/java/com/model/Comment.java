package com.model;
import java.util.ArrayList;

public class Comment {
    
    private int commentID;
    private User author;
    private String timestamp;
    private String text;
    private ArrayList<String> replies;

    public Comment(int commentID, User author, String timestamp, String text) {
        this.commentID = commentID;
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
        this.replies = new ArrayList<String>();
    }

}
