package pl.mazi.ledapp.model;


import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MessageInfoModel {
    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    public enum MessageTyp{
        MESSAGE_RECEIVER,
        MESSAGE_SENDER,
        MESSAGE_ERROR
    }

    private String message;
    private MessageTyp messageTyp;
    // Message time
    private String messageTime;

    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTORS
    /////////////////////////////////////////////////////////////////
    @SuppressLint("SimpleDateFormat")
    public MessageInfoModel(String message, MessageTyp messageTyp) {
        this.message = message;
        this.messageTyp = messageTyp;
        messageTime = new SimpleDateFormat("E HH:mm:ss").format(new Date());
    }

    /////////////////////////////////////////////////////////////////
    //// GETTERS
    /////////////////////////////////////////////////////////////////

    public String getMessage() {
        return message;
    }

    public MessageTyp getMessageTyp() {
        return messageTyp;
    }

    public String getMessageTime() {
        return messageTime;
    }
}
