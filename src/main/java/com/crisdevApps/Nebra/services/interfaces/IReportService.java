package com.crisdevApps.Nebra.services.interfaces;

import com.crisdevApps.Nebra.dto.inputDto.CreateReportDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Report;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.model.enums.ReportState;
import com.crisdevApps.Nebra.model.enums.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IReportService {

    void CreateReport(CreateReportDTO createReportDTO, UUID authorId);

    void AcceptReport(UUID reportId, UUID userId);

    void RejectReport(UUID reportId, UUID userId);

    List<GetReportDTO> GetPendingReports(int page);

    List<GetReportDTO> GetRejectedReports(int page);


    List<GetReportDTO> GetAcceptedReports(int page);
}
