<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="分类名称" prop="name">
					<el-input v-model="searchMsg.name" clearable placeholder="请输入分类名称"></el-input>
				</el-form-item>
        		<!-- <el-form-item label="分类状态" prop="isDisabled">
					<el-select v-model="searchMsg.isDisabled" clearable>
						<el-option label="正常" :value="0"></el-option>
						<el-option label="禁用" :value="1"></el-option>
					</el-select>
				</el-form-item> -->
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="gotoAdd('add')">新增</el-button>
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
			<el-table-column prop="name" label="分类名称"></el-table-column>
      		<el-table-column label="分类图标" prop="icon">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.icon)" width="50" height="50">
				</template>
			</el-table-column>			
			<!-- <el-table-column prop="isDisabled" label="状态" width="100" :formatter="formatType"></el-table-column> -->
			<el-table-column prop="description" label="描述"></el-table-column>
			<el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column>
			<el-table-column prop="updateTime" label="修改时间" :formatter="formatTime"></el-table-column>
			<el-table-column label="操作" fixed="right" width="150">
				<template slot-scope="scope">
					<el-button size="small" @click="gotoAdd('edit', scope.row)">编辑</el-button>
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
				<!-- <el-form-item label="状态" prop="isDisabled">
					<el-select v-model="editForm.isDisabled" placeholder="请选择">
						<el-option label="正常" :value="0"></el-option>
						<el-option label="禁用" :value="1"></el-option>
					</el-select>
				</el-form-item> -->
				<el-form-item label="分类名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="分类图标（宽400px * 高400px）" prop="iconFile" class="iconFileItem">
					<el-upload
						class="avatar-uploader" accept=".image, .png, .jpg"
						action="" multiple name="iconUpload"
						:on-remove="handleRemoveMainImage"
						:before-upload="beforeAvatarUpload" :http-request="upload"
						list-type="picture-card">
						<i class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>				
				<el-form-item label="描述" prop="description">
					<el-input type="textarea" :rows="5" v-model="editForm.description"></el-input>
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
					isDisabled: [{ required: true, message: '请选择分类状态', trigger: 'change' }],
					name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
					description: [{ required: false, message: '请输入分类描述', trigger: 'blur' }],
					iconFile: [{ type: 'array', required: true, message: '请上传主图', trigger: 'change' }],
				},
				//编辑界面数据
				editForm: {
					isDisabled: false,
					name: '',
					description: '',
					iconFile: [], // 主图
				}
			}
		},
		methods: {
			handleRemoveMainImage(file, filelist) {
				let arr = this.editForm.iconFile
				this.editForm.iconFile = arr.filter(function(item) {
					return item.uid !== file.uid
				});
			},		
			upload(option){ // 图片上传
				let vue = this;
				let value = option.file;
				let formData = new FormData();
				formData.append('file', value);    

				vue.$http.postupload(vue, '/rest/admin/uploadSingleImage', formData,
					function (vue, data) {
					option.onSuccess();
					let obj = {
						url: data.data,
						uid: option.file.uid
						}
					if (option.filename === 'subImagesUpload') {
						vue.editForm.subImagesFile.push(obj)
					} else if(option.filename === 'detailImagesUpload') {
						vue.editForm.detailImagesFile.push(obj)
					} else {
						vue.editForm.iconFile.push(obj)
					}
					},
					function (error) {
						option.onError();
				})
			},				
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
				} // 获取商品分类列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;
				vue.$http.post(vue, '/rest/admin/pointsMall/menu/list', param,
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
				
				vue.$http.delete(vue, '/rest/admin/pointsMall/menu/delete', {"ids" : [id]},
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
						isDisabled: row.isDisabled,
						name: row.name,
						description: row.description
					}
				}else{
					this.editForm = {
						isDisabled: 0,
						name: '',
						description: ''
					}
        		}
			},
			gotoAdd(type, row) {
				if(type === 'add') {
				this.$router.push({path: 'pointsMall_addMenu'})
				} else {
				this.$router.push({path:'pointsMall_editMenu',query:{id: row.id}})
				}
			},			
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {		  						
						this.editLoading = true;
						let vue = this

						if(!vue.editForm.iconFile.length) {
							vue.$message({
							showClose: true,
							message: '请上传商品主图',
							type: 'error'
							});
							return false
						}else if(vue.editForm.iconFile.length > 1) {
							vue.$message({
							showClose: true,
							message: '只能上传一张商品主图',
							type: 'error'
							});
							return false
						}

						let param = Object.assign({}, this.editForm);

						param.icon = vue.getIdByArr(param.iconFile)
						delete param.iconFile

						let url = '';
						if(param.id){
							url = '/rest/admin/pointsMall/menu/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									this.editLoading = false;
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
							url = '/rest/admin/pointsMall/menu/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									this.editLoading = false;
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