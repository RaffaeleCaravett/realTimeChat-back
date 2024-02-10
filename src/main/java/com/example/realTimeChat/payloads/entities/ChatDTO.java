package com.example.realTimeChat.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChatDTO(
        @NotNull(message = "Almeno un messaggio nella chat")
        List<Long> messaggio_id,
        @NotNull(message = "lo starte_id deve essere presente")
        long starter_id,
        @NotNull(message = "Almeno un partecipante nella chat")
        List<Long> partecipants_id,
        String tipo_chat,
        String nome
) {
}
