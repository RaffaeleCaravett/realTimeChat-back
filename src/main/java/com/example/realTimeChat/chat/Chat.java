package com.example.realTimeChat.chat;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="chats")
@Getter
@Setter
public class Chat {
}
