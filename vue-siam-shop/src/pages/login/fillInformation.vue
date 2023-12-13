<style scoped>
#map {
  height: 300px;
  width: 100%;
}
</style>
<template>
    <section>
		
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" label-position="left" class="login-container">              
            <div class="Status" v-if="ruleForm.name!='' && ruleForm.name!=null && ruleForm.name!=''"> 
              <span v-if="ruleForm.auditStatus==4">您好，您的申请正在审核中，请耐心等待！</span>
					    <span v-else-if="ruleForm.auditStatus==2">您好，您的申请已通过，可进入主页面操作！</span>
					    <span v-else-if="ruleForm.auditStatus==3">您好，您的申请不合格，请重新填写开店信息！<br/>
                     审核失败原因：{{ruleForm.auditReason}}
               
                    
          
                      </span>
         	
                     <!-- <el-table-column prop="auditReason" label="账单类型"></el-table-column> -->
           </div>
             
            
            <div class="title"><span>填写开店信息</span></div>
              <!-- <el-form-item prop="name" label="线下店铺牌匾名">
                     <el-input type="text" v-model="ruleForm.name" auto-complete="off" placeholder="请输入线下店铺牌匾名" ></el-input>
              </el-form-item>
              <el-form-item prop="selectedOptions" label="所在城市">
                  <el-cascader style="width:100%;" :options="options" separator="-" :props="props" clearable change-on-select placeholder="请选择所在城市"
                      v-model="ruleForm.selectedOptions">
                  </el-cascader>
              </el-form-item>
              <el-form-item prop="street" label="门店地址" >
                 <el-input type="text" v-model="ruleForm.street" auto-complete="off" placeholder="请输入门店地址" ></el-input>
              </el-form-item>               -->
      <!-- <el-form-item prop="managePrimary" label="营业类目">
                     <el-input type="text" v-model="ruleForm.managePrimary" auto-complete="off" placeholder="请输入营业类目" ></el-input>
              </el-form-item>               -->
      <!-- <el-form-item prop="companyname" label="堂食">
                     <el-input type="text" v-model="ruleForm.companyname" auto-complete="off" placeholder="请输入与单位证照资料一致的单位全称" ></el-input>
              </el-form-item>                 -->
      <!-- <el-form-item prop="shopImg" label="门脸照">
                     <el-input type="text" v-model="ruleForm.shopImg" auto-complete="off" placeholder="请输入门脸照" ></el-input>
              </el-form-item>        -->
      <!-- <el-form-item prop="shopWithinImg" label="店内照">
                     <el-input type="text" v-model="ruleForm.shopWithinImg" auto-complete="off" placeholder="请输入店内照" ></el-input>
              </el-form-item>        -->
      <!-- <el-form-item prop="shopLogoImg" label="门店logo">
                     <el-input type="text" v-model="ruleForm.shopLogoImg" auto-complete="off" placeholder="请输入门店logo" ></el-input>
              </el-form-item>                                                  -->
      <!-- <el-for  m-item prop="takeOutPhone" label="外卖电话">
                     <el-input type="text" v-model="ruleForm.takeOutPhone" auto-complete="off" placeholder="请填写外卖电话"></el-input>
              </el-form-item> -->

      <el-form-item label="门店名称" prop="name">
        <el-input v-model="ruleForm.name"></el-input>
      </el-form-item>
      <el-form-item prop="contactRealname" label="联系人姓名">
        <el-input
          type="text"
          v-model="ruleForm.contactRealname"
          auto-complete="off"
          placeholder="请填写联系人姓名"
        ></el-input>
      </el-form-item>
      <el-form-item prop="contactPhone" label="联系电话">
        <el-input
          type="text"
          v-model="ruleForm.contactPhone"
          auto-complete="off"
          placeholder="请填写联系电话"
        ></el-input>
      </el-form-item>
      <!-- <el-form-item label="外卖电话" prop="takeOutPhone">
                <el-input v-model="ruleForm.takeOutPhone"></el-input>
              </el-form-item> -->

      <!-- <el-form-item prop="selectedOptions" label="所在城市">
        <el-cascader
          style="width: 100%"
          :options="options"
          separator="-"
          :props="props"
          clearable
          change-on-select
          placeholder="请选择所在城市"
          v-model="ruleForm.selectedOptions"
        >
        </el-cascader>
      </el-form-item> -->

      <el-form-item prop="street" label="门店地址">
        <el-input
          style="width: 52%"
          type="text"
          v-model="ruleForm.street"
          auto-complete="off"
          placeholder="请输入门店地址"
          suffix-icon="el-icon-position"
          @input="handleSearch"
          @blur="handleBlur"
          id="suggestId"
        ></el-input>
        门牌号
        <el-input
          style="width: 40%"
          type="text"
          v-model="ruleForm.houseNumber"
          auto-complete="off"
          placeholder="请输入门牌号"
        ></el-input>
        <div
          id="searchResultPanel"
          style="border: 1px solid #c0c0c0; width: 150px; display: none"
        ></div>
        <div id="l-map" :style="'height:300px;width:100%;'"></div>
      </el-form-item>

      <el-form-item label="门店公告" prop="announcement">
        <el-input v-model="ruleForm.announcement"></el-input>
      </el-form-item>

      <!-- <el-form-item label="门店简介" prop="briefIntroduction">
                <el-input type="textarea" :rows="6" v-model="ruleForm.briefIntroduction"></el-input>
              </el-form-item>        -->

      <el-form-item prop="managePrimary" label="营业类目">
        <el-input
          type="text"
          v-model="ruleForm.managePrimary"
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
          :file-list="ruleForm.shopLogoImgFile"
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
          :file-list="ruleForm.shopWithinImgFile"
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
          :file-list="ruleForm.businessLicenseFile"
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
          :file-list="ruleForm.idCardFrontSideFile"
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
          :file-list="ruleForm.idCardBackSideFile"
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

      <!-- <p class="userAgreement">注册即代表同意<span @click="$router.push('/userAgreement')">《用户隐私协议》</span></p> -->
      <el-button
        type="primary"
        style="width: 100%"
        @click.native.prevent="handleNext"
        >提交</el-button
      >
      <div>
        已有账号
        <el-button
          class="button blueColor"
          type="text"
          @click="$router.push('/login')"
          >去登录</el-button
        >
      </div>
    </el-form>
  </section>
