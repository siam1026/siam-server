<template>
	<el-row class="container">
		<el-col :span="24" class="header">
			<el-col :span="10" class="logo" :class="collapsed?'logo-collapse-width':'logo-width'">
				{{collapsed?'':sysName}}
			</el-col>
			<el-col :span="10">
				<div class="tools" @click.prevent="collapse">
					<i class="el-icon-menu"></i>
				</div>
			</el-col>
			<el-col :span="4" class="userinfo">
				<el-dropdown trigger="hover">
					<span class="el-dropdown-link userinfo-inner">
            			<img :src="this.sysUserAvatar" /> 
            			{{sysUserName}}
					</span>
					<el-dropdown-menu slot="dropdown">
						<!-- <el-dropdown-item>我的消息</el-dropdown-item>
						-->
						<el-dropdown-item @click.native="showDialog">修改密码</el-dropdown-item> 
						<el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
					</el-dropdown-menu>
				</el-dropdown>
			</el-col>
		</el-col>
		<el-col :span="24" class="main">
			<aside :class="collapsed?'menu-collapsed':'menu-expanded'">
				<!--导航菜单-->
				<!-- <el-menu :default-active="$route.path" @open="handleopen" @close="handleclose" @select="handleselect"
					 unique-opened router v-if="!collapsed">
					<template v-for="(item,index) in routerArr"  v-if="!item.hidden">
						<el-submenu :key="index" :index="index+''" v-if="!item.leaf">
							<template slot="title"><i :class="item.iconCls"></i>{{item.name}}第一层</template>
							<el-menu-item v-for="child in item.children" :index="child.path" :key="child.path" v-if="!child.hidden">{{child.name}}第二层</el-menu-item>
						</el-submenu>
						<el-menu-item :key="index" v-if="item.leaf&&item.children.length>0" :index="item.children[0].path"><i :class="item.iconCls"></i>{{item.children[0].name}}第一层叶子节点</el-menu-item>
					</template>
				</el-menu> -->

				<el-menu :default-active="$route.path" @open="handleopen" @close="handleclose" @select="handleselect"
					 unique-opened router v-if="!collapsed">
					<template v-for="(item,index) in routerArr"  v-if="!item.hidden">
						<el-submenu :key="index" :index="index+''" v-if="!item.leaf">
							<template slot="title"><i :class="item.iconCls"></i>{{item.name}}</template>
							
							<!-- <el-menu-item v-for="childSecond in item.children" :index="childSecond.path" :key="childSecond.path" v-if="!childSecond.hidden">
								{{childSecond.name}}第二层
							</el-menu-item> -->
						
							<template v-for="(childSecond,index) in item.children"  v-if="!childSecond.hidden">
								<el-submenu :key="index" :index="index+''" v-if="!childSecond.leaf">
									<template slot="title">{{childSecond.name}}</template>
									<el-menu-item v-for="childThird in childSecond.children" :index="childThird.path" :key="childThird.path" v-if="!childThird.hidden">{{childThird.name}}</el-menu-item>
								</el-submenu>
								<el-menu-item :key="index" v-if="childSecond.leaf&&childSecond.children.length>0" :index="childSecond.children[0].path">{{childSecond.children[0].name}}</el-menu-item>
							</template>
						</el-submenu>
						<el-menu-item :key="index" v-if="item.leaf&&item.children.length>0" :index="item.children[0].path"><i :class="item.iconCls"></i>{{item.children[0].name}}</el-menu-item>
					</template>
				</el-menu>

				<!--导航菜单-折叠后-->
				<ul class="el-menu collapsed" v-else ref="menuCollapsed">
					<li v-for="(item,index) in routerArr" :key="index" v-if="!item.hidden" class="el-submenu item">
						<template v-if="!item.leaf">
							<div class="el-submenu__title" style="padding-left: 20px;" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"><i :class="item.iconCls"></i></div>
							<ul class="el-menu submenu" :class="'submenu-hook-'+index" @mouseover="showMenu(index,true)" @mouseout="showMenu(index,false)"> 
								<li v-for="child in item.children" v-if="!child.hidden" :key="child.path" class="el-menu-item" style="padding-left: 40px;" :class="$route.path==child.path?'is-active':''" @click="$router.push(child.path)">{{child.name}}</li>
							</ul>
						</template>
						<template v-else>
							<li class="el-submenu">
								<div class="el-submenu__title el-menu-item" style="padding-left: 20px;height: 56px;line-height: 56px;padding: 0 20px;" :class="$route.path==item.children[0].path?'is-active':''" @click="$router.push(item.children[0].path)"><i :class="item.iconCls"></i></div>
							</li>
						</template>
					</li>
				</ul>
			</aside>
			<section class="content-container">
				<div class="grid-content bg-purple-light">
					<el-col :span="24" class="breadcrumb-container">
						<strong class="title">{{$route.name}}</strong>
						<el-breadcrumb separator="/" class="breadcrumb-inner">
							<el-breadcrumb-item v-for="item in $route.matched" :key="item.path">
								{{ item.name }}
							</el-breadcrumb-item>
						</el-breadcrumb>
					</el-col>
					<el-col :span="24" class="content-wrapper">
						<transition name="fade" mode="out-in">
							<router-view></router-view>
						</transition>
					</el-col>
				</div>
			</section>
		</el-col>
		<el-dialog title="修改密码" class="changePassword" :center="true" :visible.sync="changePasswordForm" @close="closeDialog('passwordForm')" :close-on-click-modal="false">
			<el-form :model="passwordForm" class="changePassform" label-width="150px" :rules="editFormRules" ref="passwordForm">
				<el-form-item label="旧密码：" prop="oldPassword">
					<el-input type="password" v-model="passwordForm.oldPassword" autocomplete="off" placeholder="密码只包含数字、字母组成。"></el-input>
				</el-form-item>
				<el-form-item label="新密码：" prop="newPassword">
					<el-input type="password" v-model="passwordForm.newPassword" autocomplete="off" placeholder="密码只包含数字、字母组成。"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="changePasswordForm = false">取消</el-button>
				<el-button type="primary" @click.native="passwordSubmit" :loading="logining">提交</el-button>
			</div>
		</el-dialog>
	</el-row>
