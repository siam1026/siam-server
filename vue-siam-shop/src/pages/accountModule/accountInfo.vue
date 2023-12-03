<template>
  <section class="content statisticGraph-wrapper">
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-button type="primary" @click="handleEdit">申请提现</el-button>
				</el-form-item>
			</el-form>
		</el-col>
        
		<div class="todayBox">
			<div class="totdayBlock blue">
				<p class="todayBlockTitle">可提现资金</p>
				<p class="todayBlockNum">
					<span>￥{{todayList.withdrawableBalance || 0}}</span>
				</p>
			</div>
			<div class="totdayBlock green">
				<p class="todayBlockTitle">用户下单冻结资金</p>
				<p class="todayBlockNum">
					<span>￥{{todayList.orderFrozenBalance || 0}}</span>
				</p>
			</div>
			<!-- <div class="totdayBlock yellow">
				<p class="todayBlockTitle">被冻结资金</p>
				<p class="todayBlockNum">
					<span>￥{{todayList.frozenBalance || 0}}</span>
				</p>
			</div> -->
		</div>

    <el-form :model="editForm" label-width="150px" class="editForm" style="width: 80%;" :rules="editFormRules" ref="editForm">
				<el-form-item label="姓名" prop="realName">
					<el-input v-model="editForm.realName"></el-input>
				</el-form-item>

				<el-form-item label="身份证号码" prop="idCard">
					<el-input v-model="editForm.idCard"></el-input>
				</el-form-item>
 
				<el-form-item label="开户行" prop="openingBankAddress">
					<el-input v-model="editForm.openingBankAddress"></el-input>
				</el-form-item>

				<el-form-item label="开户银行名称" prop="openingBankName">
					<el-input v-model="editForm.openingBankName"></el-input>
				</el-form-item>        

        <el-form-item label="银行卡号" prop="bankCard">
					<el-input v-model="editForm.bankCard"></el-input>
				</el-form-item>    

        <el-form-item label="支付宝账号" prop="alipayAccount">
          <el-input v-model="editForm.alipayAccount"></el-input>
        </el-form-item>        

        <el-form-item label="微信账号" prop="wechatAccount">
          <el-input v-model="editForm.wechatAccount"></el-input>
        </el-form-item>    
			</el-form>

      <!--编辑界面-->
      <el-dialog title="编辑" :visible.sync="editFormVisible_second" @close="closeDialog" :close-on-click-modal="false">
        <el-form :model="editForm_second" label-width="150px" style="width: 80%;" :rules="editFormRules_second" ref="editForm_second">			
          <el-form-item label="提现金额" prop="withdrawAmount">
            <el-input v-model="editForm_second.withdrawAmount" type="number"></el-input>
          </el-form-item>	

          <div class="Status"   style="color: #FF0000 ; text-align:center; ine-height: 30px; margin-bottom: 20px;">
                 <span  >审核通过后，提现金额将默认发放到您的微信零钱！</span> 
           </div>
          <!-- <el-form-item label="手机号" prop="telephone">
            <el-input  readonly="true" v-model="editForm_second.telephone" type="string"></el-input>
          </el-form-item>	
          <el-form-item label="微信号" prop="wechatAccount">
            <el-input v-model="editForm_second.wechatAccount" type="string"></el-input>
          </el-form-item>		 -->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible_second = false">取消</el-button>
          <el-button type="primary" @click.native="editSubmit_second" :loading="editLoading_second">提交</el-button>
        </div>
      </el-dialog>
      
			<div slot="footer" class="el-dialog__footer" style="text-align:center;">
				<el-button type="primary" @click="saveShopMsg">保存</el-button>
			</div>      
  </section>
