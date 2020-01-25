package com.mobileapp.utilities.selector;

import com.mobileapp.utilities.model.ElementInfo;
import org.openqa.selenium.By;

public class AndroidSelector implements Selector {

    @Override
    public By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;
        if (elementInfo.getAndroidType().equalsIgnoreCase("css")) {
            by = By.cssSelector(elementInfo.getAndroidValue());
        } else if (elementInfo.getAndroidType().equalsIgnoreCase("id")) {
            by = By.id(elementInfo.getAndroidValue());
        } else if (elementInfo.getAndroidType().equalsIgnoreCase("xpath")) {
            by = By.xpath(elementInfo.getAndroidValue());
        } else if (elementInfo.getAndroidType().equalsIgnoreCase("class")) {
            by = By.className(elementInfo.getAndroidValue());
        } else if (elementInfo.getAndroidType().equalsIgnoreCase("text")) {
            by = By.linkText(elementInfo.getAndroidValue());
        }
        return by;
    }

    @Override
    public int getElementInfoToIndex(ElementInfo elementInfo) {
        return elementInfo.getAndroidIndex();
    }
}
