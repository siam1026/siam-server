<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<!-- <el-button type="primary" @click="testPlayAudio">测试声音播报</el-button> -->
					<el-button type="primary" @click="xpYunPrinterOrderDetail">打印</el-button>
					<el-button @click="backHistory">返回</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="relatedList" highlight-current-row v-loading="relatedListLoading" style="width: 100%;"
			:cell-style="cellStyle" :header-cell-style="getRowClass">
			<el-table-column prop="orderNo" label="订单编号" width="200"></el-table-column>
			<el-table-column prop="queueNo" label="取餐号"></el-table-column>
			<el-table-column prop="description" label="订单描述"></el-table-column>
			<el-table-column prop="goodsTotalPrice" label="订单总额">
				<template scope="scope">
					<span>{{ scope.row.goodsTotalPrice + scope.row.packingCharges + scope.row.deliveryFee }}元</span>
				</template>
			</el-table-column>
			<el-table-column prop="paymentMode" label="支付方式">
				<template scope="scope">
					<span v-if="scope.row.paymentMode == 1">微信支付</span>
					<span v-else-if="scope.row.paymentMode == 0">平台余额</span>
				</template>
			</el-table-column>
			<el-table-column prop="actualPrice" label="实付款" :formatter="addUnit"></el-table-column>
			<el-table-column prop="goodsTotalQuantity" label="商品总数量">
				<template scope="scope">
					<span>{{ scope.row.goodsTotalQuantity }}件</span>
				</template>
			</el-table-column>
			<el-table-column prop="goodsTotalPrice" label="商品总金额" :formatter="addUnit"></el-table-column>
			<el-table-column prop="packingCharges" label="包装费" :formatter="addUnit"></el-table-column>
			<el-table-column prop="deliveryFee" label="配送费" :formatter="addUnit"></el-table-column>
			<el-table-column prop="fullReductionRuleDescription" label="使用的满减规则描述" width="190"></el-table-column>
			<el-table-column prop="couponsDescription" label="使用的优惠卷描述" width="190"></el-table-column>
			<el-table-column prop="remark" label="备注"></el-table-column>
			<el-table-column prop="shoppingWay" label="购物方式" :formatter="formatShoppingWay"></el-table-column>
			<el-table-column prop="contactRealname" label="联系人姓名" width="120"></el-table-column>
			<el-table-column prop="contactPhone" label="联系电话" width="120"></el-table-column>
			<el-table-column prop="contactProvince" label="派送地址" width="190">
				<template slot-scope="scope">
					<span v-if="scope.row.shoppingWay == 1"> - </span>
					<span v-if="scope.row.shoppingWay == 2">{{ scope.row.contactProvince }} - {{ scope.row.contactCity
						}} -
						{{ scope.row.contactArea }} - {{ scope.row.contactStreet }} -
						{{ scope.row.contactHouseNumber }}</span>
				</template>
			</el-table-column>
			<el-table-column prop="status" label="订单状态" width="100" :formatter="formatOrder"></el-table-column>
			<el-table-column prop="createTime" label="下单时间" :formatter="formatTime" width="190"></el-table-column>
		</el-table>

		<br /><br />

		<!--列表-->
		<el-table :data="list" highlight-current-row v-loading="listLoading" style="width: 100%;"
			:cell-style="cellStyle" :header-cell-style="headerCellStyle">
			<!-- <el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>		
			</el-table-column> -->
			<el-table-column label="商品主图" prop="mainImage">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
				</template>
			</el-table-column>
			<el-table-column prop="goodsName" label="商品名称"></el-table-column>
			<el-table-column prop="specList" label="商品规格" :formatter="formatSpecList"></el-table-column>
			<el-table-column prop="price" label="价格" :formatter="addUnit"></el-table-column>
			<el-table-column prop="number" label="购买数量" :formatter="formatNumber"></el-table-column>
			<el-table-column prop="subtotal" label="小计" :formatter="addUnit"></el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
				:page-sizes="[10, 20, 50, 100]" :page-size="searchMsg.pageSize"
				layout="total, sizes, prev, pager, next, jumper" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="金额(元)" prop="amount">
					<el-input v-model="editForm.amount"></el-input>
				</el-form-item>
				<el-form-item label="备注" prop="remarks">
					<el-input type="textarea" v-model="editForm.remarks" :rows="5"></el-input>
				</el-form-item>
				<el-form-item label="产生时间" prop="createTime">
					<el-date-picker v-model="editForm.createTime" value-format="timestamp" format="yyyy/MM/dd hh:mm:ss"
						type="datetime" placeholder="选择产生时间">
					</el-date-picker>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
