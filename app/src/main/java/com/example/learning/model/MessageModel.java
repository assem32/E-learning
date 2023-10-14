package com.example.learning.model;

public class MessageModel {
    String senderName;

    String senderId;

    String message;

    String date;


    public  MessageModel(){}

    public MessageModel(String senderName, String senderId, String message, String date) {
        this.senderName = senderName;
        this.senderId = senderId;
        this.message = message;
        this.date = date;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
