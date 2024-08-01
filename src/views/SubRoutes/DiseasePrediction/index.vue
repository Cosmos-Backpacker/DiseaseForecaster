<template>
  <div class="mybox">
    <!-- 占空间的盒子-->
    <div style="height: 20px"/>



    <div class="box">

      <el-button plain @click="dialogTableVisible = true">
        累计数据
      </el-button>

      <el-button plain @click="dialogFormVisible = true">
        填写表单
      </el-button>

      <el-button plain @click="dialogFormVisibleJian = true">
        简略版
      </el-button>

      <el-button @click="download(userID)">
        下载检测报告
      </el-button>




      <el-dialog v-model="dialogTableVisible" title="累计记录" width="800">
        <!--         统计组件-->
        <!--              <statistics></statistics>-->
      </el-dialog>

      <!--    对话框-->
      <el-dialog v-model="dialogFormVisible" title="预测表单" width="500">
        <!--   新增计划表格   -->
        <heart-form></heart-form>
      </el-dialog>


      <el-dialog v-model="dialogFormVisibleJian" title="预测表单简略版" width="500">

        <!--   新增计划表格   -->
        <user-heart-form></user-heart-form>
      </el-dialog>

      <!--下面累计分页展示表格-->
      <div style="height: 20px"></div>
      <!--    <pagination></pagination>-->

    </div>
  </div>

</template>


<script setup>


import heartForm from './components/heartForm.vue'
import {ref} from 'vue'
import userHeartForm from './components/userHeartForm.vue'
import heartPdfAPI from "@/apis/heartPdf.js";
import imageUAPI from "@/apis/xfImageU.js";
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";
import {ElMessage} from "element-plus";



const userStore = useUserStore()

//用于存储文件
let file = null;
//用于存储图片理解返回的答案
let answer = ref("")
//获取用户ID
const userID = userStore.userInfo.id;



const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
const dialogFormVisibleJian = ref(false)


//下载检测报告PDF的函数
const download = async (id) => {
  if (id === undefined) {
    ElMessage({type: "error", message: "请先登录"})
    router.replace('/login');
  }
  await heartPdfAPI(id)
}







</script>


<style>
.mybox {
  height: 100%;
  background-color: #cccccc;
}

.box {
  height: 400px;
}


.content {
  height: 340px;
  overflow-y: scroll;
  font-size: 14px;
  width: auto;
}
</style>