package com.example.cinnoxboss.controller;

import com.example.cinnoxboss.dto.MessageRequestBodyForm;
import com.example.cinnoxboss.model.Message;
import com.example.cinnoxboss.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;

    private Message textMessage;

    private Message stickerMessage;

    private MessageRequestBodyForm messageRequestBodyForm;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() {

        textMessage = new Message();
        textMessage.setType("text");
        textMessage.setText("hello");
        textMessage.setUserId("U6f2850c6d7bc0b00cfc3cd1708901f9b");

        stickerMessage = new Message();
        stickerMessage.setType("sticker");
        stickerMessage.setPackageId("1");
        stickerMessage.setPackageId("2");
        stickerMessage.setUserId("U6f2850c6d7bc0b00cfc3cd1708901f9b");

        messageRequestBodyForm = new MessageRequestBodyForm();
        messageRequestBodyForm.setUserId("U6f2850c6d7bc0b00cfc3cd1708901f9b");
        messageRequestBodyForm.setMessages(List.of(textMessage, stickerMessage));
    }

    @Test
    public void givenUserId_whenFindById_thenReturnListOfMessage() throws Exception {

        String userId = "U6f2850c6d7bc0b00cfc3cd1708901f9b";

        BDDMockito.given(messageService.getMessagesByUserId(userId))
                .willReturn(List.of(textMessage, stickerMessage));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/messages/users/{userId}", userId));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }

    @Test
    public void givenMessageRequestBodyForm_whenSend_thenReturnMessage() throws Exception {

        BDDMockito.given(messageService.sendMessage(ArgumentMatchers.any(MessageRequestBodyForm.class)))
                .willReturn(List.of(textMessage, stickerMessage));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/messages/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(messageRequestBodyForm)));

        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(2)));
    }
}