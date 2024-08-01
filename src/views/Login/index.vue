<script setup>
import {ref} from 'vue'
import {ElMessage} from "element-plus";
import 'element-plus/theme-chalk/el-message.css'  //弹窗样式
import router from "@/router/index.js";
import {useUserStore} from "@/stores/userStore.js";



//获取store实例
const userStore = useUserStore()

// 1. 准备表单对象
const form = ref({
  account: 'user01',
  password: '123456',
  agree: true
})


//2.规则数据对象    rules里的规则和form里的变量相对应
const rules = {
  account: [
    {required: true, message: '用户名不能为空', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '密码不能为空', trigger: 'blur'},
    {min: 6, max: 14, message: '密码长度要求6-14个字符', trigger: 'blur'}
  ],
  agree: [
    {
      // 自定义校验逻辑
      validator(rule, value, callback) {
        // 勾选就通过 不勾选就不通过
        if (value) {
          callback() // 验证通过不反回错误信息
        } else {
          callback(new Error('请勾选协议'))// 验证不通过返回错误信息
        }
      }
    }
  ]
}


const formRef = ref(null);


//实现登录页面


//在你提供的 doLogin 函数中，valid 变量是作为参数传递给回调函数的。这个变量表示在调用 formRef.value.validate 时验证的结果。validate 方法本身已经内置了对表单数据的验证逻辑，并且在验证完成后，它会调用你提供的回调函数，并将验证结果作为 valid 参数传递给它。
// 由于 valid 参数是回调函数的参数，所以它的值只在回调函数内部有效。在你提供的代码片段中，valid 参数被用于决定在验证通过后执行哪些操作。如果 valid 为 true，则表示表单验证通过，你可以执行获取用户信息、显示成功消息和切换路由等操作。如果 valid 为 false，则表示表单验证未通过，你可以根据需要执行相应的错误处理逻辑。

// 请注意，validate 方法中的回调函数的 async 关键字不会影响 validate 方法本身的执行。async 只影响回调函数内部的代码，使其能够执行异步操作，并等待这些操作完成。
const doLogin = () => {
  formRef.value.validate(async (valid) => {

    if (valid) {

      const {account, password} = form.value

      await userStore.getUserInfo({account, password})   //获取用户信息并存储到store中
      //console.log(userStore)
      console.log(userStore)
      //进行判断，如果响应成功,会返回一个token值
      if (userStore.userInfo != null) {
        ElMessage({type: 'success', message: '登录成功'})

        //切换页面
        router.replace({path: '/'})
      } else if (userStore.userInfo === null)  //如果没拿到数据，代表登录失败
      {
        ElMessage({type: 'error', message: "用户名或密码错误"})
      }


    }
  })
}


//实现跳转注册页面的方法
const handleRegister = () => {
  router.push('/register'); // 跳转到注册页面
};



</script>


<template>
  <div>
    <header class="login-header">
      <div class="container m-top-20">
        <h1 class="logo">
          <RouterLink to="/login">疾病预测平台</RouterLink>
        </h1>
<!--        <RouterLink class="entry" to="/">-->
<!--          进入网站首页-->
<!--          <i class="iconfont icon-angle-right"></i>-->
<!--          <i class="iconfont icon-angle-right"></i>-->
<!--        </RouterLink>-->
      </div>
    </header>
    <section class="login-section">
      <div class="wrapper">
        <nav>
          <a href="javascript:;">账户登录</a>
        </nav>
        <div class="account-box">
          <div class="form">
            <el-form ref="formRef" :model="form" :rules="rules" label-position="right" label-width="60px"
                     status-icon>
              <el-form-item label="账户" prop="account">
                <el-input v-model="form.account"/>
              </el-form-item>
              <el-form-item label="密码" prop="password" show-message>
                <el-input v-model="form.password" show-password/>
              </el-form-item>
              <el-form-item label-width="22px" prop="agree">
                <el-checkbox size="large" v-model="form.agree">
                  我已同意隐私条款和服务条款
                </el-checkbox>
              </el-form-item>
              <div class="button-container">
                <el-button size="large" class="subBtn" @click="doLogin">点击登录</el-button>
                <el-button size="large" class="subBtn" @click="handleRegister">点击注册</el-button>
              </div>

            </el-form>
          </div>
        </div>
      </div>
    </section>

    <footer class="login-footer">
      <div class="container">
        <p>
          <a href="javascript:;">关于我们</a>
          <a href="javascript:;">帮助中心</a>
          <a href="javascript:;">售后服务</a>
          <a href="javascript:;">商务合作</a>
          <a href="javascript:;">搜索推荐</a>
          <a href="javascript:;">友情链接</a>
        </p>
        <p>CopyRight &copy; 疾病预测平台</p>
      </div>
    </footer>
  </div>
