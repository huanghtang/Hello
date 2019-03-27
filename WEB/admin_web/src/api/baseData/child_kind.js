import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/database/childkind/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/database/childkind/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/database/childkind/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/database/childkind/update',
        method: 'post',
        data: params
    })
}
export function _getMainList() {
    return fetch({
        url: '/admin/database/mainkind/getMainList',
        method: 'post'
    })
}
