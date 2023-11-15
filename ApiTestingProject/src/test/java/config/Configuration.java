package config;

public class Configuration {

    public static String host = "http://todo.ly";
    public static String password = "12345-Test";
    private static String email;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        Configuration.email = email;
    }
}
