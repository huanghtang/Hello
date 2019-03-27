import axios from 'axios'
// import { getToken } from '@/utils/token'
import Vue from '@/main'
import qs from 'qs'

axios.defaults.timeout = 10000 // 響應nrpm時間
axios.defaults.baseURL = '/api' // 配置接口地址
axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
axios.defaults.withCredentials = true

// 創建axios實例
// request攔截器
axios.interceptors.request.use(config => {
    // Do something before request is sent
    // if (getToken()) {
    //     // 讓每個請求攜帶token--['X-Token']為自定義key 請根據實際情況自行修改
    //     config.headers['jxtAdminSessionId'] = getToken()
    // }
    // 在發送請求之前做某件事
    if (config.method === 'post') {
        config.data = qs.stringify(config.data)
    }
    return config
}, error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
})

axios.interceptors.response.use((res) => {
        // 對響應數據做些事
    if (res.data.code === '501') {
        // 業務失敗
        Vue.$message.warning(res.data.msg)
        return Promise.reject(res.data.msg)
    } else if (res.data.code === '401') {
        location.href = "/401";
        //無權訪問
    } else if (res.data.code === 10001 || res.data.code === 10002) {
        Promise.reject(res.data.msg)
    } else {
        return res
    }
}, (error) => {
    console.log(error) // for debug
    return Promise.reject(error)
})

export default axios
