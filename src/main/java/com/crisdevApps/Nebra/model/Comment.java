package com.crisdevApps.Nebra.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String title;
    private int score;
    private String content;
    private LocalDateTime dateCreated;
    @ManyToOne
    private User author;
    @ManyToOne
    private Business business;
    private String answer;

}
