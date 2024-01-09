/**
 * 全局常量、方法封装模块
 * 通过原型挂载到Vue属性
 * 通过 this.Global 调用
 */

 // 后台接口服务器地址
export const baseUrl = 'http://localhost:9000'
// export const baseUrl = 'http://localhost:8080/kitty-generator'

export default {
    baseUrl
}