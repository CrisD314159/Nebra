package com.crisdevApps.Nebra.model;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Builder
public class Image {
    @Id
    private String id;
    private String link;

}
