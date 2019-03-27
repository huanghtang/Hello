import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/set/help/list',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/set/help/del/' + id,
        method: 'get'
    })
}
export function save(params) {
    return fetch({
        url: '/admin/set/help/add',
        method: 'post',
        data: params
    })
}
export function update(params) {
    return fetch({
        url: '/admin/set/help/update',
        method: 'post',
        data: params
    })
}
export function _getMachineList() {
    return fetch({
        url: '/admin/set/machine/getMachineList',
        method: 'post'
    })
}
