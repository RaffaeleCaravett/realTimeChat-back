package com.example.realTimeChat.messaggio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Messaggio,Long> {
    List<Messaggio> findAllBySender_Id(long sender_id);
    List<Messaggio> findAllByReceiver_Id(long receiver_id);
    List<Messaggio> findAllByChat_Id(long chat_id);

}
