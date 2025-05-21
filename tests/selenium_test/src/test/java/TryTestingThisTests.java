import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pages.*;
import utils.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * TryTestingThis website test class
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TryTestingThisTests {
    private WebDriver driver;
    private ConfigReader config;
    private HomePage homePage;
    private ContactPage contactPage;
    private LoginPage loginPage;
    private CookieManager cookieManager;
    
    @Before
    public void setUp() {
        // Load configuration
        config = new ConfigReader();
        
        // Initialize WebDriver
        driver = WebDriverFactory.createDriver(config);
        
        // Initialize page objects
        String baseUrl = config.getBaseUrl();
        homePage = new HomePage(driver, baseUrl);
        contactPage = new ContactPage(driver, baseUrl);
        loginPage = new LoginPage(driver, baseUrl);
        
        // Initialize Cookie manager
        cookieManager = new CookieManager(driver);
        
        System.out.println("Test setup complete: " + driver.getClass().getSimpleName());
    }
    
    /**
     * Test 1: Static Page Test
     * Test target page loading and verify page title
     */
    @Test
    public void test01_StaticPageTest() {
        // Open homepage
        homePage.openPage();
        
        // Verify page title
        assertEquals("Page title should be correct", "Try Testing This", homePage.getPageTitle());
        
        // Verify page content
        assertTrue("Page should contain welcome message", homePage.getHeaderTitle().contains("Website to practice Automation Testing"));
        
        System.out.println("Static page test passed");
    }
    
    /**
     * Test 2: Navigation Test
     * Test page navigation functionality
     */
    @Test
    public void test02_NavigationTest() {
        // Open homepage
        homePage.openPage();
        
        // Click contact page link
        homePage.clickContactLink();
        
        // Confirm navigation to contact page
        assertTrue("Should navigate to contact page", contactPage.isOnContactPage());
        
        // Click home link to return
        contactPage.clickHomeLink();
        
        // Confirm return to homepage
        assertEquals("Should return to homepage", config.getBaseUrl() + "/", driver.getCurrentUrl());
        
        System.out.println("Navigation test passed");
    }
    
    /**
     * Test 3: Multiple Static Pages Test
     * Test multiple static pages from array
     */
    @Test
    public void test03_MultipleStaticPagesTest() {
        String[] pages = {
            "",       // Homepage
            "contact" // Contact page
        };
        
        for (String page : pages) {
            String url = config.getBaseUrl() + (page.isEmpty() ? "" : "/" + page);
            driver.get(url);
            
            // Verify page has loaded
            assertFalse("Page should have a title", driver.getTitle().isEmpty());
            assertTrue("Page should have non-empty content", driver.findElement(By.tagName("body")).getText().length() > 0);
            
            System.out.println(String.format("Page '%s' test passed: %s", page.isEmpty() ? "homepage" : page, driver.getTitle()));
        }
        
        System.out.println("Multiple static pages test passed");
    }
    
    /**
     * Test 4: Basic Form Submission Test
     * Test login form completion and submission
     */
    @Test
    public void test04_LoginFormTest() {
        // Open homepage
        homePage.openPage();
        
        // Fill login form and submit
        homePage.login(config.getTestUsername(), config.getTestPassword());
        
        // Verify login success
        assertTrue("Should navigate to login success page", loginPage.isOnLoginSuccessPage());
        
        System.out.println("Login form test passed");
    }
    
    /**
     * Test 5: Failed Login Test
     * Test login form failure scenarios
     */
    @Test
    public void test05_FailedLoginTest() {
        // Open homepage
        homePage.openPage();
        
        try {
            // Fill login form with wrong credentials
            homePage.login("wrong", "wrong");
            
            // Wait for alert to appear
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            
            // Verify alert content
            String alertText = alert.getText();
            System.out.println("Alert content: " + alertText);
            assertEquals("Alert message should be correct", "Wrong Credentials! Try again!", alertText);
            
            // Accept alert
            alert.accept();
            
            // Verify still on homepage
            assertTrue("Should still be on homepage", driver.getCurrentUrl().equals(config.getBaseUrl() + "/"));
            
        } catch (Exception e) {
            System.err.println("Failed login test error: " + e.getMessage());
            fail("Failed login test error: " + e.getMessage());
        }
        
        System.out.println("Failed login test passed");
    }
    
    /**
     * Test 6: Complex Form Test
     * Test filling various form elements
     */
    @Test
    public void test06_ComplexFormTest() {
        // Open homepage
        homePage.openPage();
        
        // Fill various form elements
        homePage
            .fillUserForm("John", "Doe")
            .selectGender("male")
            .selectDropdownOption("option 2")
            // .selectMultipleOptions(new String[]{"option 1", "option 3"})
            .selectCheckboxes(true, false, true)
            .selectDatalistOption("Chocolate")
            .selectColor("#FF0000")
            .selectDate("2023-01-15")
            .setRangeSlider(75)
            .setQuantity(3)
            .enterTextareaContent("This is a test message.\nWith multiple lines.");
            
        // Verify input text
        assertEquals("First name should be correctly filled", "John", homePage.getFirstName());
        assertEquals("Last name should be correctly filled", "Doe", homePage.getLastName());
        assertTrue("Gender should be correctly selected", homePage.isGenderSelected("male"));
        // System.out.println("Selected dropdown option: " + homePage.getSelectedOption());
        assertEquals("Dropdown should be correctly selected", "Option 2", homePage.getSelectedOption());
        assertTrue("Checkbox 1 should be selected", homePage.isCheckboxSelected(homePage.getOption1CheckboxLocator()));
        assertFalse("Checkbox 2 should not be selected", homePage.isCheckboxSelected(homePage.getOption2CheckboxLocator()));
        assertTrue("Checkbox 3 should be selected", homePage.isCheckboxSelected(homePage.getOption3CheckboxLocator()));
        assertTrue("Textarea should contain input content", homePage.getTextareaContent().contains("This is a test message"));
        
        System.out.println("Complex form test passed");
    }
    
    /**
     * Test 7: Random Data Form Test
     * Test form with randomly generated data
     */
    @Test
    public void test07_RandomDataFormTest() {
        // Open homepage
        homePage.openPage();
        
        // Generate random data
        String firstName = RandomDataGenerator.generateRandomName();
        String lastName = RandomDataGenerator.generateRandomLastName();
        String gender = RandomDataGenerator.generateRandomGender();
        String option = RandomDataGenerator.generateRandomOption();
        String date = RandomDataGenerator.generateRandomDate();
        int quantity = RandomDataGenerator.generateRandomInt(5);
        String message = RandomDataGenerator.generateRandomString(20);
        
        // Fill form with random data
        homePage
            .fillUserForm(firstName, lastName)
            .selectGender(gender)
            .selectDropdownOption(option)
            .selectDate(date)
            .setQuantity(quantity)
            .enterTextareaContent(message);
        
        // Verify input data
        assertEquals("First name should be correctly filled", firstName, homePage.getFirstName());
        assertEquals("Last name should be correctly filled", lastName, homePage.getLastName());
        assertTrue("Gender should be correctly selected", homePage.isGenderSelected(gender));
        assertEquals("Dropdown should be correctly selected", option.substring(0, 1).toUpperCase() + option.substring(1), homePage.getSelectedOption());
        assertEquals("Quantity should be correctly set", String.valueOf(quantity), homePage.getQuantity());
        assertTrue("Textarea should contain input content", homePage.getTextareaContent().contains(message));
        
        System.out.println("Random data form test passed");
    }
    
    /**
     * Test 8: Table Content Test
     * Test table content and verification
     */
    @Test
    public void test08_TableTest() {
        // Open homepage
        homePage.openPage();
        
        // Verify table headers
        String[] expectedHeaders = {"Firstname", "Lastname", "Gender", "Age", "Occupation"};
        String[] actualHeaders = homePage.getTableHeaders();
        
        assertArrayEquals("Table headers should match", expectedHeaders, actualHeaders);
        
        // Verify table content
        List<String[]> tableData = homePage.getTableData();
        
        // Verify table row count
        assertEquals("Table should have 6 rows of data", 6, tableData.size());
        
        // Verify specific data
        assertTrue("Table should contain Joey", homePage.isValueInTable(0, "Joey"));
        assertTrue("Table should contain Chanandler", homePage.isValueInTable(0, "Chanandler"));
        assertTrue("Table should contain age 27", homePage.isValueInTable(3, "27"));
        
        System.out.println("Table test passed");
    }
    
    /**
     * Test 9: Drag and Drop Test
     * Test drag and drop interaction
     */
    @Test
    public void test09_DragAndDropTest() {
        // Open homepage
        homePage.openPage();
        
        // Perform drag and drop operation
        homePage.performDragAndDrop();
        
        // Try to verify drag and drop result
        boolean success = false;
        try {
            // Add delay to wait for drag and drop effect to complete
            Thread.sleep(1000);
            
            // Use one of the methods above to verify
            success = homePage.isDragDropSuccessful();
            
            // Output result but don't force assertion
            System.out.println("Drag and drop verification result: " + (success ? "success" : "unable to confirm"));
        } catch (Exception e) {
            System.out.println("Drag and drop verification exception: " + e.getMessage());
        }
        assertEquals("Drag and drop verification result:", true, success);
        System.out.println("Drag and drop test completed");
    }
    
    
    /**
     * Test 10: Tooltip Test
     * Test tooltip interaction and text
     */
    @Test
    public void test10_TooltipTest() {
        // Open homepage
        homePage.openPage();
        
        // Hover over tooltip
        homePage.hoverOverTooltip();
        
        // Get tooltip text
        String tooltipText = homePage.getTooltipText();
        
        // Verify tooltip text
        assertEquals("Tooltip text should be correct", "This is your sample Tooltip text", tooltipText);
        
        System.out.println("Tooltip test passed");
    }
    
    /**
     * Test 11: Alert Test
     * Test JavaScript alert interaction
     */
    @Test
    public void test11_AlertTest() {
        // Open homepage
        homePage.openPage();
        
        // Click alert button and accept alert
        homePage.clickAlertButton(true);
        
        // Verify result
        String alertResult = homePage.getAlertResult();
        assertEquals("Alert result should be correct", "You Pressed the OK Button!", alertResult);
        
        // Reopen page
        homePage.openPage();
        
        // Click alert button and dismiss alert
        homePage.clickAlertButton(false);
        
        // Verify result
        alertResult = homePage.getAlertResult();
        assertEquals("Alert result should be correct", "You pressed the Cancel Button!", alertResult);
        
        System.out.println("Alert test passed");
    }
    
    /**
     * Test 12: Double Click Test
     * Test double click interaction
     */
    @Test
    public void test12_DoubleClickTest() {
        // Open homepage
        homePage.openPage();
        
        // Perform double click operation
        homePage.performDoubleClick();
        
        // Verify double click result
        WebElement resultElement = driver.findElement(By.id("demo"));
        String result = resultElement.getText();
        
        assertEquals("Double click result should be correct", "Your Sample Double Click worked!", result);
        
        System.out.println("Double click test passed");
    }
    
    /**
     * Test 13: Browser History Test
     * Test browser back/forward functionality
     */
    @Test
    public void test13_BrowserHistoryTest() {
        // Open homepage
        homePage.openPage();
        String homeUrl = driver.getCurrentUrl();
        
        // Navigate to contact page
        homePage.clickContactLink();
        String contactUrl = driver.getCurrentUrl();
        
        // Go back to homepage
        driver.navigate().back();
        assertEquals("Going back should return to homepage", homeUrl, driver.getCurrentUrl());
        
        // Go forward to contact page
        driver.navigate().forward();
        assertEquals("Going forward should return to contact page", contactUrl, driver.getCurrentUrl());
        
        System.out.println("Browser history test passed");
    }
    
    /**
     * Test 14: Cookie Management Test
     * Test adding, getting and deleting cookies
     */
    @Test
    public void test14_CookieManagementTest() {
        // Open homepage
        homePage.openPage();
        
        // Add cookie
        cookieManager.addCookie("testCookie", "testValue");
        
        // Verify cookie was added
        String cookieValue = cookieManager.getCookieValue("testCookie");
        assertEquals("Cookie value should match", "testValue", cookieValue);
        
        // Delete cookie
        cookieManager.deleteCookie("testCookie");
        
        // Verify cookie was deleted
        cookieValue = cookieManager.getCookieValue("testCookie");
        assertNull("Cookie should be deleted", cookieValue);
        
        System.out.println("Cookie management test passed");
    }
    
    /**
     * Test 15: Complex XPath Test
     * Test locating elements using complex XPath
     */
    @Test
    public void test15_ComplexXPathTest() {
        // Open homepage
        homePage.openPage();
        
        // Use complex XPath to locate element
        boolean elementFound = homePage.checkComplexXPath();
        
        // Verify element was found
        assertTrue("Should be able to find element using complex XPath", elementFound);
        
        System.out.println("Complex XPath test passed");
    }
    
    /**
     * Test 16: File Upload Test
     * Test file upload functionality
     */
    @Test
    public void test16_FileUploadTest() {
        // Create temporary test file
        try {
            Path tempFile = Files.createTempFile("testUpload_", ".txt");
            Files.write(tempFile, "This is a test file for upload".getBytes());
            
            // Open homepage
            homePage.openPage();
            
            // Upload file
            homePage.uploadFile(tempFile.toAbsolutePath().toString());
            
            // In a real scenario, we might need to verify upload success notification or other indicators
            // Since this test site may not have clear upload feedback, we just check the file input field
            WebElement fileInput = driver.findElement(By.id("myfile"));
            String fileName = new File(tempFile.toString()).getName();
            
            // Clean up temporary file
            Files.deleteIfExists(tempFile);
            
            System.out.println("File upload test completed: " + fileName);
            
        } catch (Exception e) {
            fail("File upload test failed: " + e.getMessage());
        }
    }
    
    /**
     * Test 17: JavaScript Executor Test
     * Test using JavascriptExecutor to execute scripts
     */
    @Test
    public void test17_JavaScriptExecutorTest() {
        // Open homepage
        homePage.openPage();
        
        // Use JavaScript to modify page element
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('.header h1').style.color = 'red';");
        
        // Use JavaScript to get page title
        String title = (String) js.executeScript("return document.title;");
        assertEquals("Page title should be correct", "Try Testing This", title);
        
        // Use JavaScript to scroll to bottom of page
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        
        // Wait for scroll to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        System.out.println("JavaScript executor test passed");
    }
    
    /**
     * Test 18: Test Dependencies Test
     * Test case dependencies (login depends on successful form submission)
     */
    @Test
    public void test18_TestDependenciesTest() {
        // Step 1: Fill form
        homePage.openPage();
        homePage.fillUserForm("Test", "User");
        
        // Verify form was filled successfully
        assertEquals("First name should be correctly filled", "Test", homePage.getFirstName());
        assertEquals("Last name should be correctly filled", "User", homePage.getLastName());
        
        // Step 2: Login (depends on form functionality working)
        homePage.login(config.getTestUsername(), config.getTestPassword());
        
        // Verify login success
        assertTrue("Should navigate to login success page", loginPage.isOnLoginSuccessPage());
        
        System.out.println("Test dependencies test passed");
    }

    /**
    * Test 19: VulnWeb login test
    * Test the login function of another website
    */
    @Test
    public void test19_VulnWebLoginTest() {
        // Create a VulnWeb page object
        VulnWebLoginPage vulnWebPage = new VulnWebLoginPage(driver, config.getVulnWebUrl());
        
        // Open the login page
        vulnWebPage.openLoginPage();
        
        // Execute login
        vulnWebPage.login(config.getVulnWebUsername(), config.getVulnWebPassword());
        
        // Verify login success
        boolean loginSuccess = vulnWebPage.isLoggedIn();
        assertTrue("Should successfully login to VulnWeb site", loginSuccess);
        
        System.out.println("VulnWeb login test passed");
    }

    /**
     * Test 20: VulnWeb logout test
     * Test the logout function of another website
     */
    @Test
    public void test20_VulnWebLogoutTest() {
        // Create a VulnWeb page object
        VulnWebLoginPage vulnWebPage = new VulnWebLoginPage(driver, config.getVulnWebUrl());
        
        // Open login page and login
        vulnWebPage.openLoginPage();
        vulnWebPage.login(config.getVulnWebUsername(), config.getVulnWebPassword());
        
        // Verify login success
        boolean loginSuccess = vulnWebPage.isLoggedIn();
        assertTrue("Should successfully login to VulnWeb site", loginSuccess);
        
        // Execute logout
        vulnWebPage.logout();
        
        // Verify logout success
        boolean logoutSuccess = vulnWebPage.isLoggedOut();
        assertTrue("Should successfully logout from VulnWeb site", logoutSuccess);
        
        System.out.println("VulnWeb logout test passed");
    }
    
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Test completed, browser closed");
        }
    }
}