</template>


<style scoped lang='scss'>

.button-container {
  display: flex;
  justify-content: space-between; /* 或者使用其他对齐方式，如 'center' */
  align-items: center; /* 垂直对齐 */
}

.subBtn {
  margin: 0 5px; /* 可选，为了给按钮之间添加一些间距 */
}



.login-header {
  background: #fff;
  border-bottom: 1px solid #e4e4e4;

  .container {
    display: flex;
    align-items: flex-end;
    justify-content: space-between;
  }

  .logo {
    width: 200px;

    a {
      display: block;
      height: 132px;
      width: 100%;
      text-indent: -9999px;
      background: url("@/assets/images/logo.png") no-repeat center 18px / contain;
    }
  }

  .sub {
    flex: 1;
    font-size: 24px;
    font-weight: normal;
    margin-bottom: 38px;
    margin-left: 20px;
    color: #666;
  }

  .entry {
    width: 120px;
    margin-bottom: 38px;
    font-size: 16px;

    i {
      font-size: 14px;
      color: $xtxColor;
      letter-spacing: -5px;
    }
  }
}

.login-section {
  background: url('@/assets/images/bg3.jpeg') no-repeat center / cover;
  height: 488px;
  position: relative;

  .wrapper {
    width: 380px;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 54px;
    transform: translate3d(100px, 0, 0);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);

    nav {
      font-size: 14px;
      height: 55px;
      margin-bottom: 20px;
      border-bottom: 1px solid #f5f5f5;
      display: flex;
      padding: 0 40px;
      text-align: right;
      align-items: center;

      a {
        flex: 1;
        line-height: 1;
        display: inline-block;
        font-size: 18px;
        position: relative;
        text-align: center;
      }
    }
  }
}

.login-footer {
  padding: 30px 0 50px;
  background: #fff;

  p {
    text-align: center;
    color: #999;
    padding-top: 20px;

    a {
      line-height: 1;
      padding: 0 10px;
      color: #999;
      display: inline-block;

      ~ a {
        border-left: 1px solid #ccc;
      }
    }
  }
}

.account-box {
  .toggle {
    padding: 15px 40px;
    text-align: right;

    a {
      color: $xtxColor;

      i {
        font-size: 14px;
      }
    }
  }

  .form {
    padding: 0 20px 20px 20px;

    &-item {
      margin-bottom: 28px;

      .input {
        position: relative;
        height: 36px;

        > i {
          width: 34px;
          height: 34px;
          background: #cfcdcd;
          color: #fff;
          position: absolute;
          left: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 18px;
        }

        input {
          padding-left: 44px;
          border: 1px solid #cfcdcd;
          height: 36px;
          line-height: 36px;
          width: 100%;

          &.error {
            border-color: $priceColor;
          }

          &.active,
          &:focus {
            border-color: $xtxColor;
          }
        }

        .code {
          position: absolute;
          right: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 14px;
          background: #f5f5f5;
          color: #666;
          width: 90px;
          height: 34px;
          cursor: pointer;
        }
      }

      > .error {
        position: absolute;
        font-size: 12px;
        line-height: 28px;
        color: $priceColor;

        i {
          font-size: 14px;
          margin-right: 2px;
        }
      }
    }

    .agree {
      a {
        color: #069;
      }
    }

    .btn {
      display: block;
      width: 100%;
      height: 40px;
      color: #fff;
      text-align: center;
      line-height: 40px;
      background: $xtxColor;

      &.disabled {
        background: #cfcdcd;
      }
    }
  }

  .action {
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .url {
      a {
        color: #999;
        margin-left: 10px;
      }
    }
  }
}

.subBtn {
  background: $xtxColor;
  width: 100%;
  color: #fff;
}
</style>