<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg" size="small">
                <!-- <el-form-item>
					<el-input clearable v-model="searchMsg.id" placeholder="请输入用户id"></el-input>
				</el-form-item> -->
                <el-form-item>
					<el-input clearable v-model="searchMsg.username" placeholder="请输入用户名"></el-input>
				</el-form-item>
				<el-form-item>
					<el-input clearable v-model="searchMsg.mobile" placeholder="请输入手机号搜索"></el-input>
				</el-form-item>
				<!-- <el-form-item>
					<el-input clearable v-model="searchMsg.vipNo" placeholder="请输入会员编号搜索"></el-input>
				</el-form-item>                 -->
				<el-form-item>
					<el-select v-model="searchMsg.type" placeholder="请选择用户类型搜索" clearable>
						<el-option label="普通用户" :value="1"></el-option>
						<el-option label="VIP会员" :value="2"></el-option>    
					</el-select>
				</el-form-item>
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
			</el-table-column>             -->
            <!-- <el-table-column prop="id" label="用户id"></el-table-column> -->
			<el-table-column prop="username" label="用户名"></el-table-column>
            <el-table-column prop="mobile" label="手机号"></el-table-column>
            <!-- <el-table-column prop="nickname" label="昵称"></el-table-column> -->
            <el-table-column prop="type" label="类型" :formatter="formatType"></el-table-column>
            <el-table-column prop="vipNo" label="会员编号"></el-table-column>
            <!-- <el-table-column prop="inviteCode" label="邀请码"></el-table-column> -->
            <el-table-column prop="balance" label="余额">
                <template scope="scope">
                    <span>{{scope.row.balance}}元</span>
                </template>                      
            </el-table-column>            
            <el-table-column prop="points" label="积分">
                <template scope="scope">
                    <span>{{scope.row.points}}个</span>
                </template>                      
            </el-table-column>
            <!-- <el-table-column prop="isDisabled" label="是否禁用" :formatter="formatisDisabledType"></el-table-column> -->
            <!-- <el-table-column prop="isDelete" label="是否为删除用户" :formatter="formatDeleteType"></el-table-column> -->
            <el-table-column prop="createTime" label="注册时间" :formatter="formatTime" width="180"></el-table-column>
            <el-table-column prop="lastLoginTime" label="最后登录时间" :formatter="formatTime" width="180"></el-table-column>
            <el-table-column prop="lastUseTime" label="最后使用小程序的时间" :formatter="formatTime" width="180"></el-table-column>
            <el-table-column prop="lastUseAddress" label="最后使用小程序的定位地址" width="180"></el-table-column>
            <el-table-column label="操作" fixed="right" width="200">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
                    <!-- <el-button type="primary" v-if="scope.row.type == 1" size="small" @click="handleRechargeVip(scope.row)">开通会员</el-button> -->
                    <!-- <el-button type="primary" v-if="scope.row.type == 2" size="small" @click="handleRechargeVip(scope.row)">续费会员</el-button> -->
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

        <!--编辑账户dialog-->
        <el-dialog title="编辑" :visible.sync="editFormVisible" :close-on-click-modal="false">
			<el-form :model="editForm"  label-width="150px" ref="editForm">
                <el-form-item label="用户名" prop="username">
					<el-input v-model="editForm.username" disabled></el-input>
				</el-form-item>
                 <el-form-item label="手机号" prop="mobile">
					<el-input v-model="editForm.mobile" disabled></el-input>
				</el-form-item>
                 <el-form-item label="昵称" prop="nickname">
					<el-input v-model="editForm.nickname" disabled></el-input>
				</el-form-item>
                <el-form-item label="是否禁用" prop="isDisabled">
					<el-radio-group v-model="editForm.isDisabled" size="medium">
						<el-radio-button label="启用" value="0"></el-radio-button>
						<el-radio-button label="禁用" value="1"></el-radio-button>
					</el-radio-group>	                    
                </el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit">提交</el-button>
			</div>
		</el-dialog>
        
        <!--会员管理dialog-->
        <el-dialog title="会员管理" :visible.sync="editFormVisible2" :close-on-click-modal="false">
            <el-form :model="editForm2" label-width="150px" style="width: 60%;" ref="editForm2" :rules="editForm2Rules">
                <!-- <el-form-item label="会员类型" prop="type">
                    <el-radio-group v-model="editForm2.type">
                        <el-radio class="radio" :label="1">超级会员</el-radio>
                        <el-radio class="radio" :label="2">黄金会员</el-radio>
                        <el-radio class="radio" :label="3">钻石会员</el-radio>
                    </el-radio-group>
                </el-form-item> -->
                <el-form-item label="充值时长(月)" prop="rechargeTime">
                    <el-input-number :min="0" v-model="editForm2.rechargeTime"></el-input-number>
                </el-form-item>
                <el-form-item label="充值费用" prop="amount">
                    <el-input v-model="editForm2.amount"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible2 = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit2">提交</el-button>
			</div>
        </el-dialog>
	</section>
