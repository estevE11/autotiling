package cat.esteve.autotiling.level.tiles;

import cat.esteve.autotiling.ArrayUtils;
import cat.esteve.autotiling.gfx.SpriteSheet;

import java.lang.reflect.Array;

public class TilePattern {
    private int[][] pattern;
    public int x = 0, y = 0;

    public TilePattern(int x, int y, int[][] pattern) {
        this.x = x;
        this.y = y;
        this.pattern = pattern;
    }

    public int[][] translate(int[][] p) {
        int[][] res = new int[5][5];

        for(int y = 0; y < 5; y++) {
            for(int x = 0; x < 5; x++) {
                res[y][x] = p[x][y];
            }
        }

        return res;
    }

    public int[][] getPattern() {
        return this.pattern;
    }

    public Tile getTile(SpriteSheet sheet) {
        return new Tile(sheet.get(x, y), y*sheet.ssw+x);
    }

    static int[][] rotateMatrix(int[][] mat) {
        final int M = mat.length;
        final int N = mat[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = mat[r][c];
            }
        }
        return ret;
    }

    private int[][] flipMatrixV(int N, int[][] mat) {
        int[][] res = new int[N][N];
        for(int x = N-1; x >= 0; x--) {
            for(int y = N-1; y >= 0; y--) {
                res[x][y] = mat[(x*-1) + (N-1)][y];
            }
        }
        return res;
    }
}
