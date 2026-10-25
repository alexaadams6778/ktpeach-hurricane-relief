package com.model;

import java.util.ArrayList;

/**
 * @author KTPeach
 * Represents a comment posted on a help request.
 * A comment records its author, time, text, and any text replies.
 */
public class Comment {

    private User author;
    private String timestamp;
    private String text;
    private ArrayList<String> replies;

    /**
     * Creates a comment on a help request.
     *
     * @param author the user who wrote the comment
     * @param timestamp the time the comment was posted
     * @param text the comment text
     */
    public Comment(User author, String timestamp, String text) {
        this.author = author;
        this.timestamp = timestamp;
        this.text = text;
        this.replies = new ArrayList<String>();
    }

    /**
     * Returns the user who wrote the comment.
     *
     * @return the comment author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Returns the time the comment was posted.
     *
     * @return the comment timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * Returns the comment text.
     *
     * @return the comment text
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the replies to this comment.
     *
     * @return the list of reply text
     */
    public ArrayList<String> getReplies() {
        return replies;
    }

    /**
     * Adds a nonblank reply to this comment.
     *
     * @param reply the reply text to add
     */
    public void addReply(String reply) {
        if (reply != null && !reply.isBlank()) {
            replies.add(reply);
        }
    }

    /**
     * Returns the author username and comment text.
     *
     * @return the formatted comment
     */
    @Override
    public String toString() {
        return author.getUsername() + ": " + text;
    }
}