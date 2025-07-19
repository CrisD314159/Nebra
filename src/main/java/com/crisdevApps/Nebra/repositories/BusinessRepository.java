package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessCategory;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BusinessRepository extends JpaRepository<Business, UUID> {

    Optional<Business> findByIdAndUserOwner(UUID id, User owner);

    boolean existsByNameAndUserOwner(String name, User owner);


    Page<Business> findByCategoryAndBusinessState (BusinessCategory category, BusinessState state, Pageable pageable);

    Page<Business> findByBusinessStateAndUserOwner_Id(BusinessState businessState, UUID userOwnerId, Pageable pageable);


    Page<Business> findByNameIsLikeAndBusinessState(String name, BusinessState state, Pageable pageable);


    Page<Business> findByUserOwnerAndBusinessState(User owner, BusinessState state, Pageable pageable);


    Optional<Business> findByIdAndBusinessState(UUID id, BusinessState state);

    @Query(value = """
    SELECT * FROM business
    WHERE ST_DistanceSphere(location, ST_MakePoint(:lng, :lat)) < :maxDistance
    LIMIT 20
""", nativeQuery = true)
    List<Business> findNearbyBusinesses(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("maxDistance") double maxDistance
    );

    @Query(value = """
    SELECT b.*, AVG(c.score) AS average_score
    FROM business b
    JOIN comment c ON b.id = c.business_id
    GROUP BY b.id
    ORDER BY average_score DESC
    LIMIT 20
""", nativeQuery = true)
    List<Business> findTopBusinessesByScore();

}
