package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.model.enums.UserState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {


    Optional<User> findByEmail(String email); // Si especificamo el metodo bien no necesitamos el query

    Page<User> findByNameIsLikeAndUserState(String name, UserState userState, Pageable pageable);

}
