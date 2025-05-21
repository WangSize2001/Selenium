package utils;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import java.util.Set;

/**
 * Cookie management utility class
 */
public class CookieManager {
    private WebDriver driver;

    public CookieManager(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Add Cookie
     * @param name Cookie name
     * @param value Cookie value
     */
    public void addCookie(String name, String value) {
        try {
            Cookie cookie = new Cookie(name, value);
            driver.manage().addCookie(cookie);
            System.out.println("Add Cookie: " + name + "=" + value);
        } catch (Exception e) {
            System.out.println("Add Cookie failed: " + e.getMessage());
            // Try using domain-related cookies
            String domain = driver.getCurrentUrl().replaceAll("https?://", "").split("/")[0];
            Cookie cookie = new Cookie(name, value, domain);
            driver.manage().addCookie(cookie);
            System.out.println("Add Cookie using domain: " + name + "=" + value + ", domain=" + domain);
        }
    }

    /**
     * Get Cookie value
     * @param name Cookie name
     * @return Cookie value, returns null if Cookie doesn't exist
     */
    public String getCookieValue(String name) {
        Cookie cookie = driver.manage().getCookieNamed(name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * Delete specific Cookie
     * @param name Cookie name
     */
    public void deleteCookie(String name) {
        driver.manage().deleteCookieNamed(name);
        System.out.println("Deleting Cookie: " + name);
    }

    /**
     * Delete all Cookies
     */
    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
        System.out.println("Deleting all Cookies");
    }

    /**
     * Get all Cookies
     * @return Cookie set
     */
    public Set<Cookie> getAllCookies() {
        return driver.manage().getCookies();
    }

    /**
     * Print all Cookies
     */
    public void printAllCookies() {
        Set<Cookie> cookies = getAllCookies();
        System.out.println("=== All Cookies ===");
        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() + ": " + cookie.getValue());
        }
        System.out.println("=================");
    }
}