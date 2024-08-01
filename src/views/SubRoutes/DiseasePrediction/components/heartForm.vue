<template>

  <div class="child">

    <el-form
        ref="ruleFormRef"
        style="max-width: 600px"
        :model="ruleForm"
        :rules="rules"
        label-width="auto"
        class="demo-ruleForm"
        :size="'small'"
        status-icon
    >


      <el-form-item label="姓名" prop="name">
        <el-input v-model="ruleForm.name" type="textarea"/>
      </el-form-item>

      <el-form-item label="性别" prop="sex">
        <el-select v-model="ruleForm.sex" placeholder="Activity zone">
          <el-option label="男" value="shanghai"/>
          <el-option label="女" value="beijing"/>
        </el-select>
      </el-form-item>
      <el-form-item label="年龄" prop="count">
        <el-select-v2
            v-model="ruleForm.count"
            placeholder="Activity count"
            :options="options"
        />
      </el-form-item>


      <el-form-item label="胸部疼痛情况" prop="cp">
        <el-radio-group v-model="ruleForm.cp">
          <el-radio value="1.1">典型心绞痛</el-radio>
          <el-radio value="1.2">非典型心绞痛</el-radio>
          <el-radio value="1.3">没有心绞痛</el-radio>
          <el-radio value="1.4">无症状</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="静息血压" prop="trestbps">
        <el-input v-model="ruleForm.trestbps"/>
      </el-form-item>

      <el-form-item label="胆固醇" prop="chol">
        <el-input v-model="ruleForm.chol"/>
      </el-form-item>

      <el-form-item label="空腹血糖>120mg" prop="fbs">
        <el-radio-group v-model="ruleForm.fbs">
          <el-radio value="2.1">是</el-radio>
          <el-radio value="2.2">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="静息心电图测量情况" prop="estecg">
        <el-radio-group v-model="ruleForm.estecg">
          <el-radio value="3.1">普通</el-radio>
          <el-radio value="3.2">ST-T波异常</el-radio>
          <el-radio value="3.3">左心室肥大</el-radio>
        </el-radio-group>
      </el-form-item>


      <el-form-item label="最高心跳" prop="thalach">
        <el-input v-model="ruleForm.thalach"/>
      </el-form-item>

      <el-form-item label="运动有发心绞痛" prop="exang">
        <el-radio-group v-model="ruleForm.exang">
          <el-radio value="4.1">是</el-radio>
          <el-radio value="4.2">否</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="运动运动后ST段坡度" prop="oldpeak">
        <el-input v-model="ruleForm.oldpeak"/>
      </el-form-item>

      <el-form-item label="运动ST段的峰值斜率" prop="slope">
        <el-radio-group v-model="ruleForm.slope">
          <el-radio value="5.1">上坡</el-radio>
          <el-radio value="5.2">平的</el-radio>
          <el-radio value="5.3">下坡</el-radio>
        </el-radio-group>
      </el-form-item>





      <el-form-item label="主要血管数目" prop="region">
        <el-select v-model="ruleForm.region" placeholder="Activity zone">
          <el-option label="0" value="0"/>
          <el-option label="1" value="1"/>
          <el-option label="2" value="2"/>
          <el-option label="3" value="4"/>
        </el-select>
      </el-form-item>


      <el-form-item label="一种血液疾病" prop="thal">
        <el-radio-group v-model="ruleForm.thal">
          <el-radio value="6.1">正常</el-radio>
          <el-radio value="6.2">固定缺陷</el-radio>
          <el-radio value="6.3">可逆缺陷</el-radio>
        </el-radio-group>
      </el-form-item>

      <!--estecg：静息心电图测量（0：普通；1：ST-T波异常；2：左心室肥大）

      thalach：最高心跳率

      exang：运动诱发心绞痛（1：yes；2：no）

      oldpeak：运动相对于休息引起的ST抑制

      slope：运动ST段的峰值斜率（1：上坡；2：平的；3：下坡）

      ca：主要血管数目（0、1、2、3、4）

      thal：一种血液疾病（1：正常；2：固定缺陷；3：可逆的缺陷）
      -->


      <el-form-item label="最近症状描述" prop="desc">
        <el-input v-model="ruleForm.desc" type="textarea"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm(ruleFormRef)">
          提交
        </el-button>
        <el-button @click="resetForm(ruleFormRef)">清空</el-button>
      </el-form-item>
    </el-form>

  </div>


</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance, FormRules} from 'element-plus'



interface RuleForm {
  name:string,
  sex: string,//性别
  cp:'',//胸部疼痛情况
  thal:'',//一种血液疾病
  ca:'',//主要血管数目
  slope:'',//运动ST段的峰值斜率
  oldpeak:'',//运动相对于休息引起的ST抑制
  exang:'',//运动诱发心绞痛（1：yes；2：no）
  thalach: '',//最高心跳
  estecg: string,  //静息心电图测量
  fbs: string,    //空腹血糖
  chol: string,      //胆固醇
  trestbps: string,    //静息血压
  name: string
  region: string
  count: string
  date1: string
  date2: string
  delivery: boolean
  type: string[]
  resource: string
  desc: string
}

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  sex: '',
  cp:'',
  thal:'',
  ca:'',
  slope:'',
  oldpeak:'',
  exang:'',
  thalach:'',
  estecg: '',
  fbs: '',
  chol: '',
  trestbps: '',
  name: '',
  region: '',
  count: '',
  date1: '',
  date2: '',
  delivery: false,
  type: [],
  resource: '',
  desc: '',
})

const rules = reactive<FormRules<RuleForm>>({
  name: [
    {required: true, message: 'Please input Activity name', trigger: 'blur'},
    //{min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur'},
  ],
  region: [
    {
      required: true,
      message: 'Please select Activity zone',
      trigger: 'change',
    },
  ],
  count: [
    {
      required: true,
      message: 'Please select Activity count',
      trigger: 'change',
    },
  ],
  date1: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a date',
      trigger: 'change',
    },
  ],
  date2: [
    {
      type: 'date',
      required: true,
      message: 'Please pick a time',
      trigger: 'change',
    },
  ],
  type: [
    {
      type: 'array',
      required: true,
      message: 'Please select at least one activity type',
      trigger: 'change',
    },
  ],
  resource: [
    {
      required: true,
      message: 'Please select activity resource',
      trigger: 'change',
    },
  ],
  desc: [
    {required: true, message: 'Please input activity form', trigger: 'blur'},
  ],
})

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const options = Array.from({length: 100}).map((_, idx) => ({
  value: `${idx + 1}`,
  label: `${idx + 1}`,
}))
</script>

