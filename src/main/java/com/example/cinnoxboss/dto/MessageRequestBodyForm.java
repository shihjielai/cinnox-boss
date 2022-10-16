package com.example.cinnoxboss.dto;

import com.example.cinnoxboss.model.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MessageRequestBodyForm {

    @JsonProperty("to")
    private String userId;

    @JsonProperty("messages")
    private List<Message> messages;
}
