import http from "@/utils/http.js";

export default function DiseaseInquire(question){
    console.log("开始调用")
    return http.get('api/algorithm/question',{params:{question}})
}