package com.mobileapp.utilities.base;

import com.mobileapp.utilities.selector.Selector;
import com.mobileapp.utilities.selector.SelectorFactory;
import com.mobileapp.utilities.selector.SelectorType;
import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    static AppiumDriver<MobileElement> appiumDriver;
    static FluentWait<AppiumDriver> appiumFluentWait;
    static Selector selector;
    static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @BeforeScenario
    public void beforeScenario() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities;
        URL url = null;
        if (System.getenv("executionEnvironment").equalsIgnoreCase("android")){
            logger.info("Local Android");
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AOSP on IA Emulator");
            if (System.getenv("executionDevice").equalsIgnoreCase("AndroidPixel3a")) {
                logger.info("AndroidPixel3a");
                desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                logger.info("AndroidPixel3a > http://localhost:4723/wd/hub");
                url = new URL("http://localhost:4723/wd/hub");
            } else if (System.getenv("executionDevice").equalsIgnoreCase("AndroidPixel2")) {
                logger.info("AndroidPixel2");
                desiredCapabilities.setCapability(MobileCapabilityType.UDID, "emulator-5556");
                logger.info("AndroidPixel2 > http://localhost:4733/wd/hub");
                url = new URL("http://localhost:4733/wd/hub");
            }
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");

            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.google.android.youtube");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.google.android.apps.youtube.app.WatchWhileActivity");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator1");
            desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            desiredCapabilities.setCapability("unicodeKeyboard", false);
            desiredCapabilities.setCapability("resetKeyboard", false);
            appiumDriver = new AndroidDriver<>(url, desiredCapabilities);
            selector = SelectorFactory.createElementHelper(SelectorType.ANDROID);
        } else if (System.getenv("executionEnvironment").equalsIgnoreCase("ios")){
            logger.info("Local IOS");
            desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "");
            desiredCapabilities.setCapability(MobileCapabilityType.UDID, "");
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "");

            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "");
            desiredCapabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "");
            desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, true);
            desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, false);
            desiredCapabilities.setCapability("unicodeKeyboard", false);
            desiredCapabilities.setCapability("resetKeyboard", false);
            url = new URL("http://localhost:4723/wd/hub");
            appiumDriver = new IOSDriver<>(url, desiredCapabilities);
            selector = SelectorFactory.createElementHelper(SelectorType.IOS);
        }

        appiumDriver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
        appiumFluentWait = new FluentWait<>(appiumDriver);
        appiumFluentWait
                .withTimeout(2000, TimeUnit.MILLISECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
    }

    @AfterScenario
    public void afterScenario() {
        if (appiumDriver != null)
            appiumDriver.quit();
    }
}
