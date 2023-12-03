<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="规则名称" prop="name">
					<el-input v-model="searchMsg.name" clearable placeholder="请输入规则名称"></el-input>
				</el-form-item>
				<el-form-item label="活动状态" prop="status">
					<el-select v-model="searchMsg.status" clearable>
						<el-option label="开启" :value="1"></el-option>
						<el-option label="关闭" :value="2"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleEdit">新增</el-button>
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
			<el-table-column prop="name" label="规则名称"></el-table-column>
			<el-table-column prop="limitedPrice" label="满足价格" :formatter="addUnit"></el-table-column>
			<el-table-column prop="reducedPrice" label="减价额度" :formatter="addUnit"></el-table-column>
			<el-table-column prop="status" label="活动状态">
				<template scope="scope">
					<span v-if="scope.row.status == 1" style="color: black;">开启</span>
                    <span v-else-if="scope.row.status == 2" style="color: red;">关闭</span>
               </template>							
			</el-table-column>
			<el-table-column label="操作" fixed="right" width="150">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
				<el-form-item label="规则名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="满足价格" prop="limitedPrice">
					<el-input v-model="editForm.limitedPrice"></el-input>
				</el-form-item>
				<el-form-item label="减价额度" prop="reducedPrice">
					<el-input v-model="editForm.reducedPrice"></el-input>
				</el-form-item>				
				<el-form-item label="活动状态" prop="status">
					<el-radio-group v-model="editForm.status" size="medium">
						<el-radio-button label="开启" value="1"></el-radio-button>
						<el-radio-button label="关闭" value="2"></el-radio-button>
					</el-radio-group>				
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
					name: [{ required: true, message: '请输入规则名称', trigger: 'blur' }],
					limitedPrice: [{ required: true, message: '请输入满足价格', trigger: 'blur' }],
					reducedPrice: [{ required: true, message: '请输入减价额度', trigger: 'blur' }],
					status: [{ required: true, message: '请选择活动状态', trigger: 'change' }],
				},
				//编辑界面数据
				editForm: {
					name: '',
					limitedPrice: '',
					reducedPrice: '',
					status: false,
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
				return row.status == 1 ? '开启' : '关闭';
			},
			addUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '元'
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
				} // 获取商品辅料列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/merchant/fullReductionRule/list', param,
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
					// this.listLoading = true;
					let vue = this;
				
					vue.$http.delete(vue, '/rest/merchant/fullReductionRule/delete', {"id" : id},
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
						id: row.id,
						name: row.name,
						limitedPrice: row.limitedPrice,
						reducedPrice: row.reducedPrice,
						status: (row.status == 1) ? '开启' : '关闭'
					}
				}else{
					this.editForm = {
						name: '',
						limitedPrice: '',
						reducedPrice: '',
						status: '开启',		
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
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = '';

						if(parseFloat(param.reducedPrice) >= parseFloat(param.limitedPrice)){
							vue.$message({
							showClose: true,
							message: '减价额度必须小于满足价格',
							type: 'error'
							});
							return false							
						}						

						// this.editLoading = true;
						//alert(province + " " + city + " " + area);

						param.status = (param.status == '开启') ? 1 : 2;

						if(param.id){
							url = '/rest/merchant/fullReductionRule/update';
							vue.$http.put(vue, url, param,
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
						}else{
							url = '/rest/merchant/fullReductionRule/insert';
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