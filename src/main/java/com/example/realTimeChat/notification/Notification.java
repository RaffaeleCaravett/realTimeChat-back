package com.example.realTimeChat.notification;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.enums.StatoNotifica;
import com.example.realTimeChat.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.repository.cdi.Eager;

import java.util.List;

@Entity
@Table(name = "notifications")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    private String testo;
    @Enumerated(EnumType.STRING)
    private StatoNotifica statoNotifica;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;
}
