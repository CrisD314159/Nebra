package com.crisdevApps.Nebra.controladores;

import com.crisdevApps.Nebra.dto.inputDto.AnswerCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetCommentDTO;
import com.crisdevApps.Nebra.dto.outputDto.ResponseMessage;
import com.crisdevApps.Nebra.security.UserDetailsImpl;
import com.crisdevApps.Nebra.services.interfaces.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @PostMapping()
    public ResponseEntity<ResponseMessage> createComment(@Valid @RequestBody CreateCommentDTO createCommentDTO,
    @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        commentService.CreateComment(createCommentDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage(true, "Comment created"));
    }

    @PutMapping("/answer")
    public ResponseEntity<ResponseMessage> answerComment(@Valid @RequestBody AnswerCommentDTO answerCommentDTO,
    @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        UUID userId = userDetails.getId();
        commentService.AnswerComment(answerCommentDTO, userId);
        return ResponseEntity.ok(new ResponseMessage(true, "Comment Answered"));
    }

    @GetMapping("/business-comments/{id}")
    public ResponseEntity<List<GetCommentDTO>> getBusinessComments(@PathVariable UUID id, @RequestParam int page){
        List<GetCommentDTO> businessComments =  commentService.GetBusinessComments(id, page);
        return ResponseEntity.ok(businessComments);
    }

    @GetMapping("/user-comments")
    public ResponseEntity<List<GetCommentDTO>> getUserLatestComments(
            @RequestParam int page, @AuthenticationPrincipal UserDetailsImpl userDetails){
        UUID userId = userDetails.getId();
        List<GetCommentDTO> userComments =  commentService.GetUserLatestComments(userId, page);
        return ResponseEntity.ok(userComments);
    }
}
