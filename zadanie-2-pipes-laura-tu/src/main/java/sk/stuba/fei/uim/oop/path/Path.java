package sk.stuba.fei.uim.oop.path;

import lombok.Getter;
import sk.stuba.fei.uim.oop.board.Ipipe;
import sk.stuba.fei.uim.oop.board.Lpipe;
import sk.stuba.fei.uim.oop.board.TypeOfPipe;

import java.util.*;

public class Path {

    @Getter
    private ArrayList<TypeOfPipe> pipesSetting;
    @Getter
    ArrayList<Integer> allPositions;
    private int size;
    private Coordinate start;
    private Coordinate end;
    @Getter
    private ArrayList<Coordinate> pathInCoordinates;
    private ArrayList<Integer> whereStartEnd;

    public Path(int size) {
        this.size = size;

        this.pathInCoordinates = new ArrayList<>();

        whereStartEnd = this.randomStartEnd(size);

        this.start = new Coordinate(whereStartEnd.get(0), whereStartEnd.get(1), size);
        this.end = new Coordinate(whereStartEnd.get(2), whereStartEnd.get(3), size);

        this.randDFS(start);

        this.pipesSetting = new ArrayList<>();
        this.initializePipesSetting();

        this.allPositions = new ArrayList<>();
        this.initializePositions();
    }



    private void randDFS(Coordinate point) {
        this.pathInCoordinates.add(point);

        int x = point.getX();
        int y = point.getY();
        Coordinate current = new Coordinate(x, y, size);
        Coordinate next = null;
        int length;

        int index = -1;

        while ((this.end.ifCoordinateInPath(this.pathInCoordinates) == -1) || (!this.end.ifEquals(current))) {
            ArrayList<Coordinate> unvisitedNeighbors = current.getUnvisitedNeighbors(this.pathInCoordinates);

            length = this.pathInCoordinates.size();
            Collections.shuffle(unvisitedNeighbors);

            if (!unvisitedNeighbors.isEmpty()) {
                for (Coordinate c : unvisitedNeighbors) {
                    index = c.ifCoordinateInPath(this.pathInCoordinates);
                    if (index == -1) {
                        next = c;
                        this.pathInCoordinates.add(next);
                        current = next;
                        break;
                    } else {
                        unvisitedNeighbors.remove(c);
                        break;
                    }
                }
                if (!current.ifEquals(next)) {
                    pathInCoordinates.remove(length - 1);
                    length = this.pathInCoordinates.size();
                    current = this.pathInCoordinates.get(length - 1);
                }
                next = unvisitedNeighbors.get(0);
            } else {
                length = this.pathInCoordinates.size();
                this.pathInCoordinates.remove(length - 1);
                current = this.pathInCoordinates.get(length - 2);
            }
        }

    }

