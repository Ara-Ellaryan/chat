package am.home.chat.utils.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Settings {
    private Properties properties;

    private Settings() {
        this.load();
    }

    private void load() {
        this.properties = new Properties();

        try (InputStream in = Settings.class.getClassLoader().getResourceAsStream("application.properties")) {
            this.properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getString(String key) {
        return this.properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(this.properties.getProperty(key));
    }

    public static Settings getInstance() {
        return SettingsInstanceCreator.settings;
    }

    private static class SettingsInstanceCreator {
        private static final Settings settings = new Settings();
    }
}
