<script setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
import ocrAPI from "@/apis/xfModelOCR.js";
import {useUserStore} from "@/stores/userStore.js";

const ocrText=ref("")
const userStore = useUserStore()
let file = null;
const userID=userStore.userInfo.id
async function handleFileSelect(event) {

  if (event.target.files === undefined) {
    console.log("没有input")
  }
  //获取文件
  file = event.target.files [0]; // 获取选中的第一个文件
}

async function xfOCR(userID) {
  if (userID === undefined) {
    ElMessage({type: "error", message: "请先登录"})
    router.replace('/login');
  }

  const formData=new FormData()
  formData.append('file', file);

  const res=await ocrAPI(formData)
  ocrText.value=res.data
  //对答案格式进行处理
  ocrText.value=ocrText.value.replace(  /\n/gm,"<br>")


}


</script>

<template>
  <div>
    <input type="file" id="fileInput" @change="handleFileSelect">
    <el-button @click="xfOCR(userID)">上传识别</el-button>
  </div>
  <div class="content">
    <div v-html="ocrText"></div>
  </div>

</template>

<style scoped>

</style>