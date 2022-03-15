package com.chat.reactchat.service.util;

import com.chat.reactchat.dto.message.TextMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.Serializable;
import java.text.DateFormat;

@Service
public class JsonConverterUtil {
    private final ObjectMapper objectMapper;

    public JsonConverterUtil() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(DateFormat.getDateTimeInstance());
    }

    public TextMessageRequest parseTextMessage(TextMessage textMessage) throws JsonProcessingException {
        TextMessageRequest messageRequest = objectMapper.readValue(textMessage.getPayload(), TextMessageRequest.class);
        if (messageRequest.getRoom() == null || messageRequest.getMessage() == null) {
            throw new IllegalArgumentException();
        }
        return messageRequest;
    }

    public String convert(Object serializable) throws JsonProcessingException {
        return objectMapper.writeValueAsString(serializable);
    }
}
