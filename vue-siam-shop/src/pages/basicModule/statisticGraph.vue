<template>
  <div class="app-container">
    <!-- <div class="address-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">后台项目</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall">mall</a>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">前端项目</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall-admin-web">mall-admin-web</a>
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="out-border">
            <div class="layout-title">学习教程</div>
            <div class="color-main address-content">
              <a href="https://github.com/macrozheng/mall-learning">mall-learning</a>
            </div>
          </div>
        </el-col>
      </el-row>
    </div> -->
    <div class="total-layout">
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="total-frame">
            <img src="../../assets/home_order.png" class="total-icon">
            <div class="total-title">今日支付订单</div>
            <div class="total-value">{{todayList.dayCountPaid || 0}}</div>   
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <img src="../../assets/home_today_amount.png" class="total-icon">
            <div class="total-title">今日实际收入</div>
            <div class="total-value">￥{{todayList.daySumMerchantIncome || 0}}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <img src="../../assets/home_order.png" class="total-icon">
            <div class="total-title">今日进店人数</div>
            <div class="total-value">{{todayList.todayCountIntoShop || 0}}</div>   
          </div>
        </el-col>
        <el-col :span="6">
          <div class="total-frame">
            <img src="../../assets/home_order.png" class="total-icon">
            <div class="total-title">今日加购商品</div>
            <div class="total-value">{{todayList.todayCountShoppingCartGoodsNumber || 0}}</div>   
          </div>
        </el-col>                
        <!-- <el-col :span="6">
          <div class="total-frame">
            <img src="../../assets/home_yesterday_amount.png" class="total-icon">
            <div class="total-title">昨日销售总额</div>
            <div class="total-value">￥5000.00</div>
          </div>
        </el-col> -->
        <!-- <el-col :span="6">-->
          <!--<div class="total-frame">-->
            <!--<svg-icon icon-class="total-week" class="total-icon">-->
            <!--</svg-icon>-->
            <!--<div class="total-title">近7天销售总额</div>-->
            <!--<div class="total-value">￥50000.00</div>-->
          <!--</div>-->
        <!--</el-col> -->
      </el-row>
    </div>
    <!-- <el-card class="mine-layout">
      <div style="text-align: center">
        <img width="150px" height="150px" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/banner/qrcode_for_macrozheng_258.jpg">
      </div>
      <div style="text-align: center">mall全套学习教程连载中！</div>
      <div style="text-align: center;margin-top: 5px"><span class="color-main">关注公号</span>，第一时间获取。</div>
    </el-card> -->
    <div class="un-handle-layout">
      <div class="layout-title">待处理事项</div>
      <div class="un-handle-content">
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待制作订单(自取)</span>
              <el-tag  style="float: right" class="font-tag">{{todayList.waitHandleOrderCount || 0}}</el-tag>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待配送订单(外卖)</span>
           <el-tag style="float: right" class="font-tag">{{todayList.waitDeliverOrderCount || 0}}</el-tag>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">已完成订单</span>
            <el-tag style="float: right" class="font-tag">{{todayList.completedOrderCount || 0}}</el-tag>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待处理退款申请</span>
                  <el-tag  style="float: right" class="font-tag">{{todayList.handleOrderRefundCount || 0}}</el-tag>
            </div>
          </el-col>
          <!-- <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">新缺货登记</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待处理退款申请</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col> -->
        </el-row>
        <!-- <el-row :gutter="20">
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">已发货订单</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">待处理退货订单</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
          <el-col :span="8">
            <div class="un-handle-item">
              <span class="font-medium">广告位即将到期</span>
              <span style="float: right" class="color-danger">(10)</span>
            </div>
          </el-col>
        </el-row> -->
      </div>
    </div>
    <div class="overview-layout">
      <el-row :gutter="20">
        <el-col :span="12">
          <div class="out-border">
            <div class="layout-title">商品总览</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.underShelfGoodsCount || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.onShelfGoodsCount || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.sellOutGoodsCount || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.allGoodsCount || 0}}</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">已下架</el-col>
                <el-col :span="6" class="overview-item-title">已上架</el-col>
                <el-col :span="6" class="overview-item-title">库存售罄</el-col>
                <el-col :span="6" class="overview-item-title">全部商品</el-col>
              </el-row>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="out-border">
            <div class="layout-title">指数总览</div>
            <div style="padding: 40px">
              <el-row>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.perCustomerTransaction || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.orderConversionRate || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.orderPaymentConversionRate || 0}}</el-col>
                <el-col :span="6" class="color-danger overview-item-value">{{todayList.paymentConversionRate || 0}}</el-col>
              </el-row>
              <el-row class="font-medium">
                <el-col :span="6" class="overview-item-title">客单价 <span class="text-tip">成交金额/成交订单数</span></el-col>
                <el-col :span="6" class="overview-item-title">下单转化率 <span class="text-tip">下单人数/访问人数</span></el-col>
                <el-col :span="6" class="overview-item-title">下单-支付转化率 <span class="text-tip">支付人数/下单人数</span></el-col>
                <el-col :span="6" class="overview-item-title">支付转化率 <span class="text-tip">支付人数/访问人数</span></el-col>
              </el-row>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    <div class="statistics-layout">
      <div class="layout-title">订单统计</div>
      <el-row>
        <el-col :span="4">
          <div style="padding: 20px">
            <div>
              <div style="color: #909399;font-size: 14px">本月订单总数</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{todayList.thisMonthCountPaid || 0}}</div>
              <div>
                <span class="color-success" style="font-size: 14px">{{todayList.thisMonthCountPaidCompare || 0}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本周订单总数</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{todayList.thisWeekCountPaid || 0}}</div>
              <div>
                <span class="color-danger" style="font-size: 14px">{{todayList.thisWeekCountPaidCompare || 0}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上周</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本月销售总额</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{todayList.thisMonthSumActualPrice || 0}}</div>
              <div>
                <span class="color-success" style="font-size: 14px">{{todayList.thisMonthSumActualPriceCompare || 0}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上月</span>
              </div>
            </div>
            <div style="margin-top: 20px;">
              <div style="color: #909399;font-size: 14px">本周销售总额</div>
              <div style="color: #606266;font-size: 24px;padding: 10px 0">{{todayList.thisWeekSumActualPrice || 0}}</div>
              <div>
                <span class="color-danger" style="font-size: 14px">{{todayList.thisWeekSumActualPriceCompare || 0}}</span>
                <span style="color: #C0C4CC;font-size: 14px">同比上周</span>
              </div>
            </div>
          </div>
        </el-col>
        <el-col :span="20">
          <div style="padding: 10px;border-left:1px solid #DCDFE6">
            <el-date-picker
              style="float: right;z-index: 1"
              size="small"
              v-model="orderCountDate"
              type="daterange"
              align="right"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              @change="handleDateChange"
              :picker-options="pickerOptions">
            </el-date-picker>
            <div>
              <ve-line
                :data="chartData"
                :legend-visible="false"
                :loading="loading"
                :data-empty="dataEmpty"
                :settings="chartSettings"></ve-line>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  // import VCharts from 'v-charts'
  import {str2Date} from '../../utils/date';
//   import img_home_order from '/assets/home_order.png';
//   import img_home_today_amount from '/assets/home_today_amount.png';
//   import img_home_yesterday_amount from '/assets/home_yesterday_amount.png';
  const DATA_FROM_BACKEND = {
    columns: ['date', 'orderCount','orderAmount'],
    rows: [
      // {date: '2020-11-01', orderCount: 10, orderAmount: 1093},
      // {date: '2020-11-02', orderCount: 20, orderAmount: 2230},
      // {date: '2020-11-03', orderCount: 33, orderAmount: 3623},
      // {date: '2020-11-04', orderCount: 50, orderAmount: 6423},
      // {date: '2020-11-05', orderCount: 80, orderAmount: 8492},
      // {date: '2020-11-06', orderCount: 60, orderAmount: 6293},
      // {date: '2020-11-07', orderCount: 20, orderAmount: 2293},
      // {date: '2020-11-08', orderCount: 60, orderAmount: 6293},
      // {date: '2020-11-09', orderCount: 50, orderAmount: 5293},
      // {date: '2020-11-10', orderCount: 30, orderAmount: 3293},
      // {date: '2020-11-11', orderCount: 20, orderAmount: 2293},
      // {date: '2020-11-12', orderCount: 80, orderAmount: 8293},
      // {date: '2020-11-13', orderCount: 100, orderAmount: 10293},
      // {date: '2020-11-14', orderCount: 0, orderAmount: 0},
      // {date: '2020-11-15', orderCount: 0, orderAmount: 0},
      // {date: '2020-11-16', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-17', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-18', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-19', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-20', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-21', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-22', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-23', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-24', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-25', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-26', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-27', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-28', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-29', orderCount: 40, orderAmount: 4293},
      // {date: '2020-11-30', orderCount: 40, orderAmount: 4293}
    ]
  };
  export default {
    name: 'home',
    data() {
      return {
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              // const end = new Date();
              // let start = new Date();
              // start.setFullYear(2020);
              // start.setMonth(10);
              // start.setDate(12);
              // end.setTime(start.getTime() + 3600 * 1000 * 24 * 7);
              // picker.$emit('pick', [start, end]);

              const end = new Date();

              let start = new Date();
              start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 7);

              picker.$emit('pick', [start, end]);

            }
          }, {
            text: '最近一月',
            onClick(picker) {
              // const end = new Date();
              // let start = new Date();
              // start.setFullYear(2020);
              // start.setMonth(10);
              // start.setDate(12);
              // end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
              // picker.$emit('pick', [start, end]);

              const end = new Date();

              let start = new Date();
              start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30);

              picker.$emit('pick', [start, end]);

            }
          }, {
            text: '最近半年',
            onClick(picker) {
              // const end = new Date();
              // let start = new Date();
              // start.setFullYear(2020);
              // start.setMonth(10);
              // start.setDate(12);
              // end.setTime(start.getTime() + 3600 * 1000 * 24 * 30);
              // picker.$emit('pick', [start, end]);

              const end = new Date();

              let start = new Date();
              start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 180);

              picker.$emit('pick', [start, end]);

            }
          }]
        },
        orderCountDate: '',
        chartSettings: {
          xAxisType: 'time',
          area:true,
          axisSite: { right: ['orderAmount']},
        labelMap: {'orderCount': '订单数量', 'orderAmount': '订单金额'}},
        chartData: {
          columns: [],
          rows: []
        },
        loading: false,
        dataEmpty: false,
        todayList:{}, //今日数据
      }
    },
    created(){
      this.initOrderCountDate();
      let vue = this
      //查询基础统计数据
      vue.$http.post(vue, '/rest/merchant/statistics/todayStatistic', {},
        (vue, data) => {
          vue.todayList.dayCountPaid = data.data.dayCountPaid;
          vue.todayList.daySumMerchantIncome = data.data.daySumMerchantIncome;
          vue.todayList.todayCountIntoShop = data.data.todayCountIntoShop;
          vue.todayList.todayCountShoppingCartGoodsNumber = data.data.todayCountShoppingCartGoodsNumber;
          vue.todayList.waitHandleOrderCount = data.data.waitHandleOrderCount;
          vue.todayList.waitDeliverOrderCount = data.data.waitDeliverOrderCount;
          vue.todayList.completedOrderCount = data.data.completedOrderCount;
          vue.todayList.handleOrderRefundCount = data.data.handleOrderRefundCount;
          vue.todayList.underShelfGoodsCount = data.data.underShelfGoodsCount;
          vue.todayList.onShelfGoodsCount = data.data.onShelfGoodsCount;
          vue.todayList.sellOutGoodsCount = data.data.sellOutGoodsCount;
          vue.todayList.allGoodsCount = data.data.allGoodsCount;
          vue.todayList.perCustomerTransaction = data.data.perCustomerTransaction;
          vue.todayList.orderConversionRate =  Number(data.data.orderConversionRate * 100).toFixed(0) + "%";
          vue.todayList.orderPaymentConversionRate = Number(data.data.orderPaymentConversionRate * 100).toFixed(0) + "%";
          vue.todayList.paymentConversionRate = Number(data.data.paymentConversionRate * 100).toFixed(0) + "%";
        },(error, data)=> {
          vue.$message({
            showClose: true,
            message: data.message,
            type: 'error'
          });
        }
      )

      //查询订单统计数据
      vue.$http.post(vue, '/rest/merchant/order/statistic', {},
        (vue, data) => {
          DATA_FROM_BACKEND.rows = data.data.resultList;
          vue.todayList.thisMonthCountPaid = data.data.thisMonthCountPaid;
          vue.todayList.lastMonthCountPaid = data.data.lastMonthCountPaid;
          vue.todayList.thisWeekCountPaid = data.data.thisWeekCountPaid;
          vue.todayList.lastWeekCountPaid = data.data.lastWeekCountPaid;
          vue.todayList.thisMonthSumActualPrice = data.data.thisMonthSumActualPrice;
          vue.todayList.lastMonthSumActualPrice = data.data.lastMonthSumActualPrice;
          vue.todayList.thisWeekSumActualPrice = data.data.thisWeekSumActualPrice;
          vue.todayList.lastWeekSumActualPrice = data.data.lastWeekSumActualPrice;

          //计算同比情况
          if(vue.todayList.lastMonthCountPaid == 0){
            vue.todayList.thisMonthCountPaidCompare = "无";
          }else if(vue.todayList.thisMonthCountPaid > vue.todayList.lastMonthCountPaid){
            let compareNum = (vue.todayList.thisMonthCountPaid - vue.todayList.lastMonthCountPaid) / vue.todayList.lastMonthCountPaid;
            vue.todayList.thisMonthCountPaidCompare = "+" + Number(compareNum * 100).toFixed(0) + "%";
          }else{
            let compareNum = (vue.todayList.lastMonthCountPaid - vue.todayList.thisMonthCountPaid) / vue.todayList.lastMonthCountPaid;
            vue.todayList.thisMonthCountPaidCompare = "-" + Number(compareNum * 100).toFixed(0) + "%";            
          }

          if(vue.todayList.lastWeekCountPaid == 0){
            vue.todayList.thisWeekCountPaidCompare = "无";
          }else if(vue.todayList.thisWeekCountPaid > vue.todayList.lastWeekCountPaid){
            let compareNum = (vue.todayList.thisWeekCountPaid - vue.todayList.lastWeekCountPaid) / vue.todayList.lastWeekCountPaid;
            vue.todayList.thisWeekCountPaidCompare = "+" + Number(compareNum * 100).toFixed(0) + "%";
          }else{
            let compareNum = (vue.todayList.lastWeekCountPaid - vue.todayList.thisWeekCountPaid) / vue.todayList.lastWeekCountPaid;
            vue.todayList.thisWeekCountPaidCompare = "-" + Number(compareNum * 100).toFixed(0) + "%";            
          }

          if(vue.todayList.lastMonthSumActualPrice == 0){
            vue.todayList.thisMonthSumActualPriceCompare = "无";
          }else if(vue.todayList.thisMonthSumActualPrice > vue.todayList.lastMonthSumActualPrice){
            let compareNum = (vue.todayList.thisMonthSumActualPrice - vue.todayList.lastMonthSumActualPrice) / vue.todayList.lastMonthSumActualPrice;
            vue.todayList.thisMonthSumActualPriceCompare = "+" + Number(compareNum * 100).toFixed(0) + "%";
          }else{
            let compareNum = (vue.todayList.lastMonthSumActualPrice - vue.todayList.thisMonthSumActualPrice) / vue.todayList.lastMonthSumActualPrice;
            vue.todayList.thisMonthSumActualPriceCompare = "-" + Number(compareNum * 100).toFixed(0) + "%";            
          }

          if(vue.todayList.lastWeekSumActualPrice == 0){
            vue.todayList.thisWeekSumActualPriceCompare = "无";
          }else if(vue.todayList.thisWeekSumActualPrice > vue.todayList.lastWeekSumActualPrice){
            let compareNum = (vue.todayList.thisWeekSumActualPrice - vue.todayList.lastWeekSumActualPrice) / vue.todayList.lastWeekSumActualPrice;
            vue.todayList.thisWeekSumActualPriceCompare = "+" + Number(compareNum * 100).toFixed(0) + "%";
          }else{
            let compareNum = (vue.todayList.lastWeekSumActualPrice - vue.todayList.thisWeekSumActualPrice) / vue.todayList.lastWeekSumActualPrice;
            vue.todayList.thisWeekSumActualPriceCompare = "-" + Number(compareNum * 100).toFixed(0) + "%";            
          }                              

          this.getData();
        },(error, data)=> {
          vue.$message({
            showClose: true,
            message: data.message,
            type: 'error'
          });
        }
      )
    },
    methods:{
      handleDateChange(){
        this.getData();
      },
      initOrderCountDate(){
        // let start = new Date();
        // start.setFullYear(2020);
        // start.setMonth(10);
        // start.setDate(12);
        // const end = new Date();
        // end.setTime(start.getTime() + 1000 * 60 * 60 * 24 * 7);
        // this.orderCountDate=[start,end];

        //默认展示近两周的订单数据
        const end = new Date();

        let start = new Date();
        start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 14);

        this.orderCountDate=[start, end];
      },
      getData(){
        setTimeout(() => {
          this.chartData = {
            columns: ['date', 'orderCount','orderAmount'],
            rows: []
          };
          for(let i=0;i<DATA_FROM_BACKEND.rows.length;i++){
            let item=DATA_FROM_BACKEND.rows[i];
            let currDate=str2Date(item.date);
            let start=this.orderCountDate[0];
            let end=this.orderCountDate[1];
            if(currDate.getTime()>=start.getTime()&&currDate.getTime()<=end.getTime()){
              this.chartData.rows.push(item);
            }
          }
          this.dataEmpty = false;
          this.loading = false
        }, 1000)
      }
    }
  }
