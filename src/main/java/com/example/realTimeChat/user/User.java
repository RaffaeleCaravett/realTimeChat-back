package com.example.realTimeChat.user;


import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.enums.Role;
import com.example.realTimeChat.messaggio.Messaggio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String cognome;
    private String email;
    private String password;
    private int eta;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy="starter",fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Chat> chat_as_starter;
    @ManyToMany(mappedBy = "partecipants",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Chat> chat_as_partecipant;
    @OneToMany(mappedBy = "sender",fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Messaggio> messaggio_as_sender;
    @ManyToMany(mappedBy = "receiver",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Messaggio> messaggio_as_receiver;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