import axios from 'axios'
const qs = require('qs');

import $ from 'jquery'

//全局音频变量
var url = "https://siam.oss-cn-hangzhou.aliyuncs.com/data/file/order_tip.mp3";
var audio = new Audio(url);

var count = 1;

export default {
	data() {
		return {
			fundList: [],
			searchMsg: {
				pageNo: 1,
				pageSize: 20
			},

			relatedList: [],
			relatedListLoading: false,

			list: [],
			total: 0,
			listLoading: false,
			sels: [],//列表选中列

			editFormVisible: false,//编辑界面是否显示
			editLoading: false,
			editFormRules: {
				name: [{ required: true, message: '请选择名称', trigger: 'change' }],
				amount: [{ required: true, message: '请输入买入金额', trigger: 'change' }],
				// remarks: [{ required: true, message: '请输入备注', trigger: 'blur' }],
				createTime: [{ required: true, message: '请输入产生时间', trigger: 'blur' }]
			},
			//编辑界面数据
			editForm: {
				name: '',
				amount: '',
				remarks: '',
				createTime: ''
			}
		}
	},
	methods: {
		cellStyle({ row, column, rowIndex, columnIndex }) {
			return "text-align:center";
		},
		headerCellStyle({ row, rowIndex }) {
			return "text-align:center";
		},
		getRowClass({ row, column, rowIndex, columnIndex }) { //设置表格第一行的颜色
			if (rowIndex == 0) {
				return "text-align:center;background:#F5F7FA";
			} else {
				return "";
			}
		},
		closeDialog() {
			this.$refs['editForm'].resetFields();
		},
		formatNumber(row, column) { // 添加单位
			return (row[column.property] || 0) + '件'
		},
		formatSpecList(row, column) { // 添加单位
			let specStr = '';
			let specList = JSON.parse(row.specList);
			// specList.forEach(function(value, index){
			// 	console.log(value + "  " + specList[value]);
			// })
			// console.log(specList);


			// for(var key in specList){
			// 	//console.log(key + "-" + specList[key]);
			// 	if(specStr == ''){
			// 		specStr += specList[key];
					
			// 	}else{
			// 		specStr += '/' + specList[key];
			// 	}
			// }
			// return specStr;

			return row.specList
		},
		addUnit(row, column) { // 添加单位
			return (row[column.property] || 0) + '元'
		},
		formatShoppingWay(row, column) { // 添加单位
			return row.shoppingWay == 1 ? '自取' : row.shoppingWay == 2 ? '配送' : '-';
		},
		formatDescription(row, column) { // 添加单位
			//let replaceReg = new RegExp("&nbsp;", 'g');
			//return row.description.replace(/1/g, " ");
			if (row.description != undefined) {
				return row.description.replace(/&nbsp;/g, " ")
			} else {
				return "-";
			}
		},
		formatTime(row, column) {
			let date = new Date(row[column.property]);
			return this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm');
		},
		formatOrder(row, column) { // 0-待配送 1-待收货 2已完成 3-已取消 4-申诉中 5-已退款 6-申诉已完成
			let status = row[column.property]
			switch (status) {
				case 1:
					return '未付款'
					break;
				case 2:
					return '待处理'
					break;
				case 3:
					return '待自取'
					break;
				case 4:
					return '待配送'
					break;
				case 5:
					return '已配送'
					break;
				case 6:
					return '已完成'
					break;
				case 7:
					return '售后处理中'
					break;
				case 8:
					return '已退款'
					break;
				case 9:
					return '售后处理完成'
					break;
				case 10:
					return '已取消(未支付)'
					break;
				case 11:
					return '已取消(已支付)'
					break;
			}
		},
		changeDecimal(val, scale) { // 小数点后保留2位小数，不足自动补0
			// let vue = this;
			// val = vue.$utils.changeDecimal(val, scale);
			return val;
		},
		gotoOtherPage(type, row) {
			if (type === 'detail') {
				this.$router.push({ path: 'editGoods', query: { id: row.id } })
			}
		},
		backHistory() {
			this.$router.go(-1);//返回上一层
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
		testPlayAudio() {
			if (count % 2 == 1) {
				this.speckTextChangeToDelivery();
			} else {
				this.speckText();
			}
			count++;
		},
		xpYunPrinterOrderDetail() {
			//获取选中的订单id
			let id = this.$route.query.id
			if (!id) {
				alert('订单错误');
				return
			}
			//获取订单信息
			let vue = this;
			this.$confirm('是否打印当前订单结账单?', '提示', {
				confirmButtonText: '确定',
				cancelButtonText: '取消',
				type: 'warning'
			}).then(() => {
				const loading = this.$loading({
					lock: true,
					text: '订单结账单打印中，请稍等...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				vue.$http.post(vue, '/rest/merchant/order/xpYunPrinterOrderDetail', { "pageNo": -1, "pageSize": 20, "id": id },
					(vue, data) => {
						console.log(data)
						loading.close();
						this.$message({
							type: data.success ? 'success' : 'error',
							message: data.message
						});
					}, (error, data) => {
						loading.close();
						this.$message({
							type: data.success ? 'success' : 'error',
							message: data.message
						});
					});

			}).catch(() => {
				this.$message({
					type: 'info',
					message: '已取消打印'
				});
			});

		},
		printOrderDetail() {
			//伪死循环阻塞主线程3秒
			// for(let t = Date.now(); Date.now() - t <= 20000;);

			//检测HttpPrinter.exe程序是否启动
			let isStartHttpPrinter = true;
			let url = "http://127.0.0.1:12345/printreport";
			$.ajax({
				type: "post",
				url: url,
				async: false,
				data: {},
				success: function (data) {
					//HttpPrinter.exe程序已经启动
				},
				error: function (data) {
					//HttpPrinter.exe程序未启动
					alert('HttpPrinter.exe程序未启动');
					isStartHttpPrinter = false;
				}
			});
			if (!isStartHttpPrinter) {
				return false;
			}

			//数据行
			let AData = '';

			//数据行
			let BData = '';

			//获取选中的订单id
			let id = this.$route.query.id
			//获取订单信息
			let vue = this
			vue.$http.post(vue, '/rest/merchant/order/selectById', { "id": id },
				(vue, data) => {
					let order = data.data;

					let shoppingWayText = order.shoppingWay == 1 ? '自取' : '配送';
					if (order.isChangeToDelivery) {
						shoppingWayText = '由自取订单改为配送';
					}

					let address = order.contactProvince + order.contactCity + order.contactArea + order.contactStreet + order.contactHouseNumber;

					//数据行
					AData = '[' +
						'{' +
						'"title": "' + order.shopName + '", ' +
						'"shoppingWay": "配送方式：' + shoppingWayText + '", ' +
						'"nickname": "' + order.contactRealname + (order.contactSex == 0 ? '' : (order.contactSex == 1 ? '(先生)' : '(女士)')) + '", ' +
						'"mobile": "' + order.contactPhone + '", ' +
						'"logo": "' + 'https://siam.oss-cn-hangzhou.aliyuncs.com/data/images/business/logo.jpg' + '", ' +
						'"actualPrice": "' + order.actualPrice + '", ' +
						'"contactStreet": "' + address + '", ' +
						'"contactStreet": " ' + (order.shoppingWay == 1 ? '' : ('配送地址：' + address)) + '", ' +
						'"queueNumber": "' + order.queueNo + '", ' +
						'"packingCharges": "' + order.packingCharges + '", ' +
						'"deliveryFee": "' + order.deliveryFee + '", ' +
						'"fullReductionRuleDescription": "' + ((order.fullReductionRuleDescription == '' || order.fullReductionRuleDescription == null) ? '无' : (order.fullReductionRuleDescription)) + '", ' +
						'"couponsDescription": "' + ((order.couponsDescription == '' || order.couponsDescription == null) ? '无' : (order.couponsDescription)) + '", ' +
						'"remark": "' + ((order.remark == '' || order.remark == null) ? '无' : (order.remark)) + '", ' +
						'}' +
						']';


					//获取订单明细信息
					vue.$http.post(vue, '/rest/merchant/orderDetail/list', { "pageNo": -1, "pageSize": 20, "orderId": id },
						(vue, data) => {
							let orderDetailList = data.data.records
							//累加商品信息
							let str = '';
							for (let i = 0; i < orderDetailList.length; i++) {
								let orderDetail = orderDetailList[i];
								let specStr = '';
								let specList = JSON.parse(orderDetail.specList);
								for (var key in specList) {
									if (specStr == '') {
										specStr += specList[key];
									} else {
										specStr += '/' + specList[key];
									}
								}
								str += '{"goodsName": "' + orderDetail.goodsName + '", "specList": "(' + specStr + ')", "number": "' + orderDetail.number + '", "subtotal": "' + orderDetail.subtotal + '"},';
							}
							//数据行
							BData = '['
								+ str
								+ ']';
							//打印小票
							this.printOrderDetailTicket(AData, BData);

							//打印小票后，接着打印标签
							for (let i = 0; i < orderDetailList.length; i++) {
								let orderDetail = orderDetailList[i];
								let specStr = '';
								let specList = JSON.parse(orderDetail.specList);
								for (var key in specList) {
									if (specStr == '') {
										specStr += specList[key];
									} else {
										specStr += '/' + specList[key];
									}
								}
								str += '{"goodsName": ' + orderDetail.goodsName + ', "specList": "(' + specStr + ')", "number": "' + orderDetail.number + '"},';

								//数据行
								let AData_label = '[' +
									'{' +
									'"queueNumber": "' + order.queueNo + '", ' +
									'"shoppingWay": "' + (order.shoppingWay == 1 ? '自取' : '配送') + '", ' +
									'"goodsName": "' + orderDetail.goodsName + '", ' +
									'"specList": "(' + specStr + ')", ' +
									'}' +
									']';

								//按照购买数量循环进行打印
								for (let j = 0; j < orderDetail.number; j++) {
									this.printOrderDetailLabel(AData_label);
								}
							}
						}, (error, data) => {
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'error'
							});
						}
					)

				}, (error, data) => {
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

			if (AData == undefined || AData == "" || BData == undefined || BData == "") {
				vue.$message({
					showClose: true,
					message: '请输入订单信息',
					type: 'error'
				});
				return false;
			}

			//字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
			let AField = '['
				+ '{"type": "ftString", "name": "title","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "shoppingWay","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "nickname","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "mobile","size": 255,"required": false},'
				+ '{"type": "ftBlob", "name": "logo","size": 0,"required": false},'
				+ '{"type": "ftString", "name": "actualPrice","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "contactStreet","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "queueNumber","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "packingCharges","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "deliveryFee","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "fullReductionRuleDescription","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "couponsDescription","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "remark","size": 255,"required": true},'
				+ ']';

			// //数据行
			// let AData = '['+
			// 		'{'+
			// 			'"title": "辰泰科技园区店", '+
			// 			'"subTitle": "电话：4000-100-100", '+
			// 			'"shoppingWay": "配送方式：自提", '+
			// 			'"nickname": "李玉峰(先生)", '+
			// 			'"mobile": "187****9426", '+
			// 			// '"starttime": "消费日期:2019-11-01", '+
			// 			// '"tf": "", '+
			// 			// '"ysje": "100.00", '+
			// 			// '"yhje": "0.00", '+
			// 			// '"sjje": "100.00", '+
			// 			// '"kahao": "890001"'+
			// 		'}'+
			// ']';

			//字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
			let BField = '['
				+ '{"type": "ftString", "name": "goodsName","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "specList","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "number","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "subtotal","size": 255,"required": false},'
				// +'{"type": "ftString", "name": "je","size": 255,"required": false},'
				+ ']';

			// //数据行
			// let BData = '['
			// 		+'{"goodsName": "布丁果冻", "specList": "(常规/热/常规糖/小杯)", "number": "1"},'
			// 		+'{"goodsName": "布丁果冻", "specList": "(常规/多冰/常规糖/小杯)", "number": "1"},'
			// 		+'{"goodsName": "布丁果冻", "specList": "(红豆)", "number": "2"},'
			// 		+'{"goodsName": "法式奶霜绿茶", "specList": "(常规/多冰/常规糖/小杯)", "number": "1"},'
			// 		// +'{"shangpname": "清汤丸子", "shuliang": "1", "je": "20.90"},'
			// 		// +'{"shangpname": "土豆丝", "shuliang": "1", "je": "7.00"},'
			// 		// +'{"shangpname": "凉拌牛肉", "shuliang": "1", "je": "50.00"},'
			// 		// +'{"shangpname": "红烧茄子", "shuliang": "1", "je": "30.00"},'
			// 		// +'{"shangpname": "四喜丸子", "shuliang": "1", "je": "23.00"},'
			// 		// +'{"shangpname": "溜肥肠", "shuliang": "1", "je": "30.00"},'
			// 		// +'{"shangpname": "炒年糕", "shuliang": "1", "je": "33.00"},'
			// 		// +'{"shangpname": "烧鹅掌", "shuliang": "1", "je": "34.00"},'
			// 		// +'{"shangpname": "蚂蚁上树", "shuliang": "1", "je": "65.00"},'
			// 		+']';

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

			// var param = {
			// 	"ReportType": "fastreport",     /*报表类型 gridreport fastreport reportmachine 为空 将默认为gridreport  */	
			// 	"ReportName": "AAAPosTicket.fr3",     /*报表文件名 POS小票 */
			// 	"ReportVersion": 1,              /*可选。报表版本, 为空则默认1  如果本地报表的版本过低 将从 ReportUrl 地址进行下载更新*/
			// 	//"ReportUrl": "http://111.67.202.157:9099/report/fastreport/AAAPosTicket.fr3",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
			// 	"ReportUrl": "",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
			// 	"Copies": 1,                  /*可选。打印份数，支持指定打印份数。默认1份,如果为零,不打印,只返回报表生成的pdf,jpg等文件*/
			// 	"PrinterName": printerName,      /*可选。指定打印机，为空的话 使用默认打印机, 请在 控制面板 -> 设备和打印机 中查看您的打印机的名称 */
			// 	"PrintOffsetX": 0,                 /*可选。打印右偏移，单位厘米。报表的水平方向上的偏移量，向右为正，向左为负。*/
			// 	"PrintOffsetY": 0,                /*可选。打印下偏移，单位厘米。 报表的垂直方向上的偏移量，向下为正，向上为负。*/
			// 	"token": "aa",      /*可选。只要token值在列表中 方可打印*/
			// 	"taskId": "1234567",     /*可选。多个打印任务同时打印时，根据该id确定返回的是哪个打印任务。 */ 
			// 	"exportfilename": "",      /*可选。自定义 导出 文件名称 为空 或者 自定义名称 如 test */ 
			// 	"exportfiletype": "",      /*可选。自定义 导出 文件格式 为空 或者 自定义名称 如 pdf  */ 

			// 	"AField": '['  ///*字段， type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
			// 		+'{"type": "ftString", "name": "title","size": 255,"required": true},'
			// 		+'{"type": "ftString", "name": "subTitle","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "deskname","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "djh","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "czy","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "starttime","size": 255,"required": false},'		
			// 		+'{"type": "ftString", "name": "tf","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "ysje","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "yhje","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "sjje","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "kahao","size": 255,"required": false},'
			// 		+']',

			// 	"AData": '['  ///*数据行  
			// 		+'{"title": "易桥餐厅消费小票", "subTitle": "官方网站:http://www.HttpPrinter.com", "deskname": "桌号:1001", "djh": "单据号:20191101000001", "czy": "操作员:高文杰", "starttime": "消费日期:2019-11-01", "tf": "", "ysje": "100.00", "yhje": "0.00", "sjje": "100.00", "kahao": "890001"},'		
			// 		+']',

			// 	"BField": '['  ///*字段， type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
			// 		+'{"type": "ftString", "name": "shangpname","size": 255,"required": true},'
			// 		+'{"type": "ftString", "name": "shuliang","size": 255,"required": false},'
			// 		+'{"type": "ftString", "name": "je","size": 255,"required": false},'
			// 		+']',

			// 	"BData": '['  ///*数据行  
			// 		+'{"shangpname": "青椒肉丝", "shuliang": "1", "je": "14.50"},'
			// 		+'{"shangpname": "清汤丸子", "shuliang": "1", "je": "20.90"},'
			// 		+'{"shangpname": "土豆丝", "shuliang": "1", "je": "7.00"},'
			// 		+'{"shangpname": "凉拌牛肉", "shuliang": "1", "je": "50.00"},'
			// 		+'{"shangpname": "红烧茄子", "shuliang": "1", "je": "30.00"},'
			// 		+'{"shangpname": "四喜丸子", "shuliang": "1", "je": "23.00"},'
			// 		+'{"shangpname": "溜肥肠", "shuliang": "1", "je": "30.00"},'
			// 		+'{"shangpname": "炒年糕", "shuliang": "1", "je": "33.00"},'
			// 		+'{"shangpname": "烧鹅掌", "shuliang": "1", "je": "34.00"},'
			// 		+'{"shangpname": "蚂蚁上树", "shuliang": "1", "je": "65.00"},'
			// 		+']',
			// };

			// axios.post(url, qs.stringify(param), {withCredentials:false})
			// .then(data => {
			// 		// console.log(data);
			// 		// console.log((data.statusText != "OK") + "  " + (data.statusText == "OK"));
			// 		if(data.statusText != "OK"){
			// 			vue.$message({
			// 				showClose: true,
			// 				message: '打印失败，请联系管理员',
			// 				type: 'error'
			// 			});		
			// 			return false;					
			// 		}
			// 		// 小票打印成功后，打印标签
			// 		// for(let i = 0; i < 5; i++){
			// 		// 	this.printOrderDetailLabel();
			// 		// }
			// 		this.printOrderDetailLabel();

			//         // data = decodeURIComponent(data);
			//         // if(data == ""){
			//         //     alert("连接HttpPrinter失败");
			//         // }else{
			//         //     var obj = JSON.parse(data);
			//         //     if(obj.status=="ok"){
			//         //         alert("打印成功");
			//         //     }else{
			//         //         alert("打印失败：" + obj.data);
			//         //     }
			// 		// }
			// })
			// .catch(function(data) {
			// 	// vue.listLoading = false;
			// 	// vue.$message({
			// 	// 	showClose: true,
			// 	// 	message: data.message,
			// 	// 	type: 'error'
			// 	// });
			// });		



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
				success: function (data) {
					data = decodeURIComponent(data);
					console.log(data);
					if (data == "") {
						vue.$message({
							showClose: true,
							message: '连接HttpPrinter失败，请重新启动程序',
							type: 'error'
						});
					} else {
						var obj = JSON.parse(data);
						console.log(obj.status + " -- " + (obj.status == "ok") + " -- " + (obj.status == "OK"));
						if (obj.status != "ok") {
							vue.$message({
								showClose: true,
								message: "打印失败，请联系管理员",
								type: 'error'
							});
						}
					}
				},
				error: function (data) {
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

			if (AData == undefined || AData == "") {
				vue.$message({
					showClose: true,
					message: '请输入订单信息',
					type: 'error'
				});
				return false;
			}

			//字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
			let AField = '['
				+ '{"type": "ftString", "name": "queueNumber","size": 255,"required": true},'
				+ '{"type": "ftString", "name": "shoppingWay","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "goodsName","size": 255,"required": false},'
				+ '{"type": "ftString", "name": "specList","size": 255,"required": false},'
				+ ']';

			// //数据行
			// let AData = '['+
			// 		'{'+
			// 			'"queueNumber": "23", '+
			// 			'"shoppingWay": "自提", '+
			// 			'"goodsName": "布丁果冻", '+
			// 			'"specList": "(常规/热/常规糖/小杯)", '+
			// 		'}'+
			// ']';

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

			// axios.post(url, qs.stringify(param), {withCredentials:false})
			// .then(data => {
			//         data = decodeURIComponent(data);
			//         if(data == ""){
			//             alert("连接HttpPrinter失败");
			//         }else{
			//             var obj = JSON.parse(data);
			//             if(obj.status=="ok"){
			//                 alert("打印成功");
			//             }else{
			//                 alert("打印失败：" + obj.data);
			//             }
			//         }
			// })
			// .catch(function(data) {
			// 	// vue.listLoading = false;
			// 	// vue.$message({
			// 	// 	showClose: true,
			// 	// 	message: data.message,
			// 	// 	type: 'error'
			// 	// });
			// });		

			//采用同步机制
			//ajax好像不需要设置{withCredentials:false}，也可以进行跨域访问
			$.ajax({
				type: "post",
				url: url,
				async: false,
				data: qs.stringify(param),
				success: function (data) {
					data = decodeURIComponent(data);
					console.log(data);
					if (data == "") {
						vue.$message({
							showClose: true,
							message: '连接HttpPrinter失败，请重新启动程序',
							type: 'error'
						});
					} else {
						var obj = JSON.parse(data);
						if (obj.status != "ok") {
							vue.$message({
								showClose: true,
								message: "打印失败，请联系管理员",
								type: 'error'
							});
						}
					}
				},
				error: function (data) {
					vue.$message({
						showClose: true,
						message: '连接HttpPrinter失败，请重新启动程序',
						type: 'error'
					});
				}
			});
		},
		handleCurrentChange(val) {
			this.searchMsg.pageNo = val;
			this.getList(this.$route.query.id);
		},
		getRelatedList(id) { // 获取商品列表
			let vue = this
			let param = {};
			param.id = id;

			vue.relatedListLoading = true;
			vue.$http.post(vue, '/rest/merchant/order/list', param,
				(vue, data) => {
					vue.relatedList = data.data.records
					vue.relatedListLoading = false;
				}, (error, data) => {
					vue.relatedListLoading = false;
					vue.$message({
						showClose: true,
						message: data.message,
						type: 'error'
					});
				}
			)
		},
		getList(id) { // 获取商品列表
			let vue = this
			let param = Object.assign(vue.searchMsg);
			param.orderId = id;

			//去掉id，上一步查询关联列表会影响这里
			delete param.id

			vue.listLoading = true;
			vue.$http.post(vue, '/rest/merchant/orderDetail/list', param,
				(vue, data) => {
					vue.list = data.data.records
					vue.total = data.data.total
					vue.listLoading = false;
				}, (error, data) => {
					// alert(data);
					vue.listLoading = false;
					vue.$message({
						showClose: true,
						message: data.message,
						type: 'error'
					});
				}
			)
		},
		handleDel(id) { // 删除
			this.$confirm('确认删除该记录吗?', '提示', {
				type: 'warning'
			}).then(() => {
				// this.listLoading = true;
				let vue = this;

				vue.$http.delete(vue, '/rest/merchant/orderDetail/delete', { "ids": [id] },
					function (vue, data) {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
						});
						vue.getList(this.$route.query.id)
					}, function (error, data) {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			}).catch(() => { });
		},
		handleEdit: function (index, row) { // 显示编辑界面
			this.editFormVisible = true;
			if (row) {
				this.editForm = {
					id: row.id,
					name: row.name,
					amount: row.amount,
					remarks: row.remarks,
					createTime: new Date(row.createTime).getTime()
				}
			} else {
				this.editForm = {
					name: '',
					amount: '',
					remarks: '',
					createTime: ''
				}
			}
		},
		editSubmit: function () { // 编辑
			this.$refs.editForm.validate((valid) => {
				if (valid) {
					// this.editLoading = true;
					let vue = this
					let param = Object.assign({}, this.editForm);

					//处理下时间格式(本来传递一个时间戳就可以了的，但是后台配置有问题，所以先用字符串传参)
					let createTime = new Date(param.createTime);
					let str = this.$utils.formatDate(createTime, 'yyyy/MM/dd hh:mm:ss');
					param.createTime = str;

					let url = '';
					if (param.id) {
						url = '/rest/merchant/orderDetail/update';
					} else {
						url = '/rest/merchant/orderDetail/insert';
						param.orderId = this.$route.query.id;
					}
					vue.$http.post(vue, url, param,
						(vue, data) => {
							// this.editLoading = false;
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'success'
							});
							vue.getRelatedList(this.$route.query.id)
							vue.getList(this.$route.query.id)
							vue.editFormVisible = false;
						}, (error, data) => {
							vue.editFormVisible = false;
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'error'
							});
						}
					)
				}
			});
		}
	},
	created() {
		// this.getTypeList()
		let id = this.$route.query.id
		this.getRelatedList(id)
		this.getList(id)
		//开启订单自动打印定时器
		this.$orderPrint.init();
	}
}

</script>

<style scoped></style>