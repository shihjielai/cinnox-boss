package com.example.cinnoxboss.service;

import com.example.cinnoxboss.dto.MessageRequestBodyForm;
import com.example.cinnoxboss.model.Message;
import com.example.cinnoxboss.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private MessageService messageService;

    private Message textMessage;

    private Message stickerMessage;

    private MessageRequestBodyForm messageRequestBodyForm;

    @BeforeEach
    public void setUp() {

        textMessage = new Message();
        textMessage.setType("text");
        textMessage.setText("hello");

        stickerMessage = new Message();
        stickerMessage.setType("sticker");
        stickerMessage.setPackageId("1");
        stickerMessage.setPackageId("2");

        messageRequestBodyForm = new MessageRequestBodyForm();
        messageRequestBodyForm.setUserId("U6f2850c6d7bc0b00cfc3cd1708901f9b");
        messageRequestBodyForm.setMessages(List.of(textMessage, stickerMessage));
    }

    @Test
    public void givenUserId_whenGetById_thenReturnMessageListOfUser() {

        when(messageRepository.findMessageByUserId(messageRequestBodyForm.getUserId()))
                .thenReturn(List.of(textMessage, stickerMessage));

        List<Message> messages = messageService.getMessagesByUserId(messageRequestBodyForm.getUserId());
        assertThat(messages).isNotNull();
        assertThat(messages.size()).isGreaterThan(1);
    }

    @Test
    public void givenMessageRequestBodyForm_whenSend_thenReturnMessage() {

        String url = "https://api.line.me/v2/bot/message/push";

        RestTemplate restTemplate = mock(RestTemplate.class);

        HttpEntity<MessageRequestBodyForm> httpRequestEntity = new HttpEntity<>(messageRequestBodyForm);

        List<Message> messageList = List.of(textMessage, stickerMessage);

        ResponseEntity<List<Message>> mockResponse = new ResponseEntity<>(messageList, HttpStatus.OK);

        when(restTemplate.exchange(anyString(), any(), any(), any(ParameterizedTypeReference.class), any(Object[].class)))
                .thenReturn(mockResponse);

        ResponseEntity<List<Message>> exchange = restTemplate.exchange(url, HttpMethod.POST, httpRequestEntity, new ParameterizedTypeReference<>() {});

        assertEquals(messageList, exchange.getBody());
    }
}