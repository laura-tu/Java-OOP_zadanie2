package sk.stuba.fei.uim.oop.board;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class TileComponentListener extends ComponentAdapter {
    private Tile tile;

    public TileComponentListener(Tile tile) {
        this.tile = tile;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        super.componentResized(e);
        tile.resizeImage();
    }
}
