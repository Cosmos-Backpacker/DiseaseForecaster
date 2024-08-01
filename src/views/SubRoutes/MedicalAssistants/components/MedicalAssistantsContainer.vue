<template>
  <div>
    <div class="box">
      <div class="title">
        <img src="" alt class="logo"/>
        <span class="title-hn">医疗助手</span>
      </div>


      <div id="content" class="content">

        <div v-for="(item,index) in info" :key="index">
          <div class="info_r info_default" v-if="item.type === 'left info'">
            <!--            显示头像-->

            <div class="con_r con_text">
              <el-image style="width: 35px; height: 35px" src="src/assets/images/avatar/gpt图标.png" fit="fill"/>
              <!--              这个是回答显示地方-->
              <!--              这个是回答显示地方-->
              <!--              使用下面·这种渲染的方式展示内容，可以展示换行，前提是在js中替换了\n为<br>-->

              <div v-html="item.content"></div>
              <!--              这个是提示用户的提示框-->

              <div v-for="(item2,index) in item.question" :key="index">
                <div class="con_que" @click="clickQuestion(item2.content)">
                  <div class="czar-question-msg">
                    {{ item2.index }}
                    {{ item2.content }}
                  </div>
                </div>
              </div>


              <!--            显示时间-->
            </div>
            <div class="time_r">{{ item.time }}</div>
          </div>

          <div class="info_l" v-if="item.type === 'right info'">
            <div class="con_r con_text">
              <span class="con_l">{{ item.content }}</span>
              <el-image style="width: 35px;height: 35px" src="src/assets/images/avatar/user头像.png"
                        fit="fill"></el-image>
            </div>
            <div class="time_l">{{ item.time }}</div>
          </div>

        </div>

      </div>


      <div class="set problem">
        <!--        输入框中绑定了customerText这个内容-->
        <textarea
            placeholder="请输入您的问题..."
            style="height: 68px;width: 100%;resize:none;padding-right:80px;outline: none;border-color:#ccc;border-radius:5px;"
            v-model="customerText"
            @keyup.enter="sentMsg()"
            class="mo-textarea"
        >


        </textarea>
        <el-button @click="sentMsg()" class="buttons end">
          <span style="vertical-align: 4px;">发送</span>
        </el-button>

      </div>


    </div>

  </div>
</template>

<script setup>

import router from "@/router/index.js";
import {ElMessage} from "element-plus";
import {BigModel} from "@/apis/xfBigModel.js";
import {useUserStore} from "@/stores/userStore.js";

let customerText = ""

//获取userStore实例
const userStore = useUserStore()
//信息，用来存储历史对话内容，是一个列表里面放着Object对象
//获取当前用户id
const userID = userStore.userInfo.id

const info = ref([
  {
    type: 'left info',
    time: getTodayTime(),
    name: "robot",
    content: "您好，我是一个医疗专家，拥有多年的临床经验和医学研究背景。我专注于健康、医疗、饮食和锻炼等领域，致力于为人们提供最优质的健康建议和医疗服务。我可以帮助您解决各\n" +
        "种健康问题，例如制定个性化的饮食计划、推荐适合您的运动方式、诊断疾病并提供治疗方案等。无论您是需要改善身体状况还是预防疾病，我都可以为您提供专业的指导和支持。\n" +
        "此外，我不积极参与医学研究和学术交流，不断更新自己的知识和技能，以便更好地服务于广大患者和社会大众。如果您有任何健康方面的问题或雲求，欢仰随郁时向我咨询",
    question: [
      {id: 1, content: "如何预防心脏病？", index: 1},
      {id: 2, content: "如何通过饮食缓解糖尿病？", index: 2},
      {id: 3, content: "换季皮肤红肿出现红斑可能是因为什么？", index: 3},
      {id: 4, content: "婴儿发烧时应及时做什么措施？", index: 4},
      {id: 5, content: "经常熬夜吃垃圾食品的危害", index: 5}
    ],
    index: 0
  }
])


let timer = null


//发起请求的函数
function sentMsg() {
  clearTimeout(timer)
  //获取输入框的文本内容
  let text = customerText.trim()
  if (text !== '') {
    //创建用户贴呢的一个对象（right info代表在右边是用户）

    let obj = {
      type: 'right info',
      time: getTodayTime(),
      content: text,
      index: 1
    }

    //把对象放入历史集合中方便显示
    info.value.push(obj)

    //获取答案
    appendBigModelMsg(customerText)
    //清空提问内容
    customerText = ''

    nextTick(() => {
      var contentHeight = document.getElementById('content')
      contentHeight.scrollTop = contentHeight.scrollHeight
    })
  } else {
    ElMessage({type: "error", message: "问题不能为空"})
  }
}


//待会改成直接请求
async function appendBigModelMsg(question) {
  if (userID === undefined) {  //这里不能用null因为没有赋值，所以要用未被定义
    ElMessage({type: 'error', message: "请先登录"})
    router.replace('/login')
  } else {
    //调用函数清楚木目前保存的时间
    clearTimeout(timer)
    //整理一下问题
    question = question.trim()
    let answerText = ''

    //发起请求
    const res = await BigModel(userID, question)
    answerText = res.data
    answerText = answerText.replace(/\n/gm, "<br>");
    console.log(res.data)
    if (res.data != null) { //如果回答了
      //创建一个左边答案对象
      let obj = {
        type: "left info",
        time: getTodayTime(),
        name: "robot",
        content: answerText,
        question: [],
      }
      //放入历史对话集合中
      info.value.push(obj)
    } else {
      ElMessage({type: "error", message: "服务器未响应"})
    }
    nextTick(() => {
      let contentHeight = document.getElementById('content')
      contentHeight.scrollTop = contentHeight.scrollHeight
    })
  }
}