</template>

<script>
import { type } from 'os';
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
                searchMsg:{
                    pageNo:1,
                    pageSize:20
                },
				list: [],
                total:0,
                listLoading:false,
                editFormVisible:false,
                editFormVisible2:false,
                editForm:{
                   username:'',
                   mobile:'',
                   nickname:'',
                   inviteCode:'',
                   isDisabled:'',
                   isDelete:'',
                   type:''
                },
                editForm2:{
                    memberId:'',
                },
				editForm2Rules: {
					rechargeTime: [{ required: true, message: '请输入充值时长', trigger: 'blur' }],
					amount: [{ required: true, message: '请输入充值费用', trigger: 'blur' }]
                },
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
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				let str = this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
				// 处理数据库中时间为NULL场景
				if(str == "1970-01-01"){
					str = "-";
				}
        		return str;                
            },
            formatisDisabledType(row,column){
                return row.isDisabled == 0 ? '启用' : '禁用'
            },
            formatDeleteType(row,column){
                return row.isDelete == 0 ? '未删除' : '已删除'
            },
            formatType(row,column){
                return row.type == 1 ? '普通用户' : row.type == 2 ? 'VIP会员' : '-';
            },
            getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				    this.searchMsg.pageNo = pageNoParam;
				} // 获取列表
				let vue = this
                let param = Object.assign({}, vue.searchMsg);

				//处理开始日期、结束日期
				if(vue.searchMsg.createTime){
					let startDate = vue.searchMsg.createTime[0];
					let endDate = vue.searchMsg.createTime[1];
					param.startCreateTime = this.$utils.formatDate(new Date(startDate), 'yyyy/MM/dd hh:mm:ss');
					param.endCreateTime = this.$utils.formatDate(new Date(endDate), 'yyyy/MM/dd hh:mm:ss');
					delete param.createTime;
				}

				vue.listLoading = true;
				vue.$http.post(vue, '/rest/admin/member/list',param,
					(vue, data) => {
                        vue.listLoading = false
						vue.list = data.data.records
						vue.total = data.data.total
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
            editSubmit(){//编辑账户
               this.$refs.editForm.validate((valid)=>{
                   if(valid){
                        let vue = this;
                        let param = Object.assign({},vue.editForm);

                        param.isDisabled = (param.isDisabled == '启用') ? 0 : 1;
                        
                        vue.$http.post(vue,'/rest/admin/member/update' , param,
                        (vue,data)=>{
                            vue.$message({
                                showClose: true,
                                message: data.message,
                                type: 'success'
                            });
                            vue.getList();
                            vue.$refs['editForm'].resetFields();
                            vue.editFormVisible = false;
                        },(error,data)=>{
                            vue.editFormVisible = false;
                            vue.$message({
                                showClose: true,
                                message: data.message,
                                type: 'error'
                            });
                        })
                   }
                })
            },
            editSubmit2(){//充值
               this.$refs.editForm2.validate((valid)=>{
                   if(valid){
                        let vue = this;
                        let param = Object.assign({},vue.editForm2);
                        vue.$http.post(vue,'/rest/admin/vipRechargeRecord/updateVip' , param,
                        (vue,data)=>{
                            vue.$message({
                                showClose: true,
                                message: data.message,
                                type: 'success'
                            });
                            vue.getList();
                            vue.$refs['editForm2'].resetFields();
                            vue.editFormVisible2 = false;
                        },(error,data)=>{
                            vue.editFormVisible2 = false;
                            vue.$message({
                                showClose: true,
                                message: data.message,
                                type: 'error'
                            });
                        })
                   }
                })
            },
            handleEdit(row){
                this.editFormVisible = true;
                this.editForm.id = row.id
                this.editForm.username = row.username
                this.editForm.mobile = row.mobile
                this.editForm.nickname = row.nickname
                this.editForm.inviteCode = row.inviteCode
                this.editForm.isDisabled = (row.isDisabled == false) ? '启用' : '禁用';
                this.editForm.type = row.type
            },
            handleRechargeVip(row){
                this.editFormVisible2 = true;
                this.editForm2.memberId = row.id;
            },
			handleSizeChange(val) {
				this.searchMsg.pageSize = val;
				this.getList();
			},            
			handleCurrentChange(val){
				this.searchMsg.pageNo = val;
				this.getList();
            }
		},
		mounted() {
            this.getList();
            //开启订单自动打印定时器
            this.$orderPrint.init();
		}
	}

</script>

<style scoped>

</style>