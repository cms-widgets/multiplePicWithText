/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.serial = $(".gallerys").val();
            this.properties.count = $(".count").val();
            onSuccess(this.properties);
            return this.properties;
        },

        createImg:function(url,length,width){
            var htmlCode='<li class="image-list-show"> ' +
                '<img src="'+url+'"> ' +
                '<span>'+length+'x'+width+'</span> </li>';
            return htmlCode;

        },

        getGalleryContent:function(serial,count){
            var that=this;
            var url = _CMS_DataSource_URI +"/未定?页码=1&长度="+count+"&图库序列号="+serial;
            console.error("url:"+url);
            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                success:function(){
                    console.error("ajaxSuccess");
                },
                error: function(){
                    //测试,假装ajax成功，然后执行的操作
                    console.error("ajaxError");
                    console.error("count:"+count);
                    $(".clearfix li").remove();
                    var sumCode="";
                    for(var i=0;i<count;i++){
                        var url="http://placehold.it/100x75";
                        var length="1280";
                        var width="760";
                        var htmlCode='<li class="image-list-show"> ' +
                            '<img src="'+url+'"> ' +
                            '<span>'+length+'x'+width+'</span> </li>';
                        sumCode=sumCode+htmlCode;
                    }
                    $(".clearfix:last").append(sumCode);
                }
            });
        },

        bindSerialSelect:function(){
            var that= this;
            $("select[name='serial'] option").on('click',function(){
                console.error("bind");
                var serial=$(this).val();
                console.error("serial:"+serial);
                var count=$("input[name='count']").val();
                that.getGalleryContent(serial,count);
            })

        },

        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.bindSerialSelect();
        },
        close: function (globalId) {

        }
    }
});
