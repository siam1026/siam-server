<template>
	<div id="app">
		<transition name="fade"
		            mode="out-in">
			<router-view></router-view>
		</transition>
	</div>
</template>

<script>
export default {
	name: 'app',
	components: {
	}
}

</script>

<style lang="scss">
body {
	margin: 0px;
	padding: 0px;
	/*background: url(assets/bg1.jpg) center !important;
		background-size: cover;*/
	// background: #1F2D3D;
	font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, SimSun, sans-serif;
	font-size: 14px;
	-webkit-font-smoothing: antialiased;
}

#app {
	position: absolute;
	top: 0px;
	bottom: 0px;
	width: 100%;
}

.el-submenu [class^=fa] {
	vertical-align: baseline;
	margin-right: 10px;
}

.el-menu-item [class^=fa] {
	vertical-align: baseline;
	margin-right: 10px;
}
// .content-wrapper .el-input {
//   width: 300px;
// }
.toolbar {
	// background: #f2f2f2;
	padding: 10px;
	//border:1px solid #dfe6ec;
	margin: 10px 0px;
	.el-form-item {
		margin-bottom: 10px;
	}
}

.fade-enter-active,
.fade-leave-active {
	transition: all .2s ease;
}

.fade-enter,
.fade-leave-active {
	opacity: 0;
}

// 自定义样式
// 左侧菜单栏
.el-menu {
    background-color: #090723
}
.el-menu-item {
	color: #fff;
}
.el-menu-item:hover {
	color: #fff;
	background-color: #5facb4;
}
.el-menu-item.is-active {
    color: #fff;
	background-color: #5facb4;
	// background-color: cadetblue;
    // background-color: #16162f
}
.el-submenu__title {
	color: #fff;
	// font-weight: bold;
}
.el-submenu__title:hover {
	color: #fff;
	background-color: #16162f
}
// 右侧区域
.container .main .content-container[data-v-5a90ec03] {
    // background: aliceblue;
	// background: #eceff1;
	// background: #f9f7ff;
	background: white;
}
.container .main .content-container .content-wrapper[data-v-5a90ec03] {
    background-color: unset!important;
}
.container .header .userinfo .userinfo-inner[data-v-5a90ec03] {
    color: black!important;
}
// 图标
.el-icon-user:before, .el-icon-milk-tea:before, .el-icon-s-order:before,
.el-icon-house:before, .el-icon-picture:before, .el-icon-monitor:before,
.el-icon-setting:before, .el-icon-picture-outline:before {
    color: #fff;
}
// 按钮
.el-button--primary {
    color: #FFF;
    // background-color: #409EFF;
    // border-color: #409EFF;
	background-color: #5facb4;
    border-color: #5facb4;
}
.el-button--primary:focus, .el-button--primary:hover {
    // background: #66b1ff;
    // border-color: #66b1ff;
    color: #FFF;
	background: #5ca2aa;
    border-color: #5ca2aa;
}
// 网站标题
.container .header .logo-width[data-v-5a90ec03] {
    background: #22253b;
    color: #fff;
	font-weight: bold;
	width: 229px!important;
}
.container .header .logo-collapse-width[data-v-5a90ec03] {
    background: #22253b;
}
// 用户头像
.container .header .userinfo .userinfo-inner img[data-v-5a90ec03] {
	margin-top: 5px!important;
}
/* 将轮播图列表中的轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
.advertisementForm .el-upload-list--picture-card .el-upload-list__item {
	width: 448px;
	height: 248px;
}
// 堂食订单中的第一个、第二个标签页字体改成红色
.forHereOrderTabs #tab-zeroth, .forHereOrderTabs #tab-first {
	color: red;
}
// 外卖订单中的第一个、第二个标签页字体改成红色
.takeOutOrderTabs #tab-first, .takeOutOrderTabs #tab-second {
	color: red;
}
//修改添加/编辑商品页面的个别标签宽度
.mainImageFileItem .el-form-item__label {
	width: 300px!important;
}
.subImagesFileItem .el-form-item__label {
	width: 400px!important;
}
.detailImagesFileItem .el-form-item__label {
	width: 400px!important;
}
//修改门店信息页面的个别标签宽度
.shopLogoImgFileItem .el-form-item__label {
	width: 300px!important;
}
.shopWithinImgFileItem .el-form-item__label {
	width: 300px!important;
}
/* 将添加/编辑商品页面中的轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
// .mainImageFileItem .el-upload-list--picture-card .el-upload-list__item {
// 	width: 160px;
// 	height: 160px;
// }
/* 将添加/编辑商品页面中轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
// .subImagesFileItem .el-upload-list--picture-card .el-upload-list__item {
// 	width: 400px;
// 	height: 237px;
// }
/* 将添加/编辑商品页面中的轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
// .detailImagesFileItem .el-upload-list--picture-card .el-upload-list__item {
// 	width: 400px;
// 	height: 237px;
// }
/* 将门店信息页面中的轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
.shopLogoImgFileItem .el-upload-list--picture-card .el-upload-list__item {
	width: 160px;
	height: 160px;
}
/* 将门店信息页面中轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
.shopWithinImgFileItem .el-upload-list--picture-card .el-upload-list__item {
	width: 160px;
	height: 160px;
}
/* 覆盖element-radio大型区块的样式(数据中心页面) */
// .el-radio-button:first-child .el-radio-button__inner {
//     border-left: unset!important;
// }
// .el-radio-button__inner {
//     white-space: unset!important;
//     background: unset!important;
//     border: unset!important;
// }
// .el-radio-button__orig-radio:checked+.el-radio-button__inner {
//     color: #FFF;
//     background-color: #409EFF!important;
//     border-color: #409EFF!important;
//     -webkit-box-shadow: -1px 0 0 0 #409EFF!important;
//     box-shadow: -1px 0 0 0 #409EFF!important;
// }
/* element switch开关文字在按钮内显示 */
.demo .el-switch__label {
	position: absolute;
	display: none;
	color: #fff;
}
//打开时文字位置设置
.demo .el-switch__label--right {
	z-index: 1;
	right: -3px;
}
//关闭时文字位置设置
.demo .el-switch__label--left {
	z-index: 1;
	left: 16px;
}
//显示文字
.demo .el-switch__label.is-active {
	display: block;
}
.demo.el-switch .el-switch__core, .el-switch .el-switch__label {
	width: 50px !important;
}
/* 将添加/编辑商品页面中轮播图宽高比例调大一点(放在自身页面没用，放在这才有用) */
.firstPosterFileItem .el-form-item__label {
	width: 400px!important;
}
.firstPosterFileItem .el-upload-list--picture-card .el-upload-list__item {
	width: 400px;
	height: 237px;
}

//文字提示
.text-tip {
	display: block;
	color: #8c939d;    
}

//隐藏默认上传组件的文字回写列表
ul.el-upload-list--text{
  display: none!important;
}
</style>