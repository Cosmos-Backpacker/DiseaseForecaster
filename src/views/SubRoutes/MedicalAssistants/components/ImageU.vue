<script setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
import ocrAPI from "@/apis/xfModelOCR.js";
import {useUserStore} from "@/stores/userStore.js";
import imageUAPI from "@/apis/xfImageU.js";

const question = ref("");
const answer=ref("")
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


async function xfImageU(userID, question) {
  if (userID === undefined) {
    ElMessage({type: "error", message: "请先登录"})
    router.replace('/login');
  }
  if (question === "") {
    ElMessage({type: "error", message: "请输入问题"})
  }

  // 这里实现上传文件的逻辑
  // 检查文件是否有效
  if (file) {
    //将file封装成formData作为参数传送给后端
    const formData = new FormData();
    formData.append('file', file);
    const res = await imageUAPI(formData, userID, question)
    answer.value = res.data
    //对答案格式进行处理
    answer.value=answer.value.replace(/\n/gm,"<br>")

    console.log(res.data)
  } else {
    console.error('Please select a file first.');
  }

  //上传之后将问题和图片清空
  question = ""

}



</script>




<template>
  <div>
    <div>上传图片进行分析</div>
    <input type="file" id="fileInput" @change="handleFileSelect">
    <el-input v-model="question" @keyup.enter="xfImageU(userID,question)"
              placeholder="请输入您的问题..."></el-input>
    <el-button @click="xfImageU(userID,question)">确定上传</el-button>
  </div>


  <div class="content">
    <div v-html="answer"></div>

  </div>


</template>




<style scoped>



</style>