import fetch from '@/axios'

export function list(params) {
    return fetch({
        url: '/admin/auth/role/getRoleList',
        method: 'post',
        data: params
    })
}
export function save(params) {
    return fetch({
        url: '/admin/auth/role/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return fetch({
        url: '/admin/auth/role/update',
        method: 'post',
        data: params
    })
}
export function remove(id) {
    return fetch({
        url: '/admin/auth/role/remove/' + id,
        method: 'get'
    })
}

export function getMenuList() {
    return fetch({
        url: '/admin/auth/role/getMenuList',
        method: 'get'
    })
}
