import http from "@/utils/http.js";
import router from "@/router/index.js";

export default function heartPdfAPI (id){
    console.log("发起请求")
    return  http.get('api/advice/heartPdf?id=1')

}