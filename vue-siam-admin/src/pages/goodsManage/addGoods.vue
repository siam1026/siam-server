<template>
  <section>
    <el-form :model="editForm" label-width="150px" class="editForm" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="商品名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
        <el-form-item label="商品类别" prop="menuId">
					<el-select v-model="editForm.menuId" class="minInput">
            <el-option v-for="item in menuList" :key="item.id" :label="item.name" :value="item.id"></el-option>
          </el-select>
				</el-form-item>

				<el-form-item label="状态" prop="status">
					<el-select v-model="editForm.status" class="minInput">
						<el-option label="待上架" :value="1"></el-option>
						<el-option label="已上架" :value="2"></el-option>
						<el-option label="已下架" :value="3"></el-option>
						<el-option label="售罄" :value="4"></el-option>                   
					</el-select>
				</el-form-item>

        <el-form-item label="一口价(元)" prop="price">
					<el-input v-model="editForm.price" class="minInput"></el-input>
				</el-form-item>    

        <el-form-item label="折扣价(元)" prop="salePrice">
					<el-input v-model="editForm.salePrice" class="minInput"></el-input>
				</el-form-item>

        <el-form-item label="包装费" prop="packingCharges">
          <el-input v-model="editForm.packingCharges" class="minInput"></el-input>          
					<!-- <el-select v-model="editForm.packingCharges" class="minInput">
						<el-option label="1元" :value="1"></el-option>
						<el-option label="1.5元" :value="1.5"></el-option>
						<el-option label="2元" :value="2"></el-option>
						<el-option label="2.5元" :value="2.5"></el-option>                   
            <el-option label="3元" :value="3"></el-option>                   
            <el-option label="3.5元" :value="3.5"></el-option>                   
					</el-select> -->
				</el-form-item>    

        <el-form-item label="制作时长(分钟)" prop="productTime">
					<el-input v-model="editForm.productTime" class="minInput"></el-input>
				</el-form-item>        

        <!-- <el-form-item label="商品链接" prop="marketurl">
					<el-input v-model="editForm.marketurl"></el-input>
				</el-form-item> -->
        <el-form-item label="商品描述" prop="detail">
					<el-input v-model="editForm.detail"></el-input>
				</el-form-item>
				<el-form-item label="商品主图（宽400px * 高400px）" prop="mainImageFile" class="mainImageFileItem">
          <el-upload
              class="avatar-uploader" accept=".image, .png, .jpg"
              action="" multiple name="mainImageUpload"
              :on-remove="handleRemoveMainImage"
              :before-upload="beforeAvatarUpload" :http-request="upload"
              list-type="picture-card">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
				</el-form-item>
        <el-form-item label="介绍图片/商品详情页轮播图（宽600px * 高355px）" prop="subImagesFile" class="subImagesFileItem">
          <el-upload
              ref="multipleUpload"
              class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="subImagesUpload"
              :on-remove="handleRemoveSubImages"
              :before-upload="beforeAvatarUpload" :http-request="upload"
              list-type="picture-card" >
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
				</el-form-item>
        <el-form-item label="详情图片（暂无显示区域，随机上传一张图片即可）" prop="detailImagesFile" class="detailImagesFileItem">
          <el-upload
              ref="multipleUpload"
              class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="detailImagesUpload"
              :on-remove="handleRemoveDetailImages"
              :before-upload="beforeAvatarUpload" :http-request="upload"
              list-type="picture-card" >
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
				</el-form-item>
        
        <!-- <el-form-item label="规格库存" prop="">
          <el-form class="standForm">
              <el-form-item v-for="item in editForm.standData" :key="item.id" :prop="'item.'+index+'.value'">
                <el-input placeholder="规格" v-model="item.stand" ></el-input>
                <el-input placeholder="库存" v-model="item.num"></el-input>
                <el-button  @click.prevent="removeDomain(item)">删除</el-button>
                <el-button @click="addDomain">新增</el-button>
              </el-form-item>
          </el-form>
				</el-form-item> -->
			</el-form>
      <!-- <div>
        <div class="el-form-item__label" style="width: 150px;">
          规格库存
        </div>
        <div style="display: inline-block;">
          <el-form class="standForm" style="width: 100%;">
              <el-form-item v-for="item in editForm.standData" :key="item.id">
                <el-input placeholder="规格" v-model="item.stand"></el-input>
                <el-input placeholder="库存" v-model="item.num"></el-input>
                <el-button  @click.prevent="removeDomain()">删除</el-button>
                <el-button @click="addDomain">新增</el-button>
              </el-form-item>
          </el-form>
        </div>
      </div> -->
      <!-- <el-button @click="goBack">取消</el-button>
      <el-button @click="saveGoodsMsg">保存</el-button> -->
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
        mainImageFile: [], // 主图
        subImagesFile: [], // 介绍图
        detailImagesFile: [] // 详情图
      },
      editFormRules: {
        name: [{ required: true, message: '请输入商品名称', trigger: 'blur' }],
        menuId: [{ required: true, message: '请选择商品类别', trigger: 'change' }],
        price: [{ required: true, message: '请输入一口价', trigger: 'blur' }],
        salePrice: [{ required: true, message: '请输入折扣价', trigger: 'blur' }],
        packingCharges: [{ required: true, message: '请输入包装费', trigger: 'blur' }],
        productTime: [{ required: true, message: '请输入制作时长', trigger: 'blur' }],        
        status: [{ required: true, message: '请选择状态', trigger: 'blur' }],
        // returnprice: [{ required: true, message: '请输入返回HCT', trigger: 'blur' }],
        detail: [{ required: true, message: '请填写商品描述', trigger: 'blur' }],
        mainImageFile: [{ type: 'array', required: true, message: '请上传主图', trigger: 'change' }],
        subImagesFile: [{type: 'array', required: true, message: '请上传介绍图片', trigger: 'change' }],
        detailImagesFile: [{type: 'array', required: true, message: '请上传详情图片', trigger: 'change' }]
      }
    }
  },
  methods: {
    saveGoodsMsg() {
     	this.$refs.editForm.validate((valid) => {
        if (valid) {
          let vue = this
          if(!vue.editForm.mainImageFile.length) {
            vue.$message({
              showClose: true,
              message: '请上传商品主图',
              type: 'error'
            });
            return false
          }else if(vue.editForm.mainImageFile.length > 1) {
            vue.$message({
              showClose: true,
              message: '只能上传一张商品主图',
              type: 'error'
            });
            return false
          }
          if(!vue.editForm.subImagesFile.length) {
            vue.$message({
              showClose: true,
              message: '请上传介绍图片',
              type: 'error'
            });
            return false
          }
          if(!vue.editForm.detailImagesFile.length) {
            vue.$message({
              showClose: true,
              message: '请上传详情图片',
              type: 'error'
            });
            return false
          }
          let param = Object.assign({}, this.editForm);

          param.mainImage = vue.getIdByArr(param.mainImageFile)
          param.subImages =  vue.getIdByArr(param.subImagesFile)
          param.detailImages =  vue.getIdByArr(param.detailImagesFile)
          delete param.mainImageFile
          delete param.subImagesFile
          delete param.detailImagesFile
          let url = ''
          param.id ? url = '/rest/admin/goods/update' : url = '/rest/admin/goods/insert'
          vue.$http.post( vue, url, param,
            (vue, data) => {
              vue.$message({
                showClose: true,
                message: data.message,
                type: 'success'
              });
              vue.goBack()
            }, (error, data) => {
              vue.$message({
                showClose: true,
                message: data.message,
                type: 'error'
              });
            }
          )
        }
      });
    },
    goBack() {
      this.$refs['editForm'].resetFields();
      this.$router.push({path: 'goodsList'})
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
				vue.$http.post(vue, '/rest/admin/menu/list', param,
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
    handleRemoveMainImage(file, filelist) {
      let arr = this.editForm.mainImageFile
      this.editForm.mainImageFile = arr.filter(function(item) {
        return item.uid !== file.uid
      });
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
            vue.editForm.mainImageFile.push(obj)
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

