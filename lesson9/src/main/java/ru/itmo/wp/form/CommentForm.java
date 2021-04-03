package ru.itmo.wp.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentForm {
    @NotNull
    private long userId;

    @NotNull
    private long postId;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 30)
    private String text;
}
