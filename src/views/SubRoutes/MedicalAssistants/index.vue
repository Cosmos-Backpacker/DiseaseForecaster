<script setup>
//引入组件名
import MedicalAssistantsContainer from './components/MedicalAssistantsContainer.vue'

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px">
        <!-- 侧边栏标题 -->
        <div class="aside-title">AI医疗助理</div>
        <div class="aside-separator"></div>

        <!-- 侧边栏链接 -->
        <el-menu
            background-color="#F0F2F5"
            text-color="#E4E7ED"
            active-text-color="#E4E7ED"
            mode="vertical"
        >
          <el-menu-item index="/MedicalAssistants">
            <router-link to="/MedicalAssistants">AI医疗助理</router-link>
          </el-menu-item>
          <el-menu-item index="/MedicalAssistants/Robot">
            <router-link to="/MedicalAssistants/Robot">机器人</router-link>
          </el-menu-item>
          <!-- 可以继续添加更多的菜单项 -->
        </el-menu>
      </el-aside>
      <el-main>
        <!-- 子路由在此展示 -->
        <router-view/>
      </el-main>
    </el-container>
  </div>

</template>

<style scoped lang="scss">
.aside-title {
  font-size: 16px;
  font-weight: bold;
  padding: 10px;
  text-align: center;
}

.aside-separator {
  height: 1px;
  background-color: #CDD0D6;
  margin: 5px 0;
}

</style>