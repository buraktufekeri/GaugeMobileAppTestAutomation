package com.mobileapp.StepDefinitionBase;

import com.mobileapp.utilities.base.BasePage;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;

public class StepDefinitionBase extends BasePage {

    @Step("<key> element click")
    public void clickObjectStep(String key) {
        clickObject(key, key + " element clicked.");
    }

    @Step("<key> element click with <index> .element")
    public void clickObjectWithIndexStep(String key, int index) {
        clickObjectWithIndex(key, key + " element clicked with " + index + ".index.", index-1);
    }

    @Step("<key> elements click")
    public void clickObjectsStep(String key) {
        clickObjects(key, key + " element clicked");
    }

    @Step("<key> element fill <text> text")
    public void fillInputFieldStep(String key, String text) {
        fillInputField(key, text, key + " element filled " + text);
    }

    @Step("<key> element fill <text> text with <index> .element")
    public void fillInputFieldWithIndexStep(String key, String text, int index) {
        fillInputFieldWithIndex(key, text, key + " element filled with " + index + ".index.", index-1);
    }

    @Step("<key> element is displayed")
    public void isElementDisplayedStep(String key) {
        Assert.assertTrue(key + " element couldn't be displayed", isElementDisplayed(key));
    }

    @Step("<key> element is displayed with <index> .element")
    public void isElementDisplayedWithIndexStep(String key, int index) {
        Assert.assertTrue(key + " element couldn't be displayed " + index + ".index.", isElementDisplayedWithIndex(key, index-1));
    }

    @Step("<key> elements is displayed")
    public void isElementsDisplayedStep(String key) {
        for (int i = 0; i < getElementsCount(key); i++) {
            Assert.assertTrue(key + " element " + (i+1) + ".element couldn't be displayed", isElementDisplayedWithIndex(key, i));
        }
    }

    @Step("<key> keyboard send keys actions")
    public void keyboardSendKeysActionsStep(String androidKey) {
        keyboardSendKeysActions(androidKey);
    }

    @Step("<key> element fill <dateFormat> dateFormat")
    public void setFormattedDate(String key, String dateFormat) {
        fillInputField(key, getNowTime(dateFormat).replaceAll("/", ""), key + " element filled " + dateFormat);
    }

    @Step("<key> element fill <dateFormat> dateFormat <index> .element")
    public void setFormattedDateWithIndex(String key, String dateFormat, int index) {
        fillInputFieldWithIndex(key, getNowTime(dateFormat).replaceAll("/", ""), key + " element filled with " + index + ".index", index-1);
    }

    @Step("<second> second wait")
    public void secondWait(int second) {
        threadSleep(second);
    }
}
