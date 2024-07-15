<script setup>
import {ref} from 'vue';
import {ElForm, ElFormItem, ElInput, ElButton, ElMessage} from 'element-plus';
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

const userStore = useUserStore() //获取userStore实例

const formRef = ref(null);

const form = ref({
  account: 'test',
  password: 'ww11111',
  confirmPassword: 'ww11111',
});

const showMessage = ref(false);
const message = ref('');

// rules
const rules = {
  account: [
    {required: true, message: '请输入用户名', trigger: 'blur'},
    {min: 3, max: 10, message: '用户名长度在 3 到 10 个字符', trigger: 'blur'},
    {pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字和下划线', trigger: 'blur'},
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 16, message: '密码长度在 6 到 16 个字符', trigger: 'blur'},
    {pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/, message: '密码中必须同时包含字母和数字', trigger: 'blur'},
  ],
  confirmPassword: [
    {required: true, message: '请输入确认密码', trigger: 'blur'},
    {min: 6, max: 16, message: '确认密码长度在 6 到 16 个字符', trigger: 'blur'},
    {
      pattern: /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,16}$/,
      message: '确认密码中必须同时包含字母和数字',
      trigger: 'blur',
    },

    {
      validator: (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== form.value.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur',
    },
  ]
}


const onSubmit =()=>{


  formRef.value.validate(async (valid) => {

    if (valid) {
      const {account, password} = form.value

      //调用register方法发送注册方法，同时将数据保存在userStore里的registerInfo里面
      await userStore.registerUser({account, password})

      if (userStore.registerInfo.code === 400) {
        // 显示服务器返回的错误消息
        ElMessage({type: 'error', message: userStore.registerInfo.msg})


      } else if (userStore.registerInfo.code === 200) {
        //显示成功消息
        ElMessage({type: 'success', message: userStore.registerInfo.msg})
        //跳转路由到登录页面
        router.replace('/login')
      }
    }
  })
}
</script>


<template>
  <div>
    <h2>注册</h2>
    <el-form ref="formRef" :rules="rules" :model="form"label-width="80px" class="register-form">
      <el-form-item label="用户名" prop="account">
        <el-input v-model="form.account"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="form.password"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input type="password" v-model="form.confirmPassword"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">注册</el-button>
      </el-form-item>
    </el-form>
    <div v-if="showMessage">{{ message }}</div>
  </div>
</template>




<style scoped>


.register-form {
  width: 400px; /* 调整表单宽度 */
  padding: 20px; /* 添加内边距 */
  border: 1px solid #ccc; /* 添加边框 */
  border-radius: 5px; /* 圆角边框 */
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* 添加阴影效果 */
  margin: 0 auto; /* 水平居中 */
  margin-top: 100px; /* 顶部外边距，可根据需要调整 */
  background-color: #fff; /* 背景颜色 */
}

h2 {
  text-align: center; /* 标题居中 */
  margin-bottom: 20px; /* 标题与表单之间的间距 */
}

.el-form-item {
  margin-bottom: 15px; /* 表单项之间的间距 */
}

.el-input {
  width: 100%; /* 输入框宽度 */
}

.el-button {
  width: 100%; /* 注册按钮宽度 */
  margin-top: 10px; /* 注册按钮与上一个表单项的间距 */
}

div[v-if="showMessage"] {
  text-align: center; /* 提示消息居中 */
  margin-top: 20px; /* 提示消息与注册按钮的间距 */
  color: green; /* 提示消息字体颜色 */
}

</style>
