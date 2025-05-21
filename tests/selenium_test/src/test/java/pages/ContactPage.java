package pages;

import org.openqa.selenium.*;

/**
 * Contact page of TryTestingThis website
 */
public class ContactPage extends BasePage {
    // URL
    private final String CONTACT_URL;
    
    // Navigation bar elements
    private final By homeLinkLocator = By.cssSelector(".navbar a[href='/']");
    private final By contactLinkLocator = By.cssSelector(".navbar a[href*='contact']");
    
    // Page content
    private final By pageContentLocator = By.tagName("body");
    
    /**
     * Constructor
     */
    public ContactPage(WebDriver driver, String baseUrl) {
        super(driver);
        this.CONTACT_URL = baseUrl + "/contact";
    }
    
    /**
     * Open contact page
     */
    public ContactPage openPage() {
        open(CONTACT_URL);
        System.out.println("Opening contact page: " + CONTACT_URL);
        return this;
    }
    
    /**
     * Click home link
     */
    public void clickHomeLink() {
        clickElement(homeLinkLocator);
        System.out.println("Clicking home link");
    }
    
    /**
     * Get page content
     */
    public String getPageContent() {
        return getElementText(pageContentLocator);
    }
    
    /**
     * Check if on contact page
     */
    public boolean isOnContactPage() {
        return driver.getCurrentUrl().contains("/contact");
    }
    
    /**
     * Get page title
     */
    @Override
    public String getPageTitle() {
        return super.getPageTitle();
    }
}