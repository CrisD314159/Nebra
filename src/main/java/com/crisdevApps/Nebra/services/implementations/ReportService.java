package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.CreateReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.ReportMapper;
import com.crisdevApps.Nebra.model.Report;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.model.enums.ReportState;
import com.crisdevApps.Nebra.model.enums.UserRole;
import com.crisdevApps.Nebra.services.interfaces.IBusinessService;
import com.crisdevApps.Nebra.services.interfaces.IReportService;
import com.crisdevApps.Nebra.repositories.ReportRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService implements IReportService {

    private final ReportRepository reportRepository;
    private final IBusinessService businessService;
    private final EmailService emailService;
    private final ReportMapper reportMapper;
    private final IUserService userService;
    @Override
    public void CreateReport(CreateReportDTO createReportDTO, UUID authorId) {
        Business business = businessService.GetValidBusiness(createReportDTO.businessId());
        User user = userService.FindValidUserById(authorId);
        Report report = Report.builder()
                .reportState(ReportState.PENDING)
                .answer("")
                .business(business)
                .user(user)
                .reason(createReportDTO.reason())
                .build();
        reportRepository.save(report);
    }

    @Override
    public void AcceptReport(UUID reportId, UUID userId) {
        Report report =  getValidReport(reportId, userId);;

        Business reportBusiness = report.getBusiness();
        if(reportBusiness.getBusinessState() != BusinessState.ACTIVE)
            throw new ValidationException("Business not found");

        reportBusiness.setBusinessState(BusinessState.ACTIVE);
        report.setReportState(ReportState.ACCEPTED);

        reportRepository.save(report);

        emailService.SendEmail(new EmailDTO(
                "Your report has been accepted",
                "After reviewing your report, we decided to remove this business from our platform does not meet the required standards of accuracy, trust, and user safety.\n" + "Sincerely, Nebra team.",
                report.getUser().getEmail(),
                report.getUser().getName(),
                "",
                "Go to Nebra"
        ), "templates/generalEmailTemplate.html", false);

        emailService.SendEmail(new EmailDTO(
                "Your business has been removed from our platform",
                "After reviewing other users reports about your business, we decided to remove it from our platform. Your business does not meet the required standards of accuracy, trust, and user safety.\n" + "Sincerely, Nebra team.",
                reportBusiness.getUserOwner().getEmail(),
                reportBusiness.getUserOwner().getName(),
                "",
                "Go to Nebra"
        ), "templates/generalEmailTemplate.html", false);
    }

    @Override
    public void RejectReport(UUID reportId, UUID userId){

        Report report =  getValidReport(reportId, userId);;

        report.setReportState(ReportState.REJECTED);

        reportRepository.save(report);

        emailService.SendEmail(new EmailDTO(
                "You report has been rejected",
                "After reviewing your report, we decided to NOT remove this business from our platform. This business meets the required standards of accuracy, trust, and user safety.\n" + "Sincerely, Nebra team.",
                report.getUser().getEmail(),
                report.getUser().getName(),
                "",
                "Go to Nebra"
        ), "templates/generalEmailTemplate.html", false);
    }

    private Report getValidReport(UUID reportId, UUID userId) {
        User user = userService.FindValidUserById(userId);
        if(user.getUserRole().equals(UserRole.USER))
            throw new ValidationException("You are not authorized to execute this action");

        Optional<Report> reportOptional = reportRepository.findByIdAndReportState(reportId, ReportState.PENDING);
        if (reportOptional.isEmpty()){
            throw new EntityNotFoundException("Report not found");
        }

        return reportOptional.get();
    }

    @Override
    public List<GetReportDTO> GetPendingReports(int page) {
        return GetReportsByIdAndState(page, ReportState.PENDING);
    }

    @Override
    public List<GetReportDTO> GetRejectedReports(int page) {
        return GetReportsByIdAndState(page, ReportState.REJECTED);

    }

    @Override
    public List<GetReportDTO> GetAcceptedReports(int page){
        return GetReportsByIdAndState(page, ReportState.ACCEPTED);

    }

    public List<GetReportDTO> GetReportsByIdAndState(int page, ReportState reportState){
        Pageable pageable = PageRequest.of(page, 10);
        Page<Report> reports = reportRepository.findByReportState(reportState, pageable);
        return reports.stream().map(reportMapper::toDto).toList();
    }

}
