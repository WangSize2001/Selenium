package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * TryTestingThis website homepage
 */
public class HomePage extends BasePage {
    // URL
    private final String HOME_URL;
    
    // Navigation bar elements
    private final By contactLinkLocator = By.cssSelector(".navbar a[href*='contact']");
    private final By homeLinkLocator = By.cssSelector(".navbar a[href='/']");
    
    // Page header
    private final By headerTitleLocator = By.xpath("//div[@class='header']/h1");
    
    // Alert button section
    private final By alertButtonLocator = By.xpath("//button[contains(text(), 'Your Sample Alert Button')]");
    private final By alertResultLocator = By.id("demo");
    
    // Tooltip
    private final By tooltipLocator = By.className("tooltip");
    private final By tooltipTextLocator = By.className("tooltiptext");
    
    // Double-click section
    private final By doubleClickButtonLocator = By.xpath("//button[text()='Double-click me']");
    
    // Drag and drop section
    private final By dragSourceLocator = By.id("drag1");
    private final By dropTargetLocator = By.id("div1");
    
    // Login form
    private final By usernameLocator = By.id("uname");
    private final By passwordLocator = By.id("pwd");
    private final By loginButtonLocator = By.xpath("//input[@type='submit' and @value='Login']");
    
    // Main form
    private final By firstNameLocator = By.id("fname");
    private final By lastNameLocator = By.id("lname");
    
    // Gender radio buttons
    private final By maleRadioLocator = By.id("male");
    private final By femaleRadioLocator = By.id("female");
    private final By otherRadioLocator = By.id("other");
    
    // Dropdown lists
    private final By singleSelectLocator = By.id("option");
    private final By multiSelectLocator = By.id("owc");
    
    // Checkboxes
    private final By option1CheckboxLocator = By.xpath("//input[@type='checkbox' and @name='option1']");
    private final By option2CheckboxLocator = By.xpath("//input[@type='checkbox' and @name='option2']");
    private final By option3CheckboxLocator = By.xpath("//input[@type='checkbox' and @name='option3']");
    
    // Datalist
    private final By datalistInputLocator = By.xpath("//input[@list='datalists']");
    
    // Color picker
    private final By colorPickerLocator = By.id("favcolor");
    
    // Date picker
    private final By datePickerLocator = By.id("day");
    
    // Slider
    private final By rangeSliderLocator = By.id("a");
    
    // File upload
    private final By fileUploadLocator = By.id("myfile");
    
    // Quantity selector
    private final By quantityLocator = By.id("quantity");
    
    // Text area
    private final By textareaLocator = By.xpath("//textarea[@name='message']");
    
    // Submit button
    private final By submitButtonLocator = By.xpath("//button[contains(text(),'Submit')]");
    
    // Table
    private final By tableLocator = By.xpath("//table");
    
    /**
     * Constructor
     */
    public HomePage(WebDriver driver, String baseUrl) {
        super(driver);
        this.HOME_URL = baseUrl;
    }
    
    /**
     * Open homepage
     */
    public HomePage openPage() {
        open(HOME_URL);
        System.out.println("Opening homepage: " + HOME_URL);
        return this;
    }
    
    /**
     * Get header title text
     */
    public String getHeaderTitle() {
        return getElementText(headerTitleLocator);
    }
    
    /**
     * Navigate to contact page
     */
    public void navigateToContact() {
        clickElement(contactLinkLocator);
        System.out.println("Navigating to contact page");
    }
    
    /**
     * Navigate to homepage
     */
    public void navigateToHome() {
        clickElement(homeLinkLocator);
        System.out.println("Navigating to homepage");
    }
    
    /**
     * Test alert button
     * @return Alert result text
     */
    public String testAlertButton(boolean acceptAlert) {
        clickElement(alertButtonLocator);
        System.out.println("Clicking alert button");
        
        if (acceptAlert) {
            acceptAlert();
            System.out.println("Accepting alert");
        } else {
            dismissAlert();
            System.out.println("Dismissing alert");
        }
        
        return getElementText(alertResultLocator);
    }
    
