package yape.StepDefinitions;

import com.sun.istack.internal.logging.Logger;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import yape.config.BaseConfigProperties;
import yape.page.BookingSearchPage;
import yape.page.WebDriverHelper;

import java.util.List;

public class StepDefinitions extends WebDriverHelper{
    static WebDriver driver;
    private static Logger log = Logger.getLogger(StepDefinitions.class);
    private final BaseConfigProperties baseConfigProperties = new BaseConfigProperties();
    private static BookingSearchPage bookingSearchPage = new BookingSearchPage();

    public StepDefinitions(){
        driver = Hooks.driver;
    }

    @Given("an example scenario")
    public void anExampleScenario() {
    }

    @When("all step definitions are implemented")
    public void allStepDefinitionsAreImplemented() {
    }

    @Then("the scenario passes")
    public void theScenarioPasses() {
    }

    @Given("^I am in App main site$")
    public void iAmInAppMainSite() {
        driver.manage().window().maximize();
        String url = baseConfigProperties.getMainAppUrlBase();
        log.info("Navigate to: " + url);
        driver.get(url);
        HandleMyWindows.put("main", driver.getWindowHandle());
        waitPageCompletelyLoaded();

    }

    @Given("^User is on Main app screen$")
    public void userIsOnMainAppScreen() {
        bookingSearchPage.skipSignInScreen();
    }

    @Then("^User sets Destination textbox as (.*?)$")
    public void userEntersDestinationValue(String destination) {
        bookingSearchPage.setDestination(destination);
    }

    @And("^User sets travel duration dates From: (.*?) to (.*?)$")
    public void userSetsTravelDurationDatesFromTo(String from, String to) {
        bookingSearchPage.userSetTravelDates(from, to);
    }

    @And("^User sets following Occupancy values$")
    public void userSetsFollowingOccupancyValues(List<List<String>> table) {
        bookingSearchPage.userSetOccupancyValues(table);
    }

    @And("^User sets following Children values$")
    public void userSetsFollowingChildrenValues(List<List<String>> table) {
        bookingSearchPage.userSetChildrenValues(table);
    }

    @Then("^User click on (.*?) button$")
    public void userClickOnSearchButton(String button) {
        click(genericByBuilder(button));
    }

    @Then("^User selects option number (.*?) of result list$")
    public void userSelectResultValue(int index) {
        bookingSearchPage.selectFromResultList(index);
    }
}
