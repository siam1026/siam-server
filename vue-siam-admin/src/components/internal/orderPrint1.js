/**
 * 订单自动打印定时器
 */
const qs = require('qs');

import $ from 'jquery'

import $http from '../../utils/http'

var that = '';

//默认标识打印成功
var printIsSuccess = true;

const install = function(Vue) {
    Object.defineProperties(Vue.prototype, {
        $orderPrint: {
            get() {
                return {
                    //初始化订单打印事件
                    //PS: 在这个init()方法中如果程序被return，那么在当前页面下就不会再请求自动打印功能了，除非刷新页面；
                    init: function () {
                        //登录用户为admin管理员才能开启订单自动打印功能
                        let user = JSON.parse(sessionStorage.getItem('user'));
                        if(user==undefined || user.username!="admin"){
                            return false;
                        }

                        //检测HttpPrinter.exe程序是否启动
                        let isStartHttpPrinter = true;
                        let url = "http://127.0.0.1:12345/printreport";
                        $.ajax({
                            type: "post",
                            url: url,
                            async: false,
                            data: {},
                            success: function(data){
                                //HttpPrinter.exe程序已经启动
                            },
                            error: function(data){
                                //HttpPrinter.exe程序未启动
                                alert('HttpPrinter.exe程序未启动');
                                isStartHttpPrinter = false;
                            }
                        });	     
                        if(!isStartHttpPrinter){
                            return false;
                        }                                           

                        // alert("pre -> " + sessionStorage.getItem("printTimer"));

                        //如果sessionStorage中有printTimer属性，则将其进行删除(这样做的目的是因为定时器在浏览器刷新后就会失效，所以我需要在每个页面中都注册订单自动打印事件)
                        if(sessionStorage.getItem("printTimer") != undefined){
                            sessionStorage.removeItem("printTimer")
                        }

                        // alert("after -> " + sessionStorage.getItem("printTimer"));

                        //每隔一分钟轮询一次(时间间隔久一点也是为了保证当前定时器是在上一次定时器执行后才进来的)
                        //改成12秒轮询一次
                        let timer = setInterval(this.autoPrintOrderInfo, 18*1000);

                        //将定时器id存入sessionStorage
                        sessionStorage.setItem("printTimer", timer);
                        // this.printOrderDetail(); 

                        that = this;             
                    },
                    speckText(str) {
                        // 你有新的暹罗点餐订单，请注意查收！
                        // var url= "http://tts.baidu.com/text2audio?lan=zh&ie=UTF-8&spd=6&text=" + encodeURI(str); 
                        // var url = "order_tip.mp3";
                        var url = "https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/file/order_tip.mp3";
                        var audio = new Audio(url);
                        audio.play();
                    },
                    autoPrintOrderInfo() { // 获取订单列表      
                        //如果浏览器记忆当前还有订单定时器打印正在进行，则不执行打印操作(此处不能用localStorage，因为在一旦程序中途退出会导致printTimerIsRunning值永远为true，这样一来就永远不会进行打印操作)
                        if(sessionStorage.getItem("printTimerIsRunning") == true){
                            return false;
                        }
                        //标识当前有订单定时器正在进行打印操作
                        sessionStorage.setItem("printTimerIsRunning", true);
                            
                        let vue = this
                        //查询所有已付款未打印的订单
                        $http.post(vue, '/rest/admin/order/waitPrintOrderList', {},
                            (vue, data) => {
                                let orderList = data.data;
                                //进行打印操作
                                if(orderList!=undefined && orderList.length>0){
                                    for(let i = 0; i < orderList.length; i++){
                                        that.speckText();
                                        that.printOrderDetail(orderList[i].id);
                                    }
                                }
                                //标识当前订单定时器的打印操作已完成
                                sessionStorage.setItem("printTimerIsRunning", false);                                
                            },(error, data)=> {
                                alert(data.message);
                                // vue.$message({
                                //     showClose: true,
                                //     message: data.message,
                                //     type: 'error'
                                // });
                                //标识当前订单定时器的打印操作已完成
                                sessionStorage.setItem("printTimerIsRunning", false);                                         
                            }
                        )        
                    },
                    batchUpdateIsPrintedTrue(idList) { // 获取订单列表
                        // console.log(JSON.stringify(idList));
                        let vue = this
                        $http.post(vue, '/rest/admin/order/batchUpdateIsPrintedTrue', {"idListStr" : JSON.stringify(idList)},
                            (vue, data) => {
                                //修改成功
                            },(error, data)=> {
                                alert(data.message);
                                // vue.$message({
                                //     showClose: true,
                                //     message: data.message,
                                //     type: 'error'
                                // });
                            }
                        )        
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
                        let vue = this
                        $http.post(vue, '/rest/admin/order/selectById', {"id" : id},
                            (vue, data) => {
                                let order = data.data;
                                //数据行
                                AData = '['+
                                    '{'+
                                        '"title": '+ order.shopName +', '+
                                        '"shoppingWay": "配送方式：'+ (order.shoppingWay == 1 ? '自取' : '配送') +'", '+
                                        '"nickname": "'+ order.contactRealname + (order.contactSex == 0 ? '' : (order.contactSex == 1 ? '(先生)' : '(女士)')) +'", '+
                                        '"mobile": '+ order.contactPhone +', '+
                                        '"logo": '+ 'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/bussiness/logo.jpg' +', '+
                                        '"actualPrice": '+ order.actualPrice +', '+
                                        '"contactStreet": '+ order.contactStreet +', '+
                                        '"contactStreet": " '+ (order.shoppingWay == 1 ? '' : ('配送地址：'+order.contactStreet)) +'", '+
                                        '"queueNumber": '+ order.queueNo +', '+
                                    '}'+
                                ']';		
                                
                                
                                //获取订单明细信息
                                $http.post(vue, '/rest/admin/orderDetail/list', {"pageNo" : -1, "pageSize" : 20, "orderId" : id},
                                    (vue, data) => {
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
                                            str += '{"goodsName": '+ orderDetail.goodsName +', "specList": "('+ specStr +')", "number": "'+ orderDetail.number +'"},';
                                        }
                                        //数据行
                                        BData = '['
                                                + str
                                                +']';						
                                        //打印小票
                                        that.printOrderDetailTicket(AData, BData);
            
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
                                            str += '{"goodsName": '+ orderDetail.goodsName +', "specList": "('+ specStr +')", "number": "'+ orderDetail.number +'"},';
            
                                            //数据行
                                            let AData_label = '['+
                                                    '{'+
                                                        '"queueNumber": '+ order.queueNo +', '+
                                                        '"shoppingWay": '+ (order.shoppingWay == 1 ? '自取' : '配送') +', '+
                                                        '"goodsName": '+ orderDetail.goodsName +', '+
                                                        '"specList": "('+ specStr +')", '+
                                                    '}'+
                                            ']';
            
                                            //按照购买数量循环进行打印
                                            for(let j = 0; j < orderDetail.number; j++){
                                                that.printOrderDetailLabel(AData_label);								
                                            }
                                        }
            
                                        //如果打印成功，则更新该订单的"是否已打印"状态
                                        // console.log("状态"+this.printIsSuccess);
                                        if(printIsSuccess){
                                        let idList = [];
                                        idList.push(id);
                                        //这里其实只更新了一条数据，没有批量更新的场景
                                        that.batchUpdateIsPrintedTrue(idList);
                                        }
            
                                    },(error, data)=> {
                                        alert(data.message);
                                        // vue.$message({
                                        //     showClose: true,
                                        //     message: data.message,
                                        //     type: 'error'
                                        // });
                                    }
                                )								
                                
                            },(error, data)=> {
                                alert(data.message);
                                // vue.$message({
                                //     showClose: true,
                                //     message: data.message,
                                //     type: 'error'
                                // });
                            }
                        )		                        
                    },  
                    printOrderDetailTicket(AData, BData) { //打印小票
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
            
                        if(AData == undefined || AData == "" || BData == undefined || BData == ""){
                            alert('请输入订单信息');
                            // vue.$message({
                            //     showClose: true,
                            //     message: '请输入订单信息',
                            //     type: 'error'
                            // });
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
                        +']';
            
                        //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
                        let BField = '['  
                                +'{"type": "ftString", "name": "goodsName","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "specList","size": 255,"required": false},'						
                                +'{"type": "ftString", "name": "number","size": 255,"required": false},'
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
                            async: false,
                            data: qs.stringify(param),
                            success: function(data){
                                data = decodeURIComponent(data);
                                console.log(data);
                                if(data == ""){
                                    //标识打印失败
                                    printIsSuccess = false;        
                                    alert('连接HttpPrinter失败，请重新启动程序');                    
                                    // vue.$message({
                                    //     showClose: true,
                                    //     message: '连接HttpPrinter失败，请重新启动程序',
                                    //     type: 'error'
                                    // });         
                                }else{
                                    var obj = JSON.parse(data);
                                    console.log(obj.status + " -- " + (obj.status == "ok") + " -- " + (obj.status == "OK"));
                                    if(obj.status != "ok"){
                                        //标识打印失败
                                        printIsSuccess = false;                    
                                        alert('打印失败，请联系管理员');          
                                        // vue.$message({
                                        //     showClose: true,
                                        //     message: "打印失败，请联系管理员",
                                        //     type: 'error'
                                        // });							                                 	
                                    }
                                }
                            },
                            error: function(data){
                                //标识打印失败
                                printIsSuccess = false;      
                                alert('连接HttpPrinter失败，请重新启动程序');   
                                // vue.$message({
                                //     showClose: true,
                                //     message: '连接HttpPrinter失败，请重新启动程序',
                                //     type: 'error'
                                // });           
                            }
                        });						
                    },				
                    printOrderDetailLabel(AData) { //打印标签
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
            
                        if(AData == undefined || AData == ""){
                            alert('请输入订单信息');   
                            // vue.$message({
                            //     showClose: true,
                            //     message: '请输入订单信息',
                            //     type: 'error'
                            // });
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
                            async: false,
                            data: qs.stringify(param),
                            success: function(data){
                                data = decodeURIComponent(data);
                                console.log(data);
                                if(data == ""){
                                    //标识打印失败
                                    printIsSuccess = false;                   
                                    alert('连接HttpPrinter失败，请重新启动程序');        
                                    // vue.$message({
                                    //     showClose: true,
                                    //     message: '连接HttpPrinter失败，请重新启动程序',
                                    //     type: 'error'
                                    // });                           
                                }else{
                                    var obj = JSON.parse(data);
                                    if(obj.status != "ok"){
                                        //标识打印失败
                                        printIsSuccess = false;     
                                        alert('打印失败，请联系管理员'); 
                                        // vue.$message({
                                        //     showClose: true,
                                        //     message: "打印失败，请联系管理员",
                                        //     type: 'error'
                                        // });		                                  						
                                    }
                                }
                            },
                            error: function(data){
                                //标识打印失败
                                printIsSuccess = false;     
                                alert('连接HttpPrinter失败，请重新启动程序');
                                // vue.$message({
                                //     showClose: true,
                                //     message: '连接HttpPrinter失败，请重新启动程序',
                                //     type: 'error'
                                // });                      
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