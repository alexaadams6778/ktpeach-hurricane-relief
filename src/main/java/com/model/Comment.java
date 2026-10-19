package com.model;

/**
 * @author KTPeach
 * Comments on a help request
 */

import java.util.ArrayList;

public class Comment {

    private User author;
    private String timestamp;
    private String text;
    private ArrayList<String> replies;

    /**
     * the constructor for a Comment object
     * @param author the user that created the comment as a User object
     * @param timestamp the time when the comment was posted as a String
     * @param text the contents of the comment as a String
     */

    public Comment(User author, String timestamp, String text) {
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
        this.replies = new ArrayList<String>();
    }

    /**
     * returns the author of the comment
     * @return a User
     */

    public User getAuthor() {
        return author;
    }

    /**
     * returns the timestamp of the comment
     * @return a String
     */

    public String getTimestamp() {
        return timestamp;
    }

    /**
     * returns the contents of the comment
     * @return a String
     */

    public String getText() {
        return text;
    }

    /**
     * returns the replies to the comments
     * @return an ArrayList of Strings
     */

    public ArrayList<String> getReplies() {
        return replies;
    }

    /**
     * adds a reply to a comment, and checks to make sure the reply is valid
     * @param reply the reply to the comment as a String
     */

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