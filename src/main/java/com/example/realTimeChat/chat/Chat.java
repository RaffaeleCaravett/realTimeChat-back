package com.example.realTimeChat.chat;

import com.example.realTimeChat.enums.TipoChat;
import com.example.realTimeChat.messaggio.Messaggio;
import com.example.realTimeChat.notification.Notification;
import com.example.realTimeChat.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name="chats")
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(mappedBy = "chat",fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Messaggio> messaggio;
    @ManyToOne
    @JoinColumn(name="starter_id")
    private User starter;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "chat_partecipant",
            joinColumns = @JoinColumn(name = "chat_id"),
            foreignKey = @ForeignKey(name = "partecipant_id"),
            inverseJoinColumns = @JoinColumn(name = "partecipant_id"),
            inverseForeignKey = @ForeignKey(name = "chat_id"))
    private List<User> partecipants;
    @Enumerated(EnumType.STRING)
    private TipoChat tipoChat;
    @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Notification> notifications;
    private String nome;
}
