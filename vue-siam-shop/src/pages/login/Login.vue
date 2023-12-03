<template>
    <section>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-position="left" label-width="0px" class="login-container">
            <div class="title"><span>商家登录</span></div>
            <el-form-item prop="name">
                <el-input prefix-icon="el-icon-myuser" type="text" v-model="ruleForm.name" @keyup.enter.native="handleSubmit" auto-complete="off" placeholder="账号"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input prefix-icon="el-icon-mylock" type="password" @keyup.enter.native="handleSubmit" v-model="ruleForm.password" auto-complete="off" placeholder="密码"></el-input>
            </el-form-item>
            <el-form-item style="width:100%;">
                <el-button type="primary" style="width:100%;" @click.native.prevent="handleSubmit" :loading="logining">登录</el-button>
            </el-form-item>
            <el-form-item class="handelButton">
                <!-- <el-col :span="12" style="text-align: left;">
                    <el-button type="text" @click.native.prevent="$router.push('/register')" :loading="logining">免费注册</el-button>
                </el-col> -->
                <el-col :span="12" style="text-align: left;">
                    <el-button type="text" @click.native.prevent="$router.push('/register')">没有账号？去开店</el-button>
                </el-col>
                <el-col :span="12" style="text-align: right;">
                    <el-button style="color: #A4ABB2;" type="text" @click="$router.push('/setPassword')">忘记密码</el-button>
                </el-col>
            </el-form-item>
            <el-form-item>
                <div class="divider">
                    <!-- <el-button class="button blue" type="text" @click="$router.push('/QuickLogin')">快捷登录</el-button> -->
                    <el-button class="button blue" type="text" @click="$router.push('/QuickLogin')">手机验证登录</el-button>
                </div>
            </el-form-item>
        </el-form>
    </section>
</template>

<script>
  //import NProgress from 'nprogress'
  export default {
    data() {
      return {
        logining: false,
        ruleForm: {
          name: '',
          password: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            //{ validator: validaePass }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            //{ validator: validaePass2 }
          ]
        },
        checked: true
      };
    },
    methods: {
      handleReset2() {
        this.$refs.ruleForm.resetFields();
      },
      handleSubmit(ev) {
        var vue = this;
        this.$refs.ruleForm.validate((valid) => {
          // if (valid) {
          //   vue.logining = true;
          //   const companyInfo = {
          //     username: vue.ruleForm.name,
          //     password: vue.$utils.Base64(vue.ruleForm.password)
          //   }
          //   vue.$http.post(vue, '/rest/merchant/login', companyInfo,
          //     (vue, data)=> {
          //       vue.logining = false;
          //       vue.$message({
          //         showClose: true,
          //         message: data.message,
          //         type: 'success'
          //       });
          //       vue.getUserInfo()
          //     },
          //     (error, data)=> {
          //       vue.logining = false;
          //      vue.$message({
          //         showClose: true,
          //         message: data.message,
          //         type: 'error'
          //       });
          //     })
          // } else {
          //   console.log('error submit!!');
          //   return false;
          // }
          if (valid) {
            // this.logining = true;
            const user = {
              username: vue.ruleForm.name,
              password: vue.$utils.Base64(vue.ruleForm.password)
            }
            vue.$http.post(vue, '/rest/merchant/login', user,
              (vue, data)=> {
                console.log("token="+data.data.token);
                sessionStorage.setItem("token", data.data.token);
                vue.getUserInfo(data.data.token);
              },
              (error, data)=> {
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
            if(user.auditStatus == 2){
                vue.$message({
                  showClose: true,
                  message: "登录成功",
                  type: 'success'
                });              
              vue.$router.push({ path: '/'});
            }else{
							vue.$message({
								showClose:true,
								message:"您的门店信息还未审核成功，自动跳转到我要开店页面",
								type:'success'
							})
              vue.$router.push({ path: '/fillInformation'});
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
        .handelButton {
          text-align: center;
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
          .blue {
            color: #409EFF;
          }
        }
      }
  
  
</style>