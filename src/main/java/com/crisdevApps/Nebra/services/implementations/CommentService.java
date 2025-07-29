package com.crisdevApps.Nebra.services.implementations;

import com.crisdevApps.Nebra.dto.inputDto.AnswerCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.CreateCommentDTO;
import com.crisdevApps.Nebra.dto.inputDto.EmailDTO;
import com.crisdevApps.Nebra.dto.outputDto.GetCommentDTO;
import com.crisdevApps.Nebra.exceptions.EntityNotFoundException;
import com.crisdevApps.Nebra.exceptions.ValidationException;
import com.crisdevApps.Nebra.mappers.CommentMapper;
import com.crisdevApps.Nebra.model.Business;
import com.crisdevApps.Nebra.model.Comment;
import com.crisdevApps.Nebra.model.User;
import com.crisdevApps.Nebra.model.enums.BusinessState;
import com.crisdevApps.Nebra.repositories.BusinessRepository;
import com.crisdevApps.Nebra.services.interfaces.ICommentService;
import com.crisdevApps.Nebra.repositories.CommentRepository;
import com.crisdevApps.Nebra.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final CommentRepository commentRepository;
    private final EmailService emailService;
    private final IUserService userService;
    private final CommentMapper commentMapper;
    private final BusinessRepository businessRepository;
    @Override
    public void CreateComment(CreateCommentDTO createCommentDTO, UUID authorId) {
        Business business = GetValidBusiness(createCommentDTO.businessId());
        User user = userService.FindValidUserById(authorId);

        if(business.getUserOwner().getId().equals(user.getId()))
            throw new ValidationException("You cannot comment your own business");

        Comment comment = Comment.builder()
                .answer("")
                .author(user)
                .business(business)
                .content(createCommentDTO.content())
                .score(createCommentDTO.score())
                .title(createCommentDTO.title())
                .dateCreated(LocalDateTime.now())
                .build();

        String email = business.getUserOwner().getEmail();


        commentRepository.save(comment);
        emailService.SendEmail(new EmailDTO(
                "New comment",
                "Hello, there is a new comment on your business called " + business.getName(),
                email,
                business.getUserOwner().getName(),
                "",
                "Go to Nebra"
        ), "templates/generalEmailTemplate.html", false);
    }


    @Override
    public void AnswerComment(AnswerCommentDTO answerCommentDTO, UUID userId) {
        Optional<Comment> comentarioOptional = commentRepository
                .findById(answerCommentDTO.commentId());

        if (comentarioOptional.isEmpty()){
            throw new EntityNotFoundException("Comment not found");
        }

        Comment comment = comentarioOptional.get();

        if(!comment.getBusiness().getUserOwner().getId().equals(userId))
            throw new ValidationException("You can't answer this comment");

        comment.setAnswer(answerCommentDTO.answer());
        comment.setAnswered(true);

        commentRepository.save(comment);
    }

    @Override
    public List<GetCommentDTO> GetBusinessComments(UUID businessId, int page) {
        Business business = GetValidBusiness(businessId);
        Pageable pageable = PageRequest.of(page, 10);
        Page<Comment> commentPage = commentRepository.findByBusiness(business, pageable);

        return commentPage.stream().map(commentMapper::toDto).toList();

    }

    public List<Integer> GetBusinessCommentsScoreList(UUID businessId) {
        return commentRepository.getAllScoresById(businessId);
    }


    @Override
    public int CalculateBusinessAverageScore(UUID businessId) {
        List<Integer> scoreList = GetBusinessCommentsScoreList(businessId);
        int score = 0;

        if(scoreList.isEmpty()) return 0;

        for (Integer scoreItem: scoreList){
            score = score + scoreItem;
        }
        return (score/scoreList.size());
    }

    @Override
    public List<GetCommentDTO> GetUserLatestComments(UUID userId, int page) {
        User user = userService.FindValidUserById(userId);
        Pageable pageable = PageRequest.of(page, 15);
        Page<Comment> commentPage = commentRepository.
                findAllByBusiness_UserOwnerAndAnswered(user, false, pageable);

        return commentPage.stream().map(commentMapper::toDto).toList();
    }

    public Business GetValidBusiness(UUID businessId) {
        Optional<Business> businessOptional = businessRepository
                .findByIdAndBusinessState(businessId, BusinessState.ACTIVE);
        if(businessOptional.isEmpty()){
            throw  new EntityNotFoundException("Business not found");
        }
        return businessOptional.get();
    }
}