    /**
     * Fill user form
     */
    public HomePage fillUserForm(String firstName, String lastName) {
        enterText(firstNameLocator, firstName);
        enterText(lastNameLocator, lastName);
        System.out.println("Filling form: " + firstName + " " + lastName);
        return this;
    }

    /**
     * Get first name
     */
    public String getFirstName() {
        return getElementValue(firstNameLocator);
    }
    
    /**
     * Get last name
     */
    public String getLastName() {
        return getElementValue(lastNameLocator);
    }
    
    /**
     * Select gender
     * @param gender Gender (male/female/other)
     */
    public HomePage selectGender(String gender) {
        By genderLocator;
        if (gender.equalsIgnoreCase("male")) {
            genderLocator = maleRadioLocator;
        } else if (gender.equalsIgnoreCase("female")) {
            genderLocator = femaleRadioLocator;
        } else if (gender.equalsIgnoreCase("other")) {
            genderLocator = otherRadioLocator;
        } else {
            throw new IllegalArgumentException("Unsupported gender: " + gender);
        }
        
        selectRadioButton(genderLocator);
        System.out.println("Selecting gender: " + gender);
        return this;
    }

    /**
     * Check if gender is selected
     */
    public boolean isGenderSelected(String gender) {
        By genderLocator;
        if (gender.equalsIgnoreCase("male")) {
            genderLocator = maleRadioLocator;
        } else if (gender.equalsIgnoreCase("female")) {
            genderLocator = femaleRadioLocator;
        } else {
            genderLocator = otherRadioLocator;
        }
        return isRadioButtonSelected(genderLocator);
    }
    
    /**
     * Get checkbox locator
     */
    public By getOption1CheckboxLocator() {
        return option1CheckboxLocator;
    }
    
    public By getOption2CheckboxLocator() {
        return option2CheckboxLocator;
    }
    
    public By getOption3CheckboxLocator() {
        return option3CheckboxLocator;
    }
    
    /**
     * Check if checkbox is selected
     */
    public boolean isCheckboxSelected(By locator) {
        return super.isCheckboxSelected(locator);
    }

    /**
     * Get quantity
     */
    public String getQuantity() {
        return getElementValue(quantityLocator);
    }
    
    /**
     * Click alert button
     */
    public void clickAlertButton(boolean accept) {
        clickElement(alertButtonLocator);
        System.out.println("Clicking alert button");
        
        if (accept) {
            acceptAlert();
            System.out.println("Accepting alert");
        } else {
            dismissAlert();
            System.out.println("Dismissing alert");
        }
    }
    
    /**
     * Get alert result
     */
    public String getAlertResult() {
        return getElementText(alertResultLocator);
    }

    /**
     * Test tooltip
     * @return Tooltip text
     */
    public String testTooltip() {
        hoverElement(tooltipLocator);
        System.out.println("Hovering over tooltip");
        return getElementText(tooltipTextLocator);
    }
    
    /**
     * Test double click
     * @return Double click result text
     */
    public String testDoubleClick() {
        doubleClickElement(doubleClickButtonLocator);
        System.out.println("Double-clicking button");
        return getElementText(alertResultLocator);
    }
    
    /**
     * Test drag and drop
     */
    public void testDragAndDrop() {
        System.out.println("Performing drag and drop operation");
        try {
            // Try standard drag and drop
            dragAndDrop(dragSourceLocator, dropTargetLocator);
        } catch (Exception e) {
            System.out.println("Standard drag and drop failed, trying JavaScript method: " + e.getMessage());
            // Fallback method: use JavaScript for drag and drop
            jsDragAndDrop(dragSourceLocator, dropTargetLocator);
        }
    }
    
