<template>
  <div class="page">
    <h2>Home Page</h2>
    <el-button type="primary" @click="testAxios()">测试axios调用</el-button>
  </div>
</template>

<script>
import axios from 'axios'
import mock from '@/mock/mock.js'

export default {
  name: 'Home',
  methods: {
      testAxios() {
          axios.get('http://localhost:9000/getTables')
          .then(function(res) {
              alert(JSON.stringify(res.data))
          }).catch(function(res){
              alert(res)
          })
      }
  }
}
</script>