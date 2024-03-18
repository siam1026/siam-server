// var moment = require('./moment.min');
// var utilsHelper = require('./util');
import utilsHelper from './util';
const formatISODate = (date, format) => {
    // if (!format) {
    //     format = 'YYYY-MM-DD HH:mm:ss';
    // }
    // return moment(date).utc().format(format);
};
const formatDate = (date) => {
    if (date) {
        var dateee = new Date(date).toJSON();
        const dateTime = new Date(+new Date(dateee) + 28800000)
            .toISOString()
            .replace(/T/g, ' ')
            .replace(/\.[\d]{3}Z/, '');
        return dateTime;
    }
};
//解决ios不兼容new Date()方法
const fmtDate = (dateTime) => {
    let date = new Date(dateTime.replace(/^(\d{4}-\d{2}-\d{2})T(\d{2}:\d{2}:\d{2}).*$/, '$1 $2 GMT+0000').replace(/-/g, '/'));
    return formatDate(date);
};
const formatNumber = (n) => {
    n = n.toString();
    return n[1] ? n : '0' + n;
};

//专为自取和配送时间而定+自取加3，配送加33
function formatTime(format) {
    const app = getApp();
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var hour = date.getHours();
    var minute = date.getMinutes();
    var second = date.getSeconds();
    if (app.globalData.deliveryAndSelfTaking.radioIndex == 0) {
        minute = minute + 33;
    }
    if (app.globalData.deliveryAndSelfTaking.radioIndex == 1) {
        minute = minute + 3;
    }
    if (minute >= 60) {
        hour += 1;
        if (hour == 24) {
            hour = '00';
        }
        minute = minute % 60;
    }
    if (format == 'YMD') {
        return [year, month, day].map(formatNumber).join('-');
    }
    if (format == 'hms') {
        return [hour, minute].map(formatNumber).join(':');
    }
    return [year, month, day].map(formatNumber).join('/') + ' ' + [hour, minute].map(formatNumber).join(':');
}
// 判断商家是否打烊
function differenceTime(start, end) {
    let date = utilsHelper.formatTime('YMD');
    let time = new Date(date + ' ' + utilsHelper.formatTime('hm'));
    let startTime = new Date(date + ' ' + start);
    let endTime = new Date(date + ' ' + end);
    let isBusiness = false;
    if (startTime < endTime) {
        //console.log("开始时间小于结束时间")
        if (startTime <= time && time <= endTime) {
            //console.log("开始时间小于结束时间")
            isBusiness = true;
        }
    }
    if (startTime > endTime) {
        //console.log("开始时间大于结束时间")
        if (startTime <= time || time <= endTime) {
            isBusiness = true;
        }
    }
    if (startTime == endTime) {
        //console.log("开始时间等于结束时间")
        isBusiness = true;
    }
    return isBusiness;
}
//判断商家打样时间是否在30分钟之内
function itNearTime(start, end) {
    let itNear = false;
    let date = utilsHelper.formatTime('YMD');
    let nowTime = new Date(date + ' ' + utilsHelper.formatTime('hms'));
    let startTime = new Date(date + ' ' + start);
    let endTime = new Date(date + ' ' + end);
    console.log('商家打烊时间是否开门时间====>' + (startTime > endTime));
    if (startTime > endTime) {
        endTime = new Date((endTime / 1000 + 86400) * 1000);
    }
    var millisecond = new Date(endTime).getTime() - nowTime.getTime(); //时间差的毫秒数

    //计算出相差天数
    var days = Math.floor(millisecond / 86400000);

    //计算出小时数

    var leave1 = millisecond % 86400000; //计算天数后剩余的毫秒数
    var hours = Math.floor(leave1 / 3600000);
    //计算相差分钟数
    var leave2 = leave1 % 3600000; //计算小时数后剩余的毫秒数
    var minutes = Math.floor(leave2 / 60000);
    //计算相差秒数
    var leave3 = leave2 % 60000; //计算分钟数后剩余的毫秒数
    var seconds = Math.round(leave3 / 1000);
    if (hours <= 0) {
        if (minutes <= 30) {
            itNear = true;
        }
    }
    console.log(' 相差 ' + days + '天 ' + hours + '小时 ' + minutes + ' 分钟' + seconds + ' 秒');
    // console.log("计算当前时间是否大于打烊时间30分钟====》"+(endTime.getTime()-startTime.getTime()));
    // if(endTime.getTime()-startTime.getTime()>30){
    //   itNear=true;
    // }
    return itNear;
}
function getTimestamp() {
    var timestamp = Date.parse(new Date());
    return timestamp;
}
export default {
    formatISODate: formatISODate,
    formatDate: formatDate,
    fmtDate: fmtDate,
    formatNumber: formatNumber,
    formatTime: formatTime,
    differenceTime: differenceTime,
    itNearTime: itNearTime,
    getTimestamp: getTimestamp
};
