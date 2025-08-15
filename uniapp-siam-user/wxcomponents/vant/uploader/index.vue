<template>
<uni-shadow-root class="vant-uploader-index"><view class="van-uploader">
  <view class="van-uploader__wrapper">
    
    <view v-for="(item,index) in (lists)" :key="item.index" v-if="previewImage" class="van-uploader__preview">
      <image v-if="item.isImage" :mode="imageFit" :src="item.url || item.path" :alt="item.name || ('图片' + index)" class="van-uploader__preview-image" :style="'width: '+(utils.addUnit(previewSize))+'; height: '+(utils.addUnit(previewSize))+';'" :data-index="index" @click="onPreviewImage"></image>
      <view v-else class="van-uploader__file" :style="'width: '+(utils.addUnit(previewSize))+'; height: '+(utils.addUnit(previewSize))+';'">
        <van-icon name="description" class="van-uploader__file-icon"></van-icon>
        <view class="van-uploader__file-name van-ellipsis">{{ item.name || item.url || item.path }}</view>
      </view>
      <view v-if="item.status === 'uploading' || item.status === 'failed'" class="van-uploader__mask">
       <van-icon v-if="item.status === 'failed'" name="warning-o" class="van-uploader__mask-icon"></van-icon>
       <van-loading v-else class="van-uploader__loading"></van-loading>
       <text v-if="item.message" class="van-uploader__upload-text">{{ item.message }}</text>
      </view>
      <van-icon v-if="deletable" name="clear" class="van-uploader__preview-delete" :data-index="index" @click.native="deleteItem"></van-icon>
    </view>

    
    <block v-if="isInCount">
      <view class="van-uploader__slot" @click="startUpload">
        <slot></slot>
      </view>

      
      <view v-if="showUpload" :class="'van-uploader__upload '+(disabled ? 'van-uploader__upload--disabled': '')" :style="'width: '+(utils.addUnit(previewSize))+'; height: '+(utils.addUnit(previewSize))+';'" @click="startUpload">
        <van-icon :name="uploadIcon" class="van-uploader__upload-icon"></van-icon>
        <text v-if="uploadText" class="van-uploader__upload-text">{{ uploadText }}</text>
      </view>
    </block>
  </view>
</view></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>
import VanIcon from '../icon/index.vue'
import VanLoading from '../loading/index.vue'
global['__wxVueOptions'] = {components:{'van-icon': VanIcon,'van-loading': VanLoading}}

