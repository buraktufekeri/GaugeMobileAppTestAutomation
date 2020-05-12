package com.mobileapp.StepDefinitionBase;

import com.mobileapp.utilities.base.BasePage;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;

import java.util.List;

public class StepDefinitionBase extends BasePage {

    @Step("<key> elementine tikla")
    public void elementClick(String key){
        clickObject(key, key + " elementine tıklandı.");
    }

    @Step("<key> elementinin <index> .elemanina tikla")
    public void elementClickWithIndex(String key, int index){
        clickObjectWithIndex(key, key + " elementinin " + index + ". elemanına tıklandı.", index-1);
    }

    private String uniqueText;
    @Step("<key> elementine <text> unique text gir")
    public void setUniqueText(String key, String text){
        uniqueText = text + createRandomAlphanumeric(10);
        fillInputField(key, uniqueText, key + " elementine " + uniqueText + " datası girildi.");
    }

    @Step("<key> elementine <text> non unique text gir")
    public void setNonUniqueText(String key, String text){
        fillInputField(key, text, key + " elementine " + text + " datası girildi.");
    }

    @Step("<key> unique text dogru eklendi mi")
    public void isCorrectUniqueText(String key){
        Assert.assertEquals(uniqueText, getElementText(key));
    }

    @Step("<key> elementine ait task silindi mi")
    public void isDeletedTask(String key){
        int listSize = getElementsCount(key);
        List<String> listingTasksTitleList = getElementsText(key);
        for (int i = 0; i < listSize; i++){
            if (listingTasksTitleList.get(i).equals(uniqueText)){
                Assert.fail(key + " elementli " + uniqueText + " title lı task silinmedi!");
            }
        }
    }

    @Step("<key1> ve <key2> elementleri görüntülendi mi ve text leri doğru mu")
    public void isLeftMenuElementsDisplayedAndTextsCorrect(String key1, String key2){
        Assert.assertTrue(isElementDisplayed(key1));
        Assert.assertTrue(isElementDisplayed(key2));
        Assert.assertEquals("Task List", getElementText(key1));
        Assert.assertEquals("Statistics", getElementText(key2));
    }
}
