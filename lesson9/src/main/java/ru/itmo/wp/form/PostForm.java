package ru.itmo.wp.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PostForm {
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @Size(max = 200)
    private String tags;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
    private String text;
}
