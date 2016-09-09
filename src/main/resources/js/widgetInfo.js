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

        getGalleryContent:function(serial){
            var count=$("input[name='count']").val();
            if(count==undefined||count.length==0){
                console.error("have no count!");
                return;
            }

            var url = _CMS_DataSource_URI +"/findGalleryItem?size="+count+"&gallerySerial="+serial;
            console.error("url:"+url);
            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                success:function(result){
                    console.error(JSON.stringify(result));
                    console.error("ajaxSuccess");
                    console.error("count:"+count);
                    $(".clearfix li").remove();
                    var sumCode="";
                    for(var i=0;i<result.length;i++){
                        var url=result[i].thumbUri;
                        var length="";
                        var width="";
                        var htmlCode='<li class="image-list-show"> ' +
                            '<img src="'+url+'"> ' +
                            '<span>'+length+'x'+width+'</span> </li>';
                        sumCode=sumCode+htmlCode;
                    }
                    $(".clearfix:last").append(sumCode);
                },
                error: function(){
                    console.error("ajaxError");

                }
            });
        },

        bindSerialSelect:function(){
            var that= this;
            $("select[name='serial'] option").on('click',function(){
                console.error("bind");
                var serial=$(this).val();
                console.error("serial:"+serial);
                that.getGalleryContent(serial);
            })

        },

        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.bindSerialSelect();
            var serial=$(".gallerys option:first").val();
            this.getGalleryContent(serial);
        },
        close: function (globalId) {

        }
    }
});
