/**
 通用 utils
 */
import MSDataTransfer from './dataTranslate.js'
function formatMinutes(minutes) {
  let dd=Math.floor(Math.floor(minutes/60)/24);
  let hh=Math.floor(minutes/60)%24;
  let mm=minutes%60;
  let ret="";
  ret += dd===0?"":(dd+"天");
  ret += hh===0?"":(hh+"时");
  ret += mm+"分";
  return ret;
}

function formatSeconds(seconds,zh) {
  let dd=Math.floor(Math.floor(seconds/60/60)/24);
  let hh=Math.floor(seconds/60/60)%24;
  let mm=Math.floor(seconds/60)%60;
  let ret="";
  if(zh){
    ret += dd===0?"":(dd+"天");
    ret += hh===0?"":(hh+"时");
    ret += mm===0?"":(mm+"分");
    ret += seconds%60+"秒";
  }else{
    ret += dd===0?"":(dd+":");
    ret += hh===0?"":(prefixInteger(hh,2)+":");
    ret += mm===0?"":(prefixInteger(mm,2)+":");
    ret += prefixInteger(seconds%60,2);
  }
  return ret;
}

function formatSecondsShowHMS(seconds,zh) {
  let dd=Math.floor(Math.floor(seconds/60/60)/24);
  let hh=Math.floor(seconds/60/60)%24;
  let mm=Math.floor(seconds/60)%60;
  let ret="";
  if(zh){
    // ret += (dd===0?"0":dd)+"天";
    ret += (hh===0?"0":hh)+"时";
    ret += (mm===0?"0":mm)+"分";
    ret += seconds%60+"秒";
  }else{
    // ret += (dd===0?"00":dd)+":";
    ret += (hh===0?"00":prefixInteger(hh,2))+":";
    ret += (mm===0?"00":prefixInteger(mm,2))+":";
    ret += prefixInteger(seconds%60,2);
  }
  return ret;
}
//数组转成 以'，'隔开的字符串
function changeInfoType(Arr) {
  let aaa = '';
  for(let j = 0 ;j<Arr.length;j++){
    if(j<Arr.length-1){
      aaa += Arr[j] +',';
    } else{
      aaa += Arr[j];
    }
  }
  return aaa;
}

//时间戳转日期

function padLeftZero(str){
  return ('00'+str).substr(str.length);
}

function formatDate(date,fmt){
  if(/(y+)/.test(fmt)){
    fmt = fmt.replace(RegExp.$1, (date.getFullYear()+'').substr(4-RegExp.$1.length));
  }
  let o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  };
  for(let k in o){
    let str = o[k]+'';
    if(new RegExp(`(${k})`).test(fmt)){
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length===1)?str:padLeftZero(str));
    }
  }
  return fmt;
}

// num传入的数字，n需要的字符长度
function prefixInteger(num, n) {
  return (Array(n).join(0) + num).slice(-n);
}

//base64加密
function Base64(input) {
  // private property
  const _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
  let inputX = input;
  // private method for UTF-8 encoding
  function _utf8_encode(){
    inputX = inputX.replace(/\r\n/g,"\n");
    var utftext = "";
    for (var n = 0; n < inputX.length; n++) {
      var c = inputX.charCodeAt(n);
      if (c < 128) {
        utftext += String.fromCharCode(c);
      } else if((c > 127) && (c < 2048)) {
        utftext += String.fromCharCode((c >> 6) | 192);
        utftext += String.fromCharCode((c & 63) | 128);
      } else {
        utftext += String.fromCharCode((c >> 12) | 224);
        utftext += String.fromCharCode(((c >> 6) & 63) | 128);
        utftext += String.fromCharCode((c & 63) | 128);
      }

    }
    return utftext;
  };
  // public method for encoding
  let output = "";
  let chr1, chr2, chr3, enc1, enc2, enc3, enc4;
  let i = 0;
  let inputA = _utf8_encode();
  while (i < inputA.length) {
    chr1 = inputA.charCodeAt(i++);
    chr2 = inputA.charCodeAt(i++);
    chr3 = inputA.charCodeAt(i++);
    enc1 = chr1 >> 2;
    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
    enc4 = chr3 & 63;
    if (isNaN(chr2)) {
      enc3 = enc4 = 64;
    } else if (isNaN(chr3)) {
      enc4 = 64;
    }
    output = output + _keyStr.charAt(enc1) + _keyStr.charAt(enc2) + _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
  }
  return output;

}
function DeepCopy(obj) { // 对象深拷贝
  let newObj
  if (obj instanceof Array) { // 数组类型的拷贝
    newObj = []
    let i = obj.length
    while(i--) {
      newObj[i] = this.DeepCopy(obj[i])
    }
    return newObj
  } else if (obj instanceof Object) { // 对象类型的拷贝
    newObj = {}
    for (let k in obj) {
      newObj[k] = this.DeepCopy(obj[k])
    }
    return newObj
  } else {
    return obj
  }
}

function newToFixed (){
  Number.prototype.toFixed = function (n) {
    if (n > 20 || n < 0) {
      throw new RangeError('toFixed() digits argument must be between 0 and 20');
    }
    const number = this;
    if (isNaN(number) || number >= Math.pow(10, 21)) {
      return number.toString();
    }
    if (typeof (n) == 'undefined' || n == 0) {
      return (Math.round(number)).toString();
    }
    let result = number.toString();
    const arr = result.split('.');
    // 整数的情况
    if (arr.length < 2) {
      result += '.';
      for (let i = 0; i < n; i += 1) {
        result += '0';
      }
      return result;
    }
    const integer = arr[0];
    const decimal = arr[1];
    if (decimal.length == n) {
      return result;
    }
    if (decimal.length < n) {
      for (let i = 0; i < n - decimal.length; i += 1) {
        result += '0';
      }
      return result;
    }
    result = integer + '.' + decimal.substr(0, n);
    const last = decimal.substr(n, 1);
    // 四舍五入，转换为整数再处理，避免浮点数精度的损失
    if (parseInt(last, 10) >= 5) {
      const x = Math.pow(10, n);
      result = (Math.round((parseFloat(result) * x)) + 1) / x;
      result = result.toFixed(n);
    }
    return result;
  };
}

export default {
  install: function(Vue, name = '$utils') {
    Object.defineProperty(Vue.prototype, name, { value: this });
  },
  formatMinutes,
  formatSeconds,
  formatSecondsShowHMS,
  changeInfoType,
  formatDate,
  Base64,
  MSDataTransfer,
  DeepCopy,
  newToFixed
}

export {formatDate}
