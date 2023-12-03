import axios from 'axios';
import config from '../../config/index.js';

const qs = require('qs');

if (process.env.NODE_ENV === "production") {
  axios.defaults.baseURL = config.appConfig.http.baseUrl;
}
else if (process.env.NODE_ENV === "production_test") {
  axios.defaults.baseURL = config.appConfig.http.baseUrl4Test;
}
else {
  axios.defaults.baseURL = config.appConfig.http.developmentBaseUrl;
}

axios.defaults.withCredentials = true;
axios.defaults.method = 'post';
axios.defaults.headers = {'Content-Type': 'application/x-www-form-urlencoded'};

function getUrl() {
  return axios.defaults.baseURL;
}


// 请求: success是指data中result==1时的逻辑。（参数：vue，data（返回json））
// fail在默认基础上追加，同时也出现在result!=1时的逻辑
function post(vue, url, params, success, fail) {

  // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    alert(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };

  // if(params.token === undefined){
  //   params.token=vue.$store.state.token;
  // }

  var headersValue = {headers : {"Authorization" : sessionStorage.getItem("token"), 'Content-Type': 'application/json'}};
  axios.post(url, params, headersValue)
    .then(function (response) {
      let data = response.data;
      dealData(vue,data,success,fail,params);
    })
    .catch(failInner);
}

function put(vue, url, params, success, fail) {
  // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    alert(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };

  // if(params.token === undefined){
  //   //params.token=vue.$store.state.token;
  //   params.token = sessionStorage.getItem("token");
  // }

  var headersValue = {headers : {"Authorization" : sessionStorage.getItem("token"), 'Content-Type': 'application/json'}};
  axios.put(url, params, headersValue)
    .then(function (response) {
      let data = response.data;
      dealData(vue,data,success,fail,params);
    })
    .catch(failInner);
}

//delete是标识符，不能作为命名字段
function _delete(vue, url, params, success, fail) {

  // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    alert(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };

  // if(params.token === undefined){
  //   //params.token=vue.$store.state.token;
  //   params.token = sessionStorage.getItem("token");
  // }

  var xdata = {data: params, headers : {"Authorization" : sessionStorage.getItem("token"), 'Content-Type': 'application/json'}};
  //console.log(xdata);
  //var headersValue = {headers : {"Authorization" : sessionStorage.getItem("token")}};
  //axios.delete(url, xdata, headersValue)
  axios.delete(url, xdata)
    .then(function (response) {
      let data = response.data;
      dealData(vue,data,success,fail,params);
    })
    .catch(failInner);
}

function get(vue, url, success, fail) {
  // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    console.log(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };
  axios.get(url)
    .then(function (response) {
      let data = response.data;
      dealData(vue,data,success,fail);
    })
    .catch(failInner);
}

// 处理数据返回后的错误的result
// function dealData(vue, data, success,fail,params) {
//   // const msg = vue.$message;
//   if (data.result === 1) {
//     success(vue, data);
//   }
//   else if (data.result === 2) {
//     // msg.error(data.message);
//     // vue.$store.commit('rmToken');
//     // 跳转到登录
//     if(params._cannot===false){

//     } else{
//       vue.$router.push({path:'/login'});
//     }

//   } else {
//     // msg.error(data.message);
//     // 处理错误逻辑
//     if(fail !== undefined){
//       fail(undefined,data);
//     }
//   }
// }
function dealData(vue, data, success,fail,params) {
  //alert(dealData);
  // const msg = vue.$message;
  if (data.success) {
    success(vue, data);
  }else if (data.code === 2) {
    // msg.error(data.message);
    // vue.$store.commit('rmToken');
    // 跳转到登录
    if(params._cannot===false){

    } else{
      vue.$router.push({path:'/login'});
    }
  } else {
    // msg.error(data.message);
    // 处理错误逻辑
    if(fail !== undefined){
      fail(undefined,data);
    }
  }
}
//上传和下载的错误处理的方法
// function dealpost(vue, data, success,fail) {
//   // const msg = vue.$message;
//   if (data.result === 1) {
//     success(vue, data);
//   }
//   else if (data.result === 2) {
//     // msg.error(data.message);
//     // vue.$store.commit('rmToken');
//     // 跳转到登录
//     vue.$router.push({path:'/login'});
//   } else {
//     // msg.error(data.message);
//     // 处理错误逻辑
//     if(fail !== undefined){
//       fail(vue,data);
//     }
//   }
// }
function dealpost(vue, data, success,fail) {
  //alert("dealpost");

  // const msg = vue.$message;
  if (data.success) {
    success(vue, data);
  }else if (data.code === 2) {
    // msg.error(data.message);
    // vue.$store.commit('rmToken');
    // 跳转到登录
    vue.$router.push({path:'/login'});
  } else {
    // msg.error(data.message);
    // 处理错误逻辑
    if(fail !== undefined){
      fail(vue,data);
    }
  }
}

