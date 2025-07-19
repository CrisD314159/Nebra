package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.CreateReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetReportDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.ResponseMessage;
import com.crisdevApps.Nebra.security.UserDetailsImpl;
import com.crisdevApps.Nebra.services.interfaces.IReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final IReportService reportService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> createReport(
            @Valid @RequestBody CreateReportDTO createReportDTO,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        UUID userId = userDetails.getId();
        reportService.CreateReport(createReportDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "Report created"));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/accept/{id}")
    public ResponseEntity<ResponseMessage> acceptReport(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )  {
        UUID userId = userDetails.getId();
        reportService.AcceptReport(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Report accepted"));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @PutMapping("/reject/{id}")
    public ResponseEntity<ResponseMessage> rejectReport(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    )  {
        UUID userId = userDetails.getId();
        reportService.RejectReport(id, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Report accepted"));
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/pending?page={page}")
    public ResponseEntity<List<GetReportDTO>> getPendingReports(@PathVariable int page) throws Exception {
        List<GetReportDTO> reportDTOS = reportService.GetPendingReports(page);
        return ResponseEntity.ok(reportDTOS);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/accepted?page={page}")
    public ResponseEntity<List<GetReportDTO>> getAcceptedReports(@PathVariable int page) throws Exception {
        List<GetReportDTO> reportDTOS = reportService.GetAcceptedReports(page);
        return ResponseEntity.ok(reportDTOS);
    }

    @PreAuthorize("hasRole('MODERATOR')")
    @GetMapping("/rejected?page={page}")
    public ResponseEntity<List<GetReportDTO>> getRejectedReports(@PathVariable int page) throws Exception {
        List<GetReportDTO> reportDTOS = reportService.GetRejectedReports(page);
        return ResponseEntity.ok(reportDTOS);
    }

}