    private ArrayList<Integer> randomStartEnd(int size) {
        ArrayList<Integer> startAndEnd = new ArrayList<Integer>();
        int xStart, yStart;
        int xEnd, yEnd;
        Random rand = new Random();

        int wall = rand.nextInt(4);
        switch (wall) {
            case 0:
                xStart = 0;
                yStart = rand.nextInt(size);
                xEnd = size - 1;
                yEnd = rand.nextInt(size);
                break;
            case 1:
                xStart = size - 1;
                yStart = rand.nextInt(size);
                xEnd = 0;
                yEnd = rand.nextInt(size);
                break;
            case 2:
                xStart = rand.nextInt(size);
                yStart = 0;
                xEnd = rand.nextInt(size);
                yEnd = size - 1;
                break;
            case 3:
                xStart = rand.nextInt(size);
                yStart = size - 1;
                xEnd = rand.nextInt(size);
                yEnd = 0;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + wall);
        }
        startAndEnd.add(xStart);
        startAndEnd.add(yStart);
        startAndEnd.add(xEnd);
        startAndEnd.add(yEnd);
        return startAndEnd;
    }

    private void initializePipesSetting() {
        this.pipesSetting.add(TypeOfPipe.START);

        int xPrevious, xCurrent, xNext;
        int yPrevious, yCurrent, yNext;

        for (int i = 1; i < this.pathInCoordinates.size() + 1; i++) {
            xPrevious = this.pathInCoordinates.get(i - 1).getX();
            yPrevious = this.pathInCoordinates.get(i - 1).getY();

            xCurrent = this.pathInCoordinates.get(i).getX();
            yCurrent = this.pathInCoordinates.get(i).getY();

            if (xCurrent == this.whereStartEnd.get(2) && yCurrent == this.whereStartEnd.get(3)) {
                this.pipesSetting.add(TypeOfPipe.FINISH);
                break;
            }

            xNext = this.pathInCoordinates.get(i + 1).getX();
            yNext = this.pathInCoordinates.get(i + 1).getY();


            if (((xPrevious == xCurrent) && (xCurrent == xNext)) || ((yPrevious == yCurrent) && (yCurrent == yNext))) {
                this.pipesSetting.add(TypeOfPipe.I);

            }

            if ((((xPrevious == xCurrent) && (yPrevious != yCurrent)) && ((xCurrent != xNext)) && (yCurrent == yNext))) {
                this.pipesSetting.add(TypeOfPipe.L);

            }
            if (((xPrevious != xCurrent) && (yPrevious == yCurrent)) && ((xCurrent == xNext)) && (yCurrent != yNext)) {
                this.pipesSetting.add(TypeOfPipe.L);
            }
        }

    }

    private void initializePositions() {
        Lpipe lpipe = new Lpipe();
        Ipipe ipipe = new Ipipe();
        ArrayList<Integer> lPositions = new ArrayList<>();
        ArrayList<Integer> iPositions = new ArrayList<>();

        lPositions = lpipe.setCorrectPosition(this.pathInCoordinates, this.pipesSetting);
        iPositions = ipipe.setCorrectPosition(this.pathInCoordinates, this.pipesSetting);

        int xCurr=start.getX();
        int yCurr=start.getY();
        int xNext=pathInCoordinates.get(1).getX();
        int yNext=pathInCoordinates.get(1).getY();

        if(yCurr == yNext-1){
            this.allPositions.add(1);
        }
        if(xCurr == xNext-1){
            this.allPositions.add(2);
        }
        if(yCurr == yNext+1){
            this.allPositions.add(3);
        }
        if(xCurr == xNext+1){
            this.allPositions.add(4);
        }

        int lCounter = 0;
        int iCounter = 0;
        for (int i = 1; i < this.pipesSetting.size(); i++) {
            if (this.pipesSetting.get(i) == TypeOfPipe.L) {
                this.allPositions.add(lPositions.get(lCounter));
                lCounter++;
            }

            if (this.pipesSetting.get(i) == TypeOfPipe.I) {
                this.allPositions.add(iPositions.get(iCounter));
                iCounter++;
            }
        }

        int xPrevious=pathInCoordinates.get(pathInCoordinates.size()-2).getX();
        int yPrevious=pathInCoordinates.get(pathInCoordinates.size()-2).getY();
        xCurr=end.getX();
        yCurr=end.getY();

        if(yCurr == yPrevious-1){
            this.allPositions.add(1);
        }
        if(xCurr == xPrevious-1){
            this.allPositions.add(2);
        }
        if(yCurr == yPrevious+1){
            this.allPositions.add(3);
        }
        if(xCurr == xPrevious+1){
            this.allPositions.add(4);
        }
    }

    public boolean isTypeOfPipe(int xValue, int yValue, TypeOfPipe type) {

        int xCoor;
        int yCoor;
        int index = 0;
        for (Coordinate c : pathInCoordinates) {
            xCoor = c.getX();
            yCoor = c.getY();

            if ((xCoor == xValue) && (yCoor == yValue)) {
                break;
            }
            index++;
        }

        if (pipesSetting.get(index) == type) {
            return true;
        } else {
            return false;
        }

    }

}
