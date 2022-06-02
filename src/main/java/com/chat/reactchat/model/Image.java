package com.chat.reactchat.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filename")
    private String filename;

    private LocalDateTime dateCreation;

    public Image() {
        this.dateCreation = LocalDateTime.now();
    }

    public Image(String filename) {
        this();
        this.filename = filename;
    }

    public String getFilename() {
        return "/api/downloadFile/" + filename;
    }
}
