import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/set/font/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/set/font/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/set/font/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/set/font/update',
        method: 'post',
        data: params
    })
}
