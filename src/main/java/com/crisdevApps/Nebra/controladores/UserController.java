package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.CreateUserDTO;
import com.crisdevApps.Nebra.dto.inputDto.UpdateUserDTO;
import com.crisdevApps.Nebra.dto.outputDto.*;
import com.crisdevApps.Nebra.security.UserDetailsImpl;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createUser(@Valid @ModelAttribute CreateUserDTO createUserDTO){
        userService.SignUp(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "User successfully updated"));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseMessage> updateUser(@Valid @RequestBody UpdateUserDTO updateUserDTO){
        userService.EditProfile(updateUserDTO);
        return ResponseEntity.ok(new ResponseMessage(true, "User successfully updated"));
    }

    @GetMapping()
    public ResponseEntity<GetUserProfileDTO> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
        UUID userId = userDetails.getId();
        GetUserProfileDTO userProfileDTO = userService.GetUserProfile(userId);
        return ResponseEntity.ok(userProfileDTO);
    }

    @GetMapping("/search?name={name}&page{page}")
    public ResponseEntity<List<GetUserProfileDTO>> searchUsers(@PathVariable String name, @PathVariable int page){
        List<GetUserProfileDTO> userSearchResults = userService.SearchUsers(name, page);
        return ResponseEntity.ok(userSearchResults);

    }

}
