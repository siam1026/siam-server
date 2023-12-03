<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="打印机名称" prop="name">
					<el-input v-model="searchMsg.name" clearable placeholder="请输入打印机名称"></el-input>
				</el-form-item>
				<el-form-item label="打印机编号" prop="number">
					<el-input v-model="searchMsg.number" clearable placeholder="请输入打印机编号"></el-input>
				</el-form-item>				
        		<!-- <el-form-item label="打印机状态" prop="isDisabled">
					<el-select v-model="searchMsg.isDisabled" clearable>
						<el-option label="正常" :value="0"></el-option>
						<el-option label="禁用" :value="1"></el-option>
					</el-select>
				</el-form-item> -->
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
			<el-table-column prop="name" label="打印机名称"></el-table-column>
			<el-table-column prop="number" label="打印机编号"></el-table-column>
			<el-table-column prop="identifyingCode" label="打印机密钥/识别码"></el-table-column>
			<el-table-column prop="isAutoPrint" label="接单后是否自动打印小票">
				<template scope="scope">
					<span v-if="scope.row.isAutoPrint == 0">否</span>					
					<span v-else-if="scope.row.isAutoPrint == 1">是</span>				
               </template>						
			</el-table-column>
			<!-- <el-table-column prop="isDisabled" label="状态" width="100" :formatter="formatType"></el-table-column> -->
			<el-table-column prop="brand" label="打印机品牌">
				<template scope="scope">
					<span v-if="scope.row.brand == 1">飞鹅打印机</span>					
					<!-- <span v-else-if="scope.row.brand == 2">易联云打印机</span> -->
               </template>						
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column>
			<el-table-column prop="updateTime" label="修改时间" :formatter="formatTime"></el-table-column>
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
				<el-form-item label="打印机名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="打印机编号" prop="number">
					<el-input v-model="editForm.number"></el-input>
				</el-form-item>
				<el-form-item label="打印机密钥/识别码" prop="identifyingCode">
					<el-input v-model="editForm.identifyingCode"></el-input>
				</el-form-item>
				<el-form-item label="接单后是否自动打印小票" prop="isAutoPrint">
					<el-select v-model="editForm.isAutoPrint" placeholder="请选择">
						<el-option label="是" :value="1"></el-option>
						<el-option label="否" :value="0"></el-option>
					</el-select>
				</el-form-item>
				<el-form-item label="打印机品牌" prop="brand">
					<el-select v-model="editForm.brand" placeholder="请选择">
						<el-option label="飞鹅打印机" :value="1"></el-option>
						<!-- <el-option label="易联云打印机" :value="2"></el-option> -->
					</el-select>
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
					name: [{ required: true, message: '请输入打印机名称', trigger: 'blur' }],
					number: [{ required: true, message: '请输入打印机编号', trigger: 'blur' }],
					identifyingCode: [{ required: true, message: '请输入打印机密钥/识别码', trigger: 'blur' }],
					isAutoPrint: [{ required: false, message: '请选择接单后是否自动打印小票', trigger: 'change' }],
					brand: [{ required: false, message: '请选择打印机品牌', trigger: 'change' }],										
				},
				//编辑界面数据
				editForm: {
					name: '',
					number: ''
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
			getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 获取打印机列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				
				//查询小票打印机
				param.type = 1;			

				vue.listLoading = true;
				vue.$http.post(vue, '/rest/merchant/printer/list', param,
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
				
				vue.$http.delete(vue, '/rest/merchant/printer/delete', {"ids" : [id]},
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
						number: row.number,
						identifyingCode: row.identifyingCode,
						isAutoPrint: row.isAutoPrint ? 1 : 0,
						brand: row.brand,
					}
				}else{
					this.editForm = {
						name: '',
						number: '',
						identifyingCode: '',
						isAutoPrint: 1,
						brand: 1,
					}
        		}
			},
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						// this.editLoading = true;
						let vue = this
						let param = Object.assign({}, this.editForm);

						if(param.isAutoPrint == '否'){
							param.isAutoPrint = 0;
						}else if(param.isAutoPrint == '是'){
							param.isAutoPrint = 1;
						}

						if(param.brand == '飞鹅打印机'){
							param.brand = 1;
						}else if(param.brand == '易联云打印机'){
							param.brand = 2;
						}

						let url = '';
						if(param.id){								
							url = '/rest/merchant/printer/update';
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
							//设置打印机类型为小票打印机
							param.type = 1;
							url = '/rest/merchant/printer/insert';
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