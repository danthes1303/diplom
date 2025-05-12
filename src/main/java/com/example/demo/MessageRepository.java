package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    // Здесь можно добавить дополнительные методы для запросов к базе данных,
    // если они вам понадобятся.  Например, поиск сообщений по email.
}