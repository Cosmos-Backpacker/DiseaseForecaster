<template>
  <el-container>
    <el-header>
      <!-- 查询疾病，插入表单 -->
      <el-form :inline="true" :model="formInline" class="demo-form-inline">
        <el-form-item label="输入">
          <el-input
              v-model="formInline.name"
              placeholder="输入疾病名称"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Inquire(formInline.name)">查询</el-button>
        </el-form-item>
      </el-form>
    </el-header>
  </el-container>

  <el-container>
    <el-aside width="150px">
      <!-- 常见疾病列表 -->
      <el-tabs :tab-position="tabPosition" style="height: 600px" @tab-click="InquireHandleClick">
        <el-tab-pane label="常见疾病列表"></el-tab-pane>
        <el-tab-pane label="高血压" name="高血压" @click="Inquire('高血压')"></el-tab-pane>
        <el-tab-pane label="高血脂" name="高血脂" @click="Inquire('高血脂')"></el-tab-pane>
        <el-tab-pane label="冠心病" name="冠心病" @click="Inquire('冠心病')"></el-tab-pane>
        <el-tab-pane label="慢性肠胃炎" name="慢性肠胃炎" @click="Inquire('慢性肠胃炎')"></el-tab-pane>
        <el-tab-pane label="慢性心衰" name="慢性心衰" @click="Inquire('慢性心衰')"></el-tab-pane>
        <el-tab-pane label="慢性咽炎" name="慢性咽炎" @click="Inquire('慢性咽炎')"></el-tab-pane>
        <el-tab-pane label="慢性支气管炎" name="慢性支气管炎" @click="Inquire('慢性支气管炎')"></el-tab-pane>
        <el-tab-pane label="糖尿病" name="糖尿病" @click="Inquire('糖尿病')"></el-tab-pane>
        <el-tab-pane label="心脏病" name="心脏病" @click="Inquire('心脏病')"></el-tab-pane>
      </el-tabs>
    </el-aside>

    <el-container>
      <el-main>
        <div class="container">
          <div style="display: flex;justify-content: flex-start;">
            <!-- 疾病介绍区域 -->
            <div class="box1">
              <el-row :gutter="20">
                <el-col :span="24">
                  <h2>疾病介绍</h2>
                  <p>心脏病,熟悉一下：心脏病(heartdisease)是心脏疾病的总称，包括风湿性心脏病、先天性心脏病、高血压性心脏病、冠心病、心肌炎等各种心脏病。<br>
                    一、先天性心脏病<br>1.可能由于母亲在怀孕早期的疾病或服用的药物有关。<br>2.与遗传有关。<br>二、后天性心脏病<br>1.冠状动脉心脏病：抽烟及糖尿病，高血压等导致血管硬化狭窄，使血流受阻，易使心肌缺氧而受损。
                    <br>2.高血压性心脏病：动脉性高血压导致左心室肥大，肺高压症导致右心室肥大。<br>三、风湿性心脏病：慢性风湿性心脏病主要在风湿热感染后，心脏瓣膜逐渐病变所导致之异常。<br>四、肺性心脏病：因慢性支气管炎，肺气肿等导致肺动脉高血压症，使得右心室肥大或衰竭。<br>五、心肌病：新陈代谢或荷尔蒙异常的心肌变化等，有时酗酒，药物亦导致心肌变化。<br>六、心脏肿瘤：大多为良性肿瘤，以黏液瘤为最常见，原发性心脏恶性肿瘤很少见。<br>
                  </p>
                </el-col>
              </el-row>
            </div>
            <!-- 右侧区域 -->
            <div style="flex: 1; display: flex; flex-direction: column;">
              <!-- 症状区域 -->
              <div class="box2">
                <el-row :gutter="20">
                  <el-col :span="24">
                    <h2>症状</h2>
                    <p>
                      心脏病的症状包括：心肾阳虚；卵圆窝未闭；心动过速；心脏杂音；心悸；浮肿；小指不明原因痛；胸痛伴胸闷、心悸<br>
                    </p>
                  </el-col>
                </el-row>
              </div>
              <!-- 病因区域 -->
              <div class="box2">
                <el-row :gutter="20">
                  <el-col :span="24">
                    <h2>病因</h2>
                    <p>心脏病可能的病因有：胆固醇过高，患心脏疾病的机会比普通人多三倍，因为体内过多的胆固醇会积聚在血管内，使血管日渐狭窄，妨碍血液流通。<br>吸烟人士比普通人的机会多两倍半，原因是香烟中的尼古丁或烟草化学物质会损害心脏血管,若血管出现裂痕，胆固醇便会积聚起来。<br>血压高，比常人的机会多两倍半，血压高会使到血管收缩。<br>糖尿病女性患者有心脏病的机会比一般人多一倍,男性多百分之五十。<br>过分肥胖，因为肥胖引致血压高，血脂肪过高,糖尿病,而这些疾病又会诱发心脏病。<br>生活紧张，神经紧张令心律失常,内分泌失调，影响心跳,刺激心脏病发，正常的心脏比拳头大一点，透过循环系统不停泵送血液。心脏每天平均跳十万次，泵送约二千加仑的血液来回体内。若以七十岁的寿命来计算，心跳可达二十五亿次。<br>
                    </p>
                  </el-col>
                </el-row>
              </div>
            </div>
          </div>


          <!-- 并发症区域 -->
          <el-row>
            <el-col :span="6" class="section ">
              <h2>并发症</h2>
              <p>心脏病的相关并发症包括：先天性再生障碍性贫血；肺结核；脑梗塞；高原红细胞增多症；缺血性结肠炎；妊娠合并哮喘；老年人吸入性肺炎；高血压肾病；小儿结核病；感应性妄想性障碍；老年人急进性肾炎；感染性疾病所致贫血<br>
              </p>
            </el-col>
            <!-- 饮食 -->
            <el-col :span="6" class="section">
              <h2>饮食注意</h2>
              <h4>宜食</h4>
              <p>素炒小白菜;豌豆绿豆粥;五香蒜苔;素炒菠菜;苦瓜粥;马兰头菜粥;银耳冬瓜羹;芥菜炒蚕豆<br></p>

              <h4>忌食</h4>

              <p>鸡肉；鸡腿；鹌鹑蛋；白酒<br></p>
            </el-col>
            <!-- 预防 -->
            <el-col :span="10" class="section">

              <h2>如何预防</h2>
              <p>第一、减肥。肥胖者患心脏病的比例远远高于正常体重的人，特别是“苹果形”身材(腰臀肥胖)的人更危险。只要老人减肥3—5公斤，心脏状况就会有很大改善。同时，专家告诫较胖的老人，不要指望自己一下子变成超级模特，要通过平衡饮食和锻炼逐渐达到减肥的目的。<br>第二、少吃蛋黄。一个普通大小的蛋黄约含胆固醇200毫克。老人胆固醇较高的话，一周最多只能吃两个蛋黄。<br>第三、多运动。每天适度运动20分钟，可使患心脏病的几率减少30%，快走的效果最好。<br>第四、戒烟。吸烟者患心脏病的比例是不吸烟者的两倍。研究发现，戒烟2—3年后，患心脏病的风险就会降至与不吸烟者一样的水平。<br>第五、注意饮食。平时生活中坚持吃低脂肪食品，如瘦肉和低脂乳制品等。<br>第六、适量饮酒。1周喝3—9杯酒为适量，对心脏有好处。但要注意别贪杯，因为饮酒过度会引发心脏病。<br>第七、当心糖尿病。有糖尿病的人患心脏病的比例是其他人的4倍。因此，老人要定期体检，对糖尿病“早发现，早治疗”。<br>第八、控制情绪。脾气暴躁，遇到突发事件不能控制自己，也容易诱发心脏病。<br></p>

            </el-col>

          </el-row>

          <!-- 具体展示区域 -->
          <el-row>
            <el-col :span="23" class="section">
              <h2>综合治疗与诊断指南</h2>
              <p> 如何治疗：心脏病可以尝试如下治疗：手术治疗;介入治疗;药物治疗;支持性治疗<br> 治疗时间：心脏病治疗可能持续的周期为：1-3个月<br> 治好概率：心脏病治愈的概率为（仅供参考）：30%<br> 易发病人群：心脏病的易感人群包括：无特殊人群<br> 需要检查项目：心脏病通常可以通过以下方式检查出来：胸部平片；心音图检查；心脏血管超声检查；多普勒超声心动图；心电图；心血管MRI检查；红细胞磷丙糖异构酶；胸部MRI；二维超声心动图；冠状动脉造影<br></p>
            </el-col>
          </el-row>
        </div>
      </el-main>
    </el-container>
  </el-container>


