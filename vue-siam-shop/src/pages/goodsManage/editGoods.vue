<template>
  <section>
    <el-form
      :model="editForm"
      label-width="150px"
      class="editForm"
      style="width: 80%"
      :rules="editFormRules"
      ref="editForm"
    >
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>
      <el-form-item label="商品类别" prop="menuId">
        <el-select v-model="editForm.menuId" class="minInput">
          <el-option
            v-for="item in menuList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
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
        <el-input v-model="editForm.price" class="minInput" type="number"></el-input>
      </el-form-item>

      <!-- <el-form-item label="折扣价(元)" prop="salePrice">
					<el-input v-model="editForm.salePrice" class="minInput"></el-input>
				</el-form-item>         -->

      <el-form-item label="包装费" prop="packingCharges">
        <el-input v-model="editForm.packingCharges" class="minInput" type="number"></el-input>
        <!-- <el-select v-model="editForm.packingCharges" class="minInput">
						<el-option label="1元" :value="1"></el-option>
						<el-option label="1.5元" :value="1.5"></el-option>
						<el-option label="2元" :value="2"></el-option>
						<el-option label="2.5元" :value="2.5"></el-option>                   
            <el-option label="3元" :value="3"></el-option>                   
            <el-option label="3.5元" :value="3.5"></el-option>                   
					</el-select> -->
      </el-form-item>

      <!-- <el-form-item label="制作时长(分钟)" prop="productTime">
					<el-input v-model="editForm.productTime" class="minInput"></el-input>
				</el-form-item>      -->

      <!-- <el-form-item label="兑换商品所需积分数量" prop="exchangePoints">
        <el-input v-model="editForm.exchangePoints" class="minInput"></el-input>
      </el-form-item> -->

      <!-- <el-form-item label="商品链接" prop="marketurl">
					<el-input v-model="editForm.marketurl"></el-input>
				</el-form-item> -->
      <el-form-item label="商品描述" prop="detail">
        <el-input
          type="textarea"
          :rows="6"
          v-model="editForm.detail"
        ></el-input>
      </el-form-item>
      <!-- <el-form-item label="关键字" prop="keywords">
					<el-input v-model="editForm.keywords"></el-input>
				</el-form-item> -->
      <!-- <el-form-item label="商品主图（宽400px * 高400px）" prop="mainImageFile" class="mainImageFileItem">
          <el-upload
              class="avatar-uploader" accept=".image, .png, .jpg"
              action="" multiple name="mainImageUpload"
              :on-remove="handleRemoveMainImage"
              :file-list="editForm.mainImageFile"
              :before-upload="beforeAvatarUpload" :http-request="upload"
              list-type="picture-card">
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
				</el-form-item> -->

      <el-form-item
        label="介绍图片/商品详情页轮播图（宽600px * 高600px）"
        prop="subImagesFile"
        class="subImagesFileItem"
      >
        <el-upload
          ref="multipleUpload"
          class="avatar-uploader"
          action=""
          accept=".image, .png, .jpg"
          multiple
          name="subImagesUpload"
          :on-remove="handleRemoveSubImages"
          :before-upload="beforeAvatarUpload"
          list-type="picture-card"
          :file-list="editForm.subImagesFile"
          :auto-upload="false"
          :on-change="
            (file, fileList) => {
              changeUpload(file, fileList, 'subImagesUpload');
            }
          "
          :on-preview="
            (file, fileList) => {
              handlePictureCardPreview(file, fileList, 'subImagesUpload');
            }
          "
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <!-- <el-form-item label="详情图片（暂无显示区域，随机上传一张图片即可）" prop="detailImagesFile" class="detailImagesFileItem">
          <el-upload
              ref="multipleUpload"
              :file-list="editForm.detailImagesFile"
              class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="detailImagesUpload"
              :on-remove="handleRemoveDetailImages"
              :before-upload="beforeAvatarUpload" :http-request="upload"
              list-type="picture-card" >
              <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
				</el-form-item> -->

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

      <el-form-item label="后厨打印机" prop="printerId">
        <el-select v-model="editForm.printerId" class="minInput" multiple>
          <el-option
            v-for="item in printerList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="打印次数" prop="printNum">
        <el-input v-model="editForm.printNum" class="minInput" type="number"></el-input>
      </el-form-item>
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
    <div>
      <!-- accept="image/gif, image/jpeg,image/jpg,image/png,image/bmp" -->
      <!-- vueCropper 剪裁图片实现-->
      <el-dialog
        title="图片裁减"
        :visible.sync="dialogVisible"
        append-to-body
        top="10vh"
        @close="beforeClose"
      >
        <div class="cropper-content">
          <div class="cropper" style="text-align: center">
            <vueCropper
              ref="cropper"
              :img="option.img"
              :outputSize="option.size"
              :outputType="option.outputType"
              :info="true"
              :full="option.full"
              :autoCropHeight="option.autoCropHeight"
              :autoCropWidth="option.autoCropWidth"
              :canMove="option.canMove"
              :canMoveBox="option.canMoveBox"
              :original="option.original"
              :autoCrop="option.autoCrop"
              :fixed="option.fixed"
              :fixedNumber="option.fixedNumber"
              :centerBox="option.centerBox"
              :infoTrue="option.infoTrue"
              :fixedBox="option.fixedBox"
              :high="option.high"
              @realTime="realTime"
            ></vueCropper>
          </div>
        </div>
        <div
          v-if="isReduces"
          style="
             {
              width: 100%;
              display: flex;
              align-items: center;
              justify-content: center;
            }
          "
        >
          <div
            class="show-preview"
            :style="{
              width: previews.w + 'px',
              height: previews.h + 'px',
              overflow: 'hidden',
              margin: '5px 5px 5px 15px',
            }"
          >
            <div :style="previews.div">
              <img
                :src="previews.url"
                :style="previews.img"
                :preview-src-list="[previews.url]"
                title="裁减预览"
                @click="previewsView('base64')"
              />
            </div>
          </div>
        </div>
        <div
          class="preview-model"
          v-show="previewsModel"
          @click="previewsModel = false"
        >
          <div class="preview-model-show">
            <img :src="previewsModelSrc" alt="" />
          </div>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="(dialogVisible = false), (loading = false)" v-if="!isReduces"
            >取 消</el-button
          >
          <!-- <el-button type="success" @click="refreshCrop">刷新</el-button> -->
          <el-button
            type="success"
            @click="isReduces ? bindReduces() : bindPreviews()"
            >{{ isReduces ? "取消裁减" : "查看大图" }}</el-button
          >
          
          <!-- <el-button type="primary" @click="bindReduces" v-if="!isReduces"
            >裁减</el-button
          >
          <el-button
            type="primary"
            @click="finish"
            :loading="loading"
            v-else-if="isReduces"
            >确认裁减</el-button
          > -->
          <el-upload
            action=""
            class="upload-demo"
            :on-remove="handleRemoveSubImages"
            :before-upload="beforeAvatarUpload"
            :auto-upload="false"
            :file-list="refileList"
            :on-change="
              (file, fileList) => {
                changeReUpload(file, fileList, 'subImagesUpload');
              }
            "
            style="display:inline;margin-left:10px;"
          >
            <el-button type="danger">重新上传</el-button>
          </el-upload>
        </div>
      </el-dialog>
    </div>
    <!-- <el-dialog :visible.sync="dialogVisible">
  <img width="100%" :src="dialogImageUrl" alt="">
