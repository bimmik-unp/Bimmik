package com.unysoft.bimmik.model;

import com.google.gson.annotations.SerializedName;

public class MessageItem {

    @SerializedName("id") private String id;
    @SerializedName("from_user_id") private String from_user_id;
    @SerializedName("to_user_id") private String to_user_id;
    @SerializedName("title") private String title;
    @SerializedName("message") private String message;
    @SerializedName("sent_at") private String timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom_user_id() {
        return from_user_id;
    }

    public void setFrom_user_id(String from_user_id) {
        this.from_user_id = from_user_id;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
