import GlobalConfig from './global-config';
import authService from './auth';
const initHeaders = function (headers) {
    return new Promise((fulfill, reject) => {
        let headersObj = {};
        headersObj['lang'] = 'zh-CN';
        // headersObj["content-type"] = "application/x-www-form-urlencoded";
        headersObj['content-type'] = 'application/json';
        if (headers) {
            for (var propName in headers) {
                headersObj[propName] = headers[propName];
            }
        }
        authService.getToken().then(
            (token) => {
                if (token) {
                    headersObj['Authorization'] = token;
                }
                fulfill(headersObj);
            },
            () => {
                fulfill(headersObj);
            }
        );
    });
};
const handleError = function (err) {
    let body = {};
    try {
        if (err && err.success) {
            body = err;
        } else {
            body = err;
        }
    } catch (cerr) {
        console.log('CatchClause', cerr);
        console.log('CatchClause', cerr);
        body = {
            errorCode: err.code,
            errorMsg: err.message
        };
    }
    err = body;
    const errorCode = err && err ? err.code + '' : 0;
    switch (errorCode) {
        case '404':
            uni.showToast({
                title: '错误代码(404): 接口不存在或者已经不可用。',
                icon: 'none',
                duration: 3000
            });
            break;
        case '401':
            authService.deleteToken();
            uni.showToast({
                title: '用户已过期或者未登录，请登录后再使用！',
                icon: 'none',
                duration: 3000
            });
            break;
        case '403':
            uni.showToast({
                title: '您没有权限访问该功能！',
                icon: 'none',
                duration: 3000
            });
            break;
        case '500':
            uni.showToast({
                title: '内部服务器错误！',
                icon: 'none',
                duration: 3000
            });
            break;
        case '400':
            uni.showToast({
                title: '错误的请求！',
                icon: 'none',
                duration: 3000
            });
            break;
        case '502':
            uni.showToast({
                title: '服务器开小差了，稍后再试吧！',
                icon: 'none',
                duration: 3000
            });
            break;
        default:
            if (errorCode) {
                uni.showToast({
                    title: err.message,
                    icon: 'none',
                    duration: 3000
                });
            } else {
                uni.showToast({
                    title: '内部服务器错误！',
                    icon: 'none',
                    duration: 3000
                });
            }
            break;
    }
};
export default class WXHttp {
    static request(url, postData, headers = null) {
        //fulfill代表异步操作成功,reject:代表异步操作失败
        return new Promise((fulfill, reject) => {
            initHeaders(headers).then((headersObj) => {
                var errObj = {};
                uni.request({
                    url: GlobalConfig.baseUrl + url,
                    data: JSON.stringify(postData),
                    method: 'POST',
                    dataType: 'json',
                    header: headersObj,
                    success: (res) => {
                        const body = res.data;
                        try {
                            if (body && !body.success) {
                                errObj.code = res.statusCode ? res.statusCode : 101;
                                errObj.message = res.data.message;
								if (res.data.code == 2) {
									authService.deleteToken();
									authService.deleteOpenId();
									authService.deletePhoneNumber();
								}
                                if (res.data.code != 2) {
                                    handleError(errObj);
                                    reject(errObj);
                                    fulfill(body);
                                }
                            }
                            fulfill(body); //返回数据，在调用函数后面加.then
                        } catch (ex) {
                            console.log('CatchClause', ex);
                            console.log('CatchClause', ex);
                            errObj.code = res.statusCode ? res.statusCode : 101;
                            errObj.message = res.errMsg;
                            handleError(errObj);
                            reject(errObj);
                        } finally {
                            fulfill(body); //返回数据，在调用函数后面加.then
                        }
                    },

                    fail: (err) => {
                        console.log(err);
                        errObj.code = err.statusCode ? err.statusCode : 101;
                        errObj.message = err.errMsg.split(' ')[1];
                        handleError(err);
                        reject(errObj);
                    }
                });
            });
        });
    }

    /**
     * 单个本地资源上传
     * options
     * sourceType:'camera':照相机，'album':从相册
     * url:服务器资源地址
     * formData:额外formData
     * headers 中不能设置 Referer
     * success:上传成功
     * fail:上传失败
     * complete:上传成功、失败都会执行
     */
    static uploadFile(options) {
        return new Promise((fulfill, reject) => {
            initHeaders(options.headers).then((headersObj) => {
                uni.showActionSheet({
                    //获取底部菜单
                    itemList: ['拍照', '从手机相册选择'],
                    //底部菜单选项
                    success: (res) => {
                        uni.chooseImage({
                            count: 1,
                            sizeType: ['original', 'compressed'],
                            // 可以指定是原图还是压缩图，默认二者都有
                            sourceType: [res.tapIndex == 0 ? 'camera' : 'album'],
                            success: (res) => {
                                var errObj = {};
                                let filePath = res.tempFilePaths[0]; //获取选中的图片或者拍照的图片
                                let url = GlobalConfig.baseUrl + options.url; //上传接口路径
                                uni.uploadFile({
                                    //上传方法
                                    url: url,
                                    filePath: filePath,
                                    name: 'file',
                                    header: headersObj,
                                    formData: options.formData,
                                    success: (res) => {
                                        try {
                                            var result = JSON.parse(res.data);
                                            if (result && !result.success) {
                                                errObj.status = res.statusCode ? res.statusCode : 101;
                                                errObj.message = res.errMsg;
                                                handleError(errObj);
                                                reject(errObj);
                                                return;
                                            }
                                            fulfill(result); //返回数据，在调用函数后面加.then
                                        } catch (ex) {
                                            console.log('CatchClause', ex);
                                            console.log('CatchClause', ex);
                                            errObj.status = res.statusCode ? res.statusCode : 101;
                                            errObj.message = res.errMsg;
                                            handleError(errObj);
                                            reject(errObj);
                                        }
                                    },
                                    fail: (error) => {
                                        //console.log(error)
                                        errObj.status = error.statusCode ? error.statusCode : 101;
                                        errObj.message = error.errMsg.split(' ')[1];
                                        handleError(errObj);
                                        reject(errObj);
                                    }
                                });
                            }
                        });
                    }
                });
            });
        });
    }
    static uploadImage(options) {
        return new Promise((fulfill, reject) => {
            initHeaders(options.headers).then((headersObj) => {
                let url = options.url;
                const uploadTask = uni.uploadFile({
                    url: url,
                    filePath: options.filePath,
                    name: 'file',
                    header: headersObj,
                    formData: options.formData,
                    success: (res) => {
                        var result = JSON.parse(res.data);
                        if (options.success) {
                            options.success(result);
                        }
                    },
                    fail: (error) => {
                        if (options.fail) {
                            options.fail(error);
                        }
                    },
                    complete: () => {
                        if (options.complete) {
                            options.complete();
                        }
                    }
                });
                fulfill(uploadTask);
            });
        });
    }
}
