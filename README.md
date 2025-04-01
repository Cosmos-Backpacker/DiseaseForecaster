## 项目介绍

本项目是一个智慧医疗健康管理平台，旨在构建AI驱动的一站式健康管理平台，实现**预防-诊断-管理**全周期覆盖



**所有功能如下：**

![](https://pic1.imgdb.cn/item/67ebc8ea0ba3d5a1d7e91dd9.png)





项目搭建：

前端使用vue3+elementPlus，后端使用springboot3，同时使用到pyton中的fastApi进行小服务快速搭建，

数据库使用MySql和Neo4j

医学数据知识库来源于开源数据，





#### **智能医疗专家**

将医学知识库与AI大模型相结合，然后通过数据融合推荐算法给最佳答案，给用户专业精准的回复

底层大模型为DeepSeek，数据库为开源疾病数据库

![](https://pic1.imgdb.cn/item/67ebc9ed0ba3d5a1d7e91e0d.png)

![](https://pic1.imgdb.cn/item/67ebd6720ba3d5a1d7e93f87.png)





#### 心理健康咨询

通过主动问询算法让AI模拟人类心理咨询师进行问诊，同时收集用户更多的信息，等信息足够的时候会自动停止问诊，同时结合专业的心理学数据作为支撑

![](https://pic1.imgdb.cn/item/67ebcd9d0ba3d5a1d7e9239f.png)





#### 日程规划小能手

出来可以自己手动新增计划，查询计划外还支持日程规划小能手，你只需要简单描述一下，就可以立即帮你规划

![](https://pic1.imgdb.cn/item/67ebcf080ba3d5a1d7e92837.png)

​	![](https://pic1.imgdb.cn/item/67ebd2850ba3d5a1d7e93348.png)

![](https://pic1.imgdb.cn/item/67ebd29f0ba3d5a1d7e93397.png)





#### **健康问卷测试**

使用SF-36健康问卷调查，填写好问卷之后将答案返回给后端分析，同时通过AI辅助给出最终分析结果以及建议



![](https://pic1.imgdb.cn/item/67ebca880ba3d5a1d7e91e1c.png)

![](https://pic1.imgdb.cn/item/67ebcad20ba3d5a1d7e91e2a.png)

![](https://pic1.imgdb.cn/item/67ebd6f70ba3d5a1d7e9410c.png)

结果

![](https://pic1.imgdb.cn/item/67ebcbaa0ba3d5a1d7e91e5a.png)

#### 疾病知识查询

通过springboot访问本地的PYTHON项目的API来查询

python项目是利用fastAPI来搭建

![](https://pic1.imgdb.cn/item/67ebcbd40ba3d5a1d7e91e64.png)

![](https://pic1.imgdb.cn/item/67ebd7260ba3d5a1d7e941be.png)





#### 3D知识图谱展示

前端直接调用neo4j知识库，同时利用3d-force-graph来渲染数据

![](https://pic1.imgdb.cn/item/67ebcc720ba3d5a1d7e91fbc.png)

![](https://pic1.imgdb.cn/item/67ebccd10ba3d5a1d7e920f9.png)





#### MBTI人格测试

与健康问卷类似，用户回答问题完成之后会利用算法进行评分同时利用DeepSeek进行答案的分析，并给出建议

![](https://pic1.imgdb.cn/item/67ebd2c50ba3d5a1d7e9340d.png)

![](https://pic1.imgdb.cn/item/67ebd3390ba3d5a1d7e9358b.png)

![](https://pic1.imgdb.cn/item/67ebd34c0ba3d5a1d7e935cd.png)







#### 与AI语音通话

通过讯飞的语音合成技术和语音识别技术加上讯飞星火大模型打造出来的排忧解难小助手功能，可以倾听您的诉求和想法陪你聊天

![](https://pic1.imgdb.cn/item/67ebd36d0ba3d5a1d7e93633.png)







#### 趣味聊天机器人小思

接入小思机器人实现

![](https://pic1.imgdb.cn/item/67ebd3dc0ba3d5a1d7e93781.png)





#### 可视化大屏监测

接入腾讯云可视化大屏展示，展示平台功能以及用户人数，活跃情况

![](https://pic1.imgdb.cn/item/67ebd5670ba3d5a1d7e93c67.png)