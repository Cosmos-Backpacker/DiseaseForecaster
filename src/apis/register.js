import http from "@/utils/http.js";


//定义注册接口的方法
export function registerAPI(user){

    return http.post('api/register',user);
}