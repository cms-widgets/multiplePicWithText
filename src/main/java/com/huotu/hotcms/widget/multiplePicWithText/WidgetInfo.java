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
import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryItem;
import com.huotu.hotcms.service.model.GalleryItemModel;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.service.CategoryService;
import com.huotu.hotcms.service.service.ContentService;
import com.huotu.hotcms.service.service.GalleryItemService;
import com.huotu.hotcms.service.service.GalleryService;
import com.huotu.hotcms.widget.*;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget, PreProcessWidget {

    public static final String COUNT = "count";
    public static final String SERIAL = "serial";
    public static final String DATA_LIST = "dataList";


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
            return "多图控件";
        }
        return "multiplePicWithText";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "这是一个多图控件，你可以对组件进行自定义修改。";
        }
        return "This is a multiplePicWithText,  you can make custom change the component.";
    }

    @Override
    public String dependVersion() {
        return "1.1.0";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle(), new FourthWidgetStyle(), new SecondWidgetStyle()
                , new ThirdWidgetStyle(), new FifthWidgetStyle()};
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.equals(Widget.Javascript))
            return new ClassPathResource("js/widgetInfo.js", getClass().getClassLoader());
        return null;
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png", new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                , getClass().getClassLoader()));
        return map;
    }

    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle style = WidgetStyle.styleByID(this, styleId);
        //加入控件独有的属性验证
        String count=(String)componentProperties.get(COUNT);
        String serial=(String)componentProperties.get(SERIAL);
        if(StringUtils.isEmpty(count)||StringUtils.isEmpty(serial)){
            throw new IllegalArgumentException();
        }
    }

    @Override
    public Class springConfigClass() {
        return null;
    }


    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        GalleryRepository galleryRepository = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(GalleryRepository.class);
        List<Gallery> galleryList = galleryRepository.findByCategory_Site(CMSContext.RequestContext().getSite());
        if (galleryList.isEmpty()) {
            Gallery gallery = initGallery(initCategory());
            initGalleryItem(gallery, resourceService);
            properties.put(SERIAL, gallery.getSerial());
        } else {
            properties.put(SERIAL, galleryList.get(0).getSerial());
        }
        properties.put(COUNT, "5");
        return properties;
    }

    /**
     * 初始化数据源
     * @return
     */
    private Category initCategory(){
        CategoryService categoryService=getCMSServiceFromCMSContext(CategoryService.class);
        CategoryRepository categoryRepository=getCMSServiceFromCMSContext(CategoryRepository.class);
        Category category = new Category();
        category.setContentType(ContentType.Gallery);
        category.setName("默认数据源");
        categoryService.init(category);
        category.setSite(CMSContext.RequestContext().getSite());

        //保存到数据库
        categoryRepository.save(category);
        return category;
    }

    /**
     * 初始化一个图库
     * @return
     */
    private Gallery initGallery(Category category){
        GalleryService galleryService=getCMSServiceFromCMSContext(GalleryService.class);
        ContentService contentService=getCMSServiceFromCMSContext(ContentService.class);
        Gallery gallery=new Gallery();
        gallery.setTitle("默认图库标题");
        gallery.setDescription("这是一个默认图库");
        gallery.setCategory(category);
        contentService.init(gallery);
        galleryService.saveGallery(gallery);
        return gallery;
    }

    /**
     * 初始化一个图片
     * @param gallery
     * @param resourceService
     * @return
     */
    private GalleryItem initGalleryItem(Gallery gallery, ResourceService resourceService) throws IOException {
        ContentService contentService=getCMSServiceFromCMSContext(ContentService.class);
        GalleryItemService galleryItemService=getCMSServiceFromCMSContext(GalleryItemService.class);
        GalleryItem galleryItem=new GalleryItem();
        galleryItem.setTitle("默认图片标题");
        galleryItem.setDescription("这是一个默认图片");
        ClassPathResource classPathResource = new ClassPathResource("thumbnail.png", getClass().getClassLoader());

        InputStream inputStream=classPathResource.getInputStream();
        String imgPath= "_resources/"+UUID.randomUUID().toString()+".png";
        resourceService.uploadResource(imgPath,inputStream);

        galleryItem.setThumbUri(imgPath);
        galleryItem.setSize("106x82");
        galleryItem.setGallery(gallery);
        contentService.init(galleryItem);

        galleryItemService.saveGalleryItem(galleryItem);
        return galleryItem;

    }

    @Override
    public void prepareContext(WidgetStyle style, ComponentProperties properties, Map<String, Object> variables
            , Map<String, String> parameters) {
        String serial = (String) properties.get(SERIAL);
        CMSDataSourceService cmsDataSourceService = CMSContext.RequestContext().getWebApplicationContext()
                .getBean(CMSDataSourceService.class);
        int count = NumberUtils.parseNumber(variables.get(COUNT).toString(), Integer.class);
        List<GalleryItemModel> list = cmsDataSourceService.findGalleryItems(serial, count);
        variables.put(DATA_LIST, list);
    }
}
