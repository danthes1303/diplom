package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*") // Разрешаем CORS для всех доменов (Временно, для разработки)
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/submit-form")
    public ResponseEntity<String> submitForm(@RequestBody Message message) {
        try {
            messageRepository.save(message);
            return new ResponseEntity<>("Сообщение успешно сохранено!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Ошибка при сохранении сообщения: ", e); // Логируем ошибку
            return new ResponseEntity<>("Ошибка при сохранении сообщения: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}