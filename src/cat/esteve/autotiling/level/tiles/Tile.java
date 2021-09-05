package cat.esteve.autotiling.level.tiles;

import java.awt.image.BufferedImage;

public class Tile {
    public static int WIDTH = 16;

    private BufferedImage img;
    public int id;

    public Tile(BufferedImage img, int id) {
        this.img = img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public BufferedImage getImg() {
        return this.img;
    }
}
