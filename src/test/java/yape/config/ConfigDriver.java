package yape.config;

import com.sun.istack.internal.logging.Logger;
import org.openqa.selenium.WebDriver;

public class ConfigDriver {
    /******** Load Properties ********/
    private static BaseConfigProperties baseConfigProperties = new BaseConfigProperties();

    /******** Log Attribute ********/
    private static Logger log = Logger.getLogger(ConfigDriver.class);

    /******** Initialize Driver Configuration when the class is instanced ********/
    private ConfigDriver() throws Exception {
        ConfigDriver.initConfig();
    }


    public static WebDriver initConfig() throws Exception {
        WebDriver driver;
        String platform = baseConfigProperties.getPlatformName();

        log.info("***********************************************************************************************************");
        log.info("[ POM Configuration ] - Read the basic properties configuration from: ../test.properties");
        /******** POM Information ********/
        log.info("[ POM Configuration ] - Browser: " + platform);
        log.info("***********************************************************************************************************");

        /****** Load the driver *******/
        driver = WebDriverFactory.createNewWebDriver(platform);

        return driver;
    }

}
