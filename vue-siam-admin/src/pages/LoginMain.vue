<template>
  <div class="login-content">
    <!-- <img class="loginLogo" src="../assets/qiuzhidaoLoginLogo.png"> -->
    <div class="login-center" style="margin-top:90px;">
      <transition name="fade" mode="out-in">
        <router-view></router-view>
      </transition>
    </div>
    <!-- <div class="login-footer">
         <p class="corCompanyTitle">合作单位</p>
         <div class="login-footer-inner">
           <div class="login-footer-inner2">
              <img class="corCompany" src="../assets/corCompany2.png">
              <img class="corCompany" src="../assets/corCompany3.png">
            </div>
         </div>
         <div class="readyNumber">
           <img class="police" src="../assets/police.png">
           <a class="readyNumberText" href="javascript:void(0);">浙ICP备16047515号-4 &copy; 2019</a>
         </div>
    </div> -->
  </div>
</template>

<script>
  
</script>

<style lang="scss" scoped>
  .login-content {
    height:100%;
    overflow: auto;
    background: url('../assets/loginBg2.png') no-repeat top;
    // background-size: 100%;
    background-color: rgb(10, 121, 249);
    width: 100%;
    position: relative;
    // height: 100%;
    .loginLogo{
      width: 280px;
      height: 100px;
      display: block;
      margin: 0 auto;
      margin-top: 10px;
    }
    .login-center {
       margin: 10px auto 0;
      text-align: center;
    }
    .login-footer{
      width: 100%;
      height: 200px;
      // height: 20%;
      // position: absolute;

      // bottom: 0;
      // background-color: rgb(7, 84, 174);
    }
    .login-footer-inner{
      width: 800px;
      height: 140px;
      position: relative;
      margin: 14px auto 0;
      // border: 1px solid red;
      overflow: hidden;
    }
    .login-footer-inner2{
      width: 1600px;
      height: 140px;
      position: absolute;
      left: 0;
      animation: slide 10s infinite linear;
    }
    .corCompanyTitle{
      text-align: center;
      color: white;
      font-size: 16px;
      margin-top: 14px;
    }
    .corCompany{
      display: block;
      float: left;
      width: 800px;
    }
     @keyframes slide {
       0%{left: 0}
       50%{left:-400px;}
       100%{left:-800px;}
     }
  }
  .readyNumber{
    margin-top: 100px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .readyNumberText{
    color: #ffffff;
    font-size: 14px;
  }
  .police{
    width: 16px;
    height: 16px;
    margin-right: 4px;
  }
</style>