import {createRouter, createWebHistory} from 'vue-router'
//一级路由
import Layout from '@/views/Layout/index.vue'

import NotFound from "@/views/NotFound/NotFound.vue";
import Home from '@/views/Home/index.vue'


//二级路由

import DiseasePrediction from '@/views/SubRoutes/DiseasePrediction/index.vue'
import MedicalAssistants from '@/views/SubRoutes/MedicalAssistants/index.vue'
import Rendering_3D from '@/views/SubRoutes/Rendering_3D/index.vue'
import DiseaseInquiry from '@/views/SubRoutes/DiseaseInquiry/index.vue'
import PlaneDevelopment from '@/views/SubRoutes/PlanDevelopment/index.vue'
import UserCenter from '@/views/SubRoutes/UserCenter/index.vue'
import VisualScreen from "@/views/SubRoutes/visualScreen.vue";
import moreFunctions from '@/views/SubRoutes/moreFunctions/index.vue'
import MbtiTest from '@/views/SubRoutes/MbtiTest/index.vue'
import PsyConsultant from "@/views/SubRoutes/psyConsultant/index.vue";

//三级路由
import UserInfo from '@/views/SubRoutes/UserCenter/components/UserInfo.vue'
import UserHeartData from '@/views/SubRoutes/UserCenter/components/UserHeartData.vue'
import MedicalAssistantsContainer from "@/views/SubRoutes/MedicalAssistants/components/MedicalAssistantsContainer.vue";
import Robot from "@/views/SubRoutes/moreFunctions/subViews/Robot.vue";
import Ocr from "@/views/SubRoutes/MedicalAssistants/components/Ocr.vue";
import ImageU from "@/views/SubRoutes/MedicalAssistants/components/ImageU.vue";
import heartDisease from '@/views/SubRoutes/DiseasePrediction/heartComponent/heartDisease.vue'
import Diabetes from '@/views/SubRoutes/DiseasePrediction/diabetesComponent/diabetes.vue'
import HealthQuestionnaire from "@/views/SubRoutes/DiseasePrediction/HealthQuestionnaire/HealthQuestionnaire.vue";
import HealthResult from "@/views/SubRoutes/DiseasePrediction/HealthQuestionnaire/HealthResult.vue";
import FullTest from '@/views/SubRoutes/MbtiTest/subViews/fullTest.vue'
import MbtiResult from "@/views/SubRoutes/MbtiTest/subViews/MbtiResult.vue";

import LittleTroubleshooter from '@/views/SubRoutes/LittleTroubleshooter/index.vue'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            component: Layout,
            children: [     //在Layout里面渲染下面两个二级路由

                {
                    path: '',
                    component: VisualScreen
                },
                {
                    //疾病预测二级路由
                    path: 'DiseasePrediction',
                    component: DiseasePrediction,
                    //三级路由
                    children: [
                        {
                            path: '',
                            component: heartDisease
                        },
                        {
                            path: '/Diabetes',
                            component: Diabetes
                        },
                        {
                            path: '/HealthQuestionnaire',
                            component: HealthQuestionnaire
                        },
                        {
                            path: '/HealthResult',
                            component: HealthResult
                        },
                    ]
                }, {
                    //医疗助手二级路由
                    path: 'MedicalAssistants',
                    component: MedicalAssistants,
                    children: [
                        {//三级路由,智能AI
                            path: 'MedicalAssistantsContainer',
                            component: MedicalAssistantsContainer
                        },

                        {
                            path: 'Ocr',
                            component: Ocr
                        },
                        {
                            path: 'ImageU',
                            component: ImageU
                        },
                        {
                            path: '',
                            component: ()=>import('@/views/SubRoutes/MedicalAssistants/components/DeepSeek.vue')
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
                    //更多功能二级路由
                    path: 'moreFunctions',
                    component: moreFunctions
                },

                {
                    //MBTI测试二级路由
                    path: 'MbtiTest',
                    component: MbtiTest,
                },

                {   //伪子路由
                    path: 'MbtiTest/FullTest',
                    component: FullTest,
                },
                {   //伪子路由
                    path: 'MbtiTest/MbtiResult',
                    component: MbtiResult,
                },
                {   //心理辅导二级路由
                    path: 'PsyConsultant',
                    component: PsyConsultant,
                },

                {   //心理辅导二级路由
                    path: 'LittleTroubleshooter',
                    component: LittleTroubleshooter,
                },

                {
                    //二级路由小机器人
                    path: 'Robot',
                    component: Robot
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
            path: '/Home',
            component: Home,
        },
        {
            //登录页
            path: '/login',
            component: () => import('@/pages/loginPage.vue')

        },
        {
            path: '/notFound',
            component: NotFound
        }

    ]
})

export default router