</template>
<script setup>


import DiseaseInquiry from "@/apis/DiseaseInquire.js";

let tabPosition = "left"
let activeName = "first"
const formInline = reactive({
  name: ""
})

let answer = ref("")
const answerList = ref([]);
let showAnswer = ref("")

async function Inquire(question) {
  const res = await DiseaseInquiry(question)

  answer.value = res.data

  // answer.value = answer.value.replaceAll(/\r/g, '')
  //     .replaceAll(', ', '')
  //     .replaceAll('=', ':')

  console.log(answer.value)
  answer.value = answer.value.replaceAll(/\n/gm, "<br>")
      .replaceAll('=', ':');

  // answerList.value=answer.value.match(/start(.*?)end/gm)
  // console.log(answerList.value)

  const pattern = /start(.*?)end/g;
  let matches;

// 存储提取的文本


  //每次存储前清空列表
  answerList.value = []

// 循环匹配所有段落
  while ((matches = pattern.exec(answer.value)) !== null) {
    // matches[1] 是第一个捕获组，即"start"和"end"之间的文本
    answerList.value.push(matches[1]);
  }
  console.log(answerList.value)
//获取完成之后自动将第一个答案赋值给showAnswer
  showAnswer.value = answerList.value[0]
  // answerObj = JSON.parse('"' + answer.value.replaceAll(/\r/g, '')
  //         .replaceAll(', ', '')
  //         .replaceAll('=', ':')
  //
  //     + '"')


}