</template>
<script>
  import $ from 'jquery'
  
  // var urlPrefix = 'https://api.siam.shop';
  var urlPrefix = 'https://api.show.siamit.cn';
  // var urlPrefix = 'http://localhost:8060/siam-multi-brand-server';

  export default {
    data() {
      return {
        todayList:{}, //今日数据
        editForm: {
          realName:'',         
          idCard:'',          
          openingBankAddress:'',                    
          openingBankName:'',       
          bankCard:'',                    
          alipayAccount:'',       
          wechatAccount:''
        },
        editFormRules: {
          realName: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
          // idCard: [{ required: true, message: '请输入身份证号码', trigger: 'blur' }],
          // openingBankAddress: [{ required: true, message: '请输入开户行', trigger: 'blur' }],
          // openingBankName: [{ required: true, message: '请输入开户银行名称', trigger: 'blur' }],
          // bankCard: [{ required: true, message: '请输入银行卡号', trigger: 'blur' }],        
          // alipayAccount: [{ required: true, message: '请输入支付宝账号', trigger: 'blur' }],
          // wechatAccount: [{ required: true, message: '请输入微信账号', trigger: 'blur' }]
        },

				editFormVisible_second: false,//编辑界面是否显示
				editLoading_second: false,
				editFormRules_second: {
					withdrawAmount: [{ required: true, message: '请输入提现金额', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm_second: {
					withdrawAmount: '',
				},        
      }
    },
    methods: {    
      saveShopMsg() {
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            let vue = this
            let param = Object.assign({}, this.editForm);
            delete param.createTime
            delete param.updateTime

            let url = '/rest/merchant/update'
            vue.$http.post( vue, url, param,
              (vue, data) => {
                vue.$message({
                  showClose: true,
                  message: data.message,
                  type: 'success'
                });
                // vue.goBack()
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
      },
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible_second = true;
				if(row){
					this.editForm_second = {
						id: row.id,
						withdrawAmount: row.withdrawAmount,
					}
				}else{
					this.editForm_second = {
						withdrawAmount: '',	
					}
				}					
      },      
			editSubmit_second: function () { // 编辑
				this.$refs.editForm_second.validate((valid) => {
					if (valid) {
						let vue = this
						let param = Object.assign({}, this.editForm_second);
						let url = '';
            //提现金额保留两位小数
            param.withdrawAmount = Number(param.withdrawAmount).toFixed(2);

            if(param.withdrawAmount > this.todayList.withdrawableBalance){
                vue.$message({
                  showClose: true,
                  message: "超过可提现余额",
                  type: 'error'
                });                
                return false;
            }

            //查询平台服务费比例
            let merchantWithdrawFeeRatio = '';
            let map = {};
            $.ajax({
                type: "post",
                contentType: "application/json",
                url: urlPrefix + '/rest/setting/selectCurrent',
                async: false,
                data: JSON.stringify(map),
                beforeSend: function(request) {
                    request.setRequestHeader("token", sessionStorage.getItem("token"));
                },                            
                success: function(data){
                  merchantWithdrawFeeRatio = Number(data.data.merchantWithdrawFee);
                },
                error: function(data){
                  vue.$message({
                    showClose: true,
                    message: !data.message ? '操作失败，请联系管理员！' : data.message,
                    type: 'error'
                  });
                }
            });
            if(merchantWithdrawFeeRatio == ''){
              return false;
            }

            let message = '';
            //计算平台服务费
            let actualMerchantWithdrawFeeRatio = (Number(merchantWithdrawFeeRatio) / 100).toFixed(2);
            let platformFee = (Number(param.withdrawAmount) * Number(actualMerchantWithdrawFeeRatio)).toFixed(2);
            let totalAmount = (Number(param.withdrawAmount) + Number(platformFee)).toFixed(2);   
            // alert(merchantWithdrawFeeRatio + "-" + actualMerchantWithdrawFeeRatio + "-" + platformFee + "-" + totalAmount);
            //提现金额+服务费 高于 可提现金额，需要计算实际到账金额
            if(totalAmount > this.todayList.withdrawableBalance){
              let actualAmount = (Number(param.withdrawAmount) - Number(platformFee)).toFixed(2);
              message = "余额不足以支付服务费，实际到账金额如下</br>"+
                            "到账金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color='red'>"+ actualAmount +"元</font></br>"+
                            "本次服务费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ platformFee +"元";              
            }else{
              message = "提现金额&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ param.withdrawAmount +"元</br>"+
                            "本次服务费&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ platformFee +"元";   

            }

            this.$confirm(message, '收费提示', {
              dangerouslyUseHTMLString: true,
              confirmButtonText: '继续提现',
              cancelButtonText: '取消',              
              type: 'warning'
            }).then(() => {
							// this.editLoading_second = true;
              url = '/rest/merchant/merchantWithdrawRecord/insert';
							vue.$http.post(vue, url, param,
								(vue, data) => {
									// this.editLoading_second = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'success'
									});
									
									this.getDetail()
									vue.editFormVisible_second = false;
								}, (error, data) => {
									vue.editFormVisible_second = false;
									vue.$message({
										showClose: true,
										message: data.message,
										type: 'error'
									});
								}
							)
            }).catch(() => {});

					}
				});
			},      
      getDetail() { // 获取商品详情
          let vue = this
          vue.$http.post(vue, '/rest/merchant/getLoginMerchantInfo', {},
            (vue, data) => {
              let obj = data.data
              vue.editForm = Object.assign({}, obj)
              vue.todayList.balance = Number(obj.balance).toFixed(2);
              vue.todayList.withdrawableBalance = Number(obj.withdrawableBalance).toFixed(2);
              vue.todayList.frozenBalance = Number(obj.frozenBalance).toFixed(2);
              vue.todayList.orderFrozenBalance = Number(obj.orderFrozenBalance).toFixed(2);
            },(error, data)=> {
              vue.$message({
                showClose: true,
                message: data.message,
                type: 'error'
              });
            }
          )
      },
    },
    created() {
      this.getDetail()
			//开启订单自动打印定时器
			this.$orderPrint.init();           
    }
  }
</script>

<style scoped>
.editForm .el-input {
  width: 420px;
}
.standForm .el-input {
  width: 200px;
}
.avatar-uploader .el-upload {
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    width: 148px;
    height: 148px;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 148px;
    height: 148px;
    line-height: 148px;
    text-align: center;
  }
  .avatar {
    width: 148px;
    height: 148px;
    display: block;
  }
  .editForm .minInput {
    width: 240px;
  }

/* Start 余额信息模块 */
  .content{
    width: 100%;
    height: 100%;
    padding-top: 30px;
    overflow: hidden;
    /* background-color: #E7F1FA; */
  }
  .todayTitle{
    font-size:20px;
    font-weight:bold;
    color:rgba(51,51,51,1);
    margin-right: 16px;
  }
  .todayBox{
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    margin-top: 24px;
    margin-bottom: 60px;
    display: -webkit-inline-box;    
  }
  .totdayBlock{
    width: 20%;
    color: #fff;
    padding-top: 30px;
    padding-bottom: 30px;
    border-radius:4px;
    cursor: pointer;
    margin-right: 20px;
  }
  .orange{
    background-color: #F78A71;
  }
  .yellow{
    background-color: #F8B548;
  }
  .green{
    background-color: #3FD7E4;
  }
  .blue{
    background-color: #05ACEC;
  }
  .todayBlockTitle{
    margin-left: 18px;
    /* color: rgba(0, 0, 0, 0.45); */
    font-weight: 700;
    font-size: 14px;
  }
  .todayBlockNum{
    /* text-align: center; */
    font-size: 34px;
    line-height: 34px;
    margin-top: 20px;
    margin-left: 18px;
  }
  .searchBox{
    margin-top: 10px;
  }

  
  .searchTab{
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
  }
  .searchTab span{
    font-size: 16px;
    padding-right: 10px;
  }
  .searchBtn{
    margin-left: 10px;
  }
  .descBox{
    margin-top: 40px;
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }
  .descTab{
    width: 20%;
    color: #fff;
    padding-top: 30px;
    padding-bottom: 100px;
    border-radius:4px;
    border-top: 2px solid #E8AB45;
    background-color: aliceblue;
    cursor: pointer;
  }
  .descTitle{
    font-size:16px;
    color:rgba(153,153,153,1);
    margin-left: 18px;
  }
  .descNum{
    text-align: center;
    font-size:34px;
    /* font-weight:bold; */
    color:rgba(51,51,51,1);
    margin-top: 50px;
  }
  .todayBlockCompare {
    font-size: 16px;
      color: black;
  }
  .todayBlockYesterday {
    margin-left: 18px;
    margin-top: 26px;
    font-size: 14px;
    line-height: 1.5715;
  }
/* End 余额信息模块 */
</style>