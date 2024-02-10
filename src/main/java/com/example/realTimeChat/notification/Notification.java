package com.example.realTimeChat.notification;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.enums.StatoNotifica;
import com.example.realTimeChat.user.User;
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
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "sender_id")
    private User sender;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name="notification_receiver",
            joinColumns = @JoinColumn(name = "notification_id"),
            foreignKey = @ForeignKey(name = "receiver_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id"),
            inverseForeignKey = @ForeignKey(name = "notification_id"))
    private List<User> receiver;
    private String testo;
    @Enumerated(EnumType.STRING)
    private StatoNotifica statoNotifica;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "chat_id")
    private Chat chat;
}
