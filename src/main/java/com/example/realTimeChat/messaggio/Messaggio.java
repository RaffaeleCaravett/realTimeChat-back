package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.chat.Chat;
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
    @ManyToMany(mappedBy = "messaggio_as_sender",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "messaggio_user",
            joinColumns = @JoinColumn(name = "messaggio_id"),
            foreignKey = @ForeignKey(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            inverseForeignKey = @ForeignKey(name = "messaggio_id"))
    private List<User> sender;
    @ManyToMany(mappedBy = "messaggio_as_receiver",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinTable(name = "messaggio_user",
            joinColumns = @JoinColumn(name = "messaggio_id"),
            foreignKey = @ForeignKey(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            inverseForeignKey = @ForeignKey(name = "messaggio_id"))
    private List<User> receiver;
}
