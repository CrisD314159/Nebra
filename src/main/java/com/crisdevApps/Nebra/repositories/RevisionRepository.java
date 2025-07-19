package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RevisionRepository extends JpaRepository<Revision, UUID> {
}
