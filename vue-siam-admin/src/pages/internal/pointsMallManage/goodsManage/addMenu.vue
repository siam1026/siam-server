<template>
  <section>
    <el-form :model="editForm" label-width="150px" class="editForm" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="分类名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<el-form-item label="分类图标（宽400px * 高400px）" prop="iconFile" class="iconFileItem">
					<el-upload
						class="avatar-uploader" accept=".image, .png, .jpg"
						action="" multiple name="iconUpload"
						:on-remove="handleRemoveIcon"
						:before-upload="beforeAvatarUpload" :http-request="upload"
						list-type="picture-card">
						<i class="el-icon-plus avatar-uploader-icon"></i>
					</el-upload>
				</el-form-item>				
				<el-form-item label="描述" prop="description">
					<el-input type="textarea" :rows="5" v-model="editForm.description"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="el-dialog__footer">
				<el-button @click="goBack">取消</el-button>
				<el-button type="primary" @click="saveGoodsMsg">保存</el-button>
			</div>
  </section>
</template>
<script>
export default {
  data() {
    return {
      menuList: [],
      editForm: {
		isDisabled: false,
		name: '',
		description: '',
		iconFile: [], // 分类图标
      },
      editFormRules: {
        isDisabled: [{ required: true, message: '请选择分类状态', trigger: 'change' }],
		name: [{ required: true, message: '请输入分类名称', trigger: 'blur' }],
		description: [{ required: false, message: '请输入分类描述', trigger: 'blur' }],
		iconFile: [{ type: 'array', required: true, message: '请上传分类图标', trigger: 'change' }],
      }
    }
  },
  methods: {
    saveGoodsMsg() {
		this.$refs.editForm.validate((valid) => {
			if (valid) {		  						
				this.editLoading = true;
				let vue = this

				if(!vue.editForm.iconFile.length) {
					vue.$message({
					showClose: true,
					message: '请上传商品分类图标',
					type: 'error'
					});
					return false
				}else if(vue.editForm.iconFile.length > 1) {
					vue.$message({
					showClose: true,
					message: '只能上传一张商品分类图标',
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
							
							vue.goBack()
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
							vue.goBack()
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
    goBack() {
      this.$refs['editForm'].resetFields();
      this.$router.push({path: 'pointsMall_menuList'})
    },
    getIdByArr(data) {
      let arr = data || []
      let url = arr.map((item) => {
        return item.url
      });
      return url.join(',')
    },
    removeDomain(item) {
      var index = this.editForm.standData.indexOf(item)
      if (index !== -1) {
        this.editForm.standData.splice(index, 1)
      }
    },
    addDomain() {
      this.editForm.standData.push({
        stand: '',
        stock: ''
      });
    },
    getTypeList() { // 获取商品类别列表
				let vue = this
				let param = {
          pageNo: -1, 
          pageSize: 10,
          typestatus: 0
        }
				vue.$http.post(vue, '/rest/admin/pointsMall/menu/list', param,
					(vue, data) => {
						vue.menuList = data.data.records
					},(error, data)=> {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
		},
    handleRemoveSubImages(file, filelist) {
      let arr = this.editForm.subImagesFile
      this.editForm.subImagesFile = arr.filter(function(item) {
        return item.uid !== file.uid
      });
    },
    handleRemoveDetailImages(file, filelist) {
      let arr = this.editForm.detailImagesFile
      this.editForm.detailImagesFile = arr.filter(function(item) {
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
	handleRemoveIcon(file, filelist) {
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
  },
  created() {
    this.getTypeList();
    this.editForm.status = 1;
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
</style>

