import './styles/common.scss'
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import {getCategoryAPI} from "@/apis/layout.js";
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'
import http from "@/utils/http.js";
import {registerAPI} from "@/apis/register.js";


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

//----------------test-------------------

app.mount('#app')
