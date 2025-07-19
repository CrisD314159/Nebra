package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.LoginDTO;
import com.crisdevApps.Nebra.dto.outputDto.ErrorMessage;
import com.crisdevApps.Nebra.dto.outputDto.ResponseMessage;
import com.crisdevApps.Nebra.dto.outputDto.TokenDTO;
import com.crisdevApps.Nebra.services.interfaces.IAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        TokenDTO tokenDTO = authService.Login(loginDTO);
        return ResponseEntity.ok().body(tokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestBody String refreshToken){
        authService.Logout(refreshToken);
        return ResponseEntity.ok().body(new ResponseMessage(true, "Logged oud successfully"));
    }

    @PutMapping("/refresh")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody String refresh){
        TokenDTO tokens = authService.RefreshToken(refresh);
        return ResponseEntity.ok(tokens);
    }


}
