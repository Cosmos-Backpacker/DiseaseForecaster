<script setup>
import {useRouter} from 'vue-router';
import {ref} from 'vue'

const router = useRouter();

const isCollapse = ref(true)

function handleClick(path) {
  router.push(path);
}

import {
  Document,
  Menu as IconMenu,
  Setting,
} from '@element-plus/icons-vue'


function switchChange() {
  isCollapse()
}

</script>

<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px"class="aside">
        <!-- 侧边栏标题 -->
        <div class="aside-title">医疗助理</div>
        <div class="aside-separator"></div>
        <el-switch v-model="isCollapse" active-text="显示">
        </el-switch>

        <el-menu
            default-active="2"
            class="el-menu-vertical-demo"
            :collapse="!isCollapse"
        >
          <!--          使得点击菜单项，图标也可以跳转-->
          <el-menu-item index="2" @click="handleClick('/MedicalAssistants')">
            <el-icon @click="handleClick('/MedicalAssistants')">
              <icon-menu/>
            </el-icon>
            <template #title>
              <el-menu-item index="/MedicalAssistants" @click="handleClick('/MedicalAssistants')">
                <router-link to="/MedicalAssistants">AI医疗助理</router-link>
              </el-menu-item>
            </template>
          </el-menu-item>



          <el-menu-item index="4" @click="handleClick('/MedicalAssistants/Ocr')">
            <el-icon @click="handleClick('/MedicalAssistants/Ocr')">
              <setting/>
            </el-icon>
            <template #title>
              <el-menu-item @click="handleClick('/MedicalAssistants/Ocr')">
                <router-link to="/MedicalAssistants/Robot">Ocr识别</router-link>
              </el-menu-item>
            </template>
          </el-menu-item>


          <el-menu-item index="5" @click="handleClick('/MedicalAssistants/ImageU')">
            <el-icon @click="handleClick('/MedicalAssistants/ImageU')">
              <setting/>
            </el-icon>
            <template #title>
              <el-menu-item @click="handleClick('/MedicalAssistants/ImageU')">
                <router-link to="/MedicalAssistants/ImageU">图片理解</router-link>
              </el-menu-item>
            </template>
          </el-menu-item>

        </el-menu>

      </el-aside>

      <el-main >
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
.aside{
  margin-top: 10px;
  background-color: #ffffffc9;
  border-radius: 5px;
  box-shadow: 0 4px 4px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
}
</style>
<!--整体优化了布局看起来舒服一点-->