package com.example.realTimeChat.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record NotificationDTO(
        @NotNull(message = "Sender_id necessario")
        long sender_id,
        @NotNull(message = "Almeno un receiver")
        List<Long> receiver_id,
        @NotEmpty(message = "Testo necessario")
        String testo,
        @NotEmpty(message = "Stato notifica necessario")
        String statoNotifica,
        @NotNull(message = "Chat_id necessario")
        long chat_id
        ) {
}
