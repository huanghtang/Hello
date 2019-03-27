import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/database/mainkind/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/database/mainkind/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/database/mainkind/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/database/mainkind/update',
        method: 'post',
        data: params
    })
}
