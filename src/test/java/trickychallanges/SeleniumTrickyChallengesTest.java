package trickychallanges;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class SeleniumTrickyChallengesTest {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);

        // Load your saved simulation HTML file
        driver.get("file:///C:/Users/User/eclipse-workspace/ScenraiosToPractice/selenium_tricky_challenges_html_css_simulation.html");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // ---------- Challenge 1: Dynamic List ----------
    @Test
    public void testDynamicList() {
        driver.findElement(By.id("add-item")).click();
        driver.findElement(By.id("add-item")).click();

        WebElement lastButton = driver.findElement(
            By.cssSelector("#todo-list .todo-item:first-child .complete")
        );
        lastButton.click();

        String opacity = lastButton.findElement(By.xpath("..//..")).getCssValue("opacity");
        Assert.assertEquals(opacity, "0.5", "Item should be marked as completed");
    }

    // ---------- Challenge 2: Shadow DOM ----------
    @Test
    public void testShadowDom() {
        WebElement shadowHost = driver.findElement(By.id("shadow-host"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();

        WebElement input = shadowRoot.findElement(By.cssSelector("#shadow-input"));
        input.sendKeys("Hello Shadow DOM");

        WebElement button = shadowRoot.findElement(By.cssSelector("#shadow-btn"));
        button.click();
    }

    // ---------- Challenge 3: Nested iFrames ----------
    @Test
    public void testNestedIframes() {
        driver.switchTo().frame("outer-frame");
        driver.switchTo().frame("inner-frame");

        WebElement deepBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("deep-btn")));
        deepBtn.click();

        driver.switchTo().defaultContent();
    }

    // ---------- Challenge 4: AJAX / Lazy Load ----------
    @Test
    public void testAjaxLazyLoad() {
        driver.findElement(By.id("start-ajax")).click();

        WebElement special = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//div[contains(text(),'special-keyword')]")
            )
        );
        Assert.assertTrue(special.getText().contains("special-keyword"));
    }

    // ---------- Challenge 5: Hidden File Upload ----------
    @Test
    public void testFileUpload() {
        WebElement fileInput = driver.findElement(By.id("real-file"));
        fileInput.sendKeys("C:/Users/YourName/Desktop/testfile.txt");

        WebElement result = driver.findElement(By.id("upload-result"));
        Assert.assertTrue(result.getText().contains("testfile.txt"));
    }

    // ---------- Challenge 6: Drag & Drop ----------
   
    @Test
    public void testDragAndDrop() {
        WebElement drag = driver.findElement(By.id("drag-1"));
        WebElement drop = driver.findElement(By.id("dropzone-1"));

        // HTML5 drag-drop workaround using JS (compatible with older Java versions)
        String js = "function simulateDragDrop(sourceNode, destinationNode) {"
                + "const EVENT_TYPES = { DRAG_END: 'dragend', DRAG_START: 'dragstart', DROP: 'drop' };"
                + "function createCustomEvent(type) {"
                + "var event = document.createEvent('CustomEvent');"
                + "event.initCustomEvent(type, true, true, null);"
                + "event.dataTransfer = { data: {}, setData: function(k,v){this.data[k]=v;}, getData: function(k){return this.data[k];} };"
                + "return event;"
                + "}"
                + "function dispatchEvent(node, type, event) { node.dispatchEvent(event); }"
                + "var dragStartEvent = createCustomEvent(EVENT_TYPES.DRAG_START);"
                + "dispatchEvent(sourceNode, EVENT_TYPES.DRAG_START, dragStartEvent);"
                + "var dropEvent = createCustomEvent(EVENT_TYPES.DROP);"
                + "dropEvent.dataTransfer = dragStartEvent.dataTransfer;"
                + "dispatchEvent(destinationNode, EVENT_TYPES.DROP, dropEvent);"
                + "var dragEndEvent = createCustomEvent(EVENT_TYPES.DRAG_END);"
                + "dragEndEvent.dataTransfer = dragStartEvent.dataTransfer;"
                + "dispatchEvent(sourceNode, EVENT_TYPES.DRAG_END, dragEndEvent);"
                + "}"
                + "simulateDragDrop(arguments[0], arguments[1]);";

        ((JavascriptExecutor) driver).executeScript(js, drag, drop);

        Assert.assertTrue(drop.getText().contains("drag-1 dropped"));
    }


    // ---------- Challenge 7: Context Menu ----------
    @Test
    public void testContextMenu() {
        WebElement target = driver.findElement(By.id("ctx-target"));
        actions.contextClick(target).perform();

        WebElement inspectBtn = driver.findElement(By.id("ctx-inspect"));
        wait.until(ExpectedConditions.elementToBeClickable(inspectBtn)).click();

        WebElement input = driver.findElement(By.id("inspected-value"));
        Assert.assertTrue(input.isDisplayed());
    }

    // ---------- Challenge 8: Hidden / Animated Element ----------
    @Test
    public void testHiddenAnimatedElement() {
        WebElement slideBtn = driver.findElement(By.id("slide-in"));
        slideBtn.click();

        WebElement target = driver.findElement(By.id("slide-target"));
        wait.until(ExpectedConditions.elementToBeClickable(target));
        target.click();
    }

    // ---------- Challenge 9: Infinite Scroll ----------
    @Test
    public void testInfiniteScroll() {
        WebElement scrollArea = driver.findElement(By.id("scroll-area"));
        int targetItem = 50;
        JavascriptExecutor js = (JavascriptExecutor) driver;

        while (scrollArea.findElements(By.xpath("//div[text()='Item #" + targetItem + "']")).isEmpty()) {
            js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight", scrollArea);
        }

        WebElement item50 = scrollArea.findElement(By.xpath("//div[text()='Item #50']"));
        Assert.assertTrue(item50.isDisplayed());
    }

    // ---------- Challenge 10: Modal & Alerts ----------
    @Test
    public void testModalAndAlert() {
        WebElement modalBtn = driver.findElement(By.id("open-modal"));
        modalBtn.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modal")));

        // Handle alert
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();

        WebElement closeModal = driver.findElement(By.id("modal-close"));
        closeModal.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal")));
    }
}
