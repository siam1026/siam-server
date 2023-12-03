<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-input v-model="searchMsg.name" placeholder="请输入商品名称搜索"></el-input>
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
			</el-table-column> -->
      		<el-table-column label="商品主图" prop="mainImage">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
				</template>
			</el-table-column>
      		<el-table-column prop="name" label="商品名称"></el-table-column>
			<el-table-column prop="goodsNo" label="编号"></el-table-column>
			<el-table-column prop="stock" label="库存" :formatter="formatStock"></el-table-column>
			<el-table-column label="操作" fixed="right">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-->
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="searchMsg.pageSize" :total="total" style="float:right;">
			</el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="编辑库存" :visible.sync="editFormVisible" @close="editFormClose" :close-on-click-modal="false">
			<el-form size="small" :model="editForm" class="editForm" label-width="80px" style="width: 80%;">
				<el-form-item prop="stock" label="库存">
					<el-input v-model="editForm.stock"  placeholder="库存"></el-input>
				</el-form-item>
				<!-- <el-form-item>
					<el-button @click="addSpecification(item)">保存</el-button>
				</el-form-item> -->
			</el-form>
			<!-- <div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">关闭</el-button>
			</div> -->
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
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
				//编辑界面数据
				editForm: {},
        		searchData:[],
			}
		},
		methods: {
			addUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '元';
			},
			formatStock(row, column) {
				let stock = row[column.property];
				return stock;
			},			
			cellStyle({row, column, rowIndex, columnIndex}){
				return "text-align:center";
			},
			headerCellStyle({row, rowIndex}){
				return "text-align:center";
			},			
			editFormClose() {
				this.editForm = {}
			},
			editSubmit: function() { // 编辑规格
				let vue = this
				let param = Object.assign({}, vue.editForm)
				let regEn = /^[1-9]\d*$/;
				if (!param.stock) {
					vue.$message({
						showClose: true,
						message: '请输入库存',
						type: 'error'
					});
					return false
				}
				delete param.createTime
				delete param.updateTime

				vue.$http.post( vue, '/rest/admin/goods/update', param,
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
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				return this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm');
			},
			formatType (row, column) { // 0-待上架，1-已上架，2-已下架，3-已删除,4-售罄
				let type = row[column.property] 
				switch (type) {
				case 0:
					return '待上架'
					break;
				case 1:
					return '已上架'
					break;
				case 2:
					return '已下架'
					break;
				case 3:
					return '已删除'
					break;
				default:
				return '售罄'
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
				} // 获取规格列表
				let vue = this
        		let param = Object.assign({}, vue.searchMsg)
				vue.listLoading = true;
						vue.$http.post(vue, '/rest/admin/goods/list', param,
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
					handleEdit: function (index, row) { // 显示编辑界面
						this.editFormVisible = true;
				this.editForm = Object.assign({}, row)
			}
		},
		mounted() {
			this.getList();
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