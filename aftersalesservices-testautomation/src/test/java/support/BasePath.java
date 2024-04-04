package support;

import config.Configuration;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class BasePath {
    private static HashMap<String, HashMap<String, String>> basePaths;
    static {
        // Initialize basepaths map here, possibly by reading from a configuration file
        Configuration.loadBasePaths();
    }

    @SuppressWarnings("unchecked")
    public static void setBasePaths(JSONObject newPaths) {
        basePaths = new HashMap<String, HashMap<String, String>>();
        for (String key : (Iterable<String>) newPaths.keySet()) {
            JSONObject value = (JSONObject) newPaths.get(key);
            HashMap<String, String> newPath = new HashMap<String, String>();
            for (String key2 : (Iterable<String>) value.keySet()) {
                newPath.put(key2, (String) value.get(key2));
            }
            basePaths.put(key, newPath);
        }
    }

    public static String getBasePath() {
        return basePaths.get(Environment.getEnvironment()).get(Environment.getMode());
    }

}
