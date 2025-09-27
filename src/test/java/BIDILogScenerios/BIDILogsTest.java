package BIDILogScenerios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v124.log.Log;
import org.testng.annotations.Test;

public class BIDILogsTest {
    @Test
    void testBIDILogs() {
        WebDriver driver = new ChromeDriver();

        DevTools devTools = ((ChromeDriver) driver).getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());

        devTools.addListener(Log.entryAdded(), logEntry -> {
            System.out.println("Console Log: " + logEntry.getText());
            System.out.println("Level: " + logEntry.getLevel());
        });

        driver.get("https://www.selenium.dev/documentation/webdriver/bidirectional/");
        driver.quit();
    }
}
