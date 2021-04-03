package ru.itmo.wp.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserCredentials {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 30)
    @Pattern(regexp = "[a-z]+", message = "Expected lowercase Latin letters")
    private String login;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
    private String password;
}
