package com.example.realTimeChat.chat;

import com.example.realTimeChat.enums.TipoChat;
import com.example.realTimeChat.messaggio.MessageRepository;
import com.example.realTimeChat.messaggio.Messaggio;
import com.example.realTimeChat.payloads.entities.ChatDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    @Autowired
    ChatRepository chatRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    public Chat save(ChatDTO body){
        if(body.tipo_chat().equals("SINGOLA"))
        {
            List<Chat> chatList = this.findByStarterId(body.starter_id());
            if(!chatList.isEmpty()){
                for(Chat c : chatList){
                    for(User u: c.getPartecipants()){
                        if(u.getId()== body.partecipants_id().get(0)){
                            return null;
                        }
                    }
                }
            }
            List<Chat> chatList1 = this.findByStarterId(body.partecipants_id().get(0));
            if(!chatList.isEmpty()){
                for(Chat c : chatList){
                    for(User u: c.getPartecipants()){
                        if(u.getId()== body.starter_id()){
                            return null;
                        }
                    }
                }
            }
        }

        Chat chat= new Chat();
        chat.setStarter(userRepository.findById(body.starter_id()).get());
        List<User> users = new ArrayList<>();
        for(Long l : body.partecipants_id()){
            users.add(userRepository.findById(l).get());
        }
        chat.setPartecipants(users);
        chat.setTipoChat(TipoChat.SINGOLA);
        return chatRepository.save(chat);
    }

    public Chat saveGroupChat(ChatDTO chatDTO){
        if(chatDTO.partecipants_id().size()<=1){
            return this.save(chatDTO);
        }
            Chat chat = new Chat();

            chat.setNotifications(new ArrayList<>());
            chat.setTipoChat(TipoChat.DI_GRUPPO);
            chat.setStarter(userRepository.findById(chatDTO.starter_id()).get());
            List<User> partecipants = new ArrayList<>();
            for(Long l : chatDTO.partecipants_id()){
                partecipants.add(userRepository.findById(l).get());
            }
            chat.setPartecipants(partecipants);

            chat.setMessaggio(new ArrayList<>());
        return chatRepository.save(chat);
    }
    public long findByIdAndUpdate(long id,ChatDTO chatDTO){
        List<Messaggio> messageList=new ArrayList<>();
       for(Long l: chatDTO.messaggio_id()){
           messageList.add((messageRepository.findById(l)).get());
       }
        Chat chat = chatRepository.findById(id).get();
        chat.setMessaggio(messageList);
        chatRepository.save(chat);
        return chat.getId();
    }
    public List<Chat> findByStarterId(long userId){
        return chatRepository.findAllByStarter_Id(userId);
    }
    public List<Chat> findByPartecipantId(long userId){
        return chatRepository.findAllByPartecipants_Id(userId);
    }
    public boolean findByIdAndDelete(long id){
       try{
           chatRepository.deleteById(id);
           return true;
       }catch (Exception e){
           return false;
       }
    }
    public Chat findById(long id){
        return chatRepository.findById(id).get();
    }

    public List<Chat> findAll(){
        return chatRepository.findAll();
    }

}
