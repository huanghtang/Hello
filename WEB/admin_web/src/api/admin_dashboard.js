import fetch from '@/axios'

export function getYesterdayData() {
    return fetch({
        url: '/admin/dashboard/getYesterdayData',
        method: 'get'
    })
}

export function _getUserData(params) {
    return fetch({
        url: '/admin/dashboard/getProductData',
        method: 'post',
        data: params
    })
}
export function list(params) {
    return fetch({
        url: '/admin/dashboard/userPage',
        method: 'post',
        data: params
    })
}
