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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom room;

    private LocalDateTime dateCreation;

    public ChatMessage(String message, User sender, ChatRoom room){
        this.dateCreation = LocalDateTime.now();
        this.message = message;
        this.user = sender;
        this.room = room;
    }
}
