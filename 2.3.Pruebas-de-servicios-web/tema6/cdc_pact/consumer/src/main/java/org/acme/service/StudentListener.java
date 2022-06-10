package org.acme.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;

import org.acme.model.StudentMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class StudentListener {

    private static final Logger log = LoggerFactory.getLogger(StudentListener.class);

    private ObjectMapper objectMapper;

    public StudentListener() {
        this.objectMapper = new ObjectMapper();
    }

    public void consumeMessage(String messageString) throws IOException {
        StudentMessage message = objectMapper.readValue(messageString, StudentMessage.class);
        log.info("message consumer {}", message);
    }
}