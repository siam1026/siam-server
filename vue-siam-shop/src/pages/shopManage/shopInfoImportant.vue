<style scoped>
#map {
  height: 300px;
  width: 100%;
}
</style>
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
      <el-form-item label="门店名称" prop="name">
        <el-input v-model="editForm.name"></el-input>
      </el-form-item>

      <!-- <el-form-item label="外卖电话" prop="takeOutPhone">
					<el-input v-model="editForm.takeOutPhone"></el-input>
				</el-form-item> -->
      <el-form-item prop="contactPhone" label="联系电话">
        <el-input
          type="text"
          v-model="editForm.contactPhone"
          auto-complete="off"
          placeholder="请填写联系电话"
        ></el-input>
      </el-form-item>

      <!-- <el-form-item prop="selectedOptions" label="所在城市">
        <el-cascader
          style="width: 100%"
          :options="options"
          separator="-"
          :props="props"
          clearable
          change-on-select
          placeholder="请选择所在城市"
          v-model="editForm.selectedOptions"
        >
        </el-cascader>
      </el-form-item> -->

      <el-form-item prop="street" label="门店地址">
        <el-input
          type="text"
          style="width: 52%"
          v-model="editForm.street"
          auto-complete="off"
          placeholder="请输入门店地址"
          suffix-icon="el-icon-position"
          @input="handleSearch"
          @blur="handleBlur"
          id="suggestId"
        ></el-input>
        门牌号
        <el-input
          type="text"
          style="width: 40%"
          v-model="editForm.houseNumber"
          auto-complete="off"
          placeholder="请输入门牌号"
        ></el-input>
         <div id="searchResultPanel" style="
          border: 1px solid #c0c0c0;
          width: 150px;
          display: none;"></div>
          <div id="l-map" :style="'height:300px;width:100%;'"></div>
      </el-form-item>

      <el-form-item label="门店简介" prop="briefIntroduction">
        <el-input
          type="textarea"
          :rows="6"
          v-model="editForm.briefIntroduction"
        ></el-input>
      </el-form-item>

      <el-form-item prop="managePrimary" label="营业类目">
        <el-input
          type="text"
          v-model="editForm.managePrimary"
          auto-complete="off"
          placeholder="请输入营业类目"
        ></el-input>
      </el-form-item>

      <el-form-item
        label="门店头像（宽400px * 高400px）"
        prop="shopLogoImgFile"
        class="shopLogoImgFileItem"
      >
        <el-upload
          class="avatar-uploader"
          accept=".image, .png, .jpg"
          action=""
          multiple
          name="shopLogoImgUpload"
          :on-remove="handleRemoveShopLogoImg"
          :file-list="editForm.shopLogoImgFile"
          :before-upload="beforeAvatarUpload"
          :http-request="upload"
          list-type="picture-card"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item
        label="店内照片（宽600px * 高355px）"
        prop="shopWithinImgFile"
        class="shopWithinImgFileItem"
      >
        <el-upload
          ref="multipleUpload"
          :file-list="editForm.shopWithinImgFile"
          class="avatar-uploader"
          action=""
          accept=".image, .png, .jpg"
          multiple
          name="shopWithinImgUpload"
          :on-remove="handleRemoveShopWithinImg"
          :before-upload="beforeAvatarUpload"
          :http-request="upload"
          list-type="picture-card"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item
        label="营业执照"
        prop="businessLicenseFile"
        class="businessLicenseFileItem"
      >
        <el-upload
          ref="multipleUpload"
          :file-list="editForm.businessLicenseFile"
          class="avatar-uploader"
          action=""
          accept=".image, .png, .jpg"
          multiple
          name="businessLicenseUpload"
          :on-remove="handleRemoveBusinessLicense"
          :before-upload="beforeAvatarUpload"
          :http-request="upload"
          list-type="picture-card"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item
        label="身份证正面"
        prop="idCardFrontSideFile"
        class="idCardFrontSideFileItem"
      >
        <el-upload
          ref="multipleUpload"
          :file-list="editForm.idCardFrontSideFile"
          class="avatar-uploader"
          action=""
          accept=".image, .png, .jpg"
          multiple
          name="idCardFrontSideUpload"
          :on-remove="handleRemoveIdCardFrontSide"
          :before-upload="beforeAvatarUpload"
          :http-request="upload"
          list-type="picture-card"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item
        label="身份证反面"
        prop="idCardBackSideFile"
        class="idCardBackSideFileItem"
      >
        <el-upload
          ref="multipleUpload"
          :file-list="editForm.idCardBackSideFile"
          class="avatar-uploader"
          action=""
          accept=".image, .png, .jpg"
          multiple
          name="idCardBackSideUpload"
          :on-remove="handleRemoveIdCardBackSide"
          :before-upload="beforeAvatarUpload"
          :http-request="upload"
          list-type="picture-card"
        >
          <i class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
    </el-form>
    
    <div slot="footer" class="el-dialog__footer" style="text-align: center">
      <el-button type="primary" @click="saveShopMsg">提交</el-button>
    </div>
  </section>
