package com.example.realTimeChat.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findAllByStarter_Id(long id);
    List<Chat> findAllByPartecipants_Id(long id);

}
