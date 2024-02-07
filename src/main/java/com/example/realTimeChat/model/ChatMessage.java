package com.example.realTimeChat.model;

import com.example.realTimeChat.enums.MessageType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {
    private String content;
    private String sender;
    private MessageType type;

}