</el-dialog> -->
  </section>
</template>
<script>
export default {
  props: {
    pic: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      printerList: [],
      menuList: [],
      refileList:[],
      editForm: {
        mainImageFile: [], // 主图
        subImagesFile: [], // 介绍图
        detailImagesFile: [], // 详情图
      },
      editFormRules: {
        name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
        menuId: [
          { required: true, message: "请选择商品类别", trigger: "change" },
        ],
        price: [
          { required: true, message: "请输入一口价(元)", trigger: "blur" },
        ],
        salePrice: [
          { required: true, message: "请输入折扣价(元)", trigger: "blur" },
        ],
        packingCharges: [
          { required: true, message: "请输入包装费", trigger: "blur" },
        ],
        productTime: [
          { required: true, message: "请输入制作时长", trigger: "blur" },
        ],
        exchangePoints: [
          {
            required: true,
            message: "请输入兑换商品所需积分数量",
            trigger: "blur",
          },
        ],
        status: [{ required: true, message: "请选择状态", trigger: "blur" }],
        //detail: [{ required: true, message: '请填写商品描述', trigger: 'blur' }],
        // keywords: [{ required: false, message: '请填写关键字', trigger: 'blur' }],
        mainImageFile: [
          {
            type: "array",
            required: true,
            message: "请上传主图",
            trigger: "change",
          },
        ],
        subImagesFile: [
          {
            type: "array",
            required: true,
            message: "请上传介绍图片",
            trigger: "change",
          },
        ],
        detailImagesFile: [
          {
            type: "array",
            required: true,
            message: "请上传详情图片",
            trigger: "change",
          },
        ],
      },
      action: "上传地址",
      disabled: false,
      uploadData: {
        file: "",
        project: "",
      },
      imageUrl: "",
      loading: false,
      maskBox: false,
      dialogVisible: false,
      lockScroll: false,
      // 裁剪组件的基础配置option
      option: {
        img: "", // 裁剪图片的地址
        info: true, // 裁剪框的大小信息
        outputSize: 0.8, // 裁剪生成图片的质量
        outputType: "jpeg", // 裁剪生成图片的格式
        canScale: false, // 图片是否允许滚轮缩放
        autoCrop: false, // 是否默认生成截图框
        autoCropWidth: 600, // 默认生成截图框宽度
        autoCropHeight: 355, // 默认生成截图框高度
        fixedBox: false, // 固定截图框大小 不允许改变
        fixed: true, // 是否开启截图框宽高固定比例
        fixedNumber: [1, 1], // 截图框的宽高比例
        full: true, // 是否输出原图比例的截图
        canMoveBox: true, // 截图框能否拖动
        original: false, // 上传图片按照原始比例渲染
        centerBox: true, // 截图框是否被限制在图片里面
        infoTrue: true, // true 为展示真实输出图片宽高 false 展示看到的截图框宽高
        high: true,
      },
      picsList: [], //页面显示的数组
      previews: {},
      previewsModel: false,
      previewsModelSrc: "",
      isReduces: false,
    };
  },
  methods: {
    // 设置头像base64
    setAvatarBase64(src, callback) {
      let _this = this;
      let image = new Image();
      console.log(1);
      // 处理缓存
      image.src = src + "?v=" + Math.random();
      console.log(2);
      // 支持跨域图片
      image.crossOrigin = "*";
      image.onload = function () {
        let base64 = _this.transBase64FromImage(image);
        callback && callback(base64);
      };
      console.log(2.1);
    },
    // 将网络图片转换成base64格式
    transBase64FromImage(image) {
      let canvas = document.createElement("canvas");
      canvas.width = image.width;
      canvas.height = image.height;
      let ctx = canvas.getContext("2d");
      ctx.drawImage(image, 0, 0, image.width, image.height);
      // 可选其他值 image/jpeg
      return canvas.toDataURL("image/png");
    },
    saveGoodsMsg() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          let vue = this;
          // if(!vue.editForm.mainImageFile.length) {
          //   vue.$message({
          //     showClose: true,
          //     message: '请上传商品主图',
          //     type: 'error'
          //   });
          //   return false
          // }else if(vue.editForm.mainImageFile.length > 1) {
          //   vue.$message({
          //     showClose: true,
          //     message: '只能上传一张商品主图',
          //     type: 'error'
          //   });
          //   return false
          // }
          if (!vue.editForm.subImagesFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传介绍图片",
              type: "error",
            });
            return false;
          }
          // if(!vue.editForm.detailImagesFile.length) {
          //   vue.$message({
          //     showClose: true,
          //     message: '请上传详情图片',
          //     type: 'error'
          //   });
          //   return false
          // }
          let param = Object.assign({}, this.editForm);
          delete param.createTime;
          delete param.updateTime;

          param.mainImage = vue.getIdByArr(param.mainImageFile);
          param.subImages = vue.getIdByArr(param.subImagesFile);
          // param.detailImages =  vue.getIdByArr(param.detailImagesFile)
          delete param.mainImageFile;
          delete param.subImagesFile;
          // delete param.detailImagesFile

          if(this.editForm.printerId){
            param.printerId = this.editForm.printerId.join(',');
          }

          let url = "";
          param.id
            ? (url = "/rest/merchant/goods/update")
            : (url = "/rest/merchant/goods/insert");
          vue.$http.post(
            vue,
            url,
            param,
            (vue, data) => {
              vue.$message({
                showClose: true,
                message: data.message,
                type: "success",
              });
              vue.goBack();
            },
            (error, data) => {
              vue.$message({
                showClose: true,
                message: data.message,
                type: "error",
              });
            }
          );
        }
      });
    },
    goBack() {
      this.$refs["editForm"].resetFields();
      this.$router.push({ path: "goodsList" });
    },
    getIdByArr(data) {
      let arr = data || [];
      let url = arr.map((item) => {
        return item.oldUrl;
      });
      return url.join(",");
    },
    removeDomain(item) {
      var index = this.editForm.standData.indexOf(item);
      if (index !== -1) {
        this.editForm.standData.splice(index, 1);
      }
    },
    addDomain() {
      this.editForm.standData.push({
        stand: "",
        stock: "",
      });
    },
    getDetail(id) {
      // 获取商品详情
      let vue = this;
      vue.$http.post(
        vue,
        "/rest/merchant/goods/getById",
        { id },
        (vue, data) => {
          let obj = data.data;
          obj.mainImageFile = vue.resetImg(obj, "mainImage");
          obj.subImagesFile = vue.resetImg(obj, "subImages");
          // obj.detailImagesFile =  vue.resetImg(obj, 'detailImages')
          if(obj.printerId){
            obj.printerId = obj.printerId.split(',').map(Number);
          }
          vue.editForm = Object.assign({}, obj);
          console.log(vue.editForm)
        },
        (error, data) => {
          vue.$message({
            showClose: true,
            message: data.message,
            type: "error",
          });
        }
      );
    },
    resetImg(row, imgType) {
      let data = row;
      if (row[imgType]) {
        let arr = row[imgType].split(",");
        let newArr = [];
        let index = 0;
        arr.forEach((ele) => {
          let obj = {
            url: this.$http.publicUrl(ele),
            oldUrl: ele,
            name: index + 1,
          };
          newArr.push(obj);
          index++;
        });
        return newArr;
      } else {
        return [];
      }
    },
    getPrinterList() {
      // 获取商品类别列表
      let vue = this;
      let param = {
        pageNo: -1,
        pageSize: 10,
        type: 1,
      };
      vue.$http.post(
        vue,
        "/rest/merchant/printer/list",
        param,
        (vue, data) => {
          vue.printerList = data.data.records;
        },
        (error, data) => {
          vue.$message({
            showClose: true,
            message: data.message,
            type: "error",
          });
        }
      );
    },
    getTypeList() {
      // 获取商品类别列表
      let vue = this;
      let param = {
        pageNo: -1,
        pageSize: 10,
        typestatus: 0,
      };
      vue.$http.post(
        vue,
        "/rest/merchant/menu/list",
        param,
        (vue, data) => {
          vue.menuList = data.data.records;
        },
        (error, data) => {
          vue.$message({
            showClose: true,
            message: data.message,
            type: "error",
          });
        }
      );
    },
    handleRemoveMainImage(file, filelist) {
      let arr = this.editForm.mainImageFile;
      this.editForm.mainImageFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveSubImages(file, filelist) {
      let arr = this.editForm.subImagesFile;
      this.editForm.subImagesFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveDetailImages(file, filelist) {
      let arr = this.editForm.detailImagesFile;
      this.editForm.detailImagesFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message({
          showClose: true,
          message: "上传图片大小不能超过 2MB!",
          type: "error",
        });
      }
      return isLt2M;
    },
    // 上传按钮
    changeUpload(file, fileList, name, index) {
      console.log(file);
      console.log(fileList);
      console.log(name);
      console.log(index);
      this.fileinfo = file;
      this.filename = name;
      if (fileList.length == 1) {
        if (name == "subImagesUpload") {
          this.option.fixedNumber = [1, 1];
        }
      } else {
        this.option.fixedNumber = [9, 5.5];
      }
      if (name == "mainImageUpload") {
        this.option.fixedNumber = [1, 1];
      }
      //上传
      this.upload(file, name);
    },
    // 重新上传按钮
    changeReUpload(file, fileList, name, index) {
      console.log(file);
      console.log(fileList);
      console.log(name);
      console.log(index);
      this.fileinfo = file;
      this.filename = name;
      if (this.editForm.subImagesFile.length == 1) {
        if (name == "subImagesUpload") {
          this.option.fixedNumber = [1, 1];
        }
      } else {
        this.option.fixedNumber = [9, 5.5];
      }
      if (name == "mainImageUpload") {
        this.option.fixedNumber = [1, 1];
      }
      //上传
      // this.reUpload(file, fileList,name);
      this.reUpload(file, fileList,name);
    },
    reUpload(option, fileList, filename, index) {
      const loading = this.$loading({
          lock: true,
          text: '上传中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
      console.log("upload----图片重新上传");
      console.log(option);
      // 图片上传
      let _this = this;
      let value = option.raw;
      let formData = new FormData();
      formData.append("file", value);
      console.log(formData);
      _this.$http.postupload(
        _this,
        "/rest/merchant/uploadSingleImage",
        formData,
        function (_this, data) {
          if (data.success) {
            let obj = {
              url: option.url,
              uid: value.uid,
              upUrl: data.data,
              oldUrl: data.data,
              name:option.name
            };
            if (filename === "subImagesUpload") {
              if (_this.previewFile) {
                  console.log(
                    "商品详情页值和长度",
                    _this.editForm.subImagesFile,
                    _this.editForm.subImagesFile.length
                  );
                  if (_this.editForm.subImagesFile.length <= 0) {
                    _this.$set(_this.editForm.subImagesFile, 0, obj);
                  } else {
                    let is_existence = false;
                    _this.editForm.subImagesFile.forEach((element, index) => {
                      console.log("选中裁减的图片", _this.previewFile.uid);
                      if (_this.previewFile.uid == element.uid) {
                        is_existence = true;
                        console.log("是这个");
                        console.log("等于选中的图片uid", element.uid);
                        _this.$set(_this.editForm.subImagesFile, index, obj);
                      }
                    });
                    if (!is_existence) {
                      _this.editForm.subImagesFile.push(obj);
                    }
                  }
                } else {
                  _this.editForm.subImagesFile.push(obj);
                }
                console.log("上传后的图片列表", _this.editForm.subImagesFile);
            } else if (filename === "detailImagesUpload") {
              if (_this.previewFile) {
                  _this.editForm.detailImagesFile.forEach((element, index) => {
                    console.log("选中裁减的图片", _this.previewFile.uid);
                    if (_this.previewFile.uid == element.uid) {
                      console.log("是这个");
                      console.log("等于选中的图片uid", element.uid);
                      console.log("裁减后更新的图片", obj);
                      _this.$set(_this.editForm.detailImagesFile, index, obj);
                    }
                  });
                } else {
                  _this.editForm.detailImagesFile.push(obj);
                }
            } else {
              if (_this.previewFile) {
                  if (_this.editForm.mainImageFile.length <= 0) {
                    _this.$set(_this.editForm.mainImageFile, 0, obj);
                  } else {
                    let is_existence = false;
                    _this.editForm.mainImageFile.forEach((element, index) => {
                      console.log("选中裁减的图片", _this.previewFile.uid);
                      console.log("选中裁减的图片", element.uid);
                      if (_this.previewFile.uid == element.uid) {
                        console.log("是这个");
                        console.log("等于选中的图片uid", element.uid);
                        console.log("裁减后更新的图片", obj);
                        is_existence = true;
                        _this.$set(_this.editForm.mainImageFile, index, obj);
                      }
                    });
                    if (!is_existence) {
                      _this.editForm.mainImageFile.push(obj);
                    }
                  }
                } else {
                  _this.editForm.mainImageFile.push(obj);
                }
            }
            _this.$message({
              message: data.message,
              type: "success",
            });
            obj.url=_this.$http.publicUrl(data.data);
            console.log(obj)
            _this.refileList=[obj];
            _this.handlePictureCardPreview(obj, fileList, filename, index);
            //上传成功后将图片地址赋值给裁剪框显示图片
            // _this.option.img = obj.url;
            // _this.dialogVisible = true;
            // _this.previewFile = value;
          }
          loading.close();
        },
        function (error) {
          console.log(error);
        }
      );
    },
    upload(option, filename) {
      const loading = this.$loading({
          lock: true,
          text: '上传中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
      console.log("upload----图片上传");
      console.log(option);
      // 图片上传
      let vue = this;
      let value = option.raw;
      let formData = new FormData();
      formData.append("file", value);
      console.log(formData);
      vue.$http.postupload(
        vue,
        "/rest/merchant/uploadSingleImage",
        formData,
        function (vue, data) {
          if (data.success) {
            let obj = {
              url: option.url,
              uid: value.uid,
              upUrl: data.data,
              oldUrl: data.data,
            };
            if (filename === "subImagesUpload") {
              vue.editForm.subImagesFile.push(obj);
            } else if (filename === "detailImagesUpload") {
              vue.editForm.detailImagesFile.push(obj);
            } else {
              vue.editForm.mainImageFile.push(obj);
            }
            vue.$message({
              message: data.message,
              type: "success",
            });
          }
          loading.close();
          // 上传成功后将图片地址赋值给裁剪框显示图片
          // vue.option.img = obj.url;
          // vue.dialogVisible = true;
          // vue.previewFile = value;
        },
        function (error) {
          console.log(error);
        }
      );
    },
    //预览裁减框
    handlePictureCardPreview(file, fileList, name, index) {
      console.log("=============== handlePictureCardPreview ==============");
      console.log(file);
      console.log(fileList);
      console.log(name);
      console.log(index);
      let _this = this;

      //额外补充
      if (!file.hasOwnProperty("upUrl")) {
        //由于跨域问题，需要将图片先转换为base64格式，再进行赋值展示
        console.log(
          "由于跨域问题，需要将图片先转换为base64格式，再进行赋值展示"
        );
        this.setAvatarBase64(file.url, (base64) => {
          _this.option.img = base64;
        });
        let fileName = JSON.stringify(file.name);
        if (fileName.indexOf(".") == -1) {
          file.name = file.name + ".png";
        }
      } else {
        _this.option.img = file.url;
        file.name = file.uid + ".png";
      }
      this.dialogVisible = true;
      this.fileinfo = file;
      this.filename = name;
      this.previewFile = file;
    },
    refreshCrop() {
      // 重置裁剪框
      this.$refs.cropper.refresh();
    },
    // 实时预览裁减的图片
    realTime(e) {
      console.log(e);
      let _this = this;
      _this.previews = e;
      this.$refs.cropper.getCropBlob((data) => {
        console.log(data);
        let img = window.URL.createObjectURL(data);
        console.log("操作后的实时路径", img);
        // if (_this.previewFile) {
        //   var list = [];
        //   if (_this.filename == "subImagesUpload") {
        //     list = _this.editForm.subImagesFile;
        //   }
        //   if (_this.filename == "mainImageUpload") {
        //     list = _this.editForm.mainImageFile;
        //   }
        //   list.forEach((element, index) => {
        //     console.log("选中裁减的图片", _this.previewFile.uid);
        //     if (_this.previewFile.uid == element.uid) {
        //       console.log("是这个");
        //       console.log("等于选中的图片uid", element.uid);
        //       let obj = element;
        //       obj.url = img;
        //       console.log("裁减后更新的图片", obj);
        //       if (_this.filename == "subImagesUpload") {
        //         _this.$set(_this.editForm.subImagesFile, index, obj);
        //       }
        //       if (_this.filename == "mainImageUpload") {
        //         _this.$set(_this.editForm.mainImageFile, index, obj);
        //       }
        //     }
        //   });
        // }
      });
    },
    beforeClose() {
      console.log("点击了取消");
      this.isReduces = false;
      this.option.autoCrop = false;
      this.refreshCrop();
    },
    bindReduces() {
      this.isReduces = this.isReduces ? false : true;
      this.option.autoCrop = this.option.autoCrop ? false : true;
      this.refreshCrop();
    },
    bindPreviews() {
      console.log("查看原图");
      console.log(this.option.img);
      this.previewsModelSrc = this.option.img;
      this.previewsModel = true;
    },
    // 点击确认裁剪，这一步是可以拿到处理后的地址并上传服务器
    finish(option) {
      let _this = this;
      this.$refs.cropper.getCropBlob((data) => {
        let img = window.URL.createObjectURL(data);
        console.log("裁减后的照片路径", img);
        this.loading = true;
        // debugger
        console.log(this.fileinfo);
        var file_ = new File([data], this.fileinfo.name, {
          type: "image/jpeg",
          lastModified: Date.now(),
        });
        let formData = new FormData();
        console.log("获取裁减后的照片：", file_);
        formData.append("file", file_);
        _this.$http.postupload(
          _this,
          "/rest/merchant/uploadSingleImage",
          formData,
          function (_this, data) {
            console.log("============== data ==============");
            console.log(data);
            if (data.success) {
              console.log(data.success);
              let obj = {
                url: img,
                uid: file_.lastModified,
                upUrl: data.data,
                oldUrl: data.data,
              };
              console.log("裁减后更新的图片", obj);
              console.log(_this.previewFile);
              console.log(_this.filename);
              if (_this.filename === "subImagesUpload") {
                if (_this.previewFile) {
                  console.log(
                    "商品详情页值和长度",
                    _this.editForm.subImagesFile,
                    _this.editForm.subImagesFile.length
                  );
                  if (_this.editForm.subImagesFile.length <= 0) {
                    _this.$set(_this.editForm.subImagesFile, 0, obj);
                  } else {
                    let is_existence = false;
                    _this.editForm.subImagesFile.forEach((element, index) => {
                      console.log("选中裁减的图片", _this.previewFile.uid);
                      if (_this.previewFile.uid == element.uid) {
                        is_existence = true;
                        console.log("是这个");
                        console.log("等于选中的图片uid", element.uid);
                        _this.$set(_this.editForm.subImagesFile, index, obj);
                      }
                    });
                    if (!is_existence) {
                      _this.editForm.subImagesFile.push(obj);
                    }
                  }
                } else {
                  _this.editForm.subImagesFile.push(obj);
                }
                console.log("上传后的图片列表", _this.editForm.subImagesFile);
              } else if (_this.filename === "detailImagesUpload") {
                if (_this.previewFile) {
                  _this.editForm.detailImagesFile.forEach((element, index) => {
                    console.log("选中裁减的图片", _this.previewFile.uid);
                    if (_this.previewFile.uid == element.uid) {
                      console.log("是这个");
                      console.log("等于选中的图片uid", element.uid);
                      console.log("裁减后更新的图片", obj);
                      _this.$set(_this.editForm.detailImagesFile, index, obj);
                    }
                  });
                } else {
                  _this.editForm.detailImagesFile.push(obj);
                }
              } else {
                if (_this.previewFile) {
                  if (_this.editForm.mainImageFile.length <= 0) {
                    _this.$set(_this.editForm.mainImageFile, 0, obj);
                  } else {
                    let is_existence = false;
                    _this.editForm.mainImageFile.forEach((element, index) => {
                      console.log("选中裁减的图片", _this.previewFile.uid);
                      console.log("选中裁减的图片", element.uid);
                      if (_this.previewFile.uid == element.uid) {
                        console.log("是这个");
                        console.log("等于选中的图片uid", element.uid);
                        console.log("裁减后更新的图片", obj);
                        is_existence = true;
                        _this.$set(_this.editForm.mainImageFile, index, obj);
                      }
                    });
                    if (!is_existence) {
                      _this.editForm.mainImageFile.push(obj);
                    }
                  }
                } else {
                  _this.editForm.mainImageFile.push(obj);
                }
              }
              _this.$message({
                message: data.message,
                type: "success",
              });
              _this.option.img = "";
              _this.dialogVisible = false;
              _this.loading = false;
              _this.previewFile = "";
            }
          },
          function (error) {
            _this.loading = false;
          }
        );
      });
    },
    //点击预览图片
    previewsView(type) {
      // 输出
      // var test = window.open('about:blank')
      // test.document.body.innerHTML = '图片生成中..'
      if (type === "blob") {
        this.$refs.cropper.getCropBlob((data) => {
          var img = window.URL.createObjectURL(data);
          this.previewsModel = true;
          this.previewsModelSrc = img;
        });
      } else {
        this.$refs.cropper.getCropData((data) => {
          this.previewsModel = true;
          this.previewsModelSrc = data;
        });
      }
    },
  },
  created() {
    this.getPrinterList();
    this.getTypeList();
    let id = this.$route.query.id;
    this.getDetail(id);
    //开启订单自动打印定时器
    this.$orderPrint.init();
    this.imageUrl = this.picUrl;
  },
  watch: {
    pic: {
      handler(newval, oldval) {
        console.log(newval);
        if (newval) {
          this.imageUrl = newval;
        }
      },
      deep: true,
    },
  },
};
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
  border-color: #409eff;
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

.cropper-content .cropper {
  width: auto;
  height: 300px;
}
.preview-model {
  position: fixed;
  z-index: 10;
  width: 100vw;
  height: 100vh;
  overflow: auto;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.8);
}
.preview-model-show {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100vw;
  height: 100vh;
}
</style>