</template>

<script>
	export default {
		data() {
			var checkPassword = (rule, value, callback) => {
                let reg = /^[a-z0-9]+$/i
                if (!reg.test(value)) {
                    if(rule.fullField === 'password') {
                        callback(new Error('密码只包含数字、字母组成。'));
                    } else {
                        callback(new Error('用户名只包含数字、字母组成。'));
                    }
                } else {
                    callback();
                }
            };
			return {
				sysName:'暹罗点餐-调度中心',
				collapsed:false,
				sysUserName: '',
				sysUserAvatar: 'https://siam-hangzhou.oss-cn-hangzhou.aliyuncs.com/data/images/system/logo.png',
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: ''
				},
				changePasswordForm: false,
				passwordForm: {
					oldPassword: '',
					newPassword: ''
				},
				editFormRules: {
					oldPassword: [{ required: true, validator: checkPassword, trigger: 'blur' }],
					newPassword: [{ required: true, validator: checkPassword, trigger: 'blur' }],
				},
				logining: false,
				routerArr: []
			}
		},
		// computed: {
		// 	routerArr() {
		// 		let arr = this.$router.options.routes
		// 		return arr.find(function(ele){
		// 			return ele.menuRole === 'seller'
		// 		})
		// 	}
		// },
		methods: {
			showDialog() {
				this.changePasswordForm = true
			},
			passwordSubmit() {
				let vue = this
				this.$refs.passwordForm.validate((valid) => {
                    if (valid) {
						if(vue.passwordForm.newPassword.length < 6){
							vue.$message({
								showClose: true,
								message: "密码长度最少6位",
								type: 'error'
							});			
							return false;				
						}						
                        vue.logining = true;
                        let param = {
                            oldPassword: vue.$utils.Base64(vue.passwordForm.oldPassword),
                            newPassword: vue.$utils.Base64(vue.passwordForm.newPassword)
                        }
                        vue.$http.post(vue, '/rest/admin/updatePassword', param,
                            (vue, data)=> {
                                vue.logining = false;
                                vue.$message({
                                    showClose: true,
                                    message: data.message,
                                    type: 'success'
                                });
								vue.changePasswordForm = false
								vue.$router.push({ path: '/login'})
                            },
                            (error, data)=> {
                                vue.logining = false;
                                vue.$message({
                                    showClose: true,
                                    message: data.message,
                                    type: 'error'
                                });
                            })
                    }else {
                        console.log('error submit!!');
                        return false;
                    }
                })
			},
			closeDialog(formName) {
                this.$refs[formName].resetFields();
            },
			handleopen() {
				//console.log('handleopen');
			},
			handleclose() {
				//console.log('handleclose');
			},
			handleselect: function (a, b) {
			},
			//退出登录
			logout: function () {
				var vue = this;
				this.$confirm('确认退出吗?', '提示', {
					//type: 'warning'
				}).then(() => {
					vue.$http.post(vue, '/rest/admin/logout', {},
						function(vue, data) {
							sessionStorage.removeItem('user');
							vue.$router.push('/login');
						}, function(error, data) {
							vue.$message({
								showClose: true,
								message: data.message,
								type: 'error'
							});
						}
					)
				}).catch(() => {
				});
			},
			//折叠导航栏
			collapse:function(){
				this.collapsed=!this.collapsed;
			},
			showMenu(i,status){
				this.$refs.menuCollapsed.getElementsByClassName('submenu-hook-'+i)[0].style.display=status?'block':'none';
			},
			formatRouter() {
				let arr = this.$router.options.routes
				return arr.filter(function(ele){
					return ele.menuRole == 'seller'
				})
			}
		},
		mounted() {
			this.routerArr = this.$router.options.routes
			var user = sessionStorage.getItem('user');
			if (user) {
				user = JSON.parse(user);
				this.sysUserName = user.username || '';
				if(user.issaler) {
					this.routerArr = this.formatRouter()
				}
			}
		}
	}

