package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration file reading utility class
 */
public class ConfigReader {
    private Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    public ConfigReader() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            properties.load(fis);
        } catch (IOException e) {
            System.err.println("Cannot load configuration file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getBaseUrl() {
        return getProperty("baseUrl");
    }

    public String getBrowser() {
        return getProperty("browser");
    }

    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicitWait"));
    }

    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicitWait"));
    }

    public int getPageLoadTimeout() {
        return Integer.parseInt(getProperty("pageLoadTimeout"));
    }

    public String getTestUsername() {
        return getProperty("testUsername");
    }

    public String getTestPassword() {
        return getProperty("testPassword");
    }

    public String getDownloadPath() {
        return getProperty("downloadPath");
    }

    public String getVulnWebUrl() {
        return "http://testphp.vulnweb.com";
    }

    public String getVulnWebUsername() {
        return "test";
    }

    public String getVulnWebPassword() {
        return "test";
    }
}