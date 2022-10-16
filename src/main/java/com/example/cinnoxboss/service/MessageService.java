package com.example.cinnoxboss.service;

import com.example.cinnoxboss.dto.MessageRequestBodyForm;
import com.example.cinnoxboss.errorHandle.eunm.ErrorCodeEnum;
import com.example.cinnoxboss.errorHandle.exception.ErrorCodeException;
import com.example.cinnoxboss.model.Message;
import com.example.cinnoxboss.repository.MessageRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class MessageService {

    @Value("${line.bot.channel-token}")
    private String token;

    @Value("${line.bot.push.url}")
    private String lineUrl;

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> sendMessage(MessageRequestBodyForm messageRequestBodyForm) {

        try {

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String userId = messageRequestBodyForm.getUserId();

            HttpEntity request = new HttpEntity(messageRequestBodyForm, headers);
            restTemplate.exchange(lineUrl, HttpMethod.POST, request, String.class);

            List<Message> messageList = new ArrayList<>();

            for (Message message : messageRequestBodyForm.getMessages()) {

                String type = message.getType();
                String text = message.getText();
                String packageId = message.getPackageId();
                String stickerId = message.getStickerId();

                if (StringUtils.isNoneBlank(type) && "text".equals(type)) {
                    Message textMessage = new Message();
                    textMessage.setUserId(userId);
                    textMessage.setType(type);
                    textMessage.setText(text);
                    messageList.add(textMessage);
                } else if (StringUtils.isNoneBlank(type) && "sticker".equals(type)) {
                    Message stickerMessage = new Message();
                    stickerMessage.setUserId(userId);
                    stickerMessage.setType(type);
                    stickerMessage.setPackageId(packageId);
                    stickerMessage.setStickerId(stickerId);
                    messageList.add(stickerMessage);
                }
            }

            messageRepository.saveAll(messageList);

            return messageList;

        } catch (Exception e) {
            log.error("", e);
            log.error("Line發送訊息異常");
            throw new ErrorCodeException("Line發送訊息異常", ErrorCodeEnum.LINE_SEND_MESSAGE_ERROR);
        }
    }
}
