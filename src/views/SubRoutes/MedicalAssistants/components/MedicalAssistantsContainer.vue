<template>
  <div>
    <div class="box">
      <div class="title">
        <img src="" alt class="logo" />
        <span class="title-hn">AI医疗机器人</span>
      </div>
      <div id="content" class="content">
        <div v-for="(item,index) in info" :key="index">
          <div class="info_r info_default" v-if="item.type === 'left info'">
            <span class="circle circle_r"></span>
            <div class="con_r con_text">
              <div>{{item.content}}</div>
              <div v-for="(item2,index) in item.question" :key="index">
                <div class="con_que" @click="clickRobot(item2.content,item2.id)">
                  <div class="czar-question-msg">
                    {{item2.index}}
                    {{item2.content}}
                  </div>
                </div>
              </div>
            </div>
            <div class="time_r">{{item.time}}</div>
          </div>
          <div class="info_l" v-if="item.type === 'right info'">
            <div class="con_r con_text">
              <span class="con_l">{{item.content}}</span>
              <span class="circle circle_l">
                    <img src class="pic_l" />
                  </span>
            </div>
            <div class="time_l">{{item.time}}</div>
          </div>
        </div>
      </div>
      <div class="set problem">
            <textarea
                placeholder="请输入您的问题..."
                style="height: 68px;width: 100%;resize:none;padding-right:80px;outline: none;border-color:#ccc;border-radius:5px;"
                id="text"
                v-model="customerText"
                @keyup.enter="sentMsg()"
            ></textarea>
        <el-button @click="sentMsg()" class="buttons end">
          <span style="vertical-align: 4px;">发送</span>
        </el-button>
      </div>
    </div>
    <div class="article">
      <!-- 三级路由的挂载点 -->
      <RouterView />
    </div>

  </div>
</template>

