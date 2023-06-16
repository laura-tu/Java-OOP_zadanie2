package sk.stuba.fei.uim.oop.board;

import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.path.Path;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static sk.stuba.fei.uim.oop.board.TypeOfPipe.*;


public class Tile extends JPanel implements MouseListener {
    @Getter
    private final Board myBoard;
    @Getter
    private final int xValue;
    @Getter
    private final int yValue;
    @Getter
    @Setter
    private boolean highlight;
    @Getter
    @Setter
    private BufferedImage image;
    @Setter
    private int angle;
    @Setter
    @Getter
    private JLabel picLabel;
    @Getter
    private int positionOfTile;
    @Setter
    private Pipe pipe;

    private Path path;

    private TypeOfPipe type;


    public Tile(Board board, int x, int y) {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setBackground(Color.DARK_GRAY.brighter());


        this.pipe = new Pipe(TypeOfPipe.BLACK);

        this.image = this.pipe.getImage();
        this.type = this.pipe.getType();

        this.picLabel = new JLabel(new ImageIcon(this.image));

        this.add(picLabel);

        this.angle = 0;
        this.positionOfTile = 1;

        this.myBoard = board;
        this.xValue = x;
        this.yValue = y;

        this.path = myBoard.getPath();

        this.addComponentListener(new TileComponentListener(this));
        this.addMouseListener(this);

    }

    public void noHightlight() {
        this.highlight = true;
        this.setBackground(Color.GRAY);
    }

    public void hightlightGreen() {
        this.highlight = true;
        this.setBackground(Color.GREEN);
        this.setBorder(BorderFactory.createLineBorder(Color.blue));
    }


    public void resizeImage() {
        if (image == null) {
            return;
        }

        Dimension size = this.getSize();
        int width = size.width;
        int height = size.height;

        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = scaledImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();

        ((ImageIcon) ((JLabel) this.getComponent(0)).getIcon()).setImage(scaledImage);

        this.revalidate();
        this.repaint();
    }


    public void rotateAndResizeImage() {
        if (this.image == null) {
            return;
        }

        Dimension size = this.getSize();
        int width = size.width;
        int height = size.height;

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Width and height must be positive");
        }

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D graphics = resizedImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(this.image, 0, 0, width, height, null);
        graphics.dispose();

        AffineTransform rotation = AffineTransform.getRotateInstance(Math.toRadians(this.angle), resizedImage.getWidth() / 2.0, resizedImage.getHeight() / 2.0);
        BufferedImage rotatedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(resizedImage, rotation, null);
        g2d.dispose();

        ((ImageIcon) ((JLabel) this.getComponent(0)).getIcon()).setImage(rotatedImage);

        this.revalidate();
        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


        Component current = getComponentAt(e.getPoint());
        if ((current instanceof Tile)) {
            ((Tile) current).setHighlight(true);
        }

        this.angle += 90;
        if (this.angle >= 360) {
            this.angle = 0;
        }

        rotateAndResizeImage();

        this.positionOfTile++;

        if ((this.path.isTypeOfPipe(this.xValue, this.yValue, L)) || (this.path.isTypeOfPipe(this.xValue, this.yValue, START))
                || (this.path.isTypeOfPipe(this.xValue, this.yValue, FINISH))) {
            if (this.positionOfTile == 5) {
                this.positionOfTile = 1;
            }
        }

        if (this.path.isTypeOfPipe(this.xValue, this.yValue, I)) {
            if (this.positionOfTile == 3) {
                this.positionOfTile = 1;
            }
        }

        myBoard.updateCurrentPosition(this.xValue, this.yValue, this.positionOfTile);

    }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.highlight = true;
        this.setBackground(Color.YELLOW);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.highlight = true;
        this.setBackground(Color.GRAY);
    }

}

