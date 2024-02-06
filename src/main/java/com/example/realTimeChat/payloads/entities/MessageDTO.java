package com.example.realTimeChat.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record MessageDTO
        (@NotEmpty(message = "Il messaggio non può essere null")
    String messaggio,
    @NotNull(message = "Il chat_id non può essere null")
    long chat_id,
         @NotNull(message = "Sender_id necessario")
         long sender_id,
         @NotNull(message = "Receiver_id necessario")
         List<Long> receiver_id
         ){
}