</template>

<script>
//import NProgress from 'nprogress'
import cityData from "../../../static/citys.json";
import { MP } from "../../utils/bmap.js";
var map = null;
export default {
  data() {
    var checkValid = (rule, value, callback) => {
      let reg = /^1(3|4|5|7|8|9)\d{9}$/;
      if (!reg.test(value)) {
        callback(new Error("请输入正确格式手机号"));
      } else {
        callback();
      }
    };
    var passwordValid = (rule, value, callback) => {
      let reg = /^[a-z0-9]{6,20}$/i;
      if (!reg.test(value)) {
        callback(new Error("密码由6-20位数字、字母组成。"));
      } else {
        callback();
      }
    };

    return {
      logining: false,
      unclickable: false,
      options: cityData,
      props: {
        value: "label",
        children: "children",
      },
      ruleForm: {
        name: "",
        // selectedOptions: [],

        province: "",
        city: "",
        area: "",

        street: "",
        managePrimary: "",
        shopImg: "",
        shopWithinImg: "",
        shopLogoImg: "",
        businessLicense: "",
        idCardFrontSide: "",
        idCardBackSide: "",
        contactRealname: "",
        contactPhone: "",
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
      rules: {
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
        contactRealname: [
          { required: true, message: "请输入联系人姓名", trigger: "blur" },
        ],
        contactPhone: [
          { required: true, message: "请输入联系电话", trigger: "blur" },
        ],
        takeOutPhone: [
          { required: true, message: "请输入外卖电话", trigger: "blur" },
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
      },
      checked: true,
      time: 60,
      points: "",
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
        if(_this.ruleForm.city){
          console.log("省市区街道2：" + _this.ruleForm.province + ", " + _this.ruleForm.city + ", " + _this.ruleForm.area + ", " + _this.ruleForm.street);
          _this.setPlace(_this.ruleForm.city);
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
        if(local.getResults()){
          var pp = local.getResults().getPoi(0).point; //获取第一个智能搜索的结果
          map.centerAndZoom(pp, 18);
          map.addOverlay(new BMap.Marker(pp)); //添加标注
        }
      }
      var local = new BMap.LocalSearch(map, {
        //智能搜索
        onSearchComplete: myFun,
      });
      local.search(this.ruleForm.street);
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

          _this.ruleForm.points=pp;
          _this.ruleForm.longitude=pp.lng;
          _this.ruleForm.latitude=pp.lat;
          
          map.centerAndZoom(pp, 18);
          map.addOverlay(new BMap.Marker(pp)); //添加标注
        }
        // alert("myValue：" + myValue)
        _this.ruleForm.street=myValue;
        var local = new BMap.LocalSearch(map, {
          //智能搜索
          onSearchComplete: myFun,
        });
        // alert("myValue2：" + myValue)
        _this.ruleForm.street=myValue;
        local.search(myValue);
      }
    },
    handleSearch(){
      // alert("handleSearch：" + this.ruleForm.points);
      console.log("获取焦点");
      this.ruleForm.points="";
      this.getInit(this.ruleForm.city);
    },
    handleBlur(e) {
      // alert("handleBlur：" + this.ruleForm.points);
      console.log("失去焦点");
      if(!this.ruleForm.points){
        console.log("经纬度为空");
        this.ruleForm.street="";
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
    getDetail() {
      // 获取开店信息详情
      let vue = this;
      vue.$http.post(
        vue,
        "/rest/merchant/shop/getLoginMerchantShopInfo",
        {},
        (vue, data) => {
          let obj = data.data;

          console.log("标识\n");
          console.log(obj.name);
          console.log(obj.name == "");
          console.log(obj.name == null);
          console.log(obj.name == undefined);
          console.log(obj.name != undefined);
          console.log(obj.name != undefined);

          // debugger
          // obj.mainImageFile = vue.resetImg(obj, 'mainImage')
          // obj.subImagesFile = vue.resetImg(obj, 'subImages')
          // obj.detailImagesFile =  vue.resetImg(obj, 'detailImages')
          obj.shopLogoImgFile = vue.resetImg(obj, "shopLogoImg");
          obj.shopWithinImgFile = vue.resetImg(obj, "shopWithinImg");
          obj.businessLicenseFile = vue.resetImg(obj, "businessLicense");
          obj.idCardFrontSideFile = vue.resetImg(obj, "idCardFrontSide");
          obj.idCardBackSideFile = vue.resetImg(obj, "idCardBackSide");

          obj.points = {};
          obj.points.lng = obj.longitude;
          obj.points.lat = obj.latitude;

          vue.ruleForm = Object.assign({}, obj);
          // this.ruleForm.selectedOptions = [obj.province, obj.city, obj.area];
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
    getMobileCode() {
      let vue = this;
      if (!/^1(3|4|5|7|8)\d{9}$/.test(vue.ruleForm.mobile)) {
        vue.$message({
          showClose: true,
          message: "请输入正确的手机号",
          type: "error",
        });
        return false;
      }
      vue.logining = true;
      let param = {
        mobile: vue.ruleForm.mobile,
        type: "register",
      };
      vue.$http.post(
        vue,
        "/rest/front/mobile/genMobileCode",
        param,
        (vue, data) => {
          vue.logining = false;
          vue.$message({
            showClose: true,
            message: data.message,
            type: "success",
          });
          vue.timeInit();
        },
        (error, data) => {
          vue.logining = false;
          vue.$message({
            showClose: true,
            message: data.message,
            type: "error",
          });
        }
      );
    },
    timeInit() {
      let _this = this;
      _this.unclickable = true;
      let interval = window.setInterval(function () {
        if (_this.time-- <= 0) {
          _this.time = 60;
          _this.unclickable = false;
          window.clearInterval(interval);
        }
      }, 1000);
    },
    handleNext() {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          let vue = this;

          if (!vue.ruleForm.shopLogoImgFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传门店头像",
              type: "error",
            });
            return false;
          } else if (vue.ruleForm.shopLogoImgFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张门店头像",
              type: "error",
            });
            return false;
          }
          if (!vue.ruleForm.shopWithinImgFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传店内照片",
              type: "error",
            });
            return false;
          }

          if (!vue.ruleForm.businessLicenseFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传营业执照",
              type: "error",
            });
            return false;
          } else if (vue.ruleForm.businessLicenseFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张营业执照",
              type: "error",
            });
            return false;
          }
          if (!vue.ruleForm.idCardFrontSideFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传身份证正面照片",
              type: "error",
            });
            return false;
          } else if (vue.ruleForm.idCardFrontSideFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张身份证正面照片",
              type: "error",
            });
            return false;
          }
          if (!vue.ruleForm.idCardBackSideFile.length) {
            vue.$message({
              showClose: true,
              message: "请上传身份证反面照片",
              type: "error",
            });
            return false;
          } else if (vue.ruleForm.idCardBackSideFile.length > 1) {
            vue.$message({
              showClose: true,
              message: "只能上传一张身份证反面照片",
              type: "error",
            });
            return false;
          }

          let param = Object.assign({}, vue.ruleForm);
          delete param.auditTime;
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

          // let regUser = {
          //   id: param.id,
          //   name: vue.ruleForm.name,
          //   street: vue.ruleForm.street,
          //   managePrimary: vue.ruleForm.managePrimary,
          //   shopImg: vue.ruleForm.shopImg,
          //   shopWithinImg: vue.ruleForm.shopWithinImg,
          //   shopLogoImg: vue.ruleForm.shopLogoImg,
          //   takeOutPhone: vue.ruleForm.takeOutPhone,
          //   contactRealname: vue.ruleForm.contactRealname,
          //   contactPhone: vue.ruleForm.contactPhone
          // }

          // let [province, city, area] = [...vue.ruleForm.selectedOptions];
          // delete param.selectedOptions;
          // param.province = province;
          // param.city = city;
          // param.area = area;

          vue.$http.post(
            vue,
            "/rest/merchant/shop/apply",
            param,
            (vue, data) => {
              vue.$message({
                showClose: true,
                message: data.message,
                type: "success",
              });
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
    handleRemoveShopLogoImg(file, filelist) {
      let arr = this.ruleForm.shopLogoImgFile;
      this.ruleForm.shopLogoImgFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveShopWithinImg(file, filelist) {
      let arr = this.ruleForm.shopWithinImgFile;
      this.ruleForm.shopWithinImgFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveBusinessLicense(file, filelist) {
      let arr = this.ruleForm.businessLicenseFile;
      this.ruleForm.businessLicenseFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveIdCardFrontSide(file, filelist) {
      let arr = this.ruleForm.idCardFrontSideFile;
      this.ruleForm.idCardFrontSideFile = arr.filter(function (item) {
        return item.uid !== file.uid;
      });
    },
    handleRemoveIdCardBackSide(file, filelist) {
      let arr = this.ruleForm.idCardBackSideFile;
      this.ruleForm.idCardBackSideFile = arr.filter(function (item) {
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
            vue.ruleForm.shopWithinImgFile.push(obj);
          } else if (option.filename === "detailImagesUpload") {
            vue.ruleForm.detailImagesFile.push(obj);
          } else if (option.filename === "businessLicenseUpload") {
            vue.ruleForm.businessLicenseFile.push(obj);
          } else if (option.filename === "idCardFrontSideUpload") {
            vue.ruleForm.idCardFrontSideFile.push(obj);
          } else if (option.filename === "idCardBackSideUpload") {
            vue.ruleForm.idCardBackSideFile.push(obj);
          } else if (option.filename === "shopLogoImgUpload") {
            vue.ruleForm.shopLogoImgFile.push(obj);
          }
        },
        function (error) {
          option.onError();
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
  },
  created() {
    this.getDetail();
  },
};
</script>

<style lang="scss" scoped>
.login-container {
  // display: inline-block;
  /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
  // -webkit-border-radius: 5px;
  // border-radius: 5px;
  // -moz-border-radius: 5px;
  background-clip: padding-box;
  // margin: 180px auto;
  margin: 30px auto;
  width: 800px;

  // padding: 70px 110px 0;
  padding-left: 70px;
  padding-right: 70px;
  padding-top: 20px;
  background: #fff;
  // border: 1px solid #eaeaea;
  // box-shadow: 0 0 25px #cac6c6;
  .title {
    margin-bottom: 20px;
    text-align: center;
    color: #0a79f9;
    font-size: 30px;
    line-height: 30px;
  }
  .Status {
    margin-bottom: 20px;
    color: #ff0000;
    font-size: 20px;
    line-height: 30px;
  }
  .handelButton {
    text-align: center;
  }
  .divider {
    height: 1px;
    width: 100%;
    margin: 24px 0;
    background-color: #dcdfe6;
    position: relative;
    .button {
      left: 50%;
      -webkit-transform: translateX(-50%) translateY(-50%);
      transform: translateX(-50%) translateY(-50%);
      position: absolute;
      background-color: #fff;
      padding: 0 20px;
      color: #a4abb2;
    }
    .blueColor:hover {
      color: #0a79f9;
    }
  }
}
.userAgreement {
  margin-bottom: 6px;
}
.userAgreement span {
  font-weight: bold;
  color: rgb(179, 179, 179);
  cursor: pointer;
}

// .login-container {
//   width: 600px;
// }
</style>