function clickQuestion(val) {
  sentMsgById(val)
}

//下面是方法函数
function sentMsgById(val) {
  clearTimeout(timer)

  let obj_r = {
    type: 'right info',
    time: getTodayTime(),
    name: 'robot',
    content: val,
    question: [],
  };
  info.value.push(obj_r)
  appendBigModelMsg(val)
  nextTick(() => {
    var contentHeight = document.getElementById('content')
    contentHeight.scrollTop = contentHeight.scrollHeight
  })
}


function getTodayTime() {
  // 获取当前时间
  var day = new Date()
  let seconds = day.getSeconds()
  if (seconds < 10) {
    seconds = "0" + seconds
  } else {
    seconds = seconds
  }
  let minutes = day.getMinutes()
  if (minutes < 10) {
    minutes = "0" + minutes
  } else {
    minutes = minutes
  }
  let time =
      day.getFullYear() +
      "-" +
      (day.getMonth() + 1) +
      "-" +
      day.getDate() +
      " " +
      day.getHours() +
      ":" +
      minutes +
      ":" +
      seconds
  return time
}
</script>


<style scoped>
.box {
  width: 100%;
  height: 500px;
  background-color: #f5f7fa; /* 更改背景色为更浅的灰色 */
  position: relative;
  padding: 20px;
  border-radius: 8px; /* 添加边框圆角 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
}

#content {
  height: 340px;
  overflow-y: scroll;
  font-size: 14px;
  width: 100%;
  padding: 10px; /* 增加内边距 */
  background-color: #fff; /* 设置内容区域背景为白色 */
  border-radius: 8px; /* 内容区域圆角 */
}


.con_text {
  color: #333;
  margin-bottom: 5px;
}

.con_que {
  color: #1c88ff; /* 保留问题提示的蓝色 */
  height: 30px;
  line-height: 30px;
  cursor: pointer;
  transition: color 0.3s ease; /* 添加颜色过渡效果 */
}

.con_que:hover {
  color: #006bd6; /* 鼠标悬停时颜色变深 */
}

.info_r {
  position: relative;
  margin-bottom: 15px; /* 增加信息条之间的间距 */
}



.con_r {
  display: inline-block;
  width: calc(100% - 70px); /* 使用calc调整宽度以适应圆圈和间距 */
  min-height: 55px;
  background-color: #ffffff; /* 更改背景色为更浅的灰色 */
  border-radius: 8px; /* 更改圆角大小 */
  padding: 10px;
  margin-left: 40px; /* 调整左边距以适应圆圈 */
  position: relative; /* 添加相对定位以便内部元素定位 */
}

.time_r {
  position: absolute; /* 使用绝对定位将时间放在气泡右下角 */
  bottom: 5px;
  right: 10px;
  color: #999999;
  font-size: 12px;
}

.info_l {
  text-align: right;
  margin-top: 10px;
  margin-bottom: 15px; /* 增加信息条之间的间距 */
}



.con_l {
  display: inline-block;
  max-width: calc(100% - 70px); /* 限制最大宽度以适应屏幕 */
  min-height: 51px;
  background-color: #1c88ff; /* 更改左侧气泡颜色为蓝色 */
  color: #fff; /* 文本颜色改为白色 */
  border-radius: 8px; /* 圆角 */
  padding: 10px;
  text-align: left;
  margin-right: 10px; /* 调整右边距 */
  position: relative; /* 相对定位 */
}


.time_l {
  position: absolute; /* 绝对定位时间 */
  bottom: 5px;
  left: 10px;
  color: #fff; /* 时间颜色改为白色以适应气泡背景 */
  font-size: 12px;
  border-radius: 5px; /* 添加圆角效果 */
  padding: 5px 10px; /* 添加内边距以增加空间 */
}


.set problem {
  width: 100%; /* 调整宽度为100%以适应容器 */
  height: auto; /* 高度自适应内容 */
  background-color: #fff; /* 白色背景 */
  position: relative;
  margin-top: 15px; /* 上边距调整 */
  padding: 15px; /* 内边距调整 */
  border-radius: 8px; /* 圆角 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 阴影效果 */
}

.set problem textarea {
  border: none;
  outline: none;
  padding: 0;
  margin: 0;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background-image: none;
  background-color: transparent;
  font-size: inherit;
  width: 100%;
}

.buttons end {
  background: #1c88ff; /* 按钮背景色改为蓝色 */
  border-radius: 4px; /* 圆角 */
  opacity: 1;
  font-size: 12px;
  color: #fff; /* 字体颜色改为白色 */
  position: absolute;
  right: -10%;
  top: 50%;
  cursor: pointer;
  border: none;
}


textarea {
  border: none;
  outline: none;
  padding: 0;
  margin: 0;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
  background-image: none;
  background-color: transparent;
  font-size: inherit;
  width: 100%;
}

textarea:focus {
  outline: none;
}

/* 自定义样式 */
.mo-textarea {
  display: inline-block;
  resize: vertical;
  padding: 5px 15px;
  line-height: 1.5;
  box-sizing: border-box;
  color: #606266;
  background-color: #fff;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
}

/* 提示文字 */
.mo-textarea::placeholder {
  color: #c0c4cc;
}

/* 鼠标hover */
.mo-textarea:hover {
  border-color: #c0c4cc;
}

/* 获得焦点 */
.mo-textarea:focus {
  border-color: #3677f0;
}


</style>
