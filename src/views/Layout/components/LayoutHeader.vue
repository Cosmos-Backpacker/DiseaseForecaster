<script setup>

import router from "@/router/index.js";
//路由跳转函数
function toPath(path) {
  router.replace(path)
}
</script>

<template>
  <header class='app-header'>
    <div class="container">
      <h1 class="logo">
        <RouterLink to="/">疾病预测平台</RouterLink>
      </h1>

      <ul class="app-header-nav">
        <li class="home">
          <a @click="toPath('/')">平台数据展示</a>
        </li>
        <li>
          <a @click="toPath('/DiseasePrediction')">疾病预测</a>
        </li>
        <li>
          <a @click="toPath('/MedicalAssistants')"> AI智能助手</a>
        </li>
        <li>
          <a @click="toPath('/Rendering_3D')">3D展示</a>
        </li>
        <li>
          <a @click="toPath('/DiseaseInquiry')">疾病查询</a>
        </li>
        <li>
          <a @click="toPath('/moreFunctions')">更多功能</a>
        </li>
      </ul>

      <div class="search">
        <i class="iconfont icon-search"></i>
        <input type="text" placeholder="搜一搜">
      </div>
      <!-- 头部购物车 -->
      <el-button type="primary">搜索</el-button>
    </div>
  </header>
</template>


<style scoped lang='scss'>
.app-header {
  background: #fff;

  .container {
    width: 100%;
    display: flex;
    align-items: center;
  }

  .logo {
    width: 200*1.5px;
    margin-left: 30px;

    a {
      display: block;
      height: 150px;
      width: 100%;
      text-indent: -9999px;
      background: url('@/assets/images/logo.png') no-repeat center  / contain;
    }
  }

  .app-header-nav {
    width: 820px;
    display: flex;
    padding-left: 10px;
    position: relative;
    z-index: 998;
    margin-left: 50px;

    li {
      margin-right: 40px;
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

  .search {
    width: 170px;
    height: 32px;
    position: relative;
    border-bottom: 1px solid #e7e7e7;
    line-height: 32px;

    .icon-search {
      font-size: 18px;
      margin-left: 5px;
    }

    input {
      width: 140px;
      padding-left: 5px;
      color: #666;
    }
  }

  .cart {
    width: 50px;

    .curr {
      height: 32px;
      line-height: 32px;
      text-align: center;
      position: relative;
      display: block;

      .icon-cart {
        font-size: 22px;
      }

      em {
        font-style: normal;
        position: absolute;
        right: 0;
        top: 0;
        padding: 1px 6px;
        line-height: 1;
        background: $helpColor;
        color: #fff;
        font-size: 12px;
        border-radius: 10px;
        font-family: Arial;
      }
    }
  }
}
</style>