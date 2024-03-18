export default class WXStorage {
    static getStorage(key) {
        return new Promise((fulfill, reject) => {
            uni.getStorage({
                key: key,
                success: (res) => {
                    fulfill(res.data);
                },
                fail: (res) => {
                    reject();
                }
            });
        });
    }
    static setStorage(key, data) {
        return new Promise((fulfill, reject) => {
            uni.setStorage({
                key: key,
                data: data,
                success: (res) => {
                    fulfill();
                },
                fail: (res) => {
                    reject();
                }
            });
        });
    }
    static removeStorage(key) {
        return new Promise((fulfill, reject) => {
            uni.removeStorage({
                key: key,
                success: (res) => {
                    fulfill(res.data);
                },
                fail: (res) => {
                    reject();
                }
            });
        });
    }
}
