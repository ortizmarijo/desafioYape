package yape.config;

import com.sun.istack.internal.logging.Logger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AndroidDriverInitializer extends AbstractDriverInitializer {
  /** ***** Log Attribute ******* */
  private static Logger log = Logger.getLogger(AndroidDriverInitializer.class);

  private AndroidWebDriverProperties androidConfigProperties = new AndroidWebDriverProperties();
  private final BaseConfigProperties baseConfigProperties = new BaseConfigProperties();
  private final DesiredCapabilities caps = new DesiredCapabilities();

  public AppiumDriver<MobileElement> initialize() throws Exception {

    AppiumDriver<MobileElement> driver = null;

    try {
      caps.setCapability("automationName", "UiAutomator2");
      setDeviceDefaultData();
      getLocalApk();
      caps.setCapability(MobileCapabilityType.FULL_RESET, androidConfigProperties.getFullReset());
      caps.setCapability(MobileCapabilityType.NO_RESET, androidConfigProperties.getNoReset());

      caps.setCapability("groupId", androidConfigProperties.getGroupId());
      caps.setCapability("deviceGroup", androidConfigProperties.getDeviceGroup());
      caps.setCapability("disableWindowAnimation", androidConfigProperties.getDisableWindowAnimation());
      caps.setCapability("avdReadyTimeout", androidConfigProperties.getAvdReadyTimeout());
      caps.setCapability("ignoreUnimportantViews", androidConfigProperties.getIgnoreUnimportantViews());
      caps.setCapability("clearSystemFiles", androidConfigProperties.getClearSystemFiles());
      caps.setCapability("unicodeKeyboard", androidConfigProperties.getUnicodeKeyboard());
      caps.setCapability("resetKeyboard", androidConfigProperties.getResetKeyboard());
      caps.setCapability("uiautomator2ServerLaunchTimeout", androidConfigProperties.getUiautomator2ServerLaunchTimeout());
      caps.setCapability("adbExecTimeout", androidConfigProperties.getAdbExecTimeout());
      caps.setCapability("appPackage", androidConfigProperties.getAppPackage());
      caps.setCapability("appActivity", androidConfigProperties.getAppActivity());

    } catch (Exception e) {
      log.info("Executing local in local device");
    }
    try {
      driver = new AndroidDriver<>(new URL(baseConfigProperties.getDeviceURL()), caps);
      driver
          .manage()
          .timeouts()
          .implicitlyWait(baseConfigProperties.getImplicitlyWait(), TimeUnit.SECONDS);

    } catch (WebDriverException e) {
      e.printStackTrace();
      System.exit(0);
    }
    return driver;
  }

  private void setDeviceDefaultData() {
    caps.setCapability(MobileCapabilityType.PLATFORM_NAME, baseConfigProperties.getPlatformName());
    caps.setCapability(MobileCapabilityType.DEVICE_NAME, androidConfigProperties.getDeviceName());
    caps.setCapability("udid", androidConfigProperties.getUuid());
  }

  private void getLocalApk() {
    String localAPK =
        StringUtils.isNotEmpty(System.getenv("APP_PATH_APK"))
            ? System.getenv("APP_PATH_APK")
            : System.getProperty("user.dir") + androidConfigProperties.getApp();
    caps.setCapability(MobileCapabilityType.APP, localAPK);
    log.info("apk running is " + localAPK);
  }

}
