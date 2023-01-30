package yape.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AndroidWebDriverProperties {
  private static Properties prop = new Properties();
  private static final String GLOBAL_DATA_FILE_LOCATION = "/test.properties";
  private String deviceName;
  private String app;
  private String deviceGroup;
  private boolean fullReset;
  private boolean noReset;
  private String uuid;
  private String groupId;
  private boolean disableWindowAnimation;
  private Long avdReadyTimeout;
  private boolean ignoreUnimportantViews;
  private boolean clearSystemFiles;
  private boolean enforceAppInstall;
  private boolean unicodeKeyboard;
  private boolean resetKeyboard;
  private Long uiautomator2ServerLaunchTimeout;
  private Long adbExecTimeout;
  private String appPackage;
  private String appActivity;

  public AndroidWebDriverProperties() {
    initConfig();
  }

  public void initConfig() {
    try {
      InputStream input = ConfigDriver.class.getResourceAsStream(GLOBAL_DATA_FILE_LOCATION);
      prop.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }

    deviceName = prop.getProperty("webdriver.android.deviceName");
    app = prop.getProperty("webdriver.android.app");
    deviceGroup = prop.getProperty("webdriver.android.deviceGroup");
    fullReset = Boolean.parseBoolean(prop.getProperty("webdriver.android.fullReset"));
    noReset = Boolean.parseBoolean(prop.getProperty("webdriver.android.noReset"));
    uuid = prop.getProperty("webdriver.android.uuid");
    groupId = prop.getProperty("webdriver.android.groupId");
    disableWindowAnimation = Boolean.parseBoolean(prop.getProperty("webdriver.android.disableWindowAnimation"));
    avdReadyTimeout = Long.valueOf(prop.getProperty("webdriver.android.avdReadyTimeout"));
    ignoreUnimportantViews = Boolean.parseBoolean(prop.getProperty("webdriver.android.ignoreUnimportantViews"));
    clearSystemFiles = Boolean.parseBoolean(prop.getProperty("webdriver.android.clearSystemFiles"));
    enforceAppInstall = Boolean.parseBoolean(prop.getProperty("webdriver.android.enforceAppInstall"));
    unicodeKeyboard = Boolean.parseBoolean(prop.getProperty("webdriver.android.unicodeKeyboard"));
    resetKeyboard = Boolean.parseBoolean(prop.getProperty("webdriver.android.resetKeyboard"));
    uiautomator2ServerLaunchTimeout = Long.valueOf(prop.getProperty("webdriver.android.uiautomator2ServerLaunchTimeout"));
    adbExecTimeout = Long.valueOf(prop.getProperty("webdriver.android.adbExecTimeout"));
    appPackage = prop.getProperty("webdriver.android.appPackage");
    appActivity = prop.getProperty("webdriver.android.appActivity");

  }

  public String getUuid() {
    return uuid;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public String getApp() {
    return app;
  }

  public String getDeviceGroup() {
    return this.deviceGroup;
  }

  public String getGroupId() {
    return this.groupId;
  }

  public boolean getDisableWindowAnimation() {
    return this.disableWindowAnimation;
  }

  public Long getAvdReadyTimeout() {
    return this.avdReadyTimeout;
  }

  public boolean getFullReset() {
    return this.fullReset;
  }

  public boolean getNoReset() {
    return this.noReset;
  }

  public boolean getIgnoreUnimportantViews() {
    return this.ignoreUnimportantViews;
  }

  public boolean getClearSystemFiles() {
    return this.clearSystemFiles;
  }

  public boolean getUnicodeKeyboard() {
    return this.unicodeKeyboard;
  }

  public boolean getResetKeyboard() {
    return this.resetKeyboard;
  }


  public Long getUiautomator2ServerLaunchTimeout() {
    return this.uiautomator2ServerLaunchTimeout;
  }

  public Long getAdbExecTimeout() {
    return this.adbExecTimeout;
  }

  public String getAppPackage() {
    return appPackage;
  }

  public String getAppActivity() {
    return appActivity;
  }
}
