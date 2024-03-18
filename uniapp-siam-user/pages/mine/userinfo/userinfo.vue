<template>
	<view>
		<view>
			<view class="userinfo-item">
				<text class="title-text">头像</text>
				<!-- <image class="userinfo-avatar" src="{{userInfo.avatarUrl}}" mode="cover"></image> -->
				<image class="userinfo-avatar"
					:src="data.headImg ? data.headImg : '/static/assets/images/user-head.png'" mode="cover"></image>
			</view>
			<view class="userinfo-item">
				<text class="title-text">用户名</text>
				<text class="userinfo-text">{{ data.username }}</text>
			</view>
			<view class="userinfo-item">
				<text class="title-text">绑定手机</text>
				<text class="userinfo-text">{{ data.mobile }}</text>
			</view>
			<view class="userinfo-item" @tap="openRealName">
				<text class="title-text">真实姓名</text>
				<view class="input-image">
					<text
						:class="data.realName ? 'userinfo-text' : 'placeholder-text'">{{ data.realName ? data.realName : '请输入真实姓名' }}</text>
					<text class="iconfont iconhtbArrowright02"></text>
				</view>
			</view>
			<view class="userinfo-item" @tap="openConfirm">
				<text class="title-text">性别</text>
				<view class="input-image">
					<text class="userinfo-text">{{ data.sex == 1 ? '男' : data.sex == 2 ? '女' : '保密' }}</text>
					<text class="iconfont iconhtbArrowright02"></text>
				</view>
			</view>
			<view class="userinfo-item" @tap="openEmail">
				<text class="title-text">邮箱</text>

				<view class="input-image">
					<!-- <input class="userinfo-text input-email" placeholder="请输入邮箱地址" value="{{data.email}}" bindblur="blurEmail"></input> -->
					<!-- <input placeholder-class="placeholder-email" class="userinfo-text" placeholder="请输入邮箱地址" value="{{data.email}}" disabled="disabled"></input> -->
					<text
						:class="data.email ? 'userinfo-text' : 'placeholder-text'">{{ data.email ? data.email : '请输入邮箱地址' }}</text>
					<text class="iconfont iconhtbArrowright02"></text>
				</view>
			</view>

			<view class="userinfo-item" @tap="parseEventDynamicCode($event, data.isBindWx ? 'bindWx' : '')"
				:data-mobile="data.mobile" :data-isBindWx="data.isBindWx">
				<text class="title-text">绑定微信</text>
				<view class="bind-wx">
					<text class="userinfo-text">{{ data.isBindWx ? '已绑定' : '未绑定' }}</text>
					<text class="iconfont iconhtbArrowright02"></text>
				</view>
			</view>
		</view>
		<van-action-sheet :show="dialogShow" @close="close" title="选择性别">
			<view class="content_box">
				<scroll-view scroll-y style="height: 52vh">
					<radio-group @change="radioChange" class="sex-radio-group">
						<label :data-radioIndex="index" class="radio-group-label" v-for="(item, index) in modeList"
							:key="index">
							{{ item.name }}
					
							<radio :value="item.value" class="radio-group-label-radio" :checked="item.checked" />
						</label>
					</radio-group>
					
				</scroll-view>
				<view class="flex_between">
					<view class="close-botton good-choice-btn" @tap="close">取消
					</view>
					<view class="good-choice-btn theme-bg" @tap="tapdialogButton">确定修改
					</view>
				</view>
			</view>
		</van-action-sheet>
		<van-action-sheet :show="dialogShowEmail" @close="close" title="编辑邮箱">
			<view class="content_box">
				<scroll-view scroll-y style="height: 52vh">
					<view class="content_box">
						<input class="userinfo-text input-email" placeholder="请输入邮箱地址" :value="data.email"
							@input="bindInputEmail" />
					</view>
				</scroll-view>
				
				<view class="flex_between">
					<view class="close-botton good-choice-btn" @tap="close">取消
					</view>
					<view class="good-choice-btn theme-bg" @tap="tapdialogEmailButton">确定修改
					</view>
				</view>
			</view>
		</van-action-sheet>
		<van-action-sheet :show="dialogShowRealName" @close="close" title="确认真实姓名">
			<view class="content_box">
				<scroll-view scroll-y style="height: 52vh">
					<view class="title-tip">请于身份证上保持一致，信息有误影响奖励提现</view>
					<input class="userinfo-text input-email" placeholder="请输入真实姓名" :value="data.realName"
						@input="bindInputRealName" />
				</scroll-view>
				
				<view class="flex_between">
					<view class="close-botton good-choice-btn" @tap="close">取消
					</view>
					<view class="theme-bg good-choice-btn" @tap="tapdialogRealNameButton">确定修改
					</view>
				</view>
			</view>
		</van-action-sheet>
		<view class="out-login-btn theme-bg" hover-class="hover-class-public" @tap="bindOutLogTap">退出登录</view>
	</view>
