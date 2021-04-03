package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.TagService;

import javax.servlet.http.HttpSession;

@Controller
public class TagPage extends Page{
    private final PostService postService;
    private final TagService tagService;

    public TagPage(PostService postService, TagService tagService) {
        this.postService = postService;
        this.tagService = tagService;
    }

    @Guest
    @GetMapping("tag/{tagName}")
    public String show(@PathVariable String tagName,
                       Model model,
                       HttpSession httpSession) {
        Tag tag = tagService.findByName(tagName);
        if (tag == null) {
            return "TagPage";
        }
        model.addAttribute("postsWithTag", postService.findByTag(tag));
        return "TagPage";
    }
}
