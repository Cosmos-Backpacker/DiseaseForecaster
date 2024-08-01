import http from "@/utils/http.js";

export const szRobot = (issue) => {
    console.log("发起请求")
    console.log("问题是:" + issue)
    return http.get('/api/medicalRobot', {params: {issue}})

}