</template>

<script>
	import GlobalConfig from '../../../utils/global-config';
	import https from '../../../utils/http';
	import authService from '../../../utils/auth';
	var toastService = require('../../../utils/toast.service');
	var utils = require('../../../utils/util');
	var dateHelper = require('../../../utils/date-helper');
	//获取应用实例
	const app = getApp();
	export default {
		data() {
			return {
				buttons: [{
						text: '取消'
					},
					{
						text: '确定'
					}
				],

				userInfo: '',
				hasUserInfo: false,

				data: {
					isBindWx: false,
					sex: '',
					email: '',
					realName: '',
					headImg: false,
					username: '',
					mobile: ''
				},

				dialogShowEmail: false,
				email: '',
				dialogShowRealName: false,
				realName: '',
				dialogShow: false,
				maskClosable: '',
				modeList: [],
				sex: ''
			};
		},
		/**
		 * 生命周期函数--监听页面加载
		 */
		onLoad: function(options) {
			if (app.globalData.userInfo) {
				this.setData({
					userInfo: app.globalData.userInfo,
					hasUserInfo: true
				});
			} else if (this.canIUse) {
				// 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
				// 所以此处加入 callback 以防止这种情况
				app.globalData.userInfoReadyCallback = (res) => {
					this.setData({
						userInfo: res.userInfo,
						hasUserInfo: true
					});
				};
			} else {
				// 在没有 open-type=getUserInfo 版本的兼容处理
				// wx.getUserInfo({
				//   success: res => {
				//     app.globalData.userInfo = res.userInfo
				//     this.setData({
				//       userInfo: res.userInfo,
				//       hasUserInfo: true
				//     })
				//   }
				// })
			}
		},
		/**
		 * 生命周期函数--监听页面初次渲染完成
		 */
		onReady: function() {},
		/**
		 * 生命周期函数--监听页面显示
		 */
		onShow: function() {
			this.getUserInfo();
		},
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
			getUserInfo() {
				https.request('/rest/member/getLoginMemberInfo', {}).then((result) => {
					if (result.success) {
						result.data.email = result.data.email ? result.data.email : '';
						result.data.realName = result.data.realName ? result.data.realName : '';
						this.setData({
							data: result.data
						});
					}
				});
			},

			bindWx(e) {
				var that = this;
				toastService.showModal(null, '确定解除与微信账号的关联关系吗？', function confirm() {
					https
						.request('/rest/member/removeBindingWx', {
							mobile: e.currentTarget.dataset.mobile,
							isbindwx: e.currentTarget.dataset.isbindwx
						})
						.then((result) => {
							if (result.success) {
								toastService.showToast(result.message);
								that.setData({
									'data.isBindWx': false
								});
							}
						});
				});
			},

			openEmail() {
				this.setData({
					dialogShowEmail: true,
					email: this.data.email
				});
			},

			openRealName() {
				this.setData({
					dialogShowRealName: true,
					realName: this.data.email
				});
			},

			openConfirm() {
				this.setData({
					dialogShow: true,
					maskClosable: false,
					modeList: [{
							name: '男',
							value: 1,
							checked: this.data.sex == 1 ? true : false
						},
						{
							name: '女',
							value: 2,
							checked: this.data.sex == 2 ? true : false
						}
					]
				});
			},

			radioChange(e) {
				this.setData({
					sex: e.detail.value
				});
			},

			bindOutLogTap() {
				toastService.showModal(null, '确定退出登录吗？', function confirm() {
					https.request('/rest/member/logout', {}).then((result) => {
						if (result.success) {
							authService.deleteToken();
							authService.deleteOpenId();
							uni.navigateBack(1);
						}
					});
				});
			},

			tapdialogButton: function(e) {
				https.request('/rest/member/update', {
					sex: this.sex
				}).then((result) => {
					if (result.success) {
						toastService.showToast(result.message);
						this.setData({
							'data.sex': this.sex,
							dialogShow: false
						});
					}
				});
			},

			tapdialogEmailButton(e) {
				let email = this.email;
				if (email == this.data.email) {
					toastService.showToast('请输入不同的邮箱号码');
					return;
				}
				let isEmail = utils.verifyEmail(email);
				if (!isEmail) {
					toastService.showToast('邮箱号码格式错误');
					return;
				}
				https.request('/rest/member/update', {
					email: this.email
				}).then((result) => {
					if (result.success) {
						toastService.showToast(result.message);
						this.setData({
							'data.email': email,
							dialogShowEmail: false
						});
					}
				});
			},

			tapdialogRealNameButton(e) {
				let realName = this.realName;
				if (!realName) {
					toastService.showToast('请输入真实姓名');
					return;
				}
				https.request('/rest/member/update', {
					realName: realName
				}).then((result) => {
					if (result.success) {
						toastService.showToast(result.message);
						this.setData({
							'data.realName': realName,
							dialogShowRealName: false
						});
					}
				});

			},

			bindInputEmail(e) {
				this.setData({
					email: e.detail.value
				});
			},

			bindInputRealName(e) {
				this.setData({
					realName: e.detail.value
				});
			},

			trueFun() {
				console.log('占位：函数 true 未声明');
			},
			close() {
				this.setData({
					dialogShow: false,
					dialogShowRealName: false,
					dialogShowEmail: false
				})
			}
		}
	};
