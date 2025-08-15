// var Base64 = {
//     _keyStr: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',
//     encode(str) {
//         // 加密
//         var output = '';
//         var chr1;
//         var chr2;
//         var chr3;
//         var enc1;
//         var enc2;
//         var enc3;
//         var enc4;
//         var i = 0;
//         str = this._utf16to8(str);
//         while (i < str.length) {
//             chr1 = str.charCodeAt(i++);
//             chr2 = str.charCodeAt(i++);
//             chr3 = str.charCodeAt(i++);
//             enc1 = chr1 >> 2;
//             enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//             enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//             enc4 = chr3 & 63;
//             if (isNaN(chr2)) {
//                 enc3 = enc4 = 64;
//             } else if (isNaN(chr3)) {
//                 enc4 = 64;
//             }
//             output = output + this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) + this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);
//         }
//         return output;
//     },
//     decode(input) {
//         // 解密
//         var output = '';
//         var chr1;
//         var chr2;
//         var chr3;
//         var enc1;
//         var enc2;
//         var enc3;
//         var enc4;
//         var i = 0;
//         input = input.replace(/[^A-Za-z0-9\+\/\=]/g, '');
//         while (i < input.length) {
//             enc1 = this._keyStr.indexOf(input.charAt(i++));
//             enc2 = this._keyStr.indexOf(input.charAt(i++));
//             enc3 = this._keyStr.indexOf(input.charAt(i++));
//             enc4 = this._keyStr.indexOf(input.charAt(i++));
//             chr1 = (enc1 << 2) | (enc2 >> 4);
//             chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
//             chr3 = ((enc3 & 3) << 6) | enc4;
//             output = output + String.fromCharCode(chr1);
//             if (enc3 != 64) {
//                 output = output + String.fromCharCode(chr2);
//             }
//             if (enc4 != 64) {
//                 output = output + String.fromCharCode(chr3);
//             }
//         }
//         return this._utf8to16(output);
//     },
//     _utf16to8: function (str) {
//         var out;
//         var i;
//         var len;
//         var c;
//         out = '';
//         len = str.length;
//         for (i = 0; i < len; i++) {
//             c = str.charCodeAt(i);
//             if (c >= 1 && c <= 127) {
//                 out += str.charAt(i);
//             } else if (c > 2047) {
//                 out += String.fromCharCode(224 | ((c >> 12) & 15));
//                 out += String.fromCharCode(128 | ((c >> 6) & 63));
//                 out += String.fromCharCode(128 | ((c >> 0) & 63));
//             } else {
//                 out += String.fromCharCode(192 | ((c >> 6) & 31));
//                 out += String.fromCharCode(128 | ((c >> 0) & 63));
//             }
//         }
//         return out;
//     },
//     _utf8to16(str) {
//         var out;
//         var i;
//         var len;
//         var c;
//         var char2;
//         var char3;
//         out = '';
//         len = str.length;
//         i = 0;
//         while (i < len) {
//             c = str.charCodeAt(i++);
//             switch (c >> 4) {
//                 case 0:
//                 case 1:
//                 case 2:
//                 case 3:
//                 case 4:
//                 case 5:
//                 case 6:
//                     case7: out += str.charAt(i - 1);
//                     break;
//                 case 12:
//                 case 13:
//                     char2 = str.charCodeAt(i++);
//                     out += String.fromCharCode(((c & 31) << 6) | (char2 & 63));
//                     break;
//                 case 14:
//                     char2 = str.charCodeAt(i++);
//                     char3 = str.charCodeAt(i++);
//                     out += String.fromCharCode(((c & 15) << 12) | ((char2 & 63) << 6) | ((char3 & 63) << 0));
//                     break;
//             }
//         }
//         return out;
//     }
// };

// /* 暴露函数 */
// // module.exports = Base64;
