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

import static sk.stuba.fei.uim.oop.board.TypeOfPipe.*;

public class Ipipe extends JPanel {
    @Getter
    @Setter
    private BufferedImage iPipee;
    private JLabel picLabel;

    @Getter
    @Setter
    private int position;


    public Ipipe() {
        try {
            this.iPipee = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/Ipipe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.picLabel = new JLabel(new ImageIcon(this.iPipee));
        this.add(picLabel);

        this.position = 1;
    }


    public ArrayList<Integer> setCorrectPosition(ArrayList<Coordinate> pathInCoordinates, ArrayList<TypeOfPipe> pipesSetting) {
        int xPrevious, xCurrent;
        int yPrevious, yCurrent;

        ArrayList<Integer> Ipositions = new ArrayList<>();

        for (int i = 1; i < pipesSetting.size() + 1; i++) {
            if (pipesSetting.get(i) == FINISH) {
                break;
            }

            if (pipesSetting.get(i) == I) {

                xPrevious = pathInCoordinates.get(i - 1).getX();
                yPrevious = pathInCoordinates.get(i - 1).getY();

                xCurrent = pathInCoordinates.get(i).getX();
                yCurrent = pathInCoordinates.get(i).getY();


                if ((yCurrent == yPrevious + 1) || (yCurrent == yPrevious - 1)) {
                    this.setPosition(1);
                    Ipositions.add(1);
                }

                if (((xCurrent == xPrevious + 1) || (xCurrent == xPrevious - 1))) {
                    this.setPosition(2);
                    Ipositions.add(2);
                }

            }
        }
        return Ipositions;
    }
}

