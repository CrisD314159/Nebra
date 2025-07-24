package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Comment;
import com.crisdevApps.Nebra.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    Page<Comment> findByBusiness(Business business, Pageable pageable);

    @Query("select c.score from Comment c where c.business.id = :id")
    List<Integer> getAllScoresById (@Param("id") UUID id);

    Page<Comment> findAllByBusiness_UserOwnerAndAnswered(User businessUserOwner, boolean answered, Pageable pageable);
}
