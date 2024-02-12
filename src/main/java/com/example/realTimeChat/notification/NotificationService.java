package com.example.realTimeChat.notification;

import com.example.realTimeChat.chat.ChatService;
import com.example.realTimeChat.enums.StatoNotifica;
import com.example.realTimeChat.payloads.entities.NotificationDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService{


    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;
    public Notification save(NotificationDTO notificationDTO){
        Notification notification = new Notification();

        List<User> receivers = new ArrayList<>();
        for(Long l : notificationDTO.receiver_id()){
            receivers.add(userService.findById(l));
        }
        notification.setTesto(notificationDTO.testo());
        notification.setSender(userService.findById(notificationDTO.sender_id()));
        notification.setChat(chatService.findById(notificationDTO.chat_id()));
        notification.setStatoNotifica(StatoNotifica.valueOf(notificationDTO.statoNotifica()));
        notification.setReceiver(receivers.get(0));
        return notificationRepository.save(notification);
    }
    public Notification findByIdAndUpdate(long id,NotificationDTO notificationDTO){
        Notification notification = notificationRepository.findById(id).get();

        User receiver = userService.findById(notificationDTO.receiver_id().get(0));

        notification.setTesto(notificationDTO.testo());
        notification.setSender(userService.findById(notificationDTO.sender_id()));
        notification.setChat(chatService.findById(notificationDTO.chat_id()));
        notification.setStatoNotifica(StatoNotifica.valueOf(notificationDTO.statoNotifica()));
        notification.setReceiver(receiver);
        return notificationRepository.save(notification);
    }

    public boolean findByIdAndDelete(long id){
        try{
            notificationRepository.deleteById(id);
        return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Notification> getAll(){
        return notificationRepository.findAll();
    }

    public List<Notification> findByChatId(long chatId){
        return notificationRepository.findByChat_Id(chatId);
    }
    public List<Notification> findByChatIdAndStatoNotifica(long chatId,String statoNotifica){
        StatoNotifica statoNotifica1 = StatoNotifica.valueOf(statoNotifica);
        return notificationRepository.findByChat_IdAndStatoNotifica(chatId,statoNotifica1);
    }

}
