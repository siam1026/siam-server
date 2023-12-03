// 已配送订单
<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-input v-model="searchMsg.queueNo" placeholder="请输入取餐号搜索" clearable></el-input>
				</el-form-item>     
				<el-form-item>
					<el-input v-model="searchMsg.contactPhone" placeholder="请输入联系电话搜索" clearable></el-input>
				</el-form-item>            
				<!-- <el-form-item>
					<el-input v-model="searchMsg.contactRealname" placeholder="请输入联系人姓名搜索" clearable></el-input>
				</el-form-item>                   
				<el-form-item>
					<el-input v-model="searchMsg.fullReductionRuleDescription" placeholder="请输入满减规则名称搜索" clearable></el-input>
				</el-form-item>     
				<el-form-item>
					<el-input v-model="searchMsg.couponsDescription" placeholder="请输入优惠卷名称搜索" clearable></el-input>
				</el-form-item>     
				<el-form-item>
					<el-input v-model="searchMsg.contactStreet" placeholder="请输入收货地址搜索" clearable></el-input>
				</el-form-item>          -->
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
			</el-form>
		</el-col>
		<!--列表-->
		<el-table :data="list" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
			<!-- <el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>		
			</el-table-column>       -->
      <el-table-column prop="queueNo" label="取餐号"></el-table-column>
      <el-table-column prop="description" label="订单描述"></el-table-column>
      <el-table-column prop="goodsTotalPrice" label="订单总额">
				<template scope="scope">
					<span>{{scope.row.goodsTotalPrice + scope.row.packingCharges + scope.row.deliveryFee}}元</span>
				</template>      
      </el-table-column>      
      <el-table-column prop="actualPrice" label="实付款" :formatter="addUnit"></el-table-column>
      <el-table-column prop="goodsTotalQuantity" label="商品总数量">
				<template scope="scope">
					<span>{{scope.row.goodsTotalQuantity}}件</span>
				</template>    
      </el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>      
      <el-table-column prop="contactRealname" label="联系人姓名" width="120"></el-table-column>
			<el-table-column prop="contactPhone" label="联系电话" width="120"></el-table-column>
      <el-table-column prop="contactProvince" label="派送地址" width="190">
				<template slot-scope="scope">
					{{scope.row.contactProvince}} - {{scope.row.contactCity}} - {{scope.row.contactArea}} - {{scope.row.contactStreet}} - {{scope.row.contactHouseNumber}}
				</template>
      </el-table-column>			      
      <el-table-column prop="status" label="订单状态" width="100" :formatter="formatOrder"></el-table-column>
			<el-table-column prop="createTime" label="下单时间" :formatter="formatTime" width="190"></el-table-column>
			<el-table-column label="操作" fixed="right">
				<template slot-scope="scope">
          <el-button size="small" @click="gotoOtherPage('view', scope.row)">查看详情</el-button>
          <!-- <el-button size="small" @click="handleEdit(scope.row)">标记完成</el-button> -->
          <el-button size="small" @click="handleEdit(scope.row)">配送完成</el-button>
					<!-- <el-button size="small" v-if="scope.row.status == 4" @click="openDialog(scope.row.id)">申诉处理</el-button> -->
					<!-- <el-button size="small" v-if="scope.row.status == 4" @click="openDialog(scope.row.id, 1)">退款</el-button> -->
					<!-- <el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button> -->
				</template>
			</el-table-column>
		</el-table>
		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="searchMsg.pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>
		<!--编辑界面-->
		<el-dialog :title="dialogTitle" :visible.sync="editFormVisible" @close="editFormClose" :close-on-click-modal="false">
			<el-form size="small" :model="editForm" class="editForm" label-width="80px" style="width: 80%;">
        <el-form-item prop="dealadvise" label="处理意见">
          <el-input type="textarea"  placeholder="处理意见" v-model="editForm.dealadvise"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="dealOption">保存</el-button>
        </el-form-item>
			</el-form>
			<!-- <div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">关闭</el-button>
			</div> -->
		</el-dialog>
	</section>
