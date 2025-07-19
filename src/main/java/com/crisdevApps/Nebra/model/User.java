package com.crisdevApps.Nebra.model;

import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
@ToString
@Builder
@Getter
@Setter
public class User  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Image profilePicture;

    private String location;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Business> businessList;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Report> reports;

    private UserState userState;

    private UserRole userRole;

    private boolean isThirdPartyUser;

    private String verificationCode;

    private String recoveryAccountToken;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Business> favoriteBusiness;

}
