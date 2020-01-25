package com.mobileapp.utilities.base;

import com.mobileapp.utilities.model.SelectorInfo;
import io.appium.java_client.MobileElement;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BasePage extends BaseTest {

    private List<MobileElement> findElementsWithBy(By by) {
        List<MobileElement> webElementList;
        try {
            webElementList = appiumFluentWait.until(new ExpectedCondition<List<MobileElement>>() {
                @Nullable
                @Override
                public List<MobileElement> apply(@Nullable WebDriver webDriver) {
                    List<MobileElement> elements = webDriver.findElements(by);
                    return elements.size() > 0 ? elements : null;
                }
            });
            if (webElementList == null) {
                throw new NullPointerException(String.format("by = %s Web element list not found", by.toString()));
            }
        } catch (Exception e) {
            throw e;
        }

        return webElementList;
    }

    private MobileElement findElementWithBy(By by) {
        MobileElement mobileElement = null;
        try {
            mobileElement = findElementsWithBy(by).get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    private List<MobileElement> findElementsWithKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<MobileElement> mobileElements = null;
        try {
            mobileElements = findElementsWithBy(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElements;
    }

    private MobileElement findElementWithKey(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        MobileElement mobileElement = null;
        try {
            mobileElement = selectorInfo.getIndex() > 0 ? findElementsWithBy(selectorInfo.getBy()).get(selectorInfo.getIndex()) : findElementWithBy(selectorInfo.getBy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElement;
    }

    protected void fillInputField(String key, String text, String message) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            MobileElement mobileElement = findElementWithKey(key);
            if (mobileElement.isEnabled()) {
                mobileElement.clear();
                mobileElement.sendKeys(text);
                logger.info("---------------" + message + "---------------");
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("key=" + key + " by=" + selectorInfo.getBy().toString() + " Element not found");
        }
    }

    protected String getElementText(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            MobileElement mobileElement = findElementWithKey(key);
            return mobileElement.getText();
        } catch (NullPointerException e){
            throw new RuntimeException("key=" + key + " by=" + selectorInfo.getBy().toString() + " Element not found");
        }
    }

    protected boolean isElementDisplayed(String key) {
        boolean found = false;

        try {
            MobileElement mobileElement = findElementWithKey(key);
            if (mobileElement != null && mobileElement.isDisplayed()){
                found = true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return found;
    }

    protected int getElementsCount(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            return findElementsWithKey(key).size();
        } catch (NullPointerException e){
            throw new RuntimeException("key=" + key + " by=" + selectorInfo.getBy().toString() + " Element not found");
        }
    }

    protected List<String> getElementsText(String key) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        List<String> list = new ArrayList<>();

        try {
            for (MobileElement mobileElement : findElementsWithKey(key)) {
                list.add(mobileElement.getText());
            }
        } catch (NullPointerException e){
            throw new RuntimeException("key=" + key + " by=" + selectorInfo.getBy().toString() + " Element not found");
        }

        return list;
    }

    protected String getAttribute(String key, String attributeName) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);
        try {
            MobileElement mobileElement = findElementWithKey(key);
            return mobileElement.getAttribute(attributeName);
        } catch (NullPointerException e){
            throw new RuntimeException("key=" + key + " by=" + selectorInfo.getBy().toString() + " Element not found");
        }
    }

    private void waitUntilExpectedElementClickable(MobileElement mobileElement) {
        try {
            appiumFluentWait.until(ExpectedConditions.visibilityOfAllElements(mobileElement));
            appiumFluentWait.until(ExpectedConditions.elementToBeClickable(mobileElement));
        } catch (NullPointerException e) {
            throw new RuntimeException("ELEMENT " + mobileElement + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
        }
    }

    protected void clickObject(String key, String message) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        try {
            MobileElement mobileElement = findElementWithKey(key);
            if (mobileElement == null){
                throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
            } else {
                waitUntilExpectedElementClickable(mobileElement);
                mobileElement.click();
                logger.info("---------------" + message + "---------------");
            }
        } catch (NullPointerException e){
            throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
        }
    }

    protected void clickObjectWithIndex(String key, String message, int index) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        try {
            MobileElement mobileElement = findElementsWithKey(key).get(index);
            if (mobileElement == null){
                throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + index + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
            } else {
                waitUntilExpectedElementClickable(mobileElement);
                mobileElement.click();
                logger.info("---------------" + message + "---------------");
            }
        } catch (NullPointerException e){
            throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
        }
    }

    protected void clickObjects(String key, String message) {
        SelectorInfo selectorInfo = selector.getSelectorInfo(key);

        try {
            for (MobileElement mobileElement : findElementsWithKey(key)) {
                if (mobileElement == null){
                    throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
                } else {
                    waitUntilExpectedElementClickable(mobileElement);
                    mobileElement.click();
                    logger.info("---------------" + message + "---------------");
                }
            }
        } catch (NullPointerException e){
            throw new RuntimeException("ELEMENT " + selectorInfo.getBy().toString() + " NOT EXIST; AUTOMATION DATAS MAY BE INVALID!");
        }
    }

    protected String createRandomAlphanumeric(int digit){
        return RandomStringUtils.randomAlphanumeric(digit);
    }
}
