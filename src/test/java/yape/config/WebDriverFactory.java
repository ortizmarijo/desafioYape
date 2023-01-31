package yape.config;

import com.sun.istack.internal.logging.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebDriverFactory {
    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(WebDriverFactory.class);

    private WebDriverFactory() {

    }

    public static AppiumDriver<MobileElement> getDriverType() throws Exception {
        AbstractDriverInitializer initializer = new AndroidDriverInitializer();
        return initializer.initialize();
    }


    public static WebDriver createNewWebDriver(String platform) throws Exception {
        WebDriver driver;

        if ("FIREFOX".equalsIgnoreCase(platform)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        else if ("CHROME".equalsIgnoreCase(platform)) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

        }
        else if ("ANDROID".equalsIgnoreCase(platform)) {
            driver = getDriverType();

        }
        else {
            log.warning("The Driver is not selected properly, invalid name: " + platform);
            return null;
        }

        return driver;

    }

}
