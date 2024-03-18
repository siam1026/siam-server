//门店营业时间获取-专为门店使用
const formatTime = (format) => {
    const dataTime = new Date();
    const year = dataTime.getFullYear();
    const month = dataTime.getMonth() + 1;
    const day = dataTime.getDate();
    const hour = dataTime.getHours();
    const minute = dataTime.getMinutes();
    const second = dataTime.getSeconds();
    if (format == 'hm') {
        return [hour, minute].map(formatNumber).join(':');
    }
    if (format == 'hms') {
        return [hour, minute, second].map(formatNumber).join(':');
    }
    if (format == 'YMD') {
        return [year, month, day].map(formatNumber).join('/');
    }
    return [year, month, day].map(formatNumber).join('-') + ' ' + [hour, minute, second].map(formatNumber).join(':');
};
const formatNumber = (n) => {
    n = n.toString();
    return n[1] ? n : '0' + n;
};

// 转义特殊字符格式（发送请求的字符）
const characterEscape = (data) => {
    data = data
        .replace(/\{/g, '%7B')
        .replace(/\}/g, '%7D')
        .replace(/\:/g, '%3A')
        .replace(/\,/g, '%2C')
        .replace(/\ /g, '%2C')
        .replace(/\+/g, '%2B')
        .replace(/\//g, '%2F')
        .replace(/\?/g, '%3F')
        .replace(/\#/g, '%23')
        .replace(/\&/g, '%26')
        .replace(/\=/g, '%3D')
        .replace(/\\/g, '%5C');
    return data;
};

//验证手机号码格式是否正确
const verifyPhone = (data) => {
    var mobile = /^[1][0-9][0-9]{9}$/;
    var isMobile = mobile.exec(data);
    return isMobile;
};

//验证邮箱地址是否正确
const verifyEmail = (data) => {
    let email = /^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*\.[a-zA-Z0-9]{2,6}$/;
    if (email.test(data)) {
        return true;
    }
    return false;
};

//验证是否是数字
const verifyNumberPassword = (data) => {
    let number = /^[0-9]*$/;
    if (number.test(data)) {
        return true;
    }
    return false;
};

//验证有两位小数的正实数：
const verifyNumber = (data) => {
    let is = true;
    var replaceArray = [];
    for (let i = 0; i < data.length; ++i) {
        //正则判断是否合法
        var textValue = /^[0-9_.]$/.test(data.charAt(i));
        if (!textValue) {
            replaceArray.push(data.charAt(i));
        }
    }
    // console.log(replaceArray)
    if (replaceArray.length != 0) {
        is = false;
    }
    return is;
};
const toFixed = (number, digit) => {
    return Number(number.toFixed(digit ? digit : 2));
};
export default {
    formatTime: formatTime,
    formatNumber: formatNumber,
    characterEscape: characterEscape,
    verifyPhone: verifyPhone,
    verifyEmail: verifyEmail,
    verifyNumber: verifyNumber,
    verifyNumberPassword: verifyNumberPassword,
    toFixed: toFixed
};
