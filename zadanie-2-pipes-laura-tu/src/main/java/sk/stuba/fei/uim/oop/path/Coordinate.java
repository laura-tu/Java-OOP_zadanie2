package sk.stuba.fei.uim.oop.path;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;


public class Coordinate {
    @Getter
    @Setter
    private int x, y;
    private int sizeBoard;


    public Coordinate(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.sizeBoard = size;
    }

    public int ifCoordinateInPath(ArrayList<Coordinate> visitedCoordinates) {
        int len = visitedCoordinates.size();
        int x, y;

        for (int i = 0; i < len; i++) {
            x = visitedCoordinates.get(i).getX();
            y = visitedCoordinates.get(i).getY();
            if (x == getX() && y == getY()) {
                return i;
            }
        }
        return -1;
    }


    public boolean ifEquals(Coordinate d) {
        if (getX() == d.getX() && getY() == d.getY()) {
            return true;
        }
        return false;
    }

    public ArrayList<Coordinate> getUnvisitedNeighbors(ArrayList<Coordinate> visitedCoordinates) {

        ArrayList<Coordinate> unvisitedNeighbors = new ArrayList<>();

        int x = getX();
        int y = getY();


        if (y > 0) {
            Coordinate north = new Coordinate(x, y - 1, sizeBoard);
            if (!visitedCoordinates.contains(north)) {
                unvisitedNeighbors.add(north);
            }
        }

        if (y < sizeBoard - 1) {
            Coordinate south = new Coordinate(x, y + 1, sizeBoard);
            if (!visitedCoordinates.contains(south)) {
                unvisitedNeighbors.add(south);
            }
        }

        if (x > 0) {
            Coordinate west = new Coordinate(x - 1, y, sizeBoard);
            if (!visitedCoordinates.contains(west)) {
                unvisitedNeighbors.add(west);
            }
        }

        if (x < sizeBoard - 1) {
            Coordinate east = new Coordinate(x + 1, y, sizeBoard);
            if (!visitedCoordinates.contains(east)) {
                unvisitedNeighbors.add(east);
            }
        }
        return unvisitedNeighbors;
    }


}
