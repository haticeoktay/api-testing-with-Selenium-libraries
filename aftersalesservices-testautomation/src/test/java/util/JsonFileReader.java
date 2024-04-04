package util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JsonFileReader {

    public static JSONObject readFromFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            Object obj = jsonParser.parse(reader);
            return (JSONObject) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray readJSONArrayFromFile(String fileName, String parameter) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            return (JSONArray) obj.get(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject readJSONObjectFromFile(String fileName, String parameter) {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(fileName)) {
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            return (JSONObject) obj.get(parameter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
