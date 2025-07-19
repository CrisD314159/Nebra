package com.crisdevApps.Nebra.model;

import com.crisdevApps.Nebra.model.enums.ReportState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business business;
    @Column(nullable = false)
    private String reason;
    private ReportState reportState;
    private String answer;
}
