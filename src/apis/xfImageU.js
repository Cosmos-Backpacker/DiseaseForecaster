import http from "@/utils/http.js";

export default function imageUAPI (file,id,text){
    console.log("发起请求")
    return  http.post('/api/xfModel/imageU',file,{params:{id,text}})

}