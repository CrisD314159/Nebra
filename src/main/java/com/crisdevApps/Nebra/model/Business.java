package com.crisdevApps.Nebra.model;

import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class Business implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Image> images;

    private String description;

    private String name;

    private String phoneContact;

    private BusinessCategory category;

    private LocalDateTime dateCreated;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point location;

    @ManyToOne(fetch = FetchType.EAGER)
    private User userOwner;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Revision> revisionsList;

    private BusinessState businessState;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Schedule> scheduleList;

}
