import http from '@/utils/http'
import axios from "axios";


//管理端后台获取用户列表API
export function getCategoryAPI () {

    return axios.get('api/admin')


}

