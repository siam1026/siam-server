/**
 * 订单信息公共打印方法(此方法暂时废弃)
 */
const qs = require('qs');

import $ from 'jquery'

const install = function(Vue) {
    Object.defineProperties(Vue.prototype, {
        $print: {
            get() {
                return {
                    //打印
                    printOrderDetail: function (id) {
                        console.log("打印订单");
                        //伪死循环阻塞主线程3秒
                        // for(let t = Date.now(); Date.now() - t <= 20000;);

                        //数据行
                        let AData = '';

                        //数据行
                        let BData = '';
			
                        //获取订单信息
                        let vue = this
                        vue.$http.post(vue, '/rest/admin/order/selectById', {"id" : id},
                            (vue, data) => {
                                let order = data.data;
                                //数据行
                                AData = '['+
                                    '{'+
                                        '"title": '+ order.shopName +', '+
                                        '"subTitle": "电话：4000-100-100", '+
                                        '"shoppingWay": "配送方式：'+ (order.shoppingWay == 1 ? '自取' : '配送') +'", '+
                                        '"nickname": "'+ order.contactRealname +'('+ (order.contactSex == 0 ? '无' : (order.contactSex == 1 ? '先生' : '女士')) +')", '+
                                        '"mobile": '+ order.contactPhone +', '+
                                    '}'+
                                ']';		
                                
                                
                                //获取订单明细信息
                                vue.$http.post(vue, '/rest/admin/orderDetail/list', {"pageNo" : -1, "pageSize" : 20, "orderId" : id},
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
                                        this.printOrderDetailTicket(AData, BData);

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
                                                        '"queueNumber": "23", '+
                                                        '"shoppingWay": '+ (orderDetail.shoppingWay == 1 ? '自取' : '配送') +', '+
                                                        '"goodsName": '+ orderDetail.goodsName +', '+
                                                        '"specList": "('+ specStr +')", '+
                                                    '}'+
                                            ']';

                                            //按照购买数量循环进行打印
                                            for(let j = 0; j < orderDetail.number; j++){
                                                this.printOrderDetailLabel(AData_label);								
                                            }
                                        }
                                    },(error, data)=> {
                                        vue.$message({
                                            showClose: true,
                                            message: data.message,
                                            type: 'error'
                                        });
                                    }
                                )								
                                
                            },(error, data)=> {
                                vue.$message({
                                    showClose: true,
                                    message: data.message,
                                    type: 'error'
                                });
                            }
                        )		                        
                    },  
                    printOrderDetailTicket(AData, BData) { //打印小票
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
        
                        if(AData == undefined || AData == "" || BData == undefined || BData == ""){
                            vue.$message({
                                showClose: true,
                                message: '请输入订单信息',
                                type: 'error'
                            });
                            return false;
                        }
        
                        //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
                        let AField = '['
                                +'{"type": "ftString", "name": "title","size": 255,"required": true},'
                                +'{"type": "ftString", "name": "subTitle","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "shoppingWay","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "nickname","size": 255,"required": false},'
                                +'{"type": "ftString", "name": "mobile","size": 255,"required": false},'
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
                                    vue.$message({
                                        showClose: true,
                                        message: '连接HttpPrinter失败，请重新启动程序',
                                        type: 'error'
                                    });
                                }else{
                                    var obj = JSON.parse(data);
                                    console.log(obj.status + " -- " + (obj.status == "ok") + " -- " + (obj.status == "OK"));
                                    if(obj.status != "ok"){
                                        vue.$message({
                                            showClose: true,
                                            message: "打印失败，请联系管理员",
                                            type: 'error'
                                        });								
                                    }
                                }
                            },
                            error: function(data){
                                vue.$message({
                                    showClose: true,
                                    message: '连接HttpPrinter失败，请重新启动程序',
                                    type: 'error'
                                });
                            }
                        });						
                    },				
                    printOrderDetailLabel(AData) { //打印标签
                        let vue = this
                        // let printerName = "Microsoft Print to PDF";
                        let url = "http://127.0.0.1:12345/printreport";
        
                        if(AData == undefined || AData == ""){
                            vue.$message({
                                showClose: true,
                                message: '请输入订单信息',
                                type: 'error'
                            });
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
                                    vue.$message({
                                        showClose: true,
                                        message: '连接HttpPrinter失败，请重新启动程序',
                                        type: 'error'
                                    });
                                }else{
                                    var obj = JSON.parse(data);
                                    if(obj.status != "ok"){
                                        vue.$message({
                                            showClose: true,
                                            message: "打印失败，请联系管理员",
                                            type: 'error'
                                        });								
                                    }
                                }
                            },
                            error: function(data){
                                vue.$message({
                                    showClose: true,
                                    message: '连接HttpPrinter失败，请重新启动程序',
                                    type: 'error'
                                });
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