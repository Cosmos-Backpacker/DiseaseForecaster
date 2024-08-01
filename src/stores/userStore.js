import {loginAPI, logoutApi} from '@/apis/userApi.js'
import {defineStore} from 'pinia'
import {registerAPI} from "@/apis/userApi.js";
//定义一个处理用户信息的方法

//相当于窗机爱你一个用户信息库，里面已经存好了用户信息，所以再调用的时候既可以直接获取用户信息，也可以直接通过使用里面的函数来进行获取用户信息
export const useUserStore = defineStore(
    'user',   //存储在浏览器中的localStorage的key
    () => {   //回调函数
        // 1. 定义管理用户数据的state
        const userInfo = ref({})


        const clearUserInfo = () => {  //清除用户信息
            userInfo.value = {}
        }


        const userLogin = async (account, password) => {
            const res = await loginAPI(account, password)
            console.log("登录返回结果", res)
            userInfo.value = res
            console.log(userInfo.value)
            return res
        }

        const userRegister = async (account, password, checkPassword) => {
            console.log(account, password, checkPassword)
            const res = await registerAPI(account, password, checkPassword)
            console.log("注册返回结果", res)
            console.log(registerInfo.value)
            return res
        }




        const userLogout = async () => {
            const res = await logoutApi()
            console.log("登出返回结果", res)
            clearUserInfo()
            return res
        }


        // 3. 以对象的格式把state和action return
        return {
            userInfo,
            userLogout,
            userLogin,
            userRegister,
            clearUserInfo,
        }
    }, {            //持久化存储，存储到了浏览器中的localStorage
        persist: true
    }
)