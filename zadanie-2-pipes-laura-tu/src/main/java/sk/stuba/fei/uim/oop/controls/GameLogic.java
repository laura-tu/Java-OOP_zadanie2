package sk.stuba.fei.uim.oop.controls;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.board.Tile;
import sk.stuba.fei.uim.oop.path.Coordinate;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.event.*;


public class GameLogic extends UniversalAdapter {
    @Getter
    private static final int INITIAL_BOARD_SIZE = 8;
    private JFrame mainGame;
    private Board currentBoard;
    @Getter
    private JLabel label;
    @Getter
    private JLabel boardSizeLabel;
    private int currentBoardSize;
    @Getter
    @Setter
    private int level;

    public GameLogic(JFrame mainGame) {
        this.mainGame = mainGame;
        this.currentBoardSize = INITIAL_BOARD_SIZE;

        this.initializeNewBoard(this.currentBoardSize);
        this.mainGame.add(this.currentBoard);

        this.label = new JLabel();
        this.boardSizeLabel = new JLabel();

        this.level = 1;
        this.updateNameLabel();
        this.updateBoardSizeLabel();
    }

    private void updateNameLabel() {
        this.label.setText("LEVEL: " + getLevel());
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void updateBoardSizeLabel() {
        this.boardSizeLabel.setText("CURRENT BOARD SIZE: " + this.currentBoardSize);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void tileIterate(Board board) {
        Tile[][] tiles = board.getBoard();
        for (int i = 0; i < tiles.length - 1; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                Tile tile = tiles[i][j];
                tile.noHightlight();
            }
        }
    }

    private void correctPath(Board board) {
        int counter = 0;
        int first;
        int second;
        for (int i = 0; i < this.currentBoard.getCurrentPositions().size(); i++) {

            first = this.currentBoard.getCurrentPositions().get(i);
            second = this.currentBoard.getPath().getAllPositions().get(i);

            if ((first == second)) {
                counter++;
            } else {
                break;
            }
        }
        int number = counter;
        Tile[][] tiles = board.getBoard();


        outerloop:
        for (Coordinate c : this.currentBoard.getPathSetting()) {
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles[i].length; j++) {
                    Tile tile = tiles[i][j];

                    if ((c.getX() == i) && (c.getY() == j)) {

                        tile.hightlightGreen();

                        counter--;

                    }
                    if (counter == -1) {
                        tile.hightlightGreen();
                        tile.noHightlight();
                        break outerloop;
                    }
                }
            }
        }
        if (number == this.currentBoard.getCurrentPositions().size()) {
            this.level++;
            updateNameLabel();
            gameRestart();
        }
    }

    private void gameRestart() {
        this.mainGame.remove(this.currentBoard);
        this.initializeNewBoard(this.currentBoardSize);

        tileIterate(this.currentBoard);
        this.mainGame.add(this.currentBoard);

        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    private void initializeNewBoard(int dimension) {
        this.currentBoard = new Board(dimension);
        this.currentBoard.addMouseMotionListener(this);
        this.currentBoard.addMouseListener(this);
        this.mainGame.revalidate();
        this.mainGame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "RESTART":
                this.gameRestart();
            case "CHECK":
                this.correctPath(currentBoard);

        }

        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        this.currentBoardSize = ((JSlider) e.getSource()).getValue();

        this.updateBoardSizeLabel();
        this.gameRestart();
        this.mainGame.setFocusable(true);
        this.mainGame.requestFocus();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.gameRestart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.mainGame.dispose();
                System.exit(0);
                break;
            case KeyEvent.VK_ENTER:
                this.correctPath(currentBoard);
        }
    }

}
