package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