//上传图片
//添加一个发布和紧固件库--上传模板的调用方法;调取方法时直接传一个formData
// let formData = new FormData();
// formData.append('file', file,file.name);
// formData.append('type',type);
function postupload(vue, url,formData,success, fail) {
  // // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };

  //formData.get('token')的值为null，formdata.data.token的值为undefined
  if(formData.get('token') === null){ 
    formData.append('token', sessionStorage.getItem("token"));
  }

  let config = {
    headers: {"Authorization" : sessionStorage.getItem("token"), 'Content-Type': 'multipart/form-data'}
  };

  axios.post(url, formData,config).then(function (response) {
      let data = response.data;
      dealpost(vue,data,success,fail);
  }).catch(failInner);
}

//添加一个发布和后台品名属性值--上传模板的调用方法
function postsxupload(vue, url, file,params,success, fail) {
  // // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    console.log(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };
  let formData = new FormData();
  formData.append('file', file,file.name);
  formData.append('attributetblid',params);
  let config = {
    headers: {'Content-Type': 'multipart/form-data'}
  };
  axios.post(url, formData,config)
    .then(function (response) {
      let data = response.data;
      dealpost(vue,data,success,fail);
    })
    .catch(failInner);
}

//添加一个回访记录汇总-上传汇总记录的调用方法 （直接传入formData）dbh 如下：
// let formData = new FormData();
// formData.append('file', _this.file, _this.file.name)
// formData.append('id', _this.uploadData.merchantid)

function postup(vue, url, formData,success, fail) {
  // // 错误处理 默认方式
  let failInner = function (error) {
    // vue.$message.error("网络错误：" + error);
    console.log(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail(error);
    }
  };

  let config = {
    headers: {'Content-Type': 'multipart/form-data'}
  };
  axios.post(url, formData,config)
    .then(function (response) {
      let data = response.data;
      dealpost(vue,data,success,fail);
    })
    .catch(failInner);
}


//导出excel的方法定义
function getDownload(vue, url, params,filename, success, fail) {
  // 错误处理 默认方式
  let failInner = function (error) {
    vue.$message.error("网络错误：" + error);
    console.log(error);
    if(fail !== undefined){
      // 可处理额外的fail逻辑
      fail();
    }
  };
  params.token=vue.$store.state.token;
  axios({
    method: 'get',
    url: url,
    params:params, // 参数
    responseType: 'blob' // 表明返回服务器返回的数据类型
  }).then(function (response) {
    const blob = response.data;
    if ('download' in document.createElement('a')) { // 非IE下载
      const elink = document.createElement('a')
      elink.download = filename;
      elink.style.display = 'none'
      elink.href = URL.createObjectURL(blob)
      document.body.appendChild(elink)
      elink.click()
      URL.revokeObjectURL(elink.href) // 释放URL 对象
      document.body.removeChild(elink)
    } else { // IE10+下载
      navigator.msSaveBlob(blob, fileName)
    }
    success(vue);
  })
    .catch(failInner);
}

function postNoVue(url, params, success, fail) {
  alert("postNoVue");

  let failInner = function (error) {
    console.log(error);
    if(fail !== undefined){
      fail(error);
    }
  };
  axios.post(url, qs.stringify(params))
    .then(function (response) {
      let data = response.data;
      dealDataNoVue(data,success,fail,params);
    })
    .catch(failInner);
}

// function dealDataNoVue(data, success,fail,params) {
//   if (data.result === 1) {
//     success(data);
//   } else {
//     if(fail !== undefined){
//       fail(undefined,data);
//     }
//   }
// }
function dealDataNoVue(data, success,fail,params) {
  alert("dealDataNoVue");

  if (data.success) {
    success(data);
  } else {
    if(fail !== undefined){
      fail(undefined,data);
    }
  }
}
function publicUrl(path) {
  return config.appConfig.oss.domain+path;
}

export default {
  install: function(Vue, name = '$http') {
    Object.defineProperty(Vue.prototype, name, { value: this });
  },
  post: post,
  postNoVue:postNoVue,
  get:get,
  dealData: dealData,
  getUrl:getUrl,
  postupload:postupload,
  postsxupload:postsxupload,
  getDownload:getDownload,
  postup: postup,
  publicUrl: publicUrl,
  put: put,
  delete: _delete
}
