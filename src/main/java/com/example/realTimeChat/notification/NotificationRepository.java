package com.example.realTimeChat.notification;

import com.example.realTimeChat.enums.StatoNotifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {

    List<Notification> findByReceiver_Id(long receiver_id);
    List<Notification> findByChat_Id(long chat_id);
    List<Notification> findByChat_IdAndStatoNotifica(long chatId, StatoNotifica statoNotifica);
    Optional<Notification> findByChat_IdAndReceiver_IdAndSender_Id(long chat_id,long receiver_id,long sender_id);


}
