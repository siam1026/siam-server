<template>
  <div class="container" :class="$store.state.app.collapse?'menu-bar-collapse-width':'menu-bar-width'">
    <bread-crumb></bread-crumb>
    <transition name="fade" mode="out-in">
        <router-view class="content"></router-view>
    </transition>
  </div>
</template>

<script>
import BreadCrumb from "@/components/BreadCrumb"
export default {
  components:{
        BreadCrumb
  }
}
</script>

<style scoped lang="scss">
.container {
  position: absolute;
  top: 60px;
  left: 0px;
  // background: rgba(202, 243, 227, 0.1);
  .breadcrumb {
    padding: 10px;
    border-color: rgba(148, 145, 147, 0.2);
    border-bottom-width: 1px;
    border-bottom-style: solid;
  }
  .content {
    margin-bottom: 5px;
    padding-top: 5px;
    margin-left: 2px;
    // background: rgba(209, 212, 212, 0.1);
  }
}
.menu-bar-width {
  left: 200px;
}
.menu-bar-collapse-width {
  left: 65px;
}
</style>