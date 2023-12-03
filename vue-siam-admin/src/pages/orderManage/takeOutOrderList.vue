<template>
	<section>
    <el-tabs v-model="activeName" @tab-click="handleClick" style="margin-top:10px;margin-left:10px;" class="takeOutOrderTabs">
      <el-tab-pane ref="waitDeliveryNum" :label="waitDeliveryLabel" name="first" class="tab-lable-number">
        <child1 v-if="isChildUpdate1"></child1>
      </el-tab-pane>
      <el-tab-pane ref="deliveredNum" :label="deliveredLabel" name="second" class="tab-lable-number">
        <child2 v-if="isChildUpdate2"></child2>
      </el-tab-pane>
      <el-tab-pane label="已完成订单" name="third">
        <child3 v-if="isChildUpdate3"></child3>
      </el-tab-pane>      
      <el-tab-pane label="已取消订单" name="fourth">
        <child4 v-if="isChildUpdate4"></child4>
      </el-tab-pane>
      <el-tab-pane label="全部订单" name="fifth">
        <child5 v-if="isChildUpdate5"></child5>
      </el-tab-pane>
    </el-tabs>
  </section>
</template>
<script>
import tabZujianChild1 from './takeOutTabsChild1.vue'
import tabZujianChild2 from './takeOutTabsChild2.vue'
import tabZujianChild3 from './takeOutTabsChild3.vue'
import tabZujianChild4 from './takeOutTabsChild4.vue'
import tabZujianChild5 from './takeOutTabsChild5.vue'
  export default {
    components:{
        child1: tabZujianChild1,
        child2: tabZujianChild2,
        child3: tabZujianChild3,
        child4: tabZujianChild4,
        child5: tabZujianChild5
    },    
    data() {
      return {
        activeName: 'first',
        isChildUpdate1:true,
        isChildUpdate2:false,        
        isChildUpdate3:false,
        isChildUpdate4:false,
        isChildUpdate5:false,
        //轮询
        timer: null,
        waitDeliveryLabel: "待配送订单(0)",
        deliveredLabel: "已配送订单(0)"
      };
    },
    methods: {
      handleClick(tab, event) {
        if(tab.name == "first") {
            this.isChildUpdate1 = true;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
            this.isChildUpdate5 = false;
        } else if(tab.name == "second") {
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = true;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
            this.isChildUpdate5 = false;
        } else if(tab.name == "third") {
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = true;
            this.isChildUpdate4 = false;
            this.isChildUpdate5 = false;
        } else if(tab.name == "fourth") {
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = true;
            this.isChildUpdate5 = false;
        } else if(tab.name == "fifth") {
            this.isChildUpdate1 = false;
            this.isChildUpdate2 = false;
            this.isChildUpdate3 = false;
            this.isChildUpdate4 = false;
            this.isChildUpdate5 = true;
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
            let waitDeliveryNum = data.data.waitDeliveryNum;
            let deliveredNum = data.data.deliveredNum;
            // this.$refs.waitDeliveryNum.label = "待配送订单(" + waitDeliveryNum + ")";
            // this.$refs.deliveredNum.label = "已配送订单(" + deliveredNum + ")";
            //变量值一改动，页面上立马生效，所以就不用上面的ref方式了
            this.waitDeliveryLabel = "待配送订单(" + waitDeliveryNum + ")";
            this.deliveredLabel = "已配送订单(" + deliveredNum + ")";
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
#tab-first {
  color: red;
}
</style>