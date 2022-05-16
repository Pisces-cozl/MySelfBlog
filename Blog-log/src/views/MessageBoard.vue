<!--
 * @Author: your name
 * @Date: 2021-06-06 21:52:38
 * @LastEditTime: 2022-04-22 10:35:49
 * @LastEditors: your name
 * @Description: 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 * @FilePath: \补充视频对应的前端-开发慎用-前端有改动blog-app\src\views\MessageBoard.vue
-->
<template>

  <div class="me-message" v-title :data-title="title">
    <el-container class="">
      <el-main class="me-main">
        <el-alert
          title="不能留言哦。。。。。。。。。。。"
          type="warning"
          center
          show-icon>
        </el-alert>

      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: 'MessageBoard',
    data() {
      return {
      }
    },
    computed: {
      title (){
        return '留言板 - BUG神之路'
      }
    }
  }
</script>

<style scoped>
  .me-message {
  }

  .el-container {
    width: 700px;
  }

  .me-main {
    overflow: hidden;
  }

  .me-log-box {
    margin-left: 30%;
    margin-top: 20px;

  }

</style>
