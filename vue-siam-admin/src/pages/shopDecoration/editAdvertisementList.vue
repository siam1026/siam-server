<template>
  <section>
    <el-form :model="editForm" label-width="150px" class="editForm" style="width: 80%;" :rules="editFormRules" ref="editForm">
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
            :file-list="editForm.imagePathFile"
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
        type: '',
        imageName: '',
        imagePathFile: [],
        description: '',
        imageLinkUrl: '',
        sortNumber: '',		
      },
      editFormRules: {
        type: [{ required: true, message: '请选择轮播图类型', trigger: 'change' }],					
        imageName: [{ required: true, message: '请输入轮播图名称', trigger: 'blur' }],
        imagePathFile: [{ type: 'array', required: true, message: '请上传轮播图', trigger: 'change' }],
        description: [{ required: false, message: '请输入说明', trigger: 'blur' }],
        imageLinkUrl: [{ required: false, message: '请输入点击轮播图跳转的链接', trigger: 'blur' }],
        sortNumber: [{ required: false, message: '请输入排序号', trigger: 'blur' }],
      }
    }
  },
  methods: {
    saveGoodsMsg() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          let vue = this
          let param = Object.assign({}, this.editForm);
          let url = '';
          //由于编辑时轮播图展示不出来，所以暂时性做逻辑控制，修改时轮播图数组可为空，代表不修改
          if((!param.id && !vue.editForm.imagePathFile.length)) {
            vue.$message({
            showClose: true,
            message: '请上传轮播图',
            type: 'error'
            });
            return false
          }else if(vue.editForm.imagePathFile.length > 1) {
            vue.$message({
            showClose: true,
            message: '只能上传一张轮播图',
            type: 'error'
            });
            return false
          }

          param.imagePath = vue.getIdByArr(vue.editForm.imagePathFile)
          delete param.imagePathFile	
          // param.imagePath = vue.getIdByArr(param.imagePathFile)
          // delete param.imagePathFile		

          delete param.createTime
          delete param.updateTime

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
                
                vue.goBack()
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
      this.$router.push({path: 'advertisementList'})
    },
    getIdByArr(data) {
      let arr = data || []
      let url = arr.map((item) => {
        return item.oldUrl
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
    getDetail(id) { // 获取商品详情
				let vue = this
				vue.$http.post(vue, '/rest/admin/advertisement/getById', {id},
					(vue, data) => {
            let obj = data.data
            obj.imagePathFile = vue.resetImg(obj, 'imagePath')
            // obj.subImagesFile = vue.resetImg(obj, 'subImages')
            // obj.detailImagesFile =  vue.resetImg(obj, 'detailImages')

            obj.type = (obj.type == 1) ? '首页轮播图（宽450px * 高225px）' : ((obj.type == 2) ? '菜单页轮播图（宽450px * 高185px）' : ((obj.type == 3) ? '积分商城页面轮播图（宽450px * 高225px）' : ((obj.type == 4) ? '分享页面生成美图（宽1080px * 高1854px）' : '-')));

            vue.editForm = Object.assign({}, obj)
					},(error, data)=> {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
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
	handleRemoveImagePath(file, filelist) {
		let arr = this.editForm.imagePathFile
		this.editForm.imagePathFile = arr.filter(function(item) {
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
      //TODO(MARK)-编辑页面特殊配置
			let obj = {
				oldUrl: data.data,
        url: vue.$http.publicUrl(data.data),
        uid: option.file.uid,
        }
			if (option.filename === 'subImagesUpload') {
				vue.editForm.subImagesFile.push(obj)
			} else if(option.filename === 'detailImagesUpload') {
				vue.editForm.detailImagesFile.push(obj)
			} else {
				vue.editForm.imagePathFile.push(obj)
			}
			},
			function (error) {
				option.onError();
		})
	},		
  },
  created() {
    this.getTypeList()
    let id = this.$route.query.id
    this.getDetail(id)
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

