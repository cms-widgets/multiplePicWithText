/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.multiplePicWithText;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return true;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        WebElement addPT=editor.findElement(By.name("addPT"));
        WebElement removePT=editor.findElements(By.name("removerPT")).get(1);
        Actions actions = new Actions(driver);
        actions.click(addPT).build().perform();
        actions.click(removePT).build().perform();



        Map map = currentWidgetProperties.get();
        //todo
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties,
            WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        WebElement picAndText=webElement.findElements(By.tagName("div")).get(0);
        List<WebElement> picAndTexts=picAndText.findElements(By.tagName("div"));
//        List<WebElement> picAndTexts=webElement.findElement(
//                By.id("picAndTexts")).findElements(By.tagName("div"));
        for(int i=0;i<picAndTexts.size();i++){
            WebElement img=picAndTexts.get(i).findElement(By.tagName("img"));
            WebElement text=picAndTexts.get(i).findElement(By.tagName("p"));
            Assertions.assertThat(img.getAttribute("src")).isEqualToIgnoringCase("http://placehold.it/130x200");
            Assertions.assertThat(text.getAttribute("text")).isEqualToIgnoringCase("火图科技");
        }

    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties,
            WebElement> uiChanger, Supplier<Map<String, Object>> supplier) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);

    }
}
