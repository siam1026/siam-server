// var events = new event();
// function event() {
//     //定义长度
//     var length = 0;
//     //创建一个对象
//     var obj = new Object();

//     /**
//      * 判断Map是否为空
//      */
//     this.isEmpty = function () {
//         return length == 0;
//     };

//     /**
//      * 判断对象中是否包含给定Key
//      */
//     this.containsKey = function (key) {
//         return key in obj;
//     };

//     /**
//      * 判断对象中是否包含给定的Value
//      */
//     this.containsValue = function (value) {
//         for (var key in obj) {
//             if (obj[key] == value) {
//                 return true;
//             }
//         }
//         return false;
//     };

//     /**
//      *向map中添加数据
//      */
//     this.put = function (key, callback) {
//         if (!this.containsKey(key)) {
//             length++;
//             obj[key] = {
//                 callbacks: [callback]
//             };
//         } else {
//             var callbacks = obj[key].callbacks;
//             if (!callbacks) {
//                 obj[key].callbacks = [];
//             }
//             obj[key].callbacks.push(callback);
//         }
//     };

//     /**
//      * 根据给定的Key获得Value
//      */
//     this.get = function (key) {
//         return this.containsKey(key) ? obj[key] : null;
//     };

//     /**
//      * 根据给定的Key删除一个值
//      */
//     this.remove = function (key) {
//         if (this.containsKey(key) && delete obj[key]) {
//             length--;
//         }
//     };

//     /**
//      * 获得Map中的所有Value
//      */
//     this.values = function () {
//         var _values = new Array();
//         for (var key in obj) {
//             _values.push(obj[key]);
//         }
//         return _values;
//     };

//     /**
//      * 获得Map中的所有Key
//      */
//     this.keySet = function () {
//         var _keys = new Array();
//         for (var key in obj) {
//             _keys.push(key);
//         }
//         return _keys;
//     };

//     /**
//      * 获得Map的长度
//      */
//     this.size = function () {
//         return length;
//     };

//     /**
//      * 清空Map
//      */
//     this.clear = function () {
//         length = 0;
//         obj = new Object();
//     };

//     /**
//      * 广播信息
//      */
//     this.boardcast = function (key, data) {
//         var objItem = obj[key];
//         if (!objItem) {
//             return;
//         }
//         var callbacks = objItem.callbacks;
//         if (callbacks && callbacks.length) {
//             for (var i in callbacks) {
//                 var callback = callbacks[i];
//                 if (callback) {
//                     callback(data);
//                 }
//             }
//         }
//     };

//     /**
//      * 接受数据
//      */
//     this.on = function (key, callback) {
//         var objItem = obj[key];
//         if (objItem && objItem.data) {
//             if (callback) {
//                 callback(objItem.data);
//             }
//         }
//     };
//     this.emit = function (key, data) {
//         var objItem = obj[key];
//         if (objItem) {
//             objItem.data = data;
//         } else {
//             obj[key] = {
//                 data: data
//             };
//         }
//     };
//     this.destory = function (key, callback) {
//         var objItem = obj[key];
//         var callbacks = objItem.callbacks;
//         if (callbacks && callbacks.length) {
//             var idx = callbacks.indexOf(callback);
//             if (idx >= 0) {
//                 callbacks[idx] = null;
//             }
//         }
//     };
// }
// module.exports = {
//     /**
//      * 订阅
//      */
//     subscribe: function (eventKey, callback) {
//         events.put(eventKey, callback);
//     },
//     /**
//      * 广播
//      */
//     boardcast: function (eventKey, data) {
//         events.boardcast(eventKey, data);
//     },
//     /**
//      * 销毁
//      */
//     destory: function (eventKey) {
//         events.remove(eventKey);
//     }
// };
