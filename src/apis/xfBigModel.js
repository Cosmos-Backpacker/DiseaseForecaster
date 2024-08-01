import http from "@/utils/http.js";

export const BigModel = (id,text) => {
    return http.get('/api/xfModel/BigModel',{params:{id,text}})

}