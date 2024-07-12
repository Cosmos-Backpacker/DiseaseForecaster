import http from "@/utils/http";


/**
 * @description: 根据user对象进行登录
 * @param {Number} id 分类id
 * @return {*}
 */

//定义请求登录接口的方法
export function loginAPI(user){
    return http.post('api/login/user',user);
}





/**
 * @description: 获取用户可能喜欢的商品列表
 * @param {{ limit = 4 }}
 * @return {*}
 */


//获取列表信息
export const getLikeListAPI = ({ limit = 4 }) => {
    return http.get('/goods/relevant',{params:{limit}});
}