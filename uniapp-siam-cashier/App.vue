<script>
	import dateHelper from './utils/date-helper';
	import authService from './utils/auth';
	import toastService from './utils/toast.service';
	import {
		Base64
	} from 'js-base64';
	export default {
		onLaunch: function(options) {
			console.log('App Launch', options);
			// authService.getWxStorage(`${options.query.shopId}_shoppingCartList`).then((result) => {
			// 	if(!result){
			// 		authService.setWxStorage(`${options.query.shopId}_shoppingCartList`, []);
			// 	}
			// });
			if (!options.query.p) {
				toastService.showToast("请重新打开收银台");
			}
			if (options.query.p) {
				var query = Base64.decode(options.query.p);
				console.log("收银台跳转参数=============", query)
				var params = JSON.parse(query);
				console.log("收银台跳转参数=============", params)
				authService.setToken(params.token);
				this.globalData.shopId = params.shopId;
				authService.setWxStorage(`${params.shopId}_shoppingCartList`, []);
			}
		},
		onShow: function() {
			console.log('App Show');
		},
		onHide: function() {
			console.log('App Hide')
		},
		globalData: {
			shopId: '',
			getIsItNear(startTime, endTime) {
				let itNear = dateHelper.itNearTime(startTime, endTime);
				console.log('是否相差30分钟===》' + itNear);
				console.log('商家开门时间======》' + startTime);
				console.log('商家打烊时间====》' + endTime);
				if (itNear) {
					if (!this.deliveryAndSelfTaking.isItNear) {
						this.deliveryAndSelfTaking.isItNear = true;
						toastService.showModal(
							null,
							'店铺临近打烊或已打烊',
							function confirm() {
								return;
							},
							null,
							false
						);
					}
				}
			},
		}
	}
</script>

