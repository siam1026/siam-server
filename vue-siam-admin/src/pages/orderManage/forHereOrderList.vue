<template>
	<section>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top:10px;margin-left:10px;" class="forHereOrderTabs">
      <el-tab-pane ref="waitHandleNum" :label="waitHandleLabel" name="zeroth" class="tab-lable-number">
        <child0 v-if="isChildUpdate0"></child0>
      </el-tab-pane>      
      <el-tab-pane ref="waitPickUpNum" :label="waitPickUpLabel" name="first" class="tab-lable-number">
        <child1 v-if="isChildUpdate1"></child1>
      </el-tab-pane>
      <el-tab-pane label="已完成订单" name="second">
        <child2 v-if="isChildUpdate2"></child2>
      </el-tab-pane>
      <el-tab-pane label="已取消订单" name="third">
        <child3 v-if="isChildUpdate3"></child3>
      </el-tab-pane>
      <el-tab-pane label="全部订单" name="fourth">
        <child4 v-if="isChildUpdate4"></child4>
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import tabZujianChild0 from './forHereTabsChild0.vue'
import tabZujianChild1 from './forHereTabsChild1.vue'
import tabZujianChild2 from './forHereTabsChild2.vue'
import tabZujianChild3 from './forHereTabsChild3.vue'
import tabZujianChild4 from './forHereTabsChild4.vue'
  export default {
    components:{
        child0: tabZujianChild0,
        child1: tabZujianChild1,
        child2: tabZujianChild2,
        child3: tabZujianChild3,
        child4: tabZujianChild4        
    },    
    data() {
      return {
        activeName: 'zeroth',
        isChildUpdate0:true,
        isChildUpdate1:false,
        isChildUpdate2:false,        
        isChildUpdate3:false,
        isChildUpdate4:false,
        //轮询
        timer: null,
        waitHandleLabel: "待制作订单(0)",
        waitPickUpLabel: "待自取订单(0)"
      };
    },
    methods: {
      handleClick(tab, event) {
        if(tab.name == "zeroth") {
            this.isChildUpdate0 = true;
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
        }else if(tab.name == "first") {
            this.isChildUpdate0 = false;
            this.isChildUpdate1 = true;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
        } else if(tab.name == "second") {
            this.isChildUpdate0 = false;
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = true;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
        } else if(tab.name == "third") {
            this.isChildUpdate0 = false;
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = true;
            this.isChildUpdate4 = false;
        } else if(tab.name == "fourth") {
            this.isChildUpdate0 = false;
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = true;
        }
      },
      			getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 查询所有订单标签页的待处理数量
				let vue = this
        let param = {};        
				vue.$http.post(vue, '/rest/admin/order/selectAllTabWaitHandleNum', param,
					(vue, data) => {
            let waitHandleNum = data.data.waitHandleNum;
            let waitPickUpNum = data.data.waitPickUpNum;
            // this.$refs.waitPickUpNum.label = "待自取订单(" + waitPickUpNum + ")";
            //变量值一改动，页面上立马生效，所以就不用上面的ref方式了
            this.waitHandleLabel = "待制作订单(" + waitHandleNum + ")";
            this.waitPickUpLabel = "待自取订单(" + waitPickUpNum + ")";
					},(error, data)=> {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			}      
    },
		mounted() {
      this.getList();
      //每隔一分钟轮询一次
      this.timer = setInterval(this.getList, 60*1000);      
      //开启订单自动打印定时器
      this.$orderPrint.init();      
		},
    beforeDestroy(){
      //清除定时任务，否则切换到其他页面，这定时任务依旧会执行
      clearInterval(this.timer);
    }    
  };
</script>

<style scoped>
/* #tab-zeroth, #tab-first {
  color: red;
} */
</style>