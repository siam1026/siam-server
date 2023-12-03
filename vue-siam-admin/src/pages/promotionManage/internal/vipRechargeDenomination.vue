<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<!-- <el-form-item label="售价" prop="price">
					<el-input v-model="searchMsg.price" clearable placeholder="请输入销售价格"></el-input>
				</el-form-item> -->
				<!-- <el-form-item label="充值后是否赠送优惠券" prop="isGiveCoupons">
					<el-select v-model="searchMsg.isGiveCoupons" clearable>
						<el-option label="是" :value="1"></el-option>
						<el-option label="否" :value="0"></el-option>
						 <el-option label="满减优惠券" :value="2"></el-option> 
					</el-select>
				</el-form-item> -->
				<!-- <el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item> -->
				<el-form-item>
					<el-button type="primary" @click="handleEditDiscount">新增</el-button>
					<!-- <el-button type="primary" @click="handleEditFullReduction">新增满减优惠券</el-button> -->
				</el-form-item>
			</el-form>
		</el-col>
		<!--列表-->
		<el-table :data="list" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
			<!-- <el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>		
			</el-table-column> -->
			<!-- <el-table-column prop="id" label="会员编号"></el-table-column> -->
				<el-table-column prop="name" label="充值面额名称"></el-table-column>	
			<el-table-column prop="price" label="售价"></el-table-column>
		    <el-table-column prop="isSale" label="是否开启促销">
				<template scope="scope">
					<span v-if="scope.row.isSale == 1">是</span>
                    <span v-else-if="scope.row.isSale == 0">否</span>
               </template>						
			</el-table-column>	
			<el-table-column prop="salePrice" label="折扣价" ></el-table-column>	
		 <el-table-column prop="briefDescription" label="简短展示文本"></el-table-column>
	  <el-table-column prop="description" label="充值面额描述" width="190"></el-table-column>	
						
			<el-table-column prop="isGiveBalance" label="充值后是否赠送余额">
				<template scope="scope">
					<span v-if="scope.row.isGiveBalance == 1">是</span>
                    <span v-else-if="scope.row.isGiveBalance == 0">否</span>
               </template>						
			</el-table-column>
			<el-table-column prop="giveBalance" label="赠送余额数量"></el-table-column>	
			<el-table-column prop="isGiveCoupons" label="充值后是否赠送优惠券">
				<template scope="scope">
					<!-- <span>{{scope.row.isGiveCoupons}}</span> -->
					<span v-if="scope.row.isGiveCoupons == 1">是</span>
                    <span v-else-if="scope.row.isGiveCoupons == 0">否</span>
               </template>						
			</el-table-column>
			<el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column> 
			<el-table-column prop="updateTime" label="修改时间" :formatter="formatTime"></el-table-column> 
			<!-- <el-table-column prop="createTime" label="创建时间" :formatter="formatValidType1" width="190"></el-table-column>
			<el-table-column prop="updateTime" label="	修改时间" :formatter="formatValidType2"></el-table-column> -->
						
			<el-table-column label="操作" fixed="right" width="260">
				<template slot-scope="scope"> 
					<el-button size="small"  @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger"  size="small" @click="handleDel(scope.row.id)">删除</el-button>
				
					<el-button size="small"  v-if="scope.row.isGiveCoupons=='1'"  @click="handleRelationShop(scope.row.id)">关联赠送优惠券</el-button>
		
					<!-- <el-button size="small" @click="handleGiveCoupons(scope.row.id)">群发优惠券</el-button> -->
				</template>
				<!-- scope.$index, scope.row -->
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

		<!--编辑界面-会有充值-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="210px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="充值面额名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>	
				<el-form-item label="价格" prop="price">
					<el-input type="number" v-model="editForm.price"></el-input>
				</el-form-item>


				<el-form-item label="是否开启促销" prop="isSale" >
					<el-radio-group v-model="editForm.isSale" size="medium"  @change="chooseisshow"> 
						<el-radio-button label="是" value="1"></el-radio-button>
						<el-radio-button label="否" value="0"></el-radio-button>
					</el-radio-group>				
				</el-form-item>	
				
				<el-form-item   v-if="editForm.isSale=='是'" label="折扣价" prop="salePrice" >
					<el-input type="number" v-model="editForm.salePrice"></el-input>
				</el-form-item>	
				<el-form-item label="简短展示文本" prop="briefDescription">
					<el-input v-model="editForm.briefDescription"></el-input>
				</el-form-item>						 
				<!-- <el-form-item label="折扣额度（0折=0、5折=0.5）" prop="discountAmount">
					<el-input type="number" v-model="editForm.discountAmount"></el-input>
				</el-form-item>				 -->
				<!-- <el-form-item label="满足价格" prop="limitedPrice">
					<el-input v-model="editForm.limitedPrice"></el-input>
				</el-form-item>
				<el-form-item label="减价额度" prop="reducedPrice">
					<el-input v-model="editForm.reducedPrice"></el-input>
				</el-form-item>				 -->
				
				<el-form-item label="充值面额描述" prop="description">
					<el-input type="textarea" v-model="editForm.description" :rows="5" ></el-input>
				</el-form-item>		
				<el-form-item label="充值后是否赠送余额" prop="isGiveBalance">
					<el-radio-group v-model="editForm.isGiveBalance" size="medium">
						<el-radio-button label="是" value="1"></el-radio-button>
						<el-radio-button label="否" value="0"></el-radio-button>
					</el-radio-group>				
				</el-form-item>	
				<el-form-item  v-if="editForm.isGiveBalance=='是'" label="赠送余额数量" prop="giveBalance">
					<el-input type="number" v-model="editForm.giveBalance"></el-input>
				</el-form-item>										
			<el-form-item label="充值后是否赠送优惠券" prop="isGiveCoupons">
					<el-radio-group v-model="editForm.isGiveCoupons" size="medium">
						<el-radio-button label="是" value="1"></el-radio-button>
						<el-radio-button label="否" value="0"></el-radio-button>
					</el-radio-group>				
				</el-form-item>					
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--编辑界面-满减优惠券-->
		<el-dialog title="编辑" :visible.sync="editFormVisibleFullReduction" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="210px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="优惠券名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>
				<!-- <el-form-item label="优惠类型" prop="preferentialType">
					<el-radio-group v-model="editForm.preferentialType" size="medium">
						<el-radio-button label="折扣" value="1"></el-radio-button>
						<el-radio-button label="满减" value="2"></el-radio-button>
					</el-radio-group>				
				</el-form-item>						 -->
				<!-- <el-form-item label="折扣额度（0折=0、5折=0.5）" prop="discountAmount">
					<el-input type="number" v-model="editForm.discountAmount"></el-input>
				</el-form-item>				 -->
				<el-form-item label="满足价格" prop="limitedPrice">
					<el-input v-model="editForm.limitedPrice"></el-input>
				</el-form-item>
				<el-form-item label="减价额度" prop="reducedPrice">
					<el-input v-model="editForm.reducedPrice"></el-input>
				</el-form-item>				
				<el-form-item label="使用规则描述" prop="description">
					<el-input type="textarea" v-model="editForm.description" :rows="5" ></el-input>
				</el-form-item>							
				<el-form-item label="时效" prop="validType">
					<el-radio-group v-model="editForm.validType" size="medium">
						<el-radio-button label="绝对时效" value="1"></el-radio-button>
						<el-radio-button label="相对时效" value="2"></el-radio-button>
					</el-radio-group>				
				</el-form-item>		
				<el-form-item label="使用开始时间（00:00:00）" prop="validStartTime">
					<el-date-picker
						v-model="editForm.validStartTime"
						value-format="timestamp"
						format="yyyy/MM/dd"
						type="datetime"
						placeholder="选择使用开始时间">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="使用结束时间（00:00:00）" prop="validEndTime">
					<el-date-picker
						v-model="editForm.validEndTime"
						value-format="timestamp"
						format="yyyy/MM/dd"
						type="datetime"
						placeholder="选择使用结束时间">
					</el-date-picker>
				</el-form-item>											
				<el-form-item label="有效天数" prop="validDays">
					<el-input v-model="editForm.validDays"></el-input>
				</el-form-item>				
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisibleFullReduction = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmitFullReduction" :loading="editLoadingFullReduction">提交</el-button>
			</div>
		</el-dialog>		

		<!--关联赠送优惠劵-->
		<el-dialog title="关联赠送优惠劵"    :visible.sync="editFormVisibleShopTable" @close="closeDialog" :close-on-click-modal="false">
      		<div class="dialogDiv">		
				<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
					<!--列表-->
					<el-table :data="listShopTable" ref="shopTable" @selection-change="handleSelectionChange" highlight-current-row v-loading="listLoadingShopTable" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
						<el-table-column type="selection" width="55"></el-table-column>
						<!-- <el-table-column type="index" label="序号" width="50">
							<template scope="scope">
								<span>{{(searchMsgShopTable.pageNoIndex - 1) * searchMsgShopTable.pageSize + scope.$index + 1}}</span>
							</template>		
						</el-table-column> -->
						<el-table-column prop="name" label="优惠卷名称"></el-table-column>
						<el-table-column prop="consumedQuantity" label="奖励数量">
							<template scope="scope">
								<el-input type="number" v-model="consumedQuantityRelationTable[scope.row.id]" placeholder="请输入奖励数量"></el-input>
							</template>							
						</el-table-column>   
						<!-- <el-table-column prop="preferentialType" label="优惠类型">
				          <template scope="scope">
				         	<span v-if="scope.row.preferentialType == 1">折扣</span>
                           <span v-else-if="scope.row.preferentialType == 2">满减</span>
                       </template>						
			            </el-table-column> -->
						<!-- <el-table-column prop="discountAmount" label="折扣额度"></el-table-column>
						<el-table-column prop="limitedPrice" label="满足价格（元，满足该价格才能使用）"></el-table-column>
							<el-table-column prop="reducedPrice" label="减价额度(元)"></el-table-column>
								<el-table-column prop="description" label="	使用规则描述"></el-table-column>

						<el-table-column prop="validType" label="优惠类型">
				          <template scope="scope">
				         	<span v-if="scope.row.validType == 1">绝对时效</span>
                           <span v-else-if="scope.row.validType == 2">相对时效</span>
                       </template>						
			            </el-table-column> -->
									<!-- <el-table-column prop="validType" label="优惠卷名称"></el-table-column>
										<el-table-column prop="name" label="优惠卷名称"></el-table-column> -->
						<!-- <el-table-column prop="createTime" label="创建时间"></el-table-column> -->
						<!-- <el-table-column prop="province" label="区域">
							<template slot-scope="scope">
								{{scope.row.province}} - {{scope.row.city}} - {{scope.row.area}}
							</template>
						</el-table-column>
						<el-table-column prop="street" label="详细地址"></el-table-column>
						<el-table-column prop="managePrimary" label="营业类目"></el-table-column>
						<el-table-column prop="shopLogoImgFile" label="门店头像"></el-table-column>

						<el-table-column prop="isOperating" label="店铺是否营业">
							<template slot-scope="scope">
								<span v-if="scope.row.isOperating == true" style="color:green;font-weight:bold;">正常营业</span>
								<span v-else-if="scope.row.isOperating == false" style="color:red;font-weight:bold;">暂不营业</span>
							</template>
						</el-table-column>
						<el-table-column prop="startTime" label="店铺营业开始时间"></el-table-column>
						<el-table-column prop="endTime" label="店铺营业结束时间"></el-table-column>-->
						<!-- <el-table-column prop="validStartTime" label="使用开始时间" :formatter="formatTime"></el-table-column> 
						<el-table-column prop="validEndTime" label="使用结束时间" :formatter="formatTime"></el-table-column>  -->
               <!-- <el-table-column prop="validDays" label="自领取之日起有效天数"></el-table-column>
			   <el-table-column prop="isDelete" label="是否被删除">
				          <template scope="scope">
				         	<span v-if="scope.row.isDelete == 1">是</span>
                           <span v-else-if="scope.row.isDelete == 0">否</span>
                       </template>						
			 </el-table-column>
			 <el-table-column prop="source" label="是否被删除">
				          <template scope="scope">
				         	<span v-if="scope.row.source == 1">商家中心</span>
                           <span v-else-if="scope.row.source == 0">调度中心</span>
                       </template>						
			 </el-table-column>
			 <el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column> 
			 <el-table-column prop="updateTime" label="修改时间" :formatter="formatTime"></el-table-column>  -->
					</el-table>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisibleShopTable = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmitShopTable" :loading="editLoadingShopTable">提交</el-button>
				<!-- <el-button @click="toggleSelection([listShopTable[1], listShopTable[2]])">切换第二、第三行的选中状态</el-button> -->
				<!-- <el-button @click="toggleSelectionSingle(listShopTable[1])">切换第二行的选中状态</el-button> -->
				<!-- <el-button @click="toggleSelectionSingle()">取消选择</el-button>				 -->
			</div>
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
				editLoading: false,

				editsalePrice:false, //折扣价是否显示


				editFormVisibleFullReduction: false,//编辑界面是否显示
				editLoadingFullReduction: false,

				editFormRules: {
					price: [{ required: true, message: '请输入售价', trigger: 'blur' }],
					//isGiveBalance: [{ required: true, message: '请选择充值后是否赠送余额', trigger: 'blur' }],
					//giveBalance: [{ required: true, message: '请输入赠送余额数量', trigger: 'blur' }],
					isGiveCoupons: [{ required: true, message: '请选择是否赠送优惠券', trigger: 'blur' }],
					description: [{ required: true, message: '请输入充值面额描述', trigger: 'blur' }],
					name: [{ required: true, message: '请输入充值面额名称', trigger: 'blur' }],
					//isSale: [{ required: true, message: '请选择是否开启促销', trigger: 'blur' }],
					description: [{ required: true, message: '请输入充值面额描述', trigger: 'blur' }],
					// briefDescription: [{ required: true, message: '请输入简短展示文本', trigger: 'blur' }],
					validEndTime: [{ required: false, message: '请选择使用结束时间', trigger: 'blur' }],
					validDays: [{ required: false, message: '请输入自领取之日起有效天数', trigger: 'blur' }],
				},				
				//编辑界面数据
				editForm: {
					name: '',
					preferentialType: '',
					discountAmount: '',
					limitedPrice: '',
					reducedPrice: '',
					description: '',
					validType: '',
					validStartTime: '',
					validEndTime: '',
					validDays: '',
				},

				editFormVisibleShopTable: false,//编辑界面是否显示
				editLoadingShopTable: false,
				shopTable: '',
				multipleSelection: [],
				//关联店铺的列表
				searchMsgShopTable: {
					pageNo: -1,
					pageNoIndex: 1,
					pageSize: 20
				},		
				listShopTable: [],
				totalShopTable: 0,
				listLoadingShopTable: false,
				selsShopTable: [],//列表选中列			
				couponsIdShopTable: '' ,	
				consumedQuantityRelationTable: [] ,		
			}
		},
		methods: {
			cellStyle({row, column, rowIndex, columnIndex}){
				return "text-align:center";
			},
			headerCellStyle({row, rowIndex}){
				return "text-align:center";
			},			
			closeDialog() {
				this.$refs['editForm'].resetFields();
			},
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				let str = this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
				// 处理数据库中时间为NULL场景
				if(row[column.property]==undefined || str=="1970-01-01 08:00:00"){
					str = "-";
				}
        		return str;
			},
			formatValidType1(row, column) {
				if(row.validType == 1){
					let date = new Date(row[column.property]);
					let str = this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
					// 处理数据库中时间为NULL场景
					if(str == "1970-01-01"){
						str = "-";
					}
					return str;					
				}else{
					return "-"
				}				
			},	
			chooseisshow(row, column){
				if (row.isSale==1){
					this.editsalePrice=true	; //折扣价是否显示

				}else{

					this.editsalePrice=false;
				}
				},
			formatValidType2(row, column) {
				if(row.validType == 2){
					return row.validDays + "天";
				}else{
					return "-"
				}	
			},						
			formatType (row, column) { // 0=正常/启用  1=禁用
				return row.status == 1 ? '开启' : '关闭';
			},
			addUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '元'
			},					
			formatPreferentialType1(row, column) { // 添加单位
				if(row.preferentialType == 1){
					return (row[column.property] * 10) + '折'
				}else{
					return "-"
				}
			},
			formatPreferentialType2(row, column) { // 添加单位
				if(row.preferentialType == 2){
					return (row[column.property] || 0) + '元'
				}else{
					return "-"
				}
			},			
			// formatSalePrice(row, column) { // 添加单位
			// 	if(row.isSale){
			// 		return row.salePrice + '元';
			// 	}else{
			// 		return "-";
			// 	}
			// },
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
				} // 查询会员充值面额列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				//查询未被删除的商品
				//param.isDelete = 0;
				//param.source = 2;

				vue.$http.post(vue, '/rest/admin/vipRechargeDenomination/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
					},(error, data)=> {
						alert(data);
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
				
					vue.$http.delete(vue, '/rest/admin/vipRechargeDenomination/delete', {"id" : id},
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
				}).catch(() => {});
			},
			handleGiveCoupons (id) { // 派发优惠券
					let vue = this;
					vue.$http.post(vue, '/rest/admin/couponsMemberRelation/giveCoupons', {"couponsId" : id},
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

				// vue.$http.post(vue, url, param,
				// 	(vue, data) => {
				// 		// this.editLoadingShopTable = false;
				// 		vue.$message({
				// 			showClose: true,
				// 			message: data.message,
				// 			type: 'success'
				// 		});
				// 		// vue.getList()
				// 		vue.editFormVisibleShopTable = false;						
				// 	}, (error, data) => {
				// 		vue.editFormVisibleShopTable = false;
				// 		vue.$message({
				// 			showClose: true,
				// 			message: data.message,
				// 			type: 'error'
				// 		});
				// 	}
				// )							
			},			
			handleEdit: function (index, row) { // 显示编辑界面
				// if(row.preferentialType == 1){
					 this.editFormVisible = true;
				// }else{
					//  this.editFormVisibleFullReduction = true;
				// }
			
				if(row){
					this.editForm = {
						id: row.id,
						price: row.price,
						name: row.name,
						salePrice: row.salePrice,
						briefDescription: row.briefDescription,
						isGiveBalance: row.isGiveBalance == 1 ? '是' : '否',
						isGiveCoupons: row.isGiveCoupons == 1 ? '是' : '否',
						isSale: row.isSale == 1 ? '是' : '否',
					    giveBalance: row.giveBalance,
						discountAmount: row.preferentialType == 1 ? row.discountAmount : '',
						limitedPrice: row.preferentialType == 2 ? row.limitedPrice : '',
						reducedPrice: row.preferentialType == 2 ? row.reducedPrice : '',
						description: row.description,
						validType: row.validType == 1 ? '绝对时效' : '相对时效',
						validStartTime: row.validType == 1 ? new Date(row.validStartTime).getTime() : '',
						validEndTime: row.validType == 1 ? new Date(row.validEndTime).getTime() : '',
						validDays: row.validType == 2 ? row.validDays : '',
					}
				}else{
					this.editForm = {
						name: '',
						preferentialType: '',
						discountAmount: '',
						limitedPrice: '',
						reducedPrice: '',
						description: '',
						validType: '',
						validStartTime: '',
						validEndTime: '',
						validDays: '',
						isSale: '否',
					}
				}					
			},
			handleEditDiscount: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
				if(row){
					this.editForm = {
						id: row.id,
						name: row.name,
						preferentialType: row.preferentialType == 1 ? '折扣' : '满减',
						discountAmount: row.preferentialType == 1 ? row.discountAmount : '',
						limitedPrice: row.preferentialType == 2 ? row.limitedPrice : '',
						reducedPrice: row.preferentialType == 2 ? row.reducedPrice : '',
						description: row.description,
						validType: row.validType == 1 ? '绝对时效' : '相对时效',
						validStartTime: row.validType == 1 ? new Date(row.validStartTime).getTime() : '',
						validEndTime: row.validType == 1 ? new Date(row.validEndTime).getTime() : '',
						validDays: row.validType == 2 ? row.validDays : '',
					}
				}else{
					this.editForm = {
						name: '',
						preferentialType: '',
						discountAmount: '',
						limitedPrice: '',
						reducedPrice: '',
						description: '',
						validType: '',
						validStartTime: '',
						validEndTime: '',
						validDays: '',
						isSale:'否',
						isGiveBalance:'否',
					}
				}					
			},
			handleEditFullReduction: function (index, row) { // 显示编辑界面
				this.editFormVisibleFullReduction = true;
				if(row){
					this.editForm = {
						id: row.id,
						name: row.name,
						preferentialType: row.preferentialType == 1 ? '折扣' : '满减',
						discountAmount: row.preferentialType == 1 ? row.discountAmount : '',
						limitedPrice: row.preferentialType == 2 ? row.limitedPrice : '',
						reducedPrice: row.preferentialType == 2 ? row.reducedPrice : '',
						description: row.description,
						validType: row.validType == 1 ? '绝对时效' : '相对时效',
						validStartTime: row.validType == 1 ? new Date(row.validStartTime).getTime() : '',
						validEndTime: row.validType == 1 ? new Date(row.validEndTime).getTime() : '',
						validDays: row.validType == 2 ? row.validDays : '',
					}
				}else{
					this.editForm = {
						name: '',
						preferentialType: '',
						discountAmount: '',
						limitedPrice: '',
						reducedPrice: '',
						description: '',
						validType: '',
						validStartTime: '',
						validEndTime: '',
						validDays: '',
					}
				}					
			},						
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {						
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = '';

						//优惠券发放来源为调度中心
						//param.source = 2;

						//param.preferentialType = 1;

					param.isGiveBalance = (param.isGiveBalance == '是') ? 1 : 0;	
						param.isGiveCoupons = (param.isGiveCoupons == '是') ? 1 : 0;
							param.isSale = (param.isSale == '是') ? 1 : 0;
						// param.preferentialType = (param.preferentialType == '折扣') ? 1 : 2;
						// param.validType = (param.validType == '绝对时效') ? 1 : 2;	
						// if(param.price == ''){
						// 	vue.$message({
						// 	showClose: true,
						// 	message: '请填写折扣额度',
						// 	type: 'error'
						// 	});
						// 	return false							
						// }			
						// if(param.discountAmount<0 || param.discountAmount>=1){
						// 	vue.$message({
						// 	showClose: true,
						// 	message: '请填写正确的折扣额度',
						// 	type: 'error'
						// 	});
						// 	return false							
						// }									
						// delete param.limitedPrice;
						// delete param.reducedPrice;			

						// //判断优惠类型合法性
						// if(param.preferentialType == 1){
						// 	if(param.discountAmount == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写折扣额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}			
						// 	if(param.discountAmount<0 || param.discountAmount>=1){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写正确的折扣额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}									
						// 	delete param.limitedPrice;
						// 	delete param.reducedPrice;
						// }else if(param.preferentialType == 2){
						// 	if(param.limitedPrice == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写满足额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}									
						// 	if(param.reducedPrice == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写减价额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}							
						// 	if(parseFloat(param.reducedPrice) >= parseFloat(param.limitedPrice)){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '减价额度必须小于满足价格',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}		
						// 	delete param.discountAmount;						
						// }

						//判断时效合法性
						// if(param.validType == 1){
						// 	if(param.validStartTime == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写使用开始时间',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}									
						// 	if(param.validEndTime == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写使用结束时间',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}							
						// 	let end = new Date(param.validEndTime);
						// 	end.setHours(0);
						// 	end.setMinutes(0);
						// 	end.setSeconds(0);
						// 	let start = new Date(param.validStartTime);
						// 	start.setHours(0);
						// 	start.setMinutes(0);
						// 	start.setSeconds(0);								
						// 	if(end <= start){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '使用结束时间必须大于使用开始时间',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}						
						// 	delete param.validDays;			
						// 	//处理下时间格式(本来传递一个时间戳就可以了的，但是后台配置有问题，所以先用字符串传参)
						// 	let validStartTime = new Date(param.validStartTime);
						// 	let str = this.$utils.formatDate(validStartTime, 'yyyy/MM/dd');
						// 	param.validStartTime = str;

						// 	let validEndTime = new Date(param.validEndTime);
						// 	str = this.$utils.formatDate(validEndTime, 'yyyy/MM/dd');
						// 	param.validEndTime = str;										
						// }else if(param.validType == 2){
						// 	if(param.validDays == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写有效天数',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}	
						// 	delete param.validStartTime;				
						// 	delete param.validEndTime;				
						// }						
				
						// this.editLoading = true;			

						if(param.id){
							url = '/rest/admin/vipRechargeDenomination/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									// this.editLoading = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
									
									vue.getList()
									vue.editFormVisible = false;
								}, (error, data) => {
									vue.editFormVisible = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}else{
							url = '/rest/admin/vipRechargeDenomination/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									// this.editLoading = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
									
									vue.getList()
									vue.editFormVisible = false;
								}, (error, data) => {
									vue.editFormVisible = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}
					}
				});
			},			
			editSubmitFullReduction: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {						
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = '';

						//优惠券发放来源为调度中心
						param.source = 2;

						param.preferentialType = 2;
						// param.preferentialType = (param.preferentialType == '折扣') ? 1 : 2;
						param.validType = (param.validType == '绝对时效') ? 1 : 2;	
						if(param.limitedPrice == ''){
							vue.$message({
							showClose: true,
							message: '请填写满足额度',
							type: 'error'
							});
							return false							
						}									
						if(param.reducedPrice == ''){
							vue.$message({
							showClose: true,
							message: '请填写减价额度',
							type: 'error'
							});
							return false							
						}							
						if(parseFloat(param.reducedPrice) >= parseFloat(param.limitedPrice)){
							vue.$message({
							showClose: true,
							message: '减价额度必须小于满足价格',
							type: 'error'
							});
							return false							
						}		
						delete param.discountAmount;

						//判断优惠类型合法性
						// if(param.preferentialType == 1){
						// 	if(param.discountAmount == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写折扣额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}			
						// 	if(param.discountAmount<0 || param.discountAmount>=1){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写正确的折扣额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}									
						// 	delete param.limitedPrice;
						// 	delete param.reducedPrice;
						// }else if(param.preferentialType == 2){
						// 	if(param.limitedPrice == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写满足额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}									
						// 	if(param.reducedPrice == ''){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '请填写减价额度',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}							
						// 	if(parseFloat(param.reducedPrice) >= parseFloat(param.limitedPrice)){
						// 		vue.$message({
						// 		showClose: true,
						// 		message: '减价额度必须小于满足价格',
						// 		type: 'error'
						// 		});
						// 		return false							
						// 	}		
						// 	delete param.discountAmount;						
						// }

						//判断时效合法性
						if(param.validType == 1){
							if(param.validStartTime == ''){
								vue.$message({
								showClose: true,
								message: '请填写使用开始时间',
								type: 'error'
								});
								return false							
							}									
							if(param.validEndTime == ''){
								vue.$message({
								showClose: true,
								message: '请填写使用结束时间',
								type: 'error'
								});
								return false							
							}							
							let end = new Date(param.validEndTime);
							end.setHours(0);
							end.setMinutes(0);
							end.setSeconds(0);
							let start = new Date(param.validStartTime);
							start.setHours(0);
							start.setMinutes(0);
							start.setSeconds(0);								
							if(end <= start){
								vue.$message({
								showClose: true,
								message: '使用结束时间必须大于使用开始时间',
								type: 'error'
								});
								return false							
							}						
							delete param.validDays;			
							//处理下时间格式(本来传递一个时间戳就可以了的，但是后台配置有问题，所以先用字符串传参)
							let validStartTime = new Date(param.validStartTime);
							let str = this.$utils.formatDate(validStartTime, 'yyyy/MM/dd');
							param.validStartTime = str;

							let validEndTime = new Date(param.validEndTime);
							str = this.$utils.formatDate(validEndTime, 'yyyy/MM/dd');
							param.validEndTime = str;										
						}else if(param.validType == 2){
							if(param.validDays == ''){
								vue.$message({
								showClose: true,
								message: '请填写有效天数',
								type: 'error'
								});
								return false							
							}	
							delete param.validStartTime;				
							delete param.validEndTime;				
						}						
				
						// this.editLoadingFullReduction = true;			

						if(param.id){
							url = '/rest/admin/coupons/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									// this.editLoadingFullReduction = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
									
									vue.getList()
									vue.editFormVisibleFullReduction = false;
								}, (error, data) => {
									vue.editFormVisibleFullReduction = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}else{
							url = '/rest/admin/coupons/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									// this.editLoadingFullReduction = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
									
									vue.getList()
									vue.editFormVisibleFullReduction = false;
								}, (error, data) => {
									vue.editFormVisibleFullReduction = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}
					}
				});
			},					
			getListShopTable() { // 获取优惠劵列表
				let vue = this
        		let param = Object.assign({}, vue.searchMsgShopTable, {"token":sessionStorage.getItem("token")});

				console.log("param="+JSON.stringify(param));
				console.log("searchMsg="+JSON.stringify(vue.searchMsgShopTable));

				vue.listLoadingShopTable = true;
				vue.$http.post(vue, '/rest/admin/coupons/list', param,
					(vue, data) => {
						vue.listShopTable = data.data.records
						vue.totalShopTable = data.data.total
						vue.listLoadingShopTable = false;
						//this.$refs.moviesTable.toggleRowSelection(row)
					},(error, data)=> {
						vue.listLoadingShopTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)		
			},				
			handleRelationShop (id) { // 关联赠送优惠劵
				this.$nextTick(function () {
					//清除之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
					this.toggleSelectionSingle();	
				})		
				//清除表格中的耗量输入框数据
				this.consumedQuantityRelationTable = [];	
				this.editFormVisibleShopTable = true;
				this.couponsIdShopTable = id;
				let vue = this
                let param1 = Object.assign(vue.searchMsg);
				param1.vipRechargeDenominationId=id
				
				vue.$http.post(vue, '/rest/admin/vipRechargeDenominationCouponsRelation/list',param1,
					(vue, data) => {
						let shopList = data.data.records				
						//alert(shopList.length);
						if(shopList.length > 0){
							//默认选中已经关联的优惠劵
							for(let i = 0; i < shopList.length; i++){
								for(let j = 0; j < this.listShopTable.length; j++){
									if(shopList[i].couponsId == this.listShopTable[j].id){
										this.toggleSelectionSingle(this.listShopTable[j])
										this.consumedQuantityRelationTable[shopList[i].couponsId] = shopList[i].giveQuantity;
										break;
									}									
								}							
							}
						}			
						console.log(this.multipleSelection);
					},(error, data)=> {
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)			
			},		
			editSubmitShopTable: function () { // 关联会员保存事件
				let vue = this
				let param = {
					vipRechargeDenominationId : this.couponsIdShopTable
					//denominationCouponsRelationListStr : ''
				};
				let url = '';

				if(this.multipleSelection.length == 0){
					vue.$message({
						showClose: true,
						message: '请选择要关联的优惠劵',
						type: 'error'
					});
					return false							
				}		

				//生成商品id数组
				let shopIdList = [];
				for(let i = 0; i < this.multipleSelection.length; i++){
					//shopIdList[i] = this.multipleSelection[i].id;									
					shopIdList.push(this.multipleSelection[i].id);			
				}

				
				//param.shopIdListStr = JSON.stringify(shopIdList);

				//不用这种拼接的方式
				// let str = '';
				// for(let i = 0; i < this.multipleSelection.length; i++){
				// 	str += this.multipleSelection[i].id + ",";
				// }
				// str = str.substring(0, str.length-1);
				// param.shopIdList = str;
		 
				//生成商品原料关联列表
				let relationList = [];
				for(let i = 0; i < this.multipleSelection.length; i++){
					// alert(this.consumedQuantityRelationTable[this.multipleSelection[i].id])
					let consumedQuantity = this.consumedQuantityRelationTable[this.multipleSelection[i].id];
					if(consumedQuantity==undefined || consumedQuantity==""){
						vue.$message({
							showClose: true,
							message: '请填写' + this.multipleSelection[i].name + '奖励数量',
							type: 'error'
						});
						return false							
					}
					let map = {
						"couponsId" : this.multipleSelection[i].id,
						"giveQuantity" : consumedQuantity
					}
					relationList.push(map);
				}
				param.denominationCouponsRelationListStr = JSON.stringify(relationList);

				
				
				// this.editLoadingShopTable = true;
			
				url = '/rest/admin/vipRechargeDenominationCouponsRelation/insert';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						// this.editLoadingShopTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
						});
						
						// vue.getList()
						vue.editFormVisibleShopTable = false;						
					}, (error, data) => {
						vue.editFormVisibleShopTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
							
					}
				)							
			},							
			// toggleSelection() {
			// 	let vue = this;
			// 	// vue.$refs.table.clearSelection();
			// 	vue.listShopTable.forEach((item) => {
			// 		//设置该表格选框选中
			// 		// vue.$refs.table.toggleRowSelection(item);
			// 	});				
				
			// },			
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.shopTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.shopTable.clearSelection();
				}
			},
			toggleSelectionSingle(row) {
				if (row) {
					this.$refs.shopTable.toggleRowSelection(row);
				} else {
					this.$refs.shopTable.clearSelection();
				}
			},										
			handleSelectionChange(val) {
				this.multipleSelection = val;
				console.log(this.multipleSelection);
			}					
		},
		mounted() {
			this.getList();
			this.getListShopTable();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>
  /* .el-button+.el-button {
    margin-top: 10px;
    margin-left: 0;
  } */
  .dialogDiv {
      height: 560px;
      overflow-y: auto;
      overflow-x: hidden;
  }
</style>