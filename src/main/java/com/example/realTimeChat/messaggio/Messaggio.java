package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.enums.MessageState;
import com.example.realTimeChat.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "messaggi")
@Getter
@Setter
public class Messaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    private LocalDate date_at;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "chat_id")
    @JsonIgnore
    private Chat chat;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "messaggio_id")
    private User sender;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "messaggio_receiver",
            joinColumns = @JoinColumn(name = "messaggio_id"),
            foreignKey = @ForeignKey(name = "receiver_id"),
            inverseJoinColumns = @JoinColumn(name = "receiver_id"),
            inverseForeignKey = @ForeignKey(name = "messaggio_id"))
    private List<User> receiver;
    @Enumerated(EnumType.STRING)
    private MessageState messageState;
}
