package support;

import config.Configuration;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class Endpoints {
    private static HashMap<String, HashMap<String, String>> endpoints;

    static {
        // Initialize endpoints map here, possibly by reading from a configuration file
        Configuration.loadEndpoints();
    }

    public static String getURI(String uriName) {
        if (endpoints != null) {
            HashMap<String, String> uriMap = endpoints.get(uriName);
            if (uriMap != null) {
                String mode = Environment.getMode();
                if (mode != null) {
                    return uriMap.get(mode);
                } else {
                    throw new IllegalStateException("Environment mode is null");
                }
            } else {
                throw new IllegalArgumentException("URI name not found: " + uriName);
            }
        } else {
            throw new IllegalStateException("Endpoints map is not initialized");
        }
    }

    @SuppressWarnings("unchecked")
    public static void setEndpoints(JSONObject newUris) {
        endpoints = new HashMap<>();
        for (String key : (Iterable<String>) newUris.keySet()) {
            JSONObject value = (JSONObject) newUris.get(key);
            HashMap<String, String> newEndpoint = new HashMap<>();
            for (String key2 : (Iterable<String>) value.keySet()) {
                newEndpoint.put(key2, (String) value.get(key2));
            }
            endpoints.put(key, newEndpoint);
        }
    }

    public static boolean endpointDefined(String wanted) {
        return endpoints != null && endpoints.containsKey(wanted);
    }

    private static HashMap<String, HashMap<String, String>> readEndpointsFromFile(String fileName) {
        JSONParser jsonParser = new JSONParser();
        try (InputStream inputStream = Endpoints.class.getClassLoader().getResourceAsStream(fileName);
             InputStreamReader reader = new InputStreamReader(inputStream)) {
            if (reader != null) {
                Object obj = jsonParser.parse(reader);
                return (HashMap<String, HashMap<String, String>>) obj;
            } else {
                throw new IllegalStateException("File not found: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException("Error reading endpoints from file: " + fileName, e);
        }
    }
}
