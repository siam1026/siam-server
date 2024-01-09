<template>
	<!--表格选择数据界面-->
	<el-dialog :title="title" width="40%" :visible.sync="visible" :close-on-click-modal="false"
        :before-close="handleClose" size="small" top="5vh">
        <el-table :data="data" class="right-table" size="small" max-height="420"
            :show-header="showHeader" @selection-change="handleSelectionChange" >
            <el-table-column type="selection"></el-table-column>
            <el-table-column v-for="column in columns" :key="column.id"
                :prop="column.prop" :label="column.label" :width="column.width">
            </el-table-column>
        </el-table>
        <span slot="footer" class="dialog-footer">
            <el-button size="small"  @click="visible = false">{{$t('action.cancel')}}</el-button>
            <el-button size="small"  type="primary" @click="comfirm">{{$t('action.comfirm')}}</el-button>
        </span>
	</el-dialog>
</template>

<script>
export default {
    props: {
        title: {
            type: String
        },
        showHeader: {
            type: Boolean
        },
        data: {
            type: Array,
            required: true
        },
        columns: {
            type: Array,
            required: true
        }
    },
	data() {
		return {
            visible: true,
            selections: []  // 列表选中列
		}
	},
	methods: {
        // 确定选择
		comfirm() {
            this.$emit('selectionChange', this.selections)
            this.visible = false
        },
        // 选择切换
        handleSelectionChange(selections) {
            this.selections = selections
        },
        // 初始化对话框
        init() {
            this.visible = true
        },
        // 关闭对话框
        handleClose(done) {
            this.visible = false
        }
	}
}
</script>

<style scoped>

</style>