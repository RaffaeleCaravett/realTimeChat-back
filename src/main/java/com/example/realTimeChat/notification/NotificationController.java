package com.example.realTimeChat.notification;

import com.example.realTimeChat.exception.BadRequestException;
import com.example.realTimeChat.payloads.entities.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @PostMapping("")
    public Notification save(@RequestBody @Validated NotificationDTO notificationDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return notificationService.save(notificationDTO);
    }


    @GetMapping("")
public List<Notification> getAll(){
        return notificationService.getAll();
    }

    @PutMapping("/{id}")
    public Notification findByIdAndUpdate(@RequestBody NotificationDTO notificationDTO, @PathVariable long id){
        return notificationService.findByIdAndUpdate(id,notificationDTO);
    }

    @DeleteMapping("/{id}")
    public boolean findByIdAndDelete(@PathVariable long id){
        return notificationService.findByIdAndDelete(id);
    }
@GetMapping("/chat/{chat_id}")
    public List<Notification> findByChat_Id(@PathVariable long chat_id){
        return notificationService.findByChatId(chat_id);
}
@GetMapping("/chatAndStato")
    public List<Notification> findByChat_IdAndStatoNotifica(@RequestParam(defaultValue = "0") long chat_id,
                                                            @RequestParam(defaultValue = "NOT_SAW") String statoNotifica){
return notificationService.findByChatIdAndStatoNotifica(chat_id,statoNotifica);
}
}