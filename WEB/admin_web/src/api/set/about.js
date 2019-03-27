import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/set/about/list',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/set/about/update',
        method: 'post',
        data: params
    })
}
