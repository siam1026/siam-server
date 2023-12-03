<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item label="商品名称" prop="goodsName">
					<el-input v-model="searchMsg.goodsName" clearable placeholder="请输入商品名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAddRelation">设置配比</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<div class="table-wrapper">
			<div class="left-table">
				<!--列表-->
				<el-table :data="list" ref="goodsTable" @selection-change="handleSelectionChangeGoodsTable" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
					<el-table-column type="selection" width="55"></el-table-column>				
					<!-- <el-table-column type="index" label="序号" width="50">
						<template scope="scope">
							<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
						</template>		
					</el-table-column> -->
					<el-table-column prop="goodsName" label="商品名称"></el-table-column>
					<el-table-column prop="rawmaterialName" label="原料"></el-table-column>
					<el-table-column label="操作" fixed="right" width="100">
						<template slot-scope="scope">
							<el-button size="small" @click="handleDetail(scope.row.goodsId)">查看</el-button>
						</template>
						<!-- scope.$index, scope.row -->
					</el-table-column>				
				</el-table>
				<!--工具条-->
				<el-col :span="24" class="toolbar">
					<!-- <el-button type="danger" @click="batchRemove" :stock="this.sels.length===0">批量删除</el-button> -->
					<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="searchMsg.pageSize" :total="total" style="float:right;">
					</el-pagination>
				</el-col>					
			</div>

			<div class="right-table">
				<!--列表-->
				<el-table :data="listRawmaterial" highlight-current-row v-loading="listLoadingRawmaterial" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
					<!-- <el-table-column type="index" label="序号" width="50">
						<template scope="scope">
							<span>{{(searchMsgRawmaterial.pageNo - 1) * searchMsgRawmaterial.pageSize + scope.$index + 1}}</span>
						</template>		
					</el-table-column> -->
					<el-table-column prop="rawmaterialName" label="原料名称"></el-table-column>
					<el-table-column prop="consumedQuantity" label="耗量" :formatter="addUnit">
						<template scope="scope">
							<span>{{scope.row.consumedQuantity}} {{scope.row.rawmaterialUnit}}</span>
						</template>						
					</el-table-column>
					<el-table-column label="操作" fixed="right" width="150">
						<template slot-scope="scope">
							<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
							<el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除</el-button>
						</template>
						<!-- scope.$index, scope.row -->
					</el-table-column>
				</el-table>	
				<!--工具条-->
				<el-col :span="24" class="toolbar">
					<!-- <el-button type="danger" @click="batchRemove" :stock="this.sels.length===0">批量删除</el-button> -->
					<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="searchMsgRawmaterial.pageSize" :total="totalRawmaterial" style="float:right;">
					</el-pagination>
				</el-col>				
			</div>
		</div>

		<!--原料配比界面-->
		<el-dialog title="设置配比" :visible.sync="editFormVisibleRelationTable" @close="closeDialog" :close-on-click-modal="false">
      		<div class="dialogDiv">		
				<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
					<!--列表-->
					<el-table :data="listRelationTable" ref="relationTable" @selection-change="handleSelectionChangeRelationTable" highlight-current-row v-loading="listLoadingRelationTable" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
						<el-table-column type="selection" width="55"></el-table-column>
						<!-- <el-table-column type="index" label="序号" width="50">
							<template scope="scope">
								<span>{{(searchMsgRelationTable.pageNoIndex - 1) * searchMsgRelationTable.pageSize + scope.$index + 1}}</span>
							</template>		
						</el-table-column> -->
						<el-table-column prop="name" label="原料"></el-table-column>
						<el-table-column prop="consumedQuantity" label="耗量">
							<template scope="scope">
								<el-input type="number" v-model="consumedQuantityRelationTable[scope.row.id]" placeholder="请输入耗量"></el-input>
							</template>							
						</el-table-column>      
						<el-table-column prop="unit" label="单位"></el-table-column>
					</el-table>
				</el-form>
			</div>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisibleRelationTable = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmitRelationTable" :loading="editLoadingRelationTable">提交</el-button>
				<!-- <el-button @click="toggleSelection([listRelationTable[1], listRelationTable[2]])">切换第二、第三行的选中状态</el-button> -->
				<!-- <el-button @click="toggleSelectionSingle(listRelationTable[1])">切换第二行的选中状态</el-button> -->
				<!-- <el-button @click="toggleSelectionSingle()">取消选择</el-button>				 -->
			</div>
		</el-dialog>			

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="原料名称" prop="rawmaterialName">
					<el-input v-model="editForm.rawmaterialName" disabled=""></el-input>
				</el-form-item>
				<el-form-item label="耗量" prop="consumedQuantity">
					<el-input v-model="editForm.consumedQuantity"></el-input>
				</el-form-item>			
				<el-form-item label="采购单位" prop="rawmaterialUnit">
					<el-input v-model="editForm.rawmaterialUnit" disabled=""></el-input>
				</el-form-item>					
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>
	</section>
</template>

