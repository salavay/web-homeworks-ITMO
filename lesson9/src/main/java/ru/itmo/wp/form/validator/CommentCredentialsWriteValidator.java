package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.CommentForm;

@Component
public class CommentCredentialsWriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CommentForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            CommentForm commentForm = (CommentForm) target;
            if (commentForm.getText().isEmpty()) {
                errors.rejectValue("text", "text.empty-text", "empty comment text");
            }
            if (commentForm.getText().length() > 30) {
                errors.rejectValue("text", "text.too-long-text", "text should be shorter than 30 characters");
            }
        }
    }
}
