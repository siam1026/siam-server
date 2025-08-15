<template>
	<view>
		<form @submit="formSubmit" @reset="formReset">
			<view class="address-info-list">
				<view class="address-info-item">
					<text>联系人</text>
					<input placeholder="请填写您的姓名" :value="realname" name="realname" />
				</view>
				<view class="address-info-item" @change="sexRadioChange">
					<text>性别</text>
					<radio-group class="sex-radio-group" name="sex">
						<label class="sex-radio-group-label" v-for="(item, index) in sexRadio" :key="index">
							<radio :value="item.id" :checked="item.checked"></radio>

							{{ item.name }}
						</label>
					</radio-group>
				</view>
				<view class="address-info-item">
					<text>手机号</text>
					<input placeholder="请输入您的手机号" type="number" :value="phone" name="phone" @blur="bindPhoneBlur" />
				</view>
				<view class="address-info-item">
					<text>地址</text>
					<view class="section" @tap="getRegeoAddress">
						<input placeholder="选择收货地址" :disabled="true" name="street" :value="street" style="pointer-events:none;"/>
						<van-icon name="arrow" />
					</view>
				</view>
				<view class="address-info-item">
					<text>门牌号</text>
					<input placeholder="例：5号楼203室" :value="houseNumber" name="houseNumber" />
				</view>
			</view>
			<view>
				<checkbox-group name="isDefault" @change="isDefaultChange">
					<label class="setting-default">
						<checkbox :checked="checkbox" :value="isDefault" iconColor="#FFFFFF"></checkbox>
						<text class="default-words">设为默认地址</text>
					</label>
				</checkbox-group>
			</view>
			<view class="safe-area operation-view">
				<view class="delete-view" hover-class="hover-class-public" @tap="bingDelTap">删除</view>
				<button class="save-view theme-bg" hover-class="hover-class-public" form-type="submit"
					style="padding: 0; width: 50%">保存</button>
			</view>
		</form>
	</view>
</template>

