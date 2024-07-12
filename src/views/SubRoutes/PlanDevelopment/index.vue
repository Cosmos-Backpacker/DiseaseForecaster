<template>
  <!-- 占空间的盒子-->
  <div style="height: 20px"/>

  <div class="box">

    <el-button plain @click="dialogTableVisible = true">
      累计数据
    </el-button>

    <el-button plain @click="dialogFormVisible = true">
      新增计划
    </el-button>

    <el-dialog v-model="dialogTableVisible" title="累计记录" width="800">
      <!-- 统计组件-->
      <statistics></statistics>

    </el-dialog>

    <!--    对话框-->
    <el-dialog v-model="dialogFormVisible" title="新增计划" width="500">
      <!--   新增计划表格   -->
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


      <template #footer>
        <div class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">
            提交
          </el-button>
        </div>
      </template>
    </el-dialog>

    <!--下面累计分页展示表格-->
    <div style="height: 20px"></div>
    <pagination></pagination>


  </div>


</template>

<script setup>
import statistics from './components/statistics.vue'
import pagination from './components/pagination.vue'
import {reactive, ref} from 'vue'

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
.box {
  height: 400px;
}
</style>