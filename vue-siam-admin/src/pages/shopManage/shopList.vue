<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-input v-model="searchMsg.name" clearable placeholder="请输入门店名称"></el-input>
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
			</el-table-column> -->
			<el-table-column prop="shopLogoImg" label="门店头像">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.shopLogoImg)" width="50" height="50">
				</template>				
			</el-table-column>
			<!-- <el-table-column prop="id" label="门店编号"></el-table-column> -->
			<el-table-column prop="name" label="门店名称"></el-table-column>			
			<el-table-column prop="contactRealname" label="联系人姓名"></el-table-column>
			<el-table-column prop="contactPhone" label="联系电话"></el-table-column>
			<el-table-column prop="province" label="详细地址">
				<template slot-scope="scope">
					{{scope.row.province}} - {{scope.row.city}} - {{scope.row.area}} - {{scope.row.street}} - {{scope.row.houseNumber}}
				</template>
			</el-table-column>
			<el-table-column prop="managePrimary" label="营业类目"></el-table-column>

			<el-table-column prop="isOperating" label="店铺是否营业">
				<template slot-scope="scope">
					<span v-if="scope.row.isOperating == true" style="color:green;font-weight:bold;">正常营业</span>
					<span v-else-if="scope.row.isOperating == false" style="color:red;font-weight:bold;">暂不营业</span>
				</template>
			</el-table-column>					
			<el-table-column prop="startTime" label="店铺营业开始时间"></el-table-column>
			<el-table-column prop="endTime" label="店铺营业结束时间"></el-table-column>
			<el-table-column prop="status" label="状态" width="100">
				<template scope="scope">
					<span v-if="scope.row.status == 1" style="color:blue;font-weight:bold;">待上架</span>
					<span v-else-if="scope.row.status == 2" style="color:green;font-weight:bold;">已上架</span>
					<span v-else-if="scope.row.status == 3" style="color:grey;font-weight:bold;">已下架</span>
				</template>
			</el-table-column>
			<el-table-column prop="isConcernWxPublicPlatform" label="公众号关注情况">
				<template slot-scope="scope">
					<span v-if="scope.row.isConcernWxPublicPlatform == true" style="color:green;font-weight:bold;">已关注</span>
					<span v-else-if="scope.row.isConcernWxPublicPlatform == false" style="color:red;font-weight:bold;">未关注</span>
				</template>
			</el-table-column>				
			<el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column>
			<el-table-column prop="updateTime" label="修改时间" :formatter="formatTime"></el-table-column>
			<el-table-column label="操作" fixed="right" width="150">
				<template slot-scope="scope">
					<el-button v-if="scope.row.status == 1 || scope.row.status == 3" size="small" type="primary" @click="handleUpShelf(scope.row.id)">上架</el-button>					
					<el-button v-if="scope.row.status == 2" size="small" type="danger" @click="handleDownShelf(scope.row.id)">下架</el-button>					
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>					
					<el-button size="small" @click="handleDetail(scope.$index, scope.row)">详情</el-button>
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

		<!--编辑界面-->
		<el-dialog title="编辑" :visible.sync="editFormSecondVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editFormSecond" label-width="150px" style="width: 80%;" :rules="editFormSecondRules" ref="editFormSecond">
  				<el-form-item label="店铺起送价格" prop="startDeliveryPrice">
					<el-input v-model="editFormSecond.startDeliveryPrice"></el-input>
				</el-form-item>
				<el-form-item prop="memberMobile" label="绑定的小程序用户手机号(用于发送微信公众号消息、进行商家端余额提现)">
					<el-input type="text" v-model="editFormSecond.memberMobile"></el-input>
				</el-form-item>  				
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormSecondVisible = false">取消</el-button>
				<el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>

		<!--详情界面-->
		<el-dialog title="详情" :visible.sync="editFormVisible" @close="closeDialog" :close-on-click-modal="false">
			<el-form :model="editForm" label-width="150px" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="门店名称" prop="name">
					<el-input v-model="editForm.name"></el-input>
				</el-form-item>

				<!-- <el-form-item label="外卖电话" prop="takeOutPhone">
					<el-input v-model="editForm.takeOutPhone"></el-input>
				</el-form-item> -->
              
				<el-form-item prop="contactPhone" label="联系电话">
						<el-input type="text" v-model="editForm.contactPhone" auto-complete="off" placeholder="请填写联系电话"></el-input>
				</el-form-item>    

				<el-form-item prop="selectedOptions" label="所在城市">
					<el-cascader style="width:100%;" :options="options" separator="-" :props="props" clearable change-on-select placeholder="请选择所在城市"
						v-model="editForm.selectedOptions">
					</el-cascader>
				</el-form-item>

				<el-form-item prop="street" label="门店地址" >
					<el-input type="text" v-model="editForm.street" auto-complete="off" placeholder="请输入门店地址" ></el-input>
				</el-form-item>            
						
				<el-form-item label="门店公告" prop="announcement">
							<el-input v-model="editForm.announcement"></el-input>
						</el-form-item>

						<el-form-item label="门店简介" prop="briefIntroduction">
				<el-input type="textarea" :rows="6" v-model="editForm.briefIntroduction"></el-input>
						</el-form-item>       

				<el-form-item prop="managePrimary" label="营业类目">
						<el-input type="text" v-model="editForm.managePrimary" auto-complete="off" placeholder="请输入营业类目" ></el-input>
				</el-form-item>

				<el-form-item label="门店头像（宽400px * 高400px）" prop="shopLogoImgFile" class="shopLogoImgFileItem">
				<el-upload
					class="avatar-uploader" accept=".image, .png, .jpg"
					action="" multiple name="shopLogoImgUpload"
					:on-remove="handleRemoveShopLogoImg"
					:file-list="editForm.shopLogoImgFile"
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card">
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
						</el-form-item>

				<el-form-item label="店内照片（宽600px * 高355px）" prop="shopWithinImgFile" class="shopWithinImgFileItem">
				<el-upload
					ref="multipleUpload"
					:file-list="editForm.shopWithinImgFile"
					class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="shopWithinImgUpload"
					:on-remove="handleRemoveShopWithinImg"
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card" >
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
						</el-form-item>

				<el-form-item label="营业执照" prop="businessLicenseFile" class="businessLicenseFileItem">
				<el-upload
					ref="multipleUpload"
					:file-list="editForm.businessLicenseFile"
					class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="businessLicenseUpload"
					:on-remove="handleRemoveBusinessLicense"
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card" >
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
				</el-form-item>

				<el-form-item label="身份证正面" prop="idCardFrontSideFile" class="idCardFrontSideFileItem">
				<el-upload
					ref="multipleUpload"
					:file-list="editForm.idCardFrontSideFile"
					class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="idCardFrontSideUpload"
					:on-remove="handleRemoveIdCardFrontSide"
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card" >
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
				</el-form-item>

				<el-form-item label="身份证反面" prop="idCardBackSideFile" class="idCardBackSideFileItem">
				<el-upload
					ref="multipleUpload"
					:file-list="editForm.idCardBackSideFile"
					class="avatar-uploader" action="" accept=".image, .png, .jpg" multiple name="idCardBackSideUpload"
					:on-remove="handleRemoveIdCardBackSide"
					:before-upload="beforeAvatarUpload" :http-request="upload"
					list-type="picture-card" >
					<i class="el-icon-plus avatar-uploader-icon"></i>
				</el-upload>
				</el-form-item>              

				<el-form-item label="店铺是否营业" prop="isOperating">
					<el-radio-group v-model="editForm.isOperating" size="medium">
						<el-radio-button label="正常营业" value="true"></el-radio-button>
						<el-radio-button label="暂不营业" value="false"></el-radio-button>
					</el-radio-group>				
				</el-form-item>			

				<el-form-item label="店铺营业开始时间" prop="startTime">
					<el-input v-model="editForm.startTime"></el-input>
				</el-form-item>	

				<el-form-item label="店铺营业结束时间" prop="endTime">
					<el-input v-model="editForm.endTime"></el-input>
				</el-form-item>	 

        		<el-form-item label="配送费立减金额" prop="reducedDeliveryPrice">
					<el-input v-model="editForm.reducedDeliveryPrice"></el-input>
				</el-form-item>				
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="editFormVisible = false">取消</el-button>
				<!-- <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button> -->
			</div>
		</el-dialog>

	</section>
