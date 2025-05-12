package com.example.demo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "messages") // Укажите имя вашей таблицы в базе данных
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "message", columnDefinition = "TEXT")  // Используем TEXT для длинных сообщений
    private String message;

    @Column(name = "submit_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitDate;

    // Конструкторы (пустой и параметризованный)
    public Message() {
        this.submitDate = new Date(); // Устанавливаем текущую дату по умолчанию
    }

    public Message(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
        this.submitDate = new Date();
    }

    // Геттеры и сеттеры для всех полей
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getSubmitDate() { return submitDate; }
    public void setSubmitDate(Date submitDate) { this.submitDate = submitDate; }
}