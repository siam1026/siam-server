<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="轮播图名称" prop="imageName">
					<el-input v-model="searchMsg.imageName" clearable placeholder="请输入轮播图名称"></el-input>
				</el-form-item>
				<el-form-item label="轮播图类型" prop="type">
					<el-select v-model="searchMsg.type" clearable>
						<el-option label="首页轮播图" :value="1"></el-option>
						<!-- <el-option label="菜单页轮播图" :value="2"></el-option> -->
						<el-option label="积分商城页面轮播图" :value="3"></el-option>
						<el-option label="分享页面生成美图" :value="4"></el-option>
					</el-select>
				</el-form-item>
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
			<el-table-column label="轮播图" prop="imagePath">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.imagePath)" width="50" height="50">
				</template>
			</el-table-column>				
			<el-table-column prop="imageName" label="轮播图名称"></el-table-column>
			<el-table-column prop="description" label="说明"></el-table-column>
			<el-table-column prop="type" label="轮播图类型">
				<template scope="scope">
					<span v-if="scope.row.type == 1">首页轮播图</span>
					<span v-if="scope.row.type == 2">菜单页轮播图</span>
					<span v-if="scope.row.type == 3">积分商城页面轮播图</span>
					<span v-if="scope.row.type == 4">分享页面生成美图</span>
               </template>							
			</el-table-column>
			<el-table-column prop="imageLinkUrl" label="点击轮播图跳转的链接"></el-table-column>
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
			<el-form class="advertisementForm" :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="轮播图类型" prop="type">
					<el-radio-group v-model="editForm.type" size="medium">
						<el-radio-button label="首页轮播图（宽450px * 高225px）" value="1"></el-radio-button>
						<!-- <el-radio-button label="菜单页轮播图（宽450px * 高185px）" value="2"></el-radio-button> -->
						<el-radio-button label="积分商城页面轮播图（宽450px * 高225px）" value="3"></el-radio-button>
						<el-radio-button label="分享页面生成美图（宽1080px * 高1854px）" value="4"></el-radio-button>
					</el-radio-group>				
				</el-form-item>						
				<el-form-item label="轮播图名称" prop="imageName">
					<el-input v-model="editForm.imageName"></el-input>
				</el-form-item>
				<el-form-item label="轮播图" prop="imagePathFile">
					<el-upload
						class="avatar-uploader" accept=".image, .png, .jpg"
						action="" multiple name="imagePathUpload"
						:on-remove="handleRemoveImagePath"
						:before-upload="beforeAvatarUpload" :http-request="upload"
						list-type="picture-card" ref="upload">
						<i class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>				
				<el-form-item label="说明" prop="description">
					<el-input v-model="editForm.description"></el-input>
				</el-form-item>
				<el-form-item label="点击轮播图跳转的链接" prop="imageLinkUrl">
					<el-input v-model="editForm.imageLinkUrl"></el-input>
				</el-form-item>						
				<el-form-item label="排序号" prop="sortNumber">
					<el-input v-model="editForm.sortNumber"></el-input>
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
	//由于editForm.imagePathFile引用有问题，所以暂时定义一个新变量用于使用
	var tempImagePathFile = [];
	
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
					type: [{ required: true, message: '请选择轮播图类型', trigger: 'change' }],					
					imageName: [{ required: true, message: '请输入轮播图名称', trigger: 'blur' }],
					tempImagePathFile: [{ type: 'array', required: true, message: '请上传轮播图', trigger: 'change' }],
					description: [{ required: false, message: '请输入说明', trigger: 'blur' }],
					imageLinkUrl: [{ required: false, message: '请输入点击轮播图跳转的链接', trigger: 'blur' }],
					sortNumber: [{ required: false, message: '请输入排序号', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: {
					type: '',
					imageName: '',
					imagePathFile: [],
					description: '',
					imageLinkUrl: '',
					sortNumber: '',				
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
				return row.type == 1 ? '首页轮播图' : (row.type == 2 ? '菜单页轮播图' : (row.type == 3 ? '积分商城页面轮播图' : (row.type == 4 ? '分享页面生成美图' : '-')))
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
				} // 获取商品轮播图列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/admin/advertisement/list', param,
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
			handleDel (id) { // 删除
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
					let vue = this;
				
					vue.$http.delete(vue, '/rest/admin/advertisement/delete', {"id" : id},
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
			gotoAdd(type, row) {
				if(type === 'add') {
				this.$router.push({path: 'addAdvertisementList'})
				} else {
				this.$router.push({path:'editAdvertisementList',query:{id: row.id}})
				}
			},			
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
				if(row){
					this.editForm = {
						id: row.id,
						type: (row.type == 1) ? '首页轮播图（宽450px * 高225px）' : ((row.type == 2) ? '菜单页轮播图（宽450px * 高185px）' : ((row.type == 3) ? '积分商城页面轮播图（宽450px * 高225px）' : ((row.type == 4) ? '分享页面生成美图（宽1080px * 高1854px）' : '-'))),
						imageName: row.imageName,
						imagePathFile: this.resetImg(row, 'imagePath'),
						description: row.description,
						imageLinkUrl: row.imageLinkUrl,
						sortNumber: row.sortNumber,
					}
					tempImagePathFile = [];
					//清空之前上传的图片
					this.$refs.upload.clearFiles();
					// tempImagePathFile = this.resetImg(row, 'imagePath');
					// console.log("----------9999999999")
					// console.log(this.resetImg(row, 'imagePath'));

					// let obj = row
					// obj.imagePathFile = this.resetImg(obj, 'imagePath')
					// this.editForm = Object.assign({}, obj)					
				}else{
					//这里定义的editForm会把之前的editForm覆盖掉
					this.editForm = {
						type: '首页轮播图（宽450px * 高225px）',												
						imageName: '',
						imagePathFile: '',
						description: '',
						imageLinkUrl: '',
						sortNumber: '',
					}
					tempImagePathFile = [];
					//清空之前上传的图片
					this.$refs.upload.clearFiles();
				}				
			},
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = '';
						//由于编辑时轮播图展示不出来，所以暂时性做逻辑控制，修改时轮播图数组可为空，代表不修改
						if((!param.id && !tempImagePathFile.length)) {
							vue.$message({
							showClose: true,
							message: '请上传轮播图',
							type: 'error'
							});
							return false
						}else if(tempImagePathFile.length > 1) {
							vue.$message({
							showClose: true,
							message: '只能上传一张轮播图',
							type: 'error'
							});
							return false
						}

						param.imagePath = vue.getIdByArr(tempImagePathFile)
						delete param.imagePathFile	
						// param.imagePath = vue.getIdByArr(param.imagePathFile)
						// delete param.imagePathFile		

						param.type = (param.type == '首页轮播图（宽450px * 高225px）') ? 1 : ((param.type == '菜单页轮播图（宽450px * 高185px）') ? 2 : ((param.type == '积分商城页面轮播图（宽450px * 高225px）') ? 3 : ((param.type == '分享页面生成美图（宽1080px * 高1854px）') ? 4 : '')));

						// this.editLoading = true;
						if(param.id){
							url = '/rest/admin/advertisement/update';
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
									// this.editLoading = false;									
									vue.editFormVisible = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}else{
							url = '/rest/admin/advertisement/insert';
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
			},
			handleRemoveImagePath(file, filelist) {
				// let arr = this.editForm.imagePathFile
				// this.editForm.imagePathFile = arr.filter(function(item) {
				// 	return item.uid !== file.uid
				// });
				let arr = tempImagePathFile
				tempImagePathFile = arr.filter(function(item) {
					return item.uid !== file.uid
				});				
			},
			beforeAvatarUpload(file) {
				const isLt2M = file.size / 1024 / 1024 < 6;
				if (!isLt2M) {
				this.$message({
					showClose: true,
					message: '上传图片大小不能超过 6MB!',
					type: 'error'
				});
				}
				return isLt2M;
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
						if (option.filename === 'imagePathUpload') {
							tempImagePathFile.push(obj);
						}
					},
					function (error) {
						option.onError();
				})
			},
			resetImg(row, imgType) {
				let data = row
				if(row[imgType]) {
					let arr = row[imgType].split(',');
					let newArr = []
					let index = 0
					arr.forEach(ele => {
					let obj = {
						url: this.$http.publicUrl(ele),
						oldUrl: ele,
						name: index +1
					}
					newArr.push(obj)
					index++
					});
					return newArr
				} else {
					return []
				}
			},		
			getIdByArr(data) {
				let arr = data || []
				let url = arr.map((item) => {
					return item.oldUrl == undefined ? item.url : item.oldUrl;
				});
				return url.join(',')
			},		
		},
		mounted() {
			this.getList();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>
.editForm .el-input {
  width: 420px;
}
.standForm .el-input {
  width: 200px;
}
.avatar-uploader .el-upload {
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 148px;
    height: 148px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 148px;
    height: 148px;
    line-height: 148px;
    text-align: center;
  }
  .avatar {
    width: 148px;
    height: 148px;
    display: block;
  }
  .editForm .minInput {
    width: 240px;
  }
  /* 将列表的图片宽高比例调大一点 */
  .el-table__body img{
	/* width: 318px;
	height: 148px;	   */
    width: 120px;
    height: 120px;	
  }
</style>