package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class DisabledCredentials {
    @NotNull
    private long id;

    @NotNull
    private boolean disabled;


    public void setId(long id) {
        this.id = id;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public long getId() {
        return id;
    }

    public boolean isDisabled() {
        return disabled;
    }
}
