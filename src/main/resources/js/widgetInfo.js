/**
 * Created by lhx on 2016/8/11.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            this.properties.picAndTexts=[];
            for(var i=0;i<4;i++){
                var picAndText={pic:"123",text:"456"};
                this.properties.picAndTexts[i]=picAndText;
            }
            $(".LinkRowHtml").each(function(index,val){
                var title=$(val).find("input[name='picTitle']").val();
                this.properties.picAndTexts[index].text=title;

            });
            onSuccess(this.properties);
            return this.properties;
        },
        addPAT:function(){
            $("button[name='addPT']").on("click",function(){
                $(".LinkRowHtml:last").append(htmlElement.prop("outerHTML"));

            });
        },

        removePAT:function(){
            $("button[name='removerPT']").on("click",function(){
                var no = $(this).attr("index");
                $("div[class='LinkRowHtml']").eq(no).remove();
            });
        },

        uploadImage: function () {
            var me = this;
            var pat=this.properties.picAndTexts;
            for(var i=0;i<pat.length;i++){
                uploadForm({
                    ui: '#imageTextUpload',
                    inputName: 'file',
                    maxWidth: 540,
                    maxHeight: 540,
                    maxFileCount: 1,
                    isCongruent: false,
                    successCallback: function (files, data, xhr, pd) {
                        me.properties.pcImg = data.fileUri;
                    },
                    deleteCallback: function (resp, data, jqXHR) {
                        me.properties.pcImg = "";
                    }
                });

            }

        },


        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.addPAT();
            this.removePAT();
            //this.uploadImage();

        },
        close: function (globalId) {

        }
    }
});
