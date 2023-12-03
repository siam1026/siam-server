<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg"> 
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
			<el-table-column prop="shopName" label="店铺名称"></el-table-column>
			<el-table-column prop="username" label="用户名称"></el-table-column>
			<el-table-column prop="orderNo" label="订单编号">
				<template scope="scope">
					<span><a href="javascript:void(0)" @click="goOrderDetail(scope.row.orderId)" style="color:blue;">{{scope.row.orderNo}}</a></span>
				</template>  		  
			</el-table-column>
			<el-table-column prop="content" label="评价内容"></el-table-column>
			<el-table-column prop="level" label="店铺打分">
				<template scope="scope">
					<span>{{scope.row.level}}星</span>
				</template>    
			</el-table-column>
			<el-table-column prop="replyList" label="回复数量">
				<template scope="scope">
					<span>{{scope.row.replyPage.records.length}}个</span>
				</template>    		  
			</el-table-column>
			<el-table-column prop="giveLikeCount" label="点赞数量">
				<template scope="scope">
					<span>{{scope.row.giveLikeCount}}个</span>
				</template>  		  
			</el-table-column>
			<el-table-column prop="createTime" label="评价时间" :formatter="formatTime" width="190"></el-table-column>
			<el-table-column label="操作" fixed="right" width="150">
				<template slot-scope="scope">
					<!-- <el-button size="small" @click="handleEdit(scope.$index, scope.row)">回复</el-button> -->
          			<!-- <el-button size="small" @click="handleEdit(scope.$index, scope.row)">查看</el-button> -->
					<el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button>
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
		<el-dialog title="编辑" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="回复内容" prop="content">
					<el-input type="textarea" :rows="6" v-model="editForm.content"></el-input>
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

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					content: [{ required: true, message: '请输入回复内容', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: {
					content: ''			
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
				return row.stock == 1 ? '有货' : '无货';
			},
			addUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '元'
			},	
			goOrderDetail(orderDetailId) {
				if(orderDetailId) {
					this.$router.push({path:'orderDetail', query:{id: orderDetailId}})
				}
			},
			handleSizeChange(val) {
				this.searchMsg.pageSize = val;
				this.getList();
			},			
			handleCurrentChange(val) {
				this.searchMsg.pageNo = val;
				this.getList();
			},
			getList(pageNoParam) { // 获取列表
				if(pageNoParam){
					this.searchMsg.pageNo = pageNoParam;
				}
				// 获取商品辅料列表
				let vue = this
				let param = Object.assign(vue.searchMsg);

				//处理开始日期、结束日期
				if(vue.searchMsg.createTime){
					let startDate = vue.searchMsg.createTime[0];
					let endDate = vue.searchMsg.createTime[1];
					param.startCreateTime = this.$utils.formatDate(new Date(startDate), 'yyyy/MM/dd hh:mm:ss');
					param.endCreateTime = this.$utils.formatDate(new Date(endDate), 'yyyy/MM/dd hh:mm:ss');
					delete param.createTime;
				}
                
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/admin/appraise/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
					},(error, data)=> {
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
			handleDel (id) { // 删除
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					let vue = this;
					vue.$http.delete(vue, '/rest/admin/appraise/delete', {"id" : id},
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
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
				if(row){
					this.editForm = {
						appraiseId: row.id,
					}
				}else{
					this.editForm = {
						name: '',			
					}
				}

				//不用这种方式回写，这种方式会导致修改提交的时候所有属性都提交上来
				// if(row){
				// 	this.editForm = Object.assign({}, row);
				// 	this.editForm.selectedOptions2 = [row.province, row.city, row.area];	
				// }else{
				// 	this.editForm = {}
				// }							
			},
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						// this.editLoading = true;
						let vue = this
						let param = Object.assign({}, this.editForm);
						param.replyType = 1;

						let url = '/rest/admin/reply/insert';
						vue.$http.post(vue, url, param,
						(vue, data) => {
							// this.editLoading = false;
							vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
							});
							
							vue.getList()
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
		mounted() {
			this.getList();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>

</style>