global['__wxRoute'] = 'vant/uploader/index'
"use strict";
var __assign = (this && this.__assign) || function () {
    __assign = Object.assign || function(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p))
                t[p] = s[p];
        }
        return t;
    };
    return __assign.apply(this, arguments);
};
Object.defineProperty(exports, "__esModule", { value: true });
var component_1 = require("../common/component");
var utils_1 = require("./utils");
var shared_1 = require("./shared");
component_1.VantComponent({
    props: __assign(__assign({ disabled: Boolean, multiple: Boolean, uploadText: String, useBeforeRead: Boolean, afterRead: null, beforeRead: null, previewSize: {
            type: null,
            value: 90
        }, name: {
            type: [Number, String],
            value: ''
        }, accept: {
            type: String,
            value: 'image'
        }, fileList: {
            type: Array,
            value: [],
            observer: 'formatFileList'
        }, maxSize: {
            type: Number,
            value: Number.MAX_VALUE
        }, maxCount: {
            type: Number,
            value: 100
        }, deletable: {
            type: Boolean,
            value: true
        }, showUpload: {
            type: Boolean,
            value: true
        }, previewImage: {
            type: Boolean,
            value: true
        }, previewFullImage: {
            type: Boolean,
            value: true
        }, imageFit: {
            type: String,
            value: 'scaleToFill'
        }, uploadIcon: {
            type: String,
            value: 'photograph'
        } }, shared_1.chooseImageProps), shared_1.chooseVideoProps),
    data: {
        lists: [],
        isInCount: true
    },
    methods: {
        formatFileList: function () {
            var _a = this.data, _b = _a.fileList, fileList = _b === void 0 ? [] : _b, maxCount = _a.maxCount;
            var lists = fileList.map(function (item) { return (__assign(__assign({}, item), { isImage: typeof item.isImage === 'undefined' ? utils_1.isImageFile(item) : item.isImage })); });
            this.setData({ lists: lists, isInCount: lists.length < maxCount });
        },
        getDetail: function (index) {
            return {
                name: this.data.name,
                index: index == null ? this.data.fileList.length : index
            };
        },
        startUpload: function () {
            var _this = this;
            var _a = this.data, maxCount = _a.maxCount, multiple = _a.multiple, accept = _a.accept, lists = _a.lists, disabled = _a.disabled;
            if (disabled)
                return;
            utils_1.chooseFile(__assign(__assign({}, this.data), { maxCount: maxCount - lists.length }))
                .then(function (res) {
                var file = null;
                if (utils_1.isVideo(res, accept)) {
                    file = __assign({ path: res.tempFilePath }, res);
                }
                else {
                    file = multiple ? res.tempFiles : res.tempFiles[0];
                }
                _this.onBeforeRead(file);
            })
                .catch(function (error) {
                _this.$emit('error', error);
            });
        },
        onBeforeRead: function (file) {
            var _this = this;
            var _a = this.data, beforeRead = _a.beforeRead, useBeforeRead = _a.useBeforeRead;
            var res = true;
            if (typeof beforeRead === 'function') {
                res = beforeRead(file, this.getDetail());
            }
            if (useBeforeRead) {
                res = new Promise(function (resolve, reject) {
                    _this.$emit('before-read', __assign(__assign({ file: file }, _this.getDetail()), { callback: function (ok) {
                            ok ? resolve() : reject();
                        } }));
                });
            }
            if (!res) {
                return;
            }
            if (utils_1.isPromise(res)) {
                res.then(function (data) { return _this.onAfterRead(data || file); });
            }
            else {
                this.onAfterRead(file);
            }
        },
        onAfterRead: function (file) {
            var maxSize = this.data.maxSize;
            var oversize = Array.isArray(file)
                ? file.some(function (item) { return item.size > maxSize; })
                : file.size > maxSize;
            if (oversize) {
                this.$emit('oversize', __assign({ file: file }, this.getDetail()));
                return;
            }
            if (typeof this.data.afterRead === 'function') {
                this.data.afterRead(file, this.getDetail());
            }
            this.$emit('after-read', __assign({ file: file }, this.getDetail()));
        },
        deleteItem: function (event) {
            var index = event.currentTarget.dataset.index;
            this.$emit('delete', __assign(__assign({}, this.getDetail(index)), { file: this.data.fileList[index] }));
        },
        onPreviewImage: function (event) {
            var index = event.currentTarget.dataset.index;
            var lists = this.data.lists;
            var item = lists[index];
            this.$emit('click-preview', __assign({ url: item.url || item.path }, this.getDetail(index)));
            if (!this.data.previewFullImage)
                return;
            wx.previewImage({
                urls: lists
                    .filter(function (item) { return item.isImage; })
                    .map(function (item) { return item.url || item.path; }),
                current: item.url || item.path,
                fail: function () {
                    wx.showToast({ title: '预览图片失败', icon: 'none' });
                }
            });
        }
    }
});
export default global['__wxComponents']['vant/uploader/index']
</script>
<style platform="mp-weixin">
@import '../common/index.css';.van-uploader{position:relative;display:inline-block}.van-uploader__wrapper{display:-webkit-flex;display:flex;-webkit-flex-wrap:wrap;flex-wrap:wrap}.van-uploader__slot:empty{display:none}.van-uploader__slot:not(:empty)+.van-uploader__upload{display:none!important}.van-uploader__upload{position:relative;display:-webkit-flex;display:flex;-webkit-flex-direction:column;flex-direction:column;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;box-sizing:border-box;width:80px;height:80px;margin:0 8px 8px 0;background-color:#f7f8fa;border-radius:8px}.van-uploader__upload:active{background-color:#f2f3f5}.van-uploader__upload-icon{color:#dcdee0;font-size:24px}.van-uploader__upload-text{margin-top:8px;color:#969799;font-size:12px}.van-uploader__upload--disabled{opacity:.5;opacity:var(--uploader-disabled-opacity,.5)}.van-uploader__preview{position:relative;margin:0 8px 8px 0;cursor:pointer}.van-uploader__preview-image{display:block;width:80px;height:80px;overflow:hidden;border-radius:8px}.van-uploader__preview-delete{position:absolute;top:-8px;right:-8px;color:#969799;font-size:18px;background-color:#fff;border-radius:100%}.van-uploader__file{display:-webkit-flex;display:flex;-webkit-flex-direction:column;flex-direction:column;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;width:80px;height:80px;background-color:#f7f8fa;border-radius:8px}.van-uploader__file-icon{color:#646566;font-size:20px}.van-uploader__file-name{box-sizing:border-box;width:100%;margin-top:8px;padding:0 4px;color:#646566;font-size:12px;text-align:center}.van-uploader__mask{position:absolute;top:0;right:0;bottom:0;left:0;display:-webkit-flex;display:flex;-webkit-flex-direction:column;flex-direction:column;-webkit-align-items:center;align-items:center;-webkit-justify-content:center;justify-content:center;color:#fff;background-color:rgba(50,50,51,.88);border-radius:8px}.van-uploader__mask-icon{font-size:22px}.van-uploader__mask-message{margin-top:6px;padding:0 4px;font-size:12px;line-height:14px}.van-uploader__loading{width:22px;height:22px;color:#fff}
</style>