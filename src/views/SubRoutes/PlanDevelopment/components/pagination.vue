<script setup>
import {ref,reactive,onMounted} from 'vue'
// tableData-表格数据列表，total-数据总长度
const tableData=ref([])
const total=ref(0)
// searchData-向后端分页查询的对象，即当前页和每页总数
const searchData=reactive({
  current:1,
  limit:5
})


//表格数据生成
function tableAddData(){
  //给表格添加数据，调接口赋值同理
  var index=0
  //因为数据是固定生成的，容易出错，所以这里要清一下
  tableData.value=[
    {
      id:1,
      date: '2024-03-06',
      name: '跑步',
      address: '已完成',
      creatTime: '2024-03-06 16:00',
      endTime: '2024-03-06 17:00',
      isOver: '完成'
    },
    {
      id:2,
      date: '2024-03-06',
      name: '俯卧撑30个',
      address: '未完成',
      creatTime: '2024-03-06 16:00',
      endTime: '2024-03-06 16:02',
      isOver: '未完成'
    },
    {
      id:3,
      date: '2024-03-06',
      name: '拉杠铃20次',
      address: '已完成',
      creatTime: '2024-03-06 16:30',
      endTime: '2024-03-06 16:40',
      isOver: '完成'
    },
    {
      id:4,
      date: '2024-03-06',
      name: '跳绳300个',
      address: '已完成',
      creatTime: '2024-03-06 14:00',
      endTime: '2024-03-06 16:00',
      isOver: '完成'
    },
    {
      id:5,
      date: '2024-03-06',
      name: '慢跑20分钟',
      address: '已完成',
      creatTime: '2024-03-06 20:00',
      endTime: '2024-03-06 21:00',
      isOver: '完成'
    }
  ]
  for(var i=1;i<=101;i++){
    let data={}
    data.id=i
    data.data=`我的数据是：${i}`
    tableData.value.push(data)
    index+=1
  }

  total.value=index
}
//传入分页参数
function pageQuery(current,limit){
  // 模仿分页查询，将表格的数据裁切一下

  //     1     2     3
  //下标 0-9 10-19 20-29
  let begin=current*limit-limit
  //这里不减一是因为，slice方法裁切是左闭右开数组
  let end=current*limit
  tableData.value=tableData.value.slice(begin,end)
}



function getData(val = 1){
  searchData.current=val
  // 先把数据搞上
  tableAddData()
  pageQuery(searchData.current,searchData.limit)
}

onMounted(async()=>{
  getData()
})

</script>

<template>

  <div>
    <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="id" label="id" width="180" />
      <el-table-column prop="date" label="创建日期" width="180" />
      <el-table-column prop="name" label="计划" width="180" />
      <el-table-column prop="creatTime" label="开始时间" width="180" />
      <el-table-column prop="endTime" label="结束时间" width="180" />
      <el-table-column prop="isOver" label="完成情况" width="180" />
      <el-table-column>
        <el-checkbox value="gridData.isover">完成</el-checkbox>
      </el-table-column>
    </el-table>
    <el-pagination
        :current-page="searchData.current"
        :page-size="searchData.limit"
        :total="total"
        :pager-count="6"
        style="text-align: center;margin-top: 20px;"
        layout="jumper, prev, pager, next, total"
        @current-change="getData" />
  </div>


</template>

<style scoped lang="scss">

</style>