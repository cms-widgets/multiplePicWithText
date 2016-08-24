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

        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.addPAT();
            this.removePAT();
            this.uploadImage();
        },
        close: function (globalId) {

        }
    }
});
