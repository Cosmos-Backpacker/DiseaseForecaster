<template>
  <div>
    <div class="box">
      <div class="title">
        <img src="" alt class="logo"/>
        <span class="title-hn">机器人</span>
      </div>


      <div id="content" class="content">

        <div v-for="(item,index) in info" :key="index">
          <div class="info_r info_default" v-if="item.type === 'left info'">

            <div class="con_r con_text">
              <!--              这个是回答显示地方-->
              <!--suppress JSValidateTypes -->
              <el-image style="width: 35px; height: 35px" :src="RobotAvatar" fit="fill"/>
              <div>{{ item.content }}</div>
              <!--              这个是提示用户的提示框-->
              <div v-for="(item2,index) in item.question" :key="index">
                <div class="con_que" @click="clickRobot(item2.content)">
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
              <el-image style="width: 35px;height: 35px" :src=userAvatar fit="fill"/>
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
            id="text"
            v-model="customerText"
            @keyup.enter="sentMsg()"
            class="mo-textarea"
        ></textarea>
        <el-button @click="sentMsg()" class="buttons end">
          <span style="vertical-align: 4px;">发送</span>
        </el-button>
      </div>
    </div>

  </div>
</template>



<script setup>

//vite不支持使用require加载图片，所以用Import加载图片

import RobotAvatar from '@/assets/images/avatar/Robot头像.jpg'
import userAvatar from '@/assets/images/avatar/user头像.png'

//定义数据
import {szRobot} from "@/apis/szRobot.js";

let customerText = ""
//信息，用来存储历史对话内容，是一个列表里面放着Object对象
const info = ref([
  {
    type: 'left info',
    time: getTodayTime(),
    name: "robot",
    content: "您好，欢迎使用!",
    question: [
      {id: 1, content: "你好", index: 1},
      {id: 2, content: "你能干什么？", index: 2},
      {id: 3, content: "换季皮肤红肿出现红斑可能是因为什么？", index: 3},
      {id: 4, content: "婴儿发烧时应及时做什么措施？", index: 4},
      {id: 5, content: "经常熬夜吃垃圾食品的危害", index: 5}
    ],
    index: 0
  }
])


let timer = null


let robotQuestion = [
  {id: 1, content: "最近头疼身体不舒服可能是什么问题", index: 1},
  {id: 2, content: "经常睡眠不足，食欲不振是因为什么，有什么解决办法？", index: 2},
  {id: 3, content: "换季皮肤红肿出现红斑可能是因为什么？", index: 3},
  {d: 4, content: "婴儿发烧时应及时做什么措施？", index: 4},
  {id: 5, content: "经常熬夜吃垃圾食品的危害", index: 5},
]


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
    appendRobotMsg(customerText)

    //清空提问内容
    customerText = ''

    nextTick(() => {
      var contentHeight = document.getElementById('content')
      contentHeight.scrollTop = contentHeight.scrollHeight
    })
  }
}


//待会改成直接请求
async function appendRobotMsg(question) {
  //调用函数清楚木目前保存的时间
  clearTimeout(timer)
  //整理一下问题
  question = question.trim()
  let answerText = ''
  //发起请求
  const res = await szRobot(question)
  answerText = res.data
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
    //如果没找到再次跳出
    answerText = "您可能想问："
    let obj = {
      type: 'left info',
      time: getTodayTime(),
      name: "robot",
      content: answerText,
      question: robotQuestion,
    }
    //放入到历史对话集合中显示
    info.value.push(obj)
  }
  nextTick(() => {
    var contentHeight = document.getElementById('content')
    contentHeight.scrollTop = contentHeight.scrollHeight
  })
}


function clickRobot(val) {
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
  appendRobotMsg(val)
  this.$nextTick(() => {
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
