package cat.esteve.autotiling.level;

import cat.esteve.autotiling.ArrayUtils;
import cat.esteve.autotiling.gfx.MainCanvas;
import cat.esteve.autotiling.gfx.SpriteSheet;
import cat.esteve.autotiling.level.tiles.Tile;
import cat.esteve.autotiling.level.tiles.TilePattern;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

public class Level {
    private boolean[][] base_tile_map;
    private Tile[][] tile_map;
    private LinkedList<TilePattern> patterns = new LinkedList<TilePattern>();
    private LinkedList<TileBlueprint> tilesToAdd = new LinkedList<TileBlueprint>();

    private boolean base_render = false;
    private int w = 60, h = 40;

    private int mx = 0, my = 0;

    private SpriteSheet sheet_tiles;

    public Level() {
        this.sheet_tiles = new SpriteSheet("/tiles/cavesofgallet_mod.png", 8, 8);
        this.base_tile_map = new boolean[this.w][this.h];
        this.tile_map = new Tile[this.w][this.h];

        this.patterns = PatternLoader.loadPatterns("res/patterns/patterns.json");
    }

    public void update() {
        for(int i = 0; i < this.tilesToAdd.size(); i++) {
            TileBlueprint b = this.tilesToAdd.get(i);
            this.base_tile_map[b.x][b.y] = b.v;
        }
        this.tilesToAdd.clear();
        this.updateTiles();
    }

    public void render(MainCanvas canvas) {
        if(this.base_render) {
            for (int y = 0; y < this.h; y++) {
                for (int x = 0; x < this.w; x++) {
                    if (this.base_tile_map[x][y]) {
                        canvas.fill(x * Tile.WIDTH, y * Tile.WIDTH, Tile.WIDTH, Tile.WIDTH, Color.red);
                    }
                }
            }
        } else {
            for (int y = 0; y < this.h; y++) {
                for (int x = 0; x < this.w; x++) {
                    if(this.base_tile_map[x][y]){
                        canvas.getG().drawImage(this.tile_map[x][y].getImg(), x*Tile.WIDTH, y*Tile.WIDTH, Tile.WIDTH, Tile.WIDTH, null);
                    }
                }
            }
        }
        int mtx = mx/Tile.WIDTH;
        int mty = my/Tile.WIDTH;
        canvas.drawRect(mtx*Tile.WIDTH, mty*Tile.WIDTH, Tile.WIDTH, Tile.WIDTH, Color.red);
    }

    private void updateTiles() {
        for(int y = 0; y < this.h-1; y++) {
            for(int x = 0; x < this.w-1; x++) {
                if(!this.base_tile_map[x][y]) continue;
                for(TilePattern p : this.patterns) {
                    if(comparePatterns(x, y, p.getPattern())) {
                        this.tile_map[x][y] = p.getTile(this.sheet_tiles);
                        break;
                    }
                    this.tile_map[x][y] = this.createTile(1, 1);
                }
            }
        }
    }

    private boolean comparePatterns(int x, int y, int[][] pattern) {
        for(int yy = y-2; yy < y+3; yy++) {
            for(int xx = x-2; xx < x+3; xx++) {
                int px = xx-x+2;
                int py = yy-y+2;
                if(pattern[px][py] == -1) continue;
                if((this.getTile(xx, yy) ? 1 : 0) != pattern[px][py]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void mousePressed(int mx, int my, int btn) {
        this.mx = mx;
        this.my = my;
        if(btn == 1)
            this.setTile((int)(mx/Tile.WIDTH), (int)(my/Tile.WIDTH), true);
        if(btn == 3)
            this.setTile((int)(mx/Tile.WIDTH), (int)(my/Tile.WIDTH), false);
        if(btn == 2)
            this.base_render = !this.base_render;
    }

    public void mouseReleassed(int mx, int my, int btn) {
        this.mx = mx;
        this.my = my;
    }

    public void mouseDragged(int mx, int my, int btn) {
        this.mx = mx;
        this.my = my;
        if(btn == 1)
            this.setTile((int)(mx/Tile.WIDTH), (int)(my/Tile.WIDTH), true);
        if(btn == 3)
            this.setTile((int)(mx/Tile.WIDTH), (int)(my/Tile.WIDTH), false);
    }

    public void mouseMoved(int mx, int my, int btn) {
        this.mx = mx;
        this.my = my;
    }

    public Tile createTile(int x, int y) {
        return new Tile(this.sheet_tiles.get(x, y), y*this.sheet_tiles.ssw+x);
    }

    public void setTile(int x, int y, boolean v) {
        if(x < 0 || x >= this.w || y < 0 || y >= this.h) return;
        this.tilesToAdd.add(new TileBlueprint(x, y, v));
    }

    public void setTileFromMouse(int mx, int my, boolean v) {
        this.setTile(mx/Tile.WIDTH, my/Tile.WIDTH, v);
    }

    public boolean getTile(int x, int y) {
        if(x < 0 || x >= this.w || y < 0 || y >= this.h) return false;
        return this.base_tile_map[x][y];
    }

    private class TileBlueprint {
        public int x, y;
        public boolean v;

        public TileBlueprint(int x, int y, boolean v) {
            this.x = x;
            this.y = y;
            this.v = v;
        }
    }
}
