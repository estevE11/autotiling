package cat.esteve.autotiling.level;

import cat.esteve.autotiling.ArrayUtils;
import cat.esteve.autotiling.JSONUtils;
import cat.esteve.autotiling.level.tiles.Tile;
import cat.esteve.autotiling.level.tiles.TilePattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.LinkedList;

public class PatternLoader {
    public static LinkedList<TilePattern> loadPatterns(String path) {
        JSONParser parser = new JSONParser();
        LinkedList<TilePattern> res = new LinkedList<TilePattern>();

        JSONObject patternsObj = JSONUtils.load(path);
        JSONArray patterns = (JSONArray) patternsObj.get("patterns");
        patterns.forEach(it-> {
            JSONObject obj;
            try {
                obj = (JSONObject) parser.parse(it.toString());
                System.out.println("Loading pattern: " + obj.get("name"));
                int x = Integer.parseInt(obj.get("x").toString());
                int y = Integer.parseInt(obj.get("y").toString());
                JSONArray pat = (JSONArray) obj.get("pattern");
                int[][] res_pattern = new int[5][5];
                for(int i = 0; i < 5*5; i++) {
                    int py = (int)Math.ceil(i/5);
                    int px = i-(5*py);
                    res_pattern[px][py] = Integer.parseInt(pat.get(i).toString());
                }
                res.add(new TilePattern(x, y, res_pattern));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return res;
    }
}
