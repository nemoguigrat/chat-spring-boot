package com.chat.reactchat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "messages")
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom room;

    private LocalDateTime dateCreation;

    @PrePersist
    private void init() {
        dateCreation = LocalDateTime.now();
    }

    public ChatMessage(String message, User sender, ChatRoom room){
        this.message = message;
        this.sender = sender;
        this.room = room;
    }
}
