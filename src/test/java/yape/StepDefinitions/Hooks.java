package yape.StepDefinitions;

import com.sun.istack.internal.logging.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import yape.config.ConfigDriver;

import java.io.IOException;

public class Hooks {

    public static WebDriver driver;
    Logger log = Logger.getLogger(Hooks.class);
    Scenario scenario = null;
    /**
     @Before
     public void before() {
     this.scenario = scenario;
     }
     */
    @Before
    public void initDriver(Scenario scenario) throws Exception {
        log.info("***********************************************************************************************************");
        log.info("[ Configuration ] - Initializing driver configuration");
        log.info("***********************************************************************************************************");
        driver = ConfigDriver.initConfig();
        this.scenario = scenario;
        log.info("***********************************************************************************************************");
        log.info("[ Scenario ] - "+ scenario.getName());
        log.info("***********************************************************************************************************");
    }

    @After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void embedScreenshot(Scenario scenario) throws IOException {

        if(scenario.isFailed()) {
            try {
                scenario.log("The scenario failed.");
                byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "PNG", "src/test/resources/Data/Screenshots/Failed");
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }

        log.info("***********************************************************************************************************");
        log.info("[ Driver Status ] - Clean and close the intance of the driver");
        log.info("***********************************************************************************************************");
        driver.quit();

    }
}
