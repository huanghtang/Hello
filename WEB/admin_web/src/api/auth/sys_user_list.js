import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/auth/account/list',
        method: 'post',
        data: params
    })
}
export function save(params) {
    return fetch({
        url: 'admin/auth/account/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return fetch({
        url: 'admin/auth/account/update',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: 'admin/auth/account/del/' + id,
        method: 'get'
    })
}

export function getRoleList() {
    return fetch({
        url: '/admin/auth/account/getRoleList',
        method: 'get'
    })
}
