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
      <el-button plain @click="dialogFormVisible = true">
        简略版
      </el-button>


      <el-dialog v-model="dialogTableVisible" title="累计记录" width="800">
        <!-- 统计组件-->
        <!--      <statistics></statistics>-->

      </el-dialog>

      <!--    对话框-->
      <el-dialog v-model="dialogFormVisible" title="预测表单" width="500">


        <!--   新增计划表格   -->
        <heart-form></heart-form>

      </el-dialog>


      <el-dialog v-model="dialogFormVisible" title="预测表单" width="500">


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
// import statistics from './components/statistics.vue'
// import pagination from './components/pagination.vue'
import heartForm from './components/heartForm.vue'
import {reactive, ref} from 'vue'
import userHeartForm from './components/userHeartForm.vue'

const dialogTableVisible = ref(false)
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const checked2 = ref(true)

const form = reactive({
  name: '',
  region: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
  startTime: '',
  endTime: '',
  over: true,
  not_over: false
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
</script>


<style>
.mybox {
  height: 100%;
  background-color: #cccccc;
}

.box {
  height: 400px;
}
</style>