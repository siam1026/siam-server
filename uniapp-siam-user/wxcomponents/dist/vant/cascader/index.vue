<template>
<uni-shadow-root class="dist-vant-cascader-index"><view v-if="showHeader" class="van-cascader__header">
  <text class="van-cascader__title"><slot name="title"></slot>{{ title }}</text>
  <van-icon v-if="closeable" :name="closeIcon" class="van-cascader__close-icon" @click.native="onClose"></van-icon>
</view>

<van-tabs :active="activeTab" custom-class="van-cascader__tabs" wrap-class="van-cascader__tabs-wrap" tab-class="van-cascader__tab" :color="activeColor" :border="false" :swipeable="swipeable" @click="onClickTab">
  <van-tab v-for="(tab,tabIndex) in (tabs)" :key="tab.tabIndex" :title="tab.selected ? tab.selected[textKey] : placeholder" style="width: 100%;" :title-style="(!tab.selected ? 'color: #969799;font-weight:normal;' : '')">
    
    

    <view class="van-cascader__options">
      <view v-for="(option,index) in (tab.options)" :key="option.index" :class="(option.className)+' '+(utils.optionClass(tab, valueKey, option))" :style="utils.optionStyle({ tab, valueKey, option, activeColor })" :data-option="option" :data-tab-index="tabIndex" @click="onSelect">
        <text>{{ option[textKey] }}</text>
        <van-icon v-if="utils.isSelected(tab, valueKey, option)" name="success" size="18"></van-icon>
      </view>
    </view>
    
    
  </van-tab>
</van-tabs></uni-shadow-root>
</template>
<wxs src="./index.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanTab from '../tab/index.vue'
import VanTabs from '../tabs/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-tab': VanTab,'van-tabs': VanTabs}}

