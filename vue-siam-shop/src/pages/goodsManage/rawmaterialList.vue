<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="原料名称" prop="name">
					<el-input v-model="searchMsg.name" clearable placeholder="请输入原料名称"></el-input>
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
			<el-table-column prop="name" label="原料名称"></el-table-column>
			<el-table-column prop="unit" label="采购单位"></el-table-column>
			<el-table-column prop="price" label="采购单价" :formatter="addUnit"></el-table-column>
			<el-table-column prop="stock" label="当前库存">
				<template scope="scope">
					<span>{{scope.row.stock + scope.row.unit}}</span>
               </template>							
			</el-table-column>
			<el-table-column prop="stockLowerLimit" label="库存过低线">
				<template scope="scope">
					<span>{{scope.row.stockLowerLimit + scope.row.unit}}</span>
               </template>							
			</el-table-column>
			<el-table-column prop="stockUpperLimit" label="库存超出线">
				<template scope="scope">
					<span>{{scope.row.stockUpperLimit + scope.row.unit}}</span>
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
				<el-form-item label="原料名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<!-- <el-form-item label="采购单位" prop="unit">
					<el-select v-model="editForm.unit" placeholder="请选择">
						<el-option label="两" :value="1"></el-option>
						<el-option label="箱" :value="2"></el-option>
						<el-option label="瓶" :value="3"></el-option>
						<el-option label="袋" :value="4"></el-option>
						<el-option label="斤" :value="5"></el-option>
						<el-option label="千克" :value="6"></el-option>
						<el-option label="克" :value="7"></el-option>
						<el-option label="升" :value="8"></el-option>
						<el-option label="毫升" :value="9"></el-option>
						<el-option label="个" :value="10"></el-option>
						<el-option label="只" :value="11"></el-option>
					</el-select>
				</el-form-item>		 -->
				<el-form-item label="采购单位" prop="unit">
					<el-select v-model="editForm.unit" placeholder="请选择">
						<el-option v-for="item in unitArray" :key="item.value" :label="item.name" :value="item.value"></el-option>
					</el-select>
				</el-form-item>						
				<el-form-item label="采购单价" prop="price">
					<el-input v-model="editForm.price" type="number"></el-input>
				</el-form-item>
				<el-form-item label="库存" prop="stock">
					<el-input v-model="editForm.stock" type="number"></el-input>
				</el-form-item>		
				<el-form-item label="库存过低线" prop="stockLowerLimit">
					<el-input v-model="editForm.stockLowerLimit" type="number"></el-input>			
				</el-form-item>	
				<el-form-item label="库存超出线" prop="stockUpperLimit">
					<el-input v-model="editForm.stockUpperLimit" type="number"></el-input>			
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
					name: [{ required: true, message: '请输入原料名称', trigger: 'blur' }],
					unit: [{ required: true, message: '请选择采购单位', trigger: 'change' }],
					price: [{ required: true, message: '请输入采购单价', trigger: 'blur' }],
					stock: [{ required: true, message: '请输入库存', trigger: 'blur' }],
					stockLowerLimit: [{ required: true, message: '请输入库存过低线', trigger: 'blur' }],
					stockUpperLimit: [{ required: true, message: '请输入库存超出线', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: {
					name: '',
					unit: '',
					price: '',					
					stock: '',
					stockLowerLimit: '',
					stockUpperLimit: '',
				},
				unitArray: [
							// {name : "两", value : "两"},
							// {name : "箱", value : "箱"},
							// {name : "瓶", value : "瓶"},
							{name : "克", value : "克"},
							// {name : "斤", value : "斤"},
							// {name : "千克", value : "千克"},
							{name : "袋", value : "袋"},
							{name : "个", value : "个"},
							{name : "片", value : "片"},
							// {name : "升", value : "升"},
							// {name : "毫升", value : "毫升"},
							// {name : "个", value : "个"},
							// {name : "只", value : "只"}																																																																											
							]
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
				} // 获取商品原料列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/merchant/rawmaterial/list', param,
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
				
					vue.$http.delete(vue, '/rest/merchant/rawmaterial/delete', {"id" : id},
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
						unit: row.unit,
						price: row.price,
						stock: row.stock,
						stockLowerLimit: row.stockLowerLimit,
						stockUpperLimit: row.stockUpperLimit,
					}
				}else{
					this.editForm = {
						name: '',
						price: '',	
						unit: '',	
						price: '',	
						stock: '',	
						stockLowerLimit: '',	
						stockUpperLimit: '',	
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
						let url = '';

						if(param.id){
							url = '/rest/merchant/rawmaterial/update';
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
							url = '/rest/merchant/rawmaterial/insert';
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