<template>
  <div class="page-container">
    <div class="left-container">
      <div class="toolbar">
        <el-button size="medium" style="width:99%; padding-right:25px;" @click="getTables" :loading="tableLoading"
          icon="fa fa-hand-o-right">
          选择要生成的表
        </el-button>
        <el-input placeholder="关键字过滤" v-model="filterText" size="medium" style="padding-top:5px;padding-bottom:5px;">
        </el-input>
      </div>
      <div class="left-tree">
        <el-tree class="tree" v-loading="treeLoading" :data="treeData" element-loading-text="拼命加载中"
          ref="tree" size="medium" @node-click="selectTableChange" :filter-node-method="filterNode">
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span> <li class="fa fa-table fa-lg"></li> {{ data.name }} </span>
          </span>
        </el-tree>
      </div>
    </div>

    <div class="right-container">
      <div class="base-info">
        <el-form ref="tableForm" class="tableForm" :model="tableModel" :inline="true" label-width="70px"
          size="small">
          <el-form-item label="表名">
            <el-input v-model="tableModel.name" :readonly="true"></el-input>
          </el-form-item>
          <el-form-item label="类名">
            <el-input v-model="tableModel.className"></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input v-model="tableModel.description"></el-input>
          </el-form-item>
        </el-form>
      </div>
      <div class="column-info">
        <el-table :data="tableModel.columns" class="right-table" size="mini" height="375" max-height="375" stripe>
          <el-table-column prop="name" label="" width="32">
            <template slot-scope="scope">
              <span v-if="scope.row.primaryKey">
                <i class="fa fa-key" style="color:#CBA623;"></i>
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="name" label="字段名" width="110"></el-table-column>
          <el-table-column prop="fieldName" label="属性名" width="110"></el-table-column>
          <el-table-column prop="dataType" label="数据类型" width="100"></el-table-column>
          <el-table-column prop="javaType" label="Java类型" width="100"></el-table-column>
          <el-table-column prop="length" label="长度" width="80"></el-table-column>
          <el-table-column prop="precision" label="精度" width="80"></el-table-column>
          <el-table-column prop="javaType" label="非空" width="80">
            <template slot-scope="scope">
              <span v-if="!scope.row.nullable" type="success">✔</span>
              <span v-else type="danger">✘</span>
            </template>
          </el-table-column>
          <el-table-column prop="description" label="描述"></el-table-column>
        </el-table>
      </div>
      <div class="option-info">
        <el-form ref="optionForm" class="optionForm" :inline="true" label-width="70px"
          size="small">
          <span>
            <el-form-item label="包名">
              <el-input v-model="generateModel.basePackage" placeholder="如：com.louis.kitty">
                <el-button type="file" slot="append" icon="fa fa-folder fa-lg" @click="chooseBasePackage"></el-button>
              </el-input>
            </el-form-item>

          </span>
          <span style="float:right; padding-right:20px;">
            <el-button size="small" type="primary" :disabled="disabledGenerateBtn" :loading="generateLoading" @click="generateCode">生成代码</el-button>
          </span>
          <span style="float:right; padding-right:20px;">
              <el-button size="small" type="primary" :disabled="disabledGenerateBtn" :loading="generateLoading" @click="downloadFile">下载</el-button>
         </span>
        </el-form>
      </div>
    </div>
    <!--数据源配置界面-->
    <datasource-dialog ref="datasourceDialog" v-if="datasourceVisible"></datasource-dialog>
    <!--表格数据选择界面-->
    <select-table-dialog title="选择要生成的表" ref="selectTableDialog" v-if="selectTableDialogVisible"
      :data="selectTableData" :columns="selectTableColumns" @selectionChange="tableSelectionChange"
      :showHeader="true">
    </select-table-dialog>
  </div>
</template>

