<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-input v-model="searchMsg.name" clearable placeholder="请输入门店名称"></el-input>
				</el-form-item>
				<el-form-item label="">
					<el-date-picker
					v-model="searchMsg.createTime"
					type="daterange"
					align="right"
					unlink-panels
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					:picker-options="pickerOptions">
					</el-date-picker>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<!--列表-->
		<el-table :data="list" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
			<!-- <el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>
			</el-table-column> -->
			<el-table-column prop="shopLogoImg" label="门店头像">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.shopLogoImg)" width="50" height="50">
				</template>				
			</el-table-column>			
			<el-table-column prop="shopName" label="店铺名称"></el-table-column>
			<!-- <el-table-column prop="realName" label="店铺负责人"></el-table-column> -->
			<el-table-column prop="withdrawAmount" label="提现金额" :formatter="addUnitFixedTwo"></el-table-column>
			<el-table-column prop="actualAmount" label="实际到账金额" :formatter="addUnitFixedTwo"></el-table-column>			
			<el-table-column prop="platformFee" label="平台服务费" :formatter="addUnitFixedTwo"></el-table-column>
			<el-table-column prop="auditStatus" label="状态">
				<template slot-scope="scope">
					<span v-if="scope.row.auditStatus==1">平台处理中</span>
					<span v-else-if="scope.row.auditStatus==2">到账成功</span>
					<span v-else-if="scope.row.auditStatus==3">审核不通过</span>			
				</template>
			</el-table-column>
			<el-table-column prop="auditReason" label="审核不通过原因"></el-table-column>
			<el-table-column prop="createTime" label="提交时间" :formatter="formatTime"></el-table-column>
			<el-table-column prop="auditTime" label="处理时间" :formatter="formatTime"></el-table-column>
			<el-table-column label="操作" fixed="right" width="230">
				<template slot-scope="scope">
					<el-button v-if="scope.row.auditStatus == 1" size="small" type="primary" @click="handlePay(scope.row.id)">同意并打款</el-button>					
					<el-button v-if="scope.row.auditStatus == 1" size="small" type="primary" @click="handleCheck(scope.row)">拒绝</el-button>
					<!-- <el-button size="small" @click="handleDetail(scope.$index, scope.row)">详情</el-button> -->
					<!-- <el-button size="small" @click="handleDetail(scope.$index, scope.row)">编辑</el-button> -->
					<!-- <el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button> -->
				</template>
				<!-- scope.$index, scope.row -->
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:page-sizes="[10, 20, 50, 100]"
				:page-size="searchMsg.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="total"
				style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="详情" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="支付平台名称" prop="paymentPlatform">
					<el-input v-model="editForm.paymentPlatform"></el-input>
				</el-form-item>				
				<el-form-item label="支付平台流水号" prop="paymentTradeNo">
					<el-input v-model="editForm.paymentTradeNo"></el-input>
				</el-form-item>					
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

        <!--处理dialog-->
        <el-dialog title="处理" :visible.sync="checkFormVisible" :close-on-click-modal="false">
            <el-form :model="checkEdit" label-width="150px" ref="checkEdit">
                <!-- <el-form-item label="是否通过" prop="status">
                    <el-radio-group v-model="checkEdit.status">
						<el-radio class="radio" :label="1">通过</el-radio>
                        <el-radio class="radio" :label="2">不通过</el-radio>
                    </el-radio-group>
                </el-form-item> -->
                <el-form-item label="审核不通过原因" prop="opinion">
                    <el-input type="textarea" v-model="checkEdit.opinion"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button size="small" @click.native="checkFormVisible = false">取消</el-button>
                <el-button type="primary" size="small" @click="checkEditSubmit">提交</el-button>
            </div>
        </el-dialog>		
	</section>
</template>

