import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/set/machine/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/set/machine/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/set/machine/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/set/machine/update',
        method: 'post',
        data: params
    })
}