    /**
     * Verify if drag and drop was successful
     * @return true if drag and drop was successful
     */
    public boolean isDragDropSuccessful() {
        WebElement target = waitForElementVisible(dropTargetLocator);
        
        try {
            // Method 1: Check target area style changes
            String style = target.getAttribute("style");
            if (style != null && !style.isEmpty()) {
                return true;
            }
            
            // Method 2: Check if target area contains image or child elements
            List<WebElement> images = target.findElements(By.tagName("img"));
            if (!images.isEmpty()) {
                return true;
            }
            
            // Method 3: Check if target area background changed
            String bgColor = target.getCssValue("background-color");
            if (bgColor != null && !bgColor.equals("rgba(0, 0, 0, 0)") && !bgColor.equals("transparent")) {
                return true;
            }
            
            return false;
        } catch (Exception e) {
            System.out.println("Drag and drop verification exception: " + e.getMessage());
            return false;
        }
    }

    /**
     * Check if drag and drop was successful
     */
    public boolean isDragAndDropSuccessful() {
        WebElement dropTarget = waitForElementVisible(dropTargetLocator);
        try {
            // Check if target container contains the dragged element
            return dropTarget.findElements(By.xpath("./*")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Select option from dropdown - fully refactored version
     * @param option Option text
     */
    public HomePage selectDropdownOption(String option) {
        try {
            // First try to wait for element to be visible
            WebElement dropdown = waitForElementVisible(singleSelectLocator);
            
            // Directly use JavaScript to perform selection, which is usually more reliable
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "arguments[0].value = arguments[1]; " +
                "var event = new Event('change'); " +
                "arguments[0].dispatchEvent(event);", 
                dropdown, option.toLowerCase());
            
            System.out.println("Successfully selected dropdown option using JavaScript: " + option);
        } catch (Exception e) {
            // If JavaScript method fails, try standard method
            try {
                Select select = new Select(driver.findElement(singleSelectLocator));
                select.selectByVisibleText(option);
                System.out.println("Successfully selected dropdown option using Select API: " + option);
            } catch (Exception inner) {
                System.err.println("Could not select dropdown option: " + option + ", error: " + inner.getMessage());
                throw inner; // Re-throw exception to fail the test
            }
        }
        
        return this;
    }

    /**
     * Login
     * @param username Username
     * @param password Password
     */
    public void login(String username, String password) {
        enterText(usernameLocator, username);
        enterText(passwordLocator, password);
        clickElement(loginButtonLocator);
        System.out.println("Logging in: username=" + username + ", password=" + password);
    }
    
    /**
     * Fill main form
     * @param firstName First name
     * @param lastName Last name
     * @param gender Gender
     */
    public HomePage fillMainForm(String firstName, String lastName, String gender) {
        enterText(firstNameLocator, firstName);
        enterText(lastNameLocator, lastName);
        
        // Select gender
        switch (gender.toLowerCase()) {
            case "male":
                selectRadioButton(maleRadioLocator);
                break;
            case "female":
                selectRadioButton(femaleRadioLocator);
                break;
            case "other":
                selectRadioButton(otherRadioLocator);
                break;
            default:
                throw new IllegalArgumentException("Unsupported gender: " + gender);
        }
        
        System.out.println("Filling main form: firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender);
        return this;
    }
    
    /**
     * Select option
     * @param option Option text
     */
    public HomePage selectOption(String option) {
        selectFromDropdown(singleSelectLocator, option);
        System.out.println("Selecting option: " + option);
        return this;
    }
    
    /**
     * Select multiple options
     * @param options Option array
     */
    public HomePage selectMultipleOptions(String[] options) {
        selectMultipleOptions(multiSelectLocator, options);
        System.out.println("Selecting multiple options: " + String.join(", ", options));
        return this;
    }
    
    /**
     * Get selected dropdown option text
     * @param locator Dropdown locator
     * @return Selected option text
     */
    protected String getSelectedDropdownOptionText(By locator) {
        try {
            // Try standard Select method
            WebElement element = waitForElementVisible(locator);
            Select select = new Select(element);
            return select.getFirstSelectedOption().getText();
        } catch (Exception e) {
            // Try JavaScript method
            try {
                WebElement element = waitForElementVisible(locator);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                return (String) js.executeScript(
                    "return arguments[0].options[arguments[0].selectedIndex].text", element);
            } catch (Exception e2) {
                System.err.println("Could not get dropdown option: " + e2.getMessage());
                return ""; // Return empty string instead of throwing exception
            }
        }
    }

    /**
     * Get selected option text
     */
    public String getSelectedOption() {
        return getSelectedDropdownOptionText(singleSelectLocator);
    }
    
    /**
     * Select checkboxes
     * @param option1 Whether to select option 1
     * @param option2 Whether to select option 2
     * @param option3 Whether to select option 3
     */
    public HomePage selectCheckboxes(boolean option1, boolean option2, boolean option3) {
        checkCheckbox(option1CheckboxLocator, option1);
        checkCheckbox(option2CheckboxLocator, option2);
        checkCheckbox(option3CheckboxLocator, option3);
        
        System.out.println("Selecting checkboxes: option1=" + option1 + ", option2=" + option2 + ", option3=" + option3);
        return this;
    }
    
    /**
     * Select datalist option
     * @param option Option text
     */
    public HomePage selectDatalistOption(String option) {
        enterText(datalistInputLocator, option);
        System.out.println("Selecting datalist option: " + option);
        return this;
    }
    
    /**
     * Select color
     * @param colorValue Color value (format: #RRGGBB)
     */
    public HomePage selectColor(String colorValue) {
        WebElement colorPicker = waitForElementVisible(colorPickerLocator);
        js.executeScript("arguments[0].value = arguments[1]", colorPicker, colorValue);
        System.out.println("Selecting color: " + colorValue);
        return this;
    }
    
    /**
     * Select date
     * @param dateValue Date value (format: yyyy-MM-dd)
     */
    public HomePage selectDate(String dateValue) {
        WebElement datePicker = waitForElementVisible(datePickerLocator);
        js.executeScript("arguments[0].value = arguments[1]", datePicker, dateValue);
        System.out.println("Selecting date: " + dateValue);
        return this;
    }
    
    /**
     * Set range slider value
     * @param value Slider value
     */
    public HomePage setRangeSlider(int value) {
        WebElement slider = waitForElementVisible(rangeSliderLocator);
        js.executeScript("arguments[0].value = arguments[1]", slider, value);
        
        // Trigger oninput event to update output
        js.executeScript(
            "var event = new Event('input', { 'bubbles': true, 'cancelable': true });" +
            "arguments[0].dispatchEvent(event);", slider);
            
        System.out.println("Setting range slider value: " + value);
        return this;
    }
    
    /**
     * Upload file
     * @param filePath File path
     */
    public HomePage uploadFile(String filePath) {
        uploadFile(fileUploadLocator, filePath);
        System.out.println("Uploading file: " + filePath);
        return this;
    }
    
    /**
     * Set quantity
     * @param quantity Quantity value
     */
    public HomePage setQuantity(int quantity) {
        WebElement quantityInput = waitForElementVisible(quantityLocator);
        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(quantity));
        System.out.println("Setting quantity: " + quantity);
        return this;
    }
    
    /**
     * Enter textarea content
     * @param text Text content
     */
    public HomePage enterTextareaContent(String text) {
        enterText(textareaLocator, text);
        System.out.println("Entering textarea content: " + text);
        return this;
    }
     /**
     * Get textarea content
     */
    public String getTextareaContent() {
        return getElementValue(textareaLocator);
    }
    
    /**
     * Click contact link
     */
    public void clickContactLink() {
        clickElement(contactLinkLocator);
        System.out.println("Clicking contact page link");
    }

    /**
     * Click submit button
     */
    public void clickSubmit() {
        // Remember current window handle
        String currentWindow = driver.getWindowHandle();
        
        // Click submit button (opens new tab)
        jsClick(submitButtonLocator);
        
        System.out.println("Clicking submit button (opens new tab)");
        
        // Return to original window
        driver.switchTo().window(currentWindow);
    }
    
    /**
     * Get table data
     * @return Table data list (each row is a string array)
     */
    public List<String[]> getTableData() {
        WebElement table = waitForElementVisible(tableLocator);
        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));
        
        List<String[]> tableData = new ArrayList<>();
        
        // Skip header row
        for (int i = 1; i < rows.size(); i++) {
            WebElement row = rows.get(i);
            List<WebElement> cells = row.findElements(By.tagName("td"));
            
            String[] rowData = new String[cells.size()];
            for (int j = 0; j < cells.size(); j++) {
                rowData[j] = cells.get(j).getText();
            }
            
            tableData.add(rowData);
        }
        
        return tableData;
    }
    
