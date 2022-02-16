package com.chat.reactchat.model;

import com.chat.reactchat.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(unique = true, name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "rooms")
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "users_rooms",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "room_id")
    )
    private Set<ChatRoom> rooms = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender")
    private Set<ChatMessage> messages;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> role = new HashSet<>();

    public User(String email, String firstName, String secondName) {
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.active = true;
    }

    public void addUserInRoom(ChatRoom room){
        this.rooms.add(room);
        room.getUsers().add(this);
    }
}