<script>
	import axios from 'axios'
	const qs = require('qs');
	export default {
		data() {
			return {
				//左侧商品表格
				searchMsg: {
					pageNo: 1,
					pageSize: 20
				},
				list: [],
				total: 0,
				listLoading: false,
				sels: [],//列表选中列
				goodsTable: '',
				multipleSelectionGoodsTable: [],				

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					name: [{ required: true, message: '请输入辅料名称', trigger: 'blur' }],
					price: [{ required: false, message: '请输入单价', trigger: 'blur' }],
					stock: [{ required: true, message: '请选择库存', trigger: 'change' }],
				},
				//编辑界面数据
				editForm: {
					name: '',
					price: '',
					stock: false,					
				},


				//右侧原料表格
				searchMsgRawmaterial: {
					pageNo: 1,
					pageSize: 20
				},
				listRawmaterial: [],
				totalRawmaterial: 0,
				listLoadingRawmaterial: false,
				selsRawmaterial: [],//列表选中列
				//右侧列表对应的商品id
				goodsIdRawmaterialTable : '',


				//设置配比的原料列表
				editFormVisibleRelationTable: false,//编辑界面是否显示
				editLoadingRelationTable: false,
				relationTable: '',
				multipleSelectionRelationTable: [],
				//关联商品的列表
				searchMsgRelationTable: {
					pageNo: -1,
					pageNoIndex: 1,
					pageSize: 20
				},		
				listRelationTable: [],
				totalRelationTable: 0,
				listLoadingRelationTable: false,
				selsRelationTable: [],//列表选中列			
				goodsIdRelationTable: [] ,		
				//耗量数组，以原料id为数组下标
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
			formatType (row, column) { // 0=正常/启用  1=禁用
				return row.stock == 1 ? '有货' : '无货';
			},
			addUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '元'
			},			
			handleCurrentChange(val) {
				this.searchMsg.pageNo = val;
				this.getList();
			},					
						getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 获取商品列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/admin/goodsRawmaterialRelation/find/goodsRawmaterialRelation', param,
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
			handleDetail (goodsId) { // 查看原料配比详情，刷新右侧列表
				this.goodsIdRawmaterialTable = goodsId;
				let vue = this;
				vue.listLoadingRawmaterial = true;
				let param = Object.assign(vue.searchMsgRawmaterial);
				param.goodsId = goodsId;
				vue.$http.post(vue, '/rest/admin/goodsRawmaterialRelation/find/goodsRawmaterialRelationByGoodsId', param,
					function(vue, data) {
						vue.listRawmaterial = data.data.records
						vue.totalRawmaterial = data.data.total
						vue.listLoadingRawmaterial = false;
					}, function(error, data) {
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
					vue.$http.delete(vue, '/rest/admin/goodsRawmaterialRelation/delete', {"id" : id},
						function(vue, data) {
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'success'
							});
							//刷新右侧关联原料表格
							vue.handleDetail(vue.goodsIdRawmaterialTable);
							//刷新左侧商品表格
							vue.getList();
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
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
				if(row){
					this.editForm = {
						id: row.id,
						rawmaterialName: row.rawmaterialName,
						consumedQuantity: row.consumedQuantity,
						rawmaterialUnit: row.rawmaterialUnit
					}
				}else{
					this.editForm = {
						rawmaterialName: '',
						consumedQuantity: '',
						rawmaterialUnit: '',						
					}
				}

				//不用这种方式回写，这种方式会导致修改提交的时候所有属性都提交上来
				// if(row){
				// 	this.editForm = Object.assign({}, row);
				// 	this.editForm.selectedOptions2 = [row.province, row.city, row.area];	
				// }else{
				// 	this.editForm = {}
				// }							
			},
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						// this.editLoading = true;
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = '';

						delete param.rawmaterialName;
						delete param.rawmaterialUnit;

						url = '/rest/admin/goodsRawmaterialRelation/update';
						vue.$http.put(vue, url, param,
							(vue, data) => {
								// this.editLoading = false;
								vue.$message({
									showClose: true,
									message: data.message,
									type: 'success'
								});
								//刷新右侧关联原料表格(左侧商品表格不需要刷新)
								vue.handleDetail(vue.goodsIdRawmaterialTable);
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
				});
			},
			getListRelationTable() { // 获取原料配比列表
				let vue = this
        		let param = Object.assign(vue.searchMsgRelationTable);

				vue.listLoadingRelationTable = true;
				vue.$http.post(vue, '/rest/admin/rawmaterial/list', param,
					(vue, data) => {
						vue.listRelationTable = data.data.records
						vue.totalRelationTable = data.data.total
						vue.listLoadingRelationTable = false;

						//this.$refs.moviesTable.toggleRowSelection(row)
					},(error, data)=> {
						vue.listLoadingRelationTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
					}
				)
			},				
			handleAddRelation (id) { // 设置配比
				let vue = this
				if(this.multipleSelectionGoodsTable.length == 0){
					vue.$message({
						showClose: true,
						message: '请选择要配比原料的商品',
						type: 'error'
					});
					return false							
				}					

				this.$nextTick(function () {
					//清除之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
					this.toggleSelectionSingleRelationTable();	
				})			
				//清除表格中的耗量输入框数据
				this.consumedQuantityRelationTable = [];

				this.editFormVisibleRelationTable = true;
				this.goodsIdRelationTable = this.multipleSelectionGoodsTable;
				console.log("点击原料配比，选中的商品id如下：");
				console.log(this.goodsIdRelationTable);
			},		
			editSubmitRelationTable: function () { // 原料配比保存事件
				let vue = this
				let param = {
					goodsIdListStr : [],
					relationListStr : []
				};
				let url = '';

				if(this.multipleSelectionRelationTable.length == 0){
					vue.$message({
						showClose: true,
						message: '请选择要配比的原料',
						type: 'error'
					});
					return false							
				}						

				console.log("点击原料配比，选中的商品id如下：");
				console.log(this.goodsIdRelationTable);				
				console.log(this.multipleSelectionRelationTable);	
				console.log(this.consumedQuantityRelationTable);

				//生成商品id列表
				let goodsIdList = [];
				for(let i = 0; i < this.goodsIdRelationTable.length; i++){
					goodsIdList.push(this.goodsIdRelationTable[i].goodsId);
				}
				param.goodsIdListStr = JSON.stringify(goodsIdList);

				//生成商品原料关联列表
				let relationList = [];
				for(let i = 0; i < this.multipleSelectionRelationTable.length; i++){
					// alert(this.consumedQuantityRelationTable[this.multipleSelectionRelationTable[i].id])
					let consumedQuantity = this.consumedQuantityRelationTable[this.multipleSelectionRelationTable[i].id];
					if(consumedQuantity==undefined || consumedQuantity==""){
						vue.$message({
							showClose: true,
							message: '请填写' + this.multipleSelectionRelationTable[i].name + '原料的耗量',
							type: 'error'
						});
						return false							
					}
					let map = {
						"rawmaterialId" : this.multipleSelectionRelationTable[i].id,
						"consumedQuantity" : consumedQuantity
					}
					relationList.push(map);
				}
				param.relationListStr = JSON.stringify(relationList);

				// this.editLoadingRelationTable = true;
			
				url = '/rest/admin/goodsRawmaterialRelation/insert';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						// this.editLoadingRelationTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
						});
						//刷新左侧商品表格
						vue.getList();
						//如果有点击查看右侧列表的数据，则刷新右侧关联原料表格
						if(vue.goodsIdRawmaterialTable != undefined && vue.goodsIdRawmaterialTable != ''){
							vue.handleDetail(vue.goodsIdRawmaterialTable);						
						}
						vue.editFormVisibleRelationTable = false;		
						this.$nextTick(function () {
							//清除左侧商品列表之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
							this.toggleSelectionSingleGoodsTable();	
						})												
					}, (error, data) => {
						vue.editFormVisibleRelationTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'error'
						});
						this.$nextTick(function () {
							//清除左侧商品列表之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
							this.toggleSelectionSingleGoodsTable();	
						})																	
					}
				)		
				
				// axios.post(url, paramy, {headers: {"content-type" : "application/json", token : "256c5505cead4e01b30b46053ba6bbd3"}})
				// .then(data => {
                //         data = decodeURIComponent(data);
                //         if(data == ""){
                //             alert("连接HttpPrinter失败");
                //         }else{
                //             var obj = JSON.parse(data);
                //             if(obj.status=="ok"){
                //                 alert("打印成功");
                //             }else{
                //                 alert("打印失败：" + obj.data);
                //             }
                //         }
				// })
				// .catch(function(data) {
				// 	vue.listLoading = false;
				// 	vue.$message({
				// 		showClose: true,
				// 		message: data.message,
				// 		type: 'error'
				// 	});
				// });			
				
				
			},					
			toggleSelection(rows) {
				if (rows) {
					rows.forEach(row => {
						this.$refs.relationTable.toggleRowSelection(row);
					});
				} else {
					this.$refs.relationTable.clearSelection();
				}
			},
			toggleSelectionSingleGoodsTable(row) {
				if (row) {
					this.$refs.goodsTable.toggleRowSelection(row);
				} else {
					this.$refs.goodsTable.clearSelection();
				}
			},		
			toggleSelectionSingleRelationTable(row) {
				if (row) {
					this.$refs.relationTable.toggleRowSelection(row);
				} else {
					this.$refs.relationTable.clearSelection();
				}
			},												
			handleSelectionChangeGoodsTable(val) {
				this.multipleSelectionGoodsTable = val;
				console.log(this.multipleSelectionGoodsTable);
			},
			handleSelectionChangeRelationTable(val) {
				this.multipleSelectionRelationTable = val;
				console.log(this.multipleSelectionRelationTable);
				// alert(this.goodsIdRelationTable.length);
			},
			change(e){
				this.$forceUpdate();
			}
		},
		mounted() {
			this.getList();
			this.getListRelationTable();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>
	/* 还是得用百分比来控制 */
	.left-table{
		width: 50%;
		float: left;		
	}
	.right-table{
		width: 46%;
		float: right;		
	}
</style>