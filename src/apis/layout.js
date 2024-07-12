import http from '@/utils/http'
import axios from "axios";

export function getCategoryAPI () {


    return http.get('api/admin')

    // return axios({
    //     url: 'api/admin',
    //     method: 'get', //默认就是get,这个可以省略，
    //     // params: {//URL查询对象
    //     //     key: value
    //     // }
    // })

}

