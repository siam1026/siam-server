<template>
	<!--数据源配置界面-->
	<el-dialog :title="$t('common.datasource')" width="40%" :visible.sync="visible" :close-on-click-modal="false"
        :before-close="handleClose" size="small" top="5vh">
        <el-form ref="form" :model="form" label-width="80px" size="small">
            <el-form-item label="类型">
                <el-select v-model="form.dbType" placeholder="请选择数据库类型" style="width: 100%;">
                <el-option label="MySQL" value="MySQL"></el-option>
                <el-option label="Oracle" value="Oracle"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="主机">
                <el-input v-model="form.host"></el-input>
            </el-form-item>
            <el-form-item label="端口">
                <el-input v-model="form.port" type="number"></el-input>
            </el-form-item>
            <el-form-item label="用户">
                <el-input v-model="form.userName"></el-input>
            </el-form-item>
            <el-form-item label="密码">
                <el-input v-model="form.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="数据库">
                <el-input v-model="form.dbName"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button size="small"  @click="visible = false">{{$t('action.cancel')}}</el-button>
            <el-button size="small"  type="primary" :loading="testLoading" @click="testConn">{{$t('action.test')}}</el-button>
            <el-button size="small"  type="primary" :loading="saveLoading" @click="saveConfig">{{$t('action.save')}}</el-button>
        </span>
	</el-dialog>
</template>

<script>
import axios from 'axios'
export default {
	data() {
		return {
            form: {
                dbType: 'MySQL',
                host: '127.0.0.1',
                port: 3306,
                userName: 'root',
                password: '123456',
                dbName: 'kitty'
            },
            visible: true,
            testLoading: false,
            saveLoading: false,
            baseUrl: this.global.baseUrl
		}
	},
	methods: {
        init : function () {
            this.visible = true
        },
        // 加载配置
		loadConfig: function () {
            let dsStr = localStorage.getItem('datasource')
            if(dsStr != null) {
                // 如果本地存储有数据源配置，则加载
                this.form = JSON.parse(dsStr);
            }
		},
		// 保存配置
		saveConfig: function () {
            this.saveLoading = true
            localStorage.setItem('datasource', JSON.stringify(this.form))
            let dsStr = localStorage.getItem('datasource')
            if(dsStr != null) {
                this.$message({ message: '保存成功', type: 'success' })
                this.visible = false
            } else {
                this.$message({message: '保存失败, ' + res.msg, type: 'error'})
            }
            this.saveLoading = false
		},
		// 测试连接
		testConn: function () {
            this.testLoading = true
            axios.post(this.baseUrl + '/testConnection', this.form).then((res) => {
                res = res.data
                if(res.code == 200) {
                    this.$message({ message: '测试连接成功', type: 'success' })
                } else {
                    this.$message({message: res.msg, type: 'error'})
                }
                this.testLoading = false
			})
		},
        handleClose(done) {
            this.visible = false
        }
	},
	mounted() {
		this.loadConfig()
	}
}
</script>

<style scoped>

</style>