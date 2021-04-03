package ru.itmo.wp.form;

import javax.persistence.Lob;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class NoticeCredentials {
    @NotNull
    @NotEmpty
    @Lob
    @Size(min = 5, max = 500)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
