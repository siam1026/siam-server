import GlobalConfig from './global-config';
export default {
    showToast: function (title = '', duration) {
        uni.showToast({
            title: title,
            icon: 'none',
            duration: duration ? duration : 3000
        });
    },
    /**
     * 成功
     */
    showSuccess: function (title, mask = true, duration = 3000) {
        uni.showToast({
            title: title,
            mask: mask,
            image: '/static/assets/images/success.png',
            duration: duration
        });
    },
    /**
     * 警告
     */
    showWarning: function (title, duration = 3000) {
        uni.showToast({
            title: title,
            image: '/static/assets/images/warning.png',
            duration: duration
        });
    },
    /**
     * 错误
     */
    showError: function (title, mask = false, duration = 3000) {
        uni.showToast({
            title: title,
            mask: mask,
            image: '/static/assets/images/error.png',
            duration: duration
        });
    },
    /**
     * 显示加载
     */
    showLoading: function (title = '正在加载...', mask = true) {
        uni.showLoading({
            title: title,
            mask: mask
        });
    },
    /**
     * 隐藏加载
     */
    hideLoading: function () {
        uni.hideLoading();
    },
    /**
     * 显示模态窗口
     */
    showModal: function (title = null, content = null, confirm = null, cancel = null, showCancel = true) {
        uni.showModal({
            title: title ? title : '温馨提示',
            content: content || '',
            showCancel: showCancel,
            success(res) {
                if (res.confirm) {
                    //console.log('用户点击确定');
                    if (confirm) {
                        confirm();
                    }
                } else if (res.cancel) {
                    //console.log('用户点击取消');
                    if (cancel) {
                        cancel();
                    }
                }
            }
        });
    }
};
