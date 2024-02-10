package com.example.realTimeChat.chat;

import com.example.realTimeChat.exception.BadRequestException;
import com.example.realTimeChat.exception.NotFoundException;
import com.example.realTimeChat.payloads.entities.ChatDTO;
import com.example.realTimeChat.user.User;
import com.example.realTimeChat.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public Chat findById(@PathVariable int id)  {
        return chatService.findById(id);
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAuthority('USER')")
    public List<Chat> findAll()  {
        return chatService.findAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public long findByIdAndUpdate(@PathVariable long id, @RequestBody ChatDTO body) throws NotFoundException {
        if(chatRepository.findById(id).isPresent()){
            return chatService.findByIdAndUpdate(id, body);
        }
        else{
            throw new BadRequestException("La chat non è stata trovata!");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public boolean findByIdAndDelete(@PathVariable long id) throws NotFoundException {
        if(chatRepository.findById(id).isPresent()){
            return chatService.findByIdAndDelete(id);
        }else{
            throw new BadRequestException("La chat non esiste!");
        }

    }
    @PostMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public Chat saveChat(@RequestBody @Validated ChatDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
return chatService.save(body);
        }
    }
    @PostMapping("/group")
    @PreAuthorize("hasAuthority('USER')")
    public Chat saveGroupChat(@RequestBody @Validated ChatDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            return chatService.saveGroupChat(body);
        }
    }
    @GetMapping(value = "/starter/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Chat> findByUserId(@PathVariable long userId)  {
        return chatService.findByStarterId(userId);
    }

    @GetMapping(value = "/partecipant/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public List<Chat> findByPartecipantId(@PathVariable long userId)  {
        return chatService.findByPartecipantId(userId);
    }
}
