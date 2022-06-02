package com.chat.reactchat.dto.user;

import com.chat.reactchat.model.Image;
import com.chat.reactchat.model.Role;
import com.chat.reactchat.model.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private Boolean active;
    private String firstName;
    private String secondName;
    private Set<Role> role;
    private Image image;

    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.secondName = user.getSecondName();
        this.active = user.getActive();
        this.email = user.getEmail();
        this.image = user.getImage();
        this.role = user.getRole();
    }
}