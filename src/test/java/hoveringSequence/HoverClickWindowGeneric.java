package hoveringSequence;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class HoverClickWindowGeneric {
    static WebDriver driver;
    static WebDriverWait wait;
    static Actions actions;

    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().window().fullscreen();
        driver.get("file:///C:/Users/User/eclipse-workspace/TekPyramidPractice/hover_sequence%20(1).html"); // Replace with actual URL

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        actions = new Actions(driver);

        // Button locators in sequence
        List<By> buttons = Arrays.asList(
                By.id("a"),
                By.id("b"),
                By.id("c"),
                By.id("d")
        );

        // Perform hover on a, b, c and finally click on d
        hoverAndClick(buttons);

        // Handle multiple windows
        handleNewWindow();

        driver.quit();
    }

    // Generic method for hover and final click
    public static void hoverAndClick(List<By> locators) {
        for (int i = 0; i < locators.size(); i++) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locators.get(i)));
            
            if (i < locators.size() - 1) {
                // For a, b, c → just hover
                actions.moveToElement(element).perform();
            } else {
                // For last one (d) → click
                element.click();
            }
        }
    }

    // Generic method for switching to new window
    public static void handleNewWindow() {
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();

        for (String window : allWindows) {
            if (!window.equals(parentWindow)) {
                driver.switchTo().window(window);
                System.out.println("Switched to new window. Title: " + driver.getTitle());
                // Optional: close and switch back
                driver.close();
                driver.switchTo().window(parentWindow);
                break;
            }
        }
    }
    
   
}
