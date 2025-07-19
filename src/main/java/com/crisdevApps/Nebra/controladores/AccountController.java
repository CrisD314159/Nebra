package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.ChangePasswordDTO;
import com.crisdevApps.Nebra.dto.inputDto.VerifyAccountDTO;
import com.crisdevApps.Nebra.dto.outputDto.ResponseMessage;
import com.crisdevApps.Nebra.security.UserDetailsImpl;
import com.crisdevApps.Nebra.services.interfaces.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @DeleteMapping()
    public ResponseEntity<ResponseMessage> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UUID userId = userDetails.getId();
        accountService.DeleteAccount(userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Account successfully deleted"));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<ResponseMessage> changePassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO){
        accountService.ChangePassword(changePasswordDTO);
        return ResponseEntity.ok(new ResponseMessage(true, "Password changed"));
    }

    @PostMapping("/sendRecoveryLink")
    public ResponseEntity<ResponseMessage> sendRecoveryLink( @RequestBody String email){
        accountService.SendRecoveryLink(email);
        return ResponseEntity.ok(new ResponseMessage(true, "E-mail sent"));
    }

    @PutMapping("/verifyAccount")
    public ResponseEntity<ResponseMessage> verifyAccount(@Valid @RequestBody VerifyAccountDTO verifyAccountDTO){
        accountService.VerifyAccount(verifyAccountDTO);
        return ResponseEntity.ok(new ResponseMessage(true, "Account verified"));
    }


}
