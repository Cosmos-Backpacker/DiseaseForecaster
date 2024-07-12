<script setup>
import {useUserStore} from "@/stores/userStore.js";

const userStore = useUserStore()


import {reactive} from 'vue'

// do not use same name with ref
const form = reactive({
  name: '',
  password:'',
  email:'',
  age:'',
  gender:''
})

const onSubmit = () => {
  console.log('submit!')
}

</script>

<template>
  <div class="home-overview">
    <!-- 用户信息 -->
    <div class="user-meta">
      <div class="avatar">
        <img :src="userStore.userInfo?.avatar"/>
      </div>
      <h4>{{ userStore.userInfo?.account }}</h4>
    </div>
    <div class="item">
      <a href="javascript:;">
        <span class="iconfont icon-hy"></span>
        <p>会员中心</p>
      </a>
      <a href="javascript:;">
        <span class="iconfont icon-aq"></span>
        <p>安全设置</p>
      </a>
      <a href="javascript:;">
        <span class="iconfont icon-dw"></span>
        <p>地址管理</p>
      </a>
    </div>
  </div>
  <div class="like-container">
    <div class="home-panel">
      <div class="header">
        <h4 data-v-bcb266e0="">信息修改</h4>
      </div>

<!-- 用户信息表单-->
      <div class="form1">
        <el-form :model="form" label-width="auto" style="max-width: 600px">
          <el-form-item label="昵称">
            <el-input v-model="form.name"/>
          </el-form-item>

          <el-form-item label="密码">
            <el-input v-model="form.password"/>
          </el-form-item>

          <el-form-item label="邮箱">
            <el-input v-model="form.email"/>
          </el-form-item>

          <el-form-item label="年龄">
            <el-input v-model="form.age"/>
          </el-form-item>


          <el-form-item label="性别">
            <el-radio-group v-model="form.gender">
              <el-radio label="男♂"/>
              <el-radio label="女♀"/>
            </el-radio-group>
          </el-form-item>


          <el-form-item>
            <el-button type="primary" @click="onSubmit">Create</el-button>
            <el-button>Cancel</el-button>
          </el-form-item>
        </el-form>
      </div>

    </div>
  </div>


</template>


<style scoped lang="scss">


.home-overview {
  height: 132px;
  background: url(@/assets/images/center-bg.png) no-repeat center / cover;
  display: flex;

  .user-meta {
    flex: 1;
    display: flex;
    align-items: center;

    .avatar {
      width: 85px;
      height: 85px;
      border-radius: 50%;
      overflow: hidden;
      margin-left: 60px;

      img {
        width: 100%;
        height: 100%;
      }
    }

    h4 {
      padding-left: 26px;
      font-size: 18px;
      font-weight: normal;
      color: white;
    }
  }

  .item {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-around;

    &:first-child {
      border-right: 1px solid #f4f4f4;
    }

    a {
      color: white;
      font-size: 16px;
      text-align: center;

      .iconfont {
        font-size: 32px;
      }

      p {
        line-height: 32px;
      }
    }
  }
}

.like-container {
  margin-top: 20px;
  border-radius: 4px;
  background-color: #fff;
}

.home-panel {
  background-color: #fff;
  padding: 0 20px;
  margin-top: 20px;
  height: 400px;

  .header {
    height: 66px;
    border-bottom: 1px solid #f5f5f5;
    padding: 18px 0;
    display: flex;
    justify-content: space-between;
    align-items: baseline;

    h4 {
      font-size: 22px;
      font-weight: 400;
    }

  }

  .goods-list {
    display: flex;
    justify-content: space-around;
  }
}
</style>