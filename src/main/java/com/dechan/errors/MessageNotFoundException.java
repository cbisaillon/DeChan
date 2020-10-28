package com.dechan.errors;

public class MessageNotFoundException extends Exception {
    public MessageNotFoundException(){
        super("Message with specified hash does not exist.");
    }
}