function handleClick(tab) {
  activeName = tab.paneName
  console.log(activeName)
  switch (activeName) {
    case "first":
      showAnswer.value = answerList.value[0]
      break;
    case "second":
      showAnswer.value = answerList.value[1]
      break
    case "third":
      showAnswer.value = answerList.value[2]
      break
    case "fourth":
      showAnswer.value = answerList.value[3]
      break
    case "fifth":
      showAnswer.value = answerList.value[4]
      break
    case "sixth":
      showAnswer.value = answerList.value[5]
      break
    case "seventh":
      showAnswer.value = answerList.value[6]
      break
    case "eighth":
      showAnswer.value = answerList.value[7]
      break
    case "ninth":
      showAnswer.value = answerList.value[8]
      break
    case "tenth":
      showAnswer.value = answerList.value[9]
      break
    case "eleventh":
      showAnswer.value = answerList.value[10]
      break
    case "twelfth":
      showAnswer.value = answerList.value[11]
      break
    case "thirteenth":
      showAnswer.value = answerList.value[12]
      break
  }
}

function InquireHandleClick(tab) {
  Inquire(tab.paneName)
}
</script>

<style scoped>
.box1 {
  flex: 1;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 8px 6px rgba(0, 0, 0, 0.1);
  padding: 30px;
  margin: 20px;
}

.box2 {
  flex: 1;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 0 8px 6px rgba(0, 0, 0, 0.1);
  padding: 30px;
  margin: 20px;
}

.section {
  margin-top: 20px;
  margin-left: 20px;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 8px 6px rgba(0, 0, 0, 0.1);
  background-color: #ffffff;
}

.container {
  margin-left: 15px;
  margin-top: -30px;
}


/* 通用盒子样式 */
.box1, .box2, .section {
  transition: box-shadow 0.3s ease-in-out, transform 0.3s ease-in-out; /* 平滑过渡效果 */
  cursor: pointer; /* 鼠标悬停时显示手型指针 */
}

/* 鼠标悬停时的样式 */
.box1:hover, .box2:hover, .section:hover {
  box-shadow: 0 12px 15px rgba(0, 0, 0, 0.2); /* 增加阴影大小 */
  transform: translateY(-5px); /* 向上移动 */
}

/* 为.box1, .box2, .section添加边框 */
.box1, .box2, .section {
  border: 2px solid transparent; /* 默认透明边框 */
}

/* 鼠标悬停时显示边框 */
.box1:hover, .box2:hover, .section:hover {
  border: 2px solid #409EFF; /* 悬停时的边框颜色，这里使用了Element UI的主题色 */
}


</style>