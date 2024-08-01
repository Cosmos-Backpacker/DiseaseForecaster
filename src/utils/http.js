import axios from 'axios'
import {ElMessage} from "element-plus";
import 'element-plus/theme-chalk/el-message.css'
import {useUserStore} from "@/stores/userStore.js";
import router from "@/router/index.js";

//利用Axios库创建一个自己的请求方法

// 创建axios实例
const http = axios.create({
    timeout: 20000 ,  //20秒钟
    withCredentials: true,
})

http.interceptors.request.use(
    //请求前先配置请求信息
    config => {
    //排除登录和注册请求操作
    if (config.url === 'api/login/user'||config.url==='api/register') {//如果是登录或注册请求则不需要配置

        return config;
    }
    const userStore = useUserStore();
    const token = userStore.userInfo.token;

    //使用Bearer前缀可以明确地告诉服务器，后续的字符串是一个令牌，而不是其他类型的认证信息
    if(userStore.userInfo.token){
        config.headers.Authorization = `Bearer ${token}`
    }

    return config
}, e => Promise.reject(e))




http.interceptors.response.use(
    // 处理成功的响应
    res => res.data,
    // 处理错误的响应
    e => {
        // 利用弹窗进行统一错误提示
        ElMessage({type:'error',message:'很抱歉系统出现了一些问题'})
        // 将错误作为 rejected Promise 返回
        return Promise.reject(e)
    }
)

//总之，default 关键字在 export 语句中的作用是指定模块的默认导出，允许其他模块轻松地导入和使用该导出的值。在这种情况下，http 实例被设置为模块的默认导出，以便在其他地方复用。
export default http