</script>

<style scoped lang="scss">
	.container {
		position: absolute;
		top: 0px;
		bottom: 0px;
		width: 100%;
		.header {
			height: 48px;
			line-height: 48px;
			background: white;
			color:black;
			.userinfo {
				text-align: right;
				padding-right: 35px;
				float: right;
				.userinfo-inner {
					cursor: pointer;
					color:#fff;
					img {
						width: 40px;
						height: 40px;
						border-radius: 20px;
						margin: 10px 0px 10px 10px;
						float: right;
					}
				}
			}
			.logo {
				//width:230px;
				height:48px;
				font-size: 22px;
				padding-left:20px;
				padding-right:20px;
				border-color: rgba(238,241,146,0.3);
				border-right-width: 1px;
				border-right-style: solid;
				img {
					width: 40px;
					float: left;
					margin: 10px 10px 10px 18px;
				}
				.txt {
					color:#fff;
				}
			}
			.logo-width{
				width:230px;
			}
			.logo-collapse-width{
				width:60px
			}
			//头部正方体图标
			.tools{
				padding: 0px 23px;
				width:14px;
				height: 48px;
				line-height: 48px;
				cursor: pointer;
			}
		}
		.main {
			display: flex;
			// background: #324057;
			position: absolute;
			top: 48px; //决定头部的高度
			bottom: 0px;
			overflow: hidden;
			aside {
				flex:0 0 230px;
				width: 230px;
				// position: absolute;
				// top: 0px;
				// bottom: 0px;
				.el-menu{
					height: 100%;
					overflow: auto;
				}
				.collapsed{
					width:60px;
					.item{
						position: relative;
					}
					.submenu{
						position:absolute;
						top:0px;
						left:60px;
						z-index:99999;
						height:auto;
						display:none;
					}

				}
			}
			.menu-collapsed{
				flex:0 0 60px;
				width: 60px;
			}
			.menu-expanded{
				flex:0 0 230px;
				width: 230px;
			}
			.content-container {
				// background: #f1f2f7;
				flex:1;
				// position: absolute;
				// right: 0px;
				// top: 0px;
				// bottom: 0px;
				// left: 230px;
				overflow-y: scroll;
				padding: 20px;
				.breadcrumb-container {
					//margin-bottom: 15px;
					.title {
						width: 200px;
						float: left;
						color: #475669;
					}
					.breadcrumb-inner {
						float: right;
					}
				}
				.content-wrapper {
					background-color: #fff;
					box-sizing: border-box;
				}
			}
		}
		.changePassword {
			.changePassform {
				margin: 0 auto;
				max-width: 600px;
				.el-input {
					max-width: 400px;
				}
			}
		}
		
	}
</style>