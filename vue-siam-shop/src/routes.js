// const Login = () => import( './pages/Login.vue')
const LoginMain = () => import('./pages/LoginMain.vue')
const NotFound = () => import( './pages/404.vue')
const Home = () => import( './pages/Home.vue')
const Main = () => import( './pages/Main.vue')

const login = () => import('./pages/login/login.vue')
const register = () => import('./pages/login/register.vue')
const userAgreement = () => import('./pages/login/userAgreement.vue')
const QuickLogin = () => import('./pages/login/QuickLogin.vue')
const setPassword = () => import('./pages/login/setPassword.vue')
const fillInformation = () => import('./pages/login/fillInformation.vue')

//空页面
const emptyList = () => import( './pages/basicModule/emptyList.vue')
//const CoreImage = () => import( './pages/basicModule/CoreImage.vue')


//商品管理
const menuList = () => import( './pages/goodsManage/menuList.vue')
const goodsList = () => import( './pages/goodsManage/goodsList.vue')
const addGoods = () => import( './pages/goodsManage/addGoods.vue')
const editGoods = () => import( './pages/goodsManage/editGoods.vue')
const goodsAccessoriesList = () => import( './pages/goodsManage/goodsAccessoriesList.vue')
const rawmaterialList = () => import( './pages/goodsManage/rawmaterialList.vue')
const goodsRawmaterialRelationList = () => import( './pages/goodsManage/goodsRawmaterialRelationList.vue')
// const specificationList = () => import( './pages/goodsManage/specificationList.vue')
// const stockList = () => import( './pages/goodsManage/stockList.vue')
const recommendGoods = () => import( './pages/goodsManage/recommendGoods.vue')

//订单管理
const waitHandleOrderList = () => import( './pages/orderManage/waitHandleOrderList.vue')
const forHereTabsChild1 = () => import( './pages/orderManage/forHereTabsChild1.vue')
const takeOutTabsChild1 = () => import( './pages/orderManage/takeOutTabsChild1.vue')
const takeOutTabsChild2 = () => import( './pages/orderManage/takeOutTabsChild2.vue')
const forHereTabsChild2 = () => import( './pages/orderManage/forHereTabsChild2.vue')
const refundOrderList = () => import( './pages/orderManage/refundOrderList.vue')
const forHereTabsChild3 = () => import( './pages/orderManage/forHereTabsChild3.vue')
const orderDetail = () => import( './pages/orderManage/orderDetail.vue')
//const unpaidOrderList = () => import( './pages/MallManage/unpaidOrderList.vue')
const forHereOrderList = () => import( './pages/orderManage/forHereOrderList.vue')
const takeOutOrderList = () => import( './pages/orderManage/takeOutOrderList.vue')
const todayOrderList = () => import( './pages/orderManage/todayOrderList.vue')
const appraiseList = () => import( './pages/orderManage/appraiseList.vue')

//促销管理
const fullReductionRuleList = () => import( './pages/promotionManage/fullReductionRuleList.vue')
const couponsList = () => import( './pages/promotionManage/couponsList.vue')

//门店管理
const shopInfo = () => import( './pages/shopManage/shopInfo.vue')
const shopInfoImportant = () => import( './pages/shopManage/shopInfoImportant.vue')
const shopChangeRecord = () => import( './pages/shopManage/shopChangeRecord.vue')
const courierList = () => import( './pages/shopManage/internal/courierList.vue')
const ticketPrinterList = () => import( './pages/shopManage/internal/ticketPrinterList.vue')
const labelPrinterList = () => import( './pages/shopManage/internal/labelPrinterList.vue')

//轮播图管理
const advertisementList = () => import( './pages/shopDecoration/advertisementList.vue')

//数据中心
const statisticGraph = () => import( './pages/basicModule/statisticGraph.vue')

//财务报表
const accountInfo = () => import( './pages/accountModule/accountInfo.vue')
const merchantWithdrawRecord = () => import( './pages/accountModule/merchantWithdrawRecord.vue')
const merchantBillingRecord = () => import( './pages/accountModule/merchantBillingRecord.vue')


