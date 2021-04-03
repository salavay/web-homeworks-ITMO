package ru.itmo.wp.model.ticTacToe;

public enum Cell {
    E(null), X("X"), O("O");
    private final String name;

    Cell(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
