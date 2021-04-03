package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;

import java.util.Arrays;

@Component
public class PostFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return PostForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) target;
            if (!postForm.getTags().matches("[a-z ]*")) {
                errors.rejectValue("tags", "tags.pattern-mismatch", "Tags can contains only lowercase latin letters");
            }
            Arrays.stream(postForm.getTags().split("\\s+")).filter(x -> !x.isEmpty()).forEach(tag -> {
                if (tag.length() < 2) {
                    errors.rejectValue("tags", "tag.too-short", "Your tag shorter than 2 characters");
                }
                if (tag.length() > 30) {
                    errors.rejectValue("tags", "tag.too-long", "Your tag longer than 30 characters");
                }
            });
        }
    }
}
