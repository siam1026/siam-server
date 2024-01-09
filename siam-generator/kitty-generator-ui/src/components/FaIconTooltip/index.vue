  
<template>
  <el-tooltip class="tooltip" placement="top" effect="light">
    <div slot="content">
      <p>推荐使用 Font Aweson 图标</p>
      <p>使用步骤：</p>
      <p>1.进入 <a href="http://www.fontawesome.com.cn/faicons/" target="_blank">Font Aweson</a> 页面</p>
      <p>2.查找到需要的图标,点击查看。</p>
      <p>3.复制图片样式到此处。</p>
      <p>示例：fa fa-home fa-lg</p></div>
    <i class="el-icon-warning"></i>
  </el-tooltip>
</template>

<script>
export default {
  data() {
    return {
    };
  },
  methods: {

  },
  mounted() {

  }
};
</script>

<style scoped lang="scss">
.tooltip {
  padding: 10px;
}
</style>