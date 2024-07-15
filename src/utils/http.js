import axios from 'axios'
import {ElMessage} from "element-plus";
import 'element-plus/theme-chalk/el-message.css'
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

//利用Axios库创建一个自己的请求方法

// 创建axios实例
const http = axios.create({
    // baseURL: 'http://localhost:8080',//基础网络
    //已经利用代理了，这里就不需要再写baseUrl了
    timeout: 20000   //20秒钟
})

// axios请求拦截器
// 一般会进行token身份验证等   //需要将login排除在外
// 在 JavaScript 中，http.interceptors.request.use 是一个函数，它接收两个参数，这两个参数都是函数。第一个参数是在请求发送之前调用的回调函数，它接收一个配置对象 config，并允许你修改它。第二个参数是在请求出错时调用的回调函数，它接收一个错误对象 e，并允许你处理这个错误。

http.interceptors.request.use(
    //请求前先配置请求信息
    config => {
    //排除登录和注册请求操作
    if (config.url === 'api/login/user'||config.url==='api/register') {//如果是登录或注册请求则不需要配置

        return config;
    }
    // //如果不是则需要携带token，token值从userStore中获取//先获取userStore实例
    const userStore = useUserStore();
    const token = userStore.userInfo.token;
    if(userStore.userInfo.token){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
}, e => Promise.reject(e))




// axios响应式拦截器
// 一般进行错误的统一提示，token失效的处理等
// 拦截器，处理 HTTP 响应
http.interceptors.response.use(
    // 处理成功的响应
    res => res.data,
    // 处理错误的响应
    e => {
        // 利用弹窗进行统一错误提示
        console.log("响应错误拦截成功")
        ElMessage({type:'error',message:'很抱歉出现了错误'})

        // 提示 token 失效
        const userStore = useUserStore();
        if(userStore.userInfo.token === null){
            userStore.clearUserInfo()
            ElMessage({type:'error',message:'token失效，请重新登录'})
            router.push('/login')
        }
        // 将错误作为 rejected Promise 返回
        return Promise.reject(e)
    }
)

//总之，default 关键字在 export 语句中的作用是指定模块的默认导出，允许其他模块轻松地导入和使用该导出的值。在这种情况下，http 实例被设置为模块的默认导出，以便在其他地方复用。
export default http