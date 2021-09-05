package cat.esteve.autotiling.gfx;

import cat.esteve.autotiling.Start;
import cat.esteve.autotiling.main.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {
    private BufferedImage img;
    public int tw, th;
    public int ssw, ssh;

    public SpriteSheet(BufferedImage img, int tw, int th) {
        this.img = img;
        this.tw = tw;
        this.th = th;
        this.ssw = img.getWidth()/tw;
        this.ssh = img.getHeight()/th;
    }

    public SpriteSheet(String path, int tw, int th) {
        try {
            String p = "/gfx" + path;
            System.out.println("Attempting to load " + p);
            img = ImageIO.read(Start.class.getResourceAsStream(p));
            System.out.println(p + " successfuly loaded");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        this.tw = tw;
        this.th = th;
        this.ssw = img.getWidth()/tw;
        this.ssh = img.getHeight()/th;
    }

    public BufferedImage get(int x, int y) {
        return this.img.getSubimage(x*this.tw, y*this.th, this.tw, this.th);
    }

    public BufferedImage getOriginalImage() {
        return this.img;
    }
}