<script>
	import cityData from '../../../static/citys.json';
	export default {
		data() {
			return {
				pickerOptions: {
					shortcuts: [{
						text: '最近一周',
							onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近两周',
							onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 14);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一月',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三月',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 3);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近半年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 180);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 12);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近两年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 24);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 36);
							picker.$emit('pick', [start, end]);
						}
					}]
				},				
				searchMsg: {
					pageNo: 1,
					pageSize: 20
				},
				
				list: [],
				total: 0,
				listLoading: false,
				sels: [],//列表选中列

				options:cityData,
				props: {
					value: 'label',
					children: 'children'
				},

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					// isDisabled: [{ required: true, message: '请选择门店状态', trigger: 'change' }],
					name: [{ required: true, message: '请输入门店名称', trigger: 'blur' }],
					// code: [{ required: false, message: '请输入门店编码', trigger: 'blur' }],
					selectedOptions2:[{ type: 'array', required: true, message: '请选择区域', trigger: 'change' }],
					street: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
					isOperating: [{ required: true, message: '请选择店铺是否营业', trigger: 'blur' }],
					startTime: [{ required: true, message: '请输入店铺营业开始时间', trigger: 'blur' }],
					endTime: [{ required: true, message: '请输入店铺营业结束时间', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: {
					// isDisabled: false,
					name: '',
					code: '',
					selectedOptions2: [],
					street: '',
					isOperating: '正常营业',
					startTime: '8:00',
					endTime: '19:30',
				},

				checkFormVisible: false,//检查处理界面是否显示
				checkEdit: {
                    id:'',
                    status:0,
                    opinion:'',
				}	
			}
		},
		methods: {
			cellStyle({row, column, rowIndex, columnIndex}){
				return "text-align:center";
			},
			headerCellStyle({row, rowIndex}){
				return "text-align:center";
			},			
			closeDialog() {
				this.$refs['editForm'].resetFields();
			},
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				let str = this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
				// 处理数据库中时间为NULL场景
				if(row[column.property]==undefined || str=="1970-01-01 08:00:00"){
					str = "-";
				}
        		return str;
			},
			formatType (row, column) { // 0=正常/启用  1=禁用
				return row.isDisabled ? '禁用' : '正常';
			},
			handleSizeChange(val) {
				this.searchMsg.pageSize = val;
				this.getList();
			},			
			handleCurrentChange(val) {
				this.searchMsg.pageNo = val;
				this.getList();
			},
			addUnitFixedTwo(row, column) { // 添加单位
				return Number((row[column.property] || 0)).toFixed(2) + '元'
			},
			getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 获取商品门店列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				param.auditStatus = 1; //查询审核中的记录
				vue.listLoading = true;

				//searchMsg搜索条件中没有省市区，这样写会报错的
				// let [province, city, area] = [...vue.searchMsg.selectedOptions]
				// param.province = province
                // param.city = city
				// param.area = area
				
				//处理开始日期、结束日期
				if(vue.searchMsg.createTime){
					let startDate = vue.searchMsg.createTime[0];
					let endDate = vue.searchMsg.createTime[1];
					param.startCreateTime = this.$utils.formatDate(new Date(startDate), 'yyyy/MM/dd hh:mm:ss');
					param.endCreateTime = this.$utils.formatDate(new Date(endDate), 'yyyy/MM/dd hh:mm:ss');
					delete param.createTime;
				}
				
				vue.$http.post(vue, '/rest/admin/merchantWithdrawRecord/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
					},(error, data)=> {
						alert(data);
						vue.listLoading = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			},
			handlePay (id) { // 打款
				this.$confirm('确认同意并打款吗?', '提示', {
					type: 'warning'
				}).then(() => {
					let vue = this;
					let param = {};
					param.id = id;
					param.status = 1;	
					let url = '/rest/admin/merchantWithdrawRecord/auditApplyWithdraw';
					vue.$http.post(vue, url, param,
						(vue, data) => {
							// // this.editLoading = false;
							if(data.success){
								vue.$message({
									showClose: true,
									message: data.message,
									type: 'success'
								});
								vue.getList()
								vue.checkFormVisible = false;
							}
						}, (error, data) => {				
							vue.checkFormVisible = false;
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'error'
							});
						}
					)
				}).catch(() => {});
			},			
			handleDel (id) { // 删除
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
					let vue = this;
				
					vue.$http.delete(vue, '/rest/admin/merchantWithdrawRecord/delete', {"ids" : [id]},
						function(vue, data) {
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'success'
							});
							vue.getList()
						}, function(error, data) {
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'error'
							});
						}
					)
				}).catch(() => {});
			},
			handleDetail: function (index, row) { // 显示编辑界面
				// this.editFormVisible = true;
				// if(row){
				// 	this.editForm = Object.assign({}, row)
				// 	this.editForm.selectedOptions = [row.province, row.city, row.area];	
				// }else{
				// 	this.editForm = {
				// 		name: '',
				// 		code: '',
				// 		selectedOptions2: [],
				// 		street: '',
				// 		isOperating: '正常营业',
				// 		startTime: '8:00',
				// 		endTime: '19:30',			
				// 	}
				// }		    							

				//进行打款操作
				
			},
            handleCheck(row){
                this.checkFormVisible = true;
				this.checkEdit.id = row.id;
				this.checkEdit.opinion = '';
			},			
			checkEditSubmit: function () { // 编辑
                let vue = this;
				let param = Object.assign({},this.checkEdit);		
				param.status = 2;	
				if(param.status == 2 && param.opinion == ""){
					vue.$message({
						showClose: true,
						message: "请填写审核不通过原因",
						type: 'error'
					});		
					return false;
				}
				let url = '/rest/admin/merchantWithdrawRecord/auditApplyWithdraw';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						// // this.editLoading = false;
						if(data.success){
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'success'
							});
							vue.getList()
							vue.checkFormVisible = false;
						}
					}, (error, data) => {				
						vue.checkFormVisible = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			},
			editSubmit: function () { // 编辑
                let vue = this;
				let param = Object.assign({},this.editForm);			
				// let url = '/rest/admin/merchantWithdrawRecord/auditApplyWithdraw';
				// vue.$http.post(vue, url, param,
				// 	(vue, data) => {
				// 		// // this.editLoading = false;
				// 		if(data.success){
				// 			vue.$message({
				// 				showClose: true,
				// 				message: data.message,
				// 				type: 'success'
				// 			});
				// 			vue.getList()
				// 			vue.checkFormVisible = false;
				// 		}
				// 	}, (error, data) => {				
				// 		vue.checkFormVisible = false;
				// 		vue.$message({
				// 			showClose: true,
				// 			message: data.message,
				// 			type: 'error'
				// 		});
				// 	}
				// )
			}			
		},
		mounted() {
			this.getList();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>

</style>