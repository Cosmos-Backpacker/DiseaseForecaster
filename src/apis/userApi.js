import http from "@/utils/http";


/**
 * @description: 根据user对象进行登录
 * @return {*}
 * @param account
 * @param password
 */

//定义请求登录接口的方法
export async function loginAPI(account, password) {
    return http.post('api/user/login', {}, {
        params: {
            userAccount: account,
            userPassword: password
        }
    });
}

/**
 * @description: 注册用户
 * @param account
 * @param password
 * @param checkPassword

 */
export async function registerAPI(account, password, checkPassword) {
    return http.post('api/user/register', {}, {
        params: {
            userAccount: account,
            password: password,
            checkPassword: checkPassword
        }
    });
}


export async function logoutApi() {
    return await http.get('/api/user/userLayout')
}



export async function closeSseLinkApi() {
    return await http.get('/api/user/closeSseLink')
}