global['__wxRoute'] = 'dist/vant/cascader/index'
import { VantComponent } from '../common/component';
var FieldName;
(function (FieldName) {
    FieldName["TEXT"] = "text";
    FieldName["VALUE"] = "value";
    FieldName["CHILDREN"] = "children";
})(FieldName || (FieldName = {}));
const defaultFieldNames = {
    text: FieldName.TEXT,
    value: FieldName.VALUE,
    children: FieldName.CHILDREN,
};
VantComponent({
    props: {
        title: String,
        value: {
            type: String,
        },
        placeholder: {
            type: String,
            value: '请选择',
        },
        activeColor: {
            type: String,
            value: '#1989fa',
        },
        options: {
            type: Array,
            value: [],
        },
        swipeable: {
            type: Boolean,
            value: false,
        },
        closeable: {
            type: Boolean,
            value: true,
        },
        showHeader: {
            type: Boolean,
            value: true,
        },
        closeIcon: {
            type: String,
            value: 'cross',
        },
        fieldNames: {
            type: Object,
            value: defaultFieldNames,
            observer: 'updateFieldNames',
        },
    },
    data: {
        tabs: [],
        activeTab: 0,
        textKey: FieldName.TEXT,
        valueKey: FieldName.VALUE,
        childrenKey: FieldName.CHILDREN,
        innerValue: '',
    },
    watch: {
        options() {
            this.updateTabs();
        },
        value(newVal) {
            this.updateValue(newVal);
        },
    },
    created() {
        this.updateTabs();
    },
    methods: {
        updateValue(val) {
            if (val !== undefined) {
                const values = this.data.tabs.map((tab) => tab.selected && tab.selected[this.data.valueKey]);
                if (values.indexOf(val) > -1) {
                    return;
                }
            }
            this.innerValue = val;
            this.updateTabs();
        },
        updateFieldNames() {
            const { text = 'text', value = 'value', children = 'children', } = this.data.fieldNames || defaultFieldNames;
            this.setData({
                textKey: text,
                valueKey: value,
                childrenKey: children,
            });
        },
        getSelectedOptionsByValue(options, value) {
            for (let i = 0; i < options.length; i++) {
                const option = options[i];
                if (option[this.data.valueKey] === value) {
                    return [option];
                }
                if (option[this.data.childrenKey]) {
                    const selectedOptions = this.getSelectedOptionsByValue(option[this.data.childrenKey], value);
                    if (selectedOptions) {
                        return [option, ...selectedOptions];
                    }
                }
            }
        },
        updateTabs() {
            const { options } = this.data;
            const { innerValue } = this;
            if (!options.length) {
                return;
            }
            if (innerValue !== undefined) {
                const selectedOptions = this.getSelectedOptionsByValue(options, innerValue);
                if (selectedOptions) {
                    let optionsCursor = options;
                    const tabs = selectedOptions.map((option) => {
                        const tab = {
                            options: optionsCursor,
                            selected: option,
                        };
                        const next = optionsCursor.find((item) => item[this.data.valueKey] === option[this.data.valueKey]);
                        if (next) {
                            optionsCursor = next[this.data.childrenKey];
                        }
                        return tab;
                    });
                    if (optionsCursor) {
                        tabs.push({
                            options: optionsCursor,
                            selected: null,
                        });
                    }
                    this.setData({
                        tabs,
                    });
                    wx.nextTick(() => {
                        this.setData({
                            activeTab: tabs.length - 1,
                        });
                    });
                    return;
                }
            }
            this.setData({
                tabs: [
                    {
                        options,
                        selected: null,
                    },
                ],
            });
        },
        onClose() {
            this.$emit('close');
        },
        onClickTab(e) {
            const { index: tabIndex, title } = e.detail;
            this.$emit('click-tab', { title, tabIndex });
            this.setData({
                activeTab: tabIndex,
            });
        },
        // 选中
        onSelect(e) {
            const { option, tabIndex } = e.currentTarget.dataset;
            if (option && option.disabled) {
                return;
            }
            const { valueKey, childrenKey } = this.data;
            let { tabs } = this.data;
            tabs[tabIndex].selected = option;
            if (tabs.length > tabIndex + 1) {
                tabs = tabs.slice(0, tabIndex + 1);
            }
            if (option[childrenKey]) {
                const nextTab = {
                    options: option[childrenKey],
                    selected: null,
                };
                if (tabs[tabIndex + 1]) {
                    tabs[tabIndex + 1] = nextTab;
                }
                else {
                    tabs.push(nextTab);
                }
                wx.nextTick(() => {
                    this.setData({
                        activeTab: tabIndex + 1,
                    });
                });
            }
            this.setData({
                tabs,
            });
            const selectedOptions = tabs.map((tab) => tab.selected).filter(Boolean);
            const value = option[valueKey];
            const params = {
                value,
                tabIndex,
                selectedOptions,
            };
            this.innerValue = value;
            this.$emit('change', params);
            if (!option[childrenKey]) {
                this.$emit('finish', params);
            }
        },
    },
});
export default global['__wxComponents']['dist/vant/cascader/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-cascader__header{align-items:center;display:flex;height:48px;justify-content:space-between;padding:0 16px}.van-cascader__title{font-size:16px;font-weight:600;line-height:20px}.van-cascader__close-icon{color:#c8c9cc;font-size:22px;height:22px}.van-cascader__tabs-wrap{height:48px!important;padding:0 8px}.van-cascader__tab{color:#323233!important;flex:none!important;font-weight:600!important;padding:0 8px!important}.van-cascader__tab--unselected{color:#969799!important;font-weight:400!important}.van-cascader__option{align-items:center;cursor:pointer;display:flex;font-size:14px;justify-content:space-between;line-height:20px;padding:10px 16px}.van-cascader__option:active{background-color:#f2f3f5}.van-cascader__option--selected{color:#1989fa;font-weight:600}.van-cascader__option--disabled{color:#c8c9cc;cursor:not-allowed}.van-cascader__option--disabled:active{background-color:initial}.van-cascader__options{-webkit-overflow-scrolling:touch;box-sizing:border-box;height:384px;overflow-y:auto;padding-top:6px}
</style>