import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/database/banner/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/database/banner/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/database/banner/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/database/banner/update',
        method: 'post',
        data: params
    })
}
