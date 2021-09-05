package cat.esteve.autotiling;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {
    public static JSONObject load(String path) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(path));

            JSONObject jsonObject = (JSONObject) obj;

            return jsonObject;
        } catch (FileNotFoundException e) {
            System.out.println("Failed to load JSON File (" + e.getMessage() + ")");
        } catch (IOException e) {
            System.out.println("Failed to load JSON File (" + e.getMessage() + ")");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void write(String path, JSONObject toW) {
        try {

            FileWriter file = new FileWriter(path);
            file.write(toW.toJSONString());
            file.flush();
            file.close();

        } catch (IOException e) {
        }
    }
}
