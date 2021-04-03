package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.validator.CommentCredentialsWriteValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;
    private final UserService userService;
    private final CommentCredentialsWriteValidator commentCredentialsWriteValidator;

    public PostPage(PostService postService, UserService userService, CommentCredentialsWriteValidator commentCredentialsWriteValidator) {
        this.postService = postService;
        this.userService = userService;
        this.commentCredentialsWriteValidator = commentCredentialsWriteValidator;
    }

    @InitBinder("commentForm")
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (commentCredentialsWriteValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(commentCredentialsWriteValidator);
        }
    }

    @GetMapping("/post/{id}")
    @Guest
    public String post(Model model, @PathVariable String id) {
        long postId;
        try {
            postId = Long.parseLong(id);
            model.addAttribute("pagePost", postService.find(postId));
            model.addAttribute("commentForm", new CommentForm());
        } catch (NumberFormatException ignored) {
        }
        return "PostPage";
    }

    @PostMapping("/post/{id}")
    @AnyRole({Role.Name.ADMIN, Role.Name.WRITER})
    public String post(@Valid @ModelAttribute("commentForm") CommentForm commentForm,
                       BindingResult bindingResult,
                       HttpSession httpSession,
                       @PathVariable String id,
                       Model model) {
        long postId;
        try {
            postId = Long.parseLong(id);
        } catch (NumberFormatException ignored) {
            return "PostPage";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("commentForm", commentForm);
            model.addAttribute("pagePost", postService.find(postId));
            return "PostPage";
        }
        Post post = postService.find(commentForm.getPostId());
        Comment comment = new Comment();
        comment.setText(commentForm.getText());
        comment.setUser(getUser(httpSession));
        comment.setPost(post);
        postService.addComment(comment);
        return "redirect:/post/" + commentForm.getPostId();
    }
}
