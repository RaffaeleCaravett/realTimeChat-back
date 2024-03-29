package com.example.realTimeChat.user;


import com.example.realTimeChat.exception.BadRequestException;
import com.example.realTimeChat.exception.NotFoundException;
import com.example.realTimeChat.payloads.entities.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService utenteService;

    @GetMapping("/me")
    public UserDetails getProfile(@AuthenticationPrincipal UserDetails currentUser){
        return currentUser;
    };

    @DeleteMapping("/me")
    public boolean deleteProfile(@AuthenticationPrincipal User currentUser){
           return utenteService.findByIdAndDelete(currentUser.getId());
    };
    @GetMapping("/{id}")
    public User findById(@PathVariable int id)  {
        return utenteService.findById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User findByIdAndUpdate(@PathVariable int id, @RequestBody UserRegistrationDTO body) throws NotFoundException {
        return utenteService.findByIdAndUpdate(id, body);
    }
    @PutMapping("/me")
    public User findMeAndUpdate( @AuthenticationPrincipal User currentUser, @RequestBody UserRegistrationDTO body){
        return utenteService.findByIdAndUpdate(currentUser.getId(), body);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findByIdAndDelete(@PathVariable int id) throws NotFoundException {
        if(id != 1){
            utenteService.findByIdAndDelete(id);
        }else{
            throw new BadRequestException("Non puoi eliminare questo utente");
        }
    }

    @GetMapping("")
    public List<User> getUser(){
        return utenteService.getAll();
    }

}

