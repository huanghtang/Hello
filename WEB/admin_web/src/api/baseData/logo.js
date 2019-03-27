import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/database/logo/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/database/logo/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/database/logo/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/database/logo/update',
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
export function _getChildList(val) {
    return fetch({
        url: '/admin/database/childkind/getChildList/' + val,
        method: 'post'
    })
}
