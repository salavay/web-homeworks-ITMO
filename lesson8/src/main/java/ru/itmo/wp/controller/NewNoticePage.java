package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Notice;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.form.validator.NoticeCredentialsSaveValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class NewNoticePage extends Page{
    private final NoticeService noticeService;
    private final NoticeCredentialsSaveValidator noticeCredentialsSaveValidator;

    public NewNoticePage(NoticeService noticeService, NoticeCredentialsSaveValidator noticeCredentialsSaveValidator) {
        this.noticeService = noticeService;
        this.noticeCredentialsSaveValidator = noticeCredentialsSaveValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        if (noticeCredentialsSaveValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(noticeCredentialsSaveValidator);
        }
    }

    @GetMapping("/notices/new")
    public String save(Model model) {
        model.addAttribute("noticeForm", new NoticeCredentials());
        return "NewNoticePage";
    }

    @PostMapping("/notices/new")
    public String save(@Valid @ModelAttribute("noticeForm") NoticeCredentials noticeCredentials,
                       BindingResult bindingResult,
                       HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NewNoticePage";
        }
        Notice notice = new Notice();
        notice.setContent(noticeCredentials.getContent());
        noticeService.save(notice);
        putMessage(httpSession, "Notice saved");
        return "redirect:/";
    }
}
