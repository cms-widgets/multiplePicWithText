/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.multiplePicWithText;

import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * @author CJ
 */
public class FifthWidgetStyle implements WidgetStyle{

    @Override
    public String id() {
        return "multiplePicWithTextFifthWidgetStyle";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "bootstrap 风格 fourth";
        }
        return "bootstrap style multiplePicWithText fourth";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "基于bootstrap样式的 fourth";
        }
        return "Based on the bootstrap style by multiplePicWithText fourth";
    }

    @Override
    public Resource thumbnail() {
        return new ClassPathResource("/thumbnail/fifthStyleThumbnail.jpg", getClass().getClassLoader());
    }

    @Override
    public Resource previewTemplate() {
        return null;
    }

    @Override
    public Resource browseTemplate() {
        return new ClassPathResource("/template/fifthStyleBrowserTemplate.html", getClass().getClassLoader());
    }

}
