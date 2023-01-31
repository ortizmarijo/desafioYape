package yape.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class BaseConfigProperties {
  private static Properties prop = new Properties();
  private static final String GLOBAL_DATA_FILE_LOCATION = "/test.properties";

  private String platformName;
  private String deviceURL;
  private String decimalFormat;
  private Long implicitlyWait;
  private Boolean isLocal;
  private String mainAppUrlBase;

  public BaseConfigProperties() {
    initConfig();
  }

  public void initConfig() {
    try {
      InputStream input;
      input = ConfigDriver.class.getResourceAsStream(GLOBAL_DATA_FILE_LOCATION);
      prop.load(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    platformName = prop.getProperty("webdriver.platformName");
    deviceURL = prop.getProperty("webdriver.deviceURL");
    implicitlyWait = Long.valueOf(prop.getProperty("webdriver.android.implicitlyWait"));
    isLocal = Boolean.valueOf(prop.getProperty("webdriver.isLocal"));
    decimalFormat = prop.getProperty("webdriver.decimalFormat");
    mainAppUrlBase = prop.getProperty("mainAppUrlBase");
  }

  public String getPlatformName() {
    return platformName;
  }

  public String getMainAppUrlBase() {
    return mainAppUrlBase;
  }

  public String getDecimalFormat() {
    return decimalFormat;
  }

  public String getDeviceURL() {
    return deviceURL;
  }

  public Long getImplicitlyWait() {
    return this.implicitlyWait;
  }

  public Boolean isLocal() {
    return this.isLocal;
  }
  /** return Current Path */
  public String getCurrentPath() {
    return Paths.get("").toAbsolutePath().toString();
  }
}
