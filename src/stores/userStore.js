import {loginAPI} from '@/apis/user'
import { defineStore } from 'pinia'
import {registerAPI} from "@/apis/register.js";
//定义一个处理用户信息的方法

//相当于窗机爱你一个用户信息库，里面已经存好了用户信息，所以再调用的时候既可以直接获取用户信息，也可以直接通过使用里面的函数来进行获取用户信息
export const useUserStore = defineStore(
    'user',   //name
    () => {   //回调函数
        // 1. 定义管理用户数据的state
        const userInfo = ref({})
        //注册接口返回的数据保存在registerInfo中
        const registerInfo = ref({})

        // 2. 定义获取接口数据的action函数
        const getUserInfo = async (user) => {      //通过请求获得用户数据
            const res = await loginAPI(user)
            userInfo.value = res.data  //将返回的promise对象里的data值传给userInfo.value
            console.log("下面打印userInfo")
            console.log(res)
        }

     const clearUserInfo = () => {  //清除用户信息
            userInfo.value={}

     }


     //创建注册用户函数，并将数据保存到registerInfo中
     const registerUser=async (user)=>{

         const res = await registerAPI(user)
         registerInfo.value = res  //将返回的promise对象里的data值传给userInfo.value
         console.log("下面打印userInfo")
         console.log(res)
     }


        // 3. 以对象的格式把state和action return
        return {
            userInfo,
            registerInfo,
            getUserInfo,
            clearUserInfo,
            registerUser
        }
    }, {            //持久化存储，存储到了浏览器中的localStorage
        persist: true
    }
)