</script>

<style scoped>
  .app-container {
    margin-top: 40px;
    /* margin-left: 120px; */
    /* margin-right: 120px; */
  }

  .address-layout {
  }

  .total-layout {
    margin-top: 20px;
  }

  .total-frame {
    border: 1px solid #DCDFE6;
    padding: 20px;
    height: 100px;
  }

  .total-icon {
    color: #409EFF;
    width: 60px;
    height: 60px;
  }

  .total-title {
    position: relative;
    font-size: 16px;
    color: #909399;
    left: 70px;
    top: -50px;
  }

  .total-value {
    position: relative;
    font-size: 18px;
    color: #606266;
    left: 70px;
    top: -40px;
  }

  .un-handle-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }

  .layout-title {
    color: #606266;
    padding: 15px 20px;
    background: #F2F6FC;
    font-weight: bold;
  }

  .un-handle-content {
    padding: 20px 40px;
  }
 
  .color-danger {
    color: #f56c6c;
}
.font-tag {
    color: #f56c6c;
    font-size: 18px;
}
.color-success {
    color: #67c23a;
}


  .un-handle-item {
    border-bottom: 1px solid #EBEEF5;
    padding: 10px;
     font-size:20px;
  }

  .overview-layout {
    margin-top: 20px;
  }

  .overview-item-value {
    font-size: 24px;
    text-align: center;
  }

  .overview-item-title {
    margin-top: 10px;
    text-align: center;
  }

  .out-border {
    border: 1px solid #DCDFE6;
  }

  .statistics-layout {
    margin-top: 20px;
    border: 1px solid #DCDFE6;
  }
  .mine-layout {
    position: absolute;
    right: 140px;
    top: 107px;
    width: 250px;
    height: 235px;
  }
  .address-content{
    padding: 20px;
    font-size: 18px
  }
</style>
