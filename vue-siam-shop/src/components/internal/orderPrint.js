/**
 * 订单本地打印(自动) 与 订单语音提醒 定时器
 */
const qs = require('qs');

import $ from 'jquery'

import $http from '../../utils/http'

var that = '';

// var urlPrefix = 'https://api.siam.shop';
var urlPrefix = 'https://test-api.siam.shop';
// var urlPrefix = 'http://localhost:8060/siam-multi-brand-server';

//默认标识打印成功
var printIsSuccess = true;

//全局音频变量
var url = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_tip.mp3";
var audio = new Audio(url);

//开关设置
var isOpenOrderAudio = false;
var isOpenLocalPrint = false;

//PS：用域名访问不行，用ip访问可以 -- 或许是服务器上要对websocket做域名配置
// var wsUri = "ws://siammultibrandapi.siam.cn/websocket/";
var wsUri = "ws://121.41.42.84:9601/api-order/websocket/";
// var wsUri = "ws://127.0.0.1:8060/api-order/websocket/";
var output;
var websocket;

const install = function(Vue) {
    Object.defineProperties(Vue.prototype, {
        $orderPrint: {
            get() {
                return {
                    //初始化订单打印事件
                    //PS: 在这个init()方法中如果程序被return，那么在当前页面下就不会再请求自动打印功能了，除非刷新页面；
                    init: function () {
                        //定义全局变量，方面后续使用
                        that = this;          
                        //拼接WebSocket的请求路径
                        var user = sessionStorage.getItem('user');
                        if (user) {
                            user = JSON.parse(user);
                            wsUri = wsUri + user.mobile;
                        }                        
                        //启动WebSocket程序
                        this.startWebSocket();
                    },
                    startWebSocket() {
                        websocket = new WebSocket(wsUri);
                        websocket.onopen = function(evt) { that.onOpen(evt) };
                        websocket.onclose = function(evt) { that.onClose(evt) };
                        websocket.onmessage = function(evt) { that.onMessage(evt) };
                        websocket.onerror = function(evt) { that.onError(evt) };
                    },
                    onOpen(evt) {
                        console.log("\n\nwebsocket -> CONNECTED");
                        this.doSend("WebSocket rocks");
                    },
                    onClose(evt) {
                        console.log("\n\nwebsocket -> DISCONNECTED");
                    },
                    onMessage(evt) {
                        console.log('\n\nwebsocket -> <span style="color: blue;">RESPONSE: ' + evt.data+'</span>');
                        /*websocket.close();*/

                        var mark = evt.data;
                        switch(mark){
                            //新订单来了 -- 未开启本地打印
                            case "newOrder" : 
                                this.speckText();
                                break;
                            //新订单来了 -- 开启了本地打印
                            case "newOrderWithPrint" : 
                                this.speckText();
                                that.triggerOrderPrint();
                                break;                                                                
                            //订单10分钟内未点击配送/制作完成，播放超时提醒
                            case "orderOvertime" : 
                                this.speckTextOrderOvertime();
                                break;                                
                            //有订单申请退款
                            case "orderApplyRefund" : 
                                this.speckTextOrderApplyRefund();
                                break;
                            //自取订单改成配送 -- 未开启本地打印                   
                            case "orderChangeToDelivery" : 
                                this.speckTextChangeToDelivery();
                                break;
                            //自取订单改成配送 -- 开启了本地打印                                
                            case "orderChangeToDeliveryWithPrint" : 
                                this.speckTextChangeToDelivery();
                                that.triggerOrderPrint();
                                break;                                
                            //本地打印 -- 未开启语音提醒
                            case "newOrderPrint" : 
                                that.triggerOrderPrint();
                                break;                                   
                        }
                    },
                    onError(evt) {
                        console.log('\n\nwebsocket -> <span style="color: red;">ERROR:</span> ' + evt.data);
                    },
                    doSend(message) {
                        console.log("\n\nwebsocket -> SENT: " + message);
                        websocket.send(message);
                    },                
                    speckText(str) {
                        // 你有新的暹罗点餐订单，请注意查收！
                        // var url= "http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=6&text=" + encodeURI(str); 
                        // var url = "order_tip.mp3";
                        // var url = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_tip.mp3";
                        // var audio = new Audio(url);
                        // audio.play();

                        //如何解决音频播放声音重叠问题？
                        //每次自动检测订单，无论有多少订单量，只播放一次音频 => 使用全局的audio变量，这样即使你N个audio.play()方法要执行，audio也只会继续播放当前音频，其他的直接忽略掉不会处理;
                        //每次自动检测订单，有N个订单量，就播放N次音频 => 使用全局的audio变量，用计数器代替循环，在回调中进行递归执行audio.play()方法，然后计数器+1，这样就可以达到音频一个一个的有序播放了；
                        audio.src = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_tip.mp3";
                        audio.play();                        
                    },
                    speckTextChangeToDelivery(str) {
                        audio.src = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_change_to_delivery_tip.mp3";
                        audio.play();                                        
                    },        
                    speckTextOrderOvertime(str) {
                        audio.src = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_overtime_tip.mp3";
                        audio.play();                                        
                    },     
                    speckTextOrderApplyRefund(str) {
                        audio.src = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_apply_refund_tip.mp3";
                        audio.play();                                        
                    },      
                    speckTextHttpPrinterNotStarted() {
                        // 你有新的暹罗点餐订单，请注意查收！
                        // var str = "HttpPrinter打印机未开启，无法进行本地打印";
                        var str = "打印机未开启，无法进行本地打印";
                        var url = "http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=6&text=" + encodeURI(str); 
                        audio.src = url;
                        audio.play();            
                    },         
                    triggerOrderPrint() {
                        //检测HttpPrinter.exe程序是否启动，如果未启动 则不开启本地打印定时器扫描
                        let isStartHttpPrinter = true;
                        let url = "http://127.0.0.1:12345/printreport";
                        $.ajax({
                            type: "post",
                            url: url,
                            async: false,
                            data: {},
                            success: function(data){
                                //HttpPrinter.exe程序已经启动
                                if(isStartHttpPrinter){
                                    that.autoPrintOrderInfo();
                                }                                    
                            },
                            error: function(data){
                                console.log('HttpPrinter.exe程序未启动');
                                that.speckTextHttpPrinterNotStarted();
                                isStartHttpPrinter = false;                                
                            }
                        });
                    },                                                                                                      
                    autoPrintOrderInfo() { // 获取订单列表      
                        let vue = this
                        //查询所有已付款未打印的订单
                        $.ajax({
                            type: "post",
                            url: urlPrefix + '/rest/merchant/order/waitPrintOrderList',
                            async: true,
                            data: {},
                            beforeSend: function(request) {
                                request.setRequestHeader("token", sessionStorage.getItem("token"));
                            },
                            success: function(data){
                                let orderList = data.data;
                                //进行打印操作
                                if(orderList!=undefined && orderList.length>0){
                                    for(let i = 0; i < orderList.length; i++){
                                        that.printOrderDetail(orderList[i].id);
                                    }
                                }
                            },
                            error: function(data){
                                console.log('waitPrintOrderList接口请求报错');      
                            }
                        });	             
                    },
                    //打印
                    printOrderDetail: function (id) {
                        //默认标识打印成功
                        printIsSuccess = true;
            
                        //数据行
                        let AData = '';
            
                        //数据行
                        let BData = '';
            
                        //获取订单信息
                        $.ajax({
                            type: "post",
                            url: urlPrefix + '/rest/merchant/order/selectById',
                            async: true,
                            data: {"id" : id},
                            beforeSend: function(request) {
                                request.setRequestHeader("token", sessionStorage.getItem("token"));
                            },                                 
                            success: function(data){
                                let order = data.data;

                                let shoppingWayText = order.shoppingWay == 1 ? '自取' : '配送';
                                if(order.isChangeToDelivery){
                                    shoppingWayText = '由自取订单改为配送';
                                }

                                let address = order.contactProvince + order.contactCity + order.contactArea + order.contactStreet + order.contactHouseNumber;

                                //数据行
                                AData = '['+
                                    '{'+
                                        '"title": "'+ order.shopName +'", '+
                                        '"shoppingWay": "配送方式：'+ shoppingWayText +'", '+
                                        '"nickname": "'+ order.contactRealname + (order.contactSex == 0 ? '' : (order.contactSex == 1 ? '(先生)' : '(女士)')) +'", '+
                                        '"mobile": "'+ order.contactPhone +'", '+
                                        '"logo": "'+ 'https://siam.oss-cn-hangzhou.aliyuncs.com/data/images/bussiness/logo.jpg' +'", '+
                                        '"actualPrice": "'+ order.actualPrice +'", '+
                                        '"contactStreet": "'+ address +'", '+
                                        '"contactStreet": " '+ (order.shoppingWay == 1 ? '' : ('配送地址：'+address)) +'", '+
                                        '"queueNumber": "'+ order.queueNo +'", '+
                                        '"packingCharges": "'+ order.packingCharges +'", '+
                                        '"deliveryFee": "'+ order.deliveryFee +'", '+
                                        '"fullReductionRuleDescription": "'+ ((order.fullReductionRuleDescription == '' || order.fullReductionRuleDescription == null) ? '无' : (order.fullReductionRuleDescription)) +'", '+
                                        '"couponsDescription": "'+ ((order.couponsDescription == '' || order.couponsDescription == null) ? '无' : (order.couponsDescription)) +'", '+
                                        '"remark": "'+ ((order.remark == '' || order.remark == null) ? '无' : (order.remark)) +'", '+                                        
                                    '}'+
                                ']';		
                                
                                
                                //获取订单明细信息
                                $.ajax({
                                    type: "post",
                                    url: urlPrefix + '/rest/merchant/orderDetail/list',
                                    async: false,
                                    data: {"pageNo" : -1, "pageSize" : 20, "orderId" : id},
                                    beforeSend: function(request) {
                                        request.setRequestHeader("token", sessionStorage.getItem("token"));
                                    },                                         
                                    success: function(data){
                                        let orderDetailList = data.data.records
                                        //累加商品信息
                                        let str = '';
                                        for(let i = 0; i < orderDetailList.length; i++){
                                            let orderDetail = orderDetailList[i];
                                            let specStr = '';
                                            let specList = JSON.parse(orderDetail.specList);
                                            for(var key in specList){
                                                if(specStr == ''){
                                                    specStr += specList[key];	
                                                }else{
                                                    specStr += '/' + specList[key];
                                                }
                                            }
                                            str += '{"goodsName": "'+ orderDetail.goodsName +'", "specList": "('+ specStr +')", "number": "'+ orderDetail.number +'", "subtotal": "'+ orderDetail.subtotal +'"},';
                                        }
                                        //数据行
                                        BData = '['
                                                + str
                                                +']';						
                                        //打印小票
                                        that.printOrderDetailTicket(AData, BData);

                                        //如果是由自取订单改为配送，则无需打印标签
                                        if(!order.isChangeToDelivery){
                                            //打印小票后，接着打印标签
                                            for(let i = 0; i < orderDetailList.length; i++){
                                                let orderDetail = orderDetailList[i];
                                                let specStr = '';
                                                let specList = JSON.parse(orderDetail.specList);
                                                for(var key in specList){
                                                    if(specStr == ''){
                                                        specStr += specList[key];	
                                                    }else{
                                                        specStr += '/' + specList[key];
                                                    }
                                                }
                                                str += '{"goodsName": "'+ orderDetail.goodsName +'", "specList": "('+ specStr +')", "number": "'+ orderDetail.number +'"},';
                
                                                //数据行
                                                let AData_label = '['+
                                                        '{'+
                                                            '"queueNumber": "'+ order.queueNo +'", '+
                                                            '"shoppingWay": "'+ (order.shoppingWay == 1 ? '自取' : '配送') +'", '+
                                                            '"goodsName": "'+ orderDetail.goodsName +'", '+
                                                            '"specList": "('+ specStr +')", '+
                                                        '}'+
                                                ']';
                
                                                //按照购买数量循环进行打印
                                                for(let j = 0; j < orderDetail.number; j++){
                                                    that.printOrderDetailLabel(AData_label);								
                                                }
                                            }
                                        }
                                    },
                                    error: function(data){
                                        console.log('orderDetail-list接口请求报错');
                                    }
                                });	     
                                                                
                            },
                            error: function(data){
                                console.log('order-selectById接口请求报错');
                            }
                        });	     		                        
                    },  
                    printOrderDetailTicket(AData, BData) { //打印小票
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
            
                        if(AData == undefined || AData == "" || BData == undefined || BData == ""){
                            console.log('订单信息不能为空');
                            return false;
                        }
            
                        //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
                        let AField = '['
                                +'{"type": "ftString", "name": "title","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "shoppingWay","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "nickname","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "mobile","size": 255,"required": false},'
                                +'{"type": "ftBlob", "name": "logo","size": 0,"required": false},'
                                +'{"type": "ftString", "name": "actualPrice","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "contactStreet","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "queueNumber","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "packingCharges","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "deliveryFee","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "fullReductionRuleDescription","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "couponsDescription","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "remark","size": 255,"required": true},'                                
                        +']';
            
                        //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
                        let BField = '['  
                                +'{"type": "ftString", "name": "goodsName","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "specList","size": 255,"required": false},'						
                                +'{"type": "ftString", "name": "number","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "subtotal","size": 255,"required": false},'                                
                                // +'{"type": "ftString", "name": "je","size": 255,"required": false},'
                                +']';
            
                        let param = {
                            "ReportType": "fastreport",     /*报表类型 gridreport fastreport reportmachine 为空 将默认为gridreport  */	
                            "ReportName": "siamTicket.fr3",     /*报表文件名 POS小票 */
                            "ReportVersion": 1,              /*可选。报表版本, 为空则默认1  如果本地报表的版本过低 将从 ReportUrl 地址进行下载更新*/
                            //"ReportUrl": "http://111.67.202.157:9099/report/fastreport/AAAPosTicket.fr3",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
                            "ReportUrl": "",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
                            "Copies": 1,                  /*可选。打印份数，支持指定打印份数。默认1份,如果为零,不打印,只返回报表生成的pdf,jpg等文件*/
                            // "PrinterName": printerName,      /*可选。指定打印机，为空的话 使用默认打印机, 请在 控制面板 -> 设备和打印机 中查看您的打印机的名称 */
                            "PrintOffsetX": 0,                 /*可选。打印右偏移，单位厘米。报表的水平方向上的偏移量，向右为正，向左为负。*/
                            "PrintOffsetY": 0,                /*可选。打印下偏移，单位厘米。 报表的垂直方向上的偏移量，向下为正，向上为负。*/
                            "token": "aa",      /*可选。只要token值在列表中 方可打印*/
                            "taskId": "1234567",     /*可选。多个打印任务同时打印时，根据该id确定返回的是哪个打印任务。 */ 
                            "exportfilename": "",      /*可选。自定义 导出 文件名称 为空 或者 自定义名称 如 test */ 
                            "exportfiletype": "",      /*可选。自定义 导出 文件格式 为空 或者 自定义名称 如 pdf  */ 
            
                            "AField": AField,
                                
                            "AData": AData,
                                
                            "BField": BField,
                                
                            "BData": BData,
                        };		
                
                        //采用同步机制
                        //ajax好像不需要设置{withCredentials:false}，也可以进行跨域访问
                        $.ajax({
                            type: "post",
                            url: url,
                            // xhrFields: {
                            // 	withCredentials: false
                            // },					
                            // crossDomain: false,
                            async: true,
                            data: qs.stringify(param),
                            success: function(data){
                                data = decodeURIComponent(data);
                                console.log(data);
                                if(data == ""){
                                    //标识打印失败
                                    printIsSuccess = false;        
                                    console.log('打印小票报错');                
                                }else{
                                    var obj = JSON.parse(data);
                                    console.log(obj.status + " -- " + (obj.status == "ok") + " -- " + (obj.status == "OK"));
                                    if(obj.status != "ok"){
                                        //标识打印失败
                                        printIsSuccess = false;                    
                                        console.log('打印小票报错');                
                                    }
                                }
                            },
                            error: function(data){
                                //标识打印失败
                                printIsSuccess = false;      
                                console.log('打印小票报错');                
                            }
                        });						
                    },				
                    printOrderDetailLabel(AData) { //打印标签
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
            
                        if(AData == undefined || AData == ""){
                            console.log('订单信息不能为空');
                            return false;
                        }				
            
                        //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
                        let AField = '['
                                +'{"type": "ftString", "name": "queueNumber","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "shoppingWay","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "goodsName","size": 255,"required": false},'						
                                +'{"type": "ftString", "name": "specList","size": 255,"required": false},'
                                +']';
            
                        let param = {
                            "ReportType": "fastreport",     /*报表类型 gridreport fastreport reportmachine 为空 将默认为gridreport  */	
                            "ReportName": "siamLabel.fr3",     /*报表文件名 POS小票 */
                            "ReportVersion": 1,              /*可选。报表版本, 为空则默认1  如果本地报表的版本过低 将从 ReportUrl 地址进行下载更新*/
                            //"ReportUrl": "http://111.67.202.157:9099/report/fastreport/AAAPosTicket.fr3",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
                            "ReportUrl": "",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
                            "Copies": 1,                  /*可选。打印份数，支持指定打印份数。默认1份,如果为零,不打印,只返回报表生成的pdf,jpg等文件*/
                            // "PrinterName": printerName,      /*可选。指定打印机，为空的话 使用默认打印机, 请在 控制面板 -> 设备和打印机 中查看您的打印机的名称 */
                            "PrintOffsetX": 0,                 /*可选。打印右偏移，单位厘米。报表的水平方向上的偏移量，向右为正，向左为负。*/
                            "PrintOffsetY": 0,                /*可选。打印下偏移，单位厘米。 报表的垂直方向上的偏移量，向下为正，向上为负。*/
                            "token": "aa",      /*可选。只要token值在列表中 方可打印*/
                            "taskId": "1234567",     /*可选。多个打印任务同时打印时，根据该id确定返回的是哪个打印任务。 */ 
                            "exportfilename": "",      /*可选。自定义 导出 文件名称 为空 或者 自定义名称 如 test */ 
                            "exportfiletype": "",      /*可选。自定义 导出 文件格式 为空 或者 自定义名称 如 pdf  */ 
            
                            "AField": AField,
                                
                            "AData": AData
                        };		
                        
                        //采用同步机制
                        //ajax好像不需要设置{withCredentials:false}，也可以进行跨域访问
                        $.ajax({
                            type: "post",
                            url: url,
                            async: true,
                            data: qs.stringify(param),
                            success: function(data){
                                data = decodeURIComponent(data);
                                console.log(data);
                                if(data == ""){
                                    //标识打印失败
                                    printIsSuccess = false;                   
                                    console.log('打印标签报错');                          
                                }else{
                                    var obj = JSON.parse(data);
                                    if(obj.status != "ok"){
                                        //标识打印失败
                                        printIsSuccess = false;     
                                        console.log('打印标签报错');                          
                                    }
                                }
                            },
                            error: function(data){
                                //标识打印失败
                                printIsSuccess = false;     
                                console.log('打印标签报错');                                 
                            }
                        });			
                    }                                    
                }
            }
        }
    })
}

export default {
    install
}