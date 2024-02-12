package com.example.realTimeChat.user;


import com.example.realTimeChat.chat.Chat;
import com.example.realTimeChat.exception.NotFoundException;
import com.example.realTimeChat.payloads.entities.UserRegistrationDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository utenteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void deleteChat(Chat chat) {

        Query deletePartecipantsQuery = entityManager.createNativeQuery("DELETE FROM chat_partecipant WHERE chat_id = ?");
        deletePartecipantsQuery.setParameter(1, chat.getId());
        deletePartecipantsQuery.executeUpdate();

        entityManager.remove(entityManager.contains(chat) ? chat : entityManager.merge(chat));
    }

    public User findById(long id) throws NotFoundException {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public User findByIdAndUpdate(long id, UserRegistrationDTO body) throws NotFoundException {
        User found = utenteRepository.findById(id).get();
        found.setNome(body.nome());
        found.setEmail(body.email());
        found.setCognome(body.cognome());
        found.setEta(body.eta());
        return utenteRepository.save(found);
    }

    public boolean findByIdAndDelete(long id) throws NotFoundException {
        User found = utenteRepository.findById(id).get();

        for(Chat c: found.getChat_as_partecipant()){
            deleteChat(c);
        }
        try{
            utenteRepository.delete(found);
return true;
        }catch (Exception e){
            return false;
        }
    }

    public Optional<User> findByEmail(String email) throws NotFoundException {
        return utenteRepository.findByEmail(email);
    }
    public List<User> getAll(){
        return utenteRepository.findAll();
    }
}