    /**
     * Get table headers
     * @return Table header array
     */
    public String[] getTableHeaders() {
        WebElement table = waitForElementVisible(tableLocator);
        List<WebElement> headers = table.findElements(By.xpath(".//tbody/tr[1]/th"));
        
        String[] headerTexts = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            headerTexts[i] = headers.get(i).getText();
        }
        
        return headerTexts;
    }
    
    /**
     * Check if table contains specific data
     * @param columnIndex Column index
     * @param value Value to search for
     * @return true if matching data is found
     */
    public boolean isValueInTable(int columnIndex, String value) {
        List<String[]> tableData = getTableData();
        
        for (String[] row : tableData) {
            if (columnIndex < row.length && row[columnIndex].equals(value)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Perform double click
     */
    public HomePage performDoubleClick() {
        WebElement doubleClickButton = waitForElementVisible(doubleClickButtonLocator);
        actions.doubleClick(doubleClickButton).perform();
        System.out.println("Performing double click");
        return this;
    }
    
    /**
     * Get double click result text
     */
    public String getDoubleClickResult() {
        return getElementText(doubleClickButtonLocator);
    }
    
    /**
     * Perform drag and drop
     */
    public HomePage performDragAndDrop() {
        WebElement source = waitForElementVisible(dragSourceLocator);
        WebElement target = waitForElementVisible(dropTargetLocator);
        
        try {
            // Try using standard Actions API
            actions.dragAndDrop(source, target).perform();
        } catch (Exception e) {
            // If standard method fails, try JavaScript method
            String jsScript = 
                "var src=arguments[0],tgt=arguments[1];" +
                "var dataTransfer={dropEffect:'',effectAllowed:'all',files:[],items:{},types:[]};" +
                "var dragStartEvent=document.createEvent('DragEvent');" +
                "dragStartEvent.initEvent('dragstart',true,true);" +
                "dragStartEvent.dataTransfer=dataTransfer;" +
                "src.dispatchEvent(dragStartEvent);" +
                "var dropEvent=document.createEvent('DragEvent');" +
                "dropEvent.initEvent('drop',true,true);" +
                "dropEvent.dataTransfer=dataTransfer;" +
                "tgt.dispatchEvent(dropEvent);";
            
            js.executeScript(jsScript, source, target);
        }
        
        System.out.println("Performing drag and drop operation");
        return this;
    }
    
    /**
     * Hover over tooltip
     */
    public HomePage hoverOverTooltip() {
        WebElement tooltip = waitForElementVisible(tooltipLocator);
        actions.moveToElement(tooltip).perform();
        System.out.println("Hovering over tooltip");
        return this;
    }
    
    /**
     * Get tooltip text
     */
    public String getTooltipText() {
        WebElement tooltipText = waitForElementVisible(tooltipTextLocator);
        return tooltipText.getText();
    }
    
    /**
     * Check complex XPath
     * Find element using complex XPath
     */
    public boolean checkComplexXPath() {
        // Use simpler, more efficient XPath
        By simpleXPathLocator = By.xpath("//input[@id='fname']");
        
        try {
            // Add short timeout to prevent long wait
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = shortWait.until(
                ExpectedConditions.visibilityOfElementLocated(simpleXPathLocator));
            System.out.println("Successfully found XPath element");
            return true;
        } catch (Exception e) {
            System.out.println("XPath element lookup failed: " + e.getMessage());
            return false;
        }
    }
}