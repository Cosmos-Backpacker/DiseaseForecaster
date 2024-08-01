<template>
  <div>
    我是疾病查询页面
  </div>
  <el-input v-model="question">请输入想要查询的疾病信息</el-input>
  <el-button @click="Inquire(question)">执行查询</el-button>

  <div v-html="answer"></div>

</template>
<script setup>

import DiseaseInquiry from "@/apis/DiseaseInquire.js";

let answer = ref("")
const question=ref("");

async function Inquire(question) {
  const res = await DiseaseInquiry(question)

  answer.value = res.data
  console.log(answer)
  answer.value = answer.value.replace(/\n/gm, "<br>");
}

</script>

<style>

</style>