package com.crisdevApps.Nebra.repositories;

import com.crisdevApps.Nebra.model.Report;
import com.crisdevApps.Nebra.model.enums.ReportState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {


    Optional<Report> findByIdAndReportState(UUID id, ReportState reportState);


    Page<Report> findByReportState(ReportState reportState, Pageable pageable);


}
