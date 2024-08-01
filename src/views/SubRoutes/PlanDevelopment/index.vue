<template>
  <!-- 主盒子 -->
  <div class="main-content">
    <div class="left-container">
      <!-- 累计数据 box -->
      <div class="cumulative-data-box">
        <h3>累计数据</h3>
        <statistics></statistics>
      </div>

      <div class="plan-box">
        <!-- 今日计划展示 -->
        <div class="plan-status-box">
          <div class="button-container">
            <!-- 切换按钮 -->
            <el-button plain @click="togglePlanStatus">
              {{ currentPlanStatus === 'finished' ? '已完成计划' : '未完成计划' }}
            </el-button>
          </div>
          <h3>{{ currentPlanStatus === 'finished' ? '已完成计划' : '未完成计划' }}</h3>
          <div class="table-container">
            <el-table
                :data="filteredTableData"
                style="width: 100%">
              <el-table-column
                  prop="date"
                  label="日期"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="name"
                  label="姓名"
                  width="180">
              </el-table-column>
              <el-table-column
                  prop="address"
                  label="地址">
              </el-table-column>
            </el-table>
            <div v-if="filteredTableData.length === 0" class="no-data-message">
              没有更多数据
            </div>
          </div>
        </div>

        <div class="separator"></div>

        <!-- 表单添加计划 box -->
        <div class="add-plan-box">
          <h3>新增计划</h3>
          <el-form :model="form">
            <el-form-item label="计划内容" :label-width="formLabelWidth">
              <el-input v-model="form.name" autocomplete="off"/>
            </el-form-item>
            <el-form-item label="开始时间" :label-width="formLabelWidth">
              <el-date-picker v-model="form.startTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
            </el-form-item>
            <el-form-item label="结束时间" :label-width="formLabelWidth">
              <el-date-picker v-model="form.endTime" type="datetime" placeholder="选择日期时间"></el-date-picker>
            </el-form-item>
          </el-form>
          <div class="dialog-footer">
            <el-button @click="resetForm">取消</el-button>
            <el-button type="primary" @click="submitForm">提交</el-button>
          </div>
        </div>
      </div>
    </div>

    <!-- 展示计划完成情况 box -->
    <div class="display-status-box">
      <h3>日程规划小能手</h3>
      <!-- 放可视化 -->
      <div class="chat-container">
        <div class="chat-box" ref="chatBox">
          <!-- 聊天消息列表 -->
          <div class="chat-message" v-for="message in messages" :key="message.id">
            <div :class="['chat-content', message.type]">
              {{ message.text }}
            </div>
          </div>
        </div>
        <div class="chat-input">
          <el-input
              type="textarea"
              v-model="inputMessage"
              placeholder="请输入消息"
              @keyup.enter="sendMessage">
          </el-input>
          <el-button @click="sendMessage">发送</el-button>
        </div>
      </div>


    </div>
  </div>
</template>

<script setup>
import statistics from './components/statistics.vue'
import pagination from './components/pagination.vue'
import { reactive, ref, computed } from 'vue'

const formLabelWidth = '140px'

const form = reactive({
  name: '',
  startTime: '',
  endTime: ''
})

const gridData = [
  {
    date: '2024-03-06',
    name: '跑步',
    address: '已完成',
    creatTime: '2024-03-06 16:00',
    endTime: '2024-03-06 17:00',
    isOver: true
  },
  {
    date: '2024-03-06',
    name: '俯卧撑30个',
    address: '未完成',
    creatTime: '2024-03-06 16:00',
    endTime: '2024-03-06 16:02',
    isOver: false
  },
  {
    date: '2024-03-06',
    name: '拉杠铃20次',
    address: '已完成',
    creatTime: '2024-03-06 16:30',
    endTime: '2024-03-06 16:40',
    isOver: true
  },
  {
    date: '2024-03-06',
    name: '跳绳300个',
    address: '已完成',
    creatTime: '2024-03-06 14:00',
    endTime: '2024-03-06 16:00',
    isOver: true
  },
]

const currentPlanStatus = ref('finished')

