<script setup>
import {useScroll} from '@vueuse/core'

const {y} = useScroll(window)

import router from "@/router/index.js";

//路由跳转函数
function toPath(path) {
  router.replace(path)
}
</script>

<template>
  <div class="app-header-sticky  " :class="{show:y>78}">
    <div class="container">
      <!-- 网站logo -->
        <RouterLink class="logo" to="/"/>


      <!-- 导航区域 -->
      <ul class="app-header-nav ">
        <li class="home">
          <a @click="toPath('/')"> 平台数据展示</a>
        </li>
        <li>
          <a @click="toPath('/DiseasePrediction')">疾病预测</a>

        </li>
        <li><a @click="toPath('/MedicalAssistants')"> AI智能助手</a></li>
        <li><a @click="toPath('/Rendering_3D')">3D展示</a></li>
        <li><a @click="toPath('/DiseaseInquiry')">疾病查询</a></li>
        <li><a @click="toPath('/moreFunctions')">更多功能</a></li>
      </ul>

      <div class="right">
        <RouterLink to="/">退出</RouterLink>
      </div>

    </div>
  </div>
</template>


<style scoped lang='scss'>
.app-header-sticky {
  width: 100%;
  height: 80px;
  position: fixed;
  left: 0;
  top: 0;
  z-index: 999;
  background-color: #fff;
  border-bottom: 1px solid #e4e4e4;
  // 此处为关键样式!!!
  // 状态一：往上平移自身高度 + 完全透明
  transform: translateY(-100%);
  opacity: 0;

  // 状态二：移除平移 + 完全不透明
  &.show {
    transition: all 0.3s linear;
    transform: none;
    opacity: 1;
  }


  .container {
    width: 100%;
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200px;
    height: 80px;
    margin-left: 50px;
    background: url("@/assets/images/logo.png") no-repeat center center;
    background-size: 160px auto;
  }

  .right {
    width: 220px;
    display: flex;
    text-align: center;
    padding-left: 40px;
    margin-left: 50px;
    border-left: 2px solid $xtxColor;

    a {
      width: 38px;
      margin-right: 40px;
      font-size: 16px;
      line-height: 1;

      &:hover {
        color: $xtxColor;
      }
    }
  }
}

.app-header-nav {
  width: 820px;
  height: auto;
  display: flex;
  padding-left: 40px;
  margin-left: 80px;
  position: relative;
  z-index: 998;

  li {
    margin-right: auto;
    width: auto;
    text-align: center;

    a {
      font-size: 16px;
      line-height: 50px;
      height: 50px;
      display: inline-block;

      &:hover {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }

    .active {
      color: $xtxColor;
      border-bottom: 1px solid $xtxColor;
    }
  }

  .right {
    margin-right: auto;
    width: auto;
    text-align: center;

    a {
      font-size: 16px;
      line-height: 32px;
      height: 32px;
      display: inline-block;

      &:hover {
        color: $xtxColor;
        border-bottom: 1px solid $xtxColor;
      }
    }

    .active {
      color: $xtxColor;
      border-bottom: 1px solid $xtxColor;
    }
  }

}
</style>