<script>
export default {
  data() {
    return {
      customerText: "",
      info: [
        {
          type: 'left info',
          time: this.getTodayTime(),
          name: "robot",
          content:"您好，欢迎使用!",
          question: [
            { id: 1, content: "最近头疼身体不舒服可能是什么问题", index: 1 },
            { id: 2, content: "经常睡眠不足，食欲不振是因为什么，有什么解决办法？", index: 2 },
            { id: 3, content: "换季皮肤红肿出现红斑可能是因为什么？", index: 3 },
            {id: 4,content: "婴儿发烧时应及时做什么措施？",index: 4 },
            { id: 5, content: "经常熬夜吃垃圾食品的危害", index: 5 }
          ]
        }
      ],
      timer: null,
      robotQuestion: [
        { id: 1, content: "最近头疼身体不舒服可能是什么问题", index: 1 },
        { id: 2, content: "经常睡眠不足，食欲不振是因为什么，有什么解决办法？", index: 2 },
        { id: 3, content: "换季皮肤红肿出现红斑可能是因为什么？", index: 3 },
        { d: 4, content: "婴儿发烧时应及时做什么措施？", index: 4 },
        { id: 5, content: "经常熬夜吃垃圾食品的危害", index: 5 },
      ],
      robotAnswer: [
        { id: 1,content:"1、过度疲劳：长时间的工作、学习或运动，以及精神压力过大，都可能导致身体疲劳，进而引发头疼和身体不适。此时，适当的休息和放松是缓解这些症状的有效方法。\n" +
              "2、上呼吸道感染：如果免疫力较弱，上呼吸道可能受到病毒或细菌的感染，导致身体出现炎症反应，如发热、头疼、乏力等。这种情况下，可能需要服用解热镇痛药物来缓解症状。\n" +
              "3、睡眠不足：长期熬夜或睡眠质量不佳，可能导致头疼和身体不适。保持充足的睡眠和规律的作息，有助于改善这些症状。\n" +
              "4、血压问题：高血压或低血压都可能导致头疼和身体不适。高血压可能由于血管压力过高引起，而低血压则可能由于体内循环动脉压力低于正常范围导致器官供血不足。\n" +
              "5、神经系统问题：如偏头痛或紧张性头痛，这类疼痛可能会伴随恶心、呕吐等症状，使得患者感到浑身难受。\n" +
              "6、其他疾病：一些其他疾病，如脑梗死、颈椎间盘突出症等，也可能导致头疼和身体不适。这些疾病通常需要专业医生的诊断和治疗。",index: 1},
        { id: 2, content: "经常睡眠不足和食欲不振可能由多种原因造成，这些原因可能涉及生理、心理和环境等多个方面。以下是一些可能的原因：\n" +
              "\n" +
              "压力与焦虑：现代生活中，工作和学习的压力以及日常生活中的各种焦虑情绪，可能导致人们难以入睡或睡眠质量下降。同时，这些负面情绪也会影响食欲，导致食欲不振。\n" +
              "不良生活习惯：不规律的作息时间、晚上过度使用电子设备、饮用咖啡或茶等含有咖啡因的饮料，都可能影响睡眠。而饮食不规律、过度节食或暴饮暴食，也可能导致食欲不振。\n" +
              "环境因素：居住环境的噪音、光线、温度等因素，都可能影响睡眠质量。同时，环境污染、食品安全问题也可能影响食欲。\n" +
              "健康问题：某些疾病或身体状况，如神经衰弱、抑郁症、消化系统疾病等，都可能同时导致睡眠不足和食欲不振。此外，营养不良、贫血、内分泌失调等也可能影响食欲和睡眠。\n" +
              "针对这些问题，可以尝试以下方法来改善睡眠和食欲：\n" +
              "\n" +
              "调整作息：保持规律的作息时间，尽量在晚上避免使用电子设备，保持安静、舒适的睡眠环境。\n" +
              "改善饮食习惯：保持饮食均衡，避免过度节食或暴饮暴食，尽量吃新鲜、健康的食物。\n" +
              "增加运动：适当的运动可以帮助改善睡眠和食欲，同时也有助于缓解压力和焦虑情绪。\n" +
              "寻求专业帮助：如果问题持续存在或加重，建议及时就医，寻求专业医生的诊断和治疗。", index: 2 },
        { id: 3, content: "季节变化引起的过敏：季节交替时，气温、湿度等环境因素的变化可能导致皮肤无法适应，从而引发过敏症状。这包括荨麻疹和季节性皮炎等，表现为皮肤出现红斑、丘疹，并伴有瘙痒。此时，应避免用手抓挠，保持局部清洁，并在医生指导下使用抗过敏药物，如依巴斯汀片、氯雷他定胶囊等。\n" +
              "皮肤角质层过薄：季节变化可能导致皮肤角质层过薄，使皮肤屏障功能受损，从而引发红斑等症状。这种情况下，建议使用维生素E乳膏、尿素软膏等药物来修复皮肤屏障，并多吃富含维生素的蔬菜和水果，如胡萝卜、猕猴桃等。\n" +
              "紫外线过敏：春季紫外线逐渐增强，皮肤对紫外线的敏感性可能增高，导致皮肤发生变性、分解，出现红斑等症状。对于紫外线过敏的人群，出门前一定要做好防护措施，如涂抹防晒霜、戴帽子等。\n" +
              "食物过敏：某些食物也可能引起皮肤过敏反应，如海鲜、水果等。有食物过敏史的人应特别注意饮食，避免食用可能引起过敏的食物。", index: 3 },
        { id: 4,content: "首先，要确保宝宝的居住环境舒适，空气流通。这有助于降低室内的温度，使宝宝感到更加舒适。\n" +
              "\n" +
              "接下来，要密切观察宝宝的症状和全身状态。如果宝宝四肢及手脚温热且全身出汗，表示需要散热，这时可以适当减少衣物，帮助宝宝散热。同时，用温湿的毛巾轻轻擦拭宝宝的额头、颈部、腋下和腹股沟等部位，有助于降低体温。\n" +
              "\n" +
              "如果宝宝体温较高，可以使用退热贴贴在宝宝的额头、太阳穴等部位，这有助于缓解宝宝的不适感。但请注意，如果宝宝对退热贴过敏，应立即停止使用。\n" +
              "\n" +
              "此外，保持宝宝的水分摄入也非常重要。发烧会导致宝宝体内水分大量流失，因此要确保宝宝喝足够的水或其他适合的液体，以补充体内水分，并促进毒素排出。\n" +
              "\n" +
              "如果宝宝的体温超过38.5℃，或者出现持续高烧、精神萎靡等严重症状，建议及时就医，以免延误治疗。在医生的指导下，可以使用适当的退烧药来降低宝宝的体温。但请务必遵循医生的建议和剂量，避免药物过量或不当使用。\n" +
              "\n" +
              "最后，家长在照顾发烧的宝宝时，要保持冷静和耐心，密切关注宝宝的变化，并根据实际情况采取相应的措施。同时，也要注意自身的卫生和防护，避免交叉感染。",index: 4 },
        { id: 5, content: "经常熬夜和吃垃圾食品对人体健康都有明显的危害，二者同时发生可能进一步加剧这些危害。以下是关于这两者的具体危害：\n" +
              "\n" +
              "经常熬夜的危害：\n" +
              "\n" +
              "内分泌紊乱：熬夜会打破人体的生物钟，导致内分泌紊乱，影响身体的正常代谢。\n" +
              "降低肝脏解毒效率与胃的消化能力：长期熬夜会使肝脏和胃得不到充足的休息时间，降低其解毒和消化能力，毒素和食物的堆积会对这些器官造成伤害。\n" +
              "负面情绪增加：经常熬夜可能会导致情绪不稳定，增加焦虑和抑郁等负面情绪，这些情绪又可能进一步影响睡眠质量和食欲。\n" +
              "吃垃圾食品的危害：\n" +
              "\n" +
              "消化不良：垃圾食品通常难以消化，长期食用会加重胃肠道负担，引起消化不良、胃食管反流等问题。\n" +
              "营养匮乏：垃圾食品往往缺乏人体所需的营养素，长期食用可能导致营养不良，影响身体的正常发育和功能。\n" +
              "增加肝脏解毒与代谢负担：垃圾食品中的有害物质需要肝脏进行解毒和代谢，长期食用会增加肝脏的负担，甚至导致肝脏损伤。\n" +
              "当经常熬夜和吃垃圾食品这两个不良习惯同时存在时，可能会对人体造成更大的危害。例如，熬夜时吃垃圾食品可能会进一步加重胃肠道负担，导致更严重的消化问题；同时，由于肝脏和胃在熬夜时已经处于疲惫状态，再加上垃圾食品的毒素负担，可能使这些器官受到更严重的损伤。", index: 5 },
      ]
    }
  },
  created() {
    this.showTimer();
  },
  methods: {
    // 用户发送消息
    sentMsg() {
      clearTimeout(this.timer)
      this.showTimer()
      let text = this.customerText.trim()
      if (text !== '') {
        var obj = {
          type: 'right info',
          time: this.getTodayTime(),
          content: text,
        }
        this.info.push(obj)
        this.appendRobotMsg(this.customerText)
        this.customerText = ''
        this.$nextTick(() => {
          var contentHeight = document.getElementById('content')
          contentHeight.scrollTop = contentHeight.scrollHeight
        })
      }
    },
    // 机器人回答消息
    appendRobotMsg(text) {
      clearTimeout(this.timer)
      this.showTimer()
      text = text.trim()
      let answerText = ''
      let flag;
      for (let i = 0; i < this.robotAnswer.length; i++) {
        if (this.robotAnswer[i].content.indexOf(text) !== -1) {
          flag = true
          answerText = this.robotAnswer[i].content
          break
        }
      }
      if (flag) {
        let obj = {
          type: "left info",
          time: this.getTodayTime(),
          name: "robot",
          content: answerText,
          question: [],
        }
        this.info.push(obj)
      } else {
        answerText = "您可能想问："
        let obj = {
          type: 'left info',
          time: this.getTodayTime(),
          name: "robot",
          content: answerText,
          question: this.robotQuestion,
        }
        this.info.push(obj)
      }
      this.$nextTick(() => {
        var contentHeight = document.getElementById('content')
        contentHeight.scrollTop = contentHeight.scrollHeight
      })
    },
    sentMsgById(val, id) {
      clearTimeout(this.timer)
      this.showTimer()
      let robotById = this.robotAnswer.filter((item) => {
        return item.id === id;
      })
      let obj_l = {
        type: 'left info',
        time: this.getTodayTime(),
        name: 'robot',
        content: robotById[0].content,
        question: [],
      };
      let obj_r = {
        type: 'right info',
        time: this.getTodayTime(),
        name: 'robot',
        content: val,
        question: [],
      };
      this.info.push(obj_r)
      this.info.push(obj_l)
      this.$nextTick(() => {
        var contentHeight = document.getElementById('content')
        contentHeight.scrollTop = contentHeight.scrollHeight
      })
    },
    // 点击机器人的单个问题
    clickRobot(val, id) {
      this.sentMsgById(val, id)
    },
    // 结束语
    endMsg() {
      let happyEnding = {
        type: 'left info',
        time: this.getTodayTime(),
        content: "退出",
        question: [],
      };
      this.info.push(happyEnding)
      this.$nextTick(() => {
        var contentHeight = document.getElementById('content')
        contentHeight.scrollTop = contentHeight.scrollHeight
      })
    },
    showTimer() {
      this.timer = setTimeout(this.endMsg, 1000*60)
    },
    getTodayTime() {
      // 获取当前时间
      var day = new Date()
      let seconds = day.getSeconds()
      if (seconds < 10) {
        seconds = "0" + seconds
      } else {
        seconds = seconds
      }
      let minutes = day.getMinutes()
      if (minutes < 10) {
        minutes = "0" + minutes
      } else {
        minutes = minutes
      }
      let time =
          day.getFullYear() +
          "-" +
          (day.getMonth() + 1) +
          "-" +
          day.getDate() +
          " " +
          day.getHours() +
          ":" +
          minutes +
          ":" +
          seconds
      return time
    }
  }
}

