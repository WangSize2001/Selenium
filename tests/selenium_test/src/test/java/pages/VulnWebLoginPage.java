package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import java.time.Duration;

/**
 * VulnWeb Login Page Class
 */
public class VulnWebLoginPage extends BasePage {
    // URL
    private final String BASE_URL;
    private final String LOGIN_URL;
    
    // Login form elements - using multiple locator strategies
    private final By usernameLocator = By.name("uname");
    private final By altUsernameLocator = By.xpath("//input[@name='uname']");
    private final By passwordLocator = By.name("pass");
    private final By altPasswordLocator = By.xpath("//input[@name='pass']");
    private final By loginButtonLocator = By.xpath("//input[@type='submit' and @value='login']");
    private final By altLoginButtonLocator = By.xpath("//input[@value='login']");
    
    // Login success elements
    private final By userInfoHeadingLocator = By.xpath("//h2[@id='pageName' and contains(text(),'john')]");
    private final By logoutLinkLocator = By.xpath("//a[text()='Logout test']");
    
    // Logout success elements
    private final By logoutMessageLocator = By.xpath("//h1[contains(text(),'You have been logged out')]");
    
    /**
     * Constructor
     */
    public VulnWebLoginPage(WebDriver driver, String baseUrl) {
        super(driver);
        this.BASE_URL = baseUrl;
        this.LOGIN_URL = baseUrl + "/login.php";
    }
    
