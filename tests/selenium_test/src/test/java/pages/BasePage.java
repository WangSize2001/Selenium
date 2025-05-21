package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Base class for all page objects
 */
public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    /**
     * Open URL
     */
    protected void open(String url) {
        driver.get(url);
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Find element using explicit wait
     */
    protected WebElement waitForElementVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Find element using explicit wait until clickable
     */
    protected WebElement waitForElementClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait using explicit wait until element is invisible
     */
    protected boolean waitForElementInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Click element
     */
    protected void clickElement(By locator) {
        waitForElementClickable(locator).click();
    }

    /**
     * Click element using JavaScript
     */
    protected void jsClick(By locator) {
        WebElement element = waitForElementVisible(locator);
        js.executeScript("arguments[0].click();", element);
    }

    /**
     * Enter text
     */
    protected void enterText(By locator, String text) {
        WebElement element = waitForElementVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get element text
     */
    protected String getElementText(By locator) {
        return waitForElementVisible(locator).getText();
    }

    /**
     * Get element attribute
     */
    protected String getElementAttribute(By locator, String attribute) {
        return waitForElementVisible(locator).getAttribute(attribute);
    }

    /**
     * Check if element is displayed
     */
    protected boolean isElementDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Check if element exists
     */
    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Select text from dropdown
     */
    protected void selectFromDropdown(By locator, String text) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByVisibleText(text);
    }

    /**
     * Select by index from dropdown
     */
    protected void selectFromDropdownByIndex(By locator, int index) {
        Select select = new Select(waitForElementVisible(locator));
        select.selectByIndex(index);
    }

    /**
     * Select multiple options from multi-select dropdown
     */
    protected void selectMultipleOptions(By locator, String[] options) {
        Select select = new Select(waitForElementVisible(locator));
        for (String option : options) {
            select.selectByVisibleText(option);
        }
    }

    /**
     * Select radio button
     */
    protected void selectRadioButton(By locator) {
        WebElement radioButton = waitForElementClickable(locator);
        if (!radioButton.isSelected()) {
            radioButton.click();
        }
    }

    /**
     * Check checkbox
     */
    protected void checkCheckbox(By locator, boolean check) {
        WebElement checkbox = waitForElementClickable(locator);
        if ((check && !checkbox.isSelected()) || (!check && checkbox.isSelected())) {
            checkbox.click();
        }
    }

    /**
     * Check if checkbox is selected
     */
    protected boolean isCheckboxSelected(By locator) {
        return waitForElementVisible(locator).isSelected();
    }

    /**
     * Check if radio button is selected
     */
    protected boolean isRadioButtonSelected(By locator) {
        return waitForElementVisible(locator).isSelected();
    }

    /**
     * Hover over element
     */
    protected void hoverElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        actions.moveToElement(element).perform();
    }

    /**
     * Drag and drop operation
     */
    protected void dragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitForElementVisible(sourceLocator);
        WebElement target = waitForElementVisible(targetLocator);
        actions.dragAndDrop(source, target).perform();
    }

    /**
     * Drag and drop using JavaScript (alternative method)
     */
    protected void jsDragAndDrop(By sourceLocator, By targetLocator) {
        WebElement source = waitForElementVisible(sourceLocator);
        WebElement target = waitForElementVisible(targetLocator);
        
        String script = "function createEvent(typeOfEvent) {\n" +
                "  var event = document.createEvent(\"CustomEvent\");\n" +
                "  event.initCustomEvent(typeOfEvent, true, true, null);\n" +
                "  event.dataTransfer = {\n" +
                "    data: {},\n" +
                "    setData: function (key, value) {\n" +
                "      this.data[key] = value;\n" +
                "    },\n" +
                "    getData: function (key) {\n" +
                "      return this.data[key];\n" +
                "    }\n" +
                "  };\n" +
                "  return event;\n" +
                "}\n" +
                "\n" +
                "function dispatchEvent(element, event, transferData) {\n" +
                "  if (transferData !== undefined) {\n" +
                "    event.dataTransfer = transferData;\n" +
                "  }\n" +
                "  if (element.dispatchEvent) {\n" +
                "    element.dispatchEvent(event);\n" +
                "  } else if (element.fireEvent) {\n" +
                "    element.fireEvent(\"on\" + event.type, event);\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "function simulateHTML5DragAndDrop(element, target) {\n" +
                "  var dragStartEvent = createEvent('dragstart');\n" +
                "  dispatchEvent(element, dragStartEvent);\n" +
                "  var dropEvent = createEvent('drop');\n" +
                "  dispatchEvent(target, dropEvent, dragStartEvent.dataTransfer);\n" +
                "  var dragEndEvent = createEvent('dragend');\n" +
                "  dispatchEvent(element, dragEndEvent, dropEvent.dataTransfer);\n" +
                "}\n" +
                "\n" +
                "simulateHTML5DragAndDrop(arguments[0], arguments[1]);";
        
        js.executeScript(script, source, target);
    }

    /**
     * Double click element
     */
    protected void doubleClickElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        actions.doubleClick(element).perform();
    }

    /**
     * Upload file
     */
    protected void uploadFile(By locator, String filePath) {
        WebElement fileInput = waitForElementVisible(locator);
        fileInput.sendKeys(filePath);
    }

    /**
     * Get all dropdown options
     */
    protected List<WebElement> getDropdownOptions(By locator) {
        Select select = new Select(waitForElementVisible(locator));
        return select.getOptions();
    }

    /**
     * Get selected dropdown option text
     */
    protected String getSelectedDropdownOptionText(By locator) {
        Select select = new Select(waitForElementVisible(locator));
        return select.getFirstSelectedOption().getText();
    }

    /**
     * Scroll to element using JavaScript
     */
    protected void scrollToElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    /**
     * Scroll to bottom of page using JavaScript
     */
    protected void scrollToBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

    /**
     * Scroll to top of page using JavaScript
     */
    protected void scrollToTop() {
        js.executeScript("window.scrollTo(0, 0);");
    }

    /**
     * Get page source
     */
    protected String getPageSource() {
        return driver.getPageSource();
    }

    /**
     * Get current URL
     */
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Navigate back in browser
     */
    protected void navigateBack() {
        driver.navigate().back();
    }

    /**
     * Navigate forward in browser
     */
    protected void navigateForward() {
        driver.navigate().forward();
    }

    /**
     * Refresh page
     */
    protected void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Accept alert/confirmation dialog
     */
    protected String acceptAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }

    /**
     * Dismiss alert/confirmation dialog
     */
    protected String dismissAlert() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.dismiss();
        return alertText;
    }

    /**
     * Enter text in alert/confirmation dialog
     */
    protected void sendKeysToAlert(String text) {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.sendKeys(text);
    }

    /**
     * Switch to iframe
     */
    protected void switchToFrame(By frameLocator) {
        WebElement frame = waitForElementVisible(frameLocator);
        driver.switchTo().frame(frame);
    }
    
    /**
     * Get element value
     */
    protected String getElementValue(By locator) {
        WebElement element = waitForElementVisible(locator);
        return element.getAttribute("value");
    }

    /**
     * Switch back to main document
     */
    protected void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}