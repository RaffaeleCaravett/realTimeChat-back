package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.exception.BadRequestException;
import com.example.realTimeChat.exception.NotFoundException;
import com.example.realTimeChat.payloads.entities.ChatDTO;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.slf4j.Logger;
@RestController
@RequestMapping("/messaggio")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Messaggio findById(@PathVariable long id)  {
        return messageService.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable long id) throws NotFoundException {
        if(messageRepository.findById(id).isPresent()){
            messageService.findByIdAndDelete(id);
        }else{
            throw new BadRequestException("Il messaggio non esiste!");
        }

    }
    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Messaggio saveMessage(@RequestBody @Validated MessageDTO body, BindingResult validation)  {
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {return  messageService.saveMessage(body);

        }
    }
    @GetMapping(value = "/chat/{chatId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Messaggio> findByChatId(@PathVariable long chatId)  {
        return messageService.findByChatId(chatId);
    }

    @GetMapping(value = "/sender/{senderId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Messaggio> findBySenderId(@PathVariable long senderId)  {
        return messageService.findByChatId(senderId);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public long findByIdAndUpdate(@PathVariable long id, @RequestBody MessageDTO body) throws NotFoundException {
        if(messageRepository.findById(id).isPresent()){
            return messageService.findByIdAndUpdate(id, body);
        }
        else{
            throw new BadRequestException("Il messaggio non è stato trovato!");
        }
    }
}
