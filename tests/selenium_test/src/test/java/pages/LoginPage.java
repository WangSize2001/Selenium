package pages;

import org.openqa.selenium.*;

/**
 * Login success page of TryTestingThis website
 */
public class LoginPage extends BasePage {
    // URL
    private final String LOGIN_URL;
    
    // Page elements
    private final By successMessageLocator = By.xpath("//h2[contains(text(), 'Login')]");
    
    /**
     * Constructor
     */
    public LoginPage(WebDriver driver, String baseUrl) {
        super(driver);
        this.LOGIN_URL = baseUrl + "/login.html";
    }
    
    /**
     * Open login page
     */
    public LoginPage openPage() {
        open(LOGIN_URL);
        System.out.println("Opening login page: " + LOGIN_URL);
        return this;
    }
    
    /**
     * Check if on login success page
     */
    public boolean isOnLoginSuccessPage() {
        try {
            return waitForElementVisible(successMessageLocator) != null;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Get success message text
     */
    public String getSuccessMessage() {
        return getElementText(successMessageLocator);
    }
    
    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        return super.getPageTitle();
    }
    
    /**
     * Get current page URL
     */
    public String getCurrentPageUrl() {
        return driver.getCurrentUrl();
    }
}