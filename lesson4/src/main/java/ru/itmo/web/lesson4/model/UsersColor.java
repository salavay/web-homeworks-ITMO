package ru.itmo.web.lesson4.model;

public enum UsersColor {
    GREEN("green"), RED("red"), BLUE("blue");

    private final String name;

    UsersColor(String color) {
        name = color;
    }

    @Override
    public String toString() {
        return name;
    }
}
