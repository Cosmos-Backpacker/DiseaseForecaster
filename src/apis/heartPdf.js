import http from "@/utils/http.js";
import router from "@/router/index.js";
import axios from "axios";

export default function heartPdfAPI (id){
    console.log("发起请求")
    id=3
    console.log(id)

    return axios.get('api/advice/heartPdf',{params:{id}})
}


// export default function heartPdfAPI (id){
//     console.log("发起请求")
//     axios.get('api/advice/heartPdf?id=1')
//
// }