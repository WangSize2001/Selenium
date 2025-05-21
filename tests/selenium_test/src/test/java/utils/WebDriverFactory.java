package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * WebDriver factory class - responsible for creating and configuring WebDriver instances
 */
public class WebDriverFactory {

    /**
     * Create WebDriver based on configuration
     */
    public static WebDriver createDriver(ConfigReader config) {
        WebDriver driver;
        String browser = config.getBrowser().toLowerCase();

        switch (browser) {
            case "firefox":
                driver = setupFirefoxDriver(config);
                break;
            case "chrome":
            default:
                driver = setupChromeDriver(config);
                break;
        }

        // Set timeout values
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(config.getPageLoadTimeout()));
        driver.manage().window().maximize();

        return driver;
    }

    /**
     * Setup Chrome browser
     */
    private static WebDriver setupChromeDriver(ConfigReader config) {
        WebDriverManager.chromedriver().setup();
        
        ChromeOptions options = new ChromeOptions();
        
        // Headless mode
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        // Disable notifications
        options.addArguments("--disable-notifications");
        
        // Disable info bar
        options.addArguments("--disable-infobars");
        
        // Disable extensions
        options.addArguments("--disable-extensions");
        
        // Allow insecure content
        options.addArguments("--allow-running-insecure-content");
        
        // Set download path
        String downloadPath = System.getProperty("user.dir") + File.separator + config.getDownloadPath();
        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("download.default_directory", downloadPath);
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);
        
        return new ChromeDriver(options);
    }

    /**
     * Setup Firefox browser
     */
    private static WebDriver setupFirefoxDriver(ConfigReader config) {
        WebDriverManager.firefoxdriver().setup();
        
        FirefoxOptions options = new FirefoxOptions();
        
        // Headless mode
        if (config.isHeadless()) {
            options.addArguments("--headless");
        }
        
        // Configure download settings
        String downloadPath = System.getProperty("user.dir") + File.separator + config.getDownloadPath();
        File downloadDir = new File(downloadPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();
        }
        
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", downloadPath);
        profile.setPreference("browser.download.useDownloadDir", true);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
                "application/pdf;text/plain;application/text;text/xml;application/xml");
        
        options.setProfile(profile);
        
        return new FirefoxDriver(options);
    }
}