<script setup>
import {UploadFilled} from '@element-plus/icons-vue'
import {
  Document,
  Menu as IconMenu,
  Location,
  Setting,
} from '@element-plus/icons-vue'

</script>


<template>

  <div class="common-layout">
    <el-container>
      <el-aside width="200px">

        <el-menu
            default-active="2"
            class="el-menu-vertical"
        >

          <el-menu-item index="1">
            <el-icon>
              <icon-menu/>
            </el-icon>
            <span>
              <router-link to="/DiseasePrediction">心脏病</router-link>
            
            </span>
          </el-menu-item>

          <el-menu-item index="2">
            <el-icon>
              <document/>
            </el-icon>
            <span>

              <router-link to="/Diabetes">糖尿病</router-link>
            </span>
          </el-menu-item>

          <el-sub-menu index="3">
            <template #title>
              <el-icon><location /></el-icon>
              <span>其他</span>
            </template>
            <el-menu-item index="1">
              <el-icon>
                <document/>
              </el-icon>
              <span>

              <router-link to="/HealthQuestionnaire">健康问卷</router-link>
            </span>
            </el-menu-item>
          </el-sub-menu>
        </el-menu>
      </el-aside>


      <el-main>
        <router-view/>
      </el-main>


    </el-container>
  </div>


</template>


<style scoped>
.el-menu-vertical {
  height: auto;
}

.el-main{
  height: auto;
}


</style>