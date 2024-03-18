import storage from './storage';
import http from './http';
const TOKEN_KEY = 'security.token';
const OPENID_KEY = 'security.openid';
const PHONE_KEY = 'security.phone';
export default class AuthService {
    static getToken() {
        return storage.getStorage(TOKEN_KEY);
    }
    static setToken(token) {
        return storage.setStorage(TOKEN_KEY, token);
    }
    static deleteToken() {
        return storage.removeStorage(TOKEN_KEY);
    }
    static setOpenId(openId) {
        return storage.setStorage(OPENID_KEY, openId);
    }
    static getOpenId() {
        return storage.getStorage(OPENID_KEY);
    }
    static deleteOpenId() {
        return storage.removeStorage(OPENID_KEY);
    }
    static setPhoneNumber(phone) {
        return storage.setStorage(PHONE_KEY, phone);
    }
    static getPhoneNumber() {
        return storage.getStorage(PHONE_KEY);
    }
    static deletePhoneNumber() {
        return storage.removeStorage(PHONE_KEY);
    }
    static setWxStorage(storageText, storageValue) {
        return storage.setStorage('security.' + storageText, storageValue);
    }
    static getWxStorage(storageText) {
        console.log(storageText);
        return storage.getStorage('security.' + storageText);
    }
    static deleteOpenId(storageText) {
        return storage.removeStorage('security.' + storageText);
    }
    static checkIsLogin() {
        return new Promise((fulfil, reject) => {
            this.getToken().then(
                (token) => {
                    if (token) {
                        fulfil(true);
                    } else {
                        fulfil(false);
                    }
                },
                (err) => {
                    fulfil(false);
                }
            );
        });
    }
}
