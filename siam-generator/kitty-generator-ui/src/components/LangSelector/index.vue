<template>

  <el-dropdown class="lang-selector" @command="handleCommand" trigger="click">
    <el-button id="language" type="primary" class="el-dropdown-link" icon="fa fa-language fa-2x">
    </el-button>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="zh_cn:中文">中文</el-dropdown-item>
      <el-dropdown-item command="en_us:English">English</el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
  export default {
    methods: {
      // 语言切换
      handleCommand(command) {
        let array = command.split(':')
        let lang = array[0] === '' ? 'zh_cn' : array[0]
        this.$i18n.locale = lang
      }
    }
  }
</script>