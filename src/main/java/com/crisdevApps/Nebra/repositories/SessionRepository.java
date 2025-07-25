package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
}
