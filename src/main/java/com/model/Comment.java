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

    public User getAuthor() {
        return author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getReplies() {
        return replies;
    }

    public void addReply(String reply) {
        if (reply != null && !reply.isBlank()) {
            replies.add(reply);
        }
    }

    @Override
    public String toString() {
        return author.getUsername() + ": " + text;
    }
}