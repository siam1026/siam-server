<template> 
  <div class="container" :style="{'background':themeColor}" :class="$store.state.app.collapse?'menu-bar-collapse-width':'menu-bar-width'">
    <!-- 导航收缩 -->
    <span class="hamburger-container" :style="{'background':themeColor}">
      <hamburger :onClick="onCollapse" :isActive="collapse"></hamburger>
    </span>
    <!-- 导航菜单 -->
    <span class="nav-bar">
      <el-menu :default-active="activeIndex" class="el-menu-demo" 
          :background-color="themeColor" text-color="#fff" active-text-color="#ffd04b" mode="horizontal" @select="selectNavBar()">
        <el-menu-item index="1" @click="$router.push('/')"><i class="fa fa-home fa-2x"></i>  </el-menu-item>
        <el-menu-item index="2" @click="openWindow('https://gitee.com/liuge1988/kitty-generator')">{{$t("common.projectRepo")}}</el-menu-item>
        <el-menu-item index="3" @click="openWindow('https://gitee.com/liuge1988/kitty-generator/wikis/Home')">{{$t("common.doc")}}</el-menu-item>
        <el-menu-item index="4" @click="openWindow('https://www.cnblogs.com/xifengxiaoma/')">{{$t("common.blog")}}</el-menu-item>
      </el-menu>
    </span>
    <span class="tool-bar">
      <!-- 主题切换 -->
      <theme-picker class="theme-picker" :default="themeColor" @onThemeChange="onThemeChange"></theme-picker>
      <!-- 语言切换 -->
      <lang-selector class="lang-selector"></lang-selector>   
      <!-- 数据源 -->
      <el-button type="primary" icon="fa fa-database fa-2x" @click="configDatasource"></el-button>
      <!-- 用户信息 -->
      <el-dropdown class="user-info-dropdown" trigger="click" @command="handleCommand">
        <el-button type="primary" icon="fa fa-bars fa-2x"></el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="datasource">{{$t("common.datasource")}}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </span>
    <!--数据源配置界面-->
    <datasource-dialog ref="datasourceDialog" v-if="datasourceDialogVisible">  </datasource-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import mock from "@/mock/index"
import Hamburger from "@/components/Hamburger"
import ThemePicker from "@/components/ThemePicker"
import LangSelector from "@/components/LangSelector"
import DatasourceDialog from "@/views/Datasource/DatasourceDialog"
export default {
  components:{
    Hamburger,
    ThemePicker,
    LangSelector,
    DatasourceDialog
  },
  data() {
    return {
      userName: "Louis",
      userAvatar: "",
      activeIndex: '1',
      datasourceDialogVisible: false
    }
  },
  methods: {
    openWindow(url) {
      window.open(url)
    },
    selectNavBar(key, keyPath) {
      console.log(key, keyPath)
    },
    // 折叠导航栏
    onCollapse: function() {
      this.$store.commit('onCollapse')
    },
    // 切换主题
    onThemeChange: function(themeColor) {
      this.$store.commit('setThemeColor', themeColor)
    },
    handleCommand(command) {
      if('datasource' === command) {
        this.configDatasource()
      }
    },
    // 打开数据源配置界面
    configDatasource: function() {
      this.datasourceDialogVisible = true
      this.$nextTick(() => {
          this.$refs.datasourceDialog.init()
      })
    }
  },
  mounted() {
  },
  computed:{
    ...mapState({
      themeColor: state=>state.app.themeColor,
      collapse: state=>state.app.collapse
    })
  }
}
</script>

<style scoped lang="scss">
.container {
  position: absolute;
  left: 200px;
  right: 0px;
  height: 60px;
  line-height: 60px;
  .hamburger-container {
    width: 40px;
    float: left;
    border-color: rgba(238, 241, 241, 0.4);
    border-left-width: 1px;
    border-left-style: solid;
    border-right-width: 1px;
    border-right-style: solid;
    color: white;
  }
  .nav-bar {
    margin-left: auto;
    float: left;
    .el-menu {
      background: #0a463480;
    }
  }
  .tool-bar {
    float: right;
    .theme-picker {
      padding-right: 25px;
      font-size: 35px;
    }
    .lang-selector {
      padding-right: 0px;
      font-size: 15px;
      color: #fff;
      cursor: pointer;
    }
    .user-info-dropdown {
      font-size: 30px;
      padding-right: 5px;
      color: #fff;
      cursor: pointer;
      img {
        width: 40px;
        height: 40px;
        border-radius: 10px;
        margin: 10px 0px 10px 10px;
        float: right;
      }
    }
  }
}
.menu-bar-width {
  left: 200px;
}
.menu-bar-collapse-width {
  left: 65px;
}
</style>