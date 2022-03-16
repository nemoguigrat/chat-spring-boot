package com.chat.reactchat.service.util;

import com.chat.reactchat.dto.message.TextMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;

import java.io.Serializable;
import java.text.DateFormat;

@Component
public class JsonConverterUtils {
    private final ObjectMapper objectMapper;

    public JsonConverterUtils() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.setDateFormat(DateFormat.getDateTimeInstance());
    }

    public <T> T parseTextMessage(TextMessage textMessage, Class<T> objectType) throws JsonProcessingException {
        // TODO обработчик ошибок, для не корректных сообщений
        return objectMapper.readValue(textMessage.getPayload(), objectType);

    }

    public String convert(Object serializable) throws JsonProcessingException {
        return objectMapper.writeValueAsString(serializable);
    }
}