<script>
	import GlobalConfig from '../../../utils/global-config';
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	import toastService from '../../../utils/toast.service';
	import utilHelper from '../../../utils/util';
	import stringService from '../../../utils/string-service';
	//获取应用实例
	let app = null;
	export default {
		data() {
			return {
				sexRadio: [{
						id: 0,
						name: '先生',
						checked: true
					},
					{
						id: 1,
						name: '女士',
						checked: false
					}
				],

				tagRadio: [{
						id: 0,
						name: '家',
						checked: true
					},
					{
						id: 1,
						name: '公司',
						checked: false
					},
					{
						id: 2,
						name: '学校',
						checked: false
					}
				],

				sexKey: 0,
				customItem: '全部',
				region: [],
				checkbox: '',
				tagKey: '',
				isDefault: '',
				addressList: '',
				realname: '',
				phone: '',
				street: '',
				houseNumber: ''
			};
		}
		/**
		 * 生命周期函数--监听页面加载
		 */
		,
		onLoad: function(options) {
			app = getApp();
			console.log(JSON.parse(options.detail))
			var data = JSON.parse(options.detail);
			var province = data.province ? data.province : '全部';
			var city = data.city ? data.city : '全部';
			var area = data.area ? data.area : '全部';
			for (let key in this.sexRadio) {
				this.sexRadio[key].checked = false;
			}
			this.sexRadio[data.sex].checked = true;
			this.data = data;
			this.region = [province, city, area];
			this.checkbox = data.isDefault == 0 ? false : true;
			this.isDefault = data.isDefault;
			this.realname = data.realname;
			this.phone = data.phone;
			this.street = data.street;
			this.houseNumber = data.houseNumber;
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {},
		/**
		 * 生命周期函数--监听页面隐藏
		 */
		onHide: function() {},
		/**
		 * 生命周期函数--监听页面卸载
		 */
		onUnload: function() {},
		/**
		 * 页面相关事件处理函数--监听用户下拉动作
		 */
		onPullDownRefresh: function() {},
		/**
		 * 页面上拉触底事件的处理函数
		 */
		onReachBottom: function() {},
		/**
		 * 用户点击右上角分享
		 */
		onShareAppMessage: function() {},
		methods: {
			bindPhoneBlur(e) {
				let phone = e.detail.value;
				let isMobile = utilHelper.verifyPhone(phone);
				if (!isMobile) {
					toastService.showToast('手机号不正确');
					return;
				}
			},

			sexRadioChange(e) {
				let checkedValue = e.detail.value;
				for (let key in this.sexRadio) {
					this.sexRadio[key].checked = false;
				}
				this.sexRadio[checkedValue].checked = true;
				this.setData({
					sexKey: checkedValue,
					sexRadio: this.sexRadio
				});
			},

			tagRadioChange(e) {
				let checkedValue = e.detail.value;
				for (let key in this.tagRadio) {
					this.tagRadio[key].checked = false;
				}
				this.tagRadio[checkedValue].checked = true;
				this.setData({
					tagKey: checkedValue,
					tagRadio: this.tagRadio
				});
			},

			isDefaultChange(e) {
				this.isDefault = this.isDefault ? 0 : 1;
				this.checkbox = this.checkbox ? false : true;
			},

			bindRegionChange: function(e) {
				//console.log('picker发送选择改变，携带值为', e.detail.value)
				this.setData({
					region: e.detail.value
				});
			},

			bingDelTap() {
				var _this = this;
				toastService.showModal(null, '确定删除这个地址吗？', function confirm() {
					https.request('/rest/member/deliveryAddress/delete', {
						id: _this.data.id
					}).then((result) => {
						if (result.success) {
							toastService.showSuccess(result.message, true, 1000);
							setTimeout(() => {
								var pages = getCurrentPages();
								var prevPage = pages[pages.length - 2]; //上一个页面
								//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
								var addressList = prevPage.$vm.addressList;
								addressList.splice(prevPage.$vm.refreshKey, 1);
								prevPage.$vm.addressList = addressList;
								uni.navigateBack(1);
							}, 1000);
						}
					});
				});
			},

			formSubmit(e) {
				var res = e.detail.value;
				if (stringService.isEmpty(res.realname)) {
					toastService.showToast('联系人姓名为必填');
					return;
				}
				if (stringService.isEmpty(res.phone)) {
					toastService.showToast('手机号为必填');
					return;
				}
				let isMobile = utilHelper.verifyPhone(res.phone);
				if (!isMobile) {
					toastService.showToast('手机号不正确');
					return;
				}
				if (stringService.isEmpty(res.houseNumber)) {
					toastService.showToast('门牌号为必填');
					return;
				}
				console.log(this);
				res.province = this.region[0];
				res.city = this.region[1];
				res.area = this.region[2];
				res.id = this.data.id;
				res.isDefault = this.isDefault;
				res.longitude = this.longitude;
				res.latitude = this.latitude;
				https.request('/rest/member/deliveryAddress/update', res).then((result) => {
					if (result.success) {
						toastService.showSuccess(result.message, true, 1000);
						setTimeout(() => {
							var pages = getCurrentPages();
							var prevPage = pages[pages.length - 2]; //上一个页面
							//直接调用上一个页面的 setData() 方法，把数据存到上一个页面中去
							prevPage.$vm.addressList = [];
							prevPage.$vm.getDeliveryAddressList();
							uni.navigateBack(1);
						}, 1000);
					}
				});
			},

			getRegeoAddress(e) {
				uni.navigateTo({
					url: '../../address/search/search'
				});
			},

			formReset() {
				console.log('占位：函数 formReset 未声明');
			}
		}
	};
</script>
<style>
	page {
		background: #f5f5f5;
	}

	input {
		font-size: 28rpx;
		width: 80%;
	}

	.address-info-list {
		margin-top: 20rpx;
		padding: 0 20rpx;
		background: white;
	}

	.address-info-item {
		display: flex;
		align-items: center;
		line-height: 100rpx;
		border-bottom: 0.5rpx solid #f5f5f5;
	}

	.address-info-item text {
		width: 20%;
		font-size: 26rpx;
	}

	radio .wx-radio-input {
		width: 35rpx;
		height: 35rpx;
		border-radius: 50%;
		border-color: #ededed;
	}

	.sex-radio-group {
		width: 50%;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.sex-radio-group-label {
		font-size: 30rpx;
		border-radius: 50rpx;
	}

	.tag-radio-group {
		width: 100%;
		display: flex;
		justify-content: flex-start;
		align-items: center;
		padding: 10rpx;
		border-radius: 50rpx;
	}

	.tag-radio-group-label {
		width: 15%;
		height: 40rpx;
		margin-right: 20rpx;
		color: #808080;
		border-radius: 30rpx;
		font-size: 26rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.tag-unchecked {
		background: #f5f5f5;
	}

	.tag-active {
		color: white;
	}

	.picker {
		font-size: 28rpx;
	}

	.operation-view {
		width: 100%;
		height: 100rpx;
		line-height: 100rpx;
		background: white;
		position: fixed;
		bottom: 0;
		font-size: 30rpx;
		display: flex;
	}

	.delete-view {
		width: 50%;
		text-align: center;
	}

	button::after {
		border: none;
	}

	.save-view {
		width: 50%;
		text-align: center;
		color: white;
		border-radius: 0;
		border: none;
		font-size: 30rpx;
		line-height: 100rpx;
	}

	.setting-default {
		height: 60rpx;
		line-height: 70rpx;
		margin-top: 20rpx;
		padding: 0 20rpx;
		font-size: 30rpx;
		display: flex;
		align-items: center;
	}

	checkbox {
		width: 40rpx;
		height: 40rpx;
		border-radius: 50%;
		border-color: #ededed;
		display: flex;
		align-items: center;
	}

	checkbox .wx-checkbox-input {
		width: 40rpx;
		height: 40rpx;
	}

	.default-words {
		margin-left: 26rpx;
	}

	.section {
		width: 80%;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.iconhtbArrowright02 {
		width: auto;
	}
</style>