    /**
     * Handle unexpected alerts
     */
    private void handleAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.alertIsPresent());
            String alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            System.out.println("Handled unexpected alert: " + alertText);
        } catch (NoAlertPresentException | TimeoutException e) {
            // No alert present - that's fine
        }
    }
    
    /**
     * Wait for element with alternative locator
     */
    private WebElement waitWithAlternatives(By primaryLocator, By alternativeLocator) {
        try {
            WebElement element = waitForElementVisible(primaryLocator);
            System.out.println("Found element using primary locator: " + primaryLocator);
            return element;
        } catch (TimeoutException e) {
            System.out.println("Primary locator failed, trying alternative: " + alternativeLocator);
            return waitForElementVisible(alternativeLocator);
        }
    }
    
    /**
     * Open login page with retries
     */
    public VulnWebLoginPage openLoginPage() {
        // Try multiple times to ensure page loads
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                handleAlert();
                System.out.println("Opening login page (attempt " + attempt + "): " + LOGIN_URL);
                driver.get(LOGIN_URL);
                handleAlert();
                
                // Wait for page to load by checking for login form
                WebElement usernameField = waitWithAlternatives(usernameLocator, altUsernameLocator);
                if (usernameField != null) {
                    System.out.println("Login page loaded successfully");
                    return this;
                }
            } catch (UnhandledAlertException e) {
                handleAlert();
            } catch (Exception e) {
                System.out.println("Error opening login page (attempt " + attempt + "): " + e.getMessage());
                if (attempt == 3) {
                    // On last attempt, just continue instead of throwing
                    System.out.println("All attempts to open login page failed");
                }
            }
            
            // Small delay before retry
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }
        return this;
    }
    
    /**
     * Perform login with retries
     */
    public VulnWebLoginPage login(String username, String password) {
        // Try multiple times to perform login
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                handleAlert();
                System.out.println("Login attempt " + attempt + ": " + username + "/" + password);
                
                // Clear cookies and storage to avoid state issues
                driver.manage().deleteAllCookies();
                
                // Find elements with fallback strategies
                WebElement usernameField = waitWithAlternatives(usernameLocator, altUsernameLocator);
                WebElement passwordField = waitWithAlternatives(passwordLocator, altPasswordLocator);
                WebElement loginButton = waitWithAlternatives(loginButtonLocator, altLoginButtonLocator);
                
                usernameField.clear();
                usernameField.sendKeys(username);
                
                passwordField.clear();
                passwordField.sendKeys(password);
                
                loginButton.click();
                System.out.println("Login submitted");
                handleAlert();
                
                // Wait a moment for the login to process
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
                
                // Verify login was successful
                if (isLoggedIn()) {
                    System.out.println("Login successful");
                    return this;
                } else {
                    System.out.println("Login verification failed, will retry");
                }
            } catch (UnhandledAlertException e) {
                handleAlert();
            } catch (Exception e) {
                System.out.println("Login process error (attempt " + attempt + "): " + e.getMessage());
                if (attempt == 3) {
                    System.out.println("All login attempts failed");
                }
            }
            
            // If not successful and not the last attempt, reload the page
            if (attempt < 3) {
                try {
                    openLoginPage();
                } catch (Exception e) {
                    System.out.println("Failed to reload login page: " + e.getMessage());
                }
            }
        }
        return this;
    }
    
    /**
     * Perform logout
     */
    public VulnWebLoginPage logout() {
        try {
            handleAlert();
            
            if (isLoggedIn()) {
                WebElement logoutLink = waitForElementVisible(logoutLinkLocator);
                logoutLink.click();
                System.out.println("Performing logout");
                handleAlert();
                
                // Small delay after logout
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            } else {
                System.out.println("Not logged in, cannot logout");
            }
        } catch (UnhandledAlertException e) {
            handleAlert();
        } catch (Exception e) {
            System.out.println("Logout process error: " + e.getMessage());
        }
        return this;
    }
    
    /**
     * Check if logged in
     */
    public boolean isLoggedIn() {
        try {
            handleAlert();
            
            // Multiple verification methods for robustness
            boolean correctUrl = false;
            boolean hasUserInfo = false;
            boolean hasLogoutLink = false;
            
            try {
                correctUrl = driver.getCurrentUrl().contains("userinfo.php");
            } catch (Exception e) {
                System.out.println("URL verification error: " + e.getMessage());
            }
            
            try {
                hasUserInfo = isElementPresent(userInfoHeadingLocator);
            } catch (Exception e) {
                System.out.println("User info verification error: " + e.getMessage());
            }
            
            try {
                hasLogoutLink = isElementPresent(logoutLinkLocator);
            } catch (Exception e) {
                System.out.println("Logout link verification error: " + e.getMessage());
            }
            
            System.out.println("Login verification: Correct URL=" + correctUrl + 
                              ", Found user info=" + hasUserInfo + 
                              ", Found logout link=" + hasLogoutLink);
            
            return correctUrl || hasUserInfo || hasLogoutLink;
        } catch (UnhandledAlertException e) {
            handleAlert();
            return false;
        } catch (Exception e) {
            System.out.println("Login verification failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if logged out
     */
    public boolean isLoggedOut() {
        try {
            handleAlert();
            
            // Multiple verification methods
            boolean hasLogoutMessage = false;
            boolean isLoginPage = false;
            boolean hasLoginForm = false;
            
            try {
                hasLogoutMessage = isElementPresent(logoutMessageLocator);
            } catch (Exception e) {
                System.out.println("Logout message verification error: " + e.getMessage());
            }
            
            try {
                String currentUrl = driver.getCurrentUrl();
                isLoginPage = currentUrl.contains("login.php") || currentUrl.contains("logout.php");
            } catch (Exception e) {
                System.out.println("URL verification error: " + e.getMessage());
            }
            
            try {
                hasLoginForm = isElementPresent(usernameLocator) && isElementPresent(passwordLocator);
            } catch (Exception e) {
                System.out.println("Login form verification error: " + e.getMessage());
            }
            
            System.out.println("Logout verification: Found logout message=" + hasLogoutMessage + 
                              ", On login page=" + isLoginPage + 
                              ", Has login form=" + hasLoginForm);
            
            return hasLogoutMessage || isLoginPage || hasLoginForm;
        } catch (UnhandledAlertException e) {
            handleAlert();
            return false;
        } catch (Exception e) {
            System.out.println("Logout verification failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get current page URL
     */
    public String getCurrentUrl() {
        try {
            handleAlert();
            return driver.getCurrentUrl();
        } catch (UnhandledAlertException e) {
            handleAlert();
            return driver.getCurrentUrl();
        } catch (Exception e) {
            System.out.println("Error getting current URL: " + e.getMessage());
            return "";
        }
    }
}