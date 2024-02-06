package com.example.realTimeChat.messaggio;

import com.example.realTimeChat.exception.BadRequestException;
import com.example.realTimeChat.exception.NotFoundException;
import com.example.realTimeChat.payloads.entities.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public long saveMessage(@RequestBody @Validated MessageDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return  messageService.saveMessage(body);
        }
    }
    @GetMapping(value = "/chat/{chatId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Messaggio> findByChatId(@PathVariable long chatId)  {
        return messageService.findByChatId(chatId);
    }
}
