<template>
<uni-shadow-root class="dist-vant-field-input"><input :id="name" :class="(utils.bem('field__control', [inputAlign, { disabled, error }]))+' input-class'" :type="type" :focus="focus" :cursor="cursor" :value="innerValue" :auto-focus="autoFocus" :disabled="disabled || readonly" :maxlength="maxlength" :placeholder="placeholder" :placeholder-style="placeholderStyle" :placeholder-class="utils.bem('field__placeholder', { error })" :confirm-type="confirmType" :confirm-hold="confirmHold" :hold-keyboard="holdKeyboard" :cursor-spacing="cursorSpacing" :adjust-position="adjustPosition" :selection-end="selectionEnd" :selection-start="selectionStart" :always-embed="alwaysEmbed" :password="password || type === 'password'" @input="onInput" @click="onClickInput" @blur="onBlur" @focus="onFocus" @confirm="onConfirm" @keyboardheightchange="onKeyboardHeightChange"></input></uni-shadow-root>
</template>
<wxs src="../wxs/utils.wxs" module="utils"></wxs>
<script>

global['__wxRoute'] = 'dist/vant/field/input'

Component({})

export default global['__wxComponents']['dist/vant/field/input']
</script>
<style platform="mp-weixin">

</style>