</template>

<script>
import cityData from "../../../static/citys.json";
import { MP } from "../../utils/bmap.js";
var map = null;
export default {
  data() {
    return {
      options: cityData,
      props: {
        value: "label",
        children: "children",
      },
      menuList: [],
      editForm: {
        name: "",
        // selectedOptions: [],

        province: "",
        city: "",
        area: "",

        street: "",
        houseNumber: "",
        managePrimary: "",
        shopImg: "",
        shopWithinImg: "",
        shopLogoImg: "",
        businessLicense: "",
        idCardFrontSide: "",
        idCardBackSide: "",
        takeOutPhone: "",
        contactRealname: "",
        contactPhone: "",
        shopLogoImgFile: [], // 主图
        shopWithinImgFile: [], // 介绍图
        detailImagesFile: [], // 详情图
        businessLicenseFile: [], // 介绍图
        idCardFrontSideFile: [], // 介绍图
        idCardBackSideFile: [], // 介绍图
        isOperating: "正常营业",
        startTime: "8:00",
        endTime: "19:30",
        announcement: "",
        briefIntroduction: "",
        points : ""
      },
      editFormRules: {
        name: [{ required: true, message: "请输入门店名称", trigger: "blur" }],
        // selectedOptions: [
        //   {
        //     type: "array",
        //     required: true,
        //     message: "请选择所在城市",
        //     trigger: "change",
        //   },
        // ],
        street: [
          { required: true, message: "请输入并选择下拉匹配地址", trigger: "blur" },
        ],
        managePrimary: [
          { required: true, message: "请选择主营类目", trigger: "blur" },
        ],
        takeOutPhone: [
          { required: true, message: "请输入外卖电话", trigger: "blur" },
        ],
        contactPhone: [
          { required: true, message: "请输入联系电话", trigger: "blur" },
        ],
        announcement: [
          { required: true, message: "请输入门店公告", trigger: "blur" },
        ],
        briefIntroduction: [
          { required: true, message: "请输入门店简介", trigger: "blur" },
        ],
        isOperating: [
          { required: true, message: "请选择店铺是否营业", trigger: "blur" },
        ],
        startTime: [
          {
            required: true,
            message: "请输入店铺营业开始时间",
            trigger: "blur",
          },
        ],
        endTime: [
          {
            required: true,
            message: "请输入店铺营业结束时间",
            trigger: "blur",
          },
        ],
        shopLogoImgFile: [
          {
            type: "array",
            required: true,
            message: "请上传主图",
            trigger: "change",
          },
        ],
        shopWithinImgFile: [
          {
            type: "array",
            required: true,
            message: "请上传店内照片",
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
        businessLicenseFile: [
          {
            type: "array",
            required: true,
            message: "请上传营业执照",
            trigger: "change",
          },
        ],
        idCardFrontSideFile: [
          {
            type: "array",
            required: true,
            message: "请上传身份证正面照片",
            trigger: "change",
          },
        ],
        idCardBackSideFile: [
          {
            type: "array",
            required: true,
            message: "请上传身份证反面照片",
            trigger: "change",
          },
        ],
      }
    };
  },
  mounted() {
    this.$nextTick(function () {
      var _this = this;
      MP("GWT3y80ejryNBd0ed64opKGQe16UGOOx").then((BMap) => {
        //创建百度地图控件
        var geolocation = new BMap.Geolocation();
        geolocation.getCurrentPosition(
          function (r) {
            if (this.getStatus() == BMAP_STATUS_SUCCESS) {
              //以指定的经度与纬度创建一个坐标点
              var pt = r.point;
              //创建一个地理位置解析器
              var geoc = new BMap.Geocoder();
              geoc.getLocation(pt, function (rs) {
                _this.tableData = rs.surroundingPois;
                //解析格式：城市，区县，街道
                var addComp = rs.addressComponents;
                console.log("省市区街道1：" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street);
              });
            } else {
              console.log("定位失败");
            }
          },
          { enableHighAccuracy: true }
        ); //指示浏览器获取高精度的位置，默认false
      });
      setTimeout(function(){
        if(_this.editForm.city){
          console.log("省市区街道2：" + _this.editForm.province + ", " + _this.editForm.city + ", " + _this.editForm.area + ", " + _this.editForm.street);
          _this.setPlace(_this.editForm.city);
        }else{
          _this.bindGetLocation();
        }
      },500)
    });
  },
  created(){
  },
  methods: {
    // 百度地图API功能
    G(id) {
      return document.getElementById(id);
    },
    setPlace(city) {
      console.log("当前市",city);
      map = new BMap.Map("l-map");
      map.centerAndZoom(city, 12); // 初始化地图,设置城市和地图级别。
      map.clearOverlays(); //清除地图上所有覆盖物
      function myFun() {
        var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
        map.centerAndZoom(pp, 18);
        map.addOverlay(new BMap.Marker(pp)); //添加标注
      }
      var local = new BMap.LocalSearch(map, {
        //智能搜索
        onSearchComplete: myFun,
      });
      local.search(this.editForm.street);
    },
    getInit(city) {
      var _this=this;
      map = new BMap.Map("l-map");
      map.centerAndZoom(city, 12); // 初始化地图,设置城市和地图级别。

      var ac = new BMap.Autocomplete({ input: "suggestId", location: map}); //建立一个自动完成的对象

      ac.addEventListener("onhighlight", function (e) {
        //鼠标放在下拉列表上的事件
        var str = "";
        var _value = e.fromitem.value;
        var value = "";
        if (e.fromitem.index > -1) {
          value =
            _value.province +
            _value.city +
            _value.district +
            _value.street +
            _value.business;
        }
        str =
          "FromItem<br />index = " +
          e.fromitem.index +
          "<br />value = " +
          value;

        value = "";
        if (e.toitem.index > -1) {
          _value = e.toitem.value;
          value =
            _value.province +
            _value.city +
            _value.district +
            _value.street +
            _value.business;
        }
        str +=
          "<br />ToItem<br />index = " +
          e.toitem.index +
          "<br />value = " +
          value;
        _this.G("searchResultPanel").innerHTML = str;
      });

      var myValue;
      ac.addEventListener("onconfirm", function (e) {
        //鼠标点击下拉列表后的事件
        var _value = e.item.value;
        // alert("_value：" + _value)
        console.log(_value)
        myValue =
          _value.province +
          _value.city +
          _value.district +
          _value.street +
          _value.business;
        _this.G("searchResultPanel").innerHTML =
          "onconfirm<br />index = " +
          e.item.index +
          "<br />myValue = " +
          myValue;
        setPlace_();
      });
      function setPlace_() {
        map.clearOverlays(); //清除地图上所有覆盖物
        function myFun() {
          var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
          // alert(pp);

          console.log("pp~~~~~~~~~~~~");
          console.log(pp);

          _this.editForm.points=pp;
          _this.editForm.longitude=pp.lng;
          _this.editForm.latitude=pp.lat;
          
          map.centerAndZoom(pp, 18);
          map.addOverlay(new BMap.Marker(pp)); //添加标注
        }
        // alert("myValue：" + myValue)
        _this.editForm.street=myValue;
        var local = new BMap.LocalSearch(map, {
          //智能搜索
          onSearchComplete: myFun,
        });
        // alert("myValue2：" + myValue)
        _this.editForm.street=myValue;
        local.search(myValue);
      }
    },
    handleSearch(){
      // alert("handleSearch：" + this.editForm.points);
      console.log("获取焦点");
      this.editForm.points="";
      this.getInit(this.editForm.city);
    },
    handleBlur(e) {
      // alert("handleBlur：" + this.editForm.points);
      console.log("失去焦点");
      if(!this.editForm.points){
        console.log("经纬度为空");
        this.editForm.street="";
        return
      }
    },
    bindGetLocation(ev) {
      console.log("正在获取位置......");
      //创建百度地图控件
      var _this=this;
      var geolocation = new BMap.Geolocation();
      geolocation.getCurrentPosition(
        function (r) {
          console.log(r);
          if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            //以指定的经度与纬度创建一个坐标点
            var pt = r.point;
            //创建一个地理位置解析器
            var geoc = new BMap.Geocoder();
            geoc.getLocation(pt, function (rs) {
              console.log("获取当前地址")
              console.log(rs);
              
              _this.setPlace(rs.addressComponents.city);
              //解析格式：城市，区县，街道
              var addComp = rs.addressComponents;
              console.log("省市区街道3：" + addComp.province + ", " + addComp.city + ", " + addComp.district + ", " + addComp.street);
            });
          } else {
            console.log("定位失败");
          }
        },
        { enableHighAccuracy: true }
      ); //指示浏览器获取高精度的位置，默认false
    },
    getIdByArr(data) {
      let arr = data || [];
      let url = arr.map((item) => {
        return item.oldUrl;
      });
      return url.join(",");
    },
    formmatAddress(row) {
      let data = [...row];
      let [province, city, area] = data;
      this.searchMsg.province = province;
      this.searchMsg.city = city;
      // this.searchMsg.area = area
    },
    saveShopMsg() {
      this.$refs.editForm.validate((valid) => {
        if (valid) {
          let vue = this;
          if (!vue.editForm.shopLogoImgFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传门店头像",
              type: "error",
            });
            return false;
          } else if (vue.editForm.shopLogoImgFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张门店头像",
              type: "error",
            });
            return false;
          }
          if (!vue.editForm.shopWithinImgFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传店内照片",
              type: "error",
            });
            return false;
          }
          if (!vue.editForm.businessLicenseFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传营业执照",
              type: "error",
            });
            return false;
          } else if (vue.editForm.businessLicenseFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张营业执照",
              type: "error",
            });
            return false;
          }
          if (!vue.editForm.idCardFrontSideFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传身份证正面照片",
              type: "error",
            });
            return false;
          } else if (vue.editForm.idCardFrontSideFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张身份证正面照片",
              type: "error",
            });
            return false;
          }
          if (!vue.editForm.idCardBackSideFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传身份证反面照片",
              type: "error",
            });
            return false;
          } else if (vue.editForm.idCardBackSideFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张身份证反面照片",
              type: "error",
            });
            return false;
          }

          let param = Object.assign({}, this.editForm);
          delete param.createTime;
          delete param.updateTime;

          param.shopLogoImg = vue.getIdByArr(param.shopLogoImgFile);
          param.shopWithinImg = vue.getIdByArr(param.shopWithinImgFile);
          param.businessLicense = vue.getIdByArr(param.businessLicenseFile);
          param.idCardFrontSide = vue.getIdByArr(param.idCardFrontSideFile);
          param.idCardBackSide = vue.getIdByArr(param.idCardBackSideFile);
          delete param.shopLogoImgFile;
          delete param.shopWithinImgFile;
          delete param.businessLicenseFile;
          delete param.idCardFrontSideFile;
          delete param.idCardBackSideFile;

          // let [province, city, area] = [...vue.editForm.selectedOptions];
          // delete param.selectedOptions;
          // param.province = province;
          // param.city = city;
          // param.area = area;

          delete param.auditTime;
          delete param.isOperating;
          delete param.startTime;
          delete param.endTime;

          //处理店铺id
          param.shopId = param.id;
          delete param.id;

          let url = "/rest/merchant/shop/applyChangeImportantData";
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
              // vue.goBack()
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
    getDetail(id) {
      // 获取商品详情
      let vue = this;
      vue.$http.post(
        vue,
        "/rest/merchant/shop/getLoginMerchantShopInfo",
        { id },
        (vue, data) => {
          let obj = data.data;
          obj.shopLogoImgFile = vue.resetImg(obj, "shopLogoImg");
          obj.shopWithinImgFile = vue.resetImg(obj, "shopWithinImg");
          obj.businessLicenseFile = vue.resetImg(obj, "businessLicense");
          obj.idCardFrontSideFile = vue.resetImg(obj, "idCardFrontSide");
          obj.idCardBackSideFile = vue.resetImg(obj, "idCardBackSide");

          obj.points = {};
          obj.points.lng = obj.longitude;
          obj.points.lat = obj.latitude;

          vue.editForm = Object.assign({}, obj);
          // this.editForm.selectedOptions = [obj.province, obj.city, obj.area];
          this.editForm.isOperating =
            obj.isOperating == true ? "正常营业" : "暂不营业";
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
    handleRemoveShopLogoImg(file, filelist) {
      let arr = this.editForm.shopLogoImgFile;
      this.editForm.shopLogoImgFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveShopWithinImg(file, filelist) {
      let arr = this.editForm.shopWithinImgFile;
      this.editForm.shopWithinImgFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveBusinessLicense(file, filelist) {
      let arr = this.editForm.businessLicenseFile;
      this.editForm.businessLicenseFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveIdCardFrontSide(file, filelist) {
      let arr = this.editForm.idCardFrontSideFile;
      this.editForm.idCardFrontSideFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveIdCardBackSide(file, filelist) {
      let arr = this.editForm.idCardBackSideFile;
      this.editForm.idCardBackSideFile = arr.filter(function (item) {
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
    upload(option) {
      // 图片上传
      let vue = this;
      let value = option.file;
      let formData = new FormData();
      formData.append("file", value);
      vue.$http.postupload(
        vue,
        "/rest/merchant/uploadSingleImage",
        formData,
        function (vue, data) {
          option.onSuccess();
          let obj = {
            oldUrl: data.data,
            url: vue.$http.publicUrl(data.data),
            uid: option.file.uid,
          };
          if (option.filename === "shopWithinImgUpload") {
            vue.editForm.shopWithinImgFile.push(obj);
          } else if (option.filename === "detailImagesUpload") {
            vue.editForm.detailImagesFile.push(obj);
          } else if (option.filename === "businessLicenseUpload") {
            vue.editForm.businessLicenseFile.push(obj);
          } else if (option.filename === "idCardFrontSideUpload") {
            vue.editForm.idCardFrontSideFile.push(obj);
          } else if (option.filename === "idCardBackSideUpload") {
            vue.editForm.idCardBackSideFile.push(obj);
          } else if (option.filename === "shopLogoImgUpload") {
            vue.editForm.shopLogoImgFile.push(obj);
          }
        },
        function (error) {
          option.onError();
        }
      );
    },
  },
  created() {
    this.getTypeList();
    let id = this.$route.query.id;
    this.getDetail(id);
    //开启订单自动打印定时器
    this.$orderPrint.init();
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
</style>

