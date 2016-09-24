/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.multiplePicWithText;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.model.GalleryItemModel;
import com.huotu.hotcms.widget.CMSContext;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import com.huotu.widget.test.Editor;
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

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

/**
 * @author CJ
 */
public class TestWidgetInfo extends WidgetTest {

    @Override
    protected boolean printPageSource() {
        return false;
    }

    @Override
    protected void editorWork(Widget widget, Editor editor, Supplier<Map<String, Object>> currentWidgetProperties)
            throws IOException {
        WebElement count = editor.getWebElement().findElement(By.name("count"));
        count.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(count, "20").build().perform();
        Category category = new Category();
        category.setSerial("123444");
        category.setContentType(ContentType.Gallery);
        editor.chooseCategory(WidgetInfo.SERIAL, category);
        Map map = currentWidgetProperties.get();
        Assertions.assertThat(map.get(WidgetInfo.COUNT)).isEqualTo("20");
        Assertions.assertThat(map.get(WidgetInfo.SERIAL)).isEqualTo("123444");
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties,
            WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement editor = uiChanger.apply(properties);
        List<WebElement> lis = editor.findElements(By.tagName("li"));
        CMSDataSourceService cmsDataSourceService = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(CMSDataSourceService.class);
        List<GalleryItemModel> items = cmsDataSourceService.findGalleryItems(
                properties.get(WidgetInfo.SERIAL).toString(),
                Integer.valueOf(properties.get(WidgetInfo.COUNT).toString()));
        if (items==null)
            assertThat(lis).isNull();
        else
            assertThat(lis.size()).isEqualTo(items.size());
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties,
            WebElement> uiChanger, Supplier<Map<String, Object>> supplier) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement editor = uiChanger.apply(properties);
        WebElement count = editor.findElement(By.name(WidgetInfo.COUNT));
        Assertions.assertThat(count.getAttribute("value")).isEqualTo(properties.get(WidgetInfo.COUNT).toString());

    }
}