</script>
<style>
	.userinfo-item {
		line-height: 68rpx;
		display: flex;
		align-items: center;
		justify-content: space-between;
		padding: 20rpx 30rpx;
		border-bottom: 0.5rpx solid #f5f5f5;
	}

	.userinfo-avatar {
		width: 108rpx;
		height: 108rpx;
		border-radius: 50%;
		box-shadow: -2px 0px 5px 0.5px rgba(0, 0, 0, 0), 0px -2px 5px 1px rgba(0, 0, 0, 0.1), 2px 0px 5px 1px rgba(0, 0, 0, 0), 0px 2px 5px 1px rgba(0, 0, 0, 0.1);
	}

	.title-text {
		font-size: 28rpx;
		color: #8e8e8e;
	}

	.userinfo-text {
		font-size: 28rpx;
		text-align: end;
		font-weight: bold;
	}

	.input-email {
		width: 100%;
		text-align: center;
		border: 1rpx solid #8e8e8e;
		padding: 10rpx 0;
		border-radius: 15rpx;
		margin: 20rpx 0;
		color: black;
	}

	.input-image {
		display: flex;
		align-items: center;
		width: 80%;
		justify-content: flex-end;
	}

	.out-login-btn {
		margin: 50rpx;
		text-align: center;
		line-height: 88rpx;
		color: white;
		border-radius: 10rpx;
		font-size: 30rpx;
	}

	.bind-wx {
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}

	.radio-group-label {
		/* width: 100%; */
		display: flex;
		align-items: center;
		justify-content: space-between;
		height: 88rpx;
		padding: 0 20rpx;
		font-weight: bold;
	}

	.placeholder-email {
		text-align: end;
	}

	.placeholder-text {
		text-align: end;
		color: #808090;
		font-size: 30rpx;
	}

	.title-tip {
		font-size: 22rpx;
		color: red;
	}
	.close-botton{
		background-color: #ededed;
	}
	.good-choice-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
		
		margin: 20rpx;
	}
	
</style>