<script>
import axios from "axios"
import DatasourceDialog from "@/views/Datasource/DatasourceDialog"
import SelectTableDialog from "@/components/SelectTableDiaog"
export default {
  components: {
    DatasourceDialog,
    SelectTableDialog
  },
  data() {
    return {
      treeLoading: false,
      tableLoading: false,
      generateLoading: false,
      datasourceVisible: false,
      selectTableDialogVisible: false,
      disabledGenerateBtn: true,
      baseUrl: this.global.baseUrl,
      filterText: "",
      selectTableData: null,
      tableModel: {},
      generateModel: {
        basePackage:'',
        outPutFolderPath:'',
        connParam:null,
        tableModels:null
      },
      treeData: null,
      selectTableColumns: [
        {
          prop: "name",
          label: "名称"
        },
        {
          prop: "description",
          label: "描述"
        }
      ]
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  methods: {
    // 打开数据源配置界面
    configDatasource() {
      this.datasourceVisible = true
      this.$nextTick(() => {
        this.$refs.datasourceDialog.init()
      })
    },
    // 过滤表显示
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    // 获取要生成的表
    getTables() {
      let dsStr = localStorage.getItem("datasource")
      if (dsStr == null) {
        this.$message({ message: '请先配置数据源', type: 'error' })
        return
      }
      this.tableLoading = true
      dsStr = localStorage.getItem("datasource")
      axios.post(this.baseUrl + "/getTables", JSON.parse(dsStr)).then(res => {
        res = res.data
        if (res.code == 200) {
          this.selectTableDialogVisible = true
          this.selectTableData = res.data
          this.$nextTick(() => {
            this.$refs.selectTableDialog.init()
          })
        } else {
          this.$message({ message: res.msg, type: "error" })
        }
        this.tableLoading = false
      })
    },
    // 选择要生成的表
    tableSelectionChange(selections) {
      this.treeLoading = true
      let dsStr = localStorage.getItem("datasource")
      let params = {
        connParam: JSON.parse(dsStr),
        tableModels: selections
      };
      axios.post(this.baseUrl + "/getGenerateModel", params).then(res => {
        res = res.data
        if (res.code == 200) {
          this.generateModel = res.data
          this.treeData = this.generateModel.tableModels
          this.generateModel.outPutFolderPath = 'C:\\kitty'
          this.disabledGenerateBtn = false
        } else {
          this.$message({ message: res.msg, type: "error" })
        }
        this.treeLoading = false
      })
    },
    // 选择查看表信息
    selectTableChange(data) {
      this.tableModel = data
    },
    // 选择代码输出目录
    chooseBasePackage() {
      this.generateModel.basePackage = 'com.louis.kitty'
    },
    // 选择代码输出目录
    chooseOutputFolder() {
      this.generateModel.outPutFolderPath = '/home/genCode'
    },
    // 生成代码
    generateCode() {
      this.generateLoading = true;
      axios.post(this.baseUrl + "/generateModels", this.generateModel).then(res => {
        res = res.data
        if (res.code == 200) {
          this.$message({ message: '代码生成完成', type: 'success' })
        } else {
          this.$message({ message: res.msg, type: "error" })
        }
        this.generateLoading = false
      })
    },

     downloadFile() {
          axios.post(this.baseUrl + "/downLoad", this.generateModel).then(res => {
                    let url = res.request.responseURL;
                    window.open(url);
          })
        }


  }
}
</script>

<style>
.page-container {
  position: absolute;
  top: 35px;
  bottom: 5px;
  width: 99%;
  padding: 4px;
  /* background-color: rgb(21, 41, 97); */
}
.left-container {
  padding: 4px;
  float: left;
  width: 20%;
  height: 100%;
}
.right-container {
  padding-top: 1px;
  padding-bottom: 6px;
  left: 200px;
  float: right;
  width: 78%;
  height: 100%;
}
.page-container > div {
  border-color: rgba(173, 180, 180, 0.5);
  border-width: 1px;
  border-style: solid;
}
.base-info,
.column-info,
.option-info {
  padding-top: 15px;
  border-color: rgba(173, 180, 180, 0.2);
  border-width: 1px;
  border-style: solid;
}
.base-info,
.column-info,
.option-info {
  margin: 5px;
}
.tableForm,
.optionForm {
  text-align: left;
}
.tree {
  padding-top: 10px;
}
</style>