</script>
<style scoped>
.box {
  width: 100%;
  height: 500px;
  background-color: #fafafa;
  position: relative;
  padding: 20px;
}
#content {
  height: 340px;
  overflow-y: scroll;
  font-size: 14px;
  width: 100%;
}
.circle {
  display: inline-block;
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background-color: #eff1f3;
}
.con_text {
  color: #333;
  margin-bottom: 5px;
}
.con_que {
  color: #1c88ff;
  height: 30px;
  line-height: 30px;
  cursor: pointer;
}
.info_r {
  position: relative;
}
.circle_r {
  position: absolute;
  left: 0%;
}
.pic_r {
  width: 17px;
  height: 17px;
  margin: 8px;
}
.con_r {
  display: inline-block;
  width: 55%;
  min-height: 55px;
  background-color: #e2e2e2;
  border-radius: 6px;
  padding: 10px;
  margin-left: 40px;
}
.time_r {
  margin-left: 45px;
  color: #999999;
  font-size: 12px;
}
.info_l {
  text-align: right;
  color: #ffffff;
  color: #3163C5;
  margin-top: 10px;
}
.pic_l {
  width: 13px;
  height: 17px;
  margin: 8px 10px;
}
.time_l {
  margin-right: 45px;
  color: #999999;
  font-size: 12px;
  margin-top: 5px;
}
.con_l {
  display: inline-block;
  width: 220px;
  min-height: 51px;
  background-color: #3163C5;
  border-radius: 6px;
  padding: 10px;
  text-align: left;
  color: #fff;
  margin-right: 5px;
}
#question {
  cursor: pointer;
}
.setproblem {
  width: 90%;
  height: 68px;
  background-color: #ffffff;
  position: relative;
  margin-top: 20px;
  padding-bottom: 20px;
}
.setproblem textarea {
  margin-bottom: 10px;
  color: #999999;
  padding: 10px;
  box-sizing: border-box;
}
.buttons end {
  background: #3163C5;
  opacity: 1;
  border-radius: 4px;
  font-size: 10px;
  color: #ffffff;
  position: absolute;
  right: -10%;
  top: 30%;
  cursor: pointer;
  border: none;
}

.czar-item-title {
  line-height: 25px;
  border-bottom: 1px solid #ccc;
  padding-bottom: 5px;
  margin-bottom: 5px;
}

.czar-item-question {
  cursor: pointer;
  display: block;
  padding: 8px;
  position: relative;
  border-bottom: 1px dashed #ccc;
  line-height: 20px;
  min-height: 20px;
  overflow: hidden;
}

.czar-question-msg {
  float: left;
  font-size: 14px;
  color: #3163C5;
}
</style>
