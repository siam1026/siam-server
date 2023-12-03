<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-select v-model="searchMsg.shoppingWay" placeholder="请选择购物方式搜索" clearable>
						<el-option label="自取" :value="1"></el-option>
						<el-option label="发货" :value="2"></el-option>    
					</el-select>
				</el-form-item>           
				<el-form-item>
					<el-input v-model="searchMsg.orderNo" placeholder="请输入订单编号搜索" clearable></el-input>
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
				</el-form-item> -->
				<el-form-item label="">
					<el-date-picker
					v-model="searchMsg.createTime"
					type="daterange"
					align="right"
					unlink-panels
					range-separator="至"
					start-placeholder="开始日期"
					end-placeholder="结束日期"
					:picker-options="pickerOptions">
					</el-date-picker>
				</el-form-item>        
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
      
        <el-table-column type="expand">
      <template slot-scope="props">
      	<!-- 内层为用户列表数组 -->
        <el-table :data="props.row.orderDetailList" style="width: 100%">
         <el-table-column label="商品主图" prop="mainImage">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
				</template>
			</el-table-column>
      		<el-table-column prop="goodsName" label="商品名称"></el-table-column>
      		<el-table-column prop="specList" label="商品规格" :formatter="formatSpecList"></el-table-column>
			<el-table-column prop="price" label="价格" :formatter="addUnit"></el-table-column>
      		<el-table-column prop="number" label="购买数量"  :formatter="formatNumber"></el-table-column>
      		<el-table-column prop="subtotal" label="小计" :formatter="addUnit"></el-table-column>
        </el-table>
      </template>
    </el-table-column>

      <el-table-column prop="orderNo" label="订单编号"></el-table-column>
      <el-table-column prop="description" label="订单描述"></el-table-column>
      <el-table-column prop="goodsTotalPrice" label="订单总额">
				<template scope="scope">
					<span>{{scope.row.goodsTotalPrice + scope.row.deliveryFee}}元</span>
				</template>      
      </el-table-column>   
       <el-table-column prop="paymentMode" label="支付方式">
				<template scope="scope">
					<span v-if="scope.row.paymentMode == 1">微信支付</span>
          <span v-else-if="scope.row.paymentMode == 2">平台余额</span>
          <span v-else-if="scope.row.paymentMode == 3">平台积分</span>
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
      
      <el-table-column prop="refundType" label="退款类型">
				<template slot-scope="scope">
          <span v-if="scope.row.refundType == 1">未发货订单申请退款</span>
          <span v-if="scope.row.refundType == 2">已发货订单申请售后-仅退款</span>
          <span v-if="scope.row.refundType == 3">已发货订单申请售后-退货退款</span>
				</template>
      </el-table-column>	
      
      <el-table-column prop="refundWay" label="退款方式">
				<template slot-scope="scope">
          <span v-if="scope.row.refundWay == 1">全额退款</span>
          <span v-if="scope.row.refundWay == 2">部分退款</span>
				</template>
      </el-table-column>	
      
      <el-table-column prop="refundReason" label="退款原因">
				<template slot-scope="scope">
          <span v-if="scope.row.refundReason == 1">订单信息拍错（规格/尺码/颜色等）</span>
          <span v-if="scope.row.refundReason == 2">我不想要了</span>
          <span v-if="scope.row.refundReason == 3">地址/电话信息填写错误</span>
          <span v-if="scope.row.refundReason == 4">没用/少用优惠</span>
          <span v-if="scope.row.refundReason == 5">缺货</span>
          <span v-if="scope.row.refundReason == 31">缺货</span>
          <span v-if="scope.row.refundReason == 32">协商一致退款</span>
          <span v-if="scope.row.refundReason == 33">未按约定时间发货</span>
          <span v-if="scope.row.refundReason == 34">排错/多拍/不想要</span>
          <span v-if="scope.row.refundReason == 35">其他</span>
          <span v-if="scope.row.refundReason == 61">效果不好/不喜欢</span>
          <span v-if="scope.row.refundReason == 62">材质不符</span>
          <span v-if="scope.row.refundReason == 63">尺寸不符</span>
          <span v-if="scope.row.refundReason == 64">外观破损</span>
          <span v-if="scope.row.refundReason == 65">颜色/款式图案与描述不符</span>
          <span v-if="scope.row.refundReason == 66">物流问题</span>
          <span v-if="scope.row.refundReason == 67">和预期不符</span>
          <span v-if="scope.row.refundReason == 68">服务承诺/态度</span>
          <span v-if="scope.row.refundReason == 69">质量问题</span>
          <span v-if="scope.row.refundReason == 70">其他</span>                        
				</template>
      </el-table-column>

      <el-table-column prop="refundAmount" label="退款金额" :formatter="addUnit"></el-table-column>
      
      <el-table-column prop="refundStatus" label="退款状态">
				<template slot-scope="scope">
          <span v-if="scope.row.refundStatus == 1">退款申请已提交</span>
          <span v-if="scope.row.refundStatus == 2">等待商家处理</span>
          <span v-if="scope.row.refundStatus == 3">商家拒绝退款</span>
          <span v-if="scope.row.refundStatus == 4">等待平台处理</span>
          <span v-if="scope.row.refundStatus == 5">平台拒绝退款，退款已关闭</span>
          <span v-if="scope.row.refundStatus == 6">退款已关闭</span>
          <span v-if="scope.row.refundStatus == 7">退款成功</span>
				</template>
      </el-table-column>

      <el-table-column prop="refundCreateTime" label="申请退款时间" :formatter="formatTime" width="190"></el-table-column>

			<el-table-column label="操作" fixed="right">
				<template slot-scope="scope">
					<!-- <el-button size="small" v-if="scope.row.status == 0" @click="handleEdit(scope.row)">发货</el-button> -->
					<!-- <el-button size="small" @click="openDialog(scope.row.id)">售后处理</el-button> -->
          <el-button size="small" v-if="scope.row.refundStatus==4" type="primary" @click="handleCheck(scope.row)">处理</el-button>
          <!-- <el-button size="small" @click="gotoOtherPage('view', scope.row)">退款详情</el-button> -->
          <el-button size="small" @click="gotoOtherPage('view', scope.row)">订单详情</el-button>         	
          <el-button size="small" v-if="scope.row.refundType == 2 || scope.row.refundType == 3" @click="handleLogistics(scope.row)">查看物流</el-button>
					<!-- <el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button> -->
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination
				@size-change="handleSizeChange"
				@current-change="handleCurrentChange"
				:page-sizes="[10, 20, 50, 100]"
				:page-size="searchMsg.pageSize"
				layout="total, sizes, prev, pager, next, jumper"
				:total="total"
				style="float:right;">
			</el-pagination>
		</el-col>

    <!--处理dialog-->
    <el-dialog title="处理" :visible.sync="checkFormVisible" :close-on-click-modal="false">
        <el-form :model="checkEdit" label-width="150px" ref="checkEdit">
            <el-form-item label="是否通过" prop="status">
                <el-radio-group v-model="checkEdit.status">
        <el-radio class="radio" :label="1">通过</el-radio>
                    <el-radio class="radio" :label="2">不通过</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="审核意见" prop="opinion">
                <el-input type="textarea" v-model="checkEdit.opinion"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button size="small" @click.native="checkFormVisible = false">取消</el-button>
            <el-button type="primary" size="small" @click="checkEditSubmit">提交</el-button>
        </div>
    </el-dialog>
    
    <!--处理dialog-->
    <el-dialog title="查看物流" :visible.sync="queryLogisticsFormVisible" :close-on-click-modal="false">
        <div class="dialogDiv">
          <el-form :model="queryLogisticsEdit" label-width="150px" ref="queryLogisticsEdit">
              <el-form-item label="快递公司：">
                  {{queryLogisticsEdit.logisticsWay}}
              </el-form-item>
              <el-form-item label="快递编号：">
                  {{queryLogisticsEdit.logisticsNo}}
              </el-form-item>
              <el-form-item label="物流状态：">
                  <span v-if="queryLogisticsEdit.deliveryStatus == 0">快递收件(揽件)</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 1">在途中</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 2">正在派件</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 3">已签收</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 4">派送失败</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 5">疑难件</span>
                  <span v-if="queryLogisticsEdit.deliveryStatus == 6">退件签收</span>
              </el-form-item>                        
              <el-form-item label="快递员：">
                  {{queryLogisticsEdit.courierName}}
              </el-form-item>
              <el-form-item label="快递员电话：">
                  {{queryLogisticsEdit.courierPhone}}
              </el-form-item>                        
              <el-form-item label="物流跟踪信息：">
                  <el-timeline>
                    <el-timeline-item
                      v-for="(activity, index) in orderLogisticsInfo"
                      :key="index"
                      :icon="activity.icon"
                      :type="activity.type"
                      :color="activity.color"
                      :size="activity.size"
                      :timestamp="activity.timestamp">
                      {{activity.content}}
                    </el-timeline-item>
                  </el-timeline>
              </el-form-item>        
          </el-form>
        </div>
        <!-- <div slot="footer" class="dialog-footer">
            <el-button size="small" @click.native="queryLogisticsFormVisible = false">取消</el-button>
            <el-button type="primary" size="small" @click="queryLogisticsEditSubmit">提交</el-button>
        </div> -->
    </el-dialog>        
	</section>
