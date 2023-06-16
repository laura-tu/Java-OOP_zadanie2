package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Pipe {
    @Getter
    @Setter
    private BufferedImage image;

    @Getter
    private TypeOfPipe type;


    public Pipe(TypeOfPipe type) {
        this.image = tileWhichPipe(type);
        this.type = type;
    }

    public BufferedImage tileWhichPipe(TypeOfPipe type) {

        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/black.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (type) {
            case START:
                image = startPipeImage();
                return image;
            case FINISH:
                image = endPipeImage();
                return image;
            case I:
                image = iPipeImage();
                return image;
            case L:
                image = lPipeImage();
                return image;
            case BLACK:
                return image;
        }
        return null;
    }

    private BufferedImage iPipeImage() {
        BufferedImage iPipe = null;
        try {
            iPipe = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/Ipipe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return iPipe;
    }

    private BufferedImage lPipeImage() {
        BufferedImage lPipe = null;
        try {
            lPipe = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/Lpipe.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lPipe;
    }

    private BufferedImage startPipeImage() {
        BufferedImage start = null;
        try {
            start = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/start.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return start;
    }

    private BufferedImage endPipeImage() {
        BufferedImage end = null;
        try {
            end = ImageIO.read(Objects.requireNonNull(Tile.class.getResourceAsStream("/finish.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return end;
    }

}