<style>
	/*每个页面公共css */
	/* 右侧箭头公共样式 */
	.right-white-class {
		width: 18rpx;
		height: auto;
		margin-left: 20rpx;
	}

	/* 分割线 */
	.view-line {
		border: 1rpx solid #f8f8f8;
	}

	.position-sticky {
		position: sticky;
		top: 0;
		z-index: 99;
		background: white;
	}

	.position-sticky-bottom {
		position: sticky;
		bottom: 0;
		z-index: 99;
		background: white;
	}


	/* 超出范围以...显示 */
	.out_of_range {
		overflow: hidden;
		text-overflow: 'string';
		display: -webkit-box;
		-webkit-box-orient: vertical;
		align-content: center;
		word-break: break-all;
	}

	/* 不显示"..."省略号，可以以符号表示 */
	.white_space {
		white-space: nowrap;
	}

	.one_row {
		-webkit-line-clamp: 1;
	}

	.two_row {
		-webkit-line-clamp: 2;
	}

	/* 公共图标样式 */
	.icon-class {
		width: 5%;
		height: auto;
		margin-right: 10rpx;
	}

	.textarea-placeholder {
		font-size: 28rpx;
	}

	.strike_through {
		color: gainsboro;
		text-decoration: line-through;
	}

	.input-placeholder {
		font-size: 28rpx;
	}

	/* hover-class样式，点击态;opacity:透明程度*/
	.hover-class-public {
		opacity: 0.7;
		background: #f5f5f5;
		transition: 0.5s;
	}

	/* 不能操作的item */
	.isDisable {
		opacity: 0.5;
		transition: 0.5s;
	}

	.isEnd {
		opacity: 0.4;
	}

	/* 售罄样式 */
	.sell-out {
		position: absolute;
		left: 36rpx;
		height: 100rpx;
		line-height: 100rpx;
		width: 100rpx;
		text-align: center;
		opacity: 0.7;
		border-radius: 50%;
		font-size: 30rpx;
		background: #434343;
		color: white;
	}

	.menu-position {
		position: relative;
		width: 100%;
		top: 0;
		background: white;
		z-index: 9999;
		color: #434343;
	}

	/* 活动tab的公共样式 */
	.swiper-tab-item {
		font-size: 28rpx;
		color: #434343;
		display: flex;
		justify-content: center;
	}

	.swiper_table_item_view {
		display: flex;
		align-items: center;
		justify-content: center;
		border-bottom: 4rpx solid #fff;
	}

	/* 主题颜色 */
	.active {
		color: #004ca0;
		border-bottom: 4rpx solid #004ca0;
		/* transition: 0.5s; */
	}

	.active_ {
		color: #004ca0;
		border-bottom: 4rpx solid white;
		/* transition: 0.5s; */
	}

	.group-active {
		color: white;
	}

	.theme-bg {
		background: #004ca0;
		color: white;
		border: none;
	}

	.theme-color {
		color: #004ca0;
	}

	.theme-border {
		border: 0.5rpx solid #004ca0;
	}

	/* 是字体颜色加边框的宽度和实心颜色 */
	.theme-color-border {
		color: #004ca0;
		border: 0.5rpx solid #004ca0;
	}

	/* 只是边框颜色 */
	.theme-border-color {
		border-color: #004ca0;
	}

	/* 选择地址radio公共样式 */
	.radio-active {
		background: #004ca0;
		background: white;
		color: #004ca0;
		transition: 0.5s;
	}

	/* 去除选项框的radio样式 */
	.none-radio {
		display: none;
	}

	/*checkbox 选项框大小  */
	radio-group .wx-radio-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	/*radio选中后样式  */
	radio-group .wx-radio-input.wx-radio-input-checked {
		border-color: 1rpx solid #004ca0;
		border-radius: 50%;
		background: #004ca0 !important;
	}

	/* checkBox复选框公共样式 */
	checkbox .wx-checkbox-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	/*checkbox选中后样式  */
	checkbox .wx-checkbox-input.wx-checkbox-input-checked {
		border-radius: 50%;
		color: #ffffff;
		border-color: 1rpx solid #004ca0;
		background-color: #004ca0;
	}

	checkbox .wx-checkbox-input.wx-checkbox-input-checked::before {
		border-radius: 50%;
		color: #ffffff;
		border-color: 1rpx solid #004ca0;
		background-color: #004ca0;
	}

	radio .wx-radio-input {
		border-radius: 50%;
		border-color: #ededed;
	}

	radio .wx-radio-input.wx-radio-input-checked {
		color: #ffffff;
		border-radius: 50%;
		border: 1rpx solid #004ca0 !important;
		background: #004ca0 !important;
	}

	radio .wx-radio-input.wx-radio-input-checked::before {
		color: #ffffff;
		border-radius: 50%;
		border: 1rpx solid #004ca0 !important;
		background: #004ca0 !important;
	}

	radio {
		width: 40rpx;
		height: 40rpx;
		margin-right: 15rpx;
	}

	/* 加减联动器 */
	.stepper {
		width: auto;
		height: auto;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.add-reduce-input {
		color: #004ca0;
	}

	.reduce-class {
		border: 1rpx solid #004ca0;
		color: #004ca0;
	}

	/*加号和减号*/
	.stepper text {
		text-align: center;
		border: 1rpx solid #004ca0;
		border-radius: 50%;
	}

	/* 加号样式 */
	.add-class {
		background: #004ca0;
		color: white;
		border: 1rpx solid #004ca0;
	}

	.car_reduce_add {
		border-radius: 50%;
		width: 35rpx;
		height: 35rpx;
		min-height: 35rpx;
		line-height: 35rpx;
	}

	.radd-reduce-input {
		width: 24rpx;
	}

	/* 减号样式 */
	.reduce-class {
		color: #004ca0;
	}

	/*数值*/
	.stepper input {
		width: 30px;
		float: left;
		text-align: center;
		font-size: 12px;
		z-index: 0;
	}

	.top-bg-class {
		width: 100%;
	}

	.isNotData {
		background: #b8b8b8;
		color: white;
		padding: 2rpx 10rpx;
		border-radius: 5rpx;
		font-size: 22rpx;
	}

	/*  */
	.weui-navigation-bar-custom {
		width: 100%;
		overflow: hidden;
		position: fixed;
		top: 0;
		left: 0;
		z-index: 10;
	}

	.display_flex_between {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	/* 支付密码css start */
	.password_dialog_tip {
		margin: 30rpx 0;
		font-size: 24rpx;
		color: #888;
	}

	/* 密码掩码模拟 */
	.password_dialog_row {
		margin: 0 auto;
		height: 98rpx;
		position: relative;
		border-radius: 15rpx;
		display: flex;
		align-items: center;
		border: 1rpx solid #e2e2e2;
	}

	.password_dialog_row.theme-border {
		border: 1rpx solid #004ca0;
	}

	.password_dialog_row .password_dialog_item_input {
		flex: 1;
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100%;
		border-right: 1rpx solid #e2e2e2;
		position: relative;
	}

	.password_dialog_item_input:nth-last-of-type(1) {
		border-right: 0;
	}

	.password_dialog_item_input text {
		width: 30rpx;
		height: 30rpx;
		border-radius: 30rpx;
		background-color: #555;
	}

	.password_dialog_forget_pwd {
		float: right;
		margin: 30rpx;
		width: 100rpx;
		text-align: right;
		font-size: 24rpx;
	}

	/* 文本输入框位置: 设置到左边隐藏 */
	.password_dialog_input_control {
		position: relative;
		left: -300rpx;
		bottom: 0;
		width: 100rpx;
		height: 100rpx;
	}

	.top_tips {
		width: 100%;
		background: #fffadc;
		font-size: 24rpx;
		text-align: center;
		position: sticky;
		top: 0;
		z-index: -1;
		opacity: 0.7;
		visibility: visible;
	}

	.flex_center {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.flex_between_normal {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.flex_start_center {
		display: flex;
		align-items: center;
		justify-content: flex-start;
	}

	.flex_start {
		display: flex;
		justify-content: flex-start;
	}

	.flex_between {
		display: flex;
		align-items: center;
		justify-content: space-between;
	}

	.flex_end {
		display: flex;
		align-items: center;
		justify-content: flex-end;
	}

	.flex_column_center {
		display: flex;
		align-items: center;
		flex-direction: column;
	}

	.flex_column {
		display: flex;
		flex-direction: column;
	}
	
	.halfScreenDialog-btn {
		width: 100%;
		text-align: center;
		padding: 20rpx 0;
		border-radius: 15rpx;
		font-size: 28rpx;
		font-weight: bold;
	}

	.list-ismore {
		text-align: center;
		margin: 20rpx 0;
		padding: 20rpx 0;
		color: #888;
		font-size: 28rpx;
	}

	.content_box {
		padding: 16px;
	}

	.content_box_footer {
		margin: 10px;
	}

	.safe-area {
		padding-bottom: calc(constant(safe-area-inset-bottom));
		padding-bottom: calc(env(safe-area-inset-bottom));
		background: #F9FAFC;
	}

	.loading_box {
		width: 100%;
		margin-top: 30%;
		text-align: center;
	}

	.loading_list_box {
		width: 100%;
		margin: 10rpx 0;
		text-align: center;
	}

	.loading_box_class {
		color: #004ca0;
	}

	.more_box {
		padding: 20px 0;
		background: #f6f6f6;
		font-size: 26rpx;
		color: #969696;
		text-align: center;
	}

	.divider_box {}

	/* #ifdef APP-PLUS||H5 */
	uni-checkbox .uni-checkbox-input {
		border-radius: 50% !important;
		color: #ffffff !important;
	}

	uni-checkbox .uni-checkbox-input.uni-checkbox-input-checked {
		border: 2rpx solid #004ca0 !important;
		background: #004ca0;
		border-color: #004ca0;
	}

	uni-checkbox .uni-checkbox-input svg {
		background: #004ca0;
		border-radius: 50%;
		height: 100%;
		border: 5px solid #004ca0;
		color: #ffffff !important;
	}

	uni-checkbox .uni-checkbox-input.uni-checkbox-input-checked::before {
		width: 40rpx;
		height: 40rpx;
		line-height: 40rpx;
		text-align: center;
		font-size: 18rpx;
		color: #fff;
		background: transparent;
		transform: translate(-70%, -50%) scale(1);
		-webkit-transform: translate(-70%, -50%) scale(1);
	}

	/* vant搜索框不兼容H5和app问题 */
	.van-field__control[type=search]::-webkit-search-cancel-button {
		-webkit-appearance: none !important;
	}

	/* 加载提示问题 */
	.uni-toast {
		height: 8em;
		max-height: 8em;
	}

	.uni-toast__content {
		margin-top: 5px;
	}

	.uni-toast__icon {
		margin-top: 30px;
	}

	/* #endif */
	// #ifdef MP-WEIXIN||MP-ALIPAY
	/*checkbox 选项框大小  */
	checkbox .wx-checkbox-input {
		width: 40rpx;
		height: 40rpx;
	}

	/*checkbox选中后样式  */
	checkbox .wx-checkbox-input.wx-checkbox-input-checked {
		width: 40rpx;
		height: 40rpx;
	}

	checkbox {
		width: 40rpx;
		height: 40rpx;
		margin-right: 15rpx;
	}


	// #endif
</style>