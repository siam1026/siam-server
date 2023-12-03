<template>
	<section>
		<el-form :model="editForm" label-width="300px" style="width: 70%;" :rules="editFormRules" ref="editForm">
			<!-- 用户数据相关区域 -->	
			<el-form-item label="新用户注册赠送" prop="registrationRewardPoints">
				<el-input v-model="editForm.registrationRewardPoints" class="input-width">
					<template slot="append">个</template>
				</el-input>
				<span class="note-margin">积分</span>
			</el-form-item>			
			<el-form-item label="新用户注册赠送" prop="registrationRewardInviteRewardAmount">
				<el-input v-model="editForm.registrationRewardInviteRewardAmount" class="input-width">
					<template slot="append">个</template>
				</el-input>
				<span class="note-margin">奖励金</span>
			</el-form-item>
			<el-form-item label="购买1杯奶茶赠送" prop="purchaseRewardPoints">
				<el-input v-model="editForm.purchaseRewardPoints" class="input-width">
					<template slot="append">个</template>
				</el-input>
				<span class="note-margin">积分</span>
			</el-form-item>				
			<el-form-item label="奖励金累计到(≥)" prop="memberWithdrawMeetAmount">
				<el-input v-model="editForm.memberWithdrawMeetAmount" class="input-width">
					<template slot="append">元</template>
				</el-input>
				<span class="note-margin">可以提现</span>
			</el-form-item>
			<el-form-item label="用户提现手续费比例" prop="memberWithdrawFee">
				<el-input v-model="editForm.memberWithdrawFee" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>
			<el-form-item label="用户提现金额达到(≥)" prop="memberWithdrawAuditThreshold">
				<el-input v-model="editForm.memberWithdrawAuditThreshold" class="input-width">
					<template slot="append">元</template>
				</el-input>
				<span class="note-margin">需要人工审核</span>
			</el-form-item>
			<br/>
			<br/>
			<br/>
			<!-- 商家数据相关区域 -->
			<el-form-item label="商家备餐时间" prop="merchantMealPreparationTime">
				<el-input v-model="editForm.merchantMealPreparationTime" class="input-width">
					<template slot="append">分钟</template>
				</el-input>
			</el-form-item>
			<el-form-item label="系统默认起送价格" prop="startDeliveryPrice">
				<el-input v-model="editForm.startDeliveryPrice" class="input-width">
					<template slot="append">元</template>
				</el-input>
			</el-form-item>		
			<el-form-item label="配送起步距离" prop="deliveryStartingDistance">
				<el-input v-model="editForm.deliveryStartingDistance" class="input-width">
					<template slot="append">公里</template>
				</el-input>
			</el-form-item>					
			<el-form-item label="配送起步价" prop="deliveryStartingPrice">
				<el-input v-model="editForm.deliveryStartingPrice" class="input-width">
					<template slot="append">元</template>
				</el-input>
			</el-form-item>		
			<el-form-item label="配送公里价" prop="deliveryKilometerPrice">
				<el-input v-model="editForm.deliveryKilometerPrice" class="input-width">
					<template slot="append">元/公里</template>
				</el-input>
			</el-form-item>		
			<el-form-item label="配送距离上限" prop="deliveryDistanceLimit">
				<el-input v-model="editForm.deliveryDistanceLimit" class="input-width">
					<template slot="append">公里</template>
				</el-input>
			</el-form-item>	
			<el-form-item label="商家提现手续费比例" prop="merchantWithdrawFee">
				<el-input v-model="editForm.merchantWithdrawFee" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>
			<br/>
			<br/>
			<br/>
			<!-- 平台数据相关区域 -->
			<el-form-item label="用户下单系统抽佣比例" prop="orderSystemExtractionRatio">
				<el-input v-model="editForm.orderSystemExtractionRatio" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>
			<el-form-item label="客服电话" prop="customerServicePhone">
				<el-input v-model="editForm.customerServicePhone" class="input-width"></el-input>
			</el-form-item>			
			<el-form-item label="客服微信" prop="customerServiceWechat">
				<el-input v-model="editForm.customerServiceWechat" class="input-width"></el-input>
			</el-form-item>			
			<el-form-item label="客服微信二维码（宽400px * 高400px）" prop="customerServiceWechatQrcodeFile" class="customerServiceWechatQrcodeFileItem">
				<el-upload
					class="avatar-uploader" accept=".image, .png, .jpg"
					action="" multiple name="customerServiceWechatQrcodeUpload"
					:on-remove="handleRemoveCustomerServiceWechatQrcode"
              		:file-list="editForm.customerServiceWechatQrcodeFile"					
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card">
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
			</el-form-item>
			<br/>
			<br/>
			<br/>
			<!-- 邀请佣金数据相关区域 -->
       		<el-form-item label="平台邀请佣金占订单实付款的比例" prop="inviteeConsumeCommission">
				<el-input v-model="editForm.inviteeConsumeCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>
		  	<el-form-item label="无上级邀请人时，下单用户佣金占比" prop="caseoneOwnCommission">
				<el-input v-model="editForm.caseoneOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>			
			<el-form-item label="有1个上级邀请人时，下单用户佣金占比" prop="casetwoOwnCommission">
				<el-input v-model="editForm.casetwoOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
			<el-form-item label="有1个上级邀请人时，一级邀请人佣金占比" prop="casetwoFirstLevelInviterCommission">
				<el-input v-model="editForm.casetwoFirstLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
		   	<el-form-item label="有2个上级邀请人，下单用户佣金占比" prop="casethreeOwnCommission">
				<el-input v-model="editForm.casethreeOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
			<el-form-item label="有2个上级邀请人，一级邀请人佣金占比" prop="casethreeFirstLevelInviterCommission">
				<el-input v-model="editForm.casethreeFirstLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>		
			<el-form-item label="有2个上级邀请人，二级邀请人佣金占比" prop="casethreeSecondLevelInviterCommission">
				<el-input v-model="editForm.casethreeSecondLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>					
			<br/>
			<br/>
			<br/>
			<!-- 积分商城-邀请佣金数据相关区域 -->
       		<el-form-item label="积分商城-平台邀请佣金占订单实付款的比例" prop="pointsMallInviteeConsumeCommission">
				<el-input v-model="editForm.pointsMallInviteeConsumeCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>
		  	<el-form-item label="积分商城-无上级邀请人时，下单用户佣金占比" prop="pointsMallCaseoneOwnCommission">
				<el-input v-model="editForm.pointsMallCaseoneOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>			
			<el-form-item label="积分商城-有1个上级邀请人时，下单用户佣金占比" prop="pointsMallCasetwoOwnCommission">
				<el-input v-model="editForm.pointsMallCasetwoOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
			<el-form-item label="积分商城-有1个上级邀请人时，一级邀请人佣金占比" prop="pointsMallCasetwoFirstLevelInviterCommission">
				<el-input v-model="editForm.pointsMallCasetwoFirstLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
		   	<el-form-item label="积分商城-有2个上级邀请人，下单用户佣金占比" prop="pointsMallCasethreeOwnCommission">
				<el-input v-model="editForm.pointsMallCasethreeOwnCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>	
			<el-form-item label="积分商城-有2个上级邀请人，一级邀请人佣金占比" prop="pointsMallCasethreeFirstLevelInviterCommission">
				<el-input v-model="editForm.pointsMallCasethreeFirstLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>		
			<el-form-item label="积分商城-有2个上级邀请人，二级邀请人佣金占比" prop="pointsMallCasethreeSecondLevelInviterCommission">
				<el-input v-model="editForm.pointsMallCasethreeSecondLevelInviterCommission" class="input-width">
					<template slot="append">%</template>
				</el-input>
			</el-form-item>			

			<div class="el-dialog__footer">
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>																				
			</div>			
		</el-form>		
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
				editFormRules: {
					memberWithdrawAuditThreshold: [{ required: true, message: '请输入用户提现金额需审核门槛', trigger: 'blur' }],
					deliveryDistanceLimit: [{ required: true, message: '请输入配送距离上限', trigger: 'blur' }],
					purchaseRewardPoints: [{ required: true, message: '请输入购买一杯奶茶赠送积分数量', trigger: 'blur' }],
					registrationRewardPoints: [{ required: true, message: '请输入新用户注册奖励积分数量', trigger: 'blur' }],
					merchantWithdrawFee: [{ required: true, message: '请输入商家提现手续费', trigger: 'blur' }],					
					orderSystemExtractionRatio: [{ required: true, message: '请输入用户下单系统抽佣比例', trigger: 'blur' }],					
					merchantMealPreparationTime: [{ required: true, message: '请输入商家备餐时间', trigger: 'blur' }],					
					startDeliveryPrice: [{ required: true, message: '请输入起送价格', trigger: 'blur' }],					
					deliveryStartingDistance: [{ required: true, message: '请输入配送起步距离', trigger: 'blur' }],					
					deliveryStartingPrice: [{ required: true, message: '请输入配送起步价', trigger: 'blur' }],					
					deliveryKilometerPrice: [{ required: true, message: '请输入配送公里价', trigger: 'blur' }],					
					memberWithdrawFee: [{ required: true, message: '请输入用户提现手续费', trigger: 'blur' }],
					registrationRewardInviteRewardAmount: [{ required: true, message: '请输入新用户注册奖励-邀请新用户注册奖励金额', trigger: 'blur' }],
					memberWithdrawMeetAmount: [{ required: true, message: '请输入邀请新用户注册奖励金额满足多少方可提现', trigger: 'blur' }],					
					inviteeConsumeCommission: [{ required: true, message: '请输入平台邀请佣金占订单金额的百分比', trigger: 'blur' }],
					caseoneOwnCommission: [{ required: true, message: '请输入无上级邀请人时，下单用户佣金占比', trigger: 'blur' }],	
					casetwoOwnCommission: [{ required: true, message: '请输入有1个上级邀请人时，下单用户佣金占比', trigger: 'blur' }],
					casetwoFirstLevelInviterCommission: [{ required: true, message: '请输入有1个上级邀请人时，一级邀请人佣金占比', trigger: 'blur' }],
					casethreeOwnCommission: [{ required: true, message: '请输入有2个上级邀请人，下单用户佣金占比', trigger: 'blur' }],
					casethreeFirstLevelInviterCommission: [{ required: true, message: '请输入有2个上级邀请人，一级邀请人佣金占比', trigger: 'blur' }],
					casethreeSecondLevelInviterCommission: [{ required: true, message: '请输入有2个上级邀请人，二级邀请人佣金占比', trigger: 'blur' }],
					customerServicePhone: [{ required: true, message: '请输入客服电话', trigger: 'blur' }],
					customerServiceWechat: [{ required: true, message: '请输入客服微信', trigger: 'blur' }],
					customerServiceWechatQrcodeFile: [{ type: 'array', required: true, message: '请上传客服微信二维码', trigger: 'change' }],
					pointsMallInviteeConsumeCommission: [{ required: true, message: '请输入积分商城-平台邀请佣金占订单金额的百分比', trigger: 'blur' }],
					pointsMallCaseoneOwnCommission: [{ required: true, message: '请输入积分商城-无上级邀请人时，下单用户佣金占比', trigger: 'blur' }],	
					pointsMallCasetwoOwnCommission: [{ required: true, message: '请输入积分商城-有1个上级邀请人时，下单用户佣金占比', trigger: 'blur' }],
					pointsMallCasetwoFirstLevelInviterCommission: [{ required: true, message: '请输入积分商城-有1个上级邀请人时，一级邀请人佣金占比', trigger: 'blur' }],
					pointsMallCasethreeOwnCommission: [{ required: true, message: '请输入积分商城-有2个上级邀请人，下单用户佣金占比', trigger: 'blur' }],
					pointsMallCasethreeFirstLevelInviterCommission: [{ required: true, message: '请输入积分商城-有2个上级邀请人，一级邀请人佣金占比', trigger: 'blur' }],
					pointsMallCasethreeSecondLevelInviterCommission: [{ required: true, message: '请输入积分商城-有2个上级邀请人，二级邀请人佣金占比', trigger: 'blur' }],					
				},
				//编辑界面数据
				editForm: {
        			customerServiceWechatQrcodeFile: [], // 主图					
					purchaseRewardPoints: '',
					registrationRewardPoints: '',			
					// newMemberCouponsId: '',
					merchantWithdrawFee: '',								
					startDeliveryPrice: '',		
					deliveryStartingDistance: '',		
					deliveryStartingPrice: '',		
					deliveryKilometerPrice: '',		
					deliveryDistanceLimit: '',		
				}
			}
		},
		methods: {
			resetImg(row, imgType) {
				let data = row
				console.log(row)
				if(row[imgType]) {
					let arr = row[imgType].split(',');
					let newArr = []
					let index = 0;
					console.log(arr)
					arr.forEach(ele => {
					let obj = {
						url: this.$http.publicUrl(ele),
						// oldUrl: ele,
						upUrl: ele,
						name: index +1
					}
					console.log(obj)
					newArr.push(obj)
					index++
					});
					return newArr
				} else {
					return []
				}
			},			
			getIdByArr(data) {
				let arr = data || []
				let url = arr.map((item) => {
					return item.upUrl
				});
				return url.join(',')
			},			
			handleRemoveCustomerServiceWechatQrcode(file, filelist) {
				let arr = this.editForm.customerServiceWechatQrcodeFile
				this.editForm.customerServiceWechatQrcodeFile = arr.filter(function(item) {
					return item.uid !== file.uid
				});
			},
			beforeAvatarUpload(file) {
				const isLt2M = file.size / 1024 / 1024 < 6;
				if (!isLt2M) {
					this.$message({
						showClose: true,
						message: '上传图片大小不能超过 6MB!',
						type: 'error'
					});
					}
					return isLt2M;
				},
				upload(option){ // 图片上传
				console.log(option)
				let vue = this;
				let value = option.file;
				let formData = new FormData();
				formData.append('file', value);    

				vue.$http.postupload(vue, '/rest/admin/uploadSingleImage', formData,
					function (vue, data) {
						option.onSuccess();
						let obj = {
							url: vue.$http.publicUrl(data.data),
							uid: option.file.uid,
							upUrl: data.data
						}
						if (option.filename === 'subImagesUpload') {
							vue.editForm.subImagesFile.push(obj)
						} else if(option.filename === 'detailImagesUpload') {
							vue.editForm.detailImagesFile.push(obj)
						} else {
							vue.editForm.customerServiceWechatQrcodeFile.push(obj)
						}
						},
					function (error) {
						option.onError();
				})
			},			
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
			addPointsUnit(row, column) { // 添加单位
				return (row[column.property] || 0) + '个'
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
				} // 获取商品基础数据设置列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				vue.listLoading = true;

				vue.$http.post(vue, '/rest/admin/setting/list', param,
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
				
					vue.$http.delete(vue, '/rest/admin/setting/delete', {"id" : id},
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
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
				if(row){
					this.editForm = {
						id: row.id,
						purchaseRewardPoints: row.purchaseRewardPoints,
						registrationRewardPoints: row.registrationRewardPoints,
						merchantWithdrawFee: row.merchantWithdrawFee,						
						startDeliveryPrice: row.startDeliveryPrice,		
						deliveryStartingDistance: row.deliveryStartingDistance,		
						deliveryStartingPrice: row.deliveryStartingPrice,		
						deliveryKilometerPrice: row.deliveryKilometerPrice,		
						deliveryDistanceLimit: row.deliveryDistanceLimit,		
					}
				}else{
					this.editForm = {
						purchaseRewardPoints: '',
						registrationRewardPoints: '',		
						merchantWithdrawFee: '',	
						startDeliveryPrice: '',		
						deliveryStartingDistance: '',		
						deliveryStartingPrice: '',		
						deliveryKilometerPrice: '',		
						deliveryDistanceLimit: '',																		
					}
				}		
			},
			getDetail() { // 获取商品详情
				let vue = this
				vue.$http.post(vue, '/rest/admin/setting/selectCurrent', {},
					(vue, data) => {
					let obj = data.data
					obj.customerServiceWechatQrcodeFile = vue.resetImg(obj, 'customerServiceWechatQrcode')
					vue.editForm = Object.assign({}, obj)
					},(error, data)=> {
					vue.$message({
						showClose: true,
						message: data.message,
						type: 'error'
					});
					}
				)
			},			
			editSubmit: function () { // 编辑
				this.$refs.editForm.validate((valid) => {
					if (valid) {
						// this.editLoading = true;
						let vue = this
						let param = Object.assign({}, this.editForm);
						delete param.createTime;
						delete param.updateTime;

						if(!vue.editForm.customerServiceWechatQrcodeFile.length) {
							vue.$message({
							showClose: true,
							message: '请上传客服微信二维码',
							type: 'error'
							});
							return false
						}else if(vue.editForm.customerServiceWechatQrcodeFile.length > 1) {
							vue.$message({
							showClose: true,
							message: '只能上传一张客服微信二维码',
							type: 'error'
							});
							return false
						}
						
						param.customerServiceWechatQrcode = vue.getIdByArr(param.customerServiceWechatQrcodeFile)
						delete param.customerServiceWechatQrcodeFile

						let url = '';
						
						if(param.id){
							url = '/rest/admin/setting/update';
							vue.$http.put(vue, url, param,
								(vue, data) => {
									// this.editLoading = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
								}, (error, data) => {
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)							
						}else{
							url = '/rest/admin/setting/insert';
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
			}
		},
		mounted() {
			// this.getList();
			this.getDetail();
            //开启订单自动打印定时器
            this.$orderPrint.init();			
		}
	}

</script>

<style scoped>
  .input-width {
    width: 50%;
  }
  .note-margin {
    margin-left: 15px;
  }
</style>