</template>

<script>
	import cityData from '../../../static/citys.json';
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

				options:cityData,
				props: {
					value: 'label',
					children: 'children'
				},

				editFormVisible: false,//编辑界面是否显示
				editLoading: false,
				editFormRules: {
					// isDisabled: [{ required: true, message: '请选择门店状态', trigger: 'change' }],
					// name: [{ required: true, message: '请输入门店名称', trigger: 'blur' }],
					// code: [{ required: false, message: '请输入门店编码', trigger: 'blur' }],
					// selectedOptions2:[{ type: 'array', required: true, message: '请选择区域', trigger: 'change' }],
					// street: [{ required: true, message: '请输入详细地址', trigger: 'blur' }],
					// isOperating: [{ required: true, message: '请选择店铺是否营业', trigger: 'blur' }],
					// startTime: [{ required: true, message: '请输入店铺营业开始时间', trigger: 'blur' }],
					// endTime: [{ required: true, message: '请输入店铺营业结束时间', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: {
					name:'',
					selectedOptions: [],
					street:'',
					managePrimary: '',
					shopImg: '',
					shopWithinImg:'',
					shopLogoImg:'',
					businessLicense:'',
					idCardFrontSide:'',
					idCardBackSide:'',          
					takeOutPhone:'',
					contactRealname:'',
					contactPhone:'',             
					shopLogoImgFile: [], // 主图
					shopWithinImgFile: [], // 介绍图          
					detailImagesFile: [], // 详情图
					businessLicenseFile: [], // 介绍图
					idCardFrontSideFile: [], // 介绍图
					idCardBackSideFile: [], // 介绍图                             
					isOperating: '正常营业',
					startTime: '8:00',
					endTime: '19:30',              
					announcement:'',          
					briefIntroduction:'',     
					reducedDeliveryPrice:'',
				},

				editFormSecondVisible: false,//编辑界面是否显示
				editLoadingSecond: false,
				editFormSecondRules: {
					startDeliveryPrice: [{ required: true, message: '请输入店铺起送价格', trigger: 'blur' }],
					memberMobile: [{ required: true, message: '请输入绑定的小程序用户手机号', trigger: 'blur' }],
				},
				//编辑界面数据
				editFormSecond: {
					startDeliveryPrice:'',
					memberMobile: '',
				}				
			}
		},
		methods: {
			getIdByArr(data) {
				let arr = data || []
				let url = arr.map((item) => {
				return item.oldUrl
				});
				return url.join(',')
			},      
			formmatAddress(row) { 
				let data = [...row]
				let [ province, city, area] = data
				this.searchMsg.province = province
				this.searchMsg.city = city
				// this.searchMsg.area = area
			},    			
			resetImg(row, imgType) {
				let data = row
				if(row[imgType]) {
				let arr = row[imgType].split(',');
				let newArr = []
				let index = 0
				arr.forEach(ele => {
					let obj = {
					url: this.$http.publicUrl(ele),
					oldUrl: ele,
					name: index +1
					}
					newArr.push(obj)
					index++
				});
				return newArr
				} else {
				return []
				}
			},		
			handleRemoveShopLogoImg(file, filelist) {
				let arr = this.editForm.shopLogoImgFile
				this.editForm.shopLogoImgFile = arr.filter(function(item) {
				return item.uid !== file.uid
				});
			},
			handleRemoveShopWithinImg(file, filelist) {
				let arr = this.editForm.shopWithinImgFile
				this.editForm.shopWithinImgFile = arr.filter(function(item) {
				return item.uid !== file.uid
				});
			},
			handleRemoveBusinessLicense(file, filelist) {
				let arr = this.editForm.businessLicenseFile
				this.editForm.businessLicenseFile = arr.filter(function(item) {
				return item.uid !== file.uid
				});
			},
			handleRemoveIdCardFrontSide(file, filelist) {
				let arr = this.editForm.idCardFrontSideFile
				this.editForm.idCardFrontSideFile = arr.filter(function(item) {
				return item.uid !== file.uid
				});
			},
			handleRemoveIdCardBackSide(file, filelist) {
				let arr = this.editForm.idCardBackSideFile
				this.editForm.idCardBackSideFile = arr.filter(function(item) {
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
				let vue = this;
				let value = option.file;
				let formData = new FormData();
				formData.append('file', value);
				vue.$http.postupload(
					vue, '/rest/admin/uploadSingleImage', formData,
					function (vue, data) {
					option.onSuccess();
					let obj = {
						oldUrl: data.data,
						url: vue.$http.publicUrl(data.data),
						uid: option.file.uid,
					}
					if (option.filename === 'shopWithinImgUpload') {
						vue.editForm.shopWithinImgFile.push(obj)
					} else if(option.filename === 'detailImagesUpload') {
						vue.editForm.detailImagesFile.push(obj)
					} else if(option.filename === 'businessLicenseUpload') {
						vue.editForm.businessLicenseFile.push(obj)
					} else if(option.filename === 'idCardFrontSideUpload') {
						vue.editForm.idCardFrontSideFile.push(obj)
					} else if(option.filename === 'idCardBackSideUpload') {
						vue.editForm.idCardBackSideFile.push(obj)
					} else if(option.filename === 'shopLogoImgUpload') {
						vue.editForm.shopLogoImgFile.push(obj)
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
				return row.isDisabled ? '禁用' : '正常';
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
				} // 获取商品门店列表
				let vue = this
				let param = Object.assign(vue.searchMsg);
				param.auditStatus = 2; //查询审核通过的记录

				//处理开始日期、结束日期
				if(vue.searchMsg.createTime){
					let startDate = vue.searchMsg.createTime[0];
					let endDate = vue.searchMsg.createTime[1];
					param.startCreateTime = this.$utils.formatDate(new Date(startDate), 'yyyy/MM/dd hh:mm:ss');
					param.endCreateTime = this.$utils.formatDate(new Date(endDate), 'yyyy/MM/dd hh:mm:ss');
					delete param.createTime;
				}

				vue.listLoading = true;

				//searchMsg搜索条件中没有省市区，这样写会报错的
				// let [province, city, area] = [...vue.searchMsg.selectedOptions]
				// param.province = province
                // param.city = city
                // param.area = area

				vue.$http.post(vue, '/rest/admin/shop/list', param,
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
				
					vue.$http.delete(vue, '/rest/admin/shop/delete', {"ids" : [id]},
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
			handleUpShelf (id) { // 删除
				this.$confirm('确认上架该店铺吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
					let vue = this;
				
					vue.$http.put(vue, '/rest/admin/shop/update', {"id" : id, "status" : 2},
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
			handleDownShelf (id) { // 删除
				this.$confirm('确认下架该店铺吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
					let vue = this;
				
					vue.$http.put(vue, '/rest/admin/shop/update', {"id" : id, "status" : 3},
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
			handleDetail: function (index, row) { // 显示编辑界面
				let vue = this
				this.editFormVisible = true;
				if(row){
					// this.editForm = Object.assign({}, row);
					// this.editForm.selectedOptions2 = [row.province, row.city, row.area];	
					// this.editForm.isOperating = row.isOperating==true ? '正常营业' : '暂不营业';	

					// obj.shopLogoImgFile = vue.resetImg(row, 'shopLogoImg')
					// obj.shopWithinImgFile = vue.resetImg(row, 'shopWithinImg')
					row.shopLogoImgFile = vue.resetImg(row, 'shopLogoImg')
					row.shopWithinImgFile = vue.resetImg(row, 'shopWithinImg')
					row.businessLicenseFile = vue.resetImg(row, 'businessLicense')      
					row.idCardFrontSideFile = vue.resetImg(row, 'idCardFrontSide')      
					row.idCardBackSideFile = vue.resetImg(row, 'idCardBackSide')    					
					this.editForm = Object.assign({}, row)
					this.editForm.selectedOptions = [row.province, row.city, row.area];	
					this.editForm.isOperating = row.isOperating==true ? '正常营业' : '暂不营业';	
				}else{
					this.editForm = {
						name: '',
						code: '',
						selectedOptions2: [],
						street: '',
						isOperating: '正常营业',
						startTime: '8:00',
						endTime: '19:30',			
					}
				}		    							
			},							
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormSecondVisible = true;
				if(row){
					this.editFormSecond = {
						id: row.id,
						startDeliveryPrice: row.startDeliveryPrice,
						memberMobile: row.memberMobile,
					}
				}else{
					this.editFormSecond = {
						startDeliveryPrice:'',
						memberMobile: '',
					}
				}						
			},
			editSubmit: function () { // 编辑
				this.$refs.editFormSecond.validate((valid) => {
					if (valid) {
						// this.editLoading = true;
						let vue = this
						let param = Object.assign({}, this.editFormSecond);
						let url = '/rest/admin/shop/update';
						vue.$http.put(vue, url, param,
							(vue, data) => {
								// this.editLoading = false;
								vue.$message({
									showClose: true,
									message: data.message,
									type: 'success'
								});
								vue.getList()
								vue.editFormSecondVisible = false;
							}, (error, data) => {
								vue.$message({
									showClose: true,
									message: data.message,
									type: 'error'
								});
							}
						)		
					}
				});
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