<script setup>
import {ref} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
import ocrAPI from "@/apis/xfModelOCR.js";
import {useUserStore} from "@/stores/userStore.js";
import { UploadFilled } from '@element-plus/icons-vue'

const ocrText = ref("")
const userStore = useUserStore()
let myfile = null;
const fileInput = ref(null);
const userID = userStore.userInfo.id
let fileList = ref([]);


async function handleFileSelect(event) {


  console.log('handleFileSelect没有调用', event);

  //获取文件
  myfile=event.raw;
  console.log('myflie内容是',myfile)
}

function changeFile(file) {
  console.log('changeFile没有调用', file);
  myfile = file;
  return false;
}


async function xfOCR(userID) {

  console.log('检查一下',myfile)

  if (userID === undefined) {
    ElMessage({type: "error", message: "请先登录"})
    router.replace('/login');
  }
  const formData = new FormData()
  formData.append('file', myfile);
  console.log(myfile)
  const res = await ocrAPI(formData)
  ocrText.value = res.data
  //对答案格式进行处理
  ocrText.value = ocrText.value.replace(/\n/gm, "<br>")
}


</script>


<template>

  <div>
    <el-upload
        action=""
        :auto-upload="false"
        class="upload-demo"
        drag
        multiple
        :on-change="handleFileSelect"
        :before-upload="changeFile"
    >

      <el-icon class="el-icon--upload">
        <upload-filled/>
      </el-icon>
      <div class="el-upload__text">
        Drop file here or <em>click to upload</em>
      </div>
      <template #tip>
        <div class="el-upload__tip">
          jpg/png photo with a size less than 500kb
        </div>
      </template>
    </el-upload>

<!--    <input type="file" id="fileInput" @change="handleFileSelect">-->
    <el-button @click="xfOCR(userID)">上传识别</el-button>


  </div>


  <div class="content">
    <div v-html="ocrText"></div>
  </div>

</template>


<style scoped>

</style>