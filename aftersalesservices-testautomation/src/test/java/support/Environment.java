package support;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class Environment {
    private static String defaultEnv="prod";
    private static String selectedEnv = null;
    private static String defaultMode="public";
    private static String selectedMode = null;
    private static ArrayList<String> environments;
    private static ArrayList<String> modes;


    public static void setEnvs(JSONArray newEnvs) {
        environments = new ArrayList<String>();
        for (Object newEnv : newEnvs) {
            environments.add(newEnv.toString());
        }
    }

    public static void setModes(JSONArray newModes) {
        modes = new ArrayList<String>();
        for (Object newEnv : newModes) {
            modes.add(newEnv.toString());
        }
    }

    public static void setDefaultEnv(String newDefaultEnv) {
        defaultEnv = newDefaultEnv;
    }

    public static void setDefaultMode(String newDefaultMode) {
        defaultMode = newDefaultMode;
    }

    public static String getEnvironment() {
        if (selectedEnv == null) {
            String consoleEnv = System.getProperty("env");
            if (consoleEnv == null) {
                System.out.println(String.format("Environment not sent. Going to use default: %s", defaultEnv));
                selectedEnv = defaultEnv;
            } else if (!envIsAvailable(consoleEnv)) {
                System.out.println(String.format("Environment sent in parameter is not available. Sent: %s", consoleEnv));
                System.out.println(String.format("Going to use default: %s", defaultEnv));
                selectedEnv = defaultEnv;
            } else {
                System.out.println(String.format("Selected env: %s", consoleEnv));
                selectedEnv = consoleEnv;
            }
        }
        return selectedEnv;
    }

    public static String getMode() {
        if (selectedMode == null) {
            String consoleMode = System.getProperty("mode");
            if (consoleMode == null) {
                System.out.println(String.format("Mode not sent. Going to use default: %s", defaultMode));
                selectedMode = defaultMode;
            } else if (!modeIsAvailable(consoleMode)) {
                System.out.println(String.format("Mode sent in parameter is not available. Sent: %s", consoleMode));
                System.out.println(String.format("Going to use default: %s", defaultMode));
                selectedMode = defaultMode;
            } else {
                System.out.println(String.format("Selected mode: %s", consoleMode));
                selectedMode = consoleMode;
            }
        }
        return selectedMode;
    }

    public static boolean envIsAvailable(String desireEnv) {
        return environments.contains(desireEnv);
    }

    public static boolean modeIsAvailable(String mode) {
        return modes.contains(mode);
    }

}
