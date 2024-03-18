import {showToast} from './toast.service';
module.exports = {
    isCertification: function () {
        let isCertification = uni.getStorageSync('isCertification');
        uni.showModal({
            content: '请完善您的基本信息',
            success(res) {
                if (res.confirm) {
                    uni.navigateTo({
                        url: '../update_info/update_info'
                    });
                    return;
                } else if (res.cancel) {
                    uni.navigateBack(1);
                }
            }
        });
    },
    isUserName: function () {
        uni.showModal({
            content: '请设置您的昵称',
            success(res) {
                if (res.confirm) {
                    uni.navigateTo({
                        url: '../update_name/update_name'
                    });
                } else if (res.cancel) {
                    uni.navigateBack(1);
                }
            }
        });
    },
    isToken: function () {
        let token = uni.getStorageSync('token');
        if (!token) {
            uni.reLaunch({
                url: '../pages/mine/index/index'
            });
        }
    }
};
