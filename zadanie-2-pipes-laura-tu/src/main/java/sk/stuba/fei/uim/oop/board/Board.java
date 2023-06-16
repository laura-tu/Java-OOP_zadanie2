package sk.stuba.fei.uim.oop.board;

import lombok.Getter;

import sk.stuba.fei.uim.oop.path.Coordinate;
import sk.stuba.fei.uim.oop.path.Path;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    @Getter
    private ArrayList<TypeOfPipe> pipesSetting;
    @Getter
    private Tile[][] board;
    @Getter
    private Path path;
    @Getter
    private ArrayList<Coordinate> pathSetting;

    @Getter
    private ArrayList<Integer> currentPositions;


    public Board(int dimension) {

        this.path = new Path(dimension);

        this.pathSetting = this.path.getPathInCoordinates();
        this.pipesSetting = this.path.getPipesSetting();

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(Color.LIGHT_GRAY);

        this.currentPositions = this.initializeStateFirst();
        this.initializeBoard(dimension);

    }

    private ArrayList<Integer> initializeStateFirst() {
        ArrayList<Integer> newOne = new ArrayList<>();
        for (int i = 0; i < path.getPathInCoordinates().size(); i++) {
            newOne.add(0);
        }
        return newOne;
    }


    public void updateCurrentPosition(int xNum, int yNum, int state) {

        int xVal;
        int yVal;
        for (int i = 0; i < path.getPathInCoordinates().size(); i++) {

            xVal = path.getPathInCoordinates().get(i).getX();
            yVal = path.getPathInCoordinates().get(i).getY();
            if ((xVal == xNum) && (yVal == yNum)) {
                this.currentPositions.set(i, state);
                break;
            }
        }

    }

    private void initializeBoard(int dimension) {

        this.board = new Tile[dimension][dimension];
        this.setLayout(new GridLayout(dimension, dimension));

        Coordinate c = new Coordinate(0, 0, dimension);
        Pipe pipe;
        int index = -1;

        for (int x = 0; x < dimension; x++) {
            for (int y = 0; y < dimension; y++) {

                Tile tile = new Tile(this, x, y);

                c.setX(x);
                c.setY(y);

                index = c.ifCoordinateInPath(this.pathSetting);
                if (index > -1) {

                    pipe = new Pipe(this.pipesSetting.get(index));
                    tile.setImage(pipe.getImage());

                } else {
                    pipe = new Pipe(TypeOfPipe.BLACK);
                    tile.setImage(pipe.getImage());
                }
                this.board[x][y] = tile;
                this.add(tile);
                this.updateCurrentPosition(x, y, tile.getPositionOfTile());

            }
        }
    }
}
