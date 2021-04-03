package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeCredentials;
import ru.itmo.wp.service.NoticeService;

@Component
public class NoticeCredentialsSaveValidator implements Validator {
    public final NoticeService noticeService;

    public NoticeCredentialsSaveValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return NoticeCredentials.class.equals((clazz));
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeCredentials noticeForm = (NoticeCredentials) target;
            if (noticeForm.getContent().length() > 500) {
                errors.rejectValue("content", "content.too-long-content", "too long content");
            }
            if (noticeForm.getContent().length() < 5) {
                errors.rejectValue("content", "content.too-short-content", "too short content");
            }
        }
    }
}
