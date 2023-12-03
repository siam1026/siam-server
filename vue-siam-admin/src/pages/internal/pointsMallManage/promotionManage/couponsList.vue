<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="优惠券名称" prop="name">
					<el-input v-model="searchMsg.name" clearable placeholder="请输入优惠券名称"></el-input>
				</el-form-item>
				<el-form-item label="优惠券类型" prop="preferentialType">
					<el-select v-model="searchMsg.preferentialType" clearable>
						<el-option label="折扣优惠券" :value="1"></el-option>
						<!-- <el-option label="满减优惠券" :value="2"></el-option> -->
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleEditDiscount">新增折扣优惠券</el-button>
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
			<el-table-column prop="id" label="优惠券编号"></el-table-column>
			<el-table-column prop="name" label="优惠券名称"></el-table-column>
			<el-table-column prop="preferentialType" label="优惠券类型">
				<template scope="scope">
					<span v-if="scope.row.preferentialType == 1">折扣优惠券</span>
                    <span v-else-if="scope.row.preferentialType == 2">满减优惠券</span>
               </template>						
			</el-table-column>
			<el-table-column prop="discountAmount" label="折扣额度" :formatter="formatPreferentialType1"></el-table-column>
			<el-table-column prop="limitedPrice" label="满足价格" :formatter="formatPreferentialType2"></el-table-column>
			<el-table-column prop="reducedPrice" label="减价额度" :formatter="formatPreferentialType2"></el-table-column>
			<el-table-column prop="description" label="使用规则描述" width="190"></el-table-column>
			<el-table-column prop="validType" label="时效">
				<template scope="scope">
					<span v-if="scope.row.validType == 1">绝对时效</span>
                    <span v-else-if="scope.row.validType == 2">相对时效</span>
               </template>						
			</el-table-column>
			<el-table-column prop="validStartTime" label="使用开始时间" :formatter="formatValidType1" width="190"></el-table-column>
			<el-table-column prop="validEndTime" label="使用结束时间" :formatter="formatValidType1" width="190"></el-table-column>
			<el-table-column prop="validDays" label="有效天数" :formatter="formatValidType2"></el-table-column>
			<el-table-column prop="validDays" label="已发放张数">
				<template scope="scope">
					<span>{{scope.row.gaveCount}}张</span>
               </template>	
			</el-table-column>			
			<el-table-column prop="validDays" label="已使用张数">
				<template scope="scope">
					<span>{{scope.row.usedCount}}张</span>
               </template>	
			</el-table-column>				
			<el-table-column label="操作" fixed="right" width="260">
				<template slot-scope="scope">
					<el-button size="small" @click="handleRelationGoods(scope.row.id)">关联商品</el-button>
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button>
					<el-button size="small" @click="handleGiveCoupons(scope.row.id)">群发优惠券</el-button>
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

		<!--编辑界面-折扣优惠券-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
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
				<el-form-item label="折扣额度（0折=0、5折=0.5）" prop="discountAmount">
					<el-input type="number" v-model="editForm.discountAmount"></el-input>
				</el-form-item>				
				<!-- <el-form-item label="满足价格" prop="limitedPrice">
					<el-input v-model="editForm.limitedPrice"></el-input>
				</el-form-item>
				<el-form-item label="减价额度" prop="reducedPrice">
					<el-input v-model="editForm.reducedPrice"></el-input>
				</el-form-item>				 -->
				<el-form-item    label="使用规则描述" prop="description">
					<el-input type="textarea" v-model="editForm.description" :rows="5" ></el-input>
				</el-form-item>							
				<el-form-item label="时效" prop="validType">
					<el-radio-group v-model="editForm.validType" size="medium">
						<el-radio-button label="使用时间段" value="1"></el-radio-button>
						<el-radio-button label="使用天数" value="2"></el-radio-button>
					</el-radio-group>				
				</el-form-item>		
				<el-form-item  v-if="editForm.validType=='使用时间段'"   label="使用开始时间（00:00:00）" prop="validStartTime">
					<el-date-picker
						v-model="editForm.validStartTime"
						value-format="timestamp"
						format="yyyy/MM/dd"
						type="datetime"
						placeholder="选择使用开始时间">
					</el-date-picker>
				</el-form-item>
				<el-form-item v-if="editForm.validType=='使用时间段'"  label="使用结束时间（00:00:00）" prop="validEndTime">
					<el-date-picker
						v-model="editForm.validEndTime"
						value-format="timestamp"
						format="yyyy/MM/dd"
						type="datetime"
						placeholder="选择使用结束时间">
					</el-date-picker>
				</el-form-item>											
				<el-form-item v-if="editForm.validType=='使用天数'"  label="有效天数" prop="validDays">
					<el-input v-model="editForm.validDays"></el-input>
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

		<!--关联商品界面-->
		<el-dialog title="关联商品" :visible.sync="editFormVisibleGoodsTable" @close="closeDialog" :close-on-click-modal="false">
      		<div class="dialogDiv">		
				<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
					<!--列表-->
					<el-table :data="listGoodsTable" ref="goodsTable" @selection-change="handleSelectionChange" highlight-current-row v-loading="listLoadingGoodsTable" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
						<el-table-column type="selection" width="55"></el-table-column>
						<!-- <el-table-column type="index" label="序号" width="50">
							<template scope="scope">
								<span>{{(searchMsgGoodsTable.pageNoIndex - 1) * searchMsgGoodsTable.pageSize + scope.$index + 1}}</span>
							</template>		
						</el-table-column> -->
						<el-table-column label="商品主图" prop="mainImage">
							<template slot-scope="scope">
								<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
							</template>
						</el-table-column>
						<el-table-column prop="name" label="商品名称"></el-table-column>
						<el-table-column prop="menuName" label="类别"></el-table-column>      
						<!-- <el-table-column prop="goodsNo" label="编号"></el-table-column> -->
						<el-table-column prop="price" label="一口价" :formatter="addUnit"></el-table-column>
						<!-- <el-table-column prop="salePrice" label="折扣价" :formatter="formatSalePrice"></el-table-column> -->
						<!-- <el-table-column prop="packingCharges" label="包装费" :formatter="addUnit"></el-table-column> -->
					</el-table>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisibleGoodsTable = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmitGoodsTable" :loading="editLoadingGoodsTable">提交</el-button>
				<!-- <el-button @click="toggleSelection([listGoodsTable[1], listGoodsTable[2]])">切换第二、第三行的选中状态</el-button> -->
				<!-- <el-button @click="toggleSelectionSingle(listGoodsTable[1])">切换第二行的选中状态</el-button> -->
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

				editFormVisibleFullReduction: false,//编辑界面是否显示
				editLoadingFullReduction: false,

				editFormRules: {
					name: [{ required: true, message: '请输入优惠券名称', trigger: 'blur' }],
					preferentialType: [{ required: true, message: '请输入优惠类型', trigger: 'blur' }],
					discountAmount: [{ required: true, message: '请输入折扣额度', trigger: 'blur' }],
					limitedPrice: [{ required: true, message: '请输入满足价格', trigger: 'blur' }],
					reducedPrice: [{ required: true, message: '请输入减价额度', trigger: 'blur' }],
					description: [{ required: true, message: '请输入使用规则描述', trigger: 'blur' }],
					validType: [{ required: true, message: '请选择时效', trigger: 'blur' }],
					validStartTime: [{ required: false, message: '请选择使用开始时间', trigger: 'blur' }],
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

				editFormVisibleGoodsTable: false,//编辑界面是否显示
				editLoadingGoodsTable: false,
				goodsTable: '',
				multipleSelection: [],
				//关联商品的列表
				searchMsgGoodsTable: {
					pageNo: -1,
					pageNoIndex: 1,
					pageSize: 20
				},		
				listGoodsTable: [],
				totalGoodsTable: 0,
				listLoadingGoodsTable: false,
				selsGoodsTable: [],//列表选中列			
				couponsIdGoodsTable: '' ,		
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
			formatSalePrice(row, column) { // 添加单位
				if(row.isSale){
					return row.salePrice + '元';
				}else{
					return "-";
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
				} // 获取商品辅料列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				//查询未被删除的商品
				param.isDelete = 0;

				vue.$http.post(vue, '/rest/admin/pointsMall/coupons/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
					},(error, data)=> {
						// alert(data);
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
				
					vue.$http.delete(vue, '/rest/admin/pointsMall/coupons/delete', {"id" : id},
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
					vue.$http.post(vue, '/rest/admin/pointsMall/couponsMemberRelation/giveCoupons', {"couponsId" : id},
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
				// 		this.editLoadingGoodsTable = false;
				// 		vue.$message({
				// 			showClose: true,
				// 			message: data.message,
				// 			type: 'success'
				// 		});
				// 		// vue.getList()
				// 		vue.editFormVisibleGoodsTable = false;						
				// 	}, (error, data) => {
				// 		vue.editFormVisibleGoodsTable = false;
				// 		vue.$message({
				// 			showClose: true,
				// 			message: data.message,
				// 			type: 'error'
				// 		});
				// 	}
				// )							
			},			
			handleEdit: function (index, row) { // 显示编辑界面
				if(row.preferentialType == 1){
					this.editFormVisible = true;
				}else{
					this.editFormVisibleFullReduction = true;
				}
				if(row){
					this.editForm = {
						id: row.id,
						name: row.name,
						preferentialType: row.preferentialType == 1 ? '折扣' : '满减',
						discountAmount: row.preferentialType == 1 ? row.discountAmount : '',
						limitedPrice: row.preferentialType == 2 ? row.limitedPrice : '',
						reducedPrice: row.preferentialType == 2 ? row.reducedPrice : '',
						description: row.description,
						validType: row.validType == 1 ? '使用时间段' : '使用天数',
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

						param.preferentialType = 1;
						// param.preferentialType = (param.preferentialType == '折扣') ? 1 : 2;
						param.validType = (param.validType == '使用时间段') ? 1 : 2;	
						if(param.discountAmount.toString == ''){
							vue.$message({
							showClose: true,
							message: '请填写折扣额度',
							type: 'error'
							});
							return false							
						}	
					
						if(param.discountAmount<0 || param.discountAmount>=1){
							
							vue.$message({
							showClose: true,
							message: '请填写正确的折扣额度',
							type: 'error'
							});
							return false							
						}									
						delete param.limitedPrice;
						delete param.reducedPrice;			

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
				
						this.editLoading = true;			

						if(param.id){
							url = '/rest/admin/pointsMall/coupons/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									this.editLoading = false;
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
							url = '/rest/admin/pointsMall/coupons/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									this.editLoading = false;
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
				
						this.editLoadingFullReduction = true;			

						if(param.id){
							url = '/rest/admin/pointsMall/coupons/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									this.editLoadingFullReduction = false;
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
							url = '/rest/admin/pointsMall/coupons/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									this.editLoadingFullReduction = false;
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
			getListGoodsTable() { // 获取商品列表
				let vue = this
        		let param = Object.assign({}, vue.searchMsgGoodsTable, {"token":sessionStorage.getItem("token")});

				console.log("param="+JSON.stringify(param));
				console.log("searchMsg="+JSON.stringify(vue.searchMsgGoodsTable));

				vue.listLoadingGoodsTable = true;
				vue.$http.post(vue, '/rest/admin/pointsMall/goods/list', param,
					(vue, data) => {
						vue.listGoodsTable = data.data.records
						vue.totalGoodsTable = data.data.total
						vue.listLoadingGoodsTable = false;

						//this.$refs.moviesTable.toggleRowSelection(row)
					},(error, data)=> {
						vue.listLoadingGoodsTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)		
			},				
			handleRelationGoods (id) { // 关联商品
				this.$nextTick(function () {
					//清除之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
					this.toggleSelectionSingle();	
				})			
				this.editFormVisibleGoodsTable = true;
				this.couponsIdGoodsTable = id;
				let vue = this
				vue.$http.post(vue, '/rest/admin/pointsMall/coupons/selectById', {"id" : id},
					(vue, data) => {
						let goodsList = data.data.goodsList
						//alert(goodsList.length);
						if(goodsList.length > 0){
							//默认选中已经关联的商品
							for(let i = 0; i < goodsList.length; i++){
								for(let j = 0; j < this.listGoodsTable.length; j++){
									if(goodsList[i].id == this.listGoodsTable[j].id){
										this.toggleSelectionSingle(this.listGoodsTable[j])
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
			editSubmitGoodsTable: function () { // 关联商品保存事件
				let vue = this
				let param = {
					couponsId : this.couponsIdGoodsTable,
					goodsIdListStr : ''
				};
				let url = '';

				if(this.multipleSelection.length == 0){
					vue.$message({
						showClose: true,
						message: '请选择要关联的商品',
						type: 'error'
					});
					return false							
				}		

				//生成商品id数组
				let goodsIdList = [];
				for(let i = 0; i < this.multipleSelection.length; i++){
					//goodsIdList[i] = this.multipleSelection[i].id;									
					goodsIdList.push(this.multipleSelection[i].id);									
				}
				param.goodsIdListStr = JSON.stringify(goodsIdList);

				//不用这种拼接的方式
				// let str = '';
				// for(let i = 0; i < this.multipleSelection.length; i++){
				// 	str += this.multipleSelection[i].id + ",";
				// }
				// str = str.substring(0, str.length-1);
				// param.goodsIdList = str;
				
				this.editLoadingGoodsTable = true;
			
				url = '/rest/admin/pointsMall/couponsGoodsRelation/insert';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						this.editLoadingGoodsTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
						});
						// vue.getList()
						vue.editFormVisibleGoodsTable = false;						
					}, (error, data) => {
						vue.editFormVisibleGoodsTable = false;
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
			// 	vue.listGoodsTable.forEach((item) => {
			// 		//设置该表格选框选中
			// 		// vue.$refs.table.toggleRowSelection(item);
			// 	});				
				
			// },			
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.goodsTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.goodsTable.clearSelection();
				}
			},
			toggleSelectionSingle(row) {
				if (row) {
					this.$refs.goodsTable.toggleRowSelection(row);
				} else {
					this.$refs.goodsTable.clearSelection();
				}
			},										
			handleSelectionChange(val) {
				this.multipleSelection = val;
				console.log(this.multipleSelection);
			}					
		},
		mounted() {
			this.getList();
			this.getListGoodsTable();
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