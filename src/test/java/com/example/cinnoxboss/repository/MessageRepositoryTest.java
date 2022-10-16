package com.example.cinnoxboss.repository;

import com.example.cinnoxboss.model.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message textMessage;

    private Message stickerMessage;

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
    }

    @Test
    public void givenUserId_whenFindById_thenReturnListOfMessages() {

        messageRepository.insert(List.of(textMessage, stickerMessage));

        List<Message> messages = messageRepository.findMessageByUserId(textMessage.getUserId());

        assertThat(messages).isNotNull();
        assertThat(messages).isNotEmpty();
    }

    @Test
    public void givenMessageRequestBodyForm_whenSave_thenReturnMessage() {

        List<Message> messageList = messageRepository.insert(List.of(textMessage, stickerMessage));

        assertThat(messageList).isNotNull();
        assertThat(messageList).isNotEmpty();
        assertThat(messageList.size()).isEqualTo(2);
    }
}