</template>
<script>
	export default {
		data() {
			return {
				pickerOptions: {
					shortcuts: [{
						text: '最近一周',
							onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 7);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近两周',
							onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 14);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一月',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三月',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 3);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近半年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 180);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近一年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 12);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近两年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 24);
							picker.$emit('pick', [start, end]);
						}
					}, {
						text: '最近三年',
						onClick(picker) {
							const end = new Date();
							let start = new Date();
							start.setTime(end.getTime() - 1000 * 60 * 60 * 24 * 30 * 36);
							picker.$emit('pick', [start, end]);
						}
					}]
				},        
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
        timer: null,

				checkFormVisible: false,//检查处理界面是否显示
				checkEdit: {
          id:'',
          status:0,
          opinion:'',
				},

        queryLogisticsFormVisible: false,//检查处理界面是否显示
				queryLogisticsEdit: {
        },      
        orderLogisticsInfo: [] //如果把这个变量动态的存到queryLogisticsEdit会导致第一次加载物流跟踪信息时不会显示出来
			}
		},
		methods: {
      handleLogistics(row){
        this.queryLogisticsFormVisible = true;
        this.queryLogisticsEdit.logisticsWay = row.logisticsWay;
        this.queryLogisticsEdit.logisticsNo = row.logisticsNo;
        this.queryLogisticsEdit.deliveryStatus = row.deliveryStatus;
        this.queryLogisticsEdit.courierName = row.courierName;
        this.queryLogisticsEdit.courierPhone = row.courierPhone;

        let vue = this;
				let url = '/rest/admin/pointsMall/orderLogistics/list';
				vue.$http.post(vue, url, {"pageNo":1, "pageSize":100, "orderId":row.id},
					(vue, data) => {
						if(data.success){
              this.orderLogisticsInfo = new Array();
              for(var i = 0; i < data.data.records.length; i++){
                var obj = data.data.records[i];
                let map = {};
                map.content = obj.description; 
                map.timestamp = obj.descriptionTime;
                this.orderLogisticsInfo.push(map);
              }
            }
            console.log(this.orderLogisticsInfo);
            //设置第一个元素的节点颜色突出
            if(this.orderLogisticsInfo[0]){
              this.orderLogisticsInfo[0].color = '#0bbd87';
            }
					}, (error, data) => {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
          }
        )
      },
		},
		methods: {
      handleCheck(row){
        this.checkFormVisible = true;
        this.checkEdit.id = row.id;
      },			      
			checkEditSubmit: function () { // 编辑
        let vue = this;
				let param = Object.assign({},this.checkEdit);			
				if(param.status == 2 && param.opinion == ""){
					vue.$message({
						showClose: true,
						message: "审核不通过时，审核意见不能为空",
						type: 'error'
					});		
					return false;
				}
				let url = '/rest/admin/pointsMall/order/auditAfterSalesOrder';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						// this.editLoading = false;
						if(data.success){
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'success'
							});
							vue.getList()
							vue.checkFormVisible = false;
						}
					}, (error, data) => {				
						vue.checkFormVisible = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
      },   
      formatSpecList(row, column) { // 添加单位
				let specStr = '';
				let specList = JSON.parse(row.specList);
				// specList.forEach(function(value, index){
				// 	console.log(value + "  " + specList[value]);
				// })
        // console.log(specList);
        if (specStr ==''){
         specStr='-';
        }
				for(var key in specList){
					//console.log(key + "-" + specList[key]);
					if(specStr == ''){

            specStr += specList[key];	
					}else{
            specStr += '/' + specList[key];
            
					}
        }
       
				return specStr;
			},     
      cellStyle({row, column, rowIndex, columnIndex}){
        return "text-align:center";
      },
      headerCellStyle({row, rowIndex}){
        return "text-align:center";
      },      
			gotoOtherPage(type, row) {
				if(type === 'view') {
					this.$router.push({path:'pointsMall_orderDetail', query:{id: row.id}})
				}
			},
      dealOption() {
        let vue = this
        let url = ''
        let param = {
          id: vue.dealId,
          dealadvise: vue.editForm.dealadvise
        }
        vue.dealtype ? url = '/rest/admin/pointsMall/order/updateOrderByBack' : url = '/rest/admin/pointsMall/order/updateByDealadvise'
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
        vue.$http.post( vue, '/rest/admin/pointsMall/stand/update', param,
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
      formatShoppingWay(row, column) { // 添加单位
        return row.shoppingWay == 1 ? '自取' : row.shoppingWay == 2 ? '发货' : '-';
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
      formatOrder (row, column) { // 0-待发货 1-待收货 2已完成 3-已取消 4-申诉中 5-已退款 6-申诉已完成
        let status = row[column.property] 
        switch (status) {
          case 1:
            return '未付款'
            break;
          case 4:
              return '待发货'
            break;
          case 5:
            return '已发货'
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
        }
      },
			handleSizeChange(val) {
				this.searchMsg.pageSize = val;
				this.getList();
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

        //只查询status=-1 售后处理状态(7=售后处理中 9=售后处理完成 11=已取消(已支付))
        param.status=-1;

				//处理开始日期、结束日期
				if(vue.searchMsg.createTime){
					let startDate = vue.searchMsg.createTime[0];
					let endDate = vue.searchMsg.createTime[1];
					param.startCreateTime = this.$utils.formatDate(new Date(startDate), 'yyyy/MM/dd hh:mm:ss');
					param.endCreateTime = this.$utils.formatDate(new Date(endDate), 'yyyy/MM/dd hh:mm:ss');
					delete param.createTime;
				}

        vue.listLoading = true;
				vue.$http.post(vue, '/rest/admin/pointsMall/order/afterSalesListWithDetail', param,
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
				vue.$http.post(vue, '/rest/admin/pointsMall/stand/deleteById', {id},
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
          orderid: row.id,
          status: 1
        }
				vue.$http.post(vue, '/rest/admin/pointsMall/order/updateOrderState', param,
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
.dialogDiv {
    height: 560px;
    overflow-y: auto;
    overflow-x: hidden;
}
</style>