package com.chat.reactchat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "rooms")
@ToString
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @JsonIgnore
    @ManyToMany(mappedBy = "rooms")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "room", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    private Set<ChatMessage> messages = new HashSet<>();

    public ChatRoom(String name, RoomType type){
        this.name = name;
        this.roomType = type;
    }
}
