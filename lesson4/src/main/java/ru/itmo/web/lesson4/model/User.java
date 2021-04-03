package ru.itmo.web.lesson4.model;

public class User {
    private final long id;
    private final String handle;
    private final String name;
    private final UsersColor colorOfUsername;

    public User(long id, String handle, String name, UsersColor color) {
        this.id = id;
        this.handle = handle;
        this.name = name;
        this.colorOfUsername = color;
    }

    public long getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public String getName() {
        return name;
    }

    public UsersColor getColorOfUsername() {
        return colorOfUsername;
    }
}
