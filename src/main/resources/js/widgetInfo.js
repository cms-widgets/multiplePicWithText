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
            if (this.properties.serial == null || this.properties.serial == '') {
                onFailed("数据源serial为空，数据源异常");
                return;
            }
            onSuccess(this.properties);
            return this.properties;
        },
        getGalleryContent:function(serial){
            var count=$("input[name='count']").val();
            if(count==undefined||count.length==0){
                console.error("have no count!");
                return;
            }
            var url = _CMS_DataSource_URI +"/findGalleryItem?size="+count+"&gallerySerial="+serial;
            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                success:function(result){
                    $(".borderBoxs .ulImg li").remove();
                    var sumCode="";
                    for(var i=0;i<result.length;i++){
                        var url=result[i].thumbUri;
                        var htmlCode='<li class="image-list-show"> ' +
                            '<img src="'+url+'"> ' +
                            '<span>' + result[i].size + '</span> </li>';
                        sumCode=sumCode+htmlCode;
                    }
                    $(".borderBoxs .ulImg").append(sumCode);
                },
                error: function(){
                    console.error("ajaxError");
                }
            });
        },
        bindSerialSelect:function(){
            var that= this;
            $(".bindSelect").on('change', ".gallerys", function () {
                var serial=$(this).val();
                that.getGalleryContent(serial);
            })

        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.bindSerialSelect();
            var serial;
            if (this.properties.serial == undefined) {
                serial = $(".gallerys option:first").val();
            } else {
                serial = this.properties.serial;
                $(".gallerys option").each(function () {
                    if (serial == $(this).val()) {
                        $(this).attr("selected", true);
                    }
                })
            }
            this.getGalleryContent(serial);
        },
        close: function (globalId) {
            $(".bindSelect").off('change', ".gallerys");
        }
    }
});
