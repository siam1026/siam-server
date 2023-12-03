<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<!-- <el-form-item>
					<el-input v-model="searchMsg.name" placeholder="请输入商品名称搜索"></el-input>
				</el-form-item>
        <el-form-item>
					<el-input v-model="searchMsg.menuName" placeholder="请输入商品类别搜索"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchMsg.status" placeholder="请选择状态搜索" clearable>
						<el-option label="待上架" :value="1"></el-option>
						<el-option label="已上架" :value="2"></el-option>
						<el-option label="已下架" :value="3"></el-option>
						<el-option label="售罄" :value="4"></el-option>       
					</el-select>
				</el-form-item>        
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item> -->
				<el-form-item>
					<el-button type="primary" @click="handleRelationGoods()">新增</el-button>
				</el-form-item>
				<!-- <el-form-item>
          <el-button type="primary" @click="printOrderLabel">打印</el-button>          
				</el-form-item> -->
			</el-form>
		</el-col>
		<!--列表-->
		<el-table :data="list" ref="goodsTable" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
			<!-- <el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>
			</el-table-column> -->
			<el-table-column label="商品主图" prop="mainImage">
						<template slot-scope="scope">
							<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
						</template>
					</el-table-column>
			<el-table-column prop="goodsName" label="商品名称"></el-table-column>
					<!-- <el-table-column prop="goodsNo" label="编号"></el-table-column> -->
			<el-table-column prop="goodsPrice" label="一口价" :formatter="addUnit"></el-table-column>
			<!-- <el-table-column prop="salePrice" label="折扣价" :formatter="formatSalePrice"></el-table-column> -->
			<el-table-column prop="packingCharges" label="包装费" :formatter="addUnit"></el-table-column>
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

		<!--关联商品界面-->
		<el-dialog title="关联商品" :visible.sync="editFormVisibleGoodsTable" @close="closeDialog" :close-on-click-modal="false">
      <div class="dialogDiv">		
				<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
					<!--列表-->
					<el-table :data="listGoodsTable" ref="goodsTable_dialog" @selection-change="handleSelectionChange" highlight-current-row v-loading="listLoadingGoodsTable" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
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
						<el-table-column prop="packingCharges" label="包装费" :formatter="addUnit"></el-table-column>
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
				editFormRules: {
          specificationName: [{ required: true, message: '请输入规格名称', trigger: 'change' }],
          name: [{ required: true, message: '请输入规格选项名称', trigger: 'change' }],
          price: [{ required: true, message: '请输入加价金额', trigger: 'blur' }],
					// stock: [{ required: true, message: '请输入库存', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: [],
        searchData:[],
        goodsId: '',
				goodsTable: '',

				editFormVisibleGoodsTable: false,//编辑界面是否显示
				editLoadingGoodsTable: false,
				goodsTable_dialog: '',
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
      editFormClose() {
        this.editForm = []
        // this.editForm.push({price: '', name: '', stock: ''}) 
      },
      addUnit(row, column) { // 添加单位
        return (row[column.property] || 0) + '元'
      },
      formatSalePrice(row, column) { // 添加单位
        if(row.isSale){
          return row.salePrice + '元';
        }else{
          return "-";
        }
      },            
      gotoAdd(type, row) {
        if(type === 'add') {
          this.$router.push({path: 'addGoods'})
        } else {
          this.$router.push({path:'editGoods',query:{id: row.id}})
        }
      },
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				return this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm');
			},
			formatappType (row, column) { // app类型0=android  1=ios
				return row.apptype == 1 ? 'ios' : 'android';
			},
			formatType (row, column) { // 0-待上架，1-已上架，2-已下架，3-已删除,4-售罄
				let type = row[column.property] 
				switch (type) {
				case 1:
					return '待上架'
					break;
				case 2:
					return '已上架'
					break;
				case 3:
					return '已下架'
					break;
				case 4:
					return '售罄'
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
				}
				let vue = this
        let param = Object.assign({}, vue.searchMsg, {"token":sessionStorage.getItem("token")});

        console.log("param="+JSON.stringify(param));
        console.log("searchMsg="+JSON.stringify(vue.searchMsg));

				vue.listLoading = true;
				vue.$http.post(vue, '/rest/merchant/merchantRecommendGoods/list', param,
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
			getListGoodsTable() { // 获取商品列表
				let vue = this
        let param = Object.assign({}, vue.searchMsgGoodsTable, {"token":sessionStorage.getItem("token")});

				console.log("param="+JSON.stringify(param));
				console.log("searchMsg="+JSON.stringify(vue.searchMsgGoodsTable));

				vue.listLoadingGoodsTable = true;
				vue.$http.post(vue, '/rest/merchant/goods/list', param,
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
			handleRelationGoods () { // 关联商品
				this.$nextTick(function () {
					//清除之前选中的数据，如果不加nextTick就会报错，但是下面放在循环里面或者按钮出发的就不会报错
					this.toggleSelectionSingle();	
				})			
				this.editFormVisibleGoodsTable = true;
				let vue = this
				vue.$http.post(vue, '/rest/merchant/merchantRecommendGoods/list', {pageNo : 1, pageSize : 1000},
					(vue, data) => {
						let goodsList = data.data.records
						// alert(goodsList.length);
						if(goodsList.length > 0){
							//默认选中已经关联的商品
							for(let i = 0; i < goodsList.length; i++){
								for(let j = 0; j < this.listGoodsTable.length; j++){
                  // console.log(goodsList[i].goodsId + " -- " + this.listGoodsTable[j].id);
									if(goodsList[i].goodsId == this.listGoodsTable[j].id){
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
				}else if(this.multipleSelection.length > 6){
					vue.$message({
						showClose: true,
						message: '最多选择6件商品',
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
				
				// this.editLoadingGoodsTable = true;
			
				url = '/rest/merchant/merchantRecommendGoods/insert';
				vue.$http.post(vue, url, param,
					(vue, data) => {
						// this.editLoadingGoodsTable = false;
						vue.$message({
							showClose: true,
							message: data.message,
							type: 'success'
						});
						this.getList();
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
						this.$refs.goodsTable_dialog.toggleRowSelection(row);
					});
				} else {
					this.$refs.goodsTable_dialog.clearSelection();
				}
			},
			toggleSelectionSingle(row) {
				if (row) {
					this.$refs.goodsTable_dialog.toggleRowSelection(row);
				} else {
					this.$refs.goodsTable_dialog.clearSelection();
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
  .el-button+.el-button {
    margin-top: 5px;
    margin-left: 0;
  }
  .dialogDiv {
      height: 560px;
      overflow-y: auto;
      overflow-x: hidden;
  }
  /* 将列表的图片宽高比例调大一点 */
  .el-table__body img{
    width: 120px;
    height: 120px;
  }  
</style>