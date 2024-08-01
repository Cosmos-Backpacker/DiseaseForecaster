import './styles/common.scss'
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import {getCategoryAPI} from "@/apis/layout.js";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import http from "@/utils/http.js";
import {registerAPI} from "@/apis/register.js";
import {BigModel} from "@/apis/xfBigModel.js";
import {loginAPI} from "@/apis/user.js";
import {szRobot} from "@/apis/szRobot.js";
import heartPdfAPI from "@/apis/heartPdf.js";


const app = createApp(App)
const pinia = createPinia();
app.use(pinia)
pinia.use(piniaPluginPersistedstate)
app.use(router)
//----------------test-------------------
// const user=ref({
//     account: 'test',
//     password: 'ww11111'
// })
// let res=ref({})
//  res=registerAPI(user)
// console.log(res)

// console.log("下面开始测试API")
// const test=async ()=>{
//     const res=await heartPdfAPI(1)
// }
// test()



//----------------test-------------------

app.mount('#app')
