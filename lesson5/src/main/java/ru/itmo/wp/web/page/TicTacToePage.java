package ru.itmo.wp.web.page;

import ru.itmo.wp.model.ticTacToe.Cell;
import ru.itmo.wp.model.ticTacToe.GameState;
import ru.itmo.wp.model.ticTacToe.NNTicTacToeBoard;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {
    private final static int SIZE = 3;

    public void action(Map<String, Object> view, HttpServletRequest request) {
        State state;
        HttpSession session = request.getSession();
        if (session.getAttribute("state") != null) {
            state = (State) session.getAttribute("state");
        } else {
            if (SIZE >= 1 && SIZE <= 10) {
                state = new State(SIZE);
            } else {
                throw new RedirectException("/index");
            }
        }
        view.put("state", state);
        session.setAttribute("state", state);
    }

    public void onMove(Map<String, Object> view, HttpServletRequest request) {
        State state = (State) request.getSession().getAttribute("state");
        if (state.getPhase() == GameState.RUNNING) {
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (request.getParameter("cell_" + i + j) != null) {
                        state.makeMove(i, j);
                    }
                }
            }
        }
        view.put("state", state);
        throw new RedirectException(request.getRequestURI());
    }

    public void newGame(Map<String, Object> view, HttpServletRequest request) {
        State state = new State(SIZE);
        view.put("state", state);
        request.getSession().setAttribute("state", state);
        throw new RedirectException(request.getRequestURI());
    }

    public static class State {
        private final NNTicTacToeBoard board;

        public State(int size) {
            board = new NNTicTacToeBoard(size);
        }

        public void makeMove(int row, int column) {
            board.makeMove(row, column);
        }

        public int getSize() {
            return board.getSize();
        }

        public Cell[][] getCells() {
            return board.getCells();
        }

        public GameState getPhase() {
            return board.getPhase();
        }

        public boolean getCrossesMove() {
            return board.isX();
        }
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }
}
