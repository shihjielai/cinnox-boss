package com.example.cinnoxboss.controller;

import com.example.cinnoxboss.dto.MessageRequestBodyForm;
import com.example.cinnoxboss.model.Message;
import com.example.cinnoxboss.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/messages")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Message> sendMessage(@RequestBody MessageRequestBodyForm messageRequestBodyForm) {
        return messageService.sendMessage(messageRequestBodyForm);
    }

    @GetMapping("/users/{userId}")
    public List<Message> getMessagesByUserId(@PathVariable String userId) {
        return messageService.getMessagesByUserId(userId);
    }
}
