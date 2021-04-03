package ru.itmo.wp.model.ticTacToe;

import java.util.ArrayList;
import java.util.List;

public class NNTicTacToeBoard {
    private final int size;
    private final Cell[][] cells;
    private boolean isX;
    private int empty;
    private GameState phase;

    public NNTicTacToeBoard(int size) {
        this.size = size;
        empty = size * size;
        isX = true;
        cells = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[i][j] = null;
            }
        }
        phase = GameState.RUNNING;
    }

    public GameState makeMove(int row, int column) {
        if (cells[row][column] == null) {
            cells[row][column] = isX ? Cell.X : Cell.O;
            empty--;
            isX ^= true;
            return phase = checkWinFrom(row, column);
        } else {
            return phase = GameState.RUNNING;
        }
    }

    public GameState checkWinFrom(int row, int column) {
        int[] counters = new int[4];
        List<Pair> directions = new ArrayList<>(List.of(
                new Pair(-1, 0), // row
                new Pair(0, -1), // column
                new Pair(-1, 1), // diag1
                new Pair(1, 1)  // diag2
        ));
        for (int dirNow = 0; dirNow < 4; dirNow++) {
            counters[dirNow] = 1;
            Pair direction = directions.get(dirNow);
            for (int i = 0; i < 2; i++) {
                Pair posNow = new Pair(row, column);
                while (checkCoordValid(Pair.add(posNow, direction)) && counters[dirNow] < size) {
                    posNow = Pair.add(posNow, direction);
                    if (cells[posNow.x][posNow.y] != null && cells[posNow.x][posNow.y] == cells[row][column]) {
                        counters[dirNow] += 1;
                    } else {
                        break;
                    }
                }
                direction = Pair.mul(direction, new Pair(-1, -1));
            }
        }
        for (int counter = 0; counter < 4; counter++) {
            if (counters[counter] == size) {
                return (!isX ? GameState.WON_X : GameState.WON_O);
            }
        }
        if (empty == 0) {
            return GameState.DRAW;
        }
        return GameState.RUNNING;
    }

    public GameState getPhase() {
        return phase;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean isX() {
        return isX;
    }

    private static class Pair {
        private final int x;
        private final int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public static Pair add(final Pair a, final Pair b) {
            return new Pair(a.x + b.x, a.y + b.y);
        }

        public static Pair mul(final Pair a, final Pair b) {
            return new Pair(a.x * b.x, a.y * b.y);
        }
    }

    private boolean checkCoordValid(Pair pos) {
        return (pos.x >= 0 && pos.x < size) && (pos.y >= 0 && pos.y < size);
    }
}