let routes = [
    // {
    //     path: '/login',
    //     component: Login,
    //     name: '',
    //     hidden: true
    // },
    {
      path: '/login',
      component: LoginMain,
      name: '',
      hidden: true,
      children: [
          { path: '/', component: login, pagename: '登录', icon: 'el-icon-search', hidden: true },
          { path: '/register', component: register, pagename: '注册', icon: 'el-icon-search', hidden: true },
          { path: '/QuickLogin', component: QuickLogin, pagename: '手机验证登录', icon: 'el-icon-search', hidden: true },
          { path: '/setPassword', component: setPassword, pagename: '忘记密码', icon: 'el-icon-search', hidden: true },
          
      ]
    },    
    {
      path: '/userAgreement',
      component: userAgreement,
      name: '',
      hidden: true
    },    
    {
      path: '/fillInformation',
      component: fillInformation,
      name: '',
      hidden: true
    },            
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
      path: '/',
      component: Home,
      name: '数据中心',
      iconCls: 'el-icon-user',
      leaf: true,//只有一个节点
      children: [
          // { path: '/emptyList', component: emptyList, name: '每日数据' },
          { path: '/statisticGraph', component: statisticGraph, name: '数据中心' },
      ]
    },
    {
      path: '/',
      component: Home,
      name: '订单管理',
      iconCls: 'el-icon-s-order',
      children: [
          { path: '/todayOrderList', component: todayOrderList, name: '今日订单' },
          { path: '/forHereOrderList', component: forHereOrderList, name: '自取订单' },
          { path: '/takeOutOrderList', component: takeOutOrderList, name: '外卖订单' },          
          // { path: '/unpaidOrderList', component: unpaidOrderList, name: '未付款' },
          // { path: '/waitHandleOrderList', component: waitHandleOrderList, name: '待处理订单' },
          // { path: '/forHereTabsChild1', component: forHereTabsChild1, name: '待自取订单' },
          // { path: '/takeOutTabsChild1', component: takeOutTabsChild1, name: '待配送订单' },
          // { path: '/takeOutTabsChild2', component: takeOutTabsChild2, name: '已配送订单' },
          // { path: '/forHereTabsChild2', component: forHereTabsChild2, name: '已完成订单' },       
          { path: '/refundOrderList', component: refundOrderList, name: '售后处理' },          
          { path: '/appraiseList', component: appraiseList, name: '评价管理' },     
          // { path: '/forHereTabsChild3', component: forHereTabsChild3, name: '已取消订单' },          
          { path: '/orderDetail', component: orderDetail, name: '查看订单详情', hidden: true },   
         // { path: '/CoreImage', component: CoreImage, name: '裁剪图片' },        
      ]
    }, 
    {
      path: '/',
      component: Home,
      name: '商品管理',
      iconCls: 'el-icon-milk-tea',
      children: [
          { path: '/menuList', component: menuList, name: '分类管理' },
          { path: '/goodsList', component: goodsList, name: '商品列表' },
          { path: '/addGoods', component: addGoods, name: '新增商品', hidden: true },
          { path: '/editGoods', component: editGoods, name: '编辑商品', hidden: true },
          { path: '/recommendGoods', component: recommendGoods, name: '商家推荐' },
          // { path: '/goodsAccessoriesList', component: goodsAccessoriesList, name: '商品辅料' },          
          // { path: '/rawmaterialList', component: rawmaterialList, name: '商品原料' },  
          // { path: '/goodsRawmaterialRelationList', component: goodsRawmaterialRelationList, name: '原料配比' },  
          // { path: '/specificationList', component: specificationList, name: '商品规格' },
          // { path: '/stockList', component: stockList, name: '商品库存' },          
      ]
    },        
    {
      path: '/',
      component: Home,
      name: '活动中心',
      iconCls: 'el-icon-s-order',
      children: [
          // { path: '/unpaidOrderList', component: unpaidOrderList, name: '未付款' },
          { path: '/fullReductionRuleList', component: fullReductionRuleList, name: '满减' },
          { path: '/couponsList', component: couponsList, name: '优惠券' },
      ]
    },     
    {
      path: '/',
      component: Home,
      name: '门店管理',
      iconCls: 'el-icon-house',
      children: [
          { path: '/shopInfo', component: shopInfo, name: '门店基本信息' },
          { path: '/shopInfoImportant', component: shopInfoImportant, name: '门店重要信息' },
          { path: '/shopChangeRecord', component: shopChangeRecord, name: '门店信息变更记录' },          
          { path: '/advertisementList', component: advertisementList, name: '门店装修' },
          { path: '/courierList', component: courierList, name: '骑手信息' },
      ]
    },
    {
      path: '/',
      component: Home,
      name: '打印机管理',
      iconCls: 'el-icon-user',
      children: [
          { path: '/ticketPrinterList', component: ticketPrinterList, name: '小票打印机列表' },
          { path: '/labelPrinterList', component: labelPrinterList, name: '标签打印机列表' },
      ]
    },
    {
      path: '/',
      component: Home,
      name: '财务报表',
      iconCls: 'el-icon-user',
      children: [
          { path: '/accountInfo', component: accountInfo, name: '账户信息' },          
          { path: '/merchantBillingRecord', component: merchantBillingRecord, name: '账单记录' },//交易明细          
          { path: '/merchantWithdrawRecord', component: merchantWithdrawRecord, name: '提现记录' },
      ]
    },        
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;