const togglePlanStatus = () => {
  currentPlanStatus.value = currentPlanStatus.value === 'finished' ? 'unfinished' : 'finished'
}

const filteredTableData = computed(() => {
  return gridData.filter(item => item.isOver === (currentPlanStatus.value === 'finished'))
})

const resetForm = () => {
  form.name = ''
  form.startTime = ''
  form.endTime = ''
}

const submitForm = () => {
  // 处理表单提交逻辑
  console.log('Form submitted:', form)
  resetForm()
}

// 聊天相关数据和方法
const messages = ref([])
const inputMessage = ref('')

const sendMessage = () => {
  if (inputMessage.value.trim()) {
    messages.value.push({
      id: messages.value.length + 1,
      type: 'user',
      text: inputMessage.value
    })
    inputMessage.value = '' // 清空输入框
    // 模拟AI回复
    setTimeout(() => {
      messages.value.push({
        id: messages.value.length + 1,
        type: 'ai',
        text: '收到，正在为您规划日程安排中....'
      })
    }, 1000)
  }
}

// 滚动聊天消息列表到底部
const chatBox = ref(null)
watch(messages, () => {
  nextTick(() => {
    chatBox.value.scrollTop = chatBox.value.scrollHeight
  })
})
</script>

<style scoped>
/* 主盒子 */
.main-content {
  display: flex;
  margin: 0 auto;
  padding: 10px;
  align-items: stretch; /* 确保子元素高度拉伸以填充容器 */
}

.left-container {
  display: flex;
  flex-direction: column;
  width: 70%;
  padding-right: 10px;
  height: 100%;
  justify-content: center;
}
.cumulative-data-box,
.plan-status-box,
.add-plan-box,
.display-status-box {
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
  margin-bottom: 20px;
  width: 100%;
  height: 500px;
}

.cumulative-data-box h3,
.plan-status-box h3,
.add-plan-box h3 {
  margin-bottom: 10px;
}

/* 计划盒子 */
.plan-box {
  display: flex;
  flex-direction: row;
  width: 100%;
  padding: 10px;
  background-color: #fff;
  border-radius: 10px;
  justify-content: space-between;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.1);
  border: 1px solid #ccc;
}

.cumulative-data-box{
  width: 100%;
  height: 100%;
  box-shadow: 0 0px 10px rgba(0, 0, 0, 0.1);
  border: 1px solid #ccc;
}

/* 完成状态盒子 */
.plan-status-box {
  width: 50%;
  margin-left: -1%;
  margin-bottom: 20px;
  margin-right: 100px;
}

.separator {
  width: 1px; /* 实线宽度 */
  background-color: #ccc; /* 实线颜色 */
  margin: 10px 0; /* 垂直间距 */
}

.add-plan-box{
  width: 40%;
  margin-left: -120px;
  margin-right: 40px;
}

/* 按钮容器 */
.button-container {
  position: absolute;
  top: 445px;
  right: 1150px;
  border-radius: 10px;
  box-shadow: 0 8px 6px rgba(0, 0, 0, 0.1);
}

/* 表格容器 */
.table-container {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}

.no-data-message {
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  color: #999;
}

/* 表单底部 */
.dialog-footer {
  margin-top: 20px; /* 调整为更合理的间距 */
  text-align: center;
}


.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}
.chat-box {
  flex-grow: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: #f9f9f9;
  border-radius: 10px;
  margin-bottom: 10px;
}
.chat-message {
  margin-bottom: 10px;
}

.chat-content {
  padding: 5px 10px;
  border-radius: 15px;
  max-width: 70%;
  display: inline-block;
}
.chat-content.user {
  background-color: #e6e6e6;
  align-self: flex-end;
}

.chat-content.ai {
  background-color: #dcf8c6;
  align-self: flex-start;
}
.chat-input {
  display: flex;
  align-items: center;
  padding: 10px;
}
/* 输入框和发送按钮 */
.el-textarea {
  flex-grow: 1;
  margin-right: 10px;
}
.el-button {
  padding: 10px 20px;
}



</style>