</template>
<script>
	export default {
		data() {
			return {
				searchMsg: {
					pageNo: 1,
					pageSize: 20
				},
				list: [],
				total: 0,
				listLoading: false,
				sels: [],//列表选中列
				editFormVisible: false,//编辑界面是否显示
				//编辑界面数据
				editForm: {},
        searchData:[],
        dealtype: '',
        dealId: '',
        dialogTitle: '申诉处理',
        //轮询
        timer: null
			}
		},
		methods: {
      cellStyle({row, column, rowIndex, columnIndex}){
        return "text-align:center";
      },
      headerCellStyle({row, rowIndex}){
        return "text-align:center";
      },      
			gotoOtherPage(type, row) {
				if(type === 'view') {
					this.$router.push({path:'orderDetail', query:{id: row.id}})
				}
			},
      dealOption() {
        let vue = this
        let url = ''
        let param = {
          id: vue.dealId,
          dealadvise: vue.editForm.dealadvise
        }
        vue.dealtype ? url = '/rest/admin/order/updateOrderByBack' : url = '/rest/admin/order/updateByDealadvise'
        vue.$http.post( vue, url, param,
          (vue, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
            vue.editFormVisible = false
            vue.getList()
          }, (error, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
          }
        )
      },
      openDialog(id, type = 0) {
        this.editFormVisible = true
        this.dealId = id
        this.dealtype = type
        type ? this.dialogTitle = '退款' : '申诉处理'
      },
      editFormClose() {
        this.editForm = {}
      },
      addStand() { // 新增规格
        let vue = this
        let param = Object.assign({}, vue.editForm)
        let regEn = /^[1-9]\d*$/;
        if (!regEn.test(param.sort)) {
          vue.$message({
            showClose: true,
            message: '输入正整数序号',
            type: 'error'
          });
          return false
        }
        if (!param.stand) {
          vue.$message({
            showClose: true,
            message: '请输入规格',
            type: 'error'
          });
          return false
        }
        vue.$http.post( vue, '/rest/admin/stand/update', param,
          (vue, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
            vue.editFormVisible = false
            vue.getList()
          }, (error, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
          }
        )
      },
      addUnit(row, column) { // 添加单位
        return (row[column.property] || 0) + '元'
      },
      formatDescription(row, column) { // 添加单位
        //let replaceReg = new RegExp("&nbsp;", 'g');
        //return row.description.replace(/1/g, " ");
        if(row.description!=undefined){
          return row.description.replace(/&nbsp;/g, " ")
        }else{
          return "-";
        }
      },      
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				return this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm');
			},
      formatOrder (row, column) { // 0-待配送 1-待收货 2已完成 3-已取消 4-申诉中 5-已退款 6-申诉已完成
        let status = row[column.property] 
        switch (status) {
          case 1:
            return '未付款'
            break;
          case 2:
            return '待处理'
            break;
          case 3:
            return '待自取'
            break;
          case 4:
              return '待配送'
            break;
          case 5:
            return '已配送'
            break;
          case 6:
            return '已完成'
            break;
          case 7:
            return '售后处理中'
            break;
          case 8:
            return '已退款'
            break;
          case 9:
            return '售后处理完成'
            break;         
          case 10:
            return '已取消(未支付)'
            break;                  
          case 11:
            return '已取消(已支付)'
            break;                       
        }
      },
			handleCurrentChange(val) {
				this.searchMsg.pageNo = val;
				this.getList();
			},
						getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 获取订单列表
				let vue = this
        let param = Object.assign({}, vue.searchMsg)

        //只查询status=5 已配送
        param.shoppingWay=2;                
        param.status=5;

        vue.listLoading = true;
				vue.$http.post(vue, '/rest/admin/order/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
					},(error, data)=> {
						vue.listLoading = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			},
			handleDel (id) { // 删除
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
				let vue = this;
				vue.$http.post(vue, '/rest/admin/stand/deleteById', {id},
					function(vue, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
            vue.getList()
					}, function(error, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
					}
				)
					// this.listLoading = false;
				}).catch(() => {});
			},
			handleEdit (row) { // 显示编辑界面
        let vue = this;
        let param = {
          id: row.id,
          flag: 4
        }
				vue.$http.post(vue, '/rest/admin/order/updateStatus', param,
					function(vue, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
            vue.getList()
					}, function(error, data) {
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
	}

</script>

<style scoped>
.el-button+.el-button {
  margin-top: 5px;
  margin-left: 0;
}
.editForm .el-input {
  width: 300px;
}
</style>