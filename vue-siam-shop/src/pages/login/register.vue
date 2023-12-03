<template>
    <section>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="login-container">
          <div class="title"><span>暹罗点餐商家端</span></div>
            <el-form-item prop="mobile">
                <el-input prefix-icon="el-icon-myshouji" type="text" v-model="ruleForm.mobile" auto-complete="off" placeholder="请输入手机号"></el-input>
            </el-form-item>
            <el-form-item prop="mobileCode">
                <el-col :span="14">
                  <el-input prefix-icon="el-icon-mylock" type="text" v-model="ruleForm.mobileCode" auto-complete="off" placeholder="验证码"></el-input>
                </el-col>
                <el-col :span="4" :offset="1">
                    <el-button type="text" @click="getMobileCode" :disabled="unclickable"> {{ unclickable ? time + '秒后重新获取' : '获取验证码' }} </el-button>
                </el-col>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">注册/登录</el-button>
            </el-form-item>
            <el-form-item class="handelButton">
                <!-- <el-col :span="12" style="text-align: left;">
                    <el-button type="text" @click.native.prevent="$router.push('/register')" :loading="logining">免费注册</el-button>
                </el-col> -->
                <el-col :span="24" style="text-align: right;">
                    <el-button style="color: #A4ABB2;" class="blueColor" type="text" @click="$router.push('/setPassword')">忘记密码</el-button>
                </el-col>
            </el-form-item>
            <el-form-item style="width:100%;">
                <p class="userAgreement">注册即代表同意<span @click="$router.push('/userAgreement')">《用户隐私协议》</span></p>
            </el-form-item>            
            <el-form-item>
                <!-- <div class="divider">
                    <el-button class="button blueColor" type="text" @click="$router.push('/login')">账号密码登录</el-button>
                </div> -->
            </el-form-item>
        </el-form>
    </section>
  
  
</template>

<script>
  //import NProgress from 'nprogress'
  export default {
    data() {
      var checkValid = (rule, value, callback) => {
        let reg = /^1(3|4|5|7|8)\d{9}$/
        if (!reg.test(value)) {
            callback(new Error('请输入正确格式手机号'));
        } else {
            callback();
        }
      };
      return {
        logining: false,
        ruleForm: {
          mobile: '',
          mobileCode: ''
        },
        rules: {
          mobile: [
            { required: true, validator: checkValid, message: '请输入手机号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          mobileCode: [
            { required: true, message: '请输入验证码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true,
        unclickable: false,
        time: 60,
      };
    },
    methods: {
      gouserLogin() {
        this.$router.push({path: '/userLogin'})
      },
      getMobileCode() {
        let vue = this
        if (!(/^1(3|4|5|7|8|9)\d{9}$/.test(vue.ruleForm.mobile))) {
					vue.$message({
              showClose: true,
              message: '请输入正确的手机号',
              type: 'error'
          });
          return false
				}
        vue.logining = true;
        let param = {
          mobile: vue.ruleForm.mobile,
					type: 'register'
        }
        vue.$http.post(vue, '/rest/smsLog/sendMobileCode', param,
            (vue, data)=> {
                vue.logining = false;
                vue.$message({
                    showClose: true,
                    message: data.message,
                    type: 'success'
                });
                vue.timeInit()
            },
            (error, data)=> {
                vue.logining = false;
                vue.$message({
                    showClose: true,
                    message: data.message,
                    type: 'error'
                });
            })
      },
      timeInit(){
        let _this = this;
        _this.unclickable = true
        let interval = window.setInterval(function () {
          if ((_this.time--) <= 0) {
            _this.time = 60;
            _this.unclickable = false
            window.clearInterval(interval);
          }
        }, 1000);
      },
      handleReset2() {
        this.$refs.ruleForm.resetFields();
      },
      handleSubmit(ev) {
        var vue = this;
        this.$refs.ruleForm.validate((valid) => {
          if (valid) {
            vue.logining = true;
            const companyInfo = {
              mobile: vue.ruleForm.mobile,
              mobileCode: vue.ruleForm.mobileCode
            }
            vue.$http.post(vue, '/rest/merchant/registerByMobile', companyInfo,
              (vue, data)=> {
                vue.logining = false;
                // vue.$message({
                //   showClose: true,
                //   message: data.message,
                //   type: 'success'
                // });
                // vue.getUserInfo()
                console.log("token="+data.data.token);
                sessionStorage.setItem("token", data.data.token);
                vue.getUserInfo(data.data.token);                
              },
              (error, data)=> {
                vue.logining = false;
               vue.$message({
                  showClose: true,
                  message: data.message,
                  type: 'error'
                });
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      getUserInfo(token) {
        let vue = this
        vue.$http.post(vue, '/rest/merchant/getLoginMerchantInfo', {"token" : token},
          (vue, data)=> {
            let user = data.data
            user.password = vue.ruleForm.password
            vue.checked && sessionStorage.setItem('user', JSON.stringify(user));
            if(user.auditStatus != 2){
              vue.$message({
                showClose: true,
                message: "注册成功",
                type: 'success'
              });      
              vue.$router.push({ path: '/fillInformation'});              
            }else{
              vue.$message({
                showClose: true,
                message: "您之前已注册，自动跳转到商家端主页",
                type: 'success'
              });              
              vue.$router.push({ path: '/'});
            }
          },
          (error, data)=> {
            
        })        
      }
    }
  }

</script>

<style lang="scss" scoped>
  .login-container {
    display: inline-block;
    /*box-shadow: 0 0px 8px 0 rgba(0, 0, 0, 0.06), 0 1px 0px 0 rgba(0, 0, 0, 0.02);*/
    -webkit-border-radius: 5px;
    border-radius: 5px;
    -moz-border-radius: 5px;
    background-clip: padding-box;
    // margin: 180px auto;
    width: 340px;
    // height:390px;
    padding: 70px 110px 0;
    background: #fff;
    border: 1px solid #eaeaea;
    // box-shadow: 0 0 25px #cac6c6;
    .title {
      margin-bottom: 20px;
      text-align: center;
      color: #0A79F9;
      font-size: 30px;
      line-height: 30px;
    }
    .remember {
      margin: 0px 0px 35px 0px;
    }
    .divider {
      height: 1px;
      width: 100%;
      margin: 24px 0;
      background-color: #DCDFE6;
      position: relative;
      .button {
        left: 50%;
        -webkit-transform: translateX(-50%) translateY(-50%);
        transform: translateX(-50%) translateY(-50%);
        position: absolute;
        background-color: #FFF;
        padding: 0 20px;
        color: #A4ABB2;
      }
      .blueColor:hover {
        color: #0A79F9;
      }
    }
  }
  
</style>