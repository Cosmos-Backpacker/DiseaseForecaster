import {createRouter, createWebHistory} from 'vue-router'
//一级路由
import Layout from '@/views/Layout/index.vue'
import Login from '@/views/Login/index.vue'

//二级路由
import Home from '@/views/Home/index.vue'
import DiseasePrediction from '@/views/SubRoutes/DiseasePrediction/index.vue'
import MedicalAssistants from '@/views/SubRoutes/MedicalAssistants/index.vue'
import Rendering_3D from '@/views/SubRoutes/Rendering_3D/index.vue'
import DiseaseInquiry from '@/views/SubRoutes/DiseaseInquiry/index.vue'
import PlaneDevelopment from '@/views/SubRoutes/PlanDevelopment/index.vue'
import UserCenter from '@/views/SubRoutes/UserCenter/index.vue'


//三级路由
import UserInfo from '@/views/SubRoutes/UserCenter/components/UserInfo.vue'
import UserHeartData from '@/views/SubRoutes/UserCenter/components/UserHeartData.vue'
import MedicalAssistantsContainer from "@/views/SubRoutes/MedicalAssistants/components/MedicalAssistantsContainer.vue";
import Robot from "@/views/SubRoutes/MedicalAssistants/components/Robot.vue";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: Layout,
            children: [     //在Layout里面渲染下面两个二级路由
                {
                    //首页面二级路由
                    path: '',
                    component: Home
                },
                {
                    //疾病预测二级路由
                    path: 'DiseasePrediction',
                    component: DiseasePrediction
                }, {
                    //医疗助手二级路由
                    path: 'MedicalAssistants',
                    component: MedicalAssistants,
                    children: [
                        {//三级路由,智能AI
                            path: '',
                            component: MedicalAssistantsContainer
                        },
                        {
                            //三级路由小机器人
                            path: 'Robot',
                            component: Robot
                        }

                    ]
                },
                {
                    //3D渲染界面
                    path: 'Rendering_3D',
                    component: Rendering_3D
                },
                {
                    //疾病查询页面
                    path: 'DiseaseInquiry',
                    component: DiseaseInquiry
                }, {
                    //计划制定二级路由
                    path: 'PlaneDevelopment',
                    component: PlaneDevelopment
                },
                {
                    //二级路由用户中心
                    path: 'userCenter',
                    component: UserCenter,
                    children: [
                        {
                            //三级路由
                            path: '',
                            component: UserInfo,

                        },
                        {
                            path: 'userHeartData',
                            component: UserHeartData
                        }
                    ]
                }

            ]
        },
        {
            //一级路由登录页面
            path: '/login',
            component: Login

        },

    ]
})

export default router
