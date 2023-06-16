package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.path.Coordinate;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Lpipe extends JPanel {
    @Getter
    @Setter
    private BufferedImage lPipee;
    private JLabel picLabel;

    @Getter
    @Setter
    private int position;


    public Lpipe() {
        try {
            this.lPipee = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/Lpipe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.picLabel = new JLabel(new ImageIcon(this.lPipee));
        this.add(picLabel);

        this.position = 1;
    }


    public ArrayList<Integer> setCorrectPosition(ArrayList<Coordinate> pathInCoordinates, ArrayList<TypeOfPipe> pipesSetting) {
        int xPrevious, xCurrent, xNext;
        int yPrevious, yCurrent, yNext;

        ArrayList<Integer> Lpositions = new ArrayList<>();

        for (int i = 1; i < pipesSetting.size() + 1; i++) {

            if (pipesSetting.get(i) == TypeOfPipe.FINISH) {
                break;
            }

            if (pipesSetting.get(i) == TypeOfPipe.L) {

                xPrevious = pathInCoordinates.get(i - 1).getX();
                yPrevious = pathInCoordinates.get(i - 1).getY();

                xCurrent = pathInCoordinates.get(i).getX();
                yCurrent = pathInCoordinates.get(i).getY();

                xNext = pathInCoordinates.get(i + 1).getX();
                yNext = pathInCoordinates.get(i + 1).getY();

                if (((xCurrent == xPrevious + 1) && (yCurrent == yNext - 1)) ||
                        ((xCurrent == xNext + 1) && (yCurrent == yPrevious - 1))) {
                    this.setPosition(1);

                    Lpositions.add(1);
                }

                if (((xCurrent == xPrevious - 1) && (yCurrent == yNext - 1)) ||
                        ((xCurrent == xNext - 1) && (yCurrent == yPrevious - 1))) {
                    this.setPosition(2);

                    Lpositions.add(2);
                }

                if (((xCurrent == xPrevious - 1) && (yCurrent == yNext + 1)) ||
                        ((xCurrent == xNext - 1) && (yCurrent == yPrevious + 1))) {
                    this.setPosition(3);

                    Lpositions.add(3);
                }

                if (((xCurrent == xPrevious + 1) && (yCurrent == yNext + 1)) ||
                        ((xCurrent == xNext + 1) && (yCurrent == yPrevious + 1))) {
                    this.setPosition(4);

                    Lpositions.add(4);
                }

            }
        }
        return Lpositions;
    }


}
