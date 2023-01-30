package yape.page;

import com.sun.istack.internal.logging.Logger;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import yape.StepDefinitions.Hooks;
import yape.config.BaseConfigProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WebDriverHelper{
    static WebDriver driver;
    private final BaseConfigProperties baseConfigProperties = new BaseConfigProperties();
    private static Logger log = Logger.getLogger(WebDriverHelper.class);
    public static Map<String, String> ScenarioData = new HashMap<>();
    public static Map<String, String> HandleMyWindows = new HashMap<>();
    private static final int EXPLICIT_TIMEOUT = 20;
    private static final int RETRY = 20;

    public WebDriverHelper(){
        driver = Hooks.driver;
    }
    /******** Scenario Attributes ********/
    Scenario scenario = null;
    public void scenario (Scenario scenario) {
        this.scenario = scenario;
    }

    public void waitPageCompletelyLoaded (){
        String GetActual = driver.getCurrentUrl();
        System.out.println(String.format("Checking if %s page is loaded.", GetActual));
        log.info(String.format("Checking if %s page is loaded.", GetActual));
        new WebDriverWait(driver, EXPLICIT_TIMEOUT).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    /**
     * build ByXPath from text
     *
     * @param text text used as reference
     */
    public By genericByBuilder(String text) {
        String GENERIC = String.format("//*[contains(@text,'%s') or contains(@content-desc,'%s') or contains(@resource-id,'%s')]", text, text, text);
        return By.xpath(GENERIC);
    }

    /**
     * build ByAccessibilityId from text
     *
     * @param text text used as reference
     */
    public MobileBy.ByAccessibilityId genericAccessibilityIdBuilder(String text) {
        return new MobileBy.ByAccessibilityId(text);
    }


    public boolean waitVisibility(By by, Integer wt) {
        int time;
        if (wt == null) {
            time = EXPLICIT_TIMEOUT;
        } else {
            time = wt;
        }
        try {
            WebDriverWait wait = new WebDriverWait(driver, time);
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            wait.until(ExpectedConditions.elementToBeClickable(by));
            return true;
        } catch (NoSuchElementException | TimeoutException e) {
            log.info("Element is not present");
            return false;
        }
    }


    public WebElement getElementFromGenericBuilders(String label) {
        WebElement elem = null;
        if (driver.findElement(genericByBuilder(label)) != null) {
            elem = driver.findElement(genericByBuilder(label));
        }

        if (elem == null) {
            label = label.split("(?<=[,.])|(?=[,.])|\n|®|:")[0].trim();
            if (driver.findElement(genericByBuilder(label)) != null) {
                elem = driver.findElement(genericByBuilder(label));
            }
        }

        return elem;
    }

    public WebElement getElementFromGenericBuilders(By loc) {
        WebElement elem = null;
        if (driver.findElement(loc) != null) {
            elem = driver.findElement(loc);
        }

        return elem;
    }

    public List<WebElement> getElementsFromGenericBuilders(String label) {
        List<WebElement> elem = new ArrayList<>();
        if (waitVisibility(genericByBuilder(label), EXPLICIT_TIMEOUT) && driver.findElements(genericByBuilder(label)) != null) {
            elem = driver.findElements(genericByBuilder(label));
        }

        if (elem.size() == 0) {
            label = label.split("(?<=[,.])|(?=[,.])|\n|®|:")[0].trim();
            if (waitVisibility(genericByBuilder(label), EXPLICIT_TIMEOUT) && driver.findElements(genericByBuilder(label)) != null) {
                elem = driver.findElements(genericByBuilder(label));
            }
        }

        if (elem.size() == 0) {
            swipeTo(genericByBuilder(label));
            for(int i=0; i<5; i++) {
                swipe();
            }
            elem = driver.findElements(genericByBuilder(label));
        }

        return elem;
    }

    /**
     * Will simulate scrolling down into a object.
     *
     * @param object web element that you do scroll action.
     */
    public void swipeTo(By object) {
        boolean display = waitVisibility(object, EXPLICIT_TIMEOUT/2);
        int i = 0;
        while (!display && i <= 5) {
            swipe();
            display = isElementDisplayed(object);
            log.info("swipeTo element is " + display + " " + object.toString());
            i++;
        }
    }

    public void swipeHorizontalTo(By object) {
        boolean isDisplayed = isElementDisplayed(object);
        while (!isDisplayed) {
            swipeHorizontal();
            isDisplayed = isElementDisplayed(object);
        }
    }

    /** swipe horizontally */
    public void swipeHorizontal() {
        // Swipe length is a product of the screen's dimensions
        int swipeLength = (int) (driver.manage().window().getSize().getHeight() * 0.50);
        int anchorX =
                (int)
                        (driver.manage().window().getSize().getHeight()
                                * 0.90); // x coordinate will not change for this vertical swipe
        int endY =
                (int)
                        (driver.manage().window().getSize().getHeight()
                                * 0.3); // Higher on the screen (lower y coordinate)
        int startY = endY + swipeLength; // Lower on the screen (higher y coordinate)
        new TouchAction((PerformsTouchActions) driver)
                .longPress(new PointOption().withCoordinates(anchorX, startY))
                .moveTo(new PointOption().withCoordinates(anchorX, endY))
                .release()
                .perform();
    }

    /**
     * Performs a swipe using the appium driver. Swipes from the start coordinates to the end
     * coordinates.
     */
    public void swipe() {
        // Swipe length is a product of the screen's dimensions
        int swipeLength = (int) (driver.manage().window().getSize().getHeight() * 0.4);
        int anchorX =
                driver.manage().window().getSize().getWidth()
                        / 2; // x coordinate will not change for this vertical swipe
        int endY =
                (int)
                        (driver.manage().window().getSize().getHeight()
                                * 0.3); // Higher on the screen (lower y coordinate)
        int startY = endY + swipeLength; // Lower on the screen (higher y coordinate)
        new TouchAction((PerformsTouchActions) driver)
                .longPress(new PointOption().withCoordinates(anchorX, startY))
                .moveTo(new PointOption().withCoordinates(anchorX, endY))
                .release()
                .perform();
    }

    /**
     * tap near from a element
     *
     * @param element element used as reference
     */
    public void tapFixed(By element, int fixY) {
        int x = driver.manage().window().getSize().getWidth() / 2;
        int y = driver.findElement(element).getLocation().getY() + fixY;
        log.info(String.format("retrieve element coordinates: %s, %s", x, y));
        tapByCoordinates(x,y);
    }

    /** Will simulate tapping by coordinates. */
    public void tapByCoordinates(int x, int y) {
        try {
            new TouchAction((PerformsTouchActions) driver)
                    .longPress(PointOption.point(x, y))
                    .moveTo(new PointOption().withCoordinates(x, y))
                    .release()
                    .perform();
        } catch (InvalidArgumentException e) {
            log.info("The tap were send beyond windows size");
        }
    }

    /**
     * wait an element appeared and return isDisplayed boolean value
     *
     * @param object web element that you are waiting for.
     */
    public boolean isElementDisplayed(By object) {
        boolean isDisplayed;
        try {
            WebDriverWait wait = new WebDriverWait(driver, 3);
            isDisplayed = wait.until(ExpectedConditions.visibilityOfElementLocated(object)).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            isDisplayed = false;
        }
        log.info(String.format("visibility is: %s", isDisplayed));
        return isDisplayed;
    }

    public void fillField(By locator, String text) {
        explicitWait(locator);
        MobileElement element = (MobileElement) driver.findElement(locator);
        element.click();
        implicitWait(1);
        element.sendKeys(text);
    }

    /**
     * click using generic xpath
     *
     * @param locator text used as reference
     */
    public void click(By locator) {
        explicitWait(locator);
        MobileElement element = (MobileElement) driver.findElement(locator);
        element.click();
    }

    public List<WebElement> getElementList(By locator) {
        List<WebElement> elem = new ArrayList<>();

        if (waitVisibility(locator, 10)) {
            elem = driver.findElements(locator);
        }

        if (elem.size() == 0) {
            swipeTo(locator);
            for(int i=0; i<5; i++) {
                swipe();
            }
            elem = driver.findElements(locator);
        }

        return elem;
    }



    /**
     * click using generic xpath
     *
     * @param text text used as reference
     */
    public void click(String text) {
        explicitWait(genericByBuilder(text));
        driver.findElement(genericByBuilder(text)).click();
    }


    public void explicitWait(By by) {
        WebDriverWait wait = new WebDriverWait(driver, EXPLICIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void implicitWait(int seconds) {
        try {
            driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.warning("Error in implicitlyWait  ", e);
        }
    }

    public void sleep(int seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explicitWait(By by, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public void setSecureTextValue(By element, String value) {
        String fields_text;
        boolean verify;
        int i = 0;
        do {
            sleep(1);
            explicitWait(element);
            fillField(element, value);
            sleep(2);
            String parts = driver.findElement(element).getText().trim();
            int end = parts.indexOf(",");
            if (end != -1) {
                fields_text = parts.substring(0, end);
            } else {
                fields_text = driver.findElement(element).getText().trim();
            }
            verify = fields_text.equals(value);
            if (verify) {
                return;
            }
            i++;
        } while (!verify && i <= RETRY);
    }

    /**
     * Create a table with parameters given on feature step.
     *
     * @param table is a list with parameters given on step.
     */
    public DataTable createDataTable(List<List<String>> table) {
        DataTable data;
        data = DataTable.create(table);
        log.info(data.toString());
        return data;
    }

}
