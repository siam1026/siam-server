<template>
	<section>
		<!--工具条-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="searchMsg">
				<el-form-item>
					<el-input v-model="searchMsg.name" placeholder="请输入商品名称搜索"></el-input>
				</el-form-item>
        <el-form-item>
					<el-input v-model="searchMsg.menuName" placeholder="请输入商品类别搜索"></el-input>
				</el-form-item>
				<el-form-item>
					<el-select v-model="searchMsg.status" placeholder="请选择状态搜索" clearable>
						<el-option label="待上架" :value="1"></el-option>
						<el-option label="已上架" :value="2"></el-option>
						<el-option label="已下架" :value="3"></el-option>
						<el-option label="售罄" :value="4"></el-option>       
					</el-select>
				</el-form-item>        
				<el-form-item>
					<el-button type="primary" @click="getList(1)">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="gotoAdd('add')">新增</el-button>
				</el-form-item>
				<!-- <el-form-item>
          <el-button type="primary" @click="printOrderLabel">打印</el-button>          
				</el-form-item> -->
			</el-form>
		</el-col>
		<!--列表-->
		<el-table :data="list" ref="goodsTable" @selection-change="handleSelectionChange" highlight-current-row v-loading="listLoading" style="width: 100%;" :cell-style="cellStyle" :header-cell-style="headerCellStyle">
      <el-table-column type="selection" width="55"></el-table-column>
			<el-table-column type="index" label="序号" width="50">
				<template scope="scope">
					<span>{{(searchMsg.pageNo - 1) * searchMsg.pageSize + scope.$index + 1}}</span>
				</template>
			</el-table-column>
      <el-table-column prop="id" label="商品编号"></el-table-column>
      <el-table-column label="商品主图" prop="mainImage">
				<template slot-scope="scope">
					<img :src="$http.publicUrl(scope.row.mainImage)" width="50" height="50">
				</template>
			</el-table-column>
      <el-table-column prop="name" label="商品名称"></el-table-column>
      <el-table-column prop="menuName" label="商品类别"></el-table-column>      
			<!-- <el-table-column prop="goodsNo" label="编号"></el-table-column> -->
      <el-table-column prop="price" label="一口价" :formatter="addUnit"></el-table-column>
      <!-- <el-table-column prop="salePrice" label="折扣价" :formatter="formatSalePrice"></el-table-column> -->
      <!-- <el-table-column prop="packingCharges" label="包装费" :formatter="addUnit"></el-table-column> -->
      <!-- <el-table-column prop="productTime" label="制作时长">
				<template scope="scope">
          <span>{{scope.row.productTime}}分钟</span>
        </template>        
      </el-table-column> -->
      <!-- <el-table-column prop="exchangePoints" label="兑换商品所需积分数量">
				<template scope="scope">
          <span>{{scope.row.exchangePoints}}个</span>
        </template>            
      </el-table-column> -->
      <el-table-column prop="status" label="状态" width="100">
				<template scope="scope">
          <span v-if="scope.row.status == 1" style="color:blue;font-weight:bold;">待上架</span>
          <span v-else-if="scope.row.status == 2" style="color:green;font-weight:bold;">已上架</span>
          <span v-else-if="scope.row.status == 3" style="color:grey;font-weight:bold;">已下架</span>
          <span v-else-if="scope.row.status == 4" style="color:red;font-weight:bold;">售罄</span>
        </template>
      </el-table-column> 
      <el-table-column prop="saleqty" label="已售出数量">
        
        <template scope="scope">
          <span>{{scope.row.saleqty}}份</span>
        </template> </el-table-column> 
      <!-- <el-table-column prop="stock" label="可制作杯数">
				<template scope="scope">
          <span v-if="scope.row.status == 2" style="color:green;font-weight:bold;">{{scope.row.stock}}杯</span>
          <span v-else style="color: black;">-</span>
        </template>        
      </el-table-column>       -->
			<el-table-column prop="createTime" label="创建时间" :formatter="formatTime"></el-table-column>
			<el-table-column label="操作" fixed="right">
				<template slot-scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑规格</el-button>
					<!-- <el-button size="small" v-show="scope.row.goodsstatus == 1" @click="updateStatus(scope.row, 2)">下架</el-button> -->
					<!-- <el-button size="small" v-show="scope.row.goodsstatus == 0 || scope.row.goodsstatus == 2" @click="updateStatus(scope.row, 1)">上架</el-button> -->
					<el-button size="small" @click="gotoAdd('edit', scope.row)">修改商品</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.row.id)">删除商品</el-button>
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

		<!--编辑界面-->
		<el-dialog title="添加规格" :visible.sync="editFormVisible" @close="editFormClose" :close-on-click-modal="false">
      <div class="dialogDiv">
        <el-form size="small">
            <el-form-item v-for="(item , index) in editForm" :key="index">
              <el-row :gutter="20">
                <el-col :span="4">
                  <el-input placeholder="规格名称" v-model="item.specificationName"></el-input>
                </el-col>
                <el-col :span="6">
                  <el-input placeholder="规格选项名称" v-model="item.name"></el-input>
                </el-col>
                <el-col :span="4">
                  <el-input :min="0" placeholder="加价金额(元)" v-model="item.price"></el-input>
                </el-col>              
                <!-- <el-col :span="4">
                  <el-input :min="0" placeholder="库存" v-model="item.stock"></el-input>
                </el-col> -->
                <el-col :span="2"> <!-- :span属性可以用来调节与后面元素的间隔大小 -->
                  <el-button type="primary" v-if="item.id != ''" @click.prevent="updateSpecification(item)">保存</el-button>
                </el-col>
                <el-col :span="3">
                  <el-button type="danger" v-if="item.id != ''" @click.prevent="removeSpecification(item)">删除</el-button>
                </el-col>                
                <el-col :span="3">
                  <el-button @click="addSpecification(item)" v-show="index+1 === editForm.length">添加</el-button>
                </el-col>
              </el-row>
            </el-form-item>
        </el-form>
        <!-- <div slot="footer" class="dialog-footer">
          <el-button @click.native="editFormVisible = false">关闭</el-button>
        </div> -->
      </div>
		</el-dialog>
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
				editFormRules: {
          specificationName: [{ required: true, message: '请输入规格名称', trigger: 'change' }],
          name: [{ required: true, message: '请输入规格选项名称', trigger: 'change' }],
          price: [{ required: true, message: '请输入加价金额', trigger: 'blur' }],
					// stock: [{ required: true, message: '请输入库存', trigger: 'blur' }],
				},
				//编辑界面数据
				editForm: [],
        searchData:[],
        goodsId: '',
				goodsTable: '',
				multipleSelection: [],        
			}
		},
		methods: {
      cellStyle({row, column, rowIndex, columnIndex}){
        return "text-align:center";
      },
      headerCellStyle({row, rowIndex}){
        return "text-align:center";
      },
      editFormClose() {
        this.editForm = []
        // this.editForm.push({price: '', name: '', stock: ''}) 
      },
      updateSpecification(item) {
        let vue = this;
        let data = item
        let regEn = /^[1-9]\d*$/;
        let regEnPrice = /^[0-9]\d*$/;
        if (!data.specificationName) {
          vue.$message({
            showClose: true,
            message: '请输入规格名称',
            type: 'error'
          });
          return false
        }        
        if (!data.name) {
          vue.$message({
            showClose: true,
            message: '请输入规格选项名称',
            type: 'error'
          });
          return false
        }
        if (!regEnPrice.test(data.price)) {
          vue.$message({
            showClose: true,
            message: '请输入正确的加价金额',
            type: 'error'
          });
          return false
        }                   
        let param = Object.assign({}, data);       
        delete param.createTime;
        delete param.updateTime;
				vue.$http.post(vue, '/rest/admin/pointsMall/goodsSpecificationOption/update', param,
					function(vue, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
					}, function(error, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
					}
				)
      },      
      removeSpecification(item) {
        let vue = this;        
        let data = item  
        var index = this.editForm.indexOf(data)
        // alert(JSON.stringify(item));
				vue.$http.post(vue, '/rest/admin/pointsMall/goodsSpecificationOption/delete', {"ids": [data.id]},
					function(vue, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
						if (index !== -1 ) {
              vue.editForm.splice(index, 1)
              vue.editForm.length < 1 && vue.editForm.push({id: '', specificationName: '', name: '', price: ''}) 
            }
					}, function(error, data) {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
					}
				)
      },
      addSpecification(row) { // 新增规格
        let vue = this
        let data = row
        let regEn = /^[1-9]\d*$/;
        let regEnPrice = /^[0-9]\d*$/;
        if (!data.specificationName) {
          vue.$message({
            showClose: true,
            message: '请输入规格名称',
            type: 'error'
          });
          return false
        }        
        if (!data.name) {
          vue.$message({
            showClose: true,
            message: '请输入规格选项名称',
            type: 'error'
          });
          return false
        }
        if (!regEnPrice.test(data.price)) {
          vue.$message({
            showClose: true,
            message: '请输入正确的加价金额',
            type: 'error'
          });
          return false
        }        
        // if (!data.stock) {
        //   vue.$message({
        //     showClose: true,
        //     message: '请输入库存',
        //     type: 'error'
        //   });
        //   return false
        // }
        let param = Object.assign({}, data);
        param.goodsId = vue.goodsId
        let url = ''
        param.id ? url = '/rest/admin/pointsMall/goodsSpecificationOption/update' : url = '/rest/admin/pointsMall/goodsSpecificationOption/insert'
        vue.$http.post( vue, url, param,
          (vue, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'success'
            });
            //回写当前行的id
            row.id = data.data;
            //新创建一行数据
            vue.editForm.push({id: '', specificationName: '', name: '', price: ''}) 
          }, (error, data) => {
            vue.$message({
              showClose: true,
              message: data.message,
              type: 'error'
            });
          }
        )
      },
      addUnit(row, column) { // 添加单位
        return (row[column.property] || 0) + '元'
      },
      formatSalePrice(row, column) { // 添加单位
        if(row.isSale){
          return row.salePrice + '元';
        }else{
          return "-";
        }
      },            
      gotoAdd(type, row) {
        if(type === 'add') {
          this.$router.push({path: 'pointsMall_addGoods'})
        } else {
          this.$router.push({path:'pointsMall_editGoods',query:{id: row.id}})
        }
      },
			formatTime(row, column) {
				let date = new Date(row[column.property]);
				return this.$utils.formatDate(date, 'yyyy-MM-dd hh:mm');
			},
			formatappType (row, column) { // app类型0=android  1=ios
				return row.apptype == 1 ? 'ios' : 'android';
			},
      formatType (row, column) { // 0-待上架，1-已上架，2-已下架，3-已删除,4-售罄
        let type = row[column.property] 
        switch (type) {
          case 1:
            return '待上架'
            break;
          case 2:
            return '已上架'
            break;
          case 3:
            return '已下架'
            break;
          case 4:
            return '售罄'
            break;
        }
      },
			handleSizeChange(val) {
				this.searchMsg.pageSize = val;
				this.getList();
			},      
			handleCurrentChange(val) {
				this.searchMsg.pageNo = val;
				this.getList();
      },
      updateStatus(row, status=1) {
        let NoticeTitle = ''
        status === 1 ? NoticeTitle = '上架' : NoticeTitle = '下架'
        this.$confirm('确认'+ NoticeTitle +' ‘ '+ row.name +' ’ ?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
          let vue = this;
          let param = {id: row.id, goodsstatus: status}
          vue.$http.post(vue, '/rest/admin/pointsMall/goods/update', param,
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
					this.listLoading = false;
				}).catch(() => {});
      },
						getList(pageNoParam) { // 获取列表
				if(pageNoParam){
				this.searchMsg.pageNo = pageNoParam;
				} // 获取商品列表
				let vue = this
        let param = Object.assign({}, vue.searchMsg, {"token":sessionStorage.getItem("token")});

        console.log("param="+JSON.stringify(param));
        console.log("searchMsg="+JSON.stringify(vue.searchMsg));

				vue.listLoading = true;
				vue.$http.post(vue, '/rest/admin/pointsMall/goods/list', param,
					(vue, data) => {
						vue.list = data.data.records
						vue.total = data.data.total
						vue.listLoading = false;
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
      //打印订单标签
			printOrderLabel() {
				let vue = this;

				if(this.multipleSelection.length == 0){
					vue.$message({
						showClose: true,
						message: '请选择要打印标签的商品',
						type: 'error'
					});
					return false;							
				}

				//生成商品名称数组
				let goodsNameList = [];
				for(let i = 0; i < this.multipleSelection.length; i++){
          goodsNameList.push(this.multipleSelection[i].name);
        }

        //循环打印标签
				for(let i = 0; i < goodsNameList.length; i++){
          //数据行
          let AData_label = '['+
              '{'+
                '"goodsName": "'+ goodsNameList[i] +'", '+
              '}'+
          ']';
          this.printGoodsLabel(AData_label);					
        }
      },      
      //打印标签
      printGoodsLabel(AData) {
          let vue = this
          // let printerName = "Microsoft Print to PDF";
          let url = "http://127.0.0.1:12345/printreport";

          if(AData == undefined || AData == ""){
              alert('请输入订单信息');   
              return false;
          }				

          //字段，type ftBlob (base64格式) ,ftString ftInteger ftBoolean, ftFloat, ftCurrency,ftDateTime,  size (ftString 设置为实际长度,其他的设置为0,例如 ftInteger ftBlob 等设置为0 ) 
          let AField = '['
                  +'{"type": "ftString", "name": "goodsName","size": 255,"required": false},'						
                  +']';

          let param = {
              "ReportType": "fastreport",     /*报表类型 gridreport fastreport reportmachine 为空 将默认为gridreport  */	
              "ReportName": "siamGoodsLabel.fr3",     /*报表文件名 POS小票 */
              "ReportVersion": 1,              /*可选。报表版本, 为空则默认1  如果本地报表的版本过低 将从 ReportUrl 地址进行下载更新*/
              //"ReportUrl": "http://111.67.202.157:9099/report/fastreport/AAAPosTicket.fr3",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
              "ReportUrl": "",                  /*可选。为空 将不更新本地报表 , 如果本地报表不存在可以从该地址自动下载*/
              "Copies": 1,                  /*可选。打印份数，支持指定打印份数。默认1份,如果为零,不打印,只返回报表生成的pdf,jpg等文件*/
              // "PrinterName": printerName,      /*可选。指定打印机，为空的话 使用默认打印机, 请在 控制面板 -> 设备和打印机 中查看您的打印机的名称 */
              "PrintOffsetX": 0,                 /*可选。打印右偏移，单位厘米。报表的水平方向上的偏移量，向右为正，向左为负。*/
              "PrintOffsetY": 0,                /*可选。打印下偏移，单位厘米。 报表的垂直方向上的偏移量，向下为正，向上为负。*/
              "token": "aa",      /*可选。只要token值在列表中 方可打印*/
              "taskId": "1234567",     /*可选。多个打印任务同时打印时，根据该id确定返回的是哪个打印任务。 */ 
              "exportfilename": "",      /*可选。自定义 导出 文件名称 为空 或者 自定义名称 如 test */ 
              "exportfiletype": "",      /*可选。自定义 导出 文件格式 为空 或者 自定义名称 如 pdf  */ 

              "AField": AField,
                  
              "AData": AData
          };		
          
          //采用同步机制
          //ajax好像不需要设置{withCredentials:false}，也可以进行跨域访问
          $.ajax({
              type: "post",
              url: url,
              async: false,
              data: qs.stringify(param),
              success: function(data){
                  data = decodeURIComponent(data);
                  console.log(data);
                  if(data == ""){
                      //标识打印失败
                      printIsSuccess = false;                   
                      alert('连接HttpPrinter失败，请重新启动程序');                            
                  }else{
                      var obj = JSON.parse(data);
                      if(obj.status != "ok"){
                          //标识打印失败
                          printIsSuccess = false;     
                          alert('打印失败，请联系管理员'); 	                                  						
                      }
                  }
              },
              error: function(data){
                  //标识打印失败
                  printIsSuccess = false;     
                  alert('连接HttpPrinter失败，请重新启动程序');                 
              }
          });			
      },             
			handleDel (id) { // 删除
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// this.listLoading = true;
				let vue = this;
				vue.$http.post(vue, '/rest/admin/pointsMall/goods/delete', {"id":id},
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
					// this.listLoading = false;
				}).catch(() => {});
			},
			handleEdit: function (index, row) { // 显示编辑界面
				this.editFormVisible = true;
        this.goodsId = row.id
        //this.editForm.push({specificationName: '', name: '', price: ''}) 
        this.getspecificationList()
      },
      getspecificationList() {
        let vue = this   
        let param = { pageNo: -1, pageSize: 10 ,goodsId: vue.goodsId}
        vue.$http.post( vue, '/rest/admin/pointsMall/goodsSpecificationOption/list', param,
          (vue, data) => {
            let arr = data.data.records
            if(arr.length) {
              vue.editForm = arr
            }
            vue.editForm.push({id: '', specificationName: '', name: '', price: ''})            
          }, (error, data) => {
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
						let vue = this
						let param = Object.assign({}, this.editForm);
						let url = ''
						param.id ? url = '/rest/admin/pointsMall/goodsSpecificationOption/update' : url = '/rest/admin/pointsMall/goodsSpecificationOption/insert'
						vue.$http.post( vue, url, param,
							(vue, data) => {
								vue.$message({
									showClose: true,
									message: data.message,
									type: 'success'
								});
								
								// vue.getList()
								// vue.$refs['editForm'].resetFields();
								// vue.editFormVisible = false;
								
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
			handleSelectionChange(val) {
				this.multipleSelection = val;
				console.log(this.multipleSelection);
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
  .el-button+.el-button {
    margin-top: 5px;
    margin-left: 0;
  }
  .dialogDiv {
      height: 560px;
      overflow-y: auto;
      overflow-x: hidden;
  }
  /* 将列表的图片宽高比例调大一点 */
  .el-table__body img{
    width: 120px;
    height: 120px;
  }  
</style>