package utils;

@Deprecated
public class OSChecker {
    public enum OS {
        WINDOWS, LINUX, MAC, SOLARIS
    }

    private static OS os = null;

    public static OS getOS() {
        if (os == null) {
            String sys = System.getProperty("os.name").toLowerCase();
            if (sys.contains("win")) {
                os = OS.WINDOWS;
            } else if (sys.contains("nix") || sys.contains("nux")) {
                os = OS.LINUX;
            } else if (sys.contains("mac")) {
                os = OS.MAC;
            } else if (sys.contains("sunos")) {
                os = OS.SOLARIS;
            }
        }
        return os;
    }
}
