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
import com.huotu.hotcms.widget.model.PicAndText;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget{
    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";

    public static final String VALID_PICANDTEXTS="picAndTexts";

    public static final String VALID_PIC="pic";

    public static final String VALID_TEXT="text";

    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.multiplePicWithText";
    }

    @Override
    public String widgetId() {
        return "multiplePicWithText";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "A custom Widget";
        }
        return "multiplePicWithText";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "这是一个 A custom Widget，你可以对组件进行自定义修改。";
        }
        return "This is a multiplePicWithText,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.0-SNAPSHOT";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType){
        if (mediaType.equals(Widget.Javascript))
            return new ClassPathResource("js/widgetInfo.js", getClass().getClassLoader());
        return null;
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png",new ClassPathResource("thumbnail/defaultStyleThumbnail.png",getClass().getClassLoader()));
        return map;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle style = WidgetStyle.styleByID(this,styleId);
        //加入控件独有的属性验证
        List<PicAndText> picAndTexts = (List<PicAndText>) componentProperties.get(VALID_PICANDTEXTS);
        if(picAndTexts==null||ObjectUtils.isEmpty(picAndTexts.toArray())){
            throw new IllegalArgumentException();
        }

        for(int i=0;i<picAndTexts.size();i++){
            PicAndText picAndText=picAndTexts.get(i);
            if(StringUtils.isEmpty(picAndText.getText())||StringUtils.isEmpty(picAndText.getPic())){
                throw new IllegalArgumentException();
            }
        }

    }

    @Override
    public Class springConfigClass() {
        return null;
    }


    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        List<PicAndText> picAndTexts=new ArrayList<>();
        PicAndText picAndText=new PicAndText();
        picAndText.setText("火图科技");
        picAndText.setPic("http://placehold.it/130x200");
        picAndTexts.add(picAndText);
        picAndTexts.add(picAndText);
        picAndTexts.add(picAndText);
        picAndTexts.add(picAndText);
        picAndTexts.add(picAndText);
        properties.put(VALID_PICANDTEXTS,picAndTexts);
        return properties;
    }

}
