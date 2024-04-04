package support;

import org.json.simple.JSONObject;

import java.util.HashMap;

public class Users {

    private static HashMap<String, HashMap<String, String>> users;
    private static HashMap<String, HashMap<String, String>> error;
    private static HashMap<String, HashMap<String, String>> validation;

    static public String getUserEmail(String userName) {
        return users.get(userName).get("username");
    }

    static public String getUserPassword(String userName) {
        return users.get(userName).get("password");
    }

    static public String getErrorMessage(String err) {return error.get(err).get("UNKNOWN_ERROR") ; }

    static public String getInvalidInputMessages(String err) {return validation.get(err).get("INVALID_INPUTS") ; }


    @SuppressWarnings("unchecked")
    public static void setUsers(JSONObject newUris) {
        users = new HashMap<String, HashMap<String, String>>();
        for (String key : (Iterable<String>) newUris.keySet()) {
            JSONObject value = (JSONObject) newUris.get(key);
            HashMap<String, String> newUser = new HashMap<String, String>();
            for (String key2 : (Iterable<String>) value.keySet()) {
                newUser.put(key2, (String) value.get(key2));
            }
            users.put(